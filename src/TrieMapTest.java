
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public class TrieMapTest {
	public static void main(String[] args) throws InterruptedException {
		int [] i = new int[0];
		long end = 0;
		long start = 0;
//		int y = 5;

//		Unmatched received. key-19996470301 created: 19996470301 received:9566963131
//		Unmatched received. key-15630146561 created: 15630146561 received:5200639391
//		Unmatched received. key-6113274431 created: 6113274431 received:16092898201
		
//		Thread.sleep(20000); 
		start = System.currentTimeMillis();
		Edge l = new Edge(8);
		
		String key = "6113274431";
		Node created1 = l.createLink(key.hashCode(), 1, key, key);
		key = "16092898201";
		Node created2 = l.createLink(key.hashCode(), 1, key, key);
		key = "6113274431";
	    Node received1 = l.getLink(key, key.hashCode(), 1);
	    key = "16092898201";
	    Node received2 = l.getLink(key, key.hashCode(), 1);
		
		
		
		RunThread2 r1 = new RunThread2(l);
		r1.start();
		r1.join();
		RunThread2 r2 = new RunThread2(l);
		RunThread2 r3 = new RunThread2(l);
		RunThread2 r4 = new RunThread2(l);
		RunThread2 r5 = new RunThread2(l);
		RunThread2 r6 = new RunThread2(l);
		RunThread2R r7 = new RunThread2R(l);
		RunThread2R r8 = new RunThread2R(l);
		RunThread2R r9 = new RunThread2R(l);
		RunThread2R r10 = new RunThread2R(l);
		RunThread2R r11 = new RunThread2R(l);
		RunThread2R r12 = new RunThread2R(l); 
		RunThread2D r13 = new RunThread2D(l);
		RunThread2D r14 = new RunThread2D(l);
		RunThread2D r15 = new RunThread2D(l);
		RunThread2D r16 = new RunThread2D(l);
		RunThread2D r17 = new RunThread2D(l);
		RunThread2D r18 = new RunThread2D(l); 
		
		r2.start();
		r3.start();
		r4.start();
		r5.start();
		r6.start();
		r7.start();
		r8.start();
		r9.start();
		r10.start();
		r11.start();
		r12.start(); 
		r13.start();
		r14.start();
		r15.start(); 
		r16.start();
		r17.start();
		r18.start(); 
		
		
		r2.join();
		r3.join();
		r4.join();
		r5.join();
		r6.join();
		r7.join();
		r8.join();
		r9.join();
		r10.join();
		r11.join();
		r12.join(); 
		r13.join();
		r14.join();
		r15.join(); 
		r16.join();
		r17.join();
		r18.join(); 
		 end = System.currentTimeMillis();
		System.out.println("Time taken for RadixLink :" + (end - start)); 

//		Thread.sleep(20000);
		
		
		
		
		Map m = new ConcurrentHashMap();
		start = System.currentTimeMillis();
		RunThread3 m1 = new RunThread3(m);
		m1.start();
		m1.join();
		
		RunThread3 m2 = new RunThread3(m);
		RunThread3 m3 = new RunThread3(m);
		RunThread3 m4 = new RunThread3(m);
		RunThread3 m5 = new RunThread3(m);
		RunThread3 m6 = new RunThread3(m);
		RunThread3R m7 = new RunThread3R(m);
		RunThread3R m8 = new RunThread3R(m);
		RunThread3R m9 = new RunThread3R(m);
		RunThread3R m10 = new RunThread3R(m);
		RunThread3R m11 = new RunThread3R(m);
		RunThread3R m12 = new RunThread3R(m);
		RunThread3D m13 = new RunThread3D(m);
		RunThread3D m14 = new RunThread3D(m);
		RunThread3D m15 = new RunThread3D(m);
		RunThread3D m16 = new RunThread3D(m);
		RunThread3D m17 = new RunThread3D(m);
		RunThread3D m18 = new RunThread3D(m); 
		
		m2.start();
		m3.start();
		m4.start();
		m5.start();
		m6.start();
		m7.start();
		m8.start();
		m9.start();
		m10.start();
		m11.start();
		m12.start();
		m13.start();
		m14.start();
		m15.start(); 
		m16.start();
		m17.start();
		m18.start(); 
		
		
		m2.join();
		m3.join();
		m4.join();
		m5.join();
		m6.join();
		m7.join();
		m8.join();
		m9.join();
		m10.join();
		m11.join();
		m12.join(); 
		m13.join();
		m14.join();
		m15.join(); 
		m16.join();
		m17.join();
		m18.join(); 
		end = System.currentTimeMillis();
		System.out.println("Time taken for HashMap :" + (end - start));
		System.out.println("Size" + m.size()); 
		
		
		
		

		
//		 l.createLink(143151304, 15, 1);
		
		//r.removeLink(100, 15, 1);
		Thread.sleep(100000);
		
		
	}
	
}





class RunThread2 extends TestData {
	Edge r;
	public RunThread2(Edge r) {
		this.r = r;
	}
	@Override
	public void run() {

	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= TrieMap.OSIZE; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"1";
	      Node created = r.createLink(key.hashCode(), 1, key, key);
//	      if(created instanceof Edge) {
//	    	  System.out.println("It is not created the leaf node.");
//	      }
//	      Node received = r.getLink(key, key.hashCode(), 1);
//	      if(created != received) {
//	    	  System.out.println("Unmatched received. key-"+key + " created: "+ created + " received:" +received +" " + r.getLink(key, key.hashCode(), 1));
//	      }
	    }
	    
	   /* for (Map.Entry<String, String> e : m.entrySet()) {
	      String key = e.getKey();
	      r.createLink(key.hashCode(), 1, 1, key, key);
	    }*/
		

		
		
//	      r.getLink(key.hashCode(), 1);
//	      System.out.println(r.getLink(randomInt, 1));
	    
	}
}

class RunThread2R extends TestData {
	Edge r;
	public RunThread2R(Edge r) {
		this.r = r;
	}
	@Override
	public void run() {

	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= TrieMap.OSIZE; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"1";
	      r.getLink(key, key.hashCode(), 1);
	    }
	    
	   /* for (Map.Entry<String, String> e : m.entrySet()) {
	      String key = e.getKey();
	      r.createLink(key.hashCode(), 1, 1, key, key);
	    }*/
		
		/*int y = 5;
		Edge l = r.createLink(y, 1, 1, y + "", y + "");
		y = 13;
		l = r.createLink(y, 1, 1, y + "", y + "");
		y = 9;
		l = r.createLink(y, 1, 1, y + "", y + "");
		y = 29;
		l = r.createLink(y, 1, 1, y + "", y + ""); */
		
		
//	      r.getLink(key.hashCode(), 1);
//	      System.out.println(r.getLink(randomInt, 1));
	    
	}
}


class RunThread2D extends TestData {
	Edge r;
	public RunThread2D(Edge r) {
		this.r = r;
	}
	@Override
	public void run() {

	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= TrieMap.OSIZE; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"1";
	      Node n = r.removeLink(key, key.hashCode(), 1);
//	      System.out.println("Deleted Node: " + n);
	      
	    }
	    
	   /* for (Map.Entry<String, String> e : m.entrySet()) {
	      String key = e.getKey();
	      r.createLink(key.hashCode(), 1, 1, key, key);
	    }*/
		
		/*int y = 5;
		Edge l = r.createLink(y, 1, 1, y + "", y + "");
		y = 13;
		l = r.createLink(y, 1, 1, y + "", y + "");
		y = 9;
		l = r.createLink(y, 1, 1, y + "", y + "");
		y = 29;
		l = r.createLink(y, 1, 1, y + "", y + ""); */
		
		
//	      r.getLink(key.hashCode(), 1);
//	      System.out.println(r.getLink(randomInt, 1));
	    
	}
}


class RunThread3 extends TestData {
	Map mymap;
	public RunThread3(Map r) {
		this.mymap = r;
	}
	@Override
	public void run() {

	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= TrieMap.OSIZE; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"2";
	      mymap.put(key, key);
//	      mymap.get(randomInt+"2");
	    }
	    
	    /*for (Map.Entry e : m.entrySet()) {
	      mymap.put(e.getKey(), e.getValue());
//	      mymap.get(e.getKey());
	    }*/
	}
}


class RunThread3R extends TestData {
	Map mymap;
	public RunThread3R(Map r) {
		this.mymap = r;
	}
	@Override
	public void run() {

	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= TrieMap.OSIZE; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"2";
	      mymap.get(randomInt+"2");
	    }
	    
	    /*for (Map.Entry e : m.entrySet()) {
	      mymap.put(e.getKey(), e.getValue());
//	      mymap.get(e.getKey());
	    }*/
	}
}


class RunThread3D extends TestData {
	Map mymap;
	public RunThread3D(Map r) {
		this.mymap = r;
	}
	@Override
	public void run() {

	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= TrieMap.OSIZE; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"2";
	      mymap.remove(randomInt+"2");
	    }
	    
	    /*for (Map.Entry e : m.entrySet()) {
	      mymap.put(e.getKey(), e.getValue());
//	      mymap.get(e.getKey());
	    }*/
	}
}

class TestData extends Thread {
	
	public static Map<String, String> m = null;
	/*static {
		m = new ConcurrentHashMap();
		//note a single Random object is reused here
	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= 100000; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      m.put(randomInt+"2", randomInt+"2");
	      m.get(randomInt+"2");
	    }
	}*/
	

}