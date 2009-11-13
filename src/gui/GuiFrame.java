package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.BorderFactory;
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
		setJMenuBar(menu);
		setSize(600, 450);

		
		mapPanel = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				Map map = new Map();
				matrix = map.getMap();		
				int height = getHeight();
				int width = getWidth();
				for(int i=0; i<matrix.size(); i++) {
					for(int j=0; j<matrix.get(i).size(); j++) {
						System.out.println(matrix.size() + "i ist " + i + " innere size "+ matrix.get(i).size()+" j ist "+j);
						 g.setColor(matrix.get(i).get(j));
						 g.fillRect(0, 0, width, height);
					}
				}
			}
		};
		
		
	
		
		mapPanel.setBorder(BorderFactory.createLineBorder( Color.blue ));
		mapPanel.add(new Label("test"));
		mapPanel.setPreferredSize(new Dimension(200,200));
	
		
		Container cp = getContentPane();
		cp.add(mapPanel, BorderLayout.EAST);
		
		
	}
	
}
