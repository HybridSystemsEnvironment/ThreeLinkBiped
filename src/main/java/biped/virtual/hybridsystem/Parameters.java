
package biped.virtual.hybridsystem;

import biped.hybridsystem.State;
import edu.ucsc.cross.hse.core.modeling.DataStructure;
import edu.ucsc.hsl.hse.model.biped.threelink.computors.EquilibriumComputer;

/**
 * A parameter set
 */
public class Parameters extends DataStructure {

	public biped.hybridsystem.Parameters bipedParams;

	public TrajectoryParameters equilibParams;

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
	public Parameters(biped.hybridsystem.Parameters traj_params) {

		bipedParams = traj_params;
		equilibParams = EquilibriumComputer.getEquilibriumParameters(bipedParams);
	}
}
