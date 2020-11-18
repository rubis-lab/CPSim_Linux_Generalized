package org.eclipse.cpsim.menu.simulation;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

public class AnalyzeSystemWideRuntimeBehaviorButton extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeSystemWideRuntimeBehavior");
		State state = command.getState("org.eclipse.ui.commands.toggleState");
		state.setValue(!(Boolean) state.getValue());
		if (!(Boolean) state.getValue()) {
			AnalyzeSystemWideRuntimeBehavior.JFrame_exit();
			return null;
		}

		IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);

		try {
			hs.executeCommand("hyundaiConfiguratorGraphiti.commands.AbstractAnalyzeSystemWideRuntimeBehavior", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

}
