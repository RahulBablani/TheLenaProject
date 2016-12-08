import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * @author Nezar
 * @date 12/7/2016
 * @brief this class is responsible for finding, opening, and saving files
 * @details this class will open a window in which you can search through your files
 */

public class FileExplorer 
extends JPanel
implements ActionListener {

	JButton openButton, saveButton;
	JFileChooser fc;
	JPanel buttonPanel;
	File file;
	JFrame importFile;

	public static String openFile, openFileExtension, saveFile;


	/**
	 * @brief constructor for the file explorer class
	 * @details creates a window to ask whether you want to open or save a file
	 * 			 makes sure only file types: png, jpg, jpeg, gif, and bmp are accepted
	 * 
	 */
	public FileExplorer() {

		super(new BorderLayout());

		//Create a file chooser and only filter on image files to prevent errors
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg" , "bmp");
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
	 * @brief determines the state of the file extension
	 * @details used for error handling mainly of output files
	 * 			determines if no file extension was included, if there is a invalid file extension, or if it valid
	 * 			State Variable: String extension, depending on the state of the file extension.
	 * @param String fileName, can either be openFile/saveFile
	 * @return integer : returns 0 if not a valid file extension (requires further attention)
						returns 1 if valid extension
						return 2 if no extension included (grab extension from "openFile")
	 */
	private int getFileExtension(String fileName){

		String extension = "n/a";

		if (fileName.contains(".")){

			extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			System.out.println("~~~extension: " + extension);

			if (extension.equals("png") || extension.equals("gif") 
					|| extension.equals("bmp") || extension.equals("jpeg") 
					|| extension.equals("jpg")){

				//update openFileExtension just in case it is needed later
				openFileExtension = extension;
				System.out.println("~~~openFileExtension: " + openFileExtension);

				// valid extension, no further logic needed
				return 1;

			}

			//invalid file extension, further logic will take place in saveAction
			else { 
				return 0; 
			}

		}

		//no file extension included, grab from "openFile" add to "saveFile"
		else {
			return 2; 
		}

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

			getFileExtension(openFile);

		} else {
			System.out.println("\nOpen command cancelled by user.");
		}

	}


	/**
	 * @brief logic behind exporting a file
	 * @details collects name, extension, and directory and makes into absolute file path.
	 * 			also deals with error handling:
	 * 			linked with getFileExtension method...
	 * 			 displays a pop-up for invalid file extensions
	 *			auto completes the file extension if none are provided;
	 * 			also implements a file overwrite interface in which the user is prompted if they are about to overwrite a file
	 * 			environmental variable: file, will take the value of a file path designated by which ever file the user clicks 
	 */
	public void saveAction(){

		int returnVal = fc.showSaveDialog(FileExplorer.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();
			saveFile = file.getAbsolutePath();



			System.out.println("EXTENSION: " + getFileExtension(saveFile));

			//if not a valid file extension
			while(getFileExtension(saveFile)==0){

				saveFile = null;
				String message = "Error: Invalid file extension! Please try again.";
				JOptionPane.showMessageDialog(null, message , "Error", JOptionPane.ERROR_MESSAGE);

			}

			//if no file extension at all
			if(getFileExtension(saveFile)==2){
				saveFile = saveFile + "." + openFileExtension;
				System.out.println("FIXED EXTENSION: " + saveFile);
			}

			File savedFile = new File(saveFile);


			if (savedFile.exists()){

				int result = JOptionPane.showConfirmDialog(null,
						"This file already exist?\nAre you sure you want to replace it?", "Overwrite Existing File", JOptionPane.YES_NO_OPTION);

				System.out.println("@@@Result: "+ result);

				//yes=0,no=1,cancel=-1

				//yes, overwrite existing file 
				if(result==0){
					System.out.println("\nSaving: " + savedFile.getName() );
					saveFile = savedFile.getAbsolutePath();
					System.out.println("\nPath : " + saveFile);
				}

				//no, do not overwrite --> back to file explorer
				else if( result==1 || result==-1) {
					saveFile=null;
				}	

			}




		} else {
			System.out.println("\nSave command cancelled by user.");
		}

	}

	/**
	 * @brief reads actions performed
	 * @details reads which action is performed in the UI (actions associated with button presses)
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