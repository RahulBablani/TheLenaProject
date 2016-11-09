import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class FileChoose extends JPanel
                        implements ActionListener {
	
    JButton openButton, saveButton;
    JFileChooser fc;
    JPanel buttonPanel;
    File file;
    
    public static String openFile;
    public static String saveFile;


    public FileChoose() {
    	
        super(new BorderLayout());

        //Create a file chooser and only filter on image files to prevent errors
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
        fc = new JFileChooser();
        fc.setFileFilter(filter);

        //Create the open button.
        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);

        //Create the save button.  
        saveButton = new JButton("Save a File...");
        saveButton.addActionListener(this);

        //For layout purposes, put the buttons in a separate panel
        buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        //Add the buttons 
        add(buttonPanel, BorderLayout.PAGE_START);
       
    }
    
    public String getSaveFile(){
    	return saveFile;
    }

    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(FileChoose.this);
            System.out.println("returnVal: " + returnVal);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                 file = fc.getSelectedFile();
                //This is where a real application would open the file.
                System.out.println("Opening: " + file.getName() + "\n");
                openFile = file.getAbsolutePath();
                System.out.println("Path : " + openFile);
                

            } else {
                System.out.println("Open command cancelled by user.\n");
            }
            //log.setCaretPosition(log.getDocument().getLength());

        //Handle save button action.
        } else if (e.getSource() == saveButton) {
            int returnVal = fc.showSaveDialog(FileChoose.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would save the file.
                System.out.println("Saving: " + file.getName() + "\n");
                saveFile = file.getAbsolutePath();
                System.out.println("Path : " + saveFile);
            } else {
                System.out.println("Save command cancelled by user.\n");
            }
            //log.setCaretPosition(log.getDocument().getLength());
        }
    }


//    public static void createAndShowGUI() {
//        //Create and set up the window.
//        JFrame fileSelection = new JFrame("Import/Export Interface");
//        fileSelection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        //Add content to the window.
//        fileSelection.add(new FileChoose());
//
//        //Display the window.
//        fileSelection.pack();
//        fileSelection.setVisible(true);
//    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
               // UIManager.put("swing.boldMetal", Boolean.FALSE); 
            	JFrame fileSelection = new JFrame("Import/Export Interface");
                fileSelection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //Add content to the window.
                fileSelection.add(new FileChoose());

                //Display the window.
                fileSelection.pack();
                fileSelection.setVisible(true);
        		
        		
            }
        });
    }
}
