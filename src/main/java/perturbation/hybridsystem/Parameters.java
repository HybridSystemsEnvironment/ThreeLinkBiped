
package perturbation.hybridsystem;

import biped.plant.hybridsystem.Port;
import edu.ucsc.cross.hse.core.hybridsystem.input.HybridSystem;
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

	public biped.parameters.base.Parameters bipedParameters;

	public Port<HybridSystem<biped.parameters.base.State, biped.plant.hybridsystem.Parameters>> plant;

	/**
	 * Construct the parameters
	 * 
	 * @param parameter_value
	 *            value to set
	 * 
	 */
	public Parameters(biped.parameters.base.Parameters biped_parameters, double parameter_value, boolean randomize) {

		this.bipedParameters = biped_parameters;
		perturbationPercent = parameter_value;
		this.randomize = randomize;
		plant = new Port<HybridSystem<biped.parameters.base.State, biped.plant.hybridsystem.Parameters>>();

	}
}
