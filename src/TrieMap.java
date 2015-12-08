
import java.util.concurrent.atomic.AtomicReferenceArray;

public class TrieMap {
	public static int OSIZE = 20000;
}

interface  Node {
	public Node getLink(String key, int hash, int level);
	public Node createLink(int hash, int level, String key, String val);
	public Node removeLink(String key, int hash, int level);
}

class Vertex implements Node {
	String key;
	volatile String val;
	volatile Vertex next;
	
	public Vertex(String key, String val) {
		this.key = key;
		this.val = val;
	}
	
	@Override
	public boolean equals(Object obj) {
		Vertex v = (Vertex) obj;
		return this.key.equals(v.key);
	}
	
    @Override
	public int hashCode() {
		return key.hashCode();
	}
	
	@Override
	public String toString() {
		return key +"@"+key.hashCode();
	}
	
	public Node getLink(String key, int hash, int level){
		throw new UnsupportedOperationException();
	}
	public Node createLink(int hash, int level, String key, String val) {
		throw new UnsupportedOperationException();
	}
	public Node removeLink(String key, int hash, int level){
		throw new UnsupportedOperationException();
	}
}


class Edge implements Node {
	volatile AtomicReferenceArray<Node> array; //This is needed to ensure array elements are volatile
	public static Base10ToBaseX.Base base;
	
	public Edge(int size, Base10ToBaseX.Base b) {
		array = new AtomicReferenceArray<Node>(size);
		base = b;
	}
	
    
	@Override
	public Node getLink(String key, int hash, int level){
		int index = Base10ToBaseX.getBaseXValueOnAtLevel(base, hash, level);
		Node returnVal = array.get(index);
		for(;;) {
			if(returnVal == null) {
				return null;
			}
			else if((returnVal instanceof Vertex)) {
				Vertex node = (Vertex) returnVal;
				for(;node != null; node = node.next) {
					if(node.key.equals(key)) {	
						return node; 
					}
				} 
				return null;
			} else { //instanceof Edge
				level = level + 1;
				index = Base10ToBaseX.getBaseXValueOnAtLevel(base, hash, level);
				Edge e = (Edge) returnVal;
				returnVal = e.array.get(index);
			}
		}
	}
	
	
	/**
	 * Iterative approach
	 */
	@Override
	public Node createLink(int hash, int level, String key, String val) { //Remove size
		Node nodeAtIndex = this;
		Edge edgeAtIndex = (Edge) nodeAtIndex;

		for(;;) { //Repeat the work on the current node, since some other thread modified this node
			int index =  Base10ToBaseX.getBaseXValueOnAtLevel(base, hash, level);
			nodeAtIndex = edgeAtIndex.array.get(index);
		    if ( nodeAtIndex == null) {  
		    	Vertex newV = new Vertex(key, val);
		    	boolean result = edgeAtIndex.array.compareAndSet(index, null, newV);
		    	if(result == Boolean.TRUE) {
		    	   	return newV;
		    	}
		    	//continue; since new node is inserted by other thread, hence repeat it.
			} 
		    else if(nodeAtIndex instanceof Vertex) {
		    	Vertex vrtexAtIndex = (Vertex) nodeAtIndex;
		    	int newIndex = Base10ToBaseX.getBaseXValueOnAtLevel(base, vrtexAtIndex.hashCode(), level+1);
		    	int newIndex1 = Base10ToBaseX.getBaseXValueOnAtLevel(base, hash, level+1);
		    	Edge edge = new Edge(base.getLevelZeroMask()+1, base);
		    	if(newIndex != newIndex1) {
		    		Vertex newV = new Vertex(key, val);
		    		edge.array.set(newIndex, vrtexAtIndex);
		    		edge.array.set(newIndex1, newV);
		    		boolean result = edgeAtIndex.array.compareAndSet(index, vrtexAtIndex, edge); //REPLACE vertex to edge
		    	    if(result == Boolean.TRUE) {
		    	    	return newV;
		    	    }
 		    	   //continue; since vrtexAtIndex may be removed or changed to Edge already.
		    	} else if(vrtexAtIndex.key.hashCode() == hash) {//vrtex.hash == hash) {       HERE newIndex == newIndex1
		    		synchronized (vrtexAtIndex) {	
		    			boolean result = edgeAtIndex.array.compareAndSet(index, vrtexAtIndex, vrtexAtIndex); //Double check this vertex is not removed.
			    	    if(result == Boolean.TRUE) {
			    	    	Vertex prevV = vrtexAtIndex;
			    	    	for(;vrtexAtIndex != null; vrtexAtIndex = vrtexAtIndex.next) {
			    	    		prevV = vrtexAtIndex; // prevV is used to handle when vrtexAtIndex reached NULL
			    	    		if(vrtexAtIndex.key.equals(key)){
			    	    			vrtexAtIndex.val = val;
			    	    			return vrtexAtIndex;
			    	    		}
			    	    	} 
			    	    	Vertex newV = new Vertex(key, val);
			    	    	prevV.next = newV; // Within SYNCHRONIZATION since prevV.next may be added with some other.
			    		  	return newV;
			    	    }
			    	    //Continue; vrtexAtIndex got changed
		    		}
		    	} else {   //HERE newIndex == newIndex1  BUT vrtex.hash != hash
		    		edge.array.set(newIndex, vrtexAtIndex);
		    		boolean result = edgeAtIndex.array.compareAndSet(index, vrtexAtIndex, edge); //REPLACE vertex to edge
		    	    if(result == Boolean.TRUE) {
		    	    	level = level + 1;
		    	    	edgeAtIndex = edge;
		    	    }
		    	}
	    	} 		    	
			else {  //instanceof Edge
				level = level + 1;
				edgeAtIndex = (Edge) nodeAtIndex;
				//return nodeAtIndex.createLink(hash, (level + 1), key, val);
			}
		}
	} 
	
	/**
	 * Iterative approach
	 */
	@Override
	public Node removeLink(String key, int hash, int level){
		Node returnVal = this;
		Edge edgeAtIndex = (Edge) returnVal;
		
		for(;;) {
			int index = Base10ToBaseX.getBaseXValueOnAtLevel(base, hash, level);
			returnVal = edgeAtIndex.array.get(index);
			if(returnVal == null) {
				return null;
			}
			else if((returnVal instanceof Vertex)) {
				synchronized (returnVal) {
					Vertex node = (Vertex) returnVal;
					if(node.next == null) {
						if(node.key.equals(key)) {
							boolean result = edgeAtIndex.array.compareAndSet(index, node, null); 
							if(result == Boolean.TRUE) {
								return node;
							}
							continue; //Vertex may be changed to Edge
						}
						return null;  //Nothing found; This is not the same vertex we are looking for. Here hashcode is same but key is different. 
					} else {
						if(node.key.equals(key)) { //Removing the first node in the link
							boolean result = edgeAtIndex.array.compareAndSet(index, node, node.next);
							if(result == Boolean.TRUE) {
								return node;
							}
							continue; //Vertex(node) may be changed to Edge, so try again.
						}
						Vertex prevV = node; // prevV is used to handle when vrtexAtIndex is found and to be removed from its previous
						node = node.next;
						for(;node != null; prevV = node, node = node.next) {
							if(node.key.equals(key)) {
								prevV.next = node.next; //Removing other than first node in the link
								return node; 
							}
						} 
						return null;  //Nothing found in the linked list.
					}
				}
			} else { //instanceof Edge
				level = level + 1;
				edgeAtIndex = (Edge) returnVal;
			}
		}
	} 
	
	/*	
	 * 
	 * This is a recursive approach
	 * 
	 * @Override
		public Node createLink(int hash, int level, String key, String val) { //Remove size
			for(;;) { //Repeat the work on the current node, since some other thread modified this node
				int index =  Base10ToBaseX.getBaseXValueOnAtLevel(base, hash, level);
				Node nodeAtIndex = array.get(index);
			    if ( nodeAtIndex == null) {  
			    	Vertex newV = new Vertex(key, val);
			    	boolean result = array.compareAndSet(index, null, newV);
			    	if(result == Boolean.TRUE) {
			    	   	return newV;
			    	}
			    	//continue; since new node is inserted by other thread, hence repeat it.
				} 
			    else if(nodeAtIndex instanceof Vertex) {
			    	Vertex vrtexAtIndex = (Vertex) nodeAtIndex;
			    	int newIndex = Base10ToBaseX.getBaseXValueOnAtLevel(base, vrtexAtIndex.hashCode(), level+1);
			    	int newIndex1 = Base10ToBaseX.getBaseXValueOnAtLevel(base, hash, level+1);
			    	Edge edge = new Edge(base.getLevelZeroMask()+1);
			    	if(newIndex != newIndex1) {
			    		Vertex newV = new Vertex(key, val);
			    		edge.array.set(newIndex, vrtexAtIndex);
			    		edge.array.set(newIndex1, newV);
			    		boolean result = array.compareAndSet(index, vrtexAtIndex, edge); //REPLACE vertex to edge
			    	    if(result == Boolean.TRUE) {
			    	    	return newV;
			    	    }
	 		    	   //continue; since vrtexAtIndex may be removed or changed to Edge already.
			    	} else if(vrtexAtIndex.key.hashCode() == hash) {//vrtex.hash == hash) {       HERE newIndex == newIndex1
			    		synchronized (vrtexAtIndex) {	
			    			boolean result = array.compareAndSet(index, vrtexAtIndex, vrtexAtIndex); //Double check this vertex is not removed.
				    	    if(result == Boolean.TRUE) {
				    	    	Vertex prevV = vrtexAtIndex;
				    	    	for(;vrtexAtIndex != null; vrtexAtIndex = vrtexAtIndex.next) {
				    	    		prevV = vrtexAtIndex; // prevV is used to handle when vrtexAtIndex reached NULL
				    	    		if(vrtexAtIndex.key.equals(key)){
				    	    			vrtexAtIndex.val = val;
				    	    			return vrtexAtIndex;
				    	    		}
				    	    	} 
				    	    	Vertex newV = new Vertex(key, val);
				    	    	prevV.next = newV; // Within SYNCHRONIZATION since prevV.next may be added with some other.
				    		  	return newV;
				    	    }
				    	    //Continue; vrtexAtIndex got changed
			    		}
			    	} else {   //HERE newIndex == newIndex1  BUT vrtex.hash != hash
			    		edge.array.set(newIndex, vrtexAtIndex);
			    		boolean result = array.compareAndSet(index, vrtexAtIndex, edge); //REPLACE vertex to edge
			    	    if(result == Boolean.TRUE) {
			    	    	return edge.createLink(hash, (level + 1), key, val);
			    	    }
			    	}
		    	} 		    	
				else {  //instanceof Edge
					return nodeAtIndex.createLink(hash, (level + 1), key, val);
				}
			}
		}*/
	
/*	
 * This is recursive approach
 * 
 * 
 * @Override
	public Node removeLink(String key, int hash, int level){
		for(;;) {
			int index = Base10ToBaseX.getBaseXValueOnAtLevel(base, hash, level);
			Node returnVal = array.get(index);
			if(returnVal == null) {
				return null;
			}
			else if((returnVal instanceof Vertex)) {
				synchronized (returnVal) {
					Vertex node = (Vertex) returnVal;
					if(node.next == null) {
						if(node.key.equals(key)) {
							boolean result = array.compareAndSet(index, node, null); 
							if(result == Boolean.TRUE) {
								return node;
							}
							continue; //Vertex may be changed to Edge
						}
						return null;  //Nothing found; This is not the same vertex we are looking for. Here hashcode is same but key is different. 
					} else {
						if(node.key.equals(key)) { //Removing the first node in the link
							boolean result = array.compareAndSet(index, node, node.next);
							if(result == Boolean.TRUE) {
								return node;
							}
							continue; //Vertex(node) may be changed to Edge, so try again.
						}
						Vertex prevV = node; // prevV is used to handle when vrtexAtIndex is found and to be removed from its previous
						node = node.next;
						for(;node != null; prevV = node, node = node.next) {
							if(node.key.equals(key)) {
								prevV.next = node.next; //Removing other than first node in the link
								return node; 
							}
						} 
						return null;  //Nothing found in the linked list.
					}
				}
			} else { //instanceof Edge
				return returnVal.removeLink(key, hash, (level + 1));
			}
		}
	}*/
	
}



class Base10ToBaseX {
	public static enum Base {
		/**
		 * Integer is represented in 32 bit in 32 bit machine.
		 * There we can split this integer no of bits into multiples of 1,2,4,8,16 bits
		 */
		BASE2(1,1,32), BASE4(3,2,16), BASE8(7,3,11)/* OCTAL*/, /*BASE10(3,2),*/ 
		BASE16(15, 4, 8){		
			public String getFormattedValue(int val){
				switch(val) {
				case 10:
					return "A";
				case 11:
					return "B";
				case 12:
					return "C";
				case 13:
					return "D";
				case 14:
					return "E";
				case 15:
					return "F";
				default:
					return "" + val;
				}
				
			}
		}, /*BASE32(31,5,1),*/ BASE256(255, 8, 4), /*BASE512(511,9),*/ Base65536(65535, 16, 2);
		
		private int LEVEL_0_MASK;
		private int LEVEL_1_ROTATION;
		private int MAX_ROTATION;
		
		Base(int levelZeroMask, int levelOneRotation, int maxPossibleRotation) {
			this.LEVEL_0_MASK = levelZeroMask;
			this.LEVEL_1_ROTATION = levelOneRotation;
			this.MAX_ROTATION = maxPossibleRotation;
		}
		
		int getLevelZeroMask(){
			return LEVEL_0_MASK;
		}
		int getLevelOneRotation(){
			return LEVEL_1_ROTATION;
		}
		int getMaxRotation(){
			return MAX_ROTATION;
		}
		String getFormattedValue(int val){
			return "" + val;
		}
	}
	
	public static int getBaseXValueOnAtLevel(Base base, int on, int level) {
		if(level > base.getMaxRotation() || level < 1) {
			return 0; //INVALID Input
		}
		int rotation = base.getLevelOneRotation();
		int mask = base.getLevelZeroMask();

		if(level > 1) {
			rotation = (level-1) * rotation;
			mask = mask << rotation;
		} else {
			rotation = 0;
		}
		return (on & mask) >>> rotation;
	}
}


