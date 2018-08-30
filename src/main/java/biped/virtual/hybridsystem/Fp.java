
package biped.virtual.hybridsystem;

import biped.computations.BipedComputer;
import biped.hybridsystem.Controller;
import edu.ucsc.cross.hse.core.modeling.FlowMap;

/**
 * A flow map
 */
public class Fp implements FlowMap<State> {

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
	public void F(State x, State x_dot) {

		x_dot.trajTimer = 1.0;
		BipedComputer.computeChangeBetweenImpact(x, x_dot, parameters.bipedParams, controller);
		x_dot.bip.value = 1.0;
	}

}
