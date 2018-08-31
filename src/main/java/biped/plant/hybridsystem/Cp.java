
package biped.plant.hybridsystem;

import biped.computations.BipedComputer;
import biped.parameters.base.State;
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

		boolean inC = true;
		double hVal = BipedComputer.computeStepRemainder(x, parameters.bipedParams, parameters.perturbation);
		inC = inC || hVal >= 0.0;
		return inC;
	}

}
