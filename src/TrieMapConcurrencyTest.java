
import java.util.Random;


public class TrieMapConcurrencyTest {
	public static void main(String[] args) throws InterruptedException {

		long start = System.currentTimeMillis();
		Edge l = new Edge(8);
		
		CreateTrieThread_CT r1 = new CreateTrieThread_CT(l);
		CreateTrieThread_CT r2 = new CreateTrieThread_CT(l);
		CreateTrieThread_CT r3 = new CreateTrieThread_CT(l);
		CreateTrieThread_CT r4 = new CreateTrieThread_CT(l);
		CreateTrieThread_CT r5 = new CreateTrieThread_CT(l);
		CreateTrieThread_CT r6 = new CreateTrieThread_CT(l);
		ReadTrieThread_CT r7 = new ReadTrieThread_CT(l);
		ReadTrieThread_CT r8 = new ReadTrieThread_CT(l);
		ReadTrieThread_CT r9 = new ReadTrieThread_CT(l);
		ReadTrieThread_CT r10 = new ReadTrieThread_CT(l);
		ReadTrieThread_CT r11 = new ReadTrieThread_CT(l);
		ReadTrieThread_CT r12 = new ReadTrieThread_CT(l); 
		RemoveTrieThread_CT r13 = new RemoveTrieThread_CT(l);
		RemoveTrieThread_CT r14 = new RemoveTrieThread_CT(l);
		RemoveTrieThread_CT r15 = new RemoveTrieThread_CT(l);
		RemoveTrieThread_CT r16 = new RemoveTrieThread_CT(l);
		RemoveTrieThread_CT r17 = new RemoveTrieThread_CT(l);
		RemoveTrieThread_CT r18 = new RemoveTrieThread_CT(l); 
		
		r1.start();
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
		
		r1.join();
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
		long end = System.currentTimeMillis();
		System.out.println("Time taken for RadixLink :" + (end - start)); 
		Thread.sleep(100000);
	}
}





class CreateTrieThread_CT extends TestData {
	Edge r;
	public CreateTrieThread_CT(Edge r) {
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

class ReadTrieThread_CT extends TestData {
	Edge r;
	public ReadTrieThread_CT(Edge r) {
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


class RemoveTrieThread_CT extends TestData {
	Edge r;
	public RemoveTrieThread_CT(Edge r) {
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


