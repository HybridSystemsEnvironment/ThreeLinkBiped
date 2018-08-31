
package biped.parameters.virtual;

import biped.computations.EquilibriumComputer;
import biped.parameters.base.TrajectoryParameters;
import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A parameter set
 */
public class Parameters extends DataStructure {

	public biped.parameters.base.Parameters bipedParams;

	public TrajectoryParameters equilibParams;

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public Parameters(biped.parameters.base.Parameters traj_params) {

		bipedParams = traj_params;

		equilibParams = EquilibriumComputer.getEquilibriumParameters(bipedParams);
		this.getProperties().setStoreTrajectory(false);

	}
}
