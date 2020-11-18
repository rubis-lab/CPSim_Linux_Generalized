package org.eclipse.cpsim.configurator.diagram;

import org.eclipse.graphiti.dt.AbstractDiagramTypeProvider;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;

public class HyundaiConfiguratorGraphitiDiagramTypeProvider extends AbstractDiagramTypeProvider {

	private IToolBehaviorProvider[] toolBehaviorProviders;

	public HyundaiConfiguratorGraphitiDiagramTypeProvider() {
		super();
		setFeatureProvider(new HyundaiConfiguratorGraphitiFeatureProvider(this));
	}

	@Override
	public IToolBehaviorProvider[] getAvailableToolBehaviorProviders() {
		if (toolBehaviorProviders == null) {
			toolBehaviorProviders = new IToolBehaviorProvider[] {
					new HyundaiConfiguratorGraphitiBehaviorProvider(this) };
		}
		return toolBehaviorProviders;
	}
}
