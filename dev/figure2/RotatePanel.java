
package edu.ucsc.cross.hse.core.figure2;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class RotatePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3098911082405774790L;

	private Dimension dim;

	private Image image;

	private double theta;

	private Component mainComponent;

	private void initialize() {

		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
	}

	private void addResizeListener() {

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {

				if (!getSize().equals(dim)) {
					dim = getSize();
					int maximum = (int) Math.min(dim.getHeight(), dim.getWidth());
					dim = new Dimension(maximum, maximum);

					System.out.println(mainComponent.getSize().getWidth() + " resized : h " + dim.getHeight() + " w "
							+ dim.getWidth());

					image = getImage(maximum, maximum);
					repaint();
				} else {
					System.out.println(
							"NOTHING " + mainComponent.getSize().getWidth() + " resized : h " + dim.getHeight());
				}
			}
		});
	}

	public void setRotationAngle(double rotation) {

		theta = rotation;
	}

	public RotatePanel(Dimension dimm, Component main) {

		super();
		mainComponent = main;
		this.dim = dimm;
		initialize();
		image = getImage(dim.getWidth(), dim.getHeight());
		addResizeListener();
		this.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
		image = getImage(dim.getWidth(), dim.getHeight());
		repaint();
	}

	static public Image getImage(double w, double h) {

		BufferedImage bi = new BufferedImage((int) w, (int) h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.dispose();
		return bi;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
		g2d.rotate(theta);
		g2d.translate(-image.getWidth(this) / 2, -image.getHeight(this) / 2);
		g2d.drawImage(image, 0, 0, null);
	}

}
