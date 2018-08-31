
package biped.reference.control;

import biped.computations.BipedComputer;
import biped.parameters.base.TrajectoryParameters;
import biped.parameters.virtual.Parameters;
import biped.parameters.virtual.State;
import edu.ucsc.cross.hse.core.modeling.Controller;

public class ReferenceJumpController implements Controller<State, TrajectoryParameters> {

	Parameters parameters;

	/**
	 * Constructor for discrete reference controller
	 * 
	 * @param parameters
	 */
	public ReferenceJumpController(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Discrete update law kappa_r for reference system
	 */
	@Override
	public TrajectoryParameters k(State x) {

		biped.parameters.base.State x0 = BipedComputer.computeChangeAtImpact(x.bipedState, parameters.bipedParams);
		biped.parameters.base.State xf = parameters.equilibParams.getFinalState();
		double tPlus = BipedComputer.computeStepTime(parameters.bipedParams);
		TrajectoryParameters params = TrajectoryParameters.compute(x0, xf, tPlus, parameters.bipedParams);
		return params;
	}

	@Override
	public TrajectoryParameters u() {

		// TODO Auto-generated method stub
		return null;
	}

}
