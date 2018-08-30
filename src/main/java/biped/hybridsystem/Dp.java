
package biped.hybridsystem;

import biped.computations.BipedComputer;
import edu.ucsc.cross.hse.core.modeling.JumpSet;
import edu.ucsc.hsl.hse.model.biped.threelink.factories.Zero;

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
		double hVal = BipedComputer.computeStepRemainder(x, parameters);
		inD = Zero.equal(hVal) && x.plantedLegVelocity >= 0.0;
		return inD;
	}

}
