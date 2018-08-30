
package biped.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.FlowSet;

/**
 * Flow set implementation
 */
public class Cp implements FlowSet<State> {

	public Parameters parameters;

	public Cp(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Jump set computation
	 */
	@Override
	public boolean C(State x) {

		double hVal = parameters.stepAngle - x.plantedLegAngle;
		return hVal >= 0.0;
	}

}
