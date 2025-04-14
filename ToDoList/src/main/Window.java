package main;

import java.awt.*;
import javax.swing.*;

public class Window {
	private JFrame win;
	private JLabel mainText, deleteLabel;
	private Font mainFont, buttonFont, textFont;
	private JPanel mainPanel;
	private JButton addButton, deleteButton;
	private TextField tempTextField;
	private int countTextFields = 0;
	private boolean deleteModus = false;
	
	public Window() {
		// Init new Frame
		win = new JFrame();
		win.setTitle("To-Do-List 1.0");
		win.setSize(1000, 1000);
		win.setResizable(false);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Init Font
		mainFont = new Font("Arial", Font.BOLD,  33);
		buttonFont = new Font("Arial", Font.PLAIN, 25);
		textFont = new Font("Arial", Font.PLAIN, 20);
		
		// Init TextField
		tempTextField = new TextField();
		tempTextField.setBackground(Color.LIGHT_GRAY);
		tempTextField.setMaximumSize(new Dimension(500, 60));
		
		//Init MainText
		mainText = new JLabel();
		mainText.setText("TO DO LIST:");
		mainText.setFont(mainFont);
		mainText.setOpaque(false);
		// Init deleteText
		deleteLabel = new JLabel();
		deleteLabel.setText("LÖSCH MODUS: AKTIVIERT!");
		deleteLabel.setFont(mainFont);
		deleteLabel.setOpaque(true);
		
		//Init Buttons
		addButton = new JButton();
		addButton.setText("Neue Aufgabe!");
		addButton.setFont(buttonFont);
		addButton.setOpaque(false);
		// Init DeleteButton
		deleteButton = new JButton();
		deleteButton.setText("LÖSCHEN");
		deleteButton.setFont(buttonFont);
		deleteButton.setBorder(BorderFactory.createEmptyBorder(5,50,15,50));
		deleteButton.setOpaque(false);

		// Init Panel für DeleteButton
		JPanel deletePanel = new JPanel();
		deletePanel.setLayout(new BorderLayout());
		deletePanel.setOpaque(false);
		deletePanel.add(deleteButton, BorderLayout.SOUTH);
		//Init Panel für DeleteText
		JPanel delTextPanel = new JPanel();
		delTextPanel.setLayout(new BorderLayout());
		delTextPanel.setOpaque(false);
		delTextPanel.add(deleteLabel, BorderLayout.SOUTH);
		deleteLabel.setForeground(deletePanel.getBackground());
        	
		
		//Init MainPanel
		mainPanel = new JPanel();
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(mainText) ;
		mainPanel.add(Box.createRigidArea(new Dimension(-10,5)));
		mainPanel.add(addButton);
		
		
		// Lösch Modus
		deleteButton.addActionListener(eu ->{
			if(deleteModus == true) {
				deleteModus = false;
				deleteLabel.setForeground(deletePanel.getBackground());
			}
			else if(deleteModus == false) {
				deleteModus = true;
				deleteLabel.setForeground(Color.black);
			}
		}
		);
		
		
		// Check if AddButton clicked, dann check if TaskText set, dann add new TaskBox
		tempTextField.addActionListener(ev -> {
		    String taskText = tempTextField.getText().trim();
		    JCheckBox  taskBox = new JCheckBox();
		    JTextArea taskArea = new JTextArea(taskText);
		    taskArea.setLineWrap(true);
		    taskArea.setWrapStyleWord(true);
		    taskArea.setEditable(false);
		    taskArea.setFocusable(false);
		    taskArea.setOpaque(false);
		    taskArea.setFont(textFont);
		    taskArea.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));
		    taskArea.setMaximumSize(new Dimension(350, 100));
		    
		    if (!taskText.isEmpty()) {
		    	JPanel taskPanel = new JPanel();
		        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.X_AXIS));
		        taskPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		        
		        taskPanel.add(taskBox);
		        taskPanel.add(Box.createRigidArea(new Dimension(20, 5))); 
		        taskPanel.add(taskArea);
		        
		        tempTextField.setText("");
		        mainPanel.remove(tempTextField);
		        mainPanel.add(taskPanel);
		        countTextFields = 0;

		        mainPanel.revalidate();
		        mainPanel.repaint();
		        taskBox.addActionListener(k ->{
			    	if (deleteModus) {
						mainPanel.remove(taskPanel);
						mainPanel.revalidate();
				        mainPanel.repaint();
					}
			    });
		    }
		});
		
		// If "Neue Aufgabe" geklicked, dann add new TextField
		addButton.addActionListener(e -> {
		    if (countTextFields == 0) {
		        mainPanel.add(tempTextField);
		        countTextFields++;
		        mainPanel.revalidate();
		        mainPanel.repaint();
		    }
		});
		
		// Background 
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/res/images/collegeblock.jpg"));
        Image backgroundImage = backgroundIcon.getImage();

        // Custom JPanel for background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
		//Dem Frame MainPanel hinzufügen
        win.setContentPane(backgroundPanel);
        win.setLayout(new BorderLayout());
		win.add(mainPanel, BorderLayout.CENTER);
		win.add(deletePanel, BorderLayout.SOUTH);
		win.add(delTextPanel, BorderLayout.WEST);
		win.setVisible(true);
		
		
		
	}
	
}
