package gui;

import filterData.DataContainer;
import filterData.FilterData;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

import matrix.MatrixCreator;
import objectPos.Object;
import objectPos.Position;
import socket.ConnectionServer;

public class GuiFrame extends JFrame {

	private final int scaleZoom = 2;
	private final int shownMatrixWidth = 900; // 60
	private final int shownMatrixHeight = 900; // 50
	private JPanel mapPanel;
	private ArrayList<ArrayList<Color>> matrix;
	private MatrixCreator mc;
	private int matrixScrollX;
	private int matrixScrollY;
	private Hashtable<String, Object> posObjectListe;
	private JPanel activeObjectTable;
	private JPanel activObjectsContainer;
	private final TransRotatePanel transparentRotateJP;
	private JMenuBar menu;

	public GuiFrame() {
		super();
		matrixScrollX = 0;
		matrixScrollY = 0;
		
		setVisible(true);
		setTitle("Spawn a map");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(generateMenu());

		mapPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				
				for (int i = 0; i < shownMatrixWidth; i++) {
					for (int j = 0; j < shownMatrixHeight; j++) {
						g.setColor(matrix.get(i + matrixScrollX).get(j + matrixScrollY));
						g.fillRect(i * scaleZoom, j * scaleZoom, scaleZoom, scaleZoom);
					}
				}
			}
		};

		GridBagConstraints mapCenterConstraint = new GridBagConstraints();

		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		mapPanel.setPreferredSize(new Dimension(700,550));
		mapPanel.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		mapCenterConstraint.fill = GridBagConstraints.CENTER;
		transparentRotateJP = new TransRotatePanel();
		transparentRotateJP.setPreferredSize(new Dimension(900,800));
		transparentRotateJP.setLayout(new GridBagLayout());
		transparentRotateJP.add(mapPanel,mapCenterConstraint);
		
		activObjectsContainer = new JPanel();
		activObjectsContainer.setBorder(BorderFactory.createTitledBorder(loweredbevel, "Aktive Objekte"));
		activeObjectTable = new JPanel();
		activObjectsContainer.add(activeObjectTable);

		JPanel southContainer = new JPanel();
		southContainer.setLayout( new BorderLayout());
		southContainer.setSize(new Dimension(350,80));
		southContainer.setPreferredSize(new Dimension(350,80));
		southContainer.add(activObjectsContainer, BorderLayout.CENTER);

		JPanel westContainer = new JPanel();
		westContainer.setLayout(new BoxLayout(westContainer, 1));
		westContainer.setPreferredSize(new Dimension(130,250));

		JPanel buttonContainer1 = new JPanel();
		buttonContainer1.setLayout(new BorderLayout());
		buttonContainer1.setPreferredSize(new Dimension(130, 100));
		JButton scrollNorth = new JButton("^");
		scrollNorth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (matrixScrollY != 0) {
					matrixScrollY -= 200;
					mapPanel.repaint();
				}
			}
		});
		JButton scrollLeft = new JButton("<");
		scrollLeft.setPreferredSize(new Dimension(23, 40));
		scrollLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (matrixScrollX != 0) {
					matrixScrollX -= 200;
					mapPanel.repaint();
				}
			}

		});
		JButton scrollRight = new JButton(">");
		scrollRight.setPreferredSize(new Dimension(23, 40));
		scrollRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (matrixScrollX  < (matrix.size() - matrixScrollX)) {
					matrixScrollX += 200;
					mapPanel.repaint();
				}
			}

		});
		JButton scrollSouth = new JButton("v");
		scrollSouth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (matrixScrollY < (matrix.size() - matrixScrollY)) {
					matrixScrollY += 200;
					mapPanel.repaint();
				}
			}

		});
		buttonContainer1.add(scrollNorth, BorderLayout.NORTH);
		buttonContainer1.add(scrollRight, BorderLayout.EAST);
		buttonContainer1.add(scrollLeft, BorderLayout.WEST);
		buttonContainer1.add(scrollSouth, BorderLayout.SOUTH);
		//westContainer.add(buttonContainer1);

		JPanel buttonContainer = new JPanel();
		buttonContainer.setLayout(new BoxLayout(buttonContainer, 1));		
		JButton rotate = new JButton("rotate 10 degree");
		rotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transparentRotateJP.rotate();
			}

		});
		westContainer.add(buttonContainer);
		southContainer.add(scrollSouth, BorderLayout.NORTH);
		setLayout(new BorderLayout());
		Container cp = getContentPane();
		cp.add(scrollRight, BorderLayout.EAST);
		cp.add(transparentRotateJP, BorderLayout.CENTER);
		//cp.add(westContainer, BorderLayout.WEST);
		cp.add(scrollLeft, BorderLayout.WEST);
		cp.add(scrollNorth, BorderLayout.NORTH);
		cp.add(southContainer, BorderLayout.SOUTH);
		pack();
	}
	
	public void updateTransparentRotateJP(int[] direction) {
		transparentRotateJP.rotate(direction[0], direction[1]);
		System.out.println("rotate"+direction[0]);
	}
	
	
	public JMenuBar generateMenu() {
		menu = new JMenuBar();
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
				posObjectListe.clear();
				repaintMatrixJPanel();
				activeObjectTable.repaint();
			}

		});
		functions.add(resetMatrix);
		JMenuItem resetRotatedImage = new JMenuItem("reset direction cross");
		resetRotatedImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transparentRotateJP.reset();
			}

		});
		functions.add(resetRotatedImage);
		
		JMenuItem paintMatrixWindow = new JMenuItem("paint screenshot");
		paintMatrixWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DrawWholeMatrix(matrix);
			}

		});
		functions.add(paintMatrixWindow);
		menu.add(functions);
		return menu;
	}
	
	
	public void setAktiveObject(String key) {
		activeObjectTable.removeAll();
		for (Entry<String, Object> entry : posObjectListe.entrySet()) {
			Label object = new Label(entry.getKey());
			if (key.equals(entry.getKey())) {
				object.setForeground(Color.green);
			} else {
				object.setForeground(Color.black);
			}
			activeObjectTable.add(object);
		}
		activeObjectTable.doLayout();
	}

	public void setCurrentObjectHashtable(Hashtable<String, Object> posList) {
		this.posObjectListe = posList;
	}

	public void repaintMatrixJPanel() {
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
	
	

	public static void main(String[] arg) throws InterruptedException {
		ConnectionServer cs = null;
		int port = 5678;
		cs = new ConnectionServer(port);
		Thread tcpServerThread = new Thread(cs);
		tcpServerThread.start();
		
	}

}
