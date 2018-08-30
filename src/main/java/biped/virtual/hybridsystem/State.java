
package biped.virtual.hybridsystem;

import edu.ucsc.cross.hse.core.figure2.TimerState;

public class State extends biped.hybridsystem.BipedState {

	public double trajTimer;

	public TrajectoryParameters trajParams;

	public TimerState bip;

	public State(biped.hybridsystem.BipedState biped, Parameters parameters) {

		trajTimer = 0.0;
		bip = new TimerState(1.0);
		bip.getProperties().setStoreTrajectory(true);
		plantedLegAngle = biped.plantedLegAngle;
		plantedLegVelocity = biped.plantedLegVelocity;
		swingLegAngle = biped.swingLegAngle;
		swingLegVelocity = biped.swingLegVelocity;
		torsoAngle = biped.torsoAngle;
		torsoVelocity = biped.torsoVelocity;
		trajParams = new TrajectoryParameters(biped, parameters.equilibParams.getFinalState(), parameters.bipedParams);
	}
}
