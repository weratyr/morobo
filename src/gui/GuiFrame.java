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
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import filterData.FilterData;

import socket.ConnectionServer;
import sun.tools.jstat.Alignment;

import map.Map;
import matrix.MatrixCreator;

public class GuiFrame  extends JFrame {
	
	private JPanel mapPanel;
	private ArrayList<ArrayList<Color>> matrix;
	private int scaleZoom = 4;
	private int matrixLeftSize = 20;
	private int matrixRightSize = 20;
	private MatrixCreator mc;
	
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
		setSize(600, 480);
		
		mapPanel = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				
				int height = matrix.size();
				int width = matrix.get(0).size();
				
				for(int i=0; i<matrixRightSize; i++) {
					for(int j=0; j<matrixLeftSize; j++) {
////						System.out.println(matrix.size() + "i ist " + i + " innere size "+ matrix.get(i).size()+" j ist "+j);
////						System.out.println("matrix farbe "+ matrix.get(i).get(j).getGreen());
						 g.setColor(matrix.get(i).get(j));
						 g.fillRect(i*scaleZoom, j*scaleZoom, width, height);
					}
				}
			}
		};
		
		
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
	    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		mapPanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		mapPanel.setSize(500, 400);
		JPanel activObjectsContainer = new JPanel();


		activObjectsContainer.setBorder(BorderFactory.createTitledBorder(loweredbevel,"Aktive Objekte"));
		activObjectsContainer.add(new Label("aktives Objekt in der Karte"));
		
		JPanel southContainer = new JPanel();
		southContainer.setLayout(new GridLayout(2,2));
		southContainer.add(activObjectsContainer);
		
		JPanel westContainer = new JPanel();
		westContainer.setLayout(new GridLayout(3,1));
		
		
		JPanel buttonContainer1 = new JPanel();
		buttonContainer1.setLayout(new BorderLayout());
		buttonContainer1.setPreferredSize(new Dimension(110,300));
		JButton scrollNorth = new JButton("^");
		JButton scrollLeft = new JButton("<");
		scrollLeft.setPreferredSize(new Dimension(23,40));
		JButton scrollRight = new JButton(">");
		scrollRight.setPreferredSize(new Dimension(23,40));
		JButton scrollSouth = new JButton("v");
		buttonContainer1.add(scrollNorth, BorderLayout.NORTH);
		buttonContainer1.add(scrollRight, BorderLayout.EAST);
		buttonContainer1.add(scrollLeft, BorderLayout.WEST);
		buttonContainer1.add(scrollSouth, BorderLayout.SOUTH);
		westContainer.add(buttonContainer1);

		
		JPanel buttonContainer = new JPanel();
		buttonContainer.setLayout(new BoxLayout(buttonContainer,1));
		JButton startTcp = new JButton("start tcp");
		JButton stopTcp = new JButton("stop tcp");
		buttonContainer.add(startTcp);
		buttonContainer.add(stopTcp);
		westContainer.add(buttonContainer);
		
		
		Container cp = getContentPane();
		cp.add(southContainer, BorderLayout.SOUTH);
		cp.add(mapPanel, BorderLayout.CENTER);
		cp.add(westContainer, BorderLayout.WEST);
		
	}
	

	public void repaintMatrixJPanel() {;
		mapPanel.repaint();
	}
	
	
	public void setUpdatedMatrix(ArrayList<ArrayList<Color>> matrix) {
		this.matrix = matrix;
	}
	
	public void setEmptyMatrix(ArrayList<ArrayList<Color>> matrix) {
		this.matrix = matrix;
	}
	
	
	
	
	
	
	
	
	public static void main(String[] arg) throws InterruptedException {
		
		
		MatrixCreator mc = new MatrixCreator();
		mc.createMatrix();
		
		GuiFrame gui =  new GuiFrame();
		gui.setEmptyMatrix(mc.getCreatedMatrix());
//		
//		ConnectionServer cs = null;
//		try {
//			cs = new ConnectionServer(); 
//			Thread t = new Thread(cs);
//			t.start();	
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		FilterData filterData = new FilterData();
//		ArrayList<Hashtable<String, int[]>> infos;
//		while(true) {
//			
//			filterData.setInput(cs.getMessage());
//			if(cs.getMessage() != null) {
//				System.out.println("getMessage "+cs.getMessage());
//				filterData.filterData();
//			}
//		    infos = filterData.getParsedInfos();
//		    
//			if(infos.size() > 0) {
//				 System.out.println("yeah size "+ infos.size()+" message "+cs.getMessage());
//				 mc.setPixelinMatrix(infos.get(0).get("pos")[0], infos.get(0).get("pos")[1], 0, 255, 0);
//				 gui.setUpdatedMatrix(mc.getCreatedMatrix());
//				 gui.repaintMatrixJPanel();
//				 synchronized (infos) { 
//						 infos.clear();
//				 }
//			}
//			
//			Thread.sleep(1000);
//		}
		
		
		
		
		
				
	}
	
}
