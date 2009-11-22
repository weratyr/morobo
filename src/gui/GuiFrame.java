package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	private final JButton stopTcp;
	private final JButton startTcp;
	
	private final int scaleZoom = 6;
	private final int shownMatrixWidth = 60;
	private final int shownMatrixHeight = 50;
	private JPanel mapPanel;
	private ArrayList<ArrayList<Color>> matrix;
	private MatrixCreator mc;
	private int matrixScrollX;
	private int matrixScrollY;
	private Thread tcpServerThread;
	
	public GuiFrame() {
		super();
		matrixScrollX = 0;
		matrixScrollY = 0;
		
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
		setSize(550, 400);
		
		
		mapPanel = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				
				for(int i=0; i<shownMatrixWidth; i++) {
					for(int j=0; j<shownMatrixHeight; j++) {
//						System.out.println(matrix.size() + "i ist " + i + " innere size "+ matrix.get(i).size()+" j ist "+j);
//						System.out.println("matrix farbe "+ matrix.get(i).get(j).getGreen());
						 g.setColor(matrix.get(i+matrixScrollX).get(j+matrixScrollY));
						 g.fillRect(i*scaleZoom, j*scaleZoom, 4, 4);
					}
				}
			}
		};
		
		
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
	    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	    mapPanel.setPreferredSize(new Dimension(450,300));
		mapPanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
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
		buttonContainer1.setPreferredSize(new Dimension(130,300));
		JButton scrollNorth = new JButton("^");
		scrollNorth.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(matrixScrollY != 0) {
					matrixScrollY-=10;
					mapPanel.repaint();
				}
			}
			
		});
		JButton scrollLeft = new JButton("<");
		scrollLeft.setPreferredSize(new Dimension(23,40));
		scrollLeft.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(matrixScrollX != 0) {
					matrixScrollX-=10;
					mapPanel.repaint();
				}
			}
			
		});
		JButton scrollRight = new JButton(">");
		scrollRight.setPreferredSize(new Dimension(23,40));
		scrollRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(matrixScrollX < matrix.size()) {
					matrixScrollX+=10;
					mapPanel.repaint();
				}
			}
			
		});
		JButton scrollSouth = new JButton("v");
		scrollSouth.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(matrixScrollX < matrix.size()) {
					matrixScrollY+=10;
					mapPanel.repaint();
				}
			}
			
		});
		buttonContainer1.add(scrollNorth, BorderLayout.NORTH);
		buttonContainer1.add(scrollRight, BorderLayout.EAST);
		buttonContainer1.add(scrollLeft, BorderLayout.WEST);
		buttonContainer1.add(scrollSouth, BorderLayout.SOUTH);
		westContainer.add(buttonContainer1);

		
		JPanel buttonContainer = new JPanel();
		buttonContainer.setLayout(new BoxLayout(buttonContainer,1));
		startTcp = new JButton("start tcp");
		startTcp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				startTCPServer();
				startTcp.setEnabled(false);
				stopTcp.setEnabled(true);
				stopTcp.setText("stop (suspend)");
			}
			
		});
		stopTcp = new JButton("stop tcp");
		stopTcp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				stopTCPServer();
				stopTcp.setEnabled(false);
				startTcp.setEnabled(true);
				startTcp.setText("start (resume)");
			}
			
		});
		buttonContainer.add(startTcp);
		buttonContainer.add(stopTcp);
		westContainer.add(buttonContainer);
		westContainer.doLayout();
		
		Container cp = getContentPane();
		cp.add(new JLabel(), BorderLayout.NORTH);
		cp.add(southContainer, BorderLayout.SOUTH);
		cp.add(mapPanel, BorderLayout.CENTER);
		cp.add(westContainer, BorderLayout.WEST);
		cp.doLayout();
		//pack();
		
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
	
	public void startTCPServer() {
		if(tcpServerThread.isAlive()){
			tcpServerThread.resume();
		}else {
			tcpServerThread.start();
		}
	}
	
	public void stopTCPServer() {
		tcpServerThread.suspend();
	}
	
	public void setTCPServerThread(Thread tcpServerThread) {
		this.tcpServerThread = tcpServerThread;
	}
	

	public static void main(String[] arg) throws InterruptedException {
		
		
		MatrixCreator mc = new MatrixCreator();
		mc.createMatrix();
		
		GuiFrame gui =  new GuiFrame();
		gui.setEmptyMatrix(mc.getCreatedMatrix());
		
		ConnectionServer cs = null;
		cs = new ConnectionServer(); 
		Thread tcpServerThread = new Thread(cs);
		gui.setTCPServerThread(tcpServerThread);
		
		FilterData filterData = new FilterData();
		ArrayList<Hashtable<String, int[]>> infos;
		while(true) {
			
			filterData.setInput(cs.getMessage());
			if(cs.getMessage() != null) {
				System.out.println("getMessage "+cs.getMessage());
				filterData.filterData();
			}
		    infos = filterData.getParsedInfos();
		    
			if(infos.size() > 0) {
				 System.out.println("yeah size "+ infos.size()+" message "+cs.getMessage());
				 mc.setPixelinMatrix(infos.get(0).get("pos")[0], infos.get(0).get("pos")[1], 0, 255, 0);
				 gui.setUpdatedMatrix(mc.getCreatedMatrix());
				 gui.repaintMatrixJPanel();
				 synchronized (infos) { 
						 infos.clear();
				 }
			}
			
			Thread.sleep(1000);
		}
		
		
		
		
		
				
	}
	
}
