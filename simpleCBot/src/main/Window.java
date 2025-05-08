package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Window {
	private boolean isUserInputSend = false;
	private String inputString = "";
	private Font mainFont, textFont, introFont;
	private JFrame frame;
	private JPanel mainPanel, logoPanel;
	private JLabel logoText;
	private JTextArea chatTextArea, userTextArea;
	private JScrollPane mainJScrollPane;
	private ResponseLogic responseLogic;
	
	public Window() {
		// Init new Frame
		frame = new JFrame();
		frame.setTitle("Garry-ChatBot");
		frame.setSize(1000, 1000);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Init Font
		mainFont = new Font("Arial", Font.BOLD, 33);
		textFont = new Font("Arial", Font.PLAIN, 27);
		introFont = new Font("Arial", Font.BOLD, 29);
		
		// Init Labels
		//logoText
		logoText = new JLabel();
		logoText.setText("Garry the ChatBot!");
		logoText.setFont(mainFont);
		//ChatText
		chatTextArea = new JTextArea();
		chatTextArea.setText("Hallo! Was kann ich heute für dich tun? :D");
		chatTextArea.setFont(introFont);
		chatTextArea.setFocusable(false);
		chatTextArea.setEditable(false);
		chatTextArea.setWrapStyleWord(true);
		chatTextArea.setLineWrap(true);
		chatTextArea.setMaximumSize(new Dimension(600,40));
		chatTextArea.setBorder(BorderFactory.createEmptyBorder(0,40,0,0));
		//UserText
		userTextArea = new JTextArea();
		userTextArea.setFont(textFont);
		userTextArea.setEditable(true);
		userTextArea.setLineWrap(true);
		userTextArea.setWrapStyleWord(true);
		userTextArea.setMaximumSize(new Dimension(600,40));
		userTextArea.setBorder(BorderFactory.createEmptyBorder(0,40,0,0));
		
		
		// Init Panels
		logoPanel = new JPanel();
		logoPanel.setLayout(new BorderLayout());
		logoPanel.add(logoText, BorderLayout.NORTH);
		logoPanel.setBorder(BorderFactory.createEmptyBorder(0,350,0,0));
		
		
		//MainPanel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
		mainPanel.add(chatTextArea);
		mainPanel.add(userTextArea);
		mainJScrollPane = new JScrollPane(mainPanel);
		mainJScrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,50,0));
		mainJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		// Init ResponseLogic
		responseLogic = new ResponseLogic();
		
		// Action Hörerrrr
		//User Input
		userTextArea.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            if(userTextArea.getText() != null) {
		            	e.consume();
			            String userInput = userTextArea.getText();
			            mainPanel.remove(userTextArea);
			            // User Input speichern und displayen
			            JTextArea tempUserArea = new JTextArea();
			            tempUserArea.setFont(textFont);
			            tempUserArea.setEditable(false);
			            tempUserArea.setWrapStyleWord(true);
			            tempUserArea.setLineWrap(true);
			            tempUserArea.setMaximumSize(new Dimension(600,70));
			            tempUserArea.setBorder(BorderFactory.createEmptyBorder(0,40,0,0));
			            tempUserArea.setText("User: " + userInput);
			            mainPanel.add(tempUserArea);
			            
			            isUserInputSend = true;
			            mainJScrollPane.revalidate();
			            mainJScrollPane.repaint();
			            setUserInput(userInput);
			            //Bot Antwort
			            botAntwort();
		            }
		        }
		    }
		});
		
		
		// Frame sichtbar machen
		frame.setLayout(new BorderLayout());
		frame.add(logoPanel, BorderLayout.NORTH);
		frame.add(mainJScrollPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public String getUserInputg() {
		return inputString;
	}
	
	public void setUserInput(String pInputString) {
		inputString = pInputString;
	}
	
	public void botAntwort() {
		// Bot Antwort erstellen
				if(isUserInputSend == true) {
				isUserInputSend = false;
				String tempUserString = getUserInputg().toLowerCase();
				JTextArea antwortTextArea = new JTextArea();
				antwortTextArea.setFont(textFont);
				antwortTextArea.setEditable(false);
				antwortTextArea.setWrapStyleWord(true);
				antwortTextArea.setLineWrap(true);
				antwortTextArea.setMaximumSize(new Dimension(600,70));
				antwortTextArea.setBorder(BorderFactory.createEmptyBorder(0,40,0,0));
					
				//Bot Logic and Antworten
				String botAntwort = responseLogic.giveResponse(tempUserString);
				if(botAntwort.length() < 46) {
					antwortTextArea.setMaximumSize(new Dimension(600,35));
				}
				else if(botAntwort.length() < 80 && botAntwort.length() > 48) {
					antwortTextArea.setMaximumSize(new Dimension(600,100));
				}
				else if(botAntwort.length() > 80){
					antwortTextArea.setMaximumSize(new Dimension(600,300));
				}
				antwortTextArea.setText(botAntwort);
				
				mainPanel.add(antwortTextArea);
				mainPanel.add(Box.createRigidArea(new Dimension(0,40)));
				userTextArea.setText("*Text hier..*");
				userTextArea.setFocusable(true);
				mainPanel.add(userTextArea);
				mainJScrollPane.revalidate();
				mainJScrollPane.repaint();
					
			}
	}
	
}
