package edu.ucsc.hsl.hse.model.biped.threelink.states;

import edu.ucsc.cross.hse.core.modeling.DataStructure;

public class ClosedLoopBipedState extends DataStructure// ClosedLoopBipedState
{

	public PerturbationState perturbation;
	public BipedState realBiped;
	public VirtualBipedState virtualBiped;

	public ClosedLoopBipedState(BipedState real_biped, VirtualBipedState virtual_biped,
	PerturbationState perturb)
	{
		realBiped = real_biped;
		virtualBiped = virtual_biped;
		perturbation = perturb;
		realBiped.getProperties().setStoreTrajectory(true);
		virtualBiped.getProperties().setStoreTrajectory(true);
		perturbation.getProperties().setStoreTrajectory(true);
		realBiped.getProperties().setName("Real State");
		virtualBiped.getProperties().setName("Virtual State");
		perturbation.getProperties().setName("Perturbation State");
		// TODO Auto-generated constructor stub
	}

}
