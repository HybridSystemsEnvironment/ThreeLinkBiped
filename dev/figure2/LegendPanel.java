
package edu.ucsc.cross.hse.core.figure2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.Size2D;

public class LegendPanel extends JPanel {

	private LegendTitle legend;

	private Image image;

	private ArrayList<JFreeChart> chartsDisplayed;

	public LegendPanel() {

		super(new BorderLayout(), false);
		setBackground(new Color(0, 0, 0, 0));
		image = new BufferedImage(70, 100, BufferedImage.TYPE_INT_ARGB);

		setOpaque(false);

		this.legend = new LegendTitle(null);// ChartUtils.createPanel(new TrajectorySet(), "",
		this.legend.setSources(new LegendItemSource[] {});
		// "").getChart().getLegend();
		chartsDisplayed = new ArrayList<JFreeChart>();
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {

				populateLegend();
				Dimension dim = getSize();

				image = RotatePanel.getImage(dim.getWidth(), dim.getHeight());
				repaint();// recalculate variable
			}
		});
		Dimension dim = getSize();
		if (dim.getHeight() == 0) {
			dim = new Dimension(100, 30);
		}
		image = RotatePanel.getImage(dim.getWidth(), dim.getHeight());// , BufferedImage.TYPE_INT_ARGB);

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Dimension dim = getSize();
		if (dim.getHeight() == 0) {
			dim = new Dimension(100, 30);
		}
		Graphics2D g2d = (Graphics2D) g;
		Size2D legSize = legend.arrange(g2d);// , new RectangleConstraint(dim.getWidth(), dim.getHeight()));
		System.out.println(dim.getHeight() + " " + dim.getWidth());
		System.out.println(legSize.getHeight() + " " + legSize.getWidth());

		legend.draw(g2d,
				new Rectangle((int) ((getSize().getWidth() / 2) - (legSize.getWidth() / 2)),
						(int) ((dim.getHeight() / 2) - (legSize.getHeight() / 2)), (int) legSize.getWidth(),
						(int) legSize.getHeight()));
		g2d.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
	}

	private void populateLegend() {

		ArrayList<LegendItemSource> sourceList = new ArrayList<LegendItemSource>();
		legend.setSources(new LegendItemSource[] {});
		for (JFreeChart chart : chartsDisplayed) {
			boolean vis = chart.getLegend().isVisible();
			chart.getLegend().setVisible(true);
			for (LegendItemSource source : chart.getLegend().getSources()) {
				if (!sourceList.contains(source)) {
					sourceList.add(source);
				}
			}
			chart.getLegend().setVisible(vis);

		}
		legend.setSources(sourceList.toArray(new LegendItemSource[sourceList.size()]));
	}

	public void addChart(JFreeChart... chart) {

		chartsDisplayed.addAll(Arrays.asList(chart));
		populateLegend();
	}
}
