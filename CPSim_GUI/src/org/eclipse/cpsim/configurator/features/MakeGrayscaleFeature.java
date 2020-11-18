package org.eclipse.cpsim.configurator.features;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.CustomImageProvider;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.util.DiagramUtil;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class MakeGrayscaleFeature extends AbstractCustomFeature {

	public MakeGrayscaleFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Make Grayscale Feature";
	}

	/* Disable Undo because default undo doesn't support changing id */
	@Override
	public boolean canUndo(IContext context) {
		return false;
	}

	@Override
	public boolean hasDoneChanges() {
		return false;
	}

	@Override
	public boolean isAvailable(IContext context) {
		return false;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		if (RunStopSimulation.state == 0) {
			for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
				if (eobj instanceof SWC || eobj instanceof CAR || eobj instanceof CAN || eobj instanceof ECU) {
					if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CAR))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CARG);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CAN0))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CAN0G);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CAN1))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CAN1G);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CAN2))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CAN2G);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CAN3))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CAN3G);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_SWC))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_SWCG);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_MC1_ECU))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_MC1_ECUG);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_MC2_ECU))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_MC2_ECUG);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_MC3_ECU))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_MC3_ECUG);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_MC4_ECU))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_MC4_ECUG);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_REAL_MC1_ECU))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_REAL_MC1_ECUG);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_REAL_MC2_ECU))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_REAL_MC2_ECUG);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_REAL_MC3_ECU))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_REAL_MC3_ECUG);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_REAL_MC4_ECU))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_REAL_MC4_ECUG);
				}
			}
		} else {
			for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
				if (eobj instanceof SWC || eobj instanceof CAR || eobj instanceof CAN || eobj instanceof ECU) {
					if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CARG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CAR);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CAN0G))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CAN0);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CAN1G))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CAN1);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CAN2G))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CAN2);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_CAN3G))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_CAN3);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_SWCG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_SWC);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_MC1_ECUG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_MC1_ECU);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_MC2_ECUG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_MC2_ECU);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_MC3_ECUG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_MC3_ECU);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_MC4_ECUG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_MC4_ECU);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_REAL_MC1_ECUG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_REAL_MC1_ECU);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_REAL_MC2_ECUG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_REAL_MC2_ECU);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_REAL_MC3_ECUG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_REAL_MC3_ECU);
					else if (((Image) pic.getGraphicsAlgorithm()).getId().equals(CustomImageProvider.IMG_REAL_MC4_ECUG))
						((Image) pic.getGraphicsAlgorithm()).setId(CustomImageProvider.IMG_REAL_MC4_ECU);
				}
			}
		}
		// TODO Auto-generated method stub

	}

}
