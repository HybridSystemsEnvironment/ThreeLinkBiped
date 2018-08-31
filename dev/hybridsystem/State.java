
package net.biped.virtual.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class State extends DataStructure {

	public double trajTimer;

	public TrajectoryParameters trajectoryParameters;

	public biped.parameters.base.State bipedState;

	public State(biped.parameters.base.State biped, Parameters parameters) {

		trajTimer = 0.0;
		this.bipedState = biped;
		trajectoryParameters = new TrajectoryParameters(biped, parameters.equilibParams.getFinalState(),
				parameters.bipedParams);
		super.getProperties().setStoreTrajectory(true);
	}
}
