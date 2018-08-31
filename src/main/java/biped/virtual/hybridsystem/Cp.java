
package biped.virtual.hybridsystem;

import biped.computations.BipedComputer;
import biped.parameters.virtual.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.FlowSet;

/**
 * A flow set
 */
public class Cp implements FlowSet<State, Parameters> {

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(State x, Parameters parameters) {

		boolean inC = false;
		double hVal = BipedComputer.computeStepRemainder(x.bipedState, parameters.bipedParams.bipedParams);
		inC = hVal >= 0.0;

		return inC;
	}

}
