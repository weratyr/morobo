package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

import map.Map;

public class GuiFrame  extends JFrame {
	
	private JPanel mapPanel;
	private ArrayList<ArrayList<Color>> matrix;
	
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
		setSize(400, 350);

		
		mapPanel = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				Map map = new Map();
				matrix = map.getMap();		
				int height = matrix.size();
				int width = matrix.get(0).size();
				for(int i=0; i<matrix.size(); i++) {
					for(int j=0; j<matrix.get(i).size(); j++) {
//						System.out.println(matrix.size() + "i ist " + i + " innere size "+ matrix.get(i).size()+" j ist "+j);
						 g.setColor(matrix.get(i).get(j));
						 g.fillRect(i, j, width, height);
					}
				}
			}
		};
		
		
	
		
		mapPanel.setBorder(BorderFactory.createLineBorder( Color.blue ));
		mapPanel.setPreferredSize(new Dimension(200,200));
	
		JPanel buttonContainer = new JPanel();
		buttonContainer.setLayout(new GridLayout(1,1));
		JButton startTcp = new JButton("start tcp");
		JButton stopTcp = new JButton("stop tcp");
		buttonContainer.add(startTcp);
		buttonContainer.add(stopTcp);
		
		Container cp = getContentPane();
		cp.add(new Label("Map"), BorderLayout.NORTH);
		cp.add(mapPanel, BorderLayout.CENTER);
		cp.add(buttonContainer, BorderLayout.SOUTH);
		
		
	}
	
}
