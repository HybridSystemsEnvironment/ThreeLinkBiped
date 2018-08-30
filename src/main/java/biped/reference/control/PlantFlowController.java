
package biped.reference.control;

import Jama.Matrix;
import biped.computations.BipedComputer;
import biped.hybridsystem.Parameters;
import biped.hybridsystem.State;
import edu.ucsc.cross.hse.core.modeling.Controller;
import edu.ucsc.cross.hse.core.modeling.Output;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedMotion;

public class PlantFlowController implements Controller<biped.hybridsystem.State, Matrix> {

	public Parameters parameters;

	public Double kOne;

	public Double kTwo;

	Output<biped.virtual.hybridsystem.State> virtual;

	public PlantFlowController(Parameters parameters) {

		this(parameters, 2000.0, 100.0);
	}

	public PlantFlowController(Parameters parameters, double k_one, double k_two) {

		this.parameters = parameters;
		kOne = (2000.0);
		kTwo = (100.0);
	}

	public Matrix computeControlInput(State realBiped, Matrix accelerations) {

		Matrix matD = BipedComputer.computeFlowDMatrix(realBiped, parameters);
		Matrix matC = BipedComputer.computeFlowCMatrix(realBiped, parameters);
		Matrix matG = BipedComputer.computeFlowGMatrix(realBiped, parameters);
		Matrix vels = realBiped.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = parameters.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
				.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public Matrix getComputedAcceleration(State realBiped, State virtualBiped) {

		double[][] positionDifference = { { realBiped.plantedLegAngle - virtualBiped.plantedLegAngle },
				{ realBiped.swingLegAngle - virtualBiped.swingLegAngle },
				{ realBiped.torsoAngle - virtualBiped.torsoAngle } };
		Matrix deltaPos = new Matrix(positionDifference);
		double[][] velocityDifference = { { realBiped.plantedLegVelocity - virtualBiped.plantedLegVelocity },
				{ realBiped.swingLegVelocity - virtualBiped.swingLegVelocity },
				{ realBiped.torsoVelocity - virtualBiped.torsoVelocity } };
		Matrix deltaVel = new Matrix(velocityDifference);
		deltaPos = deltaPos.times(-kOne);
		deltaVel = deltaVel.times(-kTwo);
		// TODO Auto-generated method stub
		return deltaPos.plusEquals(deltaVel);
	}

	@Override
	public Matrix k(State state) {

		Matrix virtualAcceleration = VirtualFlowController.computeOrbitTrackingAccelerations(virtual.y());
		Matrix controlAccel = virtualAcceleration.plusEquals(getComputedAcceleration(state, virtual.y().bipedState));

		return computeControlInput(state, controlAccel);
	}

	/**
	 * @param virtual
	 *            the virtual to set
	 */
	public void connectVirtualSystem(Output<biped.virtual.hybridsystem.State> virtual) {

		this.virtual = virtual;
	}
}