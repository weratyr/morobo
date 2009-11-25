import java.awt.Color;
import java.awt.Component;
import java.awt.Label;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;
import java.util.Dictionary ;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.GuiFrame;

	 public class LunchApp {
	 
	 public void test() {
		 JFrame f = new JFrame();
		 JPanel p = new JPanel();
		 for(int i=0; i< 4; i++) {
			 Label l = new Label("test");
			 if(i==2) {
				 l.setForeground(Color.blue);
			 }
			 p.add(l);
		 }
		 f.add(p);
		 f.show();
		 Component c[] = p.getComponents();
		 System.out.println();
	 }
	 

	public static void main(String[] arg) throws IOException {
		new LunchApp().test();
	}
}
