
package biped.plant.hybridsystem;

import biped.computations.BipedComputer;
import biped.parameters.base.State;
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

		boolean inD = false; // add logic to determine if x in jump set
		biped.parameters.base.State trigger = x;

		double hVal = BipedComputer.computeStepRemainder(trigger, parameters.bipedParams, parameters.perturbation);
		inD = hVal <= 0.0 && x.plantedLegVelocity > 0.0;
		return inD;
	}

}
