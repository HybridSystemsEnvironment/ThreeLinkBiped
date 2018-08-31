
package biped.plant.hybridsystem;

import com.be3short.io.xml.XMLParser;

import biped.computations.BipedComputer;
import biped.parameters.base.State;
import edu.ucsc.cross.hse.core.hybridsystem.input.JumpMap;

/**
 * A jump map
 */
public class Gp implements JumpMap<State, Parameters> {

	/**
	 * Jump map
	 * 
	 * @param x
	 *            current state
	 * @param x_plus
	 *            state update values
	 */
	@Override
	public void G(State x, State x_plus, Parameters parameters) {

		BipedComputer.computeChangeAtImpact(x, x_plus, parameters.bipedParams);
		System.out.println(XMLParser.serializeObject(this));
	}

}
