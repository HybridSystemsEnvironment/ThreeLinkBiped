
package biped.plant.hybridsystem;

import biped.parameters.virtual.State;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.modeling.Output;

public class Connections {

	public Output<perturbation.hybridsystem.State> perturbation;

	public Output<biped.parameters.base.State> input;

	public HybridSys<biped.parameters.base.State> plant;

	public Output<State> reference;

	public Output<State> virtual;

	public void setConnections(Output<perturbation.hybridsystem.State> perturbation,
			Output<biped.parameters.base.State> input, Output<State> reference, Output<State> virtual,
			HybridSys<biped.parameters.base.State> plant) {

		this.perturbation = perturbation;
		this.input = input;
		this.reference = reference;
		this.virtual = virtual;
		this.plant = plant;
	}

	public void setConnections(Output<biped.parameters.base.State> input,
			Output<biped.parameters.virtual.State> reference, Output<biped.parameters.virtual.State> virtual) {

		this.perturbation = null;
		this.input = input;
		this.reference = reference;
		this.virtual = virtual;
	}

	/**
	 * @return the perturbation
	 */
	public Output<perturbation.hybridsystem.State> getPerturbation() {

		return perturbation;
	}

	/**
	 * @param perturbation
	 *            the perturbation to set
	 */
	public void setPerturbation(Output<perturbation.hybridsystem.State> perturbation) {

		this.perturbation = perturbation;
	}

	/**
	 * @return the input
	 */
	public Output<biped.parameters.base.State> getInput() {

		return input;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(Output<biped.parameters.base.State> input) {

		this.input = input;
	}

	/**
	 * @return the reference
	 */
	public Output<State> getReference() {

		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set
	 */
	public void setReference(Output<State> reference) {

		this.reference = reference;
	}

	/**
	 * @return the virtual
	 */
	public Output<State> getVirtual() {

		return virtual;
	}

	/**
	 * @param virtual
	 *            the virtual to set
	 */
	public void setVirtual(Output<State> virtual) {

		this.virtual = virtual;
	}

	/**
	 * @return the plant
	 */
	public HybridSys<biped.parameters.base.State> getPlant() {

		return plant;
	}

	/**
	 * @param plant
	 *            the plant to set
	 */
	public void setPlant(HybridSys<biped.parameters.base.State> plant) {

		this.plant = plant;
	}
}
