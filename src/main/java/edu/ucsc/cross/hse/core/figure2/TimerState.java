
package edu.ucsc.cross.hse.core.figure2;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

/**
 * A state
 */
public class TimerState extends DataStructure {

	/**
	 * Example state value
	 */
	public double value;

	/**
	 * Construct the states
	 * 
	 * @param state_value_1
	 *            value of state 1
	 * @param state_value_1
	 *            value of state 2
	 */
	public TimerState(double default_state_value) {

		value = default_state_value;

	}

}
