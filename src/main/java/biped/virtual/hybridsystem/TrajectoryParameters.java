
package biped.virtual.hybridsystem;

import java.util.HashMap;

import biped.computations.BipedComputer;
import biped.hybridsystem.Parameters;
import biped.hybridsystem.BipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedLimb;

public class TrajectoryParameters {

	public HashMap<BipedLimb, Double> B0;

	public HashMap<BipedLimb, Double> B1;

	public BipedState initialState;

	public BipedState finalState;

	public static BipedState getState(BipedState state) {

		return new BipedState(state.plantedLegAngle, state.swingLegAngle, state.torsoAngle, state.plantedLegVelocity,
				state.swingLegVelocity, state.torsoVelocity);
	}

	public TrajectoryParameters(BipedState initial_state, BipedState final_state, Parameters params) {

		initialState = getState(initial_state);
		finalState = getState(final_state);//
		Double stepTime = params.getStepTime();
		B0 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 1);
	}

	public TrajectoryParameters(BipedState initial_state, BipedState final_state, Parameters params, boolean initial) {

		initialState = getState(initial_state);
		finalState = getState(final_state);//
		Double stepTime = params.getStepTime();
		B0 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, stepTime, initial_state, final_state, 1);
	}

	public TrajectoryParameters(BipedState initial_state, BipedState final_state, Parameters params, Double step_time) {

		initialState = getState(initial_state);
		finalState = getState(final_state);//
		B0 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 0);
		B1 = BipedComputer.computeCoefficient(params, step_time, initial_state, final_state, 1);
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

	public BipedState getInitialState() {

		return initialState;
	}

	public BipedState getFinalState() {

		return finalState;
	}

}