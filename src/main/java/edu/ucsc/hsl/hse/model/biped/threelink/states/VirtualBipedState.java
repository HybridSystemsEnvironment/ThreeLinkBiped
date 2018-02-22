package edu.ucsc.hsl.hse.model.biped.threelink.states;

import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.TrajectoryParameters;

public class VirtualBipedState extends BipedState
{

	public double trajTimer;
	public TrajectoryParameters trajParams;
	public TrajectoryParameters equilibParams;

	public VirtualBipedState(Double planted_angle, Double swing_angle, Double torso_angle, Double planted_velocity,
	Double swing_velocity, Double torso_velocity)
	{
		super(planted_angle, swing_angle, torso_angle, planted_velocity, swing_velocity, torso_velocity);
		trajTimer = 0.0;
		trajParams = null;
		equilibParams = null;
	}

	public VirtualBipedState(BipedState biped)
	{
		this(biped.plantedLegAngle, biped.swingLegAngle, biped.torsoAngle, biped.plantedLegVelocity,
		biped.swingLegVelocity, biped.torsoVelocity);
	}

	public VirtualBipedState(BipedParameters params)
	{
		super();
		trajTimer = params.getStepTime() * Math.random();
		trajParams = null;
		equilibParams = null;

	}

	public static VirtualBipedState getRandomizedState(BipedParameters params)
	{
		VirtualBipedState virtual = new VirtualBipedState(params);
		randomizeState(virtual, params);
		return virtual;
	}

	public static VirtualBipedState getTestState()
	{
		return new VirtualBipedState(-.3, .3, 0.0, 0.0, 0.0, 0.0);
	}

	public static VirtualBipedState getRandomTestState(BipedParameters params)
	{
		return new VirtualBipedState(params);
	}
}
