
package biped.virtual.hybridsystem;

import Jama.Matrix;
import biped.computations.BipedComputer;
import biped.hybridsystem.Controller;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedLimb;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedMotion;

public class BipedReferenceControl implements Controller {

	public State virtualBiped;

	public Parameters parameters;

	public BipedReferenceControl(State real_biped, Parameters parameters) {

		virtualBiped = real_biped;
		this.parameters = parameters;
	}

	@Override
	public Matrix getInputTorques() {

		Matrix control = computeControlInput(getOrbitTrackingAccelerations());
		return control;
	}

	public Matrix computeControlInput(Matrix accelerations) {

		Matrix matD = BipedComputer.computeFlowDMatrix(virtualBiped, parameters.bipedParams);
		Matrix matC = BipedComputer.computeFlowCMatrix(virtualBiped, parameters.bipedParams);
		Matrix matG = BipedComputer.computeFlowGMatrix(virtualBiped, parameters.bipedParams);
		Matrix vels = virtualBiped.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = parameters.bipedParams.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
				.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public Matrix getOrbitTrackingAccelerations() {

		double[][] mat = new double[3][1];
		Double currTime = virtualBiped.trajTimer;
		Integer index = 0;

		mat[0][0] = virtualBiped.trajParams.getB0(BipedLimb.PLANTED_LEG)
				- virtualBiped.trajParams.getB1(BipedLimb.PLANTED_LEG) * currTime;
		mat[1][0] = virtualBiped.trajParams.getB0(BipedLimb.SWING_LEG)
				- virtualBiped.trajParams.getB1(BipedLimb.SWING_LEG) * currTime;
		mat[2][0] = virtualBiped.trajParams.getB0(BipedLimb.TORSO)
				- virtualBiped.trajParams.getB1(BipedLimb.TORSO) * currTime;
		index++;

		Matrix accel = new Matrix(mat);
		return accel;
	}

	public Matrix getComputedAcceleration() {

		return getOrbitTrackingAccelerations();
	}

	public void recomputeTrajectoryParams() {

		virtualBiped.trajParams = new TrajectoryParameters(virtualBiped, virtualBiped.trajParams.getFinalState(),
				parameters.bipedParams);
	}

}
