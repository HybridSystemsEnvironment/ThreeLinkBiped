
package perturbation.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A state
 */
public class PerturbState extends DataStructure {

	/**
	 * Example state value
	 */
	public double perturbationAngle;

	/**
	 * Example state value
	 */
	public double previousPerturbationAngle;

	/**
	 * Construct the states
	 * 
	 * @param state_value_1
	 *            value of state 1
	 * @param state_value_1
	 *            value of state 2
	 */
	public PerturbState(double default_state_value) {

		perturbationAngle = default_state_value;
		this.getProperties().setName("perturbation");
	}

}
