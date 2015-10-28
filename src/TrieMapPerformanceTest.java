
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


public class TrieMapPerformanceTest {
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
		
		
		
		CreateLinkThread r1 = new CreateLinkThread(l);
		r1.start();
		r1.join();
		CreateLinkThread r2 = new CreateLinkThread(l);
		CreateLinkThread r3 = new CreateLinkThread(l);
		CreateLinkThread r4 = new CreateLinkThread(l);
		CreateLinkThread r5 = new CreateLinkThread(l);
		CreateLinkThread r6 = new CreateLinkThread(l);
		ReadLinkThread r7 = new ReadLinkThread(l);
		ReadLinkThread r8 = new ReadLinkThread(l);
		ReadLinkThread r9 = new ReadLinkThread(l);
		ReadLinkThread r10 = new ReadLinkThread(l);
		ReadLinkThread r11 = new ReadLinkThread(l);
		ReadLinkThread r12 = new ReadLinkThread(l); 
		RemoveLinkThread r13 = new RemoveLinkThread(l);
		RemoveLinkThread r14 = new RemoveLinkThread(l);
		RemoveLinkThread r15 = new RemoveLinkThread(l);
		RemoveLinkThread r16 = new RemoveLinkThread(l);
		RemoveLinkThread r17 = new RemoveLinkThread(l);
		RemoveLinkThread r18 = new RemoveLinkThread(l); 
		
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
		CreateMapThread m1 = new CreateMapThread(m);
		m1.start();
		m1.join();
		
		CreateMapThread m2 = new CreateMapThread(m);
		CreateMapThread m3 = new CreateMapThread(m);
		CreateMapThread m4 = new CreateMapThread(m);
		CreateMapThread m5 = new CreateMapThread(m);
		CreateMapThread m6 = new CreateMapThread(m);
		ReadMapThread m7 = new ReadMapThread(m);
		ReadMapThread m8 = new ReadMapThread(m);
		ReadMapThread m9 = new ReadMapThread(m);
		ReadMapThread m10 = new ReadMapThread(m);
		ReadMapThread m11 = new ReadMapThread(m);
		ReadMapThread m12 = new ReadMapThread(m);
		RemoveMapThread m13 = new RemoveMapThread(m);
		RemoveMapThread m14 = new RemoveMapThread(m);
		RemoveMapThread m15 = new RemoveMapThread(m);
		RemoveMapThread m16 = new RemoveMapThread(m);
		RemoveMapThread m17 = new RemoveMapThread(m);
		RemoveMapThread m18 = new RemoveMapThread(m); 
		
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





class CreateLinkThread extends TestData {
	Edge r;
	public CreateLinkThread(Edge r) {
		this.r = r;
	}
	@Override
	public void run() {
	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= TrieMap.OSIZE; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"1";
	      Node created = r.createLink(key.hashCode(), 1, key, key);
	    }
	}
}

class ReadLinkThread extends Thread {
	Edge r;
	public ReadLinkThread(Edge r) {
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
	}
}


class RemoveLinkThread extends Thread {
	Edge r;
	public RemoveLinkThread(Edge r) {
		this.r = r;
	}
	@Override
	public void run() {

	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= TrieMap.OSIZE; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"1";
	      Node n = r.removeLink(key, key.hashCode(), 1);
	    }
	}
}


class CreateMapThread extends Thread {
	Map mymap;
	public CreateMapThread(Map r) {
		this.mymap = r;
	}
	@Override
	public void run() {

	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= TrieMap.OSIZE; ++idx){
	      int randomInt = randomGenerator.nextInt(Integer.MAX_VALUE);
	      String key = randomInt+"2";
	      mymap.put(key, key);
	    }
	}
}


class ReadMapThread extends Thread {
	Map mymap;
	public ReadMapThread(Map r) {
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
	}
}


class RemoveMapThread extends Thread {
	Map mymap;
	public RemoveMapThread(Map r) {
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
	}
}

