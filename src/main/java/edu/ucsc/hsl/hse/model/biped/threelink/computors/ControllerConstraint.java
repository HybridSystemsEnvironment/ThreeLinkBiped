
package edu.ucsc.hsl.hse.model.biped.threelink.computors;

import Jama.Matrix;
import edu.ucsc.hsl.hse.model.biped.threelink.controllers.BipedDynamicsController;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.ActuatorConstraint;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedLimb;

public class ControllerConstraint implements BipedDynamicsController {

	public BipedDynamicsController controller;

	public ActuatorConstraint constraint;

	public ControllerConstraint(BipedDynamicsController controller, ActuatorConstraint constraint) {

		this.controller = controller;
		this.constraint = constraint;
	}

	@Override
	public Matrix getInputTorques() {

		Matrix unconstrained = controller.getInputTorques();
		Matrix constrained = unconstrained.copy();
		for (BipedLimb limb : BipedLimb.values()) {
			Double unconstrainedVal = unconstrained.get(limb.vectorIndex, 0);
			Double maxVal = constraint.getLimbValueMap().get(limb);
			if (unconstrainedVal > maxVal) {
				constrained.set(limb.vectorIndex, 0, maxVal);
			}
		}

		return constrained;
	}

}
