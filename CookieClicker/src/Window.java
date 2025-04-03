

import java.awt.*;
import javax.swing.*;

public class Window{
    private JFrame frame;
    private JLabel label;
    private JPanel panel;
    private JTextArea stats;
    private int klicksCount = 0;
    private int multiplierCount = 1;
    private int klicksCountTotal = 0;

    // Sound Klasse aufrufen
    Sound sound = new Sound();

    public Window(){
        // Font für Text
        Font statFont = new Font("Arial", Font.PLAIN, 20);
        Font klicksFont = new Font("Arial", Font.BOLD, 36);

        // Window machen und so
        frame = new JFrame();
        frame.setTitle("Iron Cookie Clicker"); // Title vom Window
        frame.setSize(1200, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Background 
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("images/universe.jpg"));
        Image backgroundImage = backgroundIcon.getImage();

        // Custom JPanel for background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // JLabel machen für Klicks Text
        label = new JLabel();
        label.setText("Wilkommen bei IRON COOKIE KLICKER!");
        label.setFont(klicksFont);
        label.setBackground(Color.BLACK);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.RED);

        // Label für Stats 
        stats = new JTextArea(); // JTextArea, da mehrere New Lines
        stats.setEditable(false); 
        stats.setOpaque(false);    
        stats.setFocusable(false); 
        stats.setLineWrap(true);   
        stats.setWrapStyleWord(true); 
        stats.setFont(statFont);

        // Main Image machen
        ImageIcon imageIcon1 = new ImageIcon(getClass().getResource("images/ironCookie.PNG"));
        Image image1 = imageIcon1.getImage();
        Image resizedImage1 = image1.getScaledInstance(450, 350, Image.SCALE_SMOOTH);
        ImageIcon imageIcon2 = new ImageIcon(resizedImage1);
        // Button machen mit Main Bild drin
        JButton imageButton = new JButton(imageIcon2);
        imageButton.setBorderPainted(false);
        imageButton.setContentAreaFilled(false);
        imageButton.setFocusPainted(false);


        // Store Klasse aufrufen (Ref)
        Store store = new Store(this, sound);


        // Basically Override Func um zu schauen, ob Bild geklickt wurde
        imageButton.addActionListener(e -> {
            klicksCount = getKlicksCount() + 1 * getMultiplier(); 
            klicksCountTotal++;
            label.setText("Klicks: " + getKlicksCount()); 

            // Add Sound when Clicked
            sound.setFile(3);
            sound.playFile();
        });
        
        
        
        // Eigenes Panel für Labels
        JPanel labelPanel1 = new JPanel(new BorderLayout()); 
        labelPanel1.add(label, BorderLayout.CENTER);
        labelPanel1.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0)); 
        

        // Main Pic to adjust
        JPanel imageP = new JPanel(new BorderLayout());
        imageP.add(imageButton, BorderLayout.CENTER);
        imageP.setBorder(BorderFactory.createEmptyBorder(111, 0, 150, 0)); 
        

        // Stat Text to adjust
        JPanel statPanel = new JPanel(new BorderLayout()); 
        statPanel.add(stats, BorderLayout.WEST);
        statPanel.setBorder(BorderFactory.createEmptyBorder(150, 50, 0, 100));
        


        // Panel machen (hier alles einfügen)
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;  
        gbc.fill = GridBagConstraints.BOTH; 
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 0.5;
        gbc2.weighty = 0.5;  
        gbc2.fill = GridBagConstraints.BOTH;
        panel.add(labelPanel1, gbc2); // Add Main Klicks Label
        panel.add(store.getPanel1()); // Add Store Panel to (Frame) Panel 
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 1;
        gbc3.gridy = 1;
        gbc3.weightx = 0.5;
        gbc3.weighty = 0.5;  
        gbc3.fill = GridBagConstraints.BOTH;
        panel.add(statPanel, gbc3); // Add Stats Text to Panel

        // Nach Intro Audio Main Image adden
        Timer timer = new Timer(10000, e -> {
            panel.add(imageP, gbc); // Add Main Image
            label.setText("Klick bitte den Keks!");
            panel.revalidate();
            panel.repaint();
        });
        timer.start();
        timer.setRepeats(false);

        // Dem Frame das alles hinzufügen
        frame.setContentPane(backgroundPanel);
        frame.add(panel);
        frame.setVisible(true);

        // Timer: If Miner >= 1 add Klicks every 1 sec
        Timer timer2 = new Timer(1000, e -> {
                if(store.getMiners() >= 1){
                    klicksCount += store.getMiners();
                    updateCount();
                }
                stats.setText(
                  "Klicks Insgesamt: " + klicksCountTotal + "\n" +
                  "Klick Stärke: " + getMultiplier() + "\n" +
                  "Miners mining: " + store.getMiners());
        });
        timer2.start();
        timer2.setRepeats(true); // Sicherhaltshalber

        // (Intro) Sound spielen
        playIntroMusic(0);
        Timer introTimer = new Timer(16000, e -> {
            playIntroMusic(2);
            sound.loopFile();
        });
        introTimer.start();
        introTimer.setRepeats(false);
        
    } 

    public int getKlicksCount() { 
        return klicksCount;
    }
    public void setKlicksCount(int pKlicks){ 
        klicksCount = pKlicks;
    }
    public void updateCount(){ // Methode, um einfach den KlickCount zu updaten
        label.setText("Klicks: " + getKlicksCount());
    }
    public int getMultiplier(){
        return multiplierCount;
    }
    public void setMultiplier(int pMulti){ 
        multiplierCount = pMulti;
    }

    // Methoden zu Sound Steuerung
    public void playIntroMusic(int i){
        sound.setFile(i);
        sound.playFile();
    }

}
