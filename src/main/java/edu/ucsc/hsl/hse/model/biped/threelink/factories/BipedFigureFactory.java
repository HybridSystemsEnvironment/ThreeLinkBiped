
package edu.ucsc.hsl.hse.model.biped.threelink.factories;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartType;
import edu.ucsc.cross.hse.core.chart.RendererConfiguration;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.figure2.ChartUtils;
import edu.ucsc.cross.hse.core.figure2.FigurePanel;
import edu.ucsc.cross.hse.core.figure2.LatexLabel;
import edu.ucsc.cross.hse.core.figure2.LegendPanel;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;

public class BipedFigureFactory {

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

	public static Figure generateBipedLimbStateFigure2by3Figure(TrajectorySet solution) {

		Figure figure = new Figure(1000, 600);

		ChartPanel pA = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "torsoAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegVelocity", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegVelocity", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel tV = ChartUtils.createPanel(solution, HybridTime.TIME, "torsoVelocity", ChartType.LINE,
				getDefaultBipedRenderer(), null);

		figure.addComponent(1, 0, pA);
		figure.addComponent(2, 0, sA);
		figure.addComponent(3, 0, tA);
		figure.addComponent(1, 1, pV);
		figure.addComponent(2, 1, sV);
		figure.addComponent(3, 1, tV);

		ChartUtils.configureLabels(pA, "Time (sec)", "Planted Leg Angle (rad)", null, false);
		ChartUtils.configureLabels(sA, "Time (sec)", "Swing Leg Angle (rad)", null, false);
		ChartUtils.configureLabels(tA, "Time (sec)", "Torso Angle (rad)", null, false);
		ChartUtils.configureLabels(pV, "Time (sec)", "Planted Leg Velocity (rad/s)", null, false);
		ChartUtils.configureLabels(sV, "Time (sec)", "Swing Leg Velocity (rad/s)", null, true);
		ChartUtils.configureLabels(tV, "Time (sec)", "Torso Velocity (rad/s)", null, false);

		figure.getTitle().setText(null);
		return figure;
	}

	public static Figure generateBipedLegAngleFigPerturbed(TrajectorySet solution) {

		Figure figure = new Figure(1000, 600);

		ChartPanel pA = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);

		ChartPanel pert = ChartUtils.createPanel(solution, HybridTime.TIME, "perturbationAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		figure.addComponent(0, 0, pA);
		figure.addComponent(0, 1, sA);

		figure.addComponent(0, 2, pert);

		ChartUtils.configureLabels(pA, null, "Planted Leg Angle (rad)", null, false);
		ChartUtils.configureLabels(sA, null, "Swing Leg Angle (rad)", null, true);
		ChartUtils.configureLabels(pert, "Time (sec)", "Perturbed Angle(rad)", null, false);
		figure.getTitle().setText(null);
		return figure;
	}

	public static Figure generateBipedLimbStateFigureVerticalPerturbed(TrajectorySet solution) {

		Figure figure = new Figure(1000, 1400);

		ChartPanel pA = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel sA = ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel tA = ChartUtils.createPanel(solution, HybridTime.TIME, "torsoAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel pV = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegVelocity", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel sV = ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegVelocity", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel tV = ChartUtils.createPanel(solution, HybridTime.TIME, "torsoVelocity", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		ChartPanel pert = ChartUtils.createPanel(solution, HybridTime.TIME, "perturbationAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		figure.addComponent(0, 1, pA);
		figure.addComponent(0, 2, sA);
		figure.addComponent(0, 3, tA);
		figure.addComponent(0, 4, pV);
		figure.addComponent(0, 5, sV);
		figure.addComponent(0, 6, tV);
		figure.addComponent(0, 7, pert);

		ChartUtils.configureLabels(pA, null, "Planted Angle (rad)", null, false);
		ChartUtils.configureLabels(sA, null, "Swing Angle (rad)", null, false);
		ChartUtils.configureLabels(tA, null, "Torso Angle (rad)", null, false);
		ChartUtils.configureLabels(pV, null, "Planted Velocity (rad/s)", null, false);
		ChartUtils.configureLabels(sV, null, "Swing Velocity (rad/s)", null, false);
		ChartUtils.configureLabels(tV, null, "Torso Velocity (rad/s)", null, true);
		ChartUtils.configureLabels(pert, "Time (sec)", "Perturbed Angle(rad)", null, false);
		figure.getTitle().setText(null);
		return figure;
	}

	public static Figure generateBipedLimbStateFigureAnglesAndPerturbLatex(TrajectorySet solution) {

		Figure figure = new Figure(1000, 600);
		ChartPanel pAC = ChartUtils.createPanel(solution, HybridTime.TIME, "plantedLegAngle", ChartType.LINE,
				getDefaultBipedRenderer(), null);
		pAC.getChart().getLegend().setVisible(false);
		FigurePanel pA = new FigurePanel(pAC);
		FigurePanel sA = new FigurePanel(ChartUtils.createPanel(solution, HybridTime.TIME, "swingLegAngle",
				ChartType.LINE, getDefaultBipedRenderer(), pAC.getChart()));
		FigurePanel tA = new FigurePanel(ChartUtils.createPanel(solution, HybridTime.TIME, "torsoAngle", ChartType.LINE,
				getDefaultBipedRenderer(), pAC.getChart()));
		FigurePanel pV = new FigurePanel(ChartUtils.createPanel(solution, HybridTime.TIME, "perturbationAngle",
				ChartType.LINE, getDefaultBipedRenderer(), pAC.getChart()));
		Double rot = -Math.PI / 2;
		try {

			pA.setLeft((new LatexLabel("latex/plantedAngle.html", 25, rot)).getLabelComponent());
			sA.setLeft((new LatexLabel("latex/swingAngle.html", 25, rot)).getLabelComponent());
			tA.setLeft((new LatexLabel("latex/torsoAngle.html", 25, rot)).getLabelComponent());
			pV.setLeft((new LatexLabel("latex/perturbationAngle.html", 25, rot)).getLabelComponent());

			pA.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());
			sA.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());
			tA.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());
			pV.setBottom((new LatexLabel("latex/time.html", 25, 0.0)).getLabelComponent());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LegendPanel leg = new LegendPanel();
		leg.addChart(pAC.getChart());
		FigurePanel figurem = new FigurePanel();
		figurem.addComponent(1, 1, pA.getContentPanel());
		figurem.addComponent(1, 2, sA.getContentPanel());
		figurem.addComponent(1, 3, tA.getContentPanel());
		figurem.addComponent(1, 4, pV.getContentPanel());

		leg.setPreferredSize(new Dimension(1200, 30));
		figurem.setBottom(leg);

		figure.addComponent(1, 1, figurem.getContentPanel());

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
		figurem.addComponent(1, 1, pA.getContentPanel());
		figurem.addComponent(2, 1, sA.getContentPanel());
		figurem.addComponent(3, 1, tA.getContentPanel());
		figurem.addComponent(1, 2, pV.getContentPanel());
		figurem.addComponent(2, 2, sV.getContentPanel());
		figurem.addComponent(3, 2, tV.getContentPanel());
		// leg.setPreferredSize(new Dimension(1200, 30));
		// figurem.setBottom(leg);

		figure.addComponent(1, 1, figurem.getContentPanel());

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

}
