package org.eclipse.cpsim.configurator.features;

import org.eclipse.cpsim.Diagram.CustomImageProvider;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class SwitchEcuFeature extends AbstractCreateFeature implements ICreateFeature {

	private PictogramElement pe = null;

	public SwitchEcuFeature(IFeatureProvider fp) {
		super(fp, "CopyECU", "CopyECU");
	}

	public void setPictogram(PictogramElement pe_) {
		pe = pe_;
	}

	/* Disable Undo because default undo doesn't support changing id */
	@Override
	public boolean canUndo(IContext context) {
		return false;
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

	private void setSWC(ECU ecu, VirtualCategory cat) {
		/* Opposite side of cat */
		VirtualCategory notcat;
		if (cat.equals(VirtualCategory.VISIBLE))
			notcat = VirtualCategory.INVISIBLE;
		else if (cat.equals(VirtualCategory.INVISIBLE))
			notcat = VirtualCategory.VISIBLE;
		else
			notcat = VirtualCategory.VISIBLE;

		for (Anchor anc : ((AnchorContainer) pe).getAnchors()) {
			for (Connection con : anc.getIncomingConnections()) {
				FreeFormConnection a = (FreeFormConnection) con;
				Anchor sourceAnchor = a.getStart();

				for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
					if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement().equals(pic)) {
						EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
						if (eobj instanceof SWC) {
							SWC swc = (SWC) eobj;
							/* swc : connected SWCS with ecu */

							for (PictogramElement pics : Graphiti.getPeService()
									.getAllContainedPictogramElements(getDiagram())) {
								EObject eobjs = Graphiti.getLinkService()
										.getBusinessObjectForLinkedPictogramElement(pics);
								if (swc.equals(eobjs) && swc.getVirtual().equals(notcat)) {
									swc.setVirtual(cat);
									if (cat.equals(VirtualCategory.VISIBLE)) {
										((Image) pics.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_SWC);
									} else {
										((Image) pics.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_REAL_SWC);

									}
									// got pics, swc
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public Object[] create(ICreateContext context) {
		for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
			if (!pe.equals(pic))
				continue;
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			/* ECU */
			if (eobj instanceof ECU) {
				ECU ecu = (ECU) eobj;
				Image b = (Image) pe.getGraphicsAlgorithm();
				if (ecu.getVirtual().equals(VirtualCategory.VISIBLE)) {
					ecu.setVirtual(VirtualCategory.INVISIBLE);
					if (b.getId().equals(CustomImageProvider.IMG_MC1_ECU))
						b.setId(CustomImageProvider.IMG_REAL_MC1_ECU);
					else if (b.getId().equals(CustomImageProvider.IMG_MC2_ECU))
						b.setId(CustomImageProvider.IMG_REAL_MC2_ECU);
					else if (b.getId().equals(CustomImageProvider.IMG_MC3_ECU))
						b.setId(CustomImageProvider.IMG_REAL_MC3_ECU);
					else if (b.getId().equals(CustomImageProvider.IMG_MC4_ECU))
						b.setId(CustomImageProvider.IMG_REAL_MC4_ECU);

					setSWC(ecu, VirtualCategory.INVISIBLE);
				} else if (ecu.getVirtual().equals(VirtualCategory.INVISIBLE)) {
					ecu.setVirtual(VirtualCategory.VISIBLE);
					if (b.getId().equals(CustomImageProvider.IMG_REAL_MC1_ECU))
						b.setId(CustomImageProvider.IMG_MC1_ECU);
					else if (b.getId().equals(CustomImageProvider.IMG_REAL_MC2_ECU))
						b.setId(CustomImageProvider.IMG_MC2_ECU);
					else if (b.getId().equals(CustomImageProvider.IMG_REAL_MC3_ECU))
						b.setId(CustomImageProvider.IMG_MC3_ECU);
					else if (b.getId().equals(CustomImageProvider.IMG_REAL_MC4_ECU))
						b.setId(CustomImageProvider.IMG_MC4_ECU);

					setSWC(ecu, VirtualCategory.VISIBLE);
				}

			}
		}

		return null;
	}
}
