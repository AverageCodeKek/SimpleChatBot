package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseLogic {
	private WikiScraper scraper = new WikiScraper();
	
	 private Map<String, String> userMemory;
	    private String lastUserInput; 
	    private String currentMood; 
	    
	    public ResponseLogic() {
	        userMemory = new HashMap<>();
	        currentMood = "neutral";
	    }
	    
	    public String giveResponse(String pUserString) {
	        String tempUserString = pUserString.toLowerCase();
	        String responseString = "Garry: Tut mir leid. Das verstehe ich nicht...";
	        
	        analyzeMood(tempUserString);
	        lastUserInput = tempUserString;
	        
	        // Check for greetings
	        if(tempUserString.contains("hi") || tempUserString.contains("hello") || 
	           tempUserString.contains("hey") || tempUserString.contains("hallo")) {
	            responseString = handleGreeting();
	        }
	        else if(tempUserString.contains("was") || tempUserString.contains("wer") || 
	                tempUserString.contains("wo") || tempUserString.contains("wie") || tempUserString.contains("?")) {
	            responseString = handleQuestion(tempUserString);
	        }
	        else if(tempUserString.contains("gut") || tempUserString.contains("super") || tempUserString.contains("hamma") || 
	                tempUserString.contains("mega") || tempUserString.contains("fantastisch") || tempUserString.contains("toll") || tempUserString.contains("cool")) {
	            responseString = handlePositiveFeeling();
	        }
	        else if(tempUserString.contains("schlecht") || tempUserString.contains("mies") || tempUserString.contains("scheiße") || 
	                tempUserString.contains("traurig") || tempUserString.contains("nicht gut")) {
	            responseString = handleNegativeFeeling();
	        }
	        else if(tempUserString.contains("du ") || tempUserString.contains("garry") ||  tempUserString.contains("bot ")){
	            responseString = handleAboutGarry(tempUserString);
	        }
	        else if(lastUserInput != null && 
	                (lastUserInput.contains("wie geht") || lastUserInput.contains("wie fühl"))) {
	            responseString = "Garry: Ah, du antwortest auf meine Frage von vorhin! " + 
	                           (currentMood.equals("positive") ? 
	                           "Freut mich, dass es dir gut geht!" : 
	                           "Schade, dass du dich so fühlst...");
	        }
	        else if(tempUserString.matches(".*(ich bin|mein name ist|ich heiße)\\s+([a-z]+).*")) {
	            String name = tempUserString.replaceAll(".*(ich bin|mein name ist|ich heiße)\\s+([a-z]+).*", "$2");
	            userMemory.put("name", name);
	            responseString = "Garry: Hallo " + name + "! Schön dich kennenzulernen!";
	        }
	        else if(tempUserString.contains(" ich heiße") || tempUserString.contains("bin ich") || tempUserString.contains("mein name")
	        		|| tempUserString.contains("heiße ich") || tempUserString.contains("kennst du mich")) {
	        	if(userMemory.containsKey("name")) {
	        	    responseString = "Garry: Natürlich erinnere ich mich! Du bist " + userMemory.get("name") + "!";
	        	} else {
	        	    responseString = "Garry: Ups, habs vergessen... Wie war nochmal dein Name?";
	        	}
	        }
	        else if(tempUserString.contains("stimmt") ) {
	        	responseString = "Garry: Joa, stimmt wohl . . .";
	        }

	        return responseString; // temp
	    }
	    
	    
	    
	    private String handleGreeting() {
	        if(userMemory.containsKey("name")) {
	            return "Garry: Hallo " + userMemory.get("name") + "!  Wie geht's?";
	        } else {
	            return "Garry: Ja Hallo! Wie geht es dir? :)";
	        }
	    }
	    
	    private String handleQuestion(String question) {
	        if(question.contains("wie geht")) {
	            return "Garry: Mir geht's " + 
	                  (currentMood.equals("positive") ? "prima" : 
	                   currentMood.equals("negative") ? "nicht so gut" : "ganz okay") + 
	                  ". Und dir?";
	        }
	        else if(question.contains("was kannst du") || question.contains("funktionen")) {
	            return "Garry: Ich kann einfache Gespräche führen und mich an ein paar Dinge erinnern. "
	                 + "Aber ich lerne noch!";
	        }
	        else if(question.contains(" ich heiße") ||question.contains("bin ich") || question.contains("mein name")
	        		|| question.contains("heiße ich") || question.contains("kennst du mich")) {
	        	if(userMemory.containsKey("name")) {
	        		return  "Garry: Hmm... ach ja! Du bist " + userMemory.get("name") + "!";
	        	} else {
	        		return  "Garry: Entschuldigung, ich habe vergessen wie du heißt. Wie war nochmal dein Name?";
	        	}
	        }
	        else if (question.matches("(?i).*(wer|was|wo|wann|wie|warum)\\s+(ist|sind|war).*")) {
	            String tempWikiTextString;
				try {
					tempWikiTextString = scraper.safeWebSearch(question);
					 return "Garry: Das habe ich gefunden: " + tempWikiTextString;
				} catch (IOException e) {
					return "Garry: Hoopla, da ist was schiefgegangen bei der Suche...";
				}
	        }
	        return "Garry: Das weiß ich leider noch nicht :(";
	    }
	    
	    private String handlePositiveFeeling() {
	        currentMood = "positive";
	        if(lastUserInput != null && lastUserInput.contains("mir geht") || lastUserInput.contains("ich fühle mich")) {
	            return "Garry: Das freut mich zu hören! :)";
	        }
	        return "Garry: Das ist doch super, oder nicht? :O";
	    }
	    
	    private String handleNegativeFeeling() {
	        currentMood = "negative";
	        if(lastUserInput != null && lastUserInput.contains("mir geht")) {
	            return "Garry: Oh nein, das tut mir leid. Möchtest du darüber reden?";
	        }
	        return "Garry: Oh... Soll ich dir helfen?";
	    }
	    
	    private String handleAboutGarry(String input) {
	        if(input.contains("wie alt")) {
	            return "Garry: Ich bin erst seit kurzem aktiv, also quasi ein Baby-Bot!";
	        }
	        else if(input.contains("woher")) {
	            return "Garry: Ich komme aus den Tiefen des Java-Codes!";
	        }
	        else if(input.contains("magst")) {
	            return "Garry: Ich mag es, mit dir zu reden! Auch wenn ich nicht viel verstehe...";
	        }
	        return "Garry: Über mich? Ich bin nur ein einfacher Chatbot. Frag mich was einfaches!";
	    }
	    
	    private void analyzeMood(String input) {
	        if(input.matches(".*\\b(gut|super|mega|fantastisch|glücklich|wunderbar)\\b.*")) {
	            currentMood = "positive";
	        }
	        else if(input.matches(".*\\b(schlecht|mies|traurig|unglücklich|nicht gut|deprimiert|scheisse|scheiße)\\b.*")) {
	            currentMood = "negative";
	        }
	    }
}
