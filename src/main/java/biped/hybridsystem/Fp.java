
package biped.hybridsystem;

import biped.computations.BipedComputer;
import edu.ucsc.cross.hse.core.modeling.FlowMap;

/**
 * A flow map
 */
public class Fp implements FlowMap<BipedState> {

	/**
	 * Parameters
	 */
	public Parameters parameters;

	public Controller controller;

	/**
	 * Constructor for flow map
	 */
	public Fp(Parameters parameters, Controller controller) {

		this.parameters = parameters;
		this.controller = controller;
	}

	/**
	 * Flow map
	 * 
	 * @param x
	 *            current state
	 * @param x_dot
	 *            state derivative values
	 */
	@Override
	public void F(BipedState x, BipedState x_dot) {

		BipedComputer.computeChangeBetweenImpact(x, x_dot, parameters, controller);
	}

}
