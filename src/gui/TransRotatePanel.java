package gui;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class TransRotatePanel extends JPanel {

	private Image image;
	private int currentAngle = 0;
	
	public TransRotatePanel() {
		this.image = new ImageIcon("src/gui/fadenkreuz1.gif").getImage();
	}
	
	public void rotate() {
		// rotate 5 degrees at a time
		currentAngle += 10.0;
		if (currentAngle >= 360.0) {
			currentAngle = 0;
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
//		g2d.drawImage(image, this.getWidth(), this.getHeight(), this);
		g2d.setTransform(origXform);
		g2d.setComposite(oldComp);
		
	}
}
