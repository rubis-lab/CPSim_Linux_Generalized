package org.eclipse.cpsim.configurator.features;

import org.eclipse.cpsim.Diagram.CAR;
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

public class AddCarFeature extends AbstractAddFeature implements IAddFeature {

	private static final IColorConstant CAR_FOREGROUND = new ColorConstant(0, 0, 0);

	public AddCarFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			if (eobj instanceof CAR)
				return false;
		}
		return context.getNewObject() instanceof CAR;
	}

	@Override
	public PictogramElement add(IAddContext context) {

		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		CAR car = (CAR) context.getNewObject();

		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);

		/* Image */
		int width = CustomImageProvider.CAR_WIDTH;
		int height = CustomImageProvider.CAR_HEIGHT;
		Image image = gaService.createImage(containerShape, CustomImageProvider.IMG_CAR);
		gaService.setLocationAndSize(image, context.getX(), context.getY(), width, height);

		if (car.eResource() == null) {
			getDiagram().eResource().getContents().add(car);
		}

		link(containerShape, car);

		/* Text */
		{
			Shape shape = peCreateService.createShape(containerShape, false);
			Text text = gaService.createText(shape, car.getId());
			text.setForeground(manageColor(CAR_FOREGROUND));
			text.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
			text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
			gaService.setLocationAndSize(text, 0, 0, width, height);
		}

		peCreateService.createChopboxAnchor(containerShape);

		return containerShape;
	}
}
