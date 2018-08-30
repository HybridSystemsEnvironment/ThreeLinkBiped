
package net.biped.application;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartType;
import edu.ucsc.cross.hse.core.chart.RendererConfiguration;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure2.ChartUtils;
import edu.ucsc.cross.hse.core.figure2.FigurePanel;
import edu.ucsc.cross.hse.core.figure2.LatexLabel;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

/**
 * A post processor
 */
public class PostProcessor {

	/**
	 * Executes all processing tasks. This method is called by the main applications
	 */
	public static void processEnvironmentData(HSEnvironment environment) {

		Figure figure = generateBipedLimbStateFigure2by3FigureLatex(environment.getTrajectories());
		Figure figure2 = generateBipedStateFigure(environment.getTrajectories());

		figure.display();
		figure2.display();

		environment.saveToFile(false);
	}

	/**
	 * Generate a figure
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying state values
	 */
	public static Figure generateVerticalStateFigure(TrajectorySet solution) {

		// Create figure (width, height, title)
		Figure figure = new Figure(600, 600, "Template Simulation");
		// Add charts to figure (
		figure.add(0, 0, solution, HybridTime.TIME, "value", "Time (sec)", "State Value", null, false);
		// Return generated figure
		return figure;
	}

	/**
	 * Generate a figure
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying state values
	 */
	public static Figure generateBipedStateFigure(TrajectorySet solution) {

		// Create figure (width, height, title)
		Figure figure = new Figure(600, 600, "Template Simulation");
		// Add charts to figure (
		figure.add(0, 0, solution, HybridTime.TIME, "trajTimer", "Time (sec)", "State Value", null, true);
		// Return generated figure
		return figure;
	}

	public static Figure generateBipedLimbStateFigure2by3FigureLatex(TrajectorySet solution) {

		Figure figure = new Figure(1000, 600);
		ChartPanel pAC = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		pAC.getChart().getLegend().setVisible(false);
		FigurePanel pA = new FigurePanel(pAC);
		FigurePanel sA = new FigurePanel(ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegAngle",
				ChartType.LINE, getDefaultBipedRenderer(), pAC.getChart()));
		FigurePanel tA = new FigurePanel(ChartUtils.createPanel(solution, HybridTime.TIME, "torsoAngle", ChartType.LINE,
				getDefaultBipedRenderer(), pAC.getChart()));
		FigurePanel pV = new FigurePanel(ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegVelocity",
				ChartType.LINE, getDefaultBipedRenderer(), pAC.getChart()));
		FigurePanel sV = new FigurePanel(ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegVelocity",
				ChartType.LINE, getDefaultBipedRenderer(), pAC.getChart()));
		FigurePanel tV = new FigurePanel(ChartUtils.createPanel(solution, HybridTime.TIME, "torsoVelocity",
				ChartType.LINE, getDefaultBipedRenderer(), pAC.getChart()));
		// figure.getContentPanel().setLayout(new GridLayout(2, 3));
		Double rot = -Math.PI / 2;
		try {

			pA.setLeft((new LatexLabel("latex/plantedAngle.html", 25, rot)).getLabelComponent());
			sA.setLeft((new LatexLabel("latex/swingAngle.html", 25, rot)).getLabelComponent());
			tA.setLeft((new LatexLabel("latex/torsoAngle.html", 25, rot)).getLabelComponent());
			pV.setLeft((new LatexLabel("latex/plantedVelocity.html", 25, rot)).getLabelComponent());
			sV.setLeft((new LatexLabel("latex/swingVelocity.html", 25, rot)).getLabelComponent());
			tV.setLeft((new LatexLabel("latex/torsoVelocity.html", 25, rot)).getLabelComponent());
			pA.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());
			sA.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());
			tA.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());
			pV.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());
			sV.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());
			tV.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// LegendPanel leg = new LegendPanel();
		// leg.addChart(pAC.getChart());
		FigurePanel figurem = new FigurePanel();
		figurem.add(1, 1, pA.getContentPanel());
		figurem.add(2, 1, sA.getContentPanel());
		figurem.add(3, 1, tA.getContentPanel());
		figurem.add(1, 2, pV.getContentPanel());
		figurem.add(2, 2, sV.getContentPanel());
		figurem.add(3, 2, tV.getContentPanel());
		// leg.setPreferredSize(new Dimension(1200, 30));
		// figurem.setBottom(leg);

		figure.add(1, 1, figurem.getContentPanel());

		// figurem.display(1200, 1000);
		// ChartUtils.configureLabels(pA, "Time (sec)", "Planted Leg
		// Angle (rad)", null,
		// false);
		// ChartUtils.configureLabels(sA, "Time (sec)", "Swing Leg Angle (rad)", null,
		// false);
		// ChartUtils.configureLabels(tA, "Time (sec)", "Torso Angle (rad)", null,
		// false);
		// ChartUtils.configureLabels(pV, "Time (sec)", "Planted Leg Velocity (rad/s)",
		// null, false);
		// ChartUtils.configureLabels(sV, "Time (sec)", "Swing Leg Velocity (rad/s)",
		// null, true);
		// ChartUtils.configureLabels(tV, "Time (sec)", "Torso Velocity (rad/s)", null,
		// false);

		// figure.getTitle().setText(null);
		return figure;
	}

	public static RendererConfiguration getDefaultBipedRenderer() {

		RendererConfiguration bipedRend = new RendererConfiguration();
		bipedRend.assignSeriesColor("Physical", Color.BLUE);
		bipedRend.assignSeriesColor("Virtual", Color.GREEN);
		bipedRend.assignSeriesColor("Reference", Color.RED);
		bipedRend.assignSeriesColor("Perturbation", Color.MAGENTA);
		float dash[] = { 10.0f };
		BasicStroke virtual = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		bipedRend.assignSeriesStroke("Virtual", virtual);
		float[] dashingPattern3 = { 10f, 10f, 1f, 10f };
		BasicStroke stroke3 = new BasicStroke(3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 1.0f,
				dashingPattern3, 0.0f);
		bipedRend.assignSeriesStroke("Reference", stroke3);
		return bipedRend;
	}
}
