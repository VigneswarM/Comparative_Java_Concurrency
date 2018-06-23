import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @author Vigneswar Mourouguessin 40057918
 *
 */

public class calling implements Runnable {

	String key;
	List<String> list;
	public static List<String> Tlist = new ArrayList<String>();
	private LinkedList<String> ReponseList = new LinkedList<String>();
	public LinkedBlockingQueue<String> queue;

	public calling(String key, List<String> list, LinkedBlockingQueue<String> queue) {
		this.key = key;
		this.list = list;
	    this.queue = queue;
		Tlist.add(key);

	}

	@Override
	public void run() { 

		list.stream().forEach(x -> {

			try {
				Thread.sleep(100);
				long Time = System.nanoTime();
				String Line = x + " received " + "intro" + " from " + key + " [" + Time + "]";
				queue.put(Line);
				Line = key + " received " + "reply" + " from " + x + " [" + Time + "]";
				Thread.sleep(4);
				Message(Line);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		
		long time = System.currentTimeMillis();
		while (System.currentTimeMillis() < time + 1000) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\nProcess " + key + " has received no calls for 1 seconds, ending...");

	}

	private void Message(String Line) throws Exception {

		ReponseList.add(Line);
		while (!ReponseList.isEmpty()) {
			queue.put(ReponseList.poll());
		}
		

	}

}
