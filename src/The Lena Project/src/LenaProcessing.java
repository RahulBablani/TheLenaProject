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

	//constructor creates all contents for main GUI
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

	//loads image selected from fileExplorer interface using correct filepath
	public void imageLoader(String fileName){
		image = MarvinImageIO.loadImage(fileName);
		backupImage = image.clone();		
		imagePanel.setImage(image);
	}
	

	//creates Main GUI
	public void createMainGUI(){		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);	
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

			FileExplorer newExport = new FileExplorer();

			//opens up file explorer to save file
			newExport.saveAction();

			//Save image
			MarvinImageIO.saveImage(image,newExport.saveFile);

		}

	}
}