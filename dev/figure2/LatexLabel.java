
package edu.ucsc.cross.hse.core.figure2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import net.sourceforge.jeuclid.swing.JMathComponent;

public class LatexLabel {

	private JMathComponent mathLabel;

	private RotatePanel rotator;

	public LatexLabel(String file_path, Integer size, double rotation) throws Exception {

		initialize(file_path, size);
		rotate(rotation);
	}

	private void initialize(String path, Integer size) throws Exception {

		mathLabel = new JMathComponent();
		Document doc = importFile(path);
		mathLabel.setDocument(doc);
		mathLabel.setFontSize(size);
		mathLabel.setBackground(new Color(0, 0, 0, 0));
		mathLabel.setOpaque(false);
	}

	private Document importFile(String path) throws Exception {

		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(is);
		return doc;
	}

	public JMathComponent getMathLabel() {

		return mathLabel;
	}

	public Component rotate(Double angle) {

		if (angle != null && angle != 0) {
			rotator = new RotatePanel(new Dimension(getAdjustedFontSize(), getAdjustedFontSize()), mathLabel);
			rotator.setRotationAngle(angle);
			rotator.add(BorderLayout.LINE_START, mathLabel);
		} else {
			if (rotator != null) {
				rotator.setRotationAngle(0);
			}
		}
		return getLabelComponent();
	}

	public Component getLabelComponent() {

		if (rotator != null) {
			return rotator;
		} else {
			return mathLabel;
		}
	}

	public int getAdjustedFontSize() {

		double percentage = .2;
		return (int) (mathLabel.getFontSize() + mathLabel.getFontSize() * percentage);
	}

}
