
package biped.virtual.hybridsystem;

import biped.application.ContentLoader;
import biped.computations.BipedComputer;
import biped.hybridsystem.Link;
import edu.ucsc.cross.hse.core.modeling.JumpSet;
import edu.ucsc.hsl.hse.model.biped.threelink.factories.Zero;

/**
 * A jump set
 */
public class Dp implements JumpSet<State> {

	/**
	 * Parameters
	 */
	public Parameters parameters;

	/**
	 * Constructor for jump set
	 */
	public Dp(Parameters parameters) {

		this.parameters = parameters;
	}

	/**
	 * Flow set
	 * 
	 * @param x
	 *            current state
	 */
	@Override
	public boolean D(State x) {

		boolean inD = false; // add logic to determine if x in jump set
		// SubNode<State, biped.hybridsystem.State> node =
		// Network.getGlobal().getSubNode(x,
		// biped.hybridsystem.State.class, Link.PLANT_TO_VIRTUAL.toString());
		// // System.out.println(XMLParser.serializeObject(node));
		// biped.hybridsystem.State trigger =
		// node.getOutgoing().get(0).getSource().bipedState;

		biped.hybridsystem.State trigger = ContentLoader.getConnected(x, biped.hybridsystem.State.class,
				Link.PLANT_TO_VIRTUAL.toString());
		double hVal = BipedComputer.computeStepRemainder(trigger, parameters.bipedParams);
		inD = Zero.equal(hVal) && trigger.plantedLegVelocity >= 0.0;
		return inD;
	}

}
