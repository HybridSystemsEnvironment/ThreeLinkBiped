
package net.biped.reference.control;

import biped.application.Controller;
import biped.computations.BipedComputer;
import biped.parameters.virtual.Parameters;
import biped.parameters.virtual.State;
import biped.virtual.hybridsystem.TrajectoryParameters;

public class DiscreteController implements Controller<State, TrajectoryParameters> {

	Parameters parameters;

	/**
	 * Constructor for discrete reference controller
	 * 
	 * @param parameters
	 */
	public DiscreteController(Parameters parameters) {

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

}
