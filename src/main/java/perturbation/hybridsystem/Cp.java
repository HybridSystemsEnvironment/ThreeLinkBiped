
package perturbation.hybridsystem;

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

		boolean inC = true; // add logic to determine if x in flow set
		return inC;
	}

}
