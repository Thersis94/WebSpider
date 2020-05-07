import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/****************************************************************************
 * <b>Title</b>: WebSpider.java <b>Project</b>: WebSpider <b>Description: </b>
 * CHANGE ME!! <b>Copyright:</b> Copyright (c) 2020 <b>Company:</b> Silicon
 * Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 5, 2020
 * @updates:
 ****************************************************************************/
public class Process {

	private static List<String> menuLinks = new ArrayList<>();

	/**
	 * Main method for webspider.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// Creates an instance of the Spider class
		Spider spider = new Spider("https://www.siliconmtn.com");

		// Sets the returned array from spiders scanSite method equal to the menuLinks
		// array
		menuLinks = spider.scanSite("/");

		// For each link in the menuLinks array
		for (String link : menuLinks) {

			Thread thread = new Thread() {
				public void run() {
					try {
						spider.readSite(link);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			// Create a new Thread and run the Spider readSite method for the link
			thread.start();
		}
	}
}
