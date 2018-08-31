
package biped.reference.control;

import biped.computations.BipedComputer;
import biped.parameters.base.TrajectoryParameters;
import biped.parameters.virtual.State;
import biped.virtual.hybridsystem.Parameters;
import edu.ucsc.cross.hse.core.modeling.Controller;

public class VirtualJumpController implements Controller<State, TrajectoryParameters> {

	Parameters parameters;

	/**
	 * Constructor for discrete reference controller
	 * 
	 * @param parameters
	 */
	public VirtualJumpController(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Discrete update law kappa_r for reference system
	 */
	@Override
	public TrajectoryParameters k(State x) {

		biped.parameters.base.State plant = parameters.plant.y();
		biped.parameters.base.State x0 = BipedComputer.computeChangeAtImpact(plant, parameters.bipedParams.bipedParams);
		biped.parameters.base.State xf = parameters.bipedParams.equilibParams.getFinalState();
		double tPlus = BipedComputer.computeTimeToNextImpactStep(parameters.reference.y(),
				parameters.bipedParams.bipedParams);
		TrajectoryParameters params = TrajectoryParameters.compute(x0, xf, tPlus, parameters.bipedParams.bipedParams);
		return params;
	}

	@Override
	public TrajectoryParameters u() {

		// TODO Auto-generated method stub
		return null;
	}

}
