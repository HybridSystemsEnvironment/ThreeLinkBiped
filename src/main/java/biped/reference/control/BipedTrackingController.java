
package biped.reference.control;

import Jama.Matrix;
import biped.application.Controller;
import biped.computations.BipedComputer;
import biped.hybridsystem.Link;
import biped.hybridsystem.Parameters;
import biped.hybridsystem.State;
import edu.ucsc.cross.hse.core.network.Network;
import edu.ucsc.cross.hse.core.network.SubNode;
import edu.ucsc.hsl.hse.model.biped.threelink.specifications.BipedMotion;

public class BipedTrackingController implements Controller<biped.hybridsystem.State, Matrix> {

	public Parameters parameters;

	public Double kOne;

	public Double kTwo;

	public BipedTrackingController(Parameters parameters) {

		this.parameters = parameters;
		kOne = (2000.0);
		kTwo = (100.0);
	}

	public Matrix computeControlInput(State realBiped, Matrix accelerations) {

		Matrix matD = BipedComputer.computeFlowDMatrix(realBiped, parameters);
		Matrix matC = BipedComputer.computeFlowCMatrix(realBiped, parameters);
		Matrix matG = BipedComputer.computeFlowGMatrix(realBiped, parameters);
		Matrix vels = realBiped.getMotionMatrix(BipedMotion.ANGULAR_VELOCITY);
		Matrix torqRel = parameters.getTorqueRelationship();

		Matrix controlUnbounded = torqRel.inverse()
				.times(accelerations.minus(matD.inverse().times((matC.times(vels)).minus(matG))));
		return controlUnbounded;
	}

	public Matrix getComputedAcceleration(State realBiped, State virtualBiped) {

		double[][] positionDifference = { { realBiped.plantedLegAngle - virtualBiped.plantedLegAngle },
				{ realBiped.swingLegAngle - virtualBiped.swingLegAngle },
				{ realBiped.torsoAngle - virtualBiped.torsoAngle } };
		Matrix deltaPos = new Matrix(positionDifference);
		double[][] velocityDifference = { { realBiped.plantedLegVelocity - virtualBiped.plantedLegVelocity },
				{ realBiped.swingLegVelocity - virtualBiped.swingLegVelocity },
				{ realBiped.torsoVelocity - virtualBiped.torsoVelocity } };
		Matrix deltaVel = new Matrix(velocityDifference);
		deltaPos = deltaPos.times(-kOne);
		deltaVel = deltaVel.times(-kTwo);
		// TODO Auto-generated method stub
		return deltaPos.plusEquals(deltaVel);
	}

	@Override
	public Matrix k(State state) {

		SubNode<State, biped.virtual.hybridsystem.State> node = Network.getGlobal().getSubNode(state,
				biped.virtual.hybridsystem.State.class, Link.PLANT_TO_VIRTUAL.toString());
		// System.out.println(XMLParser.serializeObject(node));
		biped.virtual.hybridsystem.State trigger = node.getIncoming().get(0).getSource();
		Matrix virtualAcceleration = BipedReferenceControl.computeOrbitTrackingAccelerations(trigger);
		Matrix controlAccel = virtualAcceleration.plusEquals(getComputedAcceleration(state, trigger.bipedState));

		return computeControlInput(state, controlAccel);
	}
}