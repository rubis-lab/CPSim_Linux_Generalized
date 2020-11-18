package org.eclipse.cpsim.configurator.features;

import org.eclipse.cpsim.Diagram.CustomImageProvider;
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
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class SwitchSwcFeature extends AbstractCreateFeature implements ICreateFeature {

	private PictogramElement pe = null;

	public SwitchSwcFeature(IFeatureProvider fp) {
		super(fp, "CopySWC", "CopySWC");
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

	@Override
	public Object[] create(ICreateContext context) {
		for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
			if (!pe.equals(pic))
				continue;
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			/* SWC */
			if (eobj instanceof SWC) {
				SWC ecu = (SWC) eobj;
				Image b = (Image) pe.getGraphicsAlgorithm();
				if (ecu.getVirtual().equals(VirtualCategory.VISIBLE)) {
					ecu.setVirtual(VirtualCategory.INVISIBLE);
					if (b.getId().equals(CustomImageProvider.IMG_SWC))
						b.setId(CustomImageProvider.IMG_REAL_SWC);
				} else if (ecu.getVirtual().equals(VirtualCategory.INVISIBLE)) {
					ecu.setVirtual(VirtualCategory.VISIBLE);
					if (b.getId().equals(CustomImageProvider.IMG_REAL_SWC))
						b.setId(CustomImageProvider.IMG_SWC);
				}

			}
		}
		return null;
	}
}
