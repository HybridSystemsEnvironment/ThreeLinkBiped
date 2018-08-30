
package biped.application;

import Jama.Matrix;
import biped.reference.control.PlantFlowController;
import biped.reference.control.ReferenceJumpController;
import biped.reference.control.VirtualFlowController;
import biped.reference.control.VirtualJumpController;
import biped.virtual.hybridsystem.Cp;
import biped.virtual.hybridsystem.Dp;
import biped.virtual.hybridsystem.Fp;
import biped.virtual.hybridsystem.Gp;
import biped.virtual.hybridsystem.Parameters;
import biped.virtual.hybridsystem.State;
import biped.virtual.hybridsystem.TrajectoryParameters;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.modeling.Controller;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.modeling.Output;
import edu.ucsc.cross.hse.core.variable.RandomVariable;

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
		Parameters parameters = generateParameters(true);
		// Initialize the hybrid systems
		createSystems(parameters, environment);

	}

	public static Parameters generateParameters(boolean randomize) {

		biped.hybridsystem.Parameters parameters = new biped.hybridsystem.Parameters();
		if (!randomize) {
			parameters.gravity = 9.81;
			parameters.hipMass = 1.0;
			parameters.legLength = 1.0;
			parameters.legMass = 1.0;
			parameters.stepAngle = .6;
			parameters.torsoAngle = .2;
			parameters.torsoLength = 1.0;
			parameters.torsoMass = 1.0;
			parameters.useProperTorqueRelationship = true;
			parameters.walkSpeed = 1.0;
		} else {
			parameters = biped.hybridsystem.Parameters.getTestParams();
		}
		return new Parameters(parameters);

	}

	public static State generateState(Parameters parameters, boolean randomize) {

		State state = new State(parameters);
		if (!randomize) {
			state.bipedState.plantedLegAngle = -0.2;
			state.bipedState.swingLegAngle = 0.2;
			state.bipedState.torsoAngle = 0.0;
			state.bipedState.plantedLegVelocity = 0.0;
			state.bipedState.swingLegVelocity = 0.0;
			state.bipedState.torsoVelocity = 0.0;
		} else {
			double stepAngle = parameters.bipedParams.stepAngle;
			double torsoAngle = parameters.bipedParams.torsoAngle;
			double velocity = 1.0;
			state.bipedState.plantedLegAngle = RandomVariable.generate(-.95 * stepAngle, 0.0);
			state.bipedState.swingLegAngle = RandomVariable.generate(0.0, .95 * stepAngle);
			state.bipedState.torsoAngle = RandomVariable.generate(-torsoAngle, torsoAngle);
			state.bipedState.plantedLegVelocity = RandomVariable.generate(-velocity, velocity);
			state.bipedState.swingLegVelocity = RandomVariable.generate(-velocity, velocity);
			state.bipedState.torsoVelocity = RandomVariable.generate(-velocity, velocity);
		}
		return state;
	}

	public static State generateReferenceState(Parameters parameters) {

		State state = new State(parameters.equilibParams.initialState, parameters);
		return state;
	}

	public static void createSystems(Parameters parameters, HSEnvironment environment) {

		// Initialize the state
		State referenceState = generateState(parameters, false);
		// Initialize the state
		State virtualState = generateState(parameters, false);
		// Initialize the state
		biped.hybridsystem.State plantState = generateState(parameters, false).bipedState;

		// Initialize the reference flow controller
		VirtualFlowController referenceFlowControl = new VirtualFlowController(parameters);
		// Initialize the reference jump controller
		ReferenceJumpController referenceJumpControl = new ReferenceJumpController(parameters);

		// Initialize the virtual flow controller
		VirtualFlowController virtualFlowControl = new VirtualFlowController(parameters);
		// Initialize the virtual jump controller
		VirtualJumpController virtualJumpControl = new VirtualJumpController(parameters);
		// Initialize the plant flow controller
		PlantFlowController plantFlowControl = new PlantFlowController(parameters.bipedParams);

		// Initialize the reference system
		HybridSys<State> referenceSystem = createVirtualSystem(referenceState, parameters, referenceFlowControl,
				referenceJumpControl);
		// Initialize the reference system
		HybridSys<biped.hybridsystem.State> plantSystem = createRealSystem(plantState, parameters.bipedParams,
				plantFlowControl);
		// Initialize the virtual system
		HybridSys<State> virtualSystem = createVirtualSystem(virtualState, parameters, virtualFlowControl,
				virtualJumpControl, plantSystem);

		// Connect virtual to plant
		virtualJumpControl.connectPlantSystem(plantSystem);
		// Connect virtual to reference
		virtualJumpControl.connectReferenceSystem(referenceSystem);
		// Connect plant to virtual
		plantFlowControl.connectVirtualSystem(virtualSystem);

		// Add systems to the environment
		environment.getSystems().add(referenceSystem, virtualSystem, plantSystem);
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
		HybridSys<State> system = new HybridSys<State>(
				new State(parameters.equilibParams.getInitialState(), parameters), f, g, c, d);
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
	public static HybridSys<State> createVirtualSystem(State state, Parameters parameters,
			Controller<State, Matrix> continuous_controller,
			Controller<State, TrajectoryParameters> discrete_controller, Output<biped.hybridsystem.State> plant) {

		HybridSys<State> system = createVirtualSystem(state, parameters, continuous_controller, discrete_controller);
		system.setJumpSet(new Dp(parameters, plant));
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
	public static HybridSys<biped.hybridsystem.State> createRealSystem(biped.hybridsystem.State state,
			biped.hybridsystem.Parameters parameters,
			Controller<biped.hybridsystem.State, Matrix> continuous_controller) {

		// Initialize the flow map
		biped.hybridsystem.Fp f = new biped.hybridsystem.Fp(parameters, continuous_controller);
		// Initialize the jump map
		biped.hybridsystem.Gp g = new biped.hybridsystem.Gp(parameters);
		// Initialize the flow set
		biped.hybridsystem.Cp c = new biped.hybridsystem.Cp(parameters);
		// Initialize the jump set
		biped.hybridsystem.Dp d = new biped.hybridsystem.Dp(parameters);
		// Initialize the hybrid system
		HybridSys<biped.hybridsystem.State> system = new HybridSys<biped.hybridsystem.State>(state, f, g, c, d,
				parameters);

		return system;
	}

}
