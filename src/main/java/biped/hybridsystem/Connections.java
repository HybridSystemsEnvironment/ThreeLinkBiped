
package biped.hybridsystem;

import biped.virtual.hybridsystem.State;
import edu.ucsc.cross.hse.core.modeling.HybridSys;
import edu.ucsc.cross.hse.core.modeling.Output;

public class Connections {

	public Output<perturbation.hybridsystem.PerturbState> perturbation;

	public Output<biped.hybridsystem.State> input;

	public HybridSys<biped.hybridsystem.State> plant;

	public Output<State> reference;

	public Output<State> virtual;

	public void setConnections(Output<perturbation.hybridsystem.PerturbState> perturbation,
			Output<biped.hybridsystem.State> input, Output<State> reference, Output<State> virtual,
			HybridSys<biped.hybridsystem.State> plant) {

		this.perturbation = perturbation;
		this.input = input;
		this.reference = reference;
		this.virtual = virtual;
		this.plant = plant;
	}

	public void setConnections(Output<biped.hybridsystem.State> input,
			Output<biped.virtual.hybridsystem.State> reference, Output<biped.virtual.hybridsystem.State> virtual) {

		this.perturbation = null;
		this.input = input;
		this.reference = reference;
		this.virtual = virtual;
	}

	/**
	 * @return the perturbation
	 */
	public Output<perturbation.hybridsystem.PerturbState> getPerturbation() {

		return perturbation;
	}

	/**
	 * @param perturbation
	 *            the perturbation to set
	 */
	public void setPerturbation(Output<perturbation.hybridsystem.PerturbState> perturbation) {

		this.perturbation = perturbation;
	}

	/**
	 * @return the input
	 */
	public Output<biped.hybridsystem.State> getInput() {

		return input;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(Output<biped.hybridsystem.State> input) {

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
	public HybridSys<biped.hybridsystem.State> getPlant() {

		return plant;
	}

	/**
	 * @param plant
	 *            the plant to set
	 */
	public void setPlant(HybridSys<biped.hybridsystem.State> plant) {

		this.plant = plant;
	}
}
