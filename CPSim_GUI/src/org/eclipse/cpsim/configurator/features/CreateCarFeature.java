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

public class CreateCarFeature extends AbstractCreateFeature implements ICreateFeature {

	public CreateCarFeature(IFeatureProvider fp) {
		super(fp, "CAR", "Creates a new CAR");
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

		// Create a new car and add it to an EMF resource
		CAR car = DiagramFactory.eINSTANCE.createCAR();

		int idx = 0;
		Collection<PictogramElement> pes = Graphiti.getPeService().getAllContainedPictogramElements(getDiagram());
		Iterator<PictogramElement> it = pes.iterator();
		boolean cont = false;

		while (it.hasNext()) {
			PictogramElement npe = it.next();
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(npe);
			if (eobj instanceof SWC && (Message.DEFAULT_CAR_NAME).equals(((SWC) eobj).getId().split(";")[0])) {
				cont = true;
				break;
			} else if (eobj instanceof CAR && (Message.DEFAULT_CAR_NAME).equals(((CAR) eobj).getId())) {
				cont = true;
				break;
			} else if (eobj instanceof CAN && (Message.DEFAULT_CAR_NAME).equals(((CAN) eobj).getId())) {
				cont = true;
				break;
			} else if (eobj instanceof ECU && (Message.DEFAULT_CAR_NAME).equals(((ECU) eobj).getId())) {
				cont = true;
				break;
			}
		}

		while (cont && it.hasNext()) {
			PictogramElement npe = it.next();
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(npe);
			if (eobj instanceof SWC && (Message.DEFAULT_CAR_NAME + idx).equals(((SWC) eobj).getId().split(";")[0])) {
				idx++;
				it = pes.iterator();
			} else if (eobj instanceof CAR && (Message.DEFAULT_CAR_NAME + idx).equals(((CAR) eobj).getId())) {
				idx++;
				it = pes.iterator();
			} else if (eobj instanceof CAN && (Message.DEFAULT_CAR_NAME + idx).equals(((CAN) eobj).getId())) {
				idx++;
				it = pes.iterator();
			} else if (eobj instanceof ECU && (Message.DEFAULT_CAR_NAME + idx).equals(((ECU) eobj).getId())) {
				idx++;
				it = pes.iterator();
			}
		}
		// initialize CAR data member
		if (!cont)
			car.setId(Message.DEFAULT_CAR_NAME);
		else
			car.setId(Message.DEFAULT_CAR_NAME + idx);
		resource.getContents().add(car);

		addGraphicalRepresentation(context, car);
		return new Object[] { car };
	}
}
