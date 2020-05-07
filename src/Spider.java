import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/****************************************************************************
 * <b>Title</b>: Spider.java <b>Project</b>: WebSpider <b>Description: </b>
 * CHANGE ME!! <b>Copyright:</b> Copyright (c) 2020 <b>Company:</b> Silicon
 * Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 6, 2020
 * @updates:
 ****************************************************************************/
public class Spider {

	private Object inputLine = null;
	private static String readStatus = "closed";
	private String site;
	private ArrayList<String> siteLinks = new ArrayList<>();

	Spider(String site) throws MalformedURLException {
		this.site = site;
	}

	/**
	 * Parses the site HTML for
	 * <ul>
	 * and then if there anchor tags adds their href values to the siteLInks list
	 * and returns it
	 * 
	 * @param page receives a string the contains the value of the page extension to
	 *             scan
	 * @return an arrayList of possible siteLinks
	 * @throws IOException If there is an exception throws it out of the method.
	 */
	public ArrayList<String> scanSite(String page) throws IOException {

		// Adds the current page to the list of siteLinks
		siteLinks.add(page);

		// Appends the sub-directory to the URL
		site = site + page;

		// Converts the site String to a URL
		URL siteURL = new URL(site);

		// Replaces forbidden characters in the page String to adhere to Linux naming
		// standards
		page = page.replaceAll("/", "-");

		// Opens a buffered reader
		BufferedReader in = new BufferedReader(new InputStreamReader(siteURL.openStream()));

		// While there is still input in the stream continue
		while ((inputLine = in.readLine()) != null) {

			// If the line contains ul then toggle status
			if (inputLine.toString().contains("ul")) {
				if (readStatus.equals("closed"))
					readStatus = "open";
				else
					readStatus = "closed";
			}

			// If there is an anchor tag
			if (inputLine.toString().contains("<a")) {
				// And there is a href
				if (inputLine.toString().contains("href")) {
					// Then grab the subString between the quotes
					String subOne = inputLine.toString().substring(inputLine.toString().indexOf("\"/") + 1);
					String subTwo = subOne.substring(0, subOne.indexOf('"'));
					// Ignore it if it is too short or it has a "." in it
					if (subTwo.length() > 1 && subTwo.startsWith("/") && !subTwo.contains(".")) {
						// Ignore if it has already been collected
						if (!siteLinks.contains(subTwo)) {
							// Add to the ArrayList
							siteLinks.add(subTwo);
						}
					}
				}
			}
		}
		// Close the Stream
		in.close();
		return (ArrayList<String>) siteLinks;
	}

	/**
	 * readSite receives a site sub-directory and writes the pages HTML to a .txt
	 * file
	 * 
	 * @param page receives a String representing a sub-directory
	 * @throws IOException If there is an exception throws it out of the method.
	 */
	public void readSite(String page) throws IOException {

		// Appends the sub-directory to the URL
		site = site + page;

		// Converts the site String to a URL
		URL siteURL = new URL(site);

		// Replaces forbidden characters in the page String to adhere to Linux naming
		// standards
		page = page.replaceAll("/", "-");

		// Opens a buffered reader
		BufferedReader in = new BufferedReader(new InputStreamReader(siteURL.openStream()));

		
		// Sets the directory and name of the file to be created ---------------------------- CHANGE ME TO YOUR DIRECTORY ----------------------
		File outputFile = new File("/home/justinjeffrey/spider", page);

		// Creates a Stream out instance
		FileWriter out = new FileWriter(outputFile);

		// While there is still input in the stream continue
		while ((inputLine = in.readLine()) != null) {

			// Write the input stream to the output stream
			out.write(inputLine.toString());
		}

		// Close the streams
		in.close();
		out.close();
	}
}
