package edu.ucsc.hsl.hse.model.biped.threelink.controllers;

import Jama.Matrix;
import biped.computations.BipedComputer;
import biped.hybridsystem.Controller;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedMotion;
import edu.ucsc.hsl.hse.model.biped.threelink.states.BipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.VirtualBipedState;

public class BipedTrackingController implements Controller
{

	public BipedState realBiped;
	public VirtualBipedState virtualBiped;
	public BipedVirtualControl virtualController;
	public BipedParameters parameters;
	public Double kOne;
	public Double kTwo;

	public BipedTrackingController(BipedState real_biped, VirtualBipedState virtual_biped,
	BipedVirtualControl virtual_controller, BipedParameters parameters)
	{
		realBiped = real_biped;
		virtualBiped = virtual_biped;
		virtualController = virtual_controller;
		this.parameters = parameters;
		kOne = (2000.0);
		kTwo = (100.0);
	}

	@Override
	public Matrix getInputTorques()

	{
		return getControlInput();
	}

	public Matrix computeControlInput(Matrix accelerations)
	{
		Matrix matD = BipedComputer.computeFlowDMatrix(realBiped, parameters);
		Matrix matC = BipedComputer.computeFlowCMatrix(realBiped, parameters);
		Matrix matG = BipedComputer.computeFlowGMatrix(realBiped, parameters);
		Matrix vels = realBiped.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = parameters.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
		.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public Matrix getComputedAcceleration()
	{

		double[][] positionDifference =
		{
				{ realBiped.plantedLegAngle - virtualBiped.plantedLegAngle },
				{ realBiped.swingLegAngle - virtualBiped.swingLegAngle },
				{ realBiped.torsoAngle - virtualBiped.torsoAngle } };
		Matrix deltaPos = new Matrix(positionDifference);
		double[][] velocityDifference =
		{
				{ realBiped.plantedLegVelocity - virtualBiped.plantedLegVelocity },
				{ realBiped.swingLegVelocity - virtualBiped.swingLegVelocity },
				{ realBiped.torsoVelocity - virtualBiped.torsoVelocity } };
		Matrix deltaVel = new Matrix(velocityDifference);
		deltaPos = deltaPos.times(-kOne);
		deltaVel = deltaVel.times(-kTwo);
		// TODO Auto-generated method stub
		return deltaPos.plusEquals(deltaVel);
	}

	public Matrix getControlInput()
	{
		Matrix virtualAcceleration = virtualController.getComputedAcceleration();

		Matrix controlAccel = virtualAcceleration.plusEquals(getComputedAcceleration());

		return computeControlInput(controlAccel);
		// return ;
	}
}