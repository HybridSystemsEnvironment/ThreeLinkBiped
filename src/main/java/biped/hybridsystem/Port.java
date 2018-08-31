
package biped.hybridsystem;

import edu.ucsc.cross.hse.core.modeling.Output;

public class Port<X> implements Output<X> {

	private Output<X> output;

	/**
	 * @return the output
	 */
	public Output<X> getOutput() {

		return output;
	}

	/**
	 * @param output
	 *            the output to set
	 */
	public void setOutput(Output<X> output) {

		this.output = output;
	}

	@Override
	public X y() {

		// TODO Auto-generated method stub
		return output.y();
	}
}
