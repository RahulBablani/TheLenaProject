import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class FileExplorer 
extends JPanel
implements ActionListener {

	JButton openButton, saveButton;
	JFileChooser fc;
	JPanel buttonPanel;
	File file;
	JFrame importFile;

	public static String openFile, saveFile;


	//constructor creates all the contents for the window
	public FileExplorer() {

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

		//Put the buttons in a panel
		buttonPanel = new JPanel(); //use FlowLayout
		buttonPanel.add(openButton);
		buttonPanel.add(saveButton);

		//Add the buttons 
		add(buttonPanel, BorderLayout.PAGE_START);

	}
	
	//creates the actual window interface for file exploration
	public void createFileExplorer(){
		
		importFile = new JFrame("Import/Export Interface");
		importFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
		importFile.add(new FileExplorer());

        //Display the window.
		importFile.pack();
		importFile.setVisible(true);
	}
	
	//closes File Explorer window
	public void closeFileExplorer(){
		importFile.dispose();
	}

	//gets imported file's path
	public static String getOpenFile() {
		return openFile;
	}

	//gets exported file's path
	public static String getSaveFile() {
		return saveFile;
	}

	//logic behind importing an image
	public void openAction(){

		int returnVal = fc.showOpenDialog(FileExplorer.this);

		System.out.println("returnVal: " + returnVal);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			file = fc.getSelectedFile();
			System.out.println("\nOpening: " + file.getName());
			openFile = file.getAbsolutePath();
			System.out.println("\nPath : " + openFile);

		} else {
			System.out.println("\nOpen command cancelled by user.");
		}

	}

	//logic behind exporting an image
	public void saveAction(){

		int returnVal = fc.showSaveDialog(FileExplorer.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			System.out.println("\nSaving: " + file.getName() );
			saveFile = file.getAbsolutePath();
			System.out.println("\nPath : " + saveFile);
		} else {
			System.out.println("\nSave command cancelled by user.");
		}

	}

	//actions associated with button presses
	public void actionPerformed(ActionEvent e) {

		//Handle open button action.
		if (e.getSource() == openButton) {

			openAction();

			//Handle save button action.
		} else if (e.getSource() == saveButton) {

			saveAction();

		}
	}



}