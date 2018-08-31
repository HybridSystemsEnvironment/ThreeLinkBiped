
package edu.ucsc.hsl.hse.model.biped.threelink.factories;

import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.integrator.DormandPrince853IntegratorFactory;
import edu.ucsc.cross.hse.core.integrator.FirstOrderIntegratorFactory;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.specification.DomainPriority;

public class BipedSettingFactory {

	/**
	 * Initializes environment settings with configuration B
	 * 
	 * @return EnvironmentSettings
	 */
	public static EnvironmentSettings getBouncingBallEnvSettings() {

		EnvironmentSettings settings = new EnvironmentSettings();

		settings.maximumJumps = 10000;
		settings.maximumTime = 14;
		settings.dataPointInterval = .05;
		settings.eventHandlerMaximumCheckInterval = 1E-3;
		settings.eventHandlerConvergenceThreshold = 1E-9;
		settings.maxEventHandlerIterations = 100;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;

		double odeMaximumStepSize = 1e-3;
		double odeMinimumStepSize = 1e-9;
		double odeRelativeTolerance = 1.0e-6;
		double odeSolverAbsoluteTolerance = 1.0e-6;
		FirstOrderIntegratorFactory defaultIntegrator = new DormandPrince853IntegratorFactory(odeMinimumStepSize,
				odeMaximumStepSize, odeRelativeTolerance, odeSolverAbsoluteTolerance);
		settings.integrator = defaultIntegrator;

		return settings;

	}

	public static ConsoleSettings getNonDebugConsoleSettings() {

		ConsoleSettings console = new ConsoleSettings();
		console.printStatusInterval = 20.0;
		console.printProgressIncrement = 10;
		console.printIntegratorExceptions = false;
		console.printInfo = true;
		console.printDebug = false;
		console.printWarning = true;
		console.printError = true;
		console.printLogToFile = true;
		console.terminateAtInput = true;
		return console;
	}
}
