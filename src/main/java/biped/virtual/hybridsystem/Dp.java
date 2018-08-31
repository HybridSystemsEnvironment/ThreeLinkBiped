
package biped.virtual.hybridsystem;

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
		biped.hybridsystem.State trigger = x.bipedState;
		double hVal = BipedComputer.computeStepRemainder(trigger, parameters.bipedParams);
		if (!x.equals(parameters.bipedParams.connections.getReference().y())) {
			trigger = parameters.bipedParams.connections.getInput().y();

			hVal = BipedComputer.computeStepRemainder(trigger, parameters.bipedParams, true);

		}
		inD = inD || hVal <= 0.0;
		return inD;
	}

}
