package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import socket.ConnectionServer;

import map.Map;

public class GuiFrame  extends JFrame {
	
	private JPanel mapPanel;
	private ArrayList<ArrayList<Color>> matrix;
	private int scaleZoom = 6;
	private int matrixLeftSize;
	private int matrixRightSize;
	
	public GuiFrame() {
		super();
		
		setVisible(true);
		setTitle("Spawn a map");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menu = new JMenuBar();

		JMenu file = new JMenu("file");
		JMenuItem open = new JMenuItem("open");
		file.add(open);
		menu.add(file);
		
		JMenu status = new JMenu("status");
		menu.add(status);
		
		setJMenuBar(menu);
		setSize(450, 480);
		
		Map map = new Map();
		matrix = map.getMap();	
		
		mapPanel = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				
				int height = matrix.size();
				int width = matrix.get(0).size();
				
				for(int i=0; i<matrixRightSize; i++) {
					for(int j=0; j<matrixLeftSize; j++) {
//						System.out.println(matrix.size() + "i ist " + i + " innere size "+ matrix.get(i).size()+" j ist "+j);
						 g.setColor(matrix.get(i).get(j));
						 g.fillRect(i*scaleZoom, j*scaleZoom, width, height);
					}
				}
			}
		};
		
		
	
		
		mapPanel.setBorder(BorderFactory.createLineBorder( Color.blue ));
	
		
		JPanel activObjectsContainer = new JPanel();
		activObjectsContainer.setBorder(BorderFactory.createLineBorder(Color.yellow));
		activObjectsContainer.add(new Label("aktives Objekt in der Karte"));
		
		JPanel southContainer = new JPanel();
		southContainer.setLayout(new GridLayout(2,2));
		southContainer.add(activObjectsContainer);
		
		JPanel buttonContainer = new JPanel();
		JButton startTcp = new JButton("start tcp");
		JButton stopTcp = new JButton("stop tcp");
		buttonContainer.add(startTcp);
		buttonContainer.add(stopTcp);
		southContainer.add(buttonContainer);
	
		
		JPanel westContainer = new JPanel();
		
		
		JPanel buttonContainer1 = new JPanel();
		buttonContainer1.setLayout(new GridLayout(2,1));
		JButton scrollLeft = new JButton("<");
		JButton scrollRight = new JButton(">");
		JButton scrollSouth = new JButton("v");
		JButton scrollNorth = new JButton("^");
		buttonContainer1.add(scrollNorth);
		buttonContainer1.add(scrollRight);
		buttonContainer1.add(scrollLeft);
		buttonContainer1.add(scrollSouth);
		westContainer.add(buttonContainer1);
		
		
		
		
		
		Container cp = getContentPane();
		cp.add(new Label("Map"), BorderLayout.NORTH);
		cp.add(southContainer, BorderLayout.SOUTH);
		cp.add(mapPanel, BorderLayout.CENTER);
		cp.add(westContainer, BorderLayout.WEST);
		
		
		
	}
	
	public static void main(String[] arg) {
		
		try {
			ConnectionServer cs = new ConnectionServer(); 
			cs.startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			
			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		new GuiFrame();		
	}
	
}
