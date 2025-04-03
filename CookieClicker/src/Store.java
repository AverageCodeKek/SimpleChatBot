

import java.awt.*;
import javax.swing.*;

public class Store {
    private JPanel panelM;
    private int priceMultiplier;
    private int passiveMiners;

    public Store(Window win, Sound sound){ // Get Window Reference
        Window winR = win;
        Sound audio = sound;
        priceMultiplier = 1;
        passiveMiners = 0;
        Font font = new Font("Arial", Font.PLAIN, 30); 

        //Init Icon x2Upgrade
        ImageIcon storeIcon = new ImageIcon(getClass().getResource("images/x2Clicks.PNG")); 
        Image image = storeIcon.getImage();
        Image resizedImage = image.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        ImageIcon storeIcon2x = new ImageIcon(resizedImage);

        //Init Icon Miner Upgrade
        ImageIcon minerIcon = new ImageIcon(getClass().getResource("images/freeMiner.PNG"));
        Image im = minerIcon.getImage();
        Image resIm = im.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        ImageIcon storeMiner = new ImageIcon(resIm);

        // Text "Store"
        JLabel storeLabel = new JLabel();
        storeLabel.setText("Store:");
        storeLabel.setFont(font);
        storeLabel.setBackground(Color.BLACK);
        storeLabel.setHorizontalAlignment(JLabel.RIGHT);

        // Text Price von Miner Upgrade
        JLabel priceMiner = new JLabel();
        priceMiner.setText("Price: " + 100 + " Klicks");
        priceMiner.setFont(font);
        priceMiner.setBackground(Color.BLACK);
        priceMiner.setHorizontalAlignment(JLabel.RIGHT);

        // Text Price von x2Click Upgrade
        JLabel pricex2Label = new JLabel();
        pricex2Label.setText("Price: " + 50 * priceMultiplier + " Klicks");
        pricex2Label.setFont(font);
        pricex2Label.setBackground(Color.BLACK);
        pricex2Label.setHorizontalAlignment(JLabel.RIGHT);

        // Button machen mit x2Clicks Upgrade drin
        JButton imageButton1 = new JButton(storeIcon2x);
        imageButton1.setBorderPainted(false); 
        imageButton1.setContentAreaFilled(false); 
        imageButton1.setFocusPainted(false); 

        // Button machen mit Miner Upgrade
        JButton imageButton2 = new JButton(storeMiner);
        imageButton2.setBorderPainted(false); 
        imageButton2.setContentAreaFilled(false); 
        imageButton2.setFocusPainted(false);
        
        // If clicked x2Store Image check Click count und update Counter
        imageButton1.addActionListener(e -> { // Override Func
            if(winR.getKlicksCount() >= 50 * priceMultiplier){
                winR.setKlicksCount(winR.getKlicksCount() - 50 * priceMultiplier);
                winR.setMultiplier(winR.getMultiplier() + 1);
                priceMultiplier += 4;
                winR.updateCount();
                pricex2Label.setText("Price: " + 50 * priceMultiplier + " Klicks");

                // Play Sound
                audio.setFile(1);
                audio.playFile();
            }
        });

        // If clicked Miner Store Upgrade -> passive +1 Klick
        imageButton2.addActionListener(e -> {
            if(winR.getKlicksCount() >= 100 + getMiners() * 10){
                winR.setKlicksCount(winR.getKlicksCount() - (100 + getMiners() * 3));
                passiveMiners++;
                winR.updateCount();
                priceMiner.setText("Price: " + (100 + getMiners() * 10) + " Klicks");

                // Play Sound
                audio.setFile(1);
                audio.playFile();
            }
        });

        // Add x2Klick Upgrade Image to panel to adjust
        JPanel storePan1 = new JPanel(new BorderLayout()); 
        storePan1.add(imageButton1, BorderLayout.CENTER);
        storePan1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); 

        // Add x2Klick Upgrade Text to panel to adjust
        JPanel x2KlickJPanel = new JPanel(new BorderLayout()); 
        x2KlickJPanel.add(pricex2Label, BorderLayout.CENTER);
        x2KlickJPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 10)); 

        // Add Miner Upgrade Image to panel to adjust
        JPanel storePan2 = new JPanel(new BorderLayout());
        storePan2.add(imageButton2, BorderLayout.CENTER);
        storePan2.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); 

        // Add Miner Price Text to panel to adjust
        JPanel minerJPanel= new JPanel(new BorderLayout()); 
        minerJPanel.add(priceMiner, BorderLayout.CENTER);
        minerJPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 10)); 

        // Add Store Text to panel to adjust
        JPanel labelPanel2 = new JPanel(new BorderLayout());
        labelPanel2.add(storeLabel, BorderLayout.CENTER);
        labelPanel2.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 160)); 


        //Main Panel
        panelM  = new JPanel();
        panelM.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.5;  
        gbc.weighty = 0.5;  
        gbc.fill = GridBagConstraints.BOTH;
        panelM.add(storePan1, gbc); // Add Store Text

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        gbc2.weightx = 0.5;  
        gbc2.weighty = 0.5;  
        gbc2.fill = GridBagConstraints.BOTH;
        panelM.add(labelPanel2, gbc2); // Add x2Klicks Image Button

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 2;
        gbc3.gridy = 2;
        gbc3.weightx = 0.5;  
        gbc3.weighty = 0.5;  
        gbc3.fill = GridBagConstraints.BOTH;
        panelM.add(x2KlickJPanel, gbc3); // Add Price from x2Klicks Text

        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.gridx = 2;
        gbc4.gridy = 3;
        gbc4.weightx = 0.5;  
        gbc4.weighty = 0.5;  
        gbc4.fill = GridBagConstraints.BOTH;
        panelM.add(imageButton2, gbc4); // Add Miner Upgrade Image Button

        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.gridx = 2;
        gbc5.gridy = 4;
        gbc5.weightx = 0.5;  
        gbc5.weighty = 1.0;  
        gbc5.fill = GridBagConstraints.BOTH;
        panelM.add(minerJPanel, gbc5); // Add Miner Price Text

    }

    public JPanel getPanel1(){ // Return Main Panel to Window Class
        return panelM;
    }
    public int getPriceMulti(){
        return priceMultiplier;
    }
    public int getMiners(){
        return passiveMiners;
    }
}
