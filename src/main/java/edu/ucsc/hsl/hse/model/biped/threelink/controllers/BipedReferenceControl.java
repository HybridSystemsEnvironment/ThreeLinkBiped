package edu.ucsc.hsl.hse.model.biped.threelink.controllers;

import Jama.Matrix;
import edu.ucsc.hsl.hse.model.biped.threelink.computors.BipedComputer;
import edu.ucsc.hsl.hse.model.biped.threelink.computors.EquilibriumComputer;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.TrajectoryParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedLimb;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedMotion;
import edu.ucsc.hsl.hse.model.biped.threelink.states.VirtualBipedState;

public class BipedReferenceControl implements BipedDynamicsController
{

	public VirtualBipedState virtualBiped;
	public BipedParameters parameters;

	public BipedReferenceControl(VirtualBipedState real_biped, BipedParameters parameters)
	{
		virtualBiped = real_biped;
		this.parameters = parameters;
	}

	@Override
	public Matrix getInputTorques()
	{
		Matrix control = computeControlInput(getOrbitTrackingAccelerations());
		return control;
	}

	public Matrix computeControlInput(Matrix accelerations)
	{
		Matrix matD = BipedComputer.computeFlowDMatrix(virtualBiped, parameters);
		Matrix matC = BipedComputer.computeFlowCMatrix(virtualBiped, parameters);
		Matrix matG = BipedComputer.computeFlowGMatrix(virtualBiped, parameters);
		Matrix vels = virtualBiped.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = parameters.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
		.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public Matrix getOrbitTrackingAccelerations()
	{
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

	public void computeEquilibriumStates()
	{
		EquilibriumComputer comp = new EquilibriumComputer(virtualBiped, parameters);
		virtualBiped.equilibParams = comp.getEquilibriumParameters();
		virtualBiped.trajParams = new TrajectoryParameters(virtualBiped, virtualBiped.equilibParams.getFinalState(),
		parameters);
	}

	public void computeCurrentTrajectoryParams()
	{

		if (virtualBiped.equilibParams == null || virtualBiped.equilibParams.getFinalState() == null)
		{
			computeEquilibriumStates();
		} else
		{

			virtualBiped.trajParams = new TrajectoryParameters(virtualBiped, virtualBiped.equilibParams.getFinalState(),
			parameters);

		}

	}

	public Matrix getComputedAcceleration()
	{
		return getOrbitTrackingAccelerations();
	}

	public void recomputeTrajectoryParams()
	{
		computeCurrentTrajectoryParams();

	}

}
