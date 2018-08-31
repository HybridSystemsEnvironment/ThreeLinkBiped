
package edu.ucsc.hsl.hse.model.biped.threelink.parameters;

import java.util.HashMap;

import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedLimb;

public class ActuatorConstraint {

	/**
	 * Torque constraint for the actuator that drives the swing leg
	 */
	public double swingActuator;

	/**
	 * Torque constraint for the actuator that drives the planted leg
	 */
	public double plantedActuator;

	/**
	 * Torque constraint for the actuator that drives the torso
	 */
	public double torsoActuator;

	/**
	 * Constructor for equal constraints on each actuator
	 * 
	 * @param value
	 *            constraint value for each actuator
	 */
	public ActuatorConstraint(double value) {

		swingActuator = value;
		plantedActuator = value;
		torsoActuator = value;
	}

	/**
	 * Constructor for different constraints on each actuator
	 * 
	 * @param swing_value
	 *            constraint value for swing actuator
	 */
	public ActuatorConstraint(double swing, double planted, double torso) {

		swingActuator = swing;
		plantedActuator = planted;
		torsoActuator = torso;
	}

	/**
	 * Get a mapping of actuator constraint values indexed by limb
	 * 
	 * @return constraint value mapping
	 */
	public HashMap<BipedLimb, Double> getLimbValueMap() {

		HashMap<BipedLimb, Double> map = new HashMap<BipedLimb, Double>();
		map.put(BipedLimb.PLANTED_LEG, plantedActuator);
		map.put(BipedLimb.SWING_LEG, swingActuator);
		map.put(BipedLimb.TORSO, torsoActuator);
		return map;

	}
}
