import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @author Vigneswar Mourouguessin 40057918
 *
 */

public class exchange  {

	static HashMap<String, List<String>> map = new HashMap<String, List<String>>();
	private static LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	static int count=0;
	
	public static void main(String[] args) throws Exception {

		/**
		 * File to be placed at current working directory : String location =
		 * System.getProperty("user.dir"); System.out.println(location);
		 **/
 
		String file = "calls.txt";

		String line;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split(",", 2);
			if (parts.length >= 2) {
				String key = parts[0].substring(1);
				String value[] = parts[1].substring(2).split(",");
				List<String> mylist = new ArrayList<String>();

				for (int i = 0; i < value.length; i++) {
					if ((i + 1) == value.length)
						mylist.add(value[i].substring(0, value[i].length() - 3));
					else
						mylist.add(value[i]);
				}
				count = count + mylist.size();
				map.put(key, mylist);
				
			} else
				System.out.println("ignoring line: " + line);
		}

		/**
		 * Printing the file
		 **/
 	
		System.out.println("** Calls to be made **");
		for (String key : map.keySet()) {
			System.out.println(key + ": " + map.get(key));
		}

		System.out.println();
		reader.close();

		/**
		 * Creating the process
		 **/
 		
		ExecutorService MasterService = Executors.newFixedThreadPool(map.size());
		long Time = System.currentTimeMillis();
		for (String key : map.keySet()) {
		calling child = new calling(key, map.get(key), queue);
		MasterService.execute(child);
		}
		MasterService.shutdown();
		
		/**
		 * Check Master process running
		 **/
 		
		Handshake(MasterService,Time);
		
		
	}

	 static void Handshake(ExecutorService Mservice, long time) {
		 

			while (!Mservice.isTerminated() || System.currentTimeMillis() < time + 1500) {
				if (!queue.isEmpty()) {
					System.out.println(queue.poll());
				}
			}
			System.out.println("\nMaster has received no replies for 1.5 seconds, ending...");

		
	}		
	
	
	}

