
package biped.application;

import biped.virtual.hybridsystem.BipedReferenceControl;
import biped.virtual.hybridsystem.Cp;
import biped.virtual.hybridsystem.Dp;
import biped.virtual.hybridsystem.Fp;
import biped.virtual.hybridsystem.Gp;
import biped.virtual.hybridsystem.Parameters;
import biped.virtual.hybridsystem.State;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.modeling.HybridSys;

/**
 * A content loader
 */
public class ContentLoader {

	/**
	 * Load the environment contents
	 * 
	 * @param environment
	 *            environment to be loaded
	 */
	public static void loadEnvironmentContent(HSEnvironment environment) {

		// Initialize the parameters
		Parameters parameters = new Parameters(biped.hybridsystem.Parameters.getTestParams());
		// Initialize the state
		State state = new State(biped.hybridsystem.BipedState.getRandomizedState(parameters.bipedParams), parameters);

		// Initialize the hybrid system
		HybridSys<State> system = createSystem(state, parameters);
		// Add system to environment
		environment.getSystems().add(system);
	}

	/**
	 * Create the hybrid system
	 * 
	 * @param state
	 *            system state
	 * @param parameters
	 *            system parameters
	 * @return initialized hybrid system
	 */
	public static HybridSys<State> createSystem(State state, Parameters parameters) {

		BipedReferenceControl control = new BipedReferenceControl(state, parameters);
		// Initialize the flow map
		Fp f = new Fp(parameters, control);
		// Initialize the jump map
		Gp g = new Gp(parameters);
		// Initialize the flow set
		Cp c = new Cp(parameters);
		// Initialize the jump set
		Dp d = new Dp(parameters);
		// Initialize the hybrid system
		HybridSys<State> system = new HybridSys<State>(state, f, g, c, d, parameters);

		return system;
	}
}
