package FrameViews;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainView extends JFrame implements ActionListener  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5204337033480381547L;
	ConfView cV = null; 
	JButton unzipButton, confButton, clearButton;
	JTextArea log;
	JFileChooser fc = new JFileChooser();
	JCheckBox checkBoxCanc;

      public void createMainView() {    
      fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
      JFrame frame = new JFrame();
      frame.setTitle("Unzip-S");
      ImageIcon imgIcon = Utils.createImageIcon(MainView.class,"images/archive.png");
      frame.setIconImage(imgIcon.getImage());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().setBackground(Utils.COLOR_AREA);
      
      unzipButton = new JButton("Unzip", Utils.createImageIcon(MainView.class,"images/archive.png"));
      unzipButton.addActionListener(this);
      unzipButton.setSize(50, 20);
      
      
      confButton = new JButton("Configuration", Utils.createImageIcon(MainView.class,"images/conf.png"));
      confButton.addActionListener(this);
      confButton.setSize(50, 20);
      
      clearButton = new JButton("Clear log", Utils.createImageIcon(MainView.class,"images/clear.png"));
      clearButton.addActionListener(this);
      clearButton.setSize(50, 20);
      
      //For layout purposes, put the buttons in a separate panel
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(Utils.COLOR_BACKGROUND);
      buttonPanel.add(unzipButton);
      buttonPanel.add(confButton);
      buttonPanel.add(clearButton);
      
      
      //Add the buttons and the log to this panel.
      frame.add(buttonPanel, BorderLayout.PAGE_START);
      
      checkBoxCanc = new JCheckBox("Cancel content folder before unzip");
      checkBoxCanc.addActionListener(this);
      checkBoxCanc.setBackground(Utils.COLOR_BACKGROUND);
      
      log = new JTextArea(10,30);
      log.setMargin(new Insets(5,5,5,5));
      log.setEditable(false);
      log.setBackground(Utils.COLOR_AREA);
      JScrollPane logScrollPane = new JScrollPane(log);
      
      frame.add(checkBoxCanc);
      frame.add(logScrollPane, BorderLayout.PAGE_END);
      
      frame.pack();
      frame.setVisible(true);
      }
      
      public void actionPerformed(ActionEvent e) {
    	  if (e.getSource() == unzipButton) {
    		  String outputDir = "";
    			
    		try {
				outputDir = Utils.readOutputDirFromConfFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			if(outputDir.isEmpty()){
				 log.append(Utils.getTimestamp() + " " +"Output directory path not configured "+ Utils.newline);
				 return;
			}
			
			
    		int returnVal = fc.showOpenDialog(MainView.this);
    		  
              if (returnVal == JFileChooser.APPROVE_OPTION) {
            	  try {
                  File dir = fc.getSelectedFile();
                 
				
					File file = new File(Utils.CONF_DIR_PATH+File.separator+Utils.CONF_FILE);
					
					if(!file.exists()){
						log.append(Utils.getTimestamp() + " " +"Configuration file doesn't exist "+ Utils.newline);
					}else{
					
						Utils.unzipFile(dir.getPath(),outputDir+ File.separator + dir.getName(), checkBoxCanc.isSelected());
						 log.append(Utils.getTimestamp() + " " +"Unzipped: " + dir.getName() + "." + Utils.newline);
					}
					log.setCaretPosition(log.getDocument().getLength()); 
              }catch (IOException e1) {
					e1.printStackTrace();
	              }
              }
              else {
	                  log.append(Utils.getTimestamp() + " " +"Open command cancelled by user." + Utils.newline);
	              }
    	  }
    	   else if (e.getSource() == confButton) {
    		   
    		   if(cV == null) {
    			   cV = new ConfView(); 
    			   try {
					cV.createConfView(log);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		   }else {
    			 //remove the previous JFrame
    			   cV.setVisible(false);
    			   cV.dispose();
                   //create a new one
    			   cV = new ConfView();
    			   try {
					cV.createConfView(log);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		   }
          }else  if (e.getSource() == clearButton) {
        	  log.setText("");
          }
          else  if (e.getSource() == checkBoxCanc) {
        	  if(checkBoxCanc.isSelected()) {
        		  log.append(Utils.getTimestamp() + " " +"ENABLED content folder cancellation" + "." + Utils.newline);
        	  }
        	  else {
        		  log.append(Utils.getTimestamp() + " " +"DISABLED content folder cancellation" + "." + Utils.newline);
        	  }
          }
      }
}