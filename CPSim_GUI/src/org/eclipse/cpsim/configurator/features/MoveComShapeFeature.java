package org.eclipse.cpsim.configurator.features;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class MoveComShapeFeature extends DefaultMoveShapeFeature {
	public MoveComShapeFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canMoveShape(IMoveShapeContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		return super.canMoveShape(context);
	}

	@Override
	protected void postMoveShape(IMoveShapeContext context) {
		super.postMoveShape(context);

		/* Filtering */
		EObject eObj = (EObject) getBusinessObjectForPictogramElement(context.getPictogramElement());
		if (eObj instanceof SWC || eObj instanceof CAR)
			return;

		/* Find the index of CAN */
		int idx = 0;
		CAN can = null;

		/* Anchor iterator */
		for (Anchor anchor : context.getShape().getAnchors()) {
			/* Case of Input port */
			for (Connection ic : anchor.getIncomingConnections()) {
				Anchor targetAnchor = ic.getStart();

				AnchorContainer startParent = anchor.getParent();
				AnchorContainer endParent = targetAnchor.getParent();
				EObject s = (EObject) getBusinessObjectForPictogramElement(startParent);
				EObject e = (EObject) getBusinessObjectForPictogramElement(endParent);

				FreeFormConnection ffc = (FreeFormConnection) ic;
				/* Add bendpoints for making orthgonal style */
				if (s instanceof ECU && e instanceof CAN) {
					can = (CAN) e;
					idx++;
					for (PictogramElement pic : Graphiti.getPeService()
							.getAllContainedPictogramElements(getDiagram())) {
						EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
						if (eobj instanceof CAN) {
							CAN cCAN = (CAN) eobj;
							if (can.equals(cCAN))
								break;
							idx++;
						}
					}

					ffc.getBendpoints().clear();
					int x = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(anchor).getX() + (idx - 1) * 10;
					int y = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getY();
					Point bendpoint = Graphiti.getCreateService().createPoint(x, y);
					ffc.getBendpoints().add(bendpoint);

					if (idx > 1) {
						int x2 = x;
						int y2 = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(anchor).getY() + 35;
						bendpoint = Graphiti.getCreateService().createPoint(x2, y2);
						ffc.getBendpoints().add(0, bendpoint);
					}
					idx = 0;
				} else if (s instanceof CAN && e instanceof ECU) {
					can = (CAN) s;
					idx++;
					for (PictogramElement pic : Graphiti.getPeService()
							.getAllContainedPictogramElements(getDiagram())) {
						EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
						if (eobj instanceof CAN) {
							CAN cCAN = (CAN) eobj;
							if (can.equals(cCAN))
								break;
							idx++;
						}
					}

					ffc.getBendpoints().clear();
					int x = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getX()
							+ (idx - 1) * 10;
					int y = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(anchor).getY();
					Point bendpoint = Graphiti.getCreateService().createPoint(x, y);
					ffc.getBendpoints().add(bendpoint);

					if (idx > 1) {
						int x2 = x;
						int y2 = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getY() + 35;
						bendpoint = Graphiti.getCreateService().createPoint(x2, y2);
						ffc.getBendpoints().add(0, bendpoint);
					}
					idx = 0;
				}
			}

			/* Case of Output port */
			for (Connection oc : anchor.getOutgoingConnections()) {
				Anchor targetAnchor = oc.getEnd();

				AnchorContainer startParent = anchor.getParent();
				AnchorContainer endParent = targetAnchor.getParent();
				EObject s = (EObject) getBusinessObjectForPictogramElement(startParent);
				EObject e = (EObject) getBusinessObjectForPictogramElement(endParent);

				FreeFormConnection ffc = (FreeFormConnection) oc;
				/* Add bendpoints for making orthgonal style */
				if (s instanceof ECU && e instanceof CAN) {
					can = (CAN) e;
					idx++;
					for (PictogramElement pic : Graphiti.getPeService()
							.getAllContainedPictogramElements(getDiagram())) {
						EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
						if (eobj instanceof CAN) {
							CAN cCAN = (CAN) eobj;
							if (can.equals(cCAN))
								break;
							idx++;
						}
					}

					ffc.getBendpoints().clear();
					int x = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(anchor).getX() + (idx - 1) * 10;
					int y = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getY();
					Point bendpoint = Graphiti.getCreateService().createPoint(x, y);
					ffc.getBendpoints().add(bendpoint);

					if (idx > 1) {
						int x2 = x;
						int y2 = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(anchor).getY() + 35;
						bendpoint = Graphiti.getCreateService().createPoint(x2, y2);
						ffc.getBendpoints().add(0, bendpoint);
					}
					idx = 0;
				} else if (s instanceof CAN && e instanceof ECU) {
					can = (CAN) s;
					idx++;
					for (PictogramElement pic : Graphiti.getPeService()
							.getAllContainedPictogramElements(getDiagram())) {
						EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
						if (eobj instanceof CAN) {
							CAN cCAN = (CAN) eobj;
							if (can.equals(cCAN))
								break;
							idx++;
						}
					}

					ffc.getBendpoints().clear();
					int x = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getX()
							+ (idx - 1) * 10;
					int y = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(anchor).getY();
					Point bendpoint = Graphiti.getCreateService().createPoint(x, y);
					ffc.getBendpoints().add(bendpoint);

					if (idx > 1) {
						int x2 = x;
						int y2 = Graphiti.getPeLayoutService().getLocationRelativeToDiagram(targetAnchor).getY() + 35;
						bendpoint = Graphiti.getCreateService().createPoint(x2, y2);
						ffc.getBendpoints().add(0, bendpoint);
					}
					idx = 0;
				}
			}
		}
		/* Anchor iteration end */
	}

}
