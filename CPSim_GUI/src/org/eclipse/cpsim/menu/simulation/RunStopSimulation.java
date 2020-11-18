package org.eclipse.cpsim.menu.simulation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.cpsim.Diagram.util.CmdExecuter;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.cpsim.Diagram.util.ShellCommander;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.menus.UIElement;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RunStopSimulation extends AbstractHandler implements IElementUpdater {
	private final String env_string = MESSAGES.CPSIM_PATH;
	//private final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	//private final String folder_parser = MESSAGES.CPSIM_FOLDER_PARSER;
	private final String start_string = MESSAGES.CPSIM_RUN_SHELL;
	private final String stop_string = MESSAGES.CPSIM_STOP_SHELL;
	//private final String config_string = MESSAGES.CPSIM_CONFIG;
	private final String engine_string = MESSAGES.FIRST_LINE;
	private final String rawdata_xml = MESSAGES.CPSIM_RAW_DESIGN;
	//private final String[] config_line = { MESSAGES.START, MESSAGES.END, MESSAGES.INTERVAL, MESSAGES.RUNTIME };

	private final String process_name = MESSAGES.PROCESS_NAME;
	private final String start_image = "icons/start_sim.png";
	private final String stop_image = "icons/stop_sim.png";
	private final String mult_image = "icons/start_sim_mult.png";
	private ImageDescriptor start_icon;
	private ImageDescriptor stop_icon;
	private ImageDescriptor mult_icon;

	public static boolean not_runned = true;

	public static int state; // 0: stopped, 1: running

	public RunStopSimulation() {
		state = 0;

		Bundle bundle = Platform.getBundle("hyundaiConfiguratorGraphiti");
		URL fullPathString = BundleUtility.find(bundle, start_image);
		start_icon = ImageDescriptor.createFromURL(fullPathString);

		fullPathString = BundleUtility.find(bundle, stop_image);
		stop_icon = ImageDescriptor.createFromURL(fullPathString);

		fullPathString = BundleUtility.find(bundle, mult_image);
		mult_icon = ImageDescriptor.createFromURL(fullPathString);

	}

	public String getCPSIMenv() {
		try {
			String line = System.getenv(env_string);

			// check there are several paths
			if (line.indexOf(";") < 0)
				return line.trim();

			line = line.substring(0, line.indexOf(";"));
			return line.trim();

		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

	}

	/* Never Used. Have to change process_name first */
	public boolean CheckProcess() {
		List<String> processes = new ArrayList<String>();
		try {
			String line;
			Process p = Runtime.getRuntime().exec("tasklist.exe /nh");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					// keep only the process name
					processes.add(line.substring(0, line.indexOf(" ")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<String> it = processes.iterator();
		while (it.hasNext()) {
			if (it.next().equals(process_name)) {
				return true;
			}
		}

		return false;
	}

	public boolean CreateRawData(ExecutionEvent event) {
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();

		try {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

			IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			if (!(activeeditor instanceof DiagramEditor)) {
				MessageDialog.openWarning(window.getShell(), "ERROR", "Active Editor is not a Diagram Editor");
				return false;
			}
			DiagramEditor diagrameditor = (DiagramEditor) activeeditor;
			DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.newDocument();
			Element root = doc.createElement(XmlMessages.ROOT);
			doc.appendChild(root);
			doc.setXmlStandalone(true);

			Element cans = doc.createElement(XmlMessages.CAN_ROOT);
			root.appendChild(cans);
			Element cars = doc.createElement(XmlMessages.CAR);
			root.appendChild(cars);
			Element swcs = doc.createElement(XmlMessages.SWC_ROOT);
			root.appendChild(swcs);
			Element ecus = doc.createElement(XmlMessages.ECU_ROOT);
			root.appendChild(ecus);

			for (PictogramElement pic : Graphiti.getPeService()
					.getAllContainedPictogramElements(diagrameditor.getDiagramTypeProvider().getDiagram())) {
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
				/* CAN */
				if (eobj instanceof CAN) {
					CAN can = (CAN) eobj;
					Element thiscan = doc.createElement(XmlMessages.CAN);
					cans.appendChild(thiscan);

					thiscan.setAttribute(XmlMessages.ID, can.getId());
//					thiscan.setAttribute(XmlMessages.BANDWIDTH, String.valueOf(can.getBandwidth()));
//					thiscan.setAttribute(XmlMessages.TYPE,
//							can.isIsCANFD() ? XmlMessages.CAN_TYPE_CAN_FD : XmlMessages.CAN_TYPE_CAN);
//
//					String[] dir = can.getCanDB().split(";");
//					for (String s : dir) {
//						if (!s.isEmpty()) {
//							Element node = doc.createElement(XmlMessages.CANDB);
//							thiscan.appendChild(node);
//							node.appendChild(doc.createTextNode(s));
//						}
//					}

					for (Connection s : ((AnchorContainer) pic).getAnchors().get(0).getIncomingConnections()) {
						Element link = doc.createElement(XmlMessages.LINK);
						thiscan.appendChild(link);

						Anchor sourceAnchor = ((FreeFormConnection) s).getStart();
						for (PictogramElement pics : Graphiti.getPeService().getAllContainedPictogramElements(
								diagrameditor.getDiagramTypeProvider().getDiagram())) {
							if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement().equals(pics)) {
								EObject eobjs = Graphiti.getLinkService()
										.getBusinessObjectForLinkedPictogramElement(pics);
								link.setAttribute(XmlMessages.TYPE,
										(eobjs instanceof CAR) ? XmlMessages.CAR : XmlMessages.ECU);
								link.setAttribute(XmlMessages.RID,
										(eobjs instanceof CAR) ? ((CAR) eobjs).getId() : ((ECU) eobjs).getId());
								break;
							}
						}
					}
					for (Connection s : ((AnchorContainer) pic).getAnchors().get(0).getOutgoingConnections()) {
						Element link = doc.createElement(XmlMessages.LINK);
						thiscan.appendChild(link);

						Anchor sourceAnchor = ((FreeFormConnection) s).getEnd();
						for (PictogramElement pics : Graphiti.getPeService().getAllContainedPictogramElements(
								diagrameditor.getDiagramTypeProvider().getDiagram())) {
							if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement().equals(pics)) {
								EObject eobjs = Graphiti.getLinkService()
										.getBusinessObjectForLinkedPictogramElement(pics);
								link.setAttribute(XmlMessages.TYPE,
										(eobjs instanceof CAR) ? XmlMessages.CAR : XmlMessages.ECU);
								link.setAttribute(XmlMessages.RID,
										(eobjs instanceof CAR) ? ((CAR) eobjs).getId() : ((ECU) eobjs).getId());
								break;
							}
						}
					}
				}
				/* CAR */
				if (eobj instanceof CAR) {
					CAR car = (CAR) eobj;
					cars.setAttribute(XmlMessages.ID, car.getId());

					for (Connection s : ((AnchorContainer) pic).getAnchors().get(0).getOutgoingConnections()) {
						Element link = doc.createElement(XmlMessages.LINK);
						cars.appendChild(link);
						link.setAttribute(XmlMessages.TYPE, XmlMessages.CAN);

						Anchor sourceAnchor = ((FreeFormConnection) s).getEnd();
						for (PictogramElement pics : Graphiti.getPeService().getAllContainedPictogramElements(
								diagrameditor.getDiagramTypeProvider().getDiagram())) {
							if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement().equals(pics)) {
								link.setAttribute(XmlMessages.RID, ((CAN) Graphiti.getLinkService()
										.getBusinessObjectForLinkedPictogramElement(pics)).getId());
								break;
							}
						}
					}
					for (Connection s : ((AnchorContainer) pic).getAnchors().get(0).getIncomingConnections()) {
						Element link = doc.createElement(XmlMessages.LINK);
						cars.appendChild(link);
						link.setAttribute(XmlMessages.TYPE, XmlMessages.CAN);

						Anchor sourceAnchor = ((FreeFormConnection) s).getStart();
						for (PictogramElement pics : Graphiti.getPeService().getAllContainedPictogramElements(
								diagrameditor.getDiagramTypeProvider().getDiagram())) {
							if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement().equals(pics)) {
								link.setAttribute(XmlMessages.RID, ((CAN) Graphiti.getLinkService()
										.getBusinessObjectForLinkedPictogramElement(pics)).getId());
								break;
							}
						}
					}
				}
				/* SWC */
				if (eobj instanceof SWC) {
					SWC swc = (SWC) eobj;
					Element thisswc = doc.createElement(XmlMessages.SWC);
					swcs.appendChild(thisswc);

					thisswc.setAttribute(XmlMessages.ID, swc.getId().split(";")[0]);
					thisswc.setAttribute(XmlMessages.PERIOD, String.valueOf(swc.getPeriod()));
					thisswc.setAttribute(XmlMessages.DEADLINE, String.valueOf(swc.getDeadline()));
					thisswc.setAttribute(XmlMessages.WCET, String.valueOf(swc.getWcet()));
					thisswc.setAttribute(XmlMessages.BCET, String.valueOf(swc.getBcet()));
					thisswc.setAttribute(XmlMessages.PHASE, String.valueOf(swc.getPhase()));
					thisswc.setAttribute(XmlMessages.PROCON, String.valueOf(swc.getProcon()));
					thisswc.setAttribute(XmlMessages.READCON, String.valueOf(swc.getReadCon()));
					thisswc.setAttribute(XmlMessages.WRITECON, String.valueOf(swc.getWriteCon()));
					thisswc.setAttribute(XmlMessages.VIRTUAL,
							swc.getVirtual().equals(VirtualCategory.VISIBLE) ? "1" : "0");

					if (swc.getId().split(";").length == 3 && !swc.getId().split(";")[1].isEmpty()
							&& !swc.getId().split(";")[2].isEmpty()) {
						Element impl = doc.createElement(XmlMessages.IMPL);
						thisswc.appendChild(impl);

						impl.setAttribute(XmlMessages.TYPE,
								swc.getId().split(";")[1].equals("M") ? XmlMessages.SWC_TYPE_MATLAB
										: XmlMessages.SWC_TYPE_CODE);
						impl.setAttribute(XmlMessages.PATH, swc.getId().split(";")[2]);
					}

					for (Connection s : ((AnchorContainer) pic).getAnchors().get(0).getOutgoingConnections()) {
						Element link = doc.createElement(XmlMessages.LINK);
						thisswc.appendChild(link);
						link.setAttribute(XmlMessages.TYPE, XmlMessages.ECU);

						Anchor sourceAnchor = ((FreeFormConnection) s).getEnd();
						for (PictogramElement pics : Graphiti.getPeService().getAllContainedPictogramElements(
								diagrameditor.getDiagramTypeProvider().getDiagram())) {
							if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement().equals(pics)) {
								ECU ecu = ((ECU) Graphiti.getLinkService()
										.getBusinessObjectForLinkedPictogramElement(pics));
								link.setAttribute(XmlMessages.RID, ecu.getId());
								for (int i = 0; i < ecu.getNumberOfCores(); i++) {
									if (((AnchorContainer) pics).getAnchors().get(i + 1).equals(sourceAnchor)) {
										link.setAttribute(XmlMessages.IDXCORE, String.valueOf(i));
										break;
									}
								}
								break;
							}
						}
					}
				}
				/* ECU */
				if (eobj instanceof ECU) {
					ECU ecu = (ECU) eobj;
					Element thisecu = doc.createElement(XmlMessages.ECU);
					ecus.appendChild(thisecu);

					thisecu.setAttribute(XmlMessages.ID, ecu.getId());
					thisecu.setAttribute(XmlMessages.NUMCORES, String.valueOf(ecu.getNumberOfCores()));
					thisecu.setAttribute(XmlMessages.SYSCLOCK, String.valueOf(ecu.getSystemClock()));
					thisecu.setAttribute(XmlMessages.SCHEDPOLICY, ecu.getSchedulingPolicy());
					thisecu.setAttribute(XmlMessages.TARGETARCH, ecu.getTargetArchitecture());
					thisecu.setAttribute(XmlMessages.VIRTUAL,
							ecu.getVirtual().equals(VirtualCategory.VISIBLE) ? "1" : "0");

					for (int i = 0; i < ecu.getNumberOfCores(); i++) {
						for (Connection s : ((AnchorContainer) pic).getAnchors().get(i + 1).getIncomingConnections()) {
							Element link = doc.createElement(XmlMessages.LINK);
							thisecu.appendChild(link);
							link.setAttribute(XmlMessages.TYPE, XmlMessages.SWC);
							link.setAttribute(XmlMessages.IDXCORE, String.valueOf(i));

							Anchor sourceAnchor = ((FreeFormConnection) s).getStart();
							for (PictogramElement pics : Graphiti.getPeService().getAllContainedPictogramElements(
									diagrameditor.getDiagramTypeProvider().getDiagram())) {
								if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement()
										.equals(pics)) {
									link.setAttribute(XmlMessages.RID,
											((SWC) Graphiti.getLinkService()
													.getBusinessObjectForLinkedPictogramElement(pics)).getId()
															.split(";")[0]);
									break;
								}
							}
						}
					}

					for (Connection s : ((AnchorContainer) pic).getAnchors().get(0).getOutgoingConnections()) {
						Element link = doc.createElement(XmlMessages.LINK);
						thisecu.appendChild(link);
						link.setAttribute(XmlMessages.TYPE, XmlMessages.CAN);

						Anchor sourceAnchor = ((FreeFormConnection) s).getEnd();
						for (PictogramElement pics : Graphiti.getPeService().getAllContainedPictogramElements(
								diagrameditor.getDiagramTypeProvider().getDiagram())) {
							if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement().equals(pics)) {
								link.setAttribute(XmlMessages.RID, ((CAN) Graphiti.getLinkService()
										.getBusinessObjectForLinkedPictogramElement(pics)).getId());
								break;
							}
						}
					}
					for (Connection s : ((AnchorContainer) pic).getAnchors().get(0).getIncomingConnections()) {
						Element link = doc.createElement(XmlMessages.LINK);
						thisecu.appendChild(link);
						link.setAttribute(XmlMessages.TYPE, XmlMessages.CAN);

						Anchor sourceAnchor = ((FreeFormConnection) s).getStart();
						for (PictogramElement pics : Graphiti.getPeService().getAllContainedPictogramElements(
								diagrameditor.getDiagramTypeProvider().getDiagram())) {
							if (sourceAnchor.getParent().getGraphicsAlgorithm().getPictogramElement().equals(pics)) {
								link.setAttribute(XmlMessages.RID, ((CAN) Graphiti.getLinkService()
										.getBusinessObjectForLinkedPictogramElement(pics)).getId());
								break;
							}
						}
					}
				}
			}

			// output DOM XML to console
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);

			/*
			 * StreamResult console = new StreamResult(System.out);
			 * transformer.transform(source, console);
			 */

			String filepath = getCPSIMenv() +"/"+ rawdata_xml;
			//String filepath = "/home/park/"+rawdata_xml;
			File resultFile = new File(filepath);
			StreamResult result2 = new StreamResult(new FileOutputStream(resultFile));

			transformer.transform(source, result2);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = page.getActiveEditor();
		if (state == 0)
			page.saveEditor(editor, false);
		if (state == 0 && !CreateRawData(event))
			return null;

		// get cmdPath
		String cpsim_env = getCPSIMenv();
		if (cpsim_env == null) {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MessageDialog.openWarning(window.getShell(), "ERROR",
					"Check the following environment variable: " + env_string);
			return null;

		}

		String dir = cpsim_env ;

		File f = new File(dir);
		if (!f.isDirectory()) {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MessageDialog.openWarning(window.getShell(), "ERROR", "Check the following directory is exists: " + dir);
			return null;
		}

		String cmdPath = dir + "/";
		if (state == 1)
			cmdPath += stop_string;
		else
			cmdPath += start_string;

		String cmdPath_stop = cpsim_env + "/" + stop_string;

		IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		DiagramEditor diagrameditor = (DiagramEditor) activeeditor;

		Collection<PictogramElement> pic_col = (Graphiti.getPeService()
				.getAllContainedPictogramElements(diagrameditor.getDiagramTypeProvider().getDiagram()));
		PictogramElement[] pic = new PictogramElement[pic_col.size()];

		pic = pic_col.toArray(pic);

		CustomContext context = new CustomContext(pic);
		ICustomFeature a[] = diagrameditor.getDiagramTypeProvider().getFeatureProvider().getCustomFeatures(context);
		for (ICustomFeature b : a) {
			// System.out.println(b.getName());
			if (b.getName().equals("Make Grayscale Feature")) {
				// System.out.println("Got it");
				diagrameditor.getDiagramTypeProvider().getDiagramBehavior().executeFeature(b, context);
			}
		}

		// set next state
		state = 1 - state;
		try {

			if (state == 1) { // run stop step before start
				List<String> args = new ArrayList<String>();
				//CmdExecuter.execute(cmdPath_stop, dir, args, false);
				ShellCommander.shellCmd(cmdPath_stop);
			}

			if (state == 1) // wait for 1 sec
				Thread.sleep(1000);

			List<String> args = new ArrayList<String>();
			if (state == 1) { // Add config file when starting simulation
				//CmdExecuter.addConfigFromFile(configPath, args);
			}
			ShellCommander.shellCmd(cmdPath);
			//CmdExecuter.execute(cmdPath, dir, args, false);
		} catch (Exception e) {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			MessageDialog.openWarning(window.getShell(), "ERROR", "Check the following file is exists: " + cmdPath);
			return null;
		}

		if (state == 1) {
			ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
			Command command1 = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeDynamicMemory");
			Command command2 = service
					.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeSystemWideRuntimeBehavior");
			org.eclipse.core.commands.State state1 = command1.getState("org.eclipse.ui.commands.toggleState");
			org.eclipse.core.commands.State state2 = command2.getState("org.eclipse.ui.commands.toggleState");
			boolean bools1 = (boolean) state1.getValue();
			boolean bools2 = (boolean) state2.getValue();
			AnalyzeDynamicMemory.JFrame_only_close();
			AnalyzeSystemWideRuntimeBehavior.JFrame_only_close();
			state1.setValue(bools1);
			state2.setValue(bools2);

			AnalyzeDynamicMemory.Open = true;
			AnalyzeSystemWideRuntimeBehavior.Open = true;

			IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);

			try {
				Thread.sleep(1000);
			} catch (Exception e) {

			}

			try {
				hs.executeCommand("hyundaiConfiguratorGraphiti.commands.AbstractAnalyzeDynamicMemory", null);
				hs.executeCommand("hyundaiConfiguratorGraphiti.commands.AbstractAnalyzeSystemWideRuntimeBehavior",
						null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (state == 0) {
			AnalyzeDynamicMemory.Open = false;
			AnalyzeSystemWideRuntimeBehavior.Open = false;
		}

		// Refresh button
		ICommandService cs = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		// System.out.println(event.getCommand().getId());
		cs.refreshElements(event.getCommand().getId(), null);

		return null;
	}

	@Override
	public void updateElement(UIElement element, @SuppressWarnings("rawtypes") Map map) {
		// System.out.println("Updating: "+SimulationOptions.mult);
		if (state == 1) {
			element.setText("Stop Simulation");
			element.setIcon(stop_icon);
		} else {
			if (SimulationOptions.mult == 1) {
				element.setText("Multi-Run Simulation");
				element.setIcon(mult_icon);
			} else {
				element.setText("Run Simulation");
				element.setIcon(start_icon);
			}
		}

	}
}