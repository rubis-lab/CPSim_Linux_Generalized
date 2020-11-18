package org.eclipse.cpsim.menu.simulation;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

public class AnalyzeDynamicMemoryButton extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeDynamicMemory");
		State state = command.getState("org.eclipse.ui.commands.toggleState");
		state.setValue(!(Boolean) state.getValue());
		if (!(Boolean) state.getValue()) {
			AnalyzeDynamicMemory.JFrame_exit();
			return null;
		}

		IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);

		try {
			hs.executeCommand("hyundaiConfiguratorGraphiti.commands.AbstractAnalyzeDynamicMemory", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

}
