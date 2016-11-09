import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.JFrame;

public class LenaMain {

	public static String		fileName;
	public static JFrame importFile;

	public static void main(String[] args) {

		//		Scanner userInput = new Scanner(System.in); 
		//		String extension = "png";
		//		String path = "/Users/nezardimitri/Desktop";
		//		String file = "Lena";
		//
		//		System.out.println("Please fill in appropriate information about image you wish to process.");
		//		System.out.println("File extension (png/jpg): ");
		//		extension = userInput.nextLine();
		//		System.out.println("File path (EX: /Users/nezardimitri/Desktop): ");
		//		path = userInput.nextLine();
		//		System.out.println("File name (EX: Lena): ");
		//		file = userInput.nextLine();
		//
		//		fileName = path + "/" + file + "." + extension ;
		//		
		//		System.out.println(file + "." + extension + " uploaded successful as " + fileName);

		//FileChoose.createAndShowGUI();
		
		importFile = new JFrame("Import/Export Interface");
		importFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
		importFile.add(new FileChoose());

        //Display the window.
		importFile.pack();
		importFile.setVisible(true);
		
		fileName = FileChoose.openFile;
		
		
        

		//fileName = "/Users/nezardimitri/Desktop/5star.png";

		boolean check = true;

		while (check==true){
			fileName = FileChoose.openFile;
			System.out.println(fileName);
			if(fileName!= null){
				System.out.println("file chosen: " + fileName);
				LenaProcessing target = new LenaProcessing(fileName);
				target.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				check = false;
				importFile.dispose();
				//importFile.setVisible(false);
				//importFile.dispatchEvent(new WindowEvent(importFile, WindowEvent.WINDOW_CLOSING));



			}
		}

	}

}
