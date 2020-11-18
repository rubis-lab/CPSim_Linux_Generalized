package org.eclipse.cpsim.configurator.features;

import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

public class DeleteFeature extends DefaultDeleteFeature {

	public DeleteFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canDelete(IDeleteContext context) {
		// TODO Auto-generated method stub
		return RunStopSimulation.state != 1;
	}

}
