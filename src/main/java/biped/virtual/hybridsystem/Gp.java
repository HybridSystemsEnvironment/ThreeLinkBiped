
package biped.virtual.hybridsystem;

import biped.hybridsystem.BipedState;
import edu.ucsc.cross.hse.core.modeling.JumpMap;

/**
 * A jump map
 */
public class Gp implements JumpMap<State> {

	/**
	 * Parameters
	 */
	public Parameters parameters;

	/**
	 * Constructor for jump map
	 */
	public Gp(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_plus
	 *            state update values
	 */
	@Override
	public void G(State x, State x_plus) {

		// Get equilibrium (limit cycle) initial state
		BipedState equilibState = parameters.equilibParams.getInitialState();

		// Reset reference state vlues to initial equilibrium (limit cycle) state values
		x_plus.plantedLegAngle = equilibState.plantedLegAngle;
		x_plus.plantedLegVelocity = equilibState.plantedLegVelocity;
		x_plus.swingLegAngle = equilibState.swingLegAngle;
		x_plus.swingLegVelocity = equilibState.swingLegVelocity;
		x_plus.torsoAngle = equilibState.torsoAngle;
		x_plus.torsoVelocity = equilibState.torsoVelocity;
		x_plus.bip.value = 0.0;
		x_plus.trajTimer = 0.0;

		// Keep same equilibrium parameters
	}

}
