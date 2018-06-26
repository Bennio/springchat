package vn.edu.ifi.springchat.googleurlsearch;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ch.qos.logback.core.boolex.Matcher;

public class Crawler {
	private static Pattern patternDomainName;
	private Matcher matcher;
	private static final String DOMAIN_NAME_PATTERN  = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
	static {
		patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
	 }
	public String getDataFromGoogle(String query) {
		String crawler = "http://www.ifi.edu.vn"; 
		System.out.println("institut francophone innovation"+" "+query);
		query = "institut francophone innovation"+" "+query; 
		Set<String> result = new HashSet<String>();	
		String request = "https://www.google.com/search?q=" + query ;
		System.out.println("Sending request..." + request);
			
		try {
			System.out.println("Sending request...1");
		      // fetch the document over HTTP
		      Document doc = Jsoup.connect("http://google.com/search?q="+query).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
		      System.out.println("Sending request...2");
		      // get the page title
		      String title = doc.title();
		      //System.out.println("title: " + title);
		      
		      // get all links in page
		      Elements links = doc.select("a[href]"); 
		      boolean trouver = false ; 
		      int i = 0; 
		      while(trouver == false && i < links.size()) {
		      //for (org.jsoup.nodes.Element link : links) {
		        // get the value from the href attribute
		        
		        String url = links.get(i).attr("href"); 
		       
		        String titre = links.get(i).text(); 
		        // && titre.contains(query)) || ()
		        if(url.contains("ifi.edu.vn")) {
		        	System.out.println("\nlink: " + links.get(i).attr("href"));
		        	System.out.println("text: " + links.get(i).text());
		        	crawler = url ; 
		        	trouver = true ; 
		        }
		        i++; 
		      }
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		return crawler  ; 
	  }
}
