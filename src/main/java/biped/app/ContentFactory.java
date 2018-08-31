
package biped.app;

import Jama.Matrix;
import biped.parameters.base.TrajectoryParameters;
import biped.parameters.virtual.Parameters;
import biped.parameters.virtual.State;
import edu.ucsc.cross.hse.core.modeling.Controller;

public class ContentFactory {

	public static biped.parameters.virtual.State generateState(Parameters parameters, Double planted_angle,
			Double swing_angle, Double torso_angle, Double planted_velocity, Double swing_velocity,
			Double torso_velocity) {

		State state = new State(parameters, planted_angle, swing_angle, torso_angle, planted_velocity, swing_velocity,
				torso_velocity);
		return state;
	}

	public static Parameters generateParameters(boolean randomize) {

		biped.parameters.base.Parameters parameters = new biped.parameters.base.Parameters();
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
			parameters = biped.parameters.base.Parameters.getTestParams();
		}
		return new Parameters(parameters);

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
	public static HybridSystem<biped.parameters.base.State, biped.plant.hybridsystem.Parameters> createRealSystem(
			biped.parameters.base.State state, biped.plant.hybridsystem.Parameters parameters,
			Controller<biped.parameters.base.State, Matrix> continuous_controller) {

		state.getProperties().setName("Physical");

		// Initialize the flow map
		biped.plant.hybridsystem.Fp f = new biped.plant.hybridsystem.Fp(continuous_controller);
		// Initialize the jump map
		biped.plant.hybridsystem.Gp g = new biped.plant.hybridsystem.Gp();
		// Initialize the flow set
		biped.plant.hybridsystem.Cp c = new biped.plant.hybridsystem.Cp();
		// Initialize the jump set
		biped.plant.hybridsystem.Dp d = new biped.plant.hybridsystem.Dp();
		// Initialize the hybrid system
		HybridSystem<biped.parameters.base.State, biped.plant.hybridsystem.Parameters> system = new HybridSystem<biped.parameters.base.State, biped.plant.hybridsystem.Parameters>(
				state, parameters, f, g, c, d);
		return system;
	}

	public static HybridSystem<biped.parameters.virtual.State, biped.virtual.hybridsystem.Parameters> createVirtualSystem(
			biped.parameters.virtual.State state, biped.virtual.hybridsystem.Parameters parameters,
			Controller<biped.parameters.virtual.State, Matrix> continuous_controller,
			Controller<biped.parameters.virtual.State, TrajectoryParameters> discrete_controller) {

		state.bipedState.getProperties().setName("Virtual");

		// Initialize the flow map
		biped.virtual.hybridsystem.Fp f = new biped.virtual.hybridsystem.Fp(continuous_controller);
		// Initialize the jump map
		biped.virtual.hybridsystem.Gp g = new biped.virtual.hybridsystem.Gp(discrete_controller);
		// Initialize the flow set
		biped.virtual.hybridsystem.Cp c = new biped.virtual.hybridsystem.Cp();
		// Initialize the jump set
		biped.virtual.hybridsystem.Dp d = new biped.virtual.hybridsystem.Dp();
		// Initialize the hybrid system
		HybridSystem<biped.parameters.virtual.State, biped.virtual.hybridsystem.Parameters> system = new HybridSystem<biped.parameters.virtual.State, biped.virtual.hybridsystem.Parameters>(
				state, parameters, f, g, c, d);
		return system;
	}

	public static HybridSystem<biped.parameters.virtual.State, biped.parameters.virtual.Parameters> createReferenceSystem(
			biped.parameters.virtual.State state, biped.parameters.virtual.Parameters parameters,
			Controller<biped.parameters.virtual.State, Matrix> continuous_controller,
			Controller<biped.parameters.virtual.State, TrajectoryParameters> discrete_controller) {

		state.bipedState.getProperties().setName("Reference");

		// Initialize the flow map
		biped.reference.hybridsystem.Fp f = new biped.reference.hybridsystem.Fp(continuous_controller);
		// Initialize the jump map
		biped.reference.hybridsystem.Gp g = new biped.reference.hybridsystem.Gp(discrete_controller);
		// Initialize the flow set
		biped.reference.hybridsystem.Cp c = new biped.reference.hybridsystem.Cp();
		// Initialize the jump set
		biped.reference.hybridsystem.Dp d = new biped.reference.hybridsystem.Dp();
		// Initialize the hybrid system
		HybridSystem<biped.parameters.virtual.State, biped.parameters.virtual.Parameters> system = new HybridSystem<biped.parameters.virtual.State, biped.parameters.virtual.Parameters>(
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
}
