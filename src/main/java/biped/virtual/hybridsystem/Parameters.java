
package biped.virtual.hybridsystem;

import biped.parameters.virtual.State;
import biped.plant.hybridsystem.Port;
import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A parameter set
 */
public class Parameters extends DataStructure {

	public biped.parameters.virtual.Parameters bipedParams;

	public Port<State> reference;

	public Port<biped.parameters.base.State> plant;

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public Parameters(biped.parameters.base.Parameters traj_params) {

		bipedParams = new biped.parameters.virtual.Parameters(traj_params);
		plant = new Port<biped.parameters.base.State>();
		reference = new Port<State>();
		this.getProperties().setStoreTrajectory(true);
	}
}
