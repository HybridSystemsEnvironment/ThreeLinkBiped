
package biped.hybridsystem;

import biped.computations.BipedComputer;
import edu.ucsc.cross.hse.core.modeling.JumpMap;

/**
 * A jump map
 */
public class Gp implements JumpMap<BipedState> {

	/**
	 * Parameters
	 */
	public Parameters parameters;

	/**
	 * Constructor for jump map
	 */
	public Gp(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_plus
	 *            state update values
	 */
	@Override
	public void G(BipedState x, BipedState x_plus) {

		BipedComputer.computeChangeAtImpact(x, x_plus, parameters);
	}

}
