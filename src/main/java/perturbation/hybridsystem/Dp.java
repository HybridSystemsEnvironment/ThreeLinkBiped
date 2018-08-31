
package perturbation.hybridsystem;

import edu.ucsc.cross.hse.core.hybridsystem.input.JumpSet;

/**
 * A jump set
 */
public class Dp implements JumpSet<State, Parameters> {

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean D(State x, Parameters parameters) {

		biped.parameters.base.State trigger = parameters.plant.y().getState();
		boolean inD = parameters.plant.y().D(trigger); // add logic to determine if x in

		return inD;
	}

}
