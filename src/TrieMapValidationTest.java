
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public class TrieMapValidationTest {
	
	public static final int SIZE = 100000;
	
	public boolean testAllInputsAreAdded() {
		return true;
	}
	
	public boolean testAllInputsAreRemoved() {
		return true;
	}
	
	public boolean testAllInputsAreGet() {
		return true;
	}
	
	public static void main(String[] args) throws InterruptedException {
	
		Map<String, String> m = new ConcurrentHashMap();
	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= SIZE; ++idx) {
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"2";
	      m.put(key, key);
	    }
	    
	    Edge l = new Edge(8);
	    Vertex received;
	    
	    
	    //TEST same hashcode KEYS are removed properly.
	    received = (Vertex) l.createLink("921570252".hashCode(), 1, "921570252", "921570252");
	    received = (Vertex) l.createLink("9009227462".hashCode(), 1, "9009227462", "9009227462");
	    received = (Vertex) l.removeLink("9009227462", "9009227462".hashCode(), 1);
	    received = (Vertex) l.removeLink("9009227462", "9009227462".hashCode(), 1);
	    
	    
	    
	    for (Map.Entry<String, String> e : m.entrySet()) {
	    	received = (Vertex) l.createLink(e.getKey().hashCode(), 1, e.getKey(), e.getValue());
	    	if(!(e.getKey().equals(received.key) || e.getValue().equals(received.val))){
	    		System.out.println("Invalid PUT !!");
	    	}
	    	
	    	received = (Vertex) l.getLink(e.getKey(), e.getKey().hashCode(), 1);
	    	
	    	if(received == null) {
	    		System.out.println("Found NULL");
	    	}
	    	
	    	if(!(e.getKey().equals(received.key) || e.getValue().equals(received.val))){
	    		System.out.println("Invalid GET !!");
	    	}

	    }
	    
	    
	    
	    for (Map.Entry<String, String> e : m.entrySet()) {
	    	
	    	received = (Vertex) l.removeLink(e.getKey(), e.getKey().hashCode(), 1);
	    	if(!(e.getKey().equals(received.key) || e.getValue().equals(received.val))){
	    		System.out.println("Invalid REMOVE !!");
	    	}
	    	
	    	received = (Vertex) l.removeLink(e.getKey(), e.getKey().hashCode(), 1);
	    	if(received != null){
	    		System.out.println("Previous REMOVE is not successful !!");
	    	}
	    }
	}		
		
}