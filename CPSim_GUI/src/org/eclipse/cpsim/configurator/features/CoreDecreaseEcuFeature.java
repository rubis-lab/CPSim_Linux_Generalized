package org.eclipse.cpsim.configurator.features;

import org.eclipse.cpsim.Diagram.CustomImageProvider;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class CoreDecreaseEcuFeature extends AbstractCreateFeature implements ICreateFeature {
	private PictogramElement pe;

	public CoreDecreaseEcuFeature(IFeatureProvider fp) {
		super(fp, "Core Decrease", "Core Decrease");
	}

	/* Disable Undo because default undo doesn't support changing id */
	@Override
	public boolean canUndo(IContext context) {
		return false;
	}

	public void setPictogram(PictogramElement pe_) {
		pe = pe_;
	}

	public PictogramElement getPictogram() {
		return pe;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		boolean changed = false;
		for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
			if (!pe.equals(pic))
				continue;
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			/* ECU */
			if (eobj instanceof ECU) {
				ECU ecu = (ECU) eobj;
				if (ecu.getNumberOfCores() > 1 && ((AnchorContainer) pic).getAnchors().get(ecu.getNumberOfCores())
						.getIncomingConnections().size() == 0) {
					ecu.setNumberOfCores(ecu.getNumberOfCores() - 1);
					changed = true;
				}
				Image b = (Image) pe.getGraphicsAlgorithm();

				/* Change Image */
				if (ecu.getVirtual().equals(VirtualCategory.VISIBLE)) {
					switch (ecu.getNumberOfCores()) {
					case (1):
						b.setId(CustomImageProvider.IMG_MC1_ECU);
						break;
					case (2):
						b.setId(CustomImageProvider.IMG_MC2_ECU);
						break;
					case (3):
						b.setId(CustomImageProvider.IMG_MC3_ECU);
						break;
					case (4):
						b.setId(CustomImageProvider.IMG_MC4_ECU);
						break;
					}
				} else if (ecu.getVirtual().equals(VirtualCategory.INVISIBLE)) {
					switch (ecu.getNumberOfCores()) {
					case (1):
						b.setId(CustomImageProvider.IMG_REAL_MC1_ECU);
						break;
					case (2):
						b.setId(CustomImageProvider.IMG_REAL_MC2_ECU);
						break;
					case (3):
						b.setId(CustomImageProvider.IMG_REAL_MC3_ECU);
						break;
					case (4):
						b.setId(CustomImageProvider.IMG_REAL_MC4_ECU);
						break;
					}
				}

				if (changed) {
					/* Delete last Anchor */
					int i = ecu.getNumberOfCores() + 1;
					((AnchorContainer) pic).getAnchors().remove(i);
				}

				/* Virtual toggle for reverse */
				/*
				 * if ( ecu.getVirtual().equals( VirtualCategory.VISIBLE ) ) ecu.setVirtual(
				 * VirtualCategory.INVISIBLE ); else ecu.setVirtual( VirtualCategory.VISIBLE );
				 * 
				 * 
				 * Vector<Object> v = new Vector<Object>(); v.add( ecu );
				 * addGraphicalRepresentation( context, ecu );
				 */
			}
		}

		return null;
	}
}
