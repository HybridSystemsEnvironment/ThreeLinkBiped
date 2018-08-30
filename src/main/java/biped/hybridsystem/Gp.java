
package biped.hybridsystem;

import com.be3short.io.xml.XMLParser;

import biped.computations.BipedComputer;
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

		BipedComputer.computeChangeAtImpact(x, x_plus, parameters);
		System.out.println(XMLParser.serializeObject(this));
		// System.exit(0);
	}

}
