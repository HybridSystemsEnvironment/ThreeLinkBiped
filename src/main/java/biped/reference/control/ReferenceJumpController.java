
package biped.reference.control;

import biped.computations.BipedComputer;
import biped.virtual.hybridsystem.Parameters;
import biped.virtual.hybridsystem.State;
import biped.virtual.hybridsystem.TrajectoryParameters;
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

		biped.hybridsystem.State x0 = BipedComputer.computeChangeAtImpact(x.bipedState, parameters.bipedParams);
		biped.hybridsystem.State xf = parameters.equilibParams.getFinalState();
		double tPlus = BipedComputer.computeStepTime(parameters.bipedParams);
		TrajectoryParameters params = TrajectoryParameters.compute(x0, xf, tPlus, parameters.bipedParams);
		return params;
	}

}
