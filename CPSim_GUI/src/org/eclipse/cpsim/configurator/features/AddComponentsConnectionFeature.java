package org.eclipse.cpsim.configurator.features;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.cpsim.menu.simulation.XmlMessages;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.IColorConstant;
import org.w3c.dom.Element;

public class AddComponentsConnectionFeature extends AbstractAddFeature implements IAddFeature {

	public AddComponentsConnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		IAddConnectionContext addConContext = (IAddConnectionContext) context;
		Anchor sourceAnchor = addConContext.getSourceAnchor();

		/* Check sourceAnchor is SWC && there is connection with the SWC */
		for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
			if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement().equals(pic)) {
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
				if (eobj instanceof SWC && sourceAnchor.getOutgoingConnections().size() != 0) {
					return false;

				}

				if (eobj instanceof ECU || eobj instanceof CAR || eobj instanceof CAN || eobj instanceof SWC) {
					for (Connection s : ((AnchorContainer) pic).getAnchors().get(0).getIncomingConnections()) {

						Anchor distinationAnchor = ((FreeFormConnection) s).getStart();
						if (distinationAnchor.equals(addConContext.getTargetAnchor()))
							return false;
					}
					for (Connection s : ((AnchorContainer) pic).getAnchors().get(0).getOutgoingConnections()) {

						Anchor distinationAnchor = ((FreeFormConnection) s).getEnd();
						if (distinationAnchor.equals(addConContext.getTargetAnchor()))
							return false;
					}
				}
			}
		}

		return context instanceof IAddConnectionContext && context.getNewObject() instanceof EReference;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		IAddConnectionContext addConContext = (IAddConnectionContext) context;
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		IGaService gaService = Graphiti.getGaService();

		FreeFormConnection connection = peCreateService.createFreeFormConnection(getDiagram());
		Anchor sourceAnchor = addConContext.getSourceAnchor();
		Anchor targetAnchor = addConContext.getTargetAnchor();
		connection.setStart(sourceAnchor);
		connection.setEnd(targetAnchor);

		AnchorContainer startParent = addConContext.getSourceAnchor().getParent();
		AnchorContainer endParent = addConContext.getTargetAnchor().getParent();
		EObject s = (EObject) getBusinessObjectForPictogramElement(startParent);
		EObject e = (EObject) getBusinessObjectForPictogramElement(endParent);

		/* EResource refresh */
		if (!s.eResource().getContents().contains(s))
			s.eResource().getContents().add(s);
		if (!s.eResource().getContents().contains(e))
			s.eResource().getContents().add(e);

		/* Find the index of CAN */
		int idx = 0;
		CAN can = null;
		// Add bendpoints for making orthgonal style
		if (s instanceof ECU && e instanceof CAN) {
			can = (CAN) e;
			idx++;
			for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
				if (eobj instanceof CAN) {
					CAN cCAN = (CAN) eobj;
					if (can.equals(cCAN))
						break;
					idx++;
				}
			}

			int x = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(sourceAnchor).getX() + (idx - 1) * 10;
			int y = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getY();
			Point bendpoint = Graphiti.getCreateService().createPoint(x, y);
			connection.getBendpoints().add(bendpoint);

			if (idx > 1) {
				int x2 = x;
				int y2 = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(sourceAnchor).getY() + 35;
				bendpoint = Graphiti.getCreateService().createPoint(x2, y2);
				connection.getBendpoints().add(0, bendpoint);
			}
		} else if (s instanceof CAN && e instanceof ECU) {
			can = (CAN) s;
			idx++;
			for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
				if (eobj instanceof CAN) {
					CAN cCAN = (CAN) eobj;
					if (can.equals(cCAN))
						break;
					idx++;
				}
			}

			int x = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getX() + (idx - 1) * 10;
			int y = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(sourceAnchor).getY();
			Point bendpoint = Graphiti.getCreateService().createPoint(x, y);
			connection.getBendpoints().add(bendpoint);

			if (idx > 1) {
				int x2 = x;
				int y2 = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getY() + 35;
				bendpoint = Graphiti.getCreateService().createPoint(x2, y2);
				connection.getBendpoints().add(bendpoint);
			}
		} else if (s instanceof CAR && e instanceof CAN) {
			int x = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getX();
			int y = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(sourceAnchor).getY();
			Point bendpoint = Graphiti.getCreateService().createPoint(x, y);
			connection.getBendpoints().add(bendpoint);
		} else if (s instanceof CAN && e instanceof CAR) {
			int x = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(sourceAnchor).getX();
			int y = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getY();
			Point bendpoint = Graphiti.getCreateService().createPoint(x, y);
			connection.getBendpoints().add(bendpoint);
		}

		/* Ref update */
		if (s instanceof SWC) {
			ECU ecu = (ECU) e;
			SWC swc = (SWC) s;
			ecu.getSwcRefs().add(swc);
			swc.setEcuRef(ecu);
		} else if (e instanceof SWC) {
			ECU ecu = (ECU) s;
			SWC swc = (SWC) e;
			ecu.getSwcRefs().add(swc);
			swc.setEcuRef(ecu);
		}

		Polyline polyline = gaService.createPlainPolyline(connection);
		if (idx > 0) {
			polyline.setTransparency(150.0);
			polyline.setLineWidth(7);
		} else {
			polyline.setLineWidth(3);
		}
		IColorConstant[] canColors = { IColorConstant.BLACK, IColorConstant.RED, IColorConstant.GREEN,
				IColorConstant.CYAN, IColorConstant.BLUE };
		polyline.setForeground(manageColor(canColors[idx]));

		/*
		 * for ( PictogramElement pic :
		 * Graphiti.getPeService().getAllContainedPictogramElements( getDiagram() ) ) {
		 * EObject eobj =
		 * Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement( pic );
		 * }
		 */

		/*
		 * EReference addedDomainObjectConnection = (EReference) context.getNewObject();
		 * link ( connection, addedDomainObjectConnection );
		 */

		return connection;
	}

}
