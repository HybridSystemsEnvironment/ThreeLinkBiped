
package net.biped.reference.control;

import biped.application.Controller;
import biped.computations.BipedComputer;
import biped.parameters.virtual.Parameters;
import biped.parameters.virtual.State;
import biped.plant.hybridsystem.Link;
import biped.virtual.hybridsystem.TrajectoryParameters;
import edu.ucsc.cross.hse.core.network.Network;
import edu.ucsc.cross.hse.core.network.SubNode;

public class VirtualDiscreteController implements Controller<State, TrajectoryParameters> {

	Parameters parameters;

	/**
	 * Constructor for discrete reference controller
	 * 
	 * @param parameters
	 */
	public VirtualDiscreteController(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Discrete update law kappa_r for reference system
	 */
	@Override
	public TrajectoryParameters k(State x) {

		SubNode<State, State> node = Network.getGlobal().getSubNode(x, State.class,
				Link.REFERENCE_TO_VIRTUAL.toString());
		// System.out.println(XMLParser.serializeObject(node));
		State ref = node.getOutgoing().get(0).getTarget();
		SubNode<State, biped.parameters.base.State> node2 = Network.getGlobal().getSubNode(x,
				biped.parameters.base.State.class, Link.PLANT_TO_VIRTUAL.toString());
		// System.out.println(XMLParser.serializeObject(node));
		biped.parameters.base.State plant = node2.getOutgoing().get(0).getTarget();
		biped.parameters.base.State x0 = BipedComputer.computeChangeAtImpact(plant, parameters.bipedParams);
		biped.parameters.base.State xf = parameters.equilibParams.getFinalState();
		double tPlus = BipedComputer.computeTimeToNextImpactStep(ref, parameters.bipedParams);
		TrajectoryParameters params = TrajectoryParameters.compute(x0, xf, tPlus, parameters.bipedParams);
		return params;
	}

}
