
package net.biped.virtual.hybridsystem;

import biped.computations.BipedComputer;
import edu.ucsc.cross.hse.core.modeling.FlowSet;

/**
 * A flow set
 */
public class Cp implements FlowSet<State> {

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
	public boolean C(State x) {

		boolean inC = true;
		double hVal = BipedComputer.computeStepRemainder(x.bipedState, parameters.bipedParams);
		inC = hVal >= 0.0;
		return inC;
	}

}
