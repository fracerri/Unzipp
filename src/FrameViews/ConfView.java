package FrameViews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConfView extends JFrame implements ActionListener {
	JTextField input;
	JTextArea inputArea;
	JButton closeButton,addFolder;
	 JFrame frame = new JFrame();
	 JTextArea log;
	 private JLabel label;
	 private static String labelString = "Output directory: ";
	 JFileChooser fc;
	 private final Color COLOR_CUSTOM = new Color(20,95,158);
	
      /**
	 * 
	 */
	private static final long serialVersionUID = 7877521850005758440L;
	public void createConfView(JTextArea log) throws IOException {   
		
	//Create file chooser
	 fc = new JFileChooser();
	 fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
      this.log = log;
      inputArea = new JTextArea(20,60);
      inputArea.setMargin(new Insets(10,10,10,10));
      inputArea.setEditable(false);
      try {
    		inputArea.append(Utils.getConfFileBody());
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
      JScrollPane logScrollPane = new JScrollPane(inputArea);
      
      frame.setTitle("Configuration");
      frame.setSize(300,300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      ImageIcon imgIcon = Utils.createImageIcon(MainView.class,"images/conf.png");
      frame.setIconImage(imgIcon.getImage());
      frame.getContentPane().setBackground(Color.black);
      
      input = new JTextField(30);
      input.disable();
      input.setText(Utils.readOutputDirFromConfFile());
      label = new JLabel(labelString);
      label.setLabelFor(input);
      addFolder = new JButton("Update output folder", Utils.createImageIcon(MainView.class,"images/folder.png"));
      addFolder.addActionListener(this);
      addFolder.setSize(10, 10);
      
      JPanel inputPanel = new JPanel();
      inputPanel.add(label);
      inputPanel.add(input);
      inputPanel.add(addFolder);
      
     
      
      //Button
      closeButton = new JButton("close", Utils.createImageIcon(ConfView.class,"images/close.png"));
      closeButton.addActionListener(this);
      closeButton.setSize(50, 20);
      
      
      //For layout purposes, put the buttons in a separate panel
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(closeButton);
      
      inputPanel.setBackground(COLOR_CUSTOM);
      buttonPanel.setBackground(COLOR_CUSTOM);
     
      frame.add(inputPanel, BorderLayout.PAGE_START);
      frame.add(logScrollPane);
      frame.add(buttonPanel, BorderLayout.PAGE_END);
      
      frame.pack();
      frame.setVisible(true);
      frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
      
   }
	
	
      public void actionPerformed(ActionEvent e) {
    
    	   if(e.getSource() == closeButton){
    		  frame.setVisible(false);
    	  }
    	  else if (e.getSource() == addFolder) {
      		int returnVal = fc.showOpenDialog(ConfView.this);
  		  
            if (returnVal == JFileChooser.APPROVE_OPTION) {
          
                File dir = fc.getSelectedFile();
                try {
                	input.setText(dir.getPath());
					Utils.updateConfFile(dir.getPath());
					inputArea.setText("");
					inputArea.append(Utils.getConfFileBody());
				} catch (IOException e1) {
					log.append(Utils.getTimestamp() + " " +"Error during configuration file update. "+ Utils.newline);
				}
               
	  			log.append(Utils.getTimestamp() + " " + "Confuration file updated.");
            }
			
    	  }
}
      
}
      
      