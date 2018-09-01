/* ===========================================================
 * HSE : The Hybrid Systems Environment
 * ===========================================================
 *
 * MIT License
 * 
 * Copyright (c) 2018 HybridSystemsEnvironment
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.

 *
 * ------------------------------------------------
 * ChartUtils.java
 * ------------------------------------------------
 *
 * Original Author:  Brendan Short
 * Contributor(s):   
 *
 * Changes:
 * --------
 * 01-June-2018 : Version 1 (BS);
 *
 */

package edu.ucsc.cross.hse.core.figure2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.ucsc.cross.hse.core.chart.ChartType;
import edu.ucsc.cross.hse.core.chart.HybridContentRenderer;
import edu.ucsc.cross.hse.core.chart.RendererConfiguration;
import edu.ucsc.cross.hse.core.monitors.DataCollector;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.HybridTrajectoryElement;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * This set of utilities provides a few services that generate and update chart
 * components. It also generates a XYDataset that can be used to generate many
 * other displays besides the ones provided here.
 *
 * Intended Operator: User
 */
public class ChartUtils {

	/**
	 * Flag to include variable name in the series label - allows each element to be
	 * have custom color and stroke
	 */
	public static boolean includeVarNameInLabel = false;

	/**
	 * Configures the all of the main labels at once. Any label set to null will be
	 * not be displayed on the chart.
	 * 
	 * @param panel
	 *            chart panel that contains the chart to be configured
	 * @param x_axis_label
	 *            label to display at the x axis of the chart
	 * @param y_axis_label
	 *            label to display at the y axis of the chart
	 * @param show_legend
	 *            flag indicating if legend will be displayed
	 * @param overall_title
	 *            overall title displayed at top
	 */
	public static void configureLabels(ChartPanel panel, String x_axis_label, String y_axis_label, String overall_title,
			boolean show_legend) {

		panel.getChart().getXYPlot().getDomainAxis().setLabel(x_axis_label);
		panel.getChart().getXYPlot().getRangeAxis().setLabel(y_axis_label);
		panel.getChart().getLegend().setVisible(show_legend);
		panel.getChart().setTitle(overall_title);
	}

	/**
	 * Creates a combined domain xy plot that will be used to house an arrangement
	 * of other charts.
	 * 
	 * @param trajectories
	 *            hybrid trajectory set to gather the data from
	 * 
	 * @param rendering_config
	 *            the properties of the custom hybrid data renderer that allows the
	 *            colors and strokes to be adjusted and assigned to specific
	 *            elements
	 * @param subplots
	 *            sub plots to be added into the combined plot;
	 * @return the combined plot
	 */
	public static CombinedDomainXYPlot createCombinedDomainXYChart(TrajectorySet trajectories,
			RendererConfiguration rendering_config, JFreeChart... subplots) {

		RendererConfiguration renderingConfig = rendering_config;
		if (renderingConfig == null) {
			renderingConfig = new RendererConfiguration();
		}
		CombinedDomainXYPlot combo = new CombinedDomainXYPlot();
		combo.setRenderer(new HybridContentRenderer(ChartType.LINE, trajectories, renderingConfig,
				createXYDataset(trajectories, HybridTime.TIME, "")));
		for (JFreeChart subplot : subplots) {
			combo.add(subplot.getXYPlot());
		}
		return combo;
	}

	/**
	 * Creates a chart panel to house a JFreeChart. This panel is needed to disable
	 * buffering so the image vectoring is produced correctly.
	 * 
	 * @param plot
	 *            the plot to be loaded into the panel
	 * @return chart panel containing specified plot
	 */
	public static ChartPanel createPanel(JFreeChart plot) {

		ChartPanel panel = new ChartPanel(plot, false);
		panel.setMinimumDrawHeight(150);
		panel.setMinimumDrawWidth(150);
		panel.setMaximumDrawHeight(999999);
		panel.setMaximumDrawWidth(999999);
		panel.setBackground(null);
		panel.setOpaque(false);
		return panel;
	}

	/**
	 * Creates a data set, chart, and panel all at once. Some of the inputs of this
	 * method can be null to use the defaults.
	 *
	 * @param trajectories
	 *            set containing a set of trajectories that can be included in the
	 *            plot
	 * @param domain_axis_handle
	 *            that is the name of a variable with trajectory data would be
	 *            plotted on the x axis. There are predefined labels for time and
	 *            jumps since they are often used as the domain, but another
	 *            variable can be selected as well
	 * @param range_axis_handle
	 *            that is the name of a variable whose trajectory data would be
	 *            plotted on the y axis
	 * 
	 * 
	 * @return chart panel loaded with the generated chart and data set
	 */
	public static ChartPanel createPanel(TrajectorySet trajectories, String domain_axis_handle,
			String range_axis_handle) {

		return createPanel(trajectories, domain_axis_handle, range_axis_handle, null, null, null);
	}

	/**
	 * Creates a data set, chart, and panel all at once. Some of the inputs of this
	 * method can be null to use the defaults.
	 *
	 * @param trajectories
	 *            set containing a set of trajectories that can be included in the
	 *            plot
	 * @param domain_axis_handle
	 *            that is the name of a variable with trajectory data would be
	 *            plotted on the x axis. There are predefined labels for time and
	 *            jumps since they are often used as the domain, but another
	 *            variable can be selected as well
	 * @param range_axis_handle
	 *            that is the name of a variable whose trajectory data would be
	 *            plotted on the y axis
	 * @param type
	 *            type of plot to be generated (null for default)
	 * @param rendering_config
	 *            the rendering configuration specifying the color and stroke
	 *            characteristics of the rendered data (null for default)
	 * @param chart
	 *            an existing chart so that they can be customized before any data
	 *            gets loaded (null for default)
	 * 
	 * @return chart panel loaded with the generated chart and data set
	 */
	public static ChartPanel createPanel(TrajectorySet trajectories, String domain_axis_handle,
			String range_axis_handle, ChartType type, RendererConfiguration rendering_config, JFreeChart chart) {

		XYDataset dataz = createXYDataset(trajectories, domain_axis_handle, range_axis_handle);
		JFreeChart chartz = createXYChart(trajectories, dataz, chart, rendering_config, type);
		ChartPanel panel = createPanel(chartz);
		return panel;
	}

	/**
	 * Creates an XY chart contained in a JFreeChart container.
	 * 
	 * @param trajectories
	 *            hybrid trajectory set to gather the data from
	 * @param data_set
	 *            data set that will be used to generate the chart
	 * @param chart_layout
	 *            a possible pre-formatted chart to be used, otherwise a null
	 *            results in a new one being generated
	 * @param rendering_config
	 *            the properties of the custom hybrid data renderer that allows the
	 *            colors and strokes to be adjusted and assigned to specific
	 *            elements
	 * @param type
	 *            type of plot to be generated
	 * @return the generated chart
	 */
	public static JFreeChart createXYChart(TrajectorySet trajectories, XYDataset data_set, JFreeChart chart_layout,
			RendererConfiguration rendering_config, ChartType type) {

		JFreeChart chart = null;

		RendererConfiguration renderingConfig = rendering_config;
		if (renderingConfig == null) {
			renderingConfig = new RendererConfiguration();
		}

		HybridContentRenderer rend = new HybridContentRenderer(type, trajectories, renderingConfig, data_set);
		if (chart == null) {
			chart = ChartFactory.createXYLineChart(null, null, null, data_set, PlotOrientation.VERTICAL, true, true,
					true);/// , rend));
		}
		if (chart_layout != null) {
			chart.getLegend().setVisible(chart_layout.getLegend().isVisible());
		}
		try {
			int itemCount = data_set.getItemCount(0);
			chart.getXYPlot().setDataset(data_set);
			if (data_set.getXValue(0, itemCount - 1) == trajectories.getFinalTime().getTime()) {
				chart.getXYPlot().getDomainAxis().setRange(trajectories.getInitialTime().getTime(),
						trajectories.getFinalTime().getTime());
			}
		} catch (Exception noData) {
			noData.printStackTrace();
		}
		chart.getXYPlot().setRenderer(rend);
		chart.getXYPlot().setBackgroundPaint(null);
		chart.getPlot().setBackgroundPaint(null);
		chart.setBackgroundPaint(null);

		return chart;
	}

	/**
	 * Creates an XYDataset using the data from specified elements of trajectories
	 * within the trajectory set that match the selected variable names.
	 * 
	 * @param data_set
	 *            hybrid trajectory set to gather the data from
	 * @param x_field_selection
	 *            variable name of the x (domain) elements to be selected. It could
	 *            also be one of the domain handles indicating the domain of the
	 *            plot is time or jumps
	 * @param y_field_selection
	 *            variable name of the y (range) elements to be selected. Less
	 *            likely than with the x selection,It could also be one of the
	 *            domain handles indicating the range of the plot is time or jumps
	 * @return the file data set
	 */
	public static XYDataset createXYDataset(TrajectorySet data_set, String x_field_selection,
			String y_field_selection) {

		ArrayList<String> names = new ArrayList<String>();
		XYSeriesCollection dataset = new XYSeriesCollection();
		HashMap<String, XYSeries> ser = new HashMap<String, XYSeries>();
		if (y_field_selection != null) {
			for (HybridTrajectoryElement<?> data : DataCollector.getAllDataSeries(data_set)) {
				boolean matchesSelection = true;
				HybridTrajectoryElement<?> xS = null;
				if (!y_field_selection.equals(data.getChild().getName())) {
					matchesSelection = false;
				}
				if (x_field_selection != HybridTime.TIME && x_field_selection != HybridTime.JUMP) {
					xS = data.getSeriesWithSameParent(x_field_selection, DataCollector.getAllDataSeries(data_set));
					if (xS == null) {
						matchesSelection = false;
					}
				}
				matchesSelection = matchesSelection && (data.getAllStoredData().get(0).getClass().equals(Double.class)
						|| data.getAllStoredData().get(0).getClass().equals(double.class));
				if (matchesSelection) {
					String label = getSeriesName(data);
					names.add(label);
					XYSeries s1 = new XYSeries(label, false, true);
					s1.setDescription(getSeriesName(data));
					for (HybridTime ind : data_set.getTimeDomain()) {
						Double y = (Double) data.getStoredData(ind);
						Double x = ind.getTime();
						if (x_field_selection.equals(HybridTime.JUMP)) {
							x = (double) ind.getJumps();
							System.out.println(x);
						}
						if (xS != null) {
							x = (Double) xS.getStoredData(ind);
						}
						if (x != null && y != null) {
							XYDataItem dat = new XYDataItem(x, y);
							s1.add(dat);
						}
					}
					ser.put(s1.getKey().toString(), s1);

				}
			}
			ArrayList<String> serName = new ArrayList<String>(ser.keySet());
			Collections.sort(serName);
			for (String se : serName) {
				dataset.addSeries(ser.get(se));
			}
		}
		return dataset;
	}

	/**
	 * Determines series name of the trajectory element
	 * 
	 * @param data
	 *            hybrid trajectory element
	 * @return series name
	 */
	private static String getSeriesName(HybridTrajectoryElement<?> data) {

		String name = data.getParent().getProperties().getIndexedName();
		if (includeVarNameInLabel) {
			name += " (" + data.getChild().getName() + ")";
		}
		return name;
	}

	/**
	 * Create a new panel that is configured for use with a figure
	 * 
	 * @return figure panel
	 */
	public static JPanel newPanel() {

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBackground(null);
		return panel;
	}
}
