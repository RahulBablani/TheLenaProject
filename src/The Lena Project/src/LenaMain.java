import javax.swing.JFrame;

public class LenaMain {

	public static String fileName;
	static boolean check = true;

	
	//checks if user has selected a valid file to import
	private static void checkFileSelection( FileExplorer newImport){

		while (check==true){

			fileName = newImport.getOpenFile();
			System.out.print(fileName);

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

	public static void main(String[] args) {

		//create File Explorer window
		FileExplorer newImport = new FileExplorer();
		newImport.createFileExplorer();

		checkFileSelection(newImport);

	}

}
