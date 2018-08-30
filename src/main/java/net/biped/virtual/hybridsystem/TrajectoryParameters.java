
package net.biped.virtual.hybridsystem;

import java.util.HashMap;

import biped.computations.BipedComputer;
import biped.hybridsystem.Parameters;
import biped.hybridsystem.State;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedLimb;

public class TrajectoryParameters {

	public HashMap<BipedLimb, Double> B0;

	public HashMap<BipedLimb, Double> B1;

	public State initialState;

	public State finalState;

	public TrajectoryParameters(State initial_state, State final_state, Parameters params) {

		recompute(initial_state, final_state, params);

	}

	public TrajectoryParameters(State initial_state, State final_state, Double step_time, Parameters params) {

		recompute(initial_state, final_state, step_time, params);
	}

	public double getB0(BipedLimb limb) {

		return B0.get(limb);
	}

	public double getB1(BipedLimb limb)

	{

		return B1.get(limb);
	}

	public HashMap<BipedLimb, Double> getB0() {

		return B0;
	}

	public HashMap<BipedLimb, Double> getB1() {

		return B1;
	}

	public State getInitialState() {

		return initialState;
	}

	public State getFinalState() {

		return finalState;
	}

	public void recompute(State initial_state, State final_state, Parameters params) {

		Double stepTime = params.getStepTime();
		initialState = biped.hybridsystem.State.getState(initial_state);
		finalState = biped.hybridsystem.State.getState(final_state);//
		B0 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 1);
	}

	public void recompute(State initial_state, State final_state, Double step_time, Parameters params) {

		initialState = biped.hybridsystem.State.getState(initial_state);
		finalState = biped.hybridsystem.State.getState(final_state);//
		B0 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 1);
	}

	public static TrajectoryParameters compute(State initial_state, State final_state, Double step_time,
			Parameters params) {

		TrajectoryParameters param = new TrajectoryParameters(initial_state, final_state, step_time, params);
		return param;
	}
}