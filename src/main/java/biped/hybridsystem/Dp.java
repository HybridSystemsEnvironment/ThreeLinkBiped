
package biped.hybridsystem;

import biped.computations.BipedComputer;
import edu.ucsc.cross.hse.core.modeling.JumpSet;

/**
 * A jump set
 */
public class Dp implements JumpSet<State> {

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
	public boolean D(State x) {

		boolean inD = false; // add logic to determine if x in jump set
		biped.hybridsystem.State trigger = x;

		double hVal = BipedComputer.computeStepRemainder(trigger, parameters);
		if (parameters.connections.getPerturbation() != null) {
			hVal = BipedComputer.computeStepRemainder(trigger, parameters, true);
		}
		inD = hVal <= 0.0;
		return inD;
	}

}
