
package biped.plant.hybridsystem;

import biped.parameters.base.State;
import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A parameter set
 */
public class Parameters extends DataStructure {

	public biped.parameters.base.Parameters bipedParams;

	public Port<perturbation.hybridsystem.State> perturbation;

	public Port<biped.parameters.virtual.State> virtual;

	public static State getBipedState(State state) {

		return new State(state.plantedLegAngle, state.swingLegAngle, state.torsoAngle, state.plantedLegVelocity,
				state.swingLegVelocity, state.torsoVelocity);
	}

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public Parameters(biped.parameters.base.Parameters traj_params) {

		bipedParams = traj_params;
		virtual = new Port<biped.parameters.virtual.State>();
		perturbation = new Port<perturbation.hybridsystem.State>();
	}
}
