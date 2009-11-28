package gui;

import filterData.DataContainer;
import filterData.FilterData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

import matrix.MatrixCreator;
import objectPos.Object;
import objectPos.Position;
import socket.ConnectionServer;

public class GuiFrame  extends JFrame {
	
	private final JButton stopTcp;
	private final JButton startTcp;
	
	private final int scaleZoom = 6;
	private final int shownMatrixWidth = 60;  //60
	private final int shownMatrixHeight = 50; //50
	private JPanel mapPanel;
	private ArrayList<ArrayList<Color>> matrix;
	private MatrixCreator mc;
	private int matrixScrollX;
	private int matrixScrollY;
	private Thread tcpServerThread;
	private Hashtable<String,Object> posObjectListe;
	private JPanel activeObjectTable;
	private JPanel activObjectsContainer;
	
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
		
		JMenu functions = new JMenu("functions");
		JMenuItem repaintMatrix = new JMenuItem("repaint map");
		repaintMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaintMatrixJPanel();
			}
		});
		functions.add(repaintMatrix);
		JMenuItem resetMatrix = new JMenuItem("reset Matrix");
		resetMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mc.resetMatrix();
				matrix = mc.getCreatedMatrix();
				repaintMatrixJPanel();
			}
			
		});
		functions.add(resetMatrix);
		menu.add(functions);
		
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
						 g.fillRect(i*scaleZoom, j*scaleZoom, scaleZoom, scaleZoom);
					}
				}
			}
		};
		
		
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
	    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
	    mapPanel.setPreferredSize(new Dimension(450,310));
		mapPanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		activObjectsContainer = new JPanel();
		activObjectsContainer.setBorder(BorderFactory.createTitledBorder(loweredbevel,"Aktive Objekte"));
		activeObjectTable = new JPanel();
		activeObjectTable.setPreferredSize(new Dimension(400,40));
		activObjectsContainer.add(activeObjectTable);
		
		
		JPanel southContainer = new JPanel();
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
	
	
	public void setAktiveObject(String key) {
		
		activeObjectTable.removeAll();
		for(Entry<String, Object> entry : posObjectListe.entrySet()) {
			Label object = new Label( entry.getKey() );
			if(key.equals(entry.getKey())) {
				object.setForeground(Color.green);
			} else {
				object.setForeground(Color.black);
			}
			activeObjectTable.add(object);
		}
		activeObjectTable.doLayout();
	}
	
	public void setCurrentObjectHashtable(Hashtable<String,Object> posList) {
		this.posObjectListe = posList;
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
	public void setMatrixObject(MatrixCreator mc) {
		this.mc = mc;
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
		int sleepThread = 1000;
		Hashtable<String,Object> objectList = new Hashtable<String,Object>();
		
		MatrixCreator mc = new MatrixCreator();
		mc.createMatrix();
		
		GuiFrame gui =  new GuiFrame();
		gui.setEmptyMatrix(mc.getCreatedMatrix());
		gui.setMatrixObject(mc);
		
		ConnectionServer cs = null;	
		cs = new ConnectionServer(sleepThread); 
		Thread tcpServerThread = new Thread(cs);
		gui.setTCPServerThread(tcpServerThread);
		
		FilterData filterData = new FilterData();
		Object object;
	 	DataContainer infos;
	 	
		while(true) {
			if(cs.getMessage() != null) {
				//System.out.println("getMessage "+cs.getMessage());
				infos = filterData.getParsedInfos();
				filterData.filterInputData(cs.getMessage());
				
				if(!objectList.isEmpty() && objectList.containsKey(infos.getName())) {
					object = objectList.get(infos.getName());		
					Position pos = new Position();
					pos.setX(infos.getPos()[0]);
					pos.setY(infos.getPos()[1]);
					object.setPosition(pos);
					System.out.println("if conatins main pos "+ pos.getX());
				}else {
					object = new Object();
					Position pos = new Position();
					pos.setX(infos.getPos()[0]);
					pos.setY(infos.getPos()[1]);
					object.setPosition(pos);
					int color = 255;
					for(Entry entry : objectList.entrySet()) {
						color-=50;
					}
					object.setColor(color);
					object.setName(infos.getName());
					objectList.put(infos.getName(), object);
				}
				
				
				gui.setCurrentObjectHashtable(objectList);
				gui.setAktiveObject(object.getName());
				mc.setNewPosition(object);
				mc.setScanData(infos.getData());
				gui.setUpdatedMatrix(mc.getCreatedMatrix());
				gui.repaintMatrixJPanel();
			}else { // in case nothing is recieving from clients
				gui.setCurrentObjectHashtable(objectList);
				gui.setAktiveObject(new String("nothing"));
			}
			Thread.sleep(sleepThread);
		}
				
	}
	
}
