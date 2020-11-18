package org.eclipse.cpsim.configurator.features;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.DiagramFactory;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.Message;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class CreateSwcFeature extends AbstractCreateFeature implements ICreateFeature {

	public CreateSwcFeature(IFeatureProvider fp) {
		super(fp, "SWC", "Creates a new SWC");
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Resource resource = context.getTargetContainer().eResource();

		// Create a new chess board and add it to an EMF resource
		SWC swc = DiagramFactory.eINSTANCE.createSWC();
		// initialize SWC data member
		int idx = 0;
		Collection<PictogramElement> pes = Graphiti.getPeService().getAllContainedPictogramElements(getDiagram());
		Iterator<PictogramElement> it = pes.iterator();
		while (it.hasNext()) {
			PictogramElement npe = it.next();
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(npe);
			if (eobj instanceof SWC && (Message.DEFAULT_SWC_NAME + idx).equals(((SWC) eobj).getId().split(";")[0])) {
				idx++;
				it = pes.iterator();
			} else if (eobj instanceof CAR && (Message.DEFAULT_SWC_NAME + idx).equals(((CAR) eobj).getId())) {
				idx++;
				it = pes.iterator();
			} else if (eobj instanceof CAN && (Message.DEFAULT_SWC_NAME + idx).equals(((CAN) eobj).getId())) {
				idx++;
				it = pes.iterator();
			} else if (eobj instanceof ECU && (Message.DEFAULT_SWC_NAME + idx).equals(((ECU) eobj).getId())) {
				idx++;
				it = pes.iterator();
			}
		}
		swc.setId(Message.DEFAULT_SWC_NAME + idx);
		swc.setPeriod(0);
		swc.setDeadline(0);
		swc.setWcet(0);
		swc.setBcet(0);
		swc.setPhase(0);
		swc.setProcon("");
		swc.setReadCon("0");
		swc.setWriteCon("0");

		resource.getContents().add(swc);

		addGraphicalRepresentation(context, swc);
		swc.setId(Message.DEFAULT_SWC_NAME + idx + ";" + ";");
		return new Object[] { swc };
	}
}
