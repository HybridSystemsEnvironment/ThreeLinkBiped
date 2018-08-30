
package net.biped.virtual.hybridsystem;

import biped.application.Controller;
import biped.computations.BipedComputer;
import biped.hybridsystem.Link;
import edu.ucsc.cross.hse.core.modeling.JumpMap;
import edu.ucsc.cross.hse.core.network.Network;
import edu.ucsc.cross.hse.core.network.SubNode;

/**
 * A jump map
 */
public class Gp implements JumpMap<State> {

	/**
	 * Parameters
	 */
	public Parameters parameters;

	/**
	 * Controller
	 */
	Controller<State, TrajectoryParameters> controller;

	/**
	 * Constructor for jump map
	 */
	public Gp(Controller<State, TrajectoryParameters> controller, Parameters parameters) {

		this.controller = controller;
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
	public void G(State x, State x_plus) {

		SubNode<State, biped.hybridsystem.State> node = Network.getGlobal().getSubNode(x,
				biped.hybridsystem.State.class, Link.PLANT_TO_VIRTUAL.toString());
		// System.out.println(XMLParser.serializeObject(node));
		State trigger = node.getOutgoing().get(0).getSource();
		TrajectoryParameters newTrajParams = controller.k(trigger);
		BipedComputer.computeChangeAtImpact(x.bipedState, x_plus.bipedState, parameters.bipedParams);
		x_plus.trajectoryParameters.B0 = newTrajParams.B0;
		x_plus.trajectoryParameters.B1 = newTrajParams.B1;
		x_plus.trajectoryParameters.finalState = newTrajParams.getFinalState();
		x_plus.trajectoryParameters.initialState = newTrajParams.getInitialState();
		x_plus.trajTimer = 0.0;

	}

}
