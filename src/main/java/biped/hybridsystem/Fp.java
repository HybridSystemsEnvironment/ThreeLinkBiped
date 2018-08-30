
package biped.hybridsystem;

import Jama.Matrix;
import biped.computations.BipedComputer;
import edu.ucsc.cross.hse.core.modeling.Controller;
import edu.ucsc.cross.hse.core.modeling.FlowMap;

/**
 * A flow map
 */
public class Fp implements FlowMap<State> {

	/**
	 * Parameters
	 */
	public Parameters parameters;

	public Controller<State, Matrix> controller;

	/**
	 * Constructor for flow map
	 */
	public Fp(Parameters parameters, Controller<State, Matrix> controller) {

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

		BipedComputer.computeChangeBetweenImpact(x, x_dot, parameters, controller.k(x));
	}

}
