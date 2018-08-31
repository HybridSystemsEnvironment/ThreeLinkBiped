
package perturbation.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.JumpSet;

/**
 * A jump set
 */
public class Dp implements JumpSet<PerturbState> {

	/**
	 * Parameters
	 */
	public Parameters parameters;

	/**
	 * Constructor for jump set
	 */
	public Dp(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean D(PerturbState x) {

		biped.hybridsystem.State trigger = parameters.bipedParameters.connections.getPlant().getState();

		boolean inD = parameters.bipedParameters.connections.getPlant().D(trigger); // add logic to determine if x in

		return inD;
	}

}
