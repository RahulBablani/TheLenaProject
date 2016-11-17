import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.image.MarvinImageMask;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;

import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Scanner;  

/**
 * @author Nezar
 * @date 11/10/2016
 * @brief this class is responsible for the processing of images
 * @details once an image has been uploaded this class will open a window displaying said image
 * 			different buttons will be created to allow for different filters
 * @ add tests for gif
 */

public class LenaProcessing 
extends JFrame 
implements ActionListener {


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

/**
 * @brief constructor for the LenaProcessing class
 * @details creates all contents (buttons and display) for main GUI
 */
	public LenaProcessing()
	{
		super("LenaProcessing");

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

	}
	
/**
 * @brief loads image
 * @details loads image selected from fileExplorer interface using correct file path.
 * 			State Variable: image, after every action image is updated and then re displayed.
 * @param fileName (the full path of the file being uploaded)
 */
	public void imageLoader(String fileName){
		image = MarvinImageIO.loadImage(fileName);
		backupImage = image.clone();		
		imagePanel.setImage(image);
	}
	
/**
 * @brief displays GUI
 * @details once all the panels and buttons are updated this method
 * 			automatically resizes the window and makes it visible
 */
	//creates Main GUI
	public void createMainGUI(){		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);	
	}

	/**
	 * @brief reads user input
	 * @details if a filter is clicked the method will apply the filer to the image
	 * 			if reset is clicked image will be displayed in its original form
	 * 			if save is clicked the image will be saved.
	 * 			Environmental Variable: saveFile, File path coming from the file explorer
	 * @param ActionEvent e, This object is used to get the user input
	 */
	public void actionPerformed(ActionEvent e){
		long start = System.currentTimeMillis(); // start calculating for RESPONSE_TIME
		
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
		
		long time = System.currentTimeMillis() - start; // end calculating for RESPONSE_TIME
		System.out.println("RESPONSE_TIME = " + time + "ms");



		if(e.getSource() == buttonSave){

			FileExplorer newExport = new FileExplorer();

			//opens up file explorer to save file
			newExport.saveAction();

			//Save image
			MarvinImageIO.saveImage(image,newExport.saveFile);

		}

	}
}