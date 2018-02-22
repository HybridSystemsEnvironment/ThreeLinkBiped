package edu.ucsc.hsl.hse.model.biped.threelink.parameters;

import java.util.HashMap;

import edu.ucsc.hsl.hse.model.biped.threelink.computors.BipedComputer;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedLimb;
import edu.ucsc.hsl.hse.model.biped.threelink.states.BipedState;

public class TrajectoryParameters
{

	public HashMap<BipedLimb, Double> B0;
	public HashMap<BipedLimb, Double> B1;
	public BipedState initialState;
	public BipedState finalState;
	public double stepTime;

	public static BipedState getBipedState(BipedState state)
	{
		return new BipedState(state.plantedLegAngle, state.swingLegAngle, state.torsoAngle, state.plantedLegVelocity,
		state.swingLegVelocity, state.torsoVelocity);
	}

	public TrajectoryParameters(BipedState initial_state, BipedState final_state, BipedParameters params)
	{
		initialState = getBipedState(initial_state);
		finalState = getBipedState(final_state);//
		Double stepTime = params.getStepTime();
		B0 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 1);
	}

	public TrajectoryParameters(BipedState initial_state, BipedState final_state, BipedParameters params,
	boolean initial)
	{
		initialState = getBipedState(initial_state);
		finalState = getBipedState(final_state);//
		Double stepTime = params.getStepTime();
		B0 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 1);
	}

	public TrajectoryParameters(BipedState initial_state, BipedState final_state, BipedParameters params,
	Double step_time)
	{
		initialState = getBipedState(initial_state);
		finalState = getBipedState(final_state);//
		B0 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 1);
	}

	public double getB0(BipedLimb limb)
	{
		return B0.get(limb);
	}

	public double getB1(BipedLimb limb)

	{
		return B1.get(limb);
	}

	public HashMap<BipedLimb, Double> getB0()
	{
		return B0;
	}

	public HashMap<BipedLimb, Double> getB1()
	{
		return B1;
	}

	public BipedState getInitialState()
	{
		return initialState;
	}

	public BipedState getFinalState()
	{
		return finalState;
	}

	public Double getStepTime()
	{
		return stepTime;
	}

}