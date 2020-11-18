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
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

public class CoreIncreaseEcuFeature extends AbstractCreateFeature implements ICreateFeature {
	private PictogramElement pe;

	public CoreIncreaseEcuFeature(IFeatureProvider fp) {
		super(fp, "Core Increase", "Core Increase");
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

				if (ecu.getNumberOfCores() < 4) {
					ecu.setNumberOfCores(ecu.getNumberOfCores() + 1);
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
					/*
					 * Anchor test printing for (int
					 * i=0;i<((AnchorContainer)pic).getAnchors().size();i++)
					 * System.out.println("This: "+((AnchorContainer)pic).getAnchors().get(i).
					 * getClass());
					 */
					/* Anchor for Core */
					IPeCreateService peCreateService = Graphiti.getPeCreateService();
					IGaService gaService = Graphiti.getGaService();

					int i = ecu.getNumberOfCores() - 1;
					/* Create new Anchor */

					final BoxRelativeAnchor boxAnchor = peCreateService.createBoxRelativeAnchor((AnchorContainer) pic);
					boxAnchor.setRelativeWidth(0.0 + i * 0.15);
					boxAnchor.setRelativeHeight(0.0);

					final Rectangle rectangle = gaService.createPlainRectangle(boxAnchor);
					rectangle.setLineWidth(0);
					rectangle.setLineVisible(false);
					rectangle.setFilled(false);
					gaService.setLocationAndSize(rectangle, 0 + i * 10, 0, 25, 30);
				}

				/* Virtual toggle for reverse */
				/*
				 * if ( ecu.getVirtual().equals( VirtualCategory.VISIBLE ) ) ecu.setVirtual(
				 * VirtualCategory.INVISIBLE ); else ecu.setVirtual( VirtualCategory.VISIBLE );
				 * 
				 * Vector<Object> v = new Vector<Object>(); v.add( ecu );
				 * addGraphicalRepresentation( context, ecu );
				 * 
				 * return v.toArray();
				 */
			}
		}
		return null;
	}
}
