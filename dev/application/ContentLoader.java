
package net.biped.application;

import Jama.Matrix;
import biped.reference.control.VirtualFlowController;
import biped.parameters.virtual.Parameters;
import biped.parameters.virtual.State;
import biped.plant.hybridsystem.Link;
import biped.reference.control.PlantFlowController;
import biped.reference.control.ReferenceJumpController;
import biped.reference.control.VirtualJumpController;
import biped.virtual.hybridsystem.Cp;
import biped.virtual.hybridsystem.Dp;
import biped.virtual.hybridsystem.Fp;
import biped.virtual.hybridsystem.Gp;
import biped.virtual.hybridsystem.TrajectoryParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.label.Label;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.network.Network;
import edu.ucsc.cross.hse.core.network.SubNode;

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
		Parameters parameters = new Parameters(biped.parameters.base.Parameters.getTestParams());
		// Initialize the state
		State state = new State(biped.parameters.base.State.getRandomizedState(parameters.bipedParams), parameters);

		// Initialize the hybrid system
		createSystems(environment);

		// Add system to environment
	}

	public static void createSystems(HSEnvironment environment) {

		// Initialize the parameters
		Parameters parameters = new Parameters(biped.parameters.base.Parameters.getTestParams());
		// Initialize the state
		State state = new State(biped.parameters.base.State.getRandomizedState(parameters.bipedParams), parameters);
		// Initialize the state
		State state2 = new State(biped.parameters.base.State.getRandomizedState(parameters.bipedParams), parameters);// Initialize
																													// the
																													// state
		biped.parameters.base.State state3 = new State(biped.parameters.base.State.getRandomizedState(parameters.bipedParams),
				parameters).bipedState;

		HybridSys<State> system = createVirtualSystem(state, parameters, new VirtualFlowController(parameters),
				new ReferenceJumpController(parameters));
		HybridSys<State> system2 = createVirtualSystem(state2, parameters, new VirtualFlowController(parameters),
				new VirtualJumpController(parameters));
		HybridSys<biped.parameters.base.State> system3 = createRealSystem(state3, parameters.bipedParams,
				new PlantFlowController(parameters.bipedParams));

		Network.getGlobal().connect(state, state.bipedState, Label.get(Link.PLANT_TO_VIRTUAL.toString()));
		Network.getGlobal().connect(state2, state, Label.get(Link.REFERENCE_TO_VIRTUAL.toString()));
		Network.getGlobal().connect(state, state2, Label.get(Link.REFERENCE_TO_VIRTUAL.toString()));
		Network.getGlobal().connect(state2, state3, Label.get(Link.PLANT_TO_VIRTUAL.toString()));
		Network.getGlobal().connect(state3, state2.bipedState, Label.get(Link.PLANT_TO_VIRTUAL.toString()));

		environment.getSystems().add(system, system2, system3);
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
	public static HybridSys<State> createVirtualSystem(State state, Parameters parameters,
			Controller<State, Matrix> continuous_controller,
			Controller<State, TrajectoryParameters> discrete_controller) {

		// Initialize the flow map
		Fp f = new Fp(parameters, continuous_controller);
		// Initialize the jump map
		Gp g = new Gp(discrete_controller, parameters);
		// Initialize the flow set
		Cp c = new Cp(parameters);
		// Initialize the jump set
		Dp d = new Dp(parameters);
		// Initialize the hybrid system
		HybridSys<State> system = new HybridSys<State>(state, f, g, c, d, parameters);
		return system;
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
	public static HybridSys<biped.parameters.base.State> createRealSystem(biped.parameters.base.State state,
			biped.parameters.base.Parameters parameters,
			Controller<biped.parameters.base.State, Matrix> continuous_controller) {

		// Initialize the flow map
		biped.plant.hybridsystem.Fp f = new biped.plant.hybridsystem.Fp(parameters, continuous_controller);
		// Initialize the jump map
		biped.plant.hybridsystem.Gp g = new biped.plant.hybridsystem.Gp(parameters);
		// Initialize the flow set
		biped.plant.hybridsystem.Cp c = new biped.plant.hybridsystem.Cp(parameters);
		// Initialize the jump set
		biped.plant.hybridsystem.Dp d = new biped.plant.hybridsystem.Dp(parameters);
		// Initialize the hybrid system
		HybridSys<biped.parameters.base.State> system = new HybridSys<biped.parameters.base.State>(state, f, g, c, d,
				parameters);

		return system;
	}

	public static <Z, Q> Z getConnected(Q source, Class<Z> conn_class, String... labels) {

		SubNode<Q, Z> node = Network.getGlobal().getSubNode(source, conn_class, labels);
		Z item = null;
		if (node.getIncoming().size() > 0) {
			item = node.getIncoming().get(0).getSource();
		} else if (node.getOutgoing().size() > 0) {
			item = node.getOutgoing().get(0).getTarget();
		}
		return item;
	}
}
