package org.eclipse.cpsim.configurator.diagram;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cpsim.Diagram.CustomImageProvider;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.cpsim.Diagram.util.CmdExecuter;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.cpsim.Diagram.util.ShellCommander;
import org.eclipse.cpsim.configurator.features.ClickOpenControlPanel;
import org.eclipse.cpsim.configurator.features.CoreDecreaseEcuFeature;
import org.eclipse.cpsim.configurator.features.CoreIncreaseEcuFeature;
import org.eclipse.cpsim.configurator.features.SwitchEcuFeature;
import org.eclipse.cpsim.configurator.features.SwitchSwcFeature;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.ISingleClickContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.palette.IPaletteCompartmentEntry;
import org.eclipse.graphiti.palette.impl.ObjectCreationToolEntry;
import org.eclipse.graphiti.palette.impl.PaletteCompartmentEntry;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.tb.ContextButtonEntry;
import org.eclipse.graphiti.tb.ContextEntryHelper;
import org.eclipse.graphiti.tb.ContextMenuEntry;
import org.eclipse.graphiti.tb.DefaultToolBehaviorProvider;
import org.eclipse.graphiti.tb.IContextButtonEntry;
import org.eclipse.graphiti.tb.IContextButtonPadData;
import org.eclipse.graphiti.tb.IContextMenuEntry;
import org.eclipse.graphiti.tb.IShapeSelectionInfo;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class HyundaiConfiguratorGraphitiBehaviorProvider extends DefaultToolBehaviorProvider {
	HyundaiConfiguratorGraphitiBehaviorProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}

	@Override
	public IContextButtonPadData getContextButtonPad(IPictogramElementContext context) {
		IContextButtonPadData data = super.getContextButtonPad(context);
		PictogramElement pe = context.getPictogramElement();

		// 1. set the generic context buttons
		// note, that we do not add 'remove' (just as an example)
		setGenericContextButtons(data, pe, CONTEXT_BUTTON_DELETE | CONTEXT_BUTTON_UPDATE);

		// 2. add one domain specific context-button, which offers all
		// available connection-features as drag&drop features...
		EObject obj = Graphiti.getLinkService()
				.getBusinessObjectForLinkedPictogramElement(context.getPictogramElement());
		if (obj instanceof ECU) {
			CreateContext cc = new CreateContext();
			Diagram diagram = Graphiti.getPeService().getDiagramForPictogramElement(pe);
			cc.setTargetContainer(diagram);
			cc.setLocation(100, 100);

			ICreateFeature[] cf = this.getFeatureProvider().getCreateFeatures();
			for (int i = 0; i < cf.length; i++) {
				ICreateFeature iCreateFeature = cf[i];
				if (iCreateFeature instanceof SwitchEcuFeature) {
					((SwitchEcuFeature) iCreateFeature).setPictogram(context.getPictogramElement());
					IContextButtonEntry collapseButton = ContextEntryHelper.createCollapseContextButton(true,
							iCreateFeature, cc);
					collapseButton.setText("Virtual");
					collapseButton.setDescription("Change ECU's virtual mode");
					collapseButton.setIconId(CustomImageProvider.IMG_VIRTUAL_ICON);
					data.setCollapseContextButton(collapseButton);
				} else if (iCreateFeature instanceof CoreIncreaseEcuFeature) {
					((CoreIncreaseEcuFeature) iCreateFeature).setPictogram(context.getPictogramElement());
					IContextButtonEntry collapseButton = ContextEntryHelper.createCollapseContextButton(true,
							iCreateFeature, cc);
					collapseButton.setText("Core increase");
					collapseButton.setDescription("Increase the number of cores");
					collapseButton.setIconId(CustomImageProvider.IMG_ECU_INC_ICON);
					data.getDomainSpecificContextButtons().add(collapseButton);
				} else if (iCreateFeature instanceof CoreDecreaseEcuFeature) {
					((CoreDecreaseEcuFeature) iCreateFeature).setPictogram(context.getPictogramElement());
					IContextButtonEntry collapseButton = ContextEntryHelper.createCollapseContextButton(true,
							iCreateFeature, cc);
					collapseButton.setText("Core decrease");
					collapseButton.setDescription("Decrease the number of cores");
					collapseButton.setIconId(CustomImageProvider.IMG_ECU_DEC_ICON);
					data.getDomainSpecificContextButtons().add(collapseButton);
				}
			}
		} else if (obj instanceof SWC) {
			CreateContext cc = new CreateContext();
			Diagram diagram = Graphiti.getPeService().getDiagramForPictogramElement(pe);
			cc.setTargetContainer(diagram);
			cc.setLocation(100, 100);

			ICreateFeature[] cf = this.getFeatureProvider().getCreateFeatures();
			for (int i = 0; i < cf.length; i++) {
				ICreateFeature iCreateFeature = cf[i];
				if (iCreateFeature instanceof SwitchSwcFeature) {
					((SwitchSwcFeature) iCreateFeature).setPictogram(context.getPictogramElement());
					IContextButtonEntry collapseButton = ContextEntryHelper.createCollapseContextButton(true,
							iCreateFeature, cc);
					collapseButton.setText("Virtual");
					collapseButton.setDescription("Change SWC's virtual mode");
					collapseButton.setIconId(CustomImageProvider.IMG_VIRTUAL_ICON);
					data.setCollapseContextButton(collapseButton);
					break;
				}
			}
		}

		// 3.a. create new CreateConnectionContext
		CreateConnectionContext ccc = new CreateConnectionContext();
		ccc.setSourcePictogramElement(pe);
		Anchor anchor = null;
		if (pe instanceof Anchor) {
			anchor = (Anchor) pe;
		} else if (pe instanceof AnchorContainer) {
			// assume, that our shapes always have chopbox anchors
			anchor = Graphiti.getPeService().getChopboxAnchor((AnchorContainer) pe);
		}
		ccc.setSourceAnchor(anchor);

		// 3.b. create context button and add all applicable features
		ContextButtonEntry button = new ContextButtonEntry(null, context);
		button.setText("Create connection");
		button.setIconId(CustomImageProvider.IMG_EREFERENCE);
		ICreateConnectionFeature[] features = getFeatureProvider().getCreateConnectionFeatures();
		for (ICreateConnectionFeature feature : features) {
			if (feature.isAvailable(ccc) && feature.canStartConnection(ccc))
				button.addDragAndDropFeature(feature);
		}

		// 3.c. add context button, if it contains at least one feature
		if (button.getDragAndDropFeatures().size() > 0) {
			data.getDomainSpecificContextButtons().add(button);
		}

		return data;
	}

	public IPaletteCompartmentEntry[] getPalette() {
		List<IPaletteCompartmentEntry> ret = new ArrayList<IPaletteCompartmentEntry>();

		// add compartments from super class
		IPaletteCompartmentEntry[] superCompartments = super.getPalette();

		for (int i = 0; i < superCompartments.length; i++) {
			if (i == 1)
				continue;
			ret.add(superCompartments[i]);
		}

		PaletteCompartmentEntry compartmentEntry = new PaletteCompartmentEntry("Components", null);
		ret.add(compartmentEntry);

		int iconImgIdCnt = 0;
		String[] iconImgId = { CustomImageProvider.IMG_CAN_ICON, CustomImageProvider.IMG_CAR_ICON,
				CustomImageProvider.IMG_ECU_ICON, CustomImageProvider.IMG_SWC_ICON };
		IFeatureProvider featureProvider = getFeatureProvider();
		ICreateFeature[] createFeatures = featureProvider.getCreateFeatures();
		for (ICreateFeature cf : createFeatures) {
			if (cf.getCreateDescription().equals("CopyECU"))
				continue;
			else if (cf.getCreateDescription().equals("CopySWC"))
				continue;
			else if (cf.getCreateDescription().equals("Core Increase"))
				continue;
			else if (cf.getCreateDescription().equals("Core Decrease"))
				continue;

			ObjectCreationToolEntry objectCreationToolEntry = new ObjectCreationToolEntry(cf.getCreateName(),
					cf.getCreateDescription(), iconImgId[iconImgIdCnt++], cf.getCreateLargeImageId(), cf);
			compartmentEntry.addToolEntry(objectCreationToolEntry);
		}

		return ret.toArray(new IPaletteCompartmentEntry[ret.size()]);
	}

	@Override
	public IContextMenuEntry[] getContextMenu(ICustomContext context) {
		// create a sub-menu for all custom features
		ContextMenuEntry subMenu = new ContextMenuEntry(null, context);
		subMenu.setText("");
		subMenu.setDescription("Custom features submenu");
		// display sub-menu hierarchical or flat
		subMenu.setSubmenu(false);

		ContextMenuEntry runstopSubMenu = new ContextMenuEntry(null, context);
		runstopSubMenu.setText("");
		runstopSubMenu.setDescription("Run Stop");
		runstopSubMenu.setSubmenu(false);

		// create a menu-entry in the sub-menu for each custom feature
		ICustomFeature[] customFeatures = getFeatureProvider().getCustomFeatures(context);
		for (int i = 0; i < customFeatures.length; i++) {
			ICustomFeature customFeature = customFeatures[i];
			if (customFeature.isAvailable(context)) {
				if (i < 2) {
					ContextMenuEntry menuEntry = new ContextMenuEntry(customFeature, context);
					subMenu.add(menuEntry);
				} else {
					ContextMenuEntry menuEntry = new ContextMenuEntry(customFeature, context);
					runstopSubMenu.add(menuEntry);
				}
			}
		}

		IContextMenuEntry ret[] = new IContextMenuEntry[] { subMenu, runstopSubMenu };
		return ret;
	}

	@Override
	public ICustomFeature getSingleClickFeature(ISingleClickContext context) {
		ICustomFeature customFeature = new ClickOpenControlPanel(getFeatureProvider());
		// canExecute() tests especially if the context contains a EClass
		if (customFeature.canExecute(context)) {
			return customFeature;
		}

		return super.getSingleClickFeature(context);
	}

	@Override
	public boolean isConnectionSelectionEnabled() {
		return RunStopSimulation.state == 0;
	}

	@Override
	public boolean isMultiSelectionEnabled() {
		return RunStopSimulation.state == 0;
	}

	@Override
	public IShapeSelectionInfo getSelectionInfoForShape(Shape shape) {

		DiagramEditor diagrameditor = (DiagramEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		for (PictogramElement pic : Graphiti.getPeService()
				.getAllContainedPictogramElements(diagrameditor.getDiagramTypeProvider().getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			/* SWC */
			if (shape.equals(pic) && eobj instanceof SWC && RunStopSimulation.state == 1) {
				SWC swc = (SWC) eobj;
				if (swc.getVirtual().equals(VirtualCategory.INVISIBLE))
					break;
				if (MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"Open Control Panel", "Open Control Panel of " + swc.getId().split(";")[0] + "?")) {
					try {

						final String env_string = MESSAGES.CPSIM_PATH;
						final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
						final String cmd_string = MESSAGES.CPSIM_PLOTTER_CMD;
						String cpsimenv = null;
						try {
							String line = System.getenv(env_string);

							// check there are several paths
							if (line.indexOf(";") < 0) {
								cpsimenv = line.trim();
							} else {
								line = line.substring(0, line.indexOf(";"));
								cpsimenv = line.trim();
							}

						} catch (Exception e) {
							e.printStackTrace();
							return super.getSelectionInfoForShape(shape);
						}

						String dir = cpsimenv + "\\" + folder_string;
						String cmdPath = dir + "\\" + cmd_string;
						cmdPath = cmdPath.replace('/', File.separatorChar);
						List<String> args = new ArrayList<String>();
						args.add(swc.getId().split(";")[0]);
						ShellCommander.shellCmd(cmdPath);
					} catch (Exception e) {
					}
					System.out.println("OK\n");
					return super.getSelectionInfoForShape(shape);
				}
			}

		}
		return super.getSelectionInfoForShape(shape);
	}
}
