
package biped.application;

import Jama.Matrix;
import biped.parameters.TrajectoryParameters;
import biped.parameters.virtual.Parameters;
import biped.parameters.virtual.State;
import biped.reference.control.PlantFlowController;
import biped.reference.control.ReferenceJumpController;
import biped.reference.control.VirtualFlowController;
import biped.reference.control.VirtualJumpController;
import biped.virtual.hybridsystem.Cp;
import biped.virtual.hybridsystem.Dp;
import biped.virtual.hybridsystem.Fp;
import biped.virtual.hybridsystem.Gp;
import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.hybridsystem.input.HybridSystem;
import edu.ucsc.cross.hse.core.modeling.Controller;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
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

		createPerturbationSystem(parameters, environment, .3, true);

	}

	public static Parameters generateParameters(boolean randomize) {

		biped.parameters.Parameters parameters = new biped.parameters.Parameters();
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
			parameters = biped.parameters.Parameters.getTestParams();
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

	public static void createSystems(Parameters parameters, HSEnvironment environment) {

		// Initialize the state
		State referenceState = new State(parameters.equilibParams.getInitialState(), parameters);
		// Initialize the state
		State virtualState = generateState(parameters, false);

		referenceState.bipedState.getProperties().setName("Reference");

		virtualState.bipedState.getProperties().setName("Virtual");
		// Initialize the state
		biped.parameters.State plantState = generateState(parameters, false).bipedState;

		plantState.getProperties().setName("Physical");
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
		HybridSys<biped.parameters.State> plantSystem = createRealSystem(plantState, parameters.bipedParams,
				plantFlowControl);
		// Initialize the virtual system
		HybridSys<State> virtualSystem = createVirtualSystem(virtualState, parameters, virtualFlowControl,
				virtualJumpControl);

		parameters.bipedParams.connections.setConnections(null, plantSystem, referenceSystem, virtualSystem,
				plantSystem);

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
		HybridSys<State> system = new HybridSys<State>(state, f, g, c, d);
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
	public static HybridSystem<biped.parameters.State, biped.plant.hybridsystem.Parameters> createRealSystem(
			biped.parameters.State state, biped.plant.hybridsystem.Parameters parameters,
			Controller<biped.parameters.State, Matrix> continuous_controller) {

		// Initialize the flow map
		biped.plant.hybridsystem.Fp f = new biped.plant.hybridsystem.Fp(continuous_controller);
		// Initialize the jump map
		biped.plant.hybridsystem.Gp g = new biped.plant.hybridsystem.Gp();
		// Initialize the flow set
		biped.plant.hybridsystem.Cp c = new biped.plant.hybridsystem.Cp();
		// Initialize the jump set
		biped.plant.hybridsystem.Dp d = new biped.plant.hybridsystem.Dp();
		// Initialize the hybrid system
		HybridSystem<biped.parameters.State, biped.plant.hybridsystem.Parameters> system = new HybridSystem<biped.parameters.State, biped.plant.hybridsystem.Parameters>(
				state, parameters, f, g, c, d);
		return system;
	}

	public static HybridSystem<perturbation.hybridsystem.State, perturbation.hybridsystem.Parameters> createPerturbationSystem(
			perturbation.hybridsystem.State state, perturbation.hybridsystem.Parameters parameters) {

		// Initialize the flow map
		perturbation.hybridsystem.Fp f = new perturbation.hybridsystem.Fp();
		// Initialize the jump map
		perturbation.hybridsystem.Gp g = new perturbation.hybridsystem.Gp();
		// Initialize the flow set
		perturbation.hybridsystem.Cp c = new perturbation.hybridsystem.Cp();
		// Initialize the jump set
		perturbation.hybridsystem.Dp d = new perturbation.hybridsystem.Dp();
		// Initialize the hybrid system
		HybridSystem<perturbation.hybridsystem.State, perturbation.hybridsystem.Parameters> system = new HybridSystem<perturbation.hybridsystem.State, perturbation.hybridsystem.Parameters>(
				state, parameters, f, g, c, d);
		return system;
	}

	public static HybridSystem<biped.parameters.virtual.State, biped.reference.hybridsystem.Parameters> createReferenceSystem(
			biped.parameters.virtual.State state, biped.reference.hybridsystem.Parameters parameters,
			Controller<biped.parameters.virtual.State, Matrix> continuous_controller,
			Controller<biped.parameters.virtual.State, TrajectoryParameters> discrete_controller) {

		// Initialize the flow map
		biped.reference.hybridsystem.Fp f = new biped.reference.hybridsystem.Fp(continuous_controller);
		// Initialize the jump map
		biped.reference.hybridsystem.Gp g = new biped.reference.hybridsystem.Gp(discrete_controller);
		// Initialize the flow set
		biped.reference.hybridsystem.Cp c = new biped.reference.hybridsystem.Cp();
		// Initialize the jump set
		biped.reference.hybridsystem.Dp d = new biped.reference.hybridsystem.Dp();
		// Initialize the hybrid system
		HybridSystem<biped.parameters.virtual.State, biped.reference.hybridsystem.Parameters> system = new HybridSystem<biped.parameters.virtual.State, biped.reference.hybridsystem.Parameters>(
				state, parameters, f, g, c, d);
		return system;
	}

	public static void createPerturbationSystem(Parameters parameters, HSEnvironment environment,
			double perturb_percent, boolean randomize) {

		HybridSys<perturbation.hybridsystem.State> perturb = createPerturbationSystem(parameters.bipedParams,
				perturb_percent, randomize);

		parameters.bipedParams.connections.setPerturbation(perturb);

		// Add systems to the environment
		environment.getSystems().add(perturb);
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
	public static HybridSys<perturbation.hybridsystem.State> createPerturbationSystem(
			biped.parameters.Parameters parameters, double perturb_percent, boolean randomize) {

		perturbation.hybridsystem.Parameters params = new perturbation.hybridsystem.Parameters(parameters,
				perturb_percent, randomize);
		perturbation.hybridsystem.State state = new perturbation.hybridsystem.State(perturb_percent);
		// Initialize the flow map
		perturbation.hybridsystem.Fp f = new perturbation.hybridsystem.Fp(params);
		// Initialize the jump map
		perturbation.hybridsystem.Gp g = new perturbation.hybridsystem.Gp(params);
		// Initialize the flow set
		perturbation.hybridsystem.Cp c = new perturbation.hybridsystem.Cp(params);
		// Initialize the jump set
		perturbation.hybridsystem.Dp d = new perturbation.hybridsystem.Dp(params);
		// Initialize the hybrid system
		HybridSys<perturbation.hybridsystem.State> system = new HybridSys<perturbation.hybridsystem.State>(state, f, g,
				c, d, parameters);

		return system;
	}
}
