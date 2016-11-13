import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * @author Abeed
 * @date 11/10/2016
 * @brief this class is responsible for finding, opening, and saving files
 * @details this class will open a window in which you can search through your files
 * @todo Add exception test cases
 */

public class FileExplorer 
extends JPanel
implements ActionListener {

	JButton openButton, saveButton;
	JFileChooser fc;
	JPanel buttonPanel;
	File file;
	JFrame importFile;

	public static String openFile, saveFile;
	
	
/**
 * @brief constructor for the file explorer class
 * @details creates a window to ask whether you want to open or save a file
 * 			 makes sure only file types: png, jpg, jpeg, and gif are accepted
 * 
 */
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
/**
 * @brief creates window for file exploration
 * @details when either save or open is click the window for file exploration will open
 */
	public void createFileExplorer(){
		
		importFile = new JFrame("Import/Export Interface");
		importFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
		importFile.add(new FileExplorer());

        //Display the window.
		importFile.pack();
		importFile.setVisible(true);
	}
	
/**
 * @brief closes the file explorer window
 */
	public void closeFileExplorer(){
		importFile.dispose();
	}

/**
 * @brief gets the path of the imported file
 * @return openFile (path of opened file)
 */
	public static String getOpenFile() {
		return openFile;
	}

/**
 * @brief 	gets the path of the exported file
 * @return saveFile (path of saved file)
 */
	public static String getSaveFile() {
		return saveFile;
	}
	
/**
 * @brief Logic behind importing an image
 * @details collects name, extension, and directory and makes into absolute file path
 * 			environmental variable file: will take the value of a file path designated by which ever file the user clicks 
 */
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
	
/**
 * @brief logic behind exporting a file
 * @details collects name, extension, and directory and makes into absolute file path.
 * 			environmental variable file: will take the value of a file path designated by which ever file the user clicks 
 */
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

/**
 * @brief reads actions performed
 * @details reads which action is preformed in the UI (actions associated with button presses)
 * @param ActionEvent e, this variable is used to get the user input
 */
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