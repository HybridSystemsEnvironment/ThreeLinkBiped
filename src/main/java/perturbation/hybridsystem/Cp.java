
package perturbation.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.FlowSet;

/**
 * A flow set
 */
public class Cp implements FlowSet<PerturbState> {

	/**
	 * Parameters
	 */
	public Parameters parameters;

	/**
	 * Constructor for flow set
	 */
	public Cp(Parameters parameters) {

		this.parameters = parameters;

	}

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean C(PerturbState x) {

		boolean inC = true; // add logic to determine if x in flow set
		return inC;
	}

}
