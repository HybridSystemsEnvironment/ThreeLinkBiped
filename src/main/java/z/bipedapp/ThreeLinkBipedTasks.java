
package z.bipedapp;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartPanel;

import com.be3short.obj.modification.XMLParser;

import biped.reference.control.BipedReferenceControl;
import biped.reference.control.BipedTrackingController;
import edu.ucsc.cross.hse.core.chart.ChartType;
import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.chart.RendererConfiguration;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import edu.ucsc.hsl.hse.model.biped.threelink.computors.ControllerConstraint;
import edu.ucsc.hsl.hse.model.biped.threelink.controllers.BipedVirtualControl;
import edu.ucsc.hsl.hse.model.biped.threelink.factories.BipedSettingFactory;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.ActuatorConstraint;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.PerturbationParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.states.BipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.PerturbationState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.PerturbedClosedLoopBipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.VirtualBipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.systems.ClosedLoopBipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.systems.ClosedLoopBipedSystem;
import edu.ucsc.hsl.hse.model.biped.threelink.systems.PerturbedClosedLoopSystem;
import edu.ucsc.hsl.hse.model.biped.threelink.systems.ReferenceSystem;

public class ThreeLinkBipedTasks {

	/*
	 * Main class needed to run java application
	 */
	public static void main(String args[]) {

		Console.setSettings(BipedSettingFactory.getNonDebugConsoleSettings());
		runPerturbedClosedLoopSystemWithRandomizedIC(0.8);
	}

	public void openHSEnvironmentAndPlot() {

		// HSEnvironment env = HSEnvironment.loadFromFile(FileBrowser.load());

		// File datFile = new FileChooser().showOpenDialog(new Stage());
		// HSEnvironmentData dat = DataMonitor.getCSVData(datFile);
		// env.getData().load(dat.getStoreTimes(), dat.getGlobalStateData());
		// env.getOutputs().generateOutputs(env, true);
		// env.add(xyCombination());
		// System.out.println(XMLParser.serializeObject(env));
		// statesAndTimerChart().plot(env);
		// env.generateOutputs();
		// limbAnglesAndVelocities().createPlot(env);
		// env.add(HybridChart2);// .createChart(env);
		// env.generateOutputs();
	}

	public static ReferenceSystem getTestReferenceSystem() {

		VirtualBipedState state = VirtualBipedState.getTestState();
		BipedParameters params = BipedParameters.getTestParams();
		BipedReferenceControl controller = new BipedReferenceControl(state, params);
		ReferenceSystem reference = new ReferenceSystem(state, params, controller);

		reference.initialize();

		return reference;
	}

	public static ClosedLoopBipedSystem getClosedLoopSystemWithRandomizedStates(BipedParameters params,
			ReferenceSystem ref) {

		BipedState bip = BipedState.getRandomizedState(params);

		VirtualBipedState vbip = VirtualBipedState.getRandomizedState(params);

		vbip.trajTimer = params.getStepTime() * ((Math.random() * .8) + .2);
		BipedVirtualControl vc = new BipedVirtualControl(bip, vbip, ref.getComponents().getState(), params);
		BipedTrackingController tc = new BipedTrackingController(bip, vbip, vc, params);

		bip.getProperties().setName("Plant");
		vbip.getProperties().setName("Virtual System");

		ClosedLoopBipedSystem sys = new ClosedLoopBipedSystem(new ClosedLoopBipedState(bip, vbip),
				ref.getComponents().getState(), params, vc, tc);
		System.out.println(XMLParser.serializeObject(sys));
		return sys;// new HybridSystem<?>[]
		// { sys, ref };
	}

	public static PerturbedClosedLoopSystem getClosedLoopSystemWithPerturbations(BipedState plant,
			VirtualBipedState virtual, VirtualBipedState reference, BipedParameters params,
			Double maximum_perturbation_percentage) {

		BipedVirtualControl vc = new BipedVirtualControl(plant, virtual, reference, params);
		BipedTrackingController tc = new BipedTrackingController(plant, virtual, vc, params);
		PerturbationParameters pParams = new PerturbationParameters(maximum_perturbation_percentage);
		PerturbationState ps = new PerturbationState(
				(Math.random() * pParams.perturbationPercentage) * params.stepAngle);

		PerturbedClosedLoopSystem sys = new PerturbedClosedLoopSystem(
				new PerturbedClosedLoopBipedState(plant, virtual, ps), reference, params, vc, tc, pParams);
		System.out.println(XMLParser.serializeObject(sys));
		return sys;
	}

	public static PerturbedClosedLoopSystem getClosedLoopSystemWithPerturbations(BipedState plant,
			VirtualBipedState virtual, VirtualBipedState reference, BipedParameters params,
			Double maximum_perturbation_percentage, ActuatorConstraint constraint) {

		BipedVirtualControl vc = new BipedVirtualControl(plant, virtual, reference, params);
		BipedTrackingController tc = new BipedTrackingController(plant, virtual, vc, params);
		ControllerConstraint controlConst = new ControllerConstraint(tc, constraint);
		PerturbationParameters pParams = new PerturbationParameters(maximum_perturbation_percentage);
		PerturbationState ps = new PerturbationState(
				(Math.random() * pParams.perturbationPercentage) * params.stepAngle);

		PerturbedClosedLoopSystem sys = new PerturbedClosedLoopSystem(
				new PerturbedClosedLoopBipedState(plant, virtual, ps), reference, params, vc, controlConst, pParams);
		System.out.println(XMLParser.serializeObject(sys));
		return sys;
	}

	public static HybridSystem<?>[] getPerturbedClosedLoopSystemWithRandomizedIC(BipedParameters params,
			Double maximum_perturbation_percentage) {

		BipedState plant = BipedState.getRandomizedState(params);
		VirtualBipedState virtual = VirtualBipedState.getRandomizedState(params);
		VirtualBipedState reference = VirtualBipedState.getRandomizedState(params);
		virtual.trajTimer = params.getStepTime() * ((Math.random() * .8) + .2);
		BipedReferenceControl controller = new BipedReferenceControl(reference, params);
		ReferenceSystem referenceSys = new ReferenceSystem(reference, params, controller);

		PerturbedClosedLoopSystem perturbationSystem = getClosedLoopSystemWithPerturbations(plant, virtual, reference,
				params, maximum_perturbation_percentage);

		plant.getProperties().setName("Plant");
		virtual.getProperties().setName("Virtual");
		perturbationSystem.getComponents().getState().getProperties().setName("Perturbation");
		reference.getProperties().setName("Reference");

		return new HybridSystem<?>[] { referenceSys, perturbationSystem };
	}

	public static HybridSystem<?>[] getPerturbedClosedLoopSystemWithRandomizedIC(BipedParameters params,
			Double maximum_perturbation_percentage, ActuatorConstraint constraint) {

		BipedState plant = BipedState.getRandomizedState(params);
		VirtualBipedState virtual = VirtualBipedState.getRandomizedState(params);
		VirtualBipedState reference = VirtualBipedState.getRandomizedState(params);
		virtual.trajTimer = params.getStepTime() * ((Math.random() * .8) + .2);
		BipedReferenceControl controller = new BipedReferenceControl(reference, params);
		ReferenceSystem referenceSys = new ReferenceSystem(reference, params, controller);

		PerturbedClosedLoopSystem perturbationSystem = getClosedLoopSystemWithPerturbations(plant, virtual, reference,
				params, maximum_perturbation_percentage, constraint);

		plant.getProperties().setName("Plant");
		virtual.getProperties().setName("Virtual");
		perturbationSystem.getComponents().getState().getProperties().setName("Perturbation");
		reference.getProperties().setName("Reference");

		return new HybridSystem<?>[] { referenceSys, perturbationSystem };
	}

	public static void runPerturbedClosedLoopSystemWithRandomizedIC(double perturbation_percentage) {

		BipedParameters parameters = BipedParameters.getTestParams();
		HybridSystem<?>[] systems = getPerturbedClosedLoopSystemWithRandomizedIC(parameters, perturbation_percentage);
		HSEnvironment env = new HSEnvironment();
		env.getSettings().dataPointInterval = .1;
		env.getSystems().add(systems[1]);
		env.getSystems().add(systems[0]);
		// HSEFile.saveToNewFile(FileBrowser.save(), env);
		TrajectorySet traj = env.run(18.0, 100);
		// generateBipedLimbStateFigure2by3Figure(traj).exportToFile(FileBrowser.save(),
		// GraphicFormat.EPS);
		generateBipedLimbStateFigureVerticalPerturbed(traj).display();// .exportToFile(FileBrowser.save(),
																		// GraphicFormat.EPS);
	}

	public static RendererConfiguration getDefaultBipedRenderer() {

		RendererConfiguration bipedRend = new RendererConfiguration();
		bipedRend.assignSeriesColor("Plant", Color.BLUE);
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

		ChartUtils.configureLabels(pA, " ", " ", null, false);
		ChartUtils.configureLabels(sA, " ", " ", null, false);
		ChartUtils.configureLabels(tA, " ", " ", null, false);
		ChartUtils.configureLabels(pV, " ", " ", null, false);
		ChartUtils.configureLabels(sV, " ", " ", null, false);
		ChartUtils.configureLabels(tV, " ", " ", null, false);
		// ChartUtils.configureLabels(pA, null, " ", null, false);
		// ChartUtils.configureLabels(sA, null, " ", null, false);
		// ChartUtils.configureLabels(tA, null, " ", null, false);
		// ChartUtils.configureLabels(pV, null, " ", null, false);
		// ChartUtils.configureLabels(sV, null, " ", null, false);
		// ChartUtils.configureLabels(tV, null, " ", null, false);
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

		// ChartUtils.configureLabels(pA, " ", " ", null, false);
		// ChartUtils.configureLabels(sA, " ", " ", null, false);
		// ChartUtils.configureLabels(tA, " ", " ", null, false);
		// ChartUtils.configureLabels(pV, " ", " ", null, false);
		// ChartUtils.configureLabels(sV, " ", " ", null, false);
		// ChartUtils.configureLabels(tV, " ", " ", null, false);
		// ChartUtils.configureLabels(pert, " ", " ", null, false);

		ChartUtils.configureLabels(pA, null, " ", null, false);
		ChartUtils.configureLabels(sA, null, " ", null, false);
		ChartUtils.configureLabels(tA, null, " ", null, false);
		ChartUtils.configureLabels(pV, null, " ", null, false);
		ChartUtils.configureLabels(sV, null, " ", null, false);
		ChartUtils.configureLabels(tV, null, " ", null, false);
		ChartUtils.configureLabels(pert, " ", " ", null, false);
		figure.getTitle().setText(null);
		return figure;
	}
}