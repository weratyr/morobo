package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawWholeMatrix extends JFrame {
	private JPanel mainWindow;
	private ArrayList<ArrayList<Color>> matrix;
	private final int scaleZoom = 1;
	
	public DrawWholeMatrix(ArrayList<ArrayList<Color>> matrix) {
		super();
		this.matrix = matrix;
		if(matrix.size() < 0) {
			System.out.println("matrix zu klein");
			return;
		}
		paintMatrixWindow();
		add(mainWindow);
		setLayout(new GridLayout());
		setSize(new Dimension(1000, 1200));
		setVisible(true);
		//pack();
		
	}
	
	public void paintMatrixWindow () {
		mainWindow = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				
				for (int i = 0; i < matrix.size(); i++) {
					for (int j = 0; j < matrix.get(0).size(); j++) {
						g.setColor(matrix.get(i).get(j));
						g.fillRect(i * scaleZoom, j * scaleZoom, scaleZoom, scaleZoom);
					}
				}
			}
		};
	}

}
