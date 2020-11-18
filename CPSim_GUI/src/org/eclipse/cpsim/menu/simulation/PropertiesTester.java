package org.eclipse.cpsim.menu.simulation;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class PropertiesTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (property.equals("SimulationOptionCheck")) {
			if (RunStopSimulation.state == 0)
				return true;
		} else if (property.equals("ResultAnalysisCheck")) {
			if (RunStopSimulation.state == 0)
				return true;
		} else if (property.equals("GenerateECUCheck")) {
			if (RunStopSimulation.state == 0)
				return true;
		} else if (property.equals("SMCheck")) {
			if (RunStopSimulation.state == 0)
				return true;

		} else if (property.equals("Design")) {
			IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			if (activeeditor == null || activeeditor instanceof DiagramEditor)
				return true;
		} else if (property.equals("Simulation")) {
			IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			if (activeeditor == null || activeeditor instanceof DiagramEditor)
				return true;
		} else if (property.equals("Implementation")) {
			IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			if (activeeditor == null || activeeditor instanceof DiagramEditor)
				return true;
		}

		return false;
	}
}
