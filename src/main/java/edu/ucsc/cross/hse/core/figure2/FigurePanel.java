
package edu.ucsc.cross.hse.core.figure2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.figure.GraphicFormat;

public class FigurePanel {

	/**
	 * Default height and width of the
	 */
	protected static int defaultHeight = 600;

	/**
	 * Default title font
	 */
	protected static Font defaultTitleFont = new Font("SansSerif", Font.BOLD, 18);

	/**
	 * Default height and width of the figure
	 */
	protected static int defaultWidth = 800;

	/**
	 * The root parent panel that holds the inner container panel and any other
	 * border elements
	 */
	protected JPanel containerPanel;

	/**
	 * The panel that contains all of the components
	 */
	protected JPanel contentPanel;

	/**
	 * The frame of the window display
	 */
	protected JFrame displayFrame;

	/**
	 * The label for the title of the figure
	 */
	protected JLabel title;

	/**
	 * Construct a new figure with default dimensions
	 */
	public FigurePanel() {

		this(defaultWidth, defaultHeight);

	}

	/**
	 * Construct a new figure with default dimensions
	 */
	public FigurePanel(ChartPanel panel) {

		this(defaultWidth, defaultHeight);
		add(1, 1, panel);
	}

	/**
	 * Construct a new figure with the specified dimensions
	 * 
	 * @param width
	 *            pixel width of the figure pane
	 * @param height
	 *            height of the figure pane
	 * 
	 */
	public FigurePanel(int width, int height) {

		containerPanel = defaultPanel(true, width, height);
		contentPanel = defaultPanel(false, width, height);
		containerPanel.add(BorderLayout.CENTER, contentPanel);

	}

	/**
	 * Add a component to the figure with a pre-defined constraint
	 * 
	 * @param constraints
	 *            custom constraint that will determine the placement of the
	 *            component
	 * @param component
	 *            component to be displayed in the figure
	 */
	public void addBorderComponent(String location, Component component) {

		if (!location.equals(BorderLayout.CENTER)) {

			containerPanel.add(component, location);
		}
	}

	/**
	 * Add a component to the figure with a pre-defined constraint
	 * 
	 * @param constraints
	 *            custom constraint that will determine the placement of the
	 *            component
	 * @param component
	 *            component to be displayed in the figure
	 */
	public void setTop(Component component) {

		containerPanel.add(BorderLayout.PAGE_START, component);
	}

	/**
	 * Add a component to the figure with a pre-defined constraint
	 * 
	 * @param constraints
	 *            custom constraint that will determine the placement of the
	 *            component
	 * @param component
	 *            component to be displayed in the figure
	 */
	public void setBottom(Component component) {

		JPanel holder = defaultPanel(true, 100, 100);
		holder.setPreferredSize(null);
		holder.add(BorderLayout.CENTER, component);
		// holder.addComponentListener(new ComponentAdapter() {
		//
		// @Override
		// public void componentResized(ComponentEvent e) {
		//
		// double d = containerPanel.getSize().getWidth() -
		// contentPanel.getSize().getWidth();
		// System.out.println(d + " Chage! " + component.getBounds().getCenterX());//
		// .getAlignmentY());
		// double off = contentPanel.getBounds().getWidth() -
		// holder.getBounds().getWidth();
		// holder.setBounds((int) contentPanel.getBounds().getX(), (int)
		// holder.getBounds().getY(),
		// (int) contentPanel.getBounds().getBounds().getWidth(), (int)
		// holder.getBounds().getHeight());
		// // double off = contentPanel.getBounds().getCenterX() -
		// // component.getBounds().getCenterX();
		// // double adj = d / containerPanel.getSize().getWidth();
		// // Double dd = .5 + adj;
		// // double mid = containerPanel.getBounds().getX() +
		// // containerPanel.getBounds().getWidth() / 2;
		// // double off = mid - component.getBounds().getX() +
		// // component.getBounds().getWidth() / 2
		// // - component.getBounds().getX();
		// // component.setBounds(
		// // (int) (component.getBounds().getX() + holder.getBounds().getCenterX()
		// // - component.getBounds().getCenterX()),
		// // (int) component.getBounds().getY(), (int)
		// component.getBounds().getWidth(),
		// // (int) component.getBounds().getHeight());
		// // // holder.setAlignmentY((float) (dd.floatValue()));
		// // System.out.println(d + " Adj " + off + " " +
		// // component.getBounds().getCenterX());
		// // holder.repaint();
		// }
		// });
		holder.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentMoved(ComponentEvent e) {

				double d = containerPanel.getSize().getWidth() - contentPanel.getSize().getWidth();
				System.out.println(d + "  Chage!  " + component.getBounds().getCenterX());// .getAlignmentY());
				double off = contentPanel.getBounds().getWidth() - holder.getBounds().getWidth();
				if (Math.abs(off) >= 2) {
					holder.setPreferredSize(new Dimension((int) contentPanel.getBounds().getWidth(),
							(int) holder.getBounds().getHeight()));
					holder.setBounds((int) contentPanel.getBounds().getX(), (int) holder.getBounds().getY(),
							(int) contentPanel.getBounds().getWidth(), (int) holder.getBounds().getHeight());
				}
				// double off = contentPanel.getBounds().getCenterX() -
				// component.getBounds().getCenterX();
				// double adj = d / containerPanel.getSize().getWidth();
				// Double dd = .5 + adj;
				// double mid = containerPanel.getBounds().getX() +
				// containerPanel.getBounds().getWidth() / 2;
				// double off = mid - component.getBounds().getX() +
				// component.getBounds().getWidth() / 2
				// - component.getBounds().getX();
				// component.setBounds(
				// (int) (component.getBounds().getX() + holder.getBounds().getCenterX()
				// - component.getBounds().getCenterX()),
				// (int) component.getBounds().getY(), (int) component.getBounds().getWidth(),
				// (int) component.getBounds().getHeight());
				// // holder.setAlignmentY((float) (dd.floatValue()));
				// System.out.println(d + " Adj " + off + " " +
				// component.getBounds().getCenterX());
			}
		});
		containerPanel.add(BorderLayout.PAGE_END, holder);
		System.out.println(containerPanel.getSize().getWidth() + " AA" + component.getAlignmentY());

	}

	/**
	 * Add a component to the figure with a pre-defined constraint
	 * 
	 * @param constraints
	 *            custom constraint that will determine the placement of the
	 *            component
	 * @param component
	 *            component to be displayed in the figure
	 */
	public void setLeft(Component component) {

		containerPanel.add(BorderLayout.LINE_START, component);
	}

	/**
	 * Add a component to the figure with a pre-defined constraint
	 * 
	 * @param constraints
	 *            custom constraint that will determine the placement of the
	 *            component
	 * @param component
	 *            component to be displayed in the figure
	 */
	public void setRight(Component component) {

		containerPanel.add(BorderLayout.LINE_END, component);
	}

	/**
	 * Add a component to the figure with a generated constraint from the grid
	 * indices
	 * 
	 * @param x
	 *            column index of the component
	 * @param y
	 *            row index of the component
	 * @param component
	 *            component to be displayed in the figure
	 */
	public void add(int x, int y, Component component) {

		expandContentGridLayout(x, y);
		// GridBagConstraints con = new GridBagConstraints();
		// con.fill = GridBagConstraints.BOTH;
		// con.gridx = x - 1;
		// con.gridy = y - 1;
		GridLayout con = new GridLayout(x, y);
		contentPanel.add(component, con);

	}

	private void expandContentGridLayout(int x, int y) {

		GridLayout gl = (GridLayout) contentPanel.getLayout();
		if (gl.getColumns() < x) {
			gl.setColumns(x);
		}
		if (gl.getRows() < y) {
			gl.setRows(y);
		}
		contentPanel.setLayout(gl);
	}

	/**
	 * Generates the default c panel
	 * 
	 * @param width
	 *            specified panel width
	 * @param height
	 *            specified panel height
	 * @return pane generated figure panel
	 */
	private JPanel defaultPanel(boolean main_panel, int x, int y) {

		JPanel pane = new JPanel(new GridLayout(1, 1), false);
		if (main_panel) {
			pane = new JPanel(new BorderLayout(), false);

		}
		pane.setPreferredSize(new Dimension(x, y));
		pane.setBackground(new Color(0, 0, 0, 0));
		pane.setOpaque(false);
		return pane;
	}

	private JFrame prepareJFrame(int x, int y) {

		displayFrame = new JFrame("Figure");
		displayFrame.setContentPane(containerPanel);
		displayFrame.setPreferredSize(new Dimension(x, y));
		displayFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		displayFrame.getContentPane().setPreferredSize(new Dimension(x, y));
		displayFrame.setPreferredSize(new Dimension(x, y));
		return displayFrame;
	}

	/**
	 * Creates the figure in new window that can be displayed or hidden
	 * 
	 * 
	 * @return frame the newly displayed J frames
	 */
	public JFrame display() {

		return display((int) containerPanel.getPreferredSize().getWidth(),
				(int) containerPanel.getPreferredSize().getHeight());
	}

	/**
	 * Creates the figure in new window that can be displayed or hidden
	 * 
	 * @param x
	 *            the width of the image to be generated
	 * 
	 * @param y
	 *            the height of the image to be generated
	 * @return frame the newly displayed J frames
	 */
	public JFrame display(int x, int y) {

		displayFrame = prepareJFrame(x, y);
		displayFrame.pack();
		displayFrame.setVisible(true);
		return displayFrame;
	}

	/**
	 * Exports the figure to an output file in a specified image form
	 * 
	 * @param location
	 *            location where the image file is to be created
	 * @param format
	 *            image format for file to be exported to
	 * 
	 * @return true if export was a success, false otherwise
	 * 
	 */
	public boolean exportToFile(File location, GraphicFormat format) {

		display();
		// boolean success = FigureExporter.exportToFile(location, this, format);
		displayFrame.dispatchEvent(new WindowEvent(displayFrame, WindowEvent.WINDOW_CLOSING));
		return false;// success;
	}

	/**
	 * Exports the figure to an output file in a specified image form
	 * 
	 * @param location
	 *            location where the image file is to be created
	 * @param format
	 *            image format for file to be exported to
	 * 
	 * @param x
	 *            the width of the image to be generated
	 * @param y
	 *            the height of the image to be generated
	 * 
	 * @return true if export was successful, false otherwise
	 */
	public boolean exportToFile(File location, GraphicFormat format, int x, int y) {

		contentPanel.setPreferredSize(new Dimension(x, y));
		return exportToFile(location, format);
	}

	/**
	 * Get the main panel that contains the components
	 * 
	 * @return panel the main panel
	 */
	public JPanel getContentPanel() {

		return containerPanel;
	}

	/**
	 * Get the figure frame
	 * 
	 * @return figure frame
	 */
	public JFrame getFrame() {

		return displayFrame;
	}

	/**
	 * Get the title label of the entire figure. This label is displayed at the top
	 * of the figure, or hidden if there is no title. The title is hidden by setting
	 * the text to null
	 * 
	 * @return the title label of the figure
	 */
	public JLabel getTitle() {

		return title;
	}

	/**
	 * Creates a positioned grid bag constraint with specified x and y locations
	 * 
	 * @param x
	 *            horizontal position index in a grid
	 * @param y
	 *            vertical position index in a grid
	 * 
	 * @return grid bag constraint with specified x and y locations
	 */
	public static GridBagConstraints createGridBagConstraints(int x, int y) {

		GridBagConstraints dc = new GridBagConstraints();
		dc.gridx = x;
		dc.gridy = y;
		dc.fill = GridBagConstraints.BOTH;
		dc.weightx = .5;
		dc.weighty = .5;
		return dc;
	}

	/**
	 * Create a sub panel that can hold chart panels and be added to a figure
	 * 
	 * @param layout
	 *            layout format of the sub panel
	 * @return prepared sub panel
	 */
	public static JPanel createSubPanel(LayoutManager layout) {

		JPanel panel = new JPanel(layout, false);
		panel.setOpaque(false);
		panel.setBackground(null);
		return panel;
	}
}
