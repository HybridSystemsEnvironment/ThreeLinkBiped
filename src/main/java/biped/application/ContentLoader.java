
package biped.application;

import biped.hybridsystem.Link;
import biped.reference.control.BipedReferenceControl;
import biped.reference.control.DiscreteController;
import biped.virtual.hybridsystem.Cp;
import biped.virtual.hybridsystem.Dp;
import biped.virtual.hybridsystem.Fp;
import biped.virtual.hybridsystem.Gp;
import biped.virtual.hybridsystem.Parameters;
import biped.virtual.hybridsystem.State;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.label.Label;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;

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
		State state = new State(biped.hybridsystem.State.getRandomizedState(parameters.bipedParams), parameters);

		// Initialize the hybrid system
		HybridSys<State> system = createSystem(state, parameters);

		Network.getGlobal().connect(state, state.bipedState, Label.get(Link.PLANT_TO_VIRTUAL.toString()));
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

		DiscreteController dcon = new DiscreteController(parameters);
		BipedReferenceControl control = new BipedReferenceControl(parameters);
		// Initialize the flow map
		Fp f = new Fp(parameters, control);
		// Initialize the jump map
		Gp g = new Gp(dcon, parameters);
		// Initialize the flow set
		Cp c = new Cp(parameters);
		// Initialize the jump set
		Dp d = new Dp(parameters);
		// Initialize the hybrid system
		HybridSys<State> system = new HybridSys<State>(state, f, g, c, d, parameters);

		return system;
	}
}
