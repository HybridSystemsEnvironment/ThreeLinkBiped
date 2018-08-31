
package z.bipedapp;

import org.jfree.chart.ChartPanel;

import edu.ucsc.cross.hse.core.chart.ChartUtils;
import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.figure.Figure;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.trajectory.HybridTime;
import edu.ucsc.cross.hse.core.trajectory.TrajectorySet;
import edu.ucsc.hsl.hse.model.biped.threelink.factories.BipedFigureFactory;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.ActuatorConstraint;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;

/**
 * The main class of the bouncing ball application that prepares and operates
 * the environment, and generates figure(s).
 * 
 * @author Brendan Short
 *
 */
public class BipedApplication {

	/**
	 * Main method for running application
	 * 
	 * @param args
	 *            none
	 */
	public static void main(String args[]) {

		// Load console settings
		loadConsoleSettings();
		for (int i = 0; i < 1; i++) {
			// Create set of connected agents
			SystemSet systems = new SystemSet(ThreeLinkBipedTasks.getPerturbedClosedLoopSystemWithRandomizedIC(
					BipedParameters.getTestParams(), 0.0, new ActuatorConstraint(100.0)));

			// Create configured settings
			EnvironmentSettings settings = getEnvironmentSettings();
			// Create loaded environment
			HSEnvironment environment = HSEnvironment.create(systems, settings);
			// Run simulation and store result trajectories
			TrajectorySet trajectories = environment.run();
			// Generate figure and display in window
			BipedFigureFactory.generateBipedLimbStateFigure2by3FigureLatex(trajectories).display();//
			// .exportToFile(FileBrowser.save(),
			// BipedFigureFactory.generateBipedLimbStateFigure2by3FigureLatex(trajectories).exportToFile(
			// new File("bipedTrials/Converge_" + i),
			//
			// GraphicFormat.PDF);// .display();

			// BipedFigureFactory.generateBipedLimbStateFigureAnglesAndPerturbLatex(trajectories).exportToFile(
			// new File("bipedTrials/Converge_" + i),

			// GraphicFormat.PDF);// .display();
		}
		// BipedFigureFactory.generateBipedLimbStateFigure2by3FigureLatex(trajectories).display();
	}

	/**
	 * Creates the configured environment settings
	 * 
	 * @return EnvironmentSettings
	 */
	public static EnvironmentSettings getEnvironmentSettings() {

		// Create engine settings
		EnvironmentSettings settings = new EnvironmentSettings();
		// Specify general parameter values
		settings.maximumJumps = 10000;
		settings.maximumTime = 5;
		settings.dataPointInterval = .1;
		settings.eventHandlerMaximumCheckInterval = 1E-3;
		settings.eventHandlerConvergenceThreshold = 1E-9;
		settings.maxEventHandlerIterations = 100;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		// Specify integrator parameter values
		double odeMaximumStepSize = 1e-1;
		double odeMinimumStepSize = 1e-3;
		double odeRelativeTolerance = 1.0e-6;
		double odeSolverAbsoluteTolerance = 1.0e-6;
		// Create and store integrator factory
		settings.integrator = new DormandPrince853IntegratorFactory(odeMinimumStepSize, odeMaximumStepSize,
				odeRelativeTolerance, odeSolverAbsoluteTolerance);
		// Return configured settings
		return settings;
	}

	/**
	 * Creates and loads console settings
	 */
	public static void loadConsoleSettings() {

		// Create new console settings
		ConsoleSettings console = new ConsoleSettings();
		// Configure message type visibility
		console.printInfo = true;
		console.printDebug = false;
		console.printWarning = true;
		console.printError = true;
		// Configure status messages
		console.printIntegratorExceptions = false;
		console.printStatusInterval = 10.0;
		console.printProgressIncrement = 10;
		// Configure input and output handling
		console.printLogToFile = true;
		console.terminateAtInput = true;
		// Load configured settings
		Console.setSettings(console);
	}

	/**
	 * Generate a figure with the vertical (y position and velocity) bouncing ball
	 * state elements
	 * 
	 * @param solution
	 *            trajectory set containing data to load into figure
	 * @return a figure displaying all vertical bouncing ball state elements
	 */
	public static Figure generateVerticalStateFigure(TrajectorySet solution) {

		// Create figure w:1000 h:600
		Figure figure = new Figure(1000, 600);
		// Assign title to figure
		figure.getTitle().setText("Bouncing Ball Simulation");
		// Create charts
		ChartPanel yPos = ChartUtils.createPanel(solution, HybridTime.TIME, "yPosition");
		ChartPanel yVel = ChartUtils.createPanel(solution, HybridTime.TIME, "yVelocity");
		// Label chart axis and configure legend visibility
		ChartUtils.configureLabels(yPos, "Time (sec)", "Y Position (m)", null, false);
		ChartUtils.configureLabels(yVel, "Time (sec)", "Y Velocity (m/s)", null, false);
		// Add charts to figure
		figure.add(0, 0, yPos);
		figure.add(0, 1, yVel);
		// Return generated figure
		return figure;
	}

}