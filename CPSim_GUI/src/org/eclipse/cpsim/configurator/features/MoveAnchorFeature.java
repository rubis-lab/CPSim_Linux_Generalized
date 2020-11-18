package org.eclipse.cpsim.configurator.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveAnchorContext;
import org.eclipse.graphiti.features.impl.DefaultMoveAnchorFeature;

public class MoveAnchorFeature extends DefaultMoveAnchorFeature {
	public MoveAnchorFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canMoveAnchor(IMoveAnchorContext context) {
		return false;
	}
}
