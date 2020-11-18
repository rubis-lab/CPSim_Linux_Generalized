package org.eclipse.cpsim.menu.implementation;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.util.CmdExecuter;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AutomaticallyGenerateECUCode extends AbstractHandler {

	private final String env_string = MESSAGES.CPSIM_PATH;
	private final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	private final String cmd_string = MESSAGES.CPSIM_ECU_CMD;
	private final String model_string = MESSAGES.CPSIM_MODEL_PATH;

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

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (!(activeeditor instanceof DiagramEditor)) {
			MessageDialog.openWarning(window.getShell(), "ERROR", "Active Editor is not a Diagram Editor");
			return null;
		}
		ListDialog dia = new ListDialog(window.getShell()) {
			@Override
			protected boolean isResizable() {
				return false;
			}
		};
		dia.setHelpAvailable(false);
		List<ECU> eculist = new ArrayList<ECU>();

		dia.setTitle("Automatically Generate ECU Code");
		dia.setMessage("Select ECU");

		int ecunum = 0;
		DiagramEditor diagrameditor = (DiagramEditor) activeeditor;

		/* Check all objects in graphiti diagram */
		for (PictogramElement pic : Graphiti.getPeService()
				.getAllContainedPictogramElements(diagrameditor.getDiagramTypeProvider().getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			/* ECU */
			if (eobj instanceof ECU) {
				ecunum++;
				ECU ecu = (ECU) eobj;
				eculist.add(ecu);

			}
		}

		/* If there is no ECU */
		if (ecunum == 0) {
			MessageDialog.openWarning(window.getShell(), "ERROR", "There is no ECU in the Diagram Editor");
			return null;
		}

		dia.setInput(eculist.toArray());
		dia.setContentProvider(new ArrayContentProvider());
		dia.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ECU) element).getId();
			}
		});
		dia.setInitialSelections(new Object[] { eculist.get(0) });

		/* Selected result */
		if (dia.open() == Window.OK) {
			// (ECU)dia.getResult()[0] : Selected ECU

			// get cmdPath
			String cpsim_env = getCPSIMenv();
			if (cpsim_env == null) {
				MessageDialog.openWarning(window.getShell(), "ERROR",
						"Check the following environment variable: " + env_string);
				return null;

			}

			String dir = cpsim_env + "\\" + folder_string;
			String cmdPath = dir + "\\" + cmd_string;

			/* Command line */
			cmdPath = cmdPath.replace('/', File.separatorChar);
			List<String> args = new ArrayList<String>();
			args.add(((ECU) dia.getResult()[0]).getId());
			CmdExecuter.execute(cmdPath, dir, args, false);

			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String modelPath = cpsim_env + model_string;
			File f = new File(modelPath);
			System.out.println(modelPath);
			if (f.exists() && f.isFile()) {
				Long lastModified = f.lastModified();
				long diff = Calendar.getInstance().getTime().getTime() - lastModified;
				if (diff < 5 * 60 * 1000) {
					MessageDialog.openInformation(window.getShell(), "SUCCEED", "ECU Code Generation has completed");
					return null;

				} else {
					MessageDialog.openWarning(window.getShell(), "ERROR", "ECU Code Generation has failed");
					return null;
				}
			} else {
				MessageDialog.openWarning(window.getShell(), "ERROR", "ECU Code Generation has failed");
				return null;
			}

		}

		return null;
	}
}
