package org.eclipse.cpsim.menu.design;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ResetPerspective extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IPerspectiveDescriptor descriptor = PlatformUI.getWorkbench().getPerspectiveRegistry()
				.findPerspectiveWithId("org.eclipse.hyundai.perspective.HyundaiPerspective");
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().setPerspective(descriptor);

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().resetPerspective();
		return null;
	}
}
