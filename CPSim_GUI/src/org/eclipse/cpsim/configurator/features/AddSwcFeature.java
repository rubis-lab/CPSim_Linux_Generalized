package org.eclipse.cpsim.configurator.features;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cpsim.Diagram.CustomImageProvider;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public class AddSwcFeature extends AbstractAddFeature implements IAddFeature {

	private static final IColorConstant SWC_FOREGROUND = new ColorConstant(0, 0, 0);

	public AddSwcFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		return context.getNewObject() instanceof SWC;
	}

	@Override
	public PictogramElement add(IAddContext context) {

		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		SWC swc = (SWC) context.getNewObject();

		/* Delete the pictogram if pictogram which has same SWC object already exists */
		List<Connection> reconnections = null;
		ArrayList<Boolean> isStartPosition = new ArrayList<Boolean>();
		int x = -1, y = -1;
		for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			if (eobj instanceof SWC) {
				SWC cSWC = (SWC) eobj;
				if (swc.equals(cSWC)) {
					x = pic.getGraphicsAlgorithm().getX();
					y = pic.getGraphicsAlgorithm().getY();

					/* Virtual toggle */
					if (swc.getEcuRef() != null && swc.getEcuRef().getVirtual().equals(VirtualCategory.INVISIBLE)) {
						swc.setVirtual(VirtualCategory.INVISIBLE);
					} else {
						if (swc.getVirtual().equals(VirtualCategory.VISIBLE))
							swc.setVirtual(VirtualCategory.INVISIBLE);
						else
							swc.setVirtual(VirtualCategory.VISIBLE);
					}

					/* Collect connections for reconnecting */
					reconnections = Graphiti.getPeService().getAllConnections((AnchorContainer) pic);
					Iterator<Connection> conIter = reconnections.iterator();
					while (conIter.hasNext()) {
						Connection con = conIter.next();
						Anchor picAnchor = Graphiti.getPeService().getChopboxAnchor((AnchorContainer) pic);
						if (picAnchor.equals(con.getStart()))
							isStartPosition.add(true);
						else
							isStartPosition.add(false);
					}

					Graphiti.getPeService().deletePictogramElement(pic);
				}
			}
		}

		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);

		/* Image */
		int width = CustomImageProvider.SWC_WIDTH;
		int height = CustomImageProvider.SWC_HEIGHT;
		int context_x, context_y;

		/* Set coordinates */
		if (x != -1 && y != -1) {
			context_x = x;
			context_y = y;
		} else {
			context_x = context.getX();
			context_y = context.getY();
		}

		/* Create the image according to ECU type */
		if (swc.getVirtual().equals(VirtualCategory.VISIBLE)) {
			Image image = gaService.createImage(containerShape, CustomImageProvider.IMG_SWC);
			gaService.setLocationAndSize(image, context_x, context_y, width, height);
		} else {
			Image image = gaService.createImage(containerShape, CustomImageProvider.IMG_REAL_SWC);
			gaService.setLocationAndSize(image, context_x, context_y, width, height);
		}

		if (swc.eResource() == null) {
			getDiagram().eResource().getContents().add(swc);
		}

		/* Text */
		Shape shape = peCreateService.createShape(containerShape, false);
		Text text = gaService.createText(shape, swc.getId());
		text.setForeground(manageColor(SWC_FOREGROUND));
		text.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
		gaService.setLocationAndSize(text, 0, -25, width, height);

		peCreateService.createChopboxAnchor(containerShape);

		link(containerShape, swc);

		if (reconnections != null) {
			Anchor ac = Graphiti.getPeService().getChopboxAnchor((AnchorContainer) containerShape);

			int idx = 0;
			Iterator<Connection> conIter = reconnections.iterator();
			while (conIter.hasNext()) {
				Connection con = conIter.next();
				if (isStartPosition.get(idx++))
					con.setStart(ac);
				else
					con.setEnd(ac);
			}
		}

		return containerShape;
	}
}
