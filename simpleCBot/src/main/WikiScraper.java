package main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URLEncoder;

public class WikiScraper {
	private long lastRequestTime = 0;
	private static final long DELAY_MS = 3000; 
	
	public static String searchWiki(String query) throws IOException {
        String url = "https://de.wikipedia.org/wiki/" + getWikiUrl(query);
        Document doc = Jsoup.connect(url).get(); 

        // Extract the first paragraph
        Element firstParagraph = doc.select("div.mw-parser-output > p").first();
        if (firstParagraph != null) {
            String text = firstParagraph.text();
            return text.length() > 300 ? text.substring(0, 300) + "..." : text;
        }
        return null;
    }
	
	public String safeWebSearch(String query) throws IOException {
	    long now = System.currentTimeMillis();
	    if (now - lastRequestTime < DELAY_MS) {
	        return "Garry: Bitte warte einen Moment.";
	    }
	    lastRequestTime = now;
	    return WikiScraper.searchWiki(extractSearchQuery(query));
	}
	    
	    public static String extractSearchQuery(String question) {
	        // Match common German question patterns
	        if (question.matches("(?i).*(wer|was|wo|wann|wie|warum)\\s+(ist|sind|war).*")) {
	            return question.replaceAll("(?i)^.*?(wer|was|wo|wann|wie|warum)\\s+(ist|sind|war)\\s+", "")
	                          .replaceAll("[?„“]", "").trim();
	        }
	        return question;
	    }
	    
	    public static String getWikiUrl(String query) throws IOException {
	        String apiUrl = "https://de.wikipedia.org/w/api.php?action=opensearch&search=" 
	                      + URLEncoder.encode(query, "UTF-8") + "&limit=1";
	        
	        Document doc = Jsoup.connect(apiUrl).ignoreContentType(true).get();
	        String json = doc.body().text();
	        
	        String url = json.split("\"")[3]; 
	        
	        return url;
	    }
	
}
