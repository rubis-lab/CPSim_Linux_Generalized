package org.eclipse.cpsim.configurator.features;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CustomImageProvider;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

public class AddCanFeature extends AbstractAddFeature implements IAddFeature {
	private final int CAN_LIMIT = 4;

	private static final IColorConstant CAN_FOREGROUND = new ColorConstant(0, 0, 0);

	public AddCanFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		int idx = 0;
		for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			if (eobj instanceof CAN)
				idx++;
		}
		if (idx >= CAN_LIMIT)
			return false;

		return context.getNewObject() instanceof CAN;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		CAN can = (CAN) context.getNewObject();

		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);

		/* Image */
		/*
		 * int width = CustomImageProvider.CAN_WIDTH; int height =
		 * CustomImageProvider.CAN_HEIGHT; Image image = gaService.createImage(
		 * containerShape, CustomImageProvider.IMG_CAN );
		 */
		int width = CustomImageProvider.NCAN_WIDTH;
		int height = CustomImageProvider.NCAN_HEIGHT;

		String[] existsPaths = new String[CAN_LIMIT];
		/* Find the index of CAN */
		int i = 0;
		if (can != null) {
			for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
				if (eobj instanceof CAN) {
					Image b = (Image) pic.getGraphicsAlgorithm();
					existsPaths[i++] = b.getId();
					System.out.println(b.getId());

				}
			}
		}

		String[] canPaths = { CustomImageProvider.IMG_CAN0, CustomImageProvider.IMG_CAN1, CustomImageProvider.IMG_CAN2,
				CustomImageProvider.IMG_CAN3 };

		int idx = 0;
		for (int j = 0; j < CAN_LIMIT; j++) {
			if (canPaths[idx].equals(existsPaths[j])) {
				idx++;
				j = -1;
			}
		}

		Image image = gaService.createImage(containerShape, canPaths[idx]);
		// gaService.setLocationAndSize( image, context.getX(), context.getY(), width,
		// height );
		gaService.setLocationAndSize(image, 0, context.getY(), width, height);

		if (can.eResource() == null) {
			getDiagram().eResource().getContents().add(can);
		}

		link(containerShape, can);

		/* Text */
		Shape shape = peCreateService.createShape(containerShape, false);
		Text text = gaService.createText(shape, can.getId());
		text.setForeground(manageColor(CAN_FOREGROUND));
		text.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		text.setVerticalAlignment(Orientation.ALIGNMENT_BOTTOM);
		gaService.setLocationAndSize(text, 0, 0, width, height);

		peCreateService.createChopboxAnchor(containerShape);

		return containerShape;
	}
}
