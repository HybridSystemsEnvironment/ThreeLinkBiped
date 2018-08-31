
package biped.parameters.virtual;

import biped.parameters.base.TrajectoryParameters;
import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class State extends DataStructure {

	public double trajTimer;

	public TrajectoryParameters trajectoryParameters;

	public biped.parameters.base.State bipedState;

	public State(biped.parameters.base.State biped, biped.parameters.virtual.Parameters parameters) {

		trajTimer = 0.0;
		this.bipedState = biped;
		trajectoryParameters = new TrajectoryParameters(biped, parameters.equilibParams.getFinalState(),
				parameters.bipedParams);
		super.getProperties().setStoreTrajectory(true);
	}

	public State(Parameters parameters) {

		this(new biped.parameters.base.State(), parameters);
	}

	public State(Parameters parameters, Double planted_angle, Double swing_angle, Double torso_angle,
			Double planted_velocity, Double swing_velocity, Double torso_velocity) {

		this(new biped.parameters.base.State(planted_angle, swing_angle, torso_angle, planted_velocity, swing_velocity,
				torso_velocity), parameters);
	}
}
