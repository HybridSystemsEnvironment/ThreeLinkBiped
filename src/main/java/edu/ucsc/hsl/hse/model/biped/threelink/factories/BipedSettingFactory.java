package edu.ucsc.hsl.hse.model.biped.threelink.factories;

import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;

public class BipedSettingFactory
{

	public static EnvironmentSettings getSettingsFast()
	{
		EnvironmentSettings settings = new EnvironmentSettings();
		settings.odeMinimumStepSize = .00005;
		settings.odeMaximumStepSize = .001;
		settings.odeSolverAbsoluteTolerance = 1.0e-8;
		settings.odeRelativeTolerance = 1.0e-8;
		settings.eventHandlerMaximumCheckInterval = .5E-5;
		settings.eventHandlerConvergenceThreshold = .00000000000001;
		settings.maxEventHandlerIterations = 100;
		settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		return settings;
	}

	public static ConsoleSettings getNonDebugConsoleSettings()
	{
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
