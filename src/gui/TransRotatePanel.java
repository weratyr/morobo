package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

import javax.swing.*;

public class TransRotatePanel extends JPanel {

	private Image image;
	private double currentAngle = 0;
	private String imagePath = "src/gui/fadenkreuz1.gif";
	
	public TransRotatePanel() {
		File f = new File(imagePath);
		if(!f.exists()) {
			System.out.println("fadenkreuz nicht gefunden oder existiert nicht !!! ");
		}
		
		this.image = new ImageIcon(imagePath).getImage();
	}
	
	public void rotate() {
		// rotate 5 degrees at a time
		currentAngle += 10.0;
		if (currentAngle >= 360.0) {
			currentAngle = 0;
		}
		repaint();
	}
	
	public void rotate(int l, int r) {
		double deltaAlphaRad;
		double wheelWidth = 18;
		deltaAlphaRad = (l - r) / wheelWidth;
		System.out.println("before current Angle " + currentAngle);
		currentAngle += (deltaAlphaRad*180)/Math.PI;
		System.out.println("current Angle " + currentAngle);
		if (currentAngle > 360.0) {
			currentAngle -= 360;
		}
		repaint();
	}
	

	public void reset() {
		currentAngle = 0;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		Composite oldComp = g2d.getComposite();
		Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
		AffineTransform origXform = g2d.getTransform();
		AffineTransform newXform = (AffineTransform) (origXform.clone());
		// center of rotation is center of the panel
		int xRot = this.getWidth() / 2;
		int yRot = this.getHeight() / 2;
		newXform.rotate(Math.toRadians(currentAngle), xRot, yRot);
		g2d.setTransform(newXform);
		
		int x = (getWidth() - image.getWidth(this)) / 2;
		int y = (getHeight() - image.getHeight(this)) / 2;
		g2d.setComposite(alphaComp);
		g2d.drawImage(image, x, y, image.getWidth(this), image.getHeight(this), this);
		g2d.setTransform(origXform);
		g2d.setComposite(oldComp);
		
	}
}
