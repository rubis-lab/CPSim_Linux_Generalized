package org.eclipse.cpsim.configurator.features;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cpsim.Diagram.CustomImageProvider;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
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

public class AddEcuFeature extends AbstractAddFeature implements IAddFeature {

	private static final IColorConstant ECU_FOREGROUND = new ColorConstant(0, 0, 0);

	public AddEcuFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		return context.getNewObject() instanceof ECU;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		Diagram targetDiagram = (Diagram) context.getTargetContainer();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		ECU ecu = (ECU) context.getNewObject();

		/* Delete the pictogram if pictogram which has same ECU object already exists */
		List<Connection> reconnections = null;
		ArrayList<Boolean> isStartPosition = new ArrayList<Boolean>();
		ArrayList<Integer> connectedAnchors = new ArrayList<Integer>();
		int x = -1, y = -1;
		for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			if (eobj instanceof ECU) {
				ECU cECU = (ECU) eobj;
				if (ecu.equals(cECU)) {
					x = pic.getGraphicsAlgorithm().getX();
					y = pic.getGraphicsAlgorithm().getY();

					/* Virtual toggle */
					if (ecu.getVirtual().equals(VirtualCategory.VISIBLE))
						ecu.setVirtual(VirtualCategory.INVISIBLE);
					else
						ecu.setVirtual(VirtualCategory.VISIBLE);

					/* Collect connections for reconnecting */
					reconnections = Graphiti.getPeService().getAllConnections((AnchorContainer) pic);
					Iterator<Connection> conIter = reconnections.iterator();
					while (conIter.hasNext()) {
						Connection con = conIter.next();
//						Anchor picAnchor = Graphiti.getPeService().getChopboxAnchor( (AnchorContainer)pic );
						AnchorContainer ac = (AnchorContainer) pic;
						EList<Anchor> anchors = ac.getAnchors();

						boolean isStart = false;
						for (int i = 0; i < anchors.size(); i++) {
							Anchor coreAnchor = anchors.get(i);
							if (coreAnchor.equals(con.getStart())) {
								isStart = true;
								connectedAnchors.add(i);
							} else if (coreAnchor.equals(con.getEnd())) {
								connectedAnchors.add(i);
							}
						}
						if (isStart)
							isStartPosition.add(true);
						else
							isStartPosition.add(false);

						/*
						 * if ( picAnchor.equals( con.getStart() ) ) isStartPosition.add( true ); else
						 * isStartPosition.add( false );
						 */
					}

					Graphiti.getPeService().deletePictogramElement(pic);
				}
			}
		}

		ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);

		/* Image */
		/*
		 * int width = CustomImageProvider.ECU_WIDTH; int height =
		 * CustomImageProvider.ECU_HEIGHT;
		 */
		int width = CustomImageProvider.MECU_WIDTH;
		int height = CustomImageProvider.MECU_HEIGHT;
		int context_x, context_y;

		/* Set coordinates */
		if (x != -1 && y != -1) {
			context_x = x;
			context_y = y;
		} else {
			context_x = context.getX();
			context_y = context.getY();
		}

		if (ecu.getNumberOfCores() == 0)
			ecu.setNumberOfCores(1);
		/* Create the image according to ECU type */
		if (ecu.getVirtual().equals(VirtualCategory.VISIBLE)) {
			Image image;
			switch (ecu.getNumberOfCores()) {
			case 1:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_MC1_ECU);
				break;
			case 2:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_MC2_ECU);
				break;
			case 3:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_MC3_ECU);
				break;
			case 4:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_MC4_ECU);
				break;
			default:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_MC4_ECU);
				break;
			}
			gaService.setLocationAndSize(image, context_x, context_y, width, height);
		} else {
			Image image;
			switch (ecu.getNumberOfCores()) {
			case 1:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_REAL_MC1_ECU);
				break;
			case 2:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_REAL_MC2_ECU);
				break;
			case 3:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_REAL_MC3_ECU);
				break;
			case 4:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_REAL_MC4_ECU);
				break;
			default:
				image = gaService.createImage(containerShape, CustomImageProvider.IMG_REAL_MC4_ECU);
				break;
			}
			gaService.setLocationAndSize(image, context_x, context_y, width, height);
		}

		if (ecu.eResource() == null) {
			getDiagram().eResource().getContents().add(ecu);
		}

		/* Text */
		Shape shape = peCreateService.createShape(containerShape, false);
		Text text = gaService.createText(shape, ecu.getId());
		text.setForeground(manageColor(ECU_FOREGROUND));
		text.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
		text.setVerticalAlignment(Orientation.ALIGNMENT_BOTTOM);
		gaService.setLocationAndSize(text, 0, 35, width, height);

		peCreateService.createChopboxAnchor(containerShape);

		/* Anchor for Core */
		for (int i = 0; i < ecu.getNumberOfCores(); i++) {
			final BoxRelativeAnchor boxAnchor = peCreateService.createBoxRelativeAnchor(containerShape);
			boxAnchor.setRelativeWidth(0.0 + i * 0.15);
			boxAnchor.setRelativeHeight(0.0);

			final Rectangle rectangle = gaService.createInvisibleRectangle(boxAnchor);
			gaService.setLocationAndSize(rectangle, 0 + i * 10, 0, 25, 30);
		}

		/* Link */
		link(containerShape, ecu);

		if (reconnections != null) {
			// Anchor ac = Graphiti.getPeService().getChopboxAnchor((AnchorContainer)
			// containerShape);
			AnchorContainer ac = (AnchorContainer) containerShape;

			int idx = 0;
			Iterator<Connection> conIter = reconnections.iterator();
			while (conIter.hasNext()) {
				Connection con = conIter.next();
				int anchorIdx = connectedAnchors.get(idx);
				Anchor cac = ac.getAnchors().get(anchorIdx);
				if (isStartPosition.get(idx))
					con.setStart(cac);
				else
					con.setEnd(cac);

				idx++;
			}
		}

		return containerShape;
	}
}
