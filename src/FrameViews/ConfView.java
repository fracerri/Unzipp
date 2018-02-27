package FrameViews;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConfView extends JFrame implements ActionListener {
	//JTextField input;
	JTextArea inputArea;
	JButton enterButton, closeButton;
	 JFrame frame = new JFrame();
	 JTextArea log;
	 //private JLabel label;
	 //private static String labelString = "Output directory: ";
	
      /**
	 * 
	 */
	private static final long serialVersionUID = 7877521850005758440L;
	public void createConfView(JTextArea log) {   
      this.log = log;
      inputArea = new JTextArea(20,50);
      inputArea.setMargin(new Insets(5,5,5,5));
      inputArea.setEditable(true);
      try {
    		inputArea.append(Utils.getConfFileBody());
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
      JScrollPane logScrollPane = new JScrollPane(inputArea);
      
      frame.setTitle("Configuration");
      frame.setSize(300,300);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
       //input = new JTextField(15);
     //  input.addActionListener(this);
       //input.requestFocus(); 
       //label = new JLabel(labelString);
       //label.setLabelFor(input);
      
      //Button
      enterButton = new JButton("Enter", Utils.createImageIcon(ConfView.class,"images/enter.png"));
      enterButton.addActionListener(this);
      enterButton.setSize(50, 20);
      
      closeButton = new JButton("close", Utils.createImageIcon(ConfView.class,"images/close.png"));
      closeButton.addActionListener(this);
      closeButton.setSize(50, 20);
      
      //For layout purposes, put the buttons in a separate panel
      JPanel buttonPanel = new JPanel();
     // buttonPanel.add(label);
     // buttonPanel.add(input);
      
      buttonPanel.add(enterButton);
      buttonPanel.add(closeButton);
      
      //Add the buttons and the log to this panel.
      frame.add(logScrollPane, BorderLayout.CENTER);
      frame.add(buttonPanel, BorderLayout.PAGE_END);
      
      frame.pack();
      frame.setVisible(true);
      frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
      
   }
	
	
      public void actionPerformed(ActionEvent e) {
    	  if (e.getSource() == enterButton) {
    		  File dir = new File(Utils.CONF_DIR_PATH);
      		try {
  			if(!dir.exists()){
  				dir.mkdirs();
  			}
  			File file = new File(Utils.CONF_DIR_PATH+File.separator+Utils.CONF_FILE);
  			if(!file.exists()){
  				file.createNewFile();
  			}
  			
  			//String inputString = input.getText();	
  			//Utils.setOutputDirToConfFile(inputString);
  			Utils.updateConfFile(inputArea.getText());
  			log.append(Utils.getTimestamp() + " " + "Confuration file updated.");
  			//inputArea.setText("");
  			//inputArea.append(Utils.getConfFileBody());
  			//input.setText("");
  				} catch (IOException ex) {
  					ex.printStackTrace();
  				}
    	  }
    	  else if(e.getSource() == closeButton){
    		  frame.setVisible(false);
    	  }
    	 
			
}
      
}
      
      