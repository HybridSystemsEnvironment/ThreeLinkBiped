
package biped.hybridsystem;

import biped.computation.ComputationEngine;
import edu.ucsc.cross.hse.core.modeling.JumpSet;

/**
 * Jump set implementation.
 */
public class Dp implements JumpSet<State> {

	public Parameters parameters;

	public Dp(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Jump set computation
	 */
	@Override
	public boolean D(State x) {

		double hVal = ComputationEngine.computeStepDetectH(x, parameters);
		return hVal <= 0.0 && x.plantedLegVelocity >= 0.0;
	}

}
