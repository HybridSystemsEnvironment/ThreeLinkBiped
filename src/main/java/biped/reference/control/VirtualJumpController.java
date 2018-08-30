
package biped.reference.control;

import biped.computations.BipedComputer;
import biped.virtual.hybridsystem.Parameters;
import biped.virtual.hybridsystem.State;
import biped.virtual.hybridsystem.TrajectoryParameters;
import edu.ucsc.cross.hse.core.modeling.Controller;
import edu.ucsc.cross.hse.core.modeling.Output;

public class VirtualJumpController implements Controller<State, TrajectoryParameters> {

	Parameters parameters;

	/**
	 * Reference input
	 */
	Output<State> reference;

	/**
	 * Reference input
	 */
	Output<biped.hybridsystem.State> plant;

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

		biped.hybridsystem.State plant = this.plant.y();
		biped.hybridsystem.State x0 = BipedComputer.computeChangeAtImpact(plant, parameters.bipedParams);
		biped.hybridsystem.State xf = parameters.equilibParams.getFinalState();
		double tPlus = BipedComputer.computeTimeToNextImpactStep(reference.y(), parameters.bipedParams);
		TrajectoryParameters params = TrajectoryParameters.compute(x0, xf, tPlus, parameters.bipedParams);
		return params;
	}

	/**
	 * @param reference
	 *            the reference to set
	 */
	public void connectReferenceSystem(Output<State> reference) {

		this.reference = reference;
	}

	/**
	 * @param plant
	 *            the plant to set
	 */
	public void connectPlantSystem(Output<biped.hybridsystem.State> plant) {

		this.plant = plant;
	}

}
