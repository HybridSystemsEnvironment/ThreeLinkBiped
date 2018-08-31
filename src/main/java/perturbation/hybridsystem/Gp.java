
package perturbation.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.JumpMap;
import edu.ucsc.cross.hse.core.variable.RandomVariable;

/**
 * A jump map
 */
public class Gp implements JumpMap<PerturbState> {

	/**
	 * Parameters
	 */
	public Parameters parameters;

	/**
	 * Constructor for jump map
	 */
	public Gp(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_plus
	 *            state update values
	 */
	@Override
	public void G(PerturbState x, PerturbState x_plus) {

		double perturbationAngle = parameters.perturbationPercent * parameters.bipedParameters.stepAngle;
		if (parameters.randomize) {
			perturbationAngle = RandomVariable.generate(-perturbationAngle, perturbationAngle);
		}
		x_plus.perturbationAngle = perturbationAngle;
	}

}
