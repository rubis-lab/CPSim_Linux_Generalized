package org.eclipse.cpsim.configurator.features;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
import org.eclipse.graphiti.mm.pictograms.ChopboxAnchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

public class CreateComponentsConnectionFeature extends AbstractCreateConnectionFeature
		implements ICreateConnectionFeature {
	private final int CAR_IDX = 0;
	private final int CAN_IDX = 1;
	private final int ECU_IDX = 2;
	private final int SWC_IDX = 3;

	public CreateComponentsConnectionFeature(IFeatureProvider fp) {
		super(fp, "Connection", "Creates a new Connection between two Components");
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		if (getEObject(context.getSourceAnchor()) != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		// return true if both anchors belong to a EClass
		// and those EClasses are not identical
		EObject source = getEObject(context.getSourceAnchor());
		EObject target = getEObject(context.getTargetAnchor());
		if (source != null && target != null
				&& source.eClass().getClassifierID() != target.eClass().getClassifierID()) {
			AnchorContainer startParent = context.getSourceAnchor().getParent();
			AnchorContainer endParent = context.getTargetAnchor().getParent();
			Object startNode = getBusinessObjectForPictogramElement(startParent);
			Object endNode = getBusinessObjectForPictogramElement(endParent);

			if (startNode instanceof CAR || endNode instanceof CAR) {
				if (startNode instanceof CAN || endNode instanceof CAN) {
					return true;
				}
			} // CAR

			else if (startNode instanceof CAN) {
				if (endNode instanceof ECU && context.getTargetAnchor() instanceof ChopboxAnchor) {
					return true;
				}
			} else if (endNode instanceof CAN) {
				if (startNode instanceof ECU && context.getSourceAnchor() instanceof ChopboxAnchor) {
					return true;
				}
			} // CAN

			else if (startNode instanceof ECU) {
				if (endNode instanceof SWC && context.getSourceAnchor() instanceof BoxRelativeAnchor) {
					return true;
				}
			} else if (endNode instanceof ECU) {
				if (startNode instanceof SWC && context.getTargetAnchor() instanceof BoxRelativeAnchor) {
					return true;
				}
			}
			// ECU
		}

		return false;
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		Connection newConnection = null;

		// get EClasses which should be connected
		AnchorContainer sourceParent = context.getSourceAnchor().getParent();
		AnchorContainer targetParent = context.getTargetAnchor().getParent();
		Object sourceObj = getBusinessObjectForPictogramElement(sourceParent);
		Object targetObj = getBusinessObjectForPictogramElement(targetParent);

		CAR carSource = null;
		CAN canSource = null;
		ECU ecuSource = null;
		SWC swcSource = null;
		int locatedSourceIdx = -1;
		CAR carTarget = null;
		CAN canTarget = null;
		ECU ecuTarget = null;
		SWC swcTarget = null;
		int locatedTargetIdx = -1;

		/* Source */
		if (sourceObj instanceof CAR) {
			carSource = (CAR) sourceObj;
			locatedSourceIdx = CAR_IDX;
		} else if (sourceObj instanceof CAN) {
			canSource = (CAN) sourceObj;
			locatedSourceIdx = CAN_IDX;
		} else if (sourceObj instanceof ECU) {
			ecuSource = (ECU) sourceObj;
			locatedSourceIdx = ECU_IDX;
		} else if (sourceObj instanceof SWC) {
			swcSource = (SWC) sourceObj;
			locatedSourceIdx = SWC_IDX;
		}

		/* Target */
		if (targetObj instanceof CAR) {
			carTarget = (CAR) targetObj;
			locatedTargetIdx = CAR_IDX;
			carTarget.setCanRef(canSource);
			canSource.setCarRef(carTarget);
		} else if (targetObj instanceof CAN) {
			canTarget = (CAN) targetObj;
			locatedTargetIdx = CAN_IDX;
			if (locatedSourceIdx == CAR_IDX) {
				canTarget.setCarRef(carSource);
				carSource.setCanRef(canTarget);
			} else if (locatedSourceIdx == ECU_IDX) {
				if (!canTarget.getEcuRefs().contains(ecuSource))
					canTarget.getEcuRefs().add(ecuSource);
				ecuSource.setCanRef(canTarget);
			}
		} else if (targetObj instanceof ECU) {
			ecuTarget = (ECU) targetObj;
			locatedTargetIdx = ECU_IDX;
			if (locatedSourceIdx == CAN_IDX) {
				ecuTarget.setCanRef(canSource);
				if (!canSource.getEcuRefs().contains(ecuTarget))
					canSource.getEcuRefs().add(ecuTarget);
			} else if (locatedSourceIdx == SWC_IDX) {
				if (!ecuTarget.getSwcRefs().contains(swcSource))
					ecuTarget.getSwcRefs().add(swcSource);
				swcSource.setEcuRef(ecuTarget);
			}
		} else if (targetObj instanceof SWC) {
			swcTarget = (SWC) targetObj;
			locatedTargetIdx = SWC_IDX;
			swcTarget.setEcuRef(ecuSource);
			if (!ecuSource.getSwcRefs().contains(swcTarget))
				ecuSource.getSwcRefs().add(swcTarget);
		}

		// Linking
		EObject[] sourceList = { carSource, canSource, ecuSource, swcSource };
		EObject[] targetList = { carTarget, canTarget, ecuTarget, swcTarget };

		if (locatedSourceIdx != -1 && locatedTargetIdx != -1) {
			// create new business object
			EReference eReference = createEReference(sourceList[locatedSourceIdx], targetList[locatedTargetIdx]);

			// add connection for business object
			AddConnectionContext addContext = new AddConnectionContext(context.getSourceAnchor(),
					context.getTargetAnchor());
			addContext.setNewObject(eReference);
			newConnection = (Connection) getFeatureProvider().addIfPossible(addContext);
		}

		return newConnection;
	}

	private EObject getEObject(Anchor anchor) {
		if (anchor != null) {
			Object obj = getBusinessObjectForPictogramElement(anchor.getParent());
			if (obj instanceof EObject)
				return (EObject) obj;
		}
		return null;
	}

	private EReference createEReference(EObject source, EObject target) {
		EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		eReference.setName("new Reference");
		eReference.setEType(source.eClass());
		eReference.setLowerBound(0);
		eReference.setUpperBound(1);
		source.eClass().getEStructuralFeatures().add(eReference);
		return eReference;
	}

}
