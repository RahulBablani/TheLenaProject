import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.image.MarvinImageMask;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;

import java.util.Scanner;  



public class Lena extends JFrame implements ActionListener
{
	private MarvinImagePanel 	imagePanel;
	private MarvinImage 		image, 
	backupImage;

	private JPanel 				panelFilter;

	private JButton 			buttonGray,
	buttonEdgeDetector, 
	buttonInvert, 
	buttonReset,
	buttonSave;

	private MarvinImagePlugin 	imagePlugin;

	public static String		fileName; 


	public Lena()
	{
		super("Lena");

		// Create Graphical Interface
		buttonGray = new JButton("Gray");
		buttonGray.addActionListener(this);
		buttonEdgeDetector = new JButton("EdgeDetector");
		buttonEdgeDetector.addActionListener(this);
		buttonInvert = new JButton("Invert");
		buttonInvert.addActionListener(this);
		buttonReset = new JButton("Reset");
		buttonReset.addActionListener(this);
		buttonSave = new JButton("Save");
		buttonSave.addActionListener(this);

		panelFilter = new JPanel();
		panelFilter.add(buttonGray);
		panelFilter.add(buttonEdgeDetector);
		panelFilter.add(buttonInvert);
		panelFilter.add(buttonReset);
		panelFilter.add(buttonSave);


		// ImagePanel
		imagePanel = new MarvinImagePanel();


		Container mainWindow = getContentPane();
		mainWindow.setLayout(new BorderLayout());
		mainWindow.add(panelFilter, BorderLayout.SOUTH);
		mainWindow.add(imagePanel, BorderLayout.NORTH);

		// Load image
		image = MarvinImageIO.loadImage(fileName);
		backupImage = image.clone();		
		imagePanel.setImage(image);

		setSize(340,430);
		setVisible(true);	

	}

	public static void main(String args[]){

		Scanner userInput = new Scanner(System.in); 
		String extension = "png";
		String path = "/Users/nezardimitri/Desktop";
		String file = "Lena";

		System.out.println("Please fill in appropriate information about image you wish to process.");
		System.out.println("File extension (png/jpg): ");
		extension = userInput.nextLine();
		System.out.println("File path (EX: /Users/nezardimitri/Desktop): ");
		path = userInput.nextLine();
		System.out.println("File name (EX: Lena): ");
		file = userInput.nextLine();

		fileName = path + "/" + file + "." + extension ;
		
		System.out.println(file + "." + extension + " uploaded successful as " + fileName);
		
		Lena target = new Lena();

		target.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent e){

		if(e.getSource() == buttonReset) {
			image = backupImage.clone();
		}

		else if(e.getSource() == buttonGray){
			imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.grayScale.jar");
			imagePlugin.process(image, image, null, MarvinImageMask.NULL_MASK, false);
		}

		else if(e.getSource() == buttonEdgeDetector){
			imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.edge.edgeDetector.jar");
			imagePlugin.process(image, image, null, MarvinImageMask.NULL_MASK, false);
		}

		else if(e.getSource() == buttonInvert){
			imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.invert.jar");
			imagePlugin.process(image, image, null, MarvinImageMask.NULL_MASK, false);
		}

		image.update();
		imagePanel.setImage(image);



		if(e.getSource() == buttonSave){
			
			Scanner userInput = new Scanner(System.in); 
			String extension = "png";
			String path = "/Users/nezardimitri/Desktop";
			String file = "LenaProcessed";

			System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("\nPlease fill in appropriate information about image you wish to save.");
			System.out.println("File extension (png/jpg): ");
			extension = userInput.nextLine();
			System.out.println("File path (EX: /Users/nezardimitri/Desktop): ");
			path = userInput.nextLine();
			System.out.println("File name (EX: LenaProcessed): ");
			file = userInput.nextLine();

			fileName = path + "/" + file + "." + extension ;
			
			//Save image
			MarvinImageIO.saveImage(image,fileName);
			
			System.out.println(file + "." + extension + " saved successful as " + fileName);
			
			//Save image
//			MarvinImageIO.saveImage(image,"/Users/nezardimitri/Desktop/image.png");
//			MarvinImageIO.saveImage(backupImage,"/Users/nezardimitri/Desktop/backupImage.png");
//			System.out.println("SAVE SUCCESS!!");
		}

	}
}