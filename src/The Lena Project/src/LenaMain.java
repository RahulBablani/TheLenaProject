import javax.swing.JFrame;

/**
 * @author Nezar
 * @date 11/10/2016
 * @brief this is the main class
 * @details this class will instantiate the file explore and the GUI
 * 
 */

public class LenaMain {

	public static String fileName;
	static boolean check = true;

/**
 * @brief checks if a file has been selected
 * @details it checks if the file path is not null.
 * 			If not null it will upload the file and open the GUI
 * 
 * @param newImport (Checks the file path that newImport has uploaded)
 */
	private static void checkFileSelection( FileExplorer newImport){

		while (check==true){

			fileName = newImport.getOpenFile();
			System.out.print("");

			if(fileName!= null){

				System.out.println("\nfile chosen: " + fileName);

				LenaProcessing edittingWindow = new LenaProcessing();
				edittingWindow.imageLoader(fileName);
				edittingWindow.createMainGUI();
				
				check = false;

				newImport.closeFileExplorer();

			}
		}


	}

/**
 * @brief Main
 * @details makes a file explorer and uses it as a param or file check
 * @param args
 */
	public static void main(String[] args) {

		//create File Explorer window
		FileExplorer newImport = new FileExplorer();
		newImport.createFileExplorer();

		checkFileSelection(newImport);

	}

}
