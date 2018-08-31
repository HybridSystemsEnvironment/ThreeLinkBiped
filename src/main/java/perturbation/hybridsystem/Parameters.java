
package perturbation.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A parameter set
 */
public class Parameters extends DataStructure {

	/**
	 * Example parameter value
	 */
	public double perturbationPercent;

	public boolean randomize;

	public biped.hybridsystem.Parameters bipedParameters;

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public Parameters(biped.hybridsystem.Parameters biped_parameters, double parameter_value, boolean randomize) {

		this.bipedParameters = biped_parameters;
		perturbationPercent = parameter_value;
		this.randomize = randomize;

	}
}
