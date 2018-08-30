
package biped.reference.control;

import biped.application.Controller;
import biped.computations.BipedComputer;
import biped.hybridsystem.Link;
import biped.virtual.hybridsystem.Parameters;
import biped.virtual.hybridsystem.State;
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
		SubNode<State, biped.hybridsystem.State> node2 = Network.getGlobal().getSubNode(x,
				biped.hybridsystem.State.class, Link.PLANT_TO_VIRTUAL.toString());
		// System.out.println(XMLParser.serializeObject(node));
		biped.hybridsystem.State plant = node2.getOutgoing().get(0).getTarget();
		biped.hybridsystem.State x0 = BipedComputer.computeChangeAtImpact(plant, parameters.bipedParams);
		biped.hybridsystem.State xf = parameters.equilibParams.getFinalState();
		double tPlus = BipedComputer.computeTimeToNextImpactStep(ref, parameters.bipedParams);
		TrajectoryParameters params = TrajectoryParameters.compute(x0, xf, tPlus, parameters.bipedParams);
		return params;
	}

}
