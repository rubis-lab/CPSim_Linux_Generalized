package org.eclipse.cpsim.configurator.features;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.dialog.CANPropertiesDialog;
import org.eclipse.cpsim.Diagram.dialog.CARPropertiesDialog;
import org.eclipse.cpsim.Diagram.dialog.ECUPropertiesDialog;
import org.eclipse.cpsim.Diagram.dialog.PropertiesDialog;
import org.eclipse.cpsim.Diagram.dialog.SWCPropertiesDialog;
import org.eclipse.cpsim.Diagram.util.DiagramUtil;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class DrillDownOpenPropertiesFeature extends AbstractDrillDownFeature {
	private boolean changed = false;

	public DrillDownOpenPropertiesFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Property Settings";
	}

	/* Disable Undo because default undo doesn't support changing id */
	@Override
	public boolean canUndo(IContext context) {
		return false;
	}

	@Override
	protected Collection<Diagram> getDiagrams() {
		Collection<Diagram> result = Collections.emptyList();
		Resource resource = getDiagram().eResource();
		URI uri = resource.getURI();
		URI uriTrimmed = uri.trimFragment();
		if (uriTrimmed.isPlatformResource()) {
			String platformString = uriTrimmed.toPlatformString(true);
			IResource fileResource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
			if (fileResource != null) {
				IProject project = fileResource.getProject();
				result = DiagramUtil.getDiagrams(project);
			}
		}
		return result;
	}

	@Override
	public boolean isAvailable(IContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		ICustomContext icontext = (ICustomContext) context;
		PictogramElement[] pes = icontext.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SWC)
				return true;
			else if (bo instanceof CAR)
				return true;
			else if (bo instanceof CAN)
				return true;
			else if (bo instanceof ECU)
				return true;

		}

		return false;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public boolean hasDoneChanges() {
		return changed;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		final EObject obj = (EObject) getBusinessObjectForPictogramElement(pes[0]);

		try {
			// SHOULD WE NEED TO IMPLEMENT THIS WITH THREAD??

			Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			PropertiesDialog dialog;
			if (obj instanceof CAR) {
				dialog = new CARPropertiesDialog(activeShell, obj);
				if (dialog.open() == 0)
					changed = true;
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pes[0]);
				if (obj.equals(eobj)) {
					ContainerShape containerShape = (ContainerShape) pes[0];
					EList<Shape> shapes = containerShape.getChildren();
					for (Shape s : shapes) {
						// System.out.print(s.toString() + " : ");

						if (s.getGraphicsAlgorithm() instanceof org.eclipse.graphiti.mm.algorithms.Text) {
							org.eclipse.graphiti.mm.algorithms.Text temp = (org.eclipse.graphiti.mm.algorithms.Text) s
									.getGraphicsAlgorithm();
							String tmp_txt = temp.getValue();
							temp.setValue("Temp_Name");
							temp.setValue(tmp_txt);
						}
						;
					}
				}
			} else if (obj instanceof CAN) {
				dialog = new CANPropertiesDialog(activeShell, obj);
				if (dialog.open() == 0)
					changed = true;
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pes[0]);
				if (obj.equals(eobj)) {
					ContainerShape containerShape = (ContainerShape) pes[0];
					EList<Shape> shapes = containerShape.getChildren();
					for (Shape s : shapes) {
						// System.out.print(s.toString() + " : ");

						if (s.getGraphicsAlgorithm() instanceof org.eclipse.graphiti.mm.algorithms.Text) {
							org.eclipse.graphiti.mm.algorithms.Text temp = (org.eclipse.graphiti.mm.algorithms.Text) s
									.getGraphicsAlgorithm();
							String tmp_txt = temp.getValue();
							temp.setValue("Temp_Name");
							temp.setValue(tmp_txt);
						}
						;
					}
				}
			} else if (obj instanceof ECU) {
				dialog = new ECUPropertiesDialog(activeShell, obj);
				if (dialog.open() == 0)
					changed = true;
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pes[0]);
				if (obj.equals(eobj)) {
					ContainerShape containerShape = (ContainerShape) pes[0];
					EList<Shape> shapes = containerShape.getChildren();
					for (Shape s : shapes) {
						// System.out.print(s.toString() + " : ");

						if (s.getGraphicsAlgorithm() instanceof org.eclipse.graphiti.mm.algorithms.Text) {
							org.eclipse.graphiti.mm.algorithms.Text temp = (org.eclipse.graphiti.mm.algorithms.Text) s
									.getGraphicsAlgorithm();
							String tmp_txt = temp.getValue();
							temp.setValue("Temp_Name");
							temp.setValue(tmp_txt);
						}
						;
					}
				}
			} else if (obj instanceof SWC) {
				dialog = new SWCPropertiesDialog(activeShell, obj);
				if (dialog.open() == 0)
					changed = true;
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pes[0]);
				if (obj.equals(eobj)) {
					ContainerShape containerShape = (ContainerShape) pes[0];
					EList<Shape> shapes = containerShape.getChildren();
					for (Shape s : shapes) {

						if (s.getGraphicsAlgorithm() instanceof org.eclipse.graphiti.mm.algorithms.Text) {
							org.eclipse.graphiti.mm.algorithms.Text temp = (org.eclipse.graphiti.mm.algorithms.Text) s
									.getGraphicsAlgorithm();
							String tmp_txt = temp.getValue();
							temp.setValue("Temp_Name");
							temp.setValue(tmp_txt);
						}
						;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
