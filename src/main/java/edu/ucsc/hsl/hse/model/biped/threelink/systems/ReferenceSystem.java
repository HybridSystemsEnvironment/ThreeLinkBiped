package edu.ucsc.hsl.hse.model.biped.threelink.systems;

import biped.computations.BipedComputer;
import biped.reference.control.BipedReferenceControl;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.hsl.hse.model.biped.threelink.parameters.BipedParameters;
import edu.ucsc.hsl.hse.model.biped.threelink.states.BipedState;
import edu.ucsc.hsl.hse.model.biped.threelink.states.VirtualBipedState;

public class ReferenceSystem extends HybridSystem<VirtualBipedState>
{

	public BipedParameters params;
	public BipedReferenceControl controller;

	public ReferenceSystem(VirtualBipedState state, BipedParameters params, BipedReferenceControl controller)
	{
		super(state);
		state.getProperties().setName("Reference Biped");
		this.params = params;
		this.controller = controller;
		initialize();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void F(VirtualBipedState x, VirtualBipedState x_dot)
	{
		BipedComputer.computeChangeBetweenImpact(x, x_dot, params, controller.getInputTorques());
		x_dot.trajTimer = 1.0;
	}

	@Override
	public boolean C(VirtualBipedState x)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void G(VirtualBipedState x, VirtualBipedState x_plus)
	{
		// Get equilibrium (limit cycle) initial state
		BipedState equilibState = x.equilibParams.getInitialState();

		// Reset reference state vlues to initial equilibrium (limit cycle) state values
		x_plus.plantedLegAngle = equilibState.plantedLegAngle;
		x_plus.plantedLegVelocity = equilibState.plantedLegVelocity;
		x_plus.swingLegAngle = equilibState.swingLegAngle;
		x_plus.swingLegVelocity = equilibState.swingLegVelocity;
		x_plus.torsoAngle = equilibState.torsoAngle;
		x_plus.torsoVelocity = equilibState.torsoVelocity;

		// Reset trajectory timer
		x_plus.trajTimer = 0.0;

		// Keep same equilibrium parameters
		x_plus.equilibParams = x.equilibParams;
		x_plus.trajParams = x.trajParams;

	}

	@Override
	public boolean D(VirtualBipedState x)
	{
		// TODO Auto-generated method stub
		// return false;

		return BipedComputer.isImpactOccurring(x, params);
	}

	public void initialize()
	{
		controller.computeEquilibriumStates();
		BipedState equilibState = getComponents().getState().equilibParams.getInitialState();
		// System.out.println(XMLParser.serializeObject(equilibState));
		getComponents().getState().plantedLegAngle = equilibState.plantedLegAngle;
		getComponents().getState().plantedLegVelocity = equilibState.plantedLegVelocity;
		getComponents().getState().swingLegAngle = equilibState.swingLegAngle;
		getComponents().getState().swingLegVelocity = equilibState.swingLegVelocity;
		getComponents().getState().torsoAngle = equilibState.torsoAngle;
		getComponents().getState().torsoVelocity = equilibState.torsoVelocity;

		// Reset trajectory timer
		getComponents().getState().trajTimer = 0.0;

		controller.computeCurrentTrajectoryParams();
		// getComponents().getState().equilibParams = controller.virtualBiped.equilibParams;
		// getComponents().getState().trajParams = controller.virtualBiped.trajParams;
		// System.out.println(XMLParser.serializeObject(equilibState));

	}

}
