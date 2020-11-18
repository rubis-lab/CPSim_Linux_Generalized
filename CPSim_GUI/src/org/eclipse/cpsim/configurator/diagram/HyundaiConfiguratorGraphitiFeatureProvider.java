package org.eclipse.cpsim.configurator.diagram;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.configurator.features.AddCanFeature;
import org.eclipse.cpsim.configurator.features.AddCarFeature;
import org.eclipse.cpsim.configurator.features.AddComponentsConnectionFeature;
import org.eclipse.cpsim.configurator.features.AddEcuFeature;
import org.eclipse.cpsim.configurator.features.AddSwcFeature;
import org.eclipse.cpsim.configurator.features.CoreDecreaseEcuFeature;
import org.eclipse.cpsim.configurator.features.CoreIncreaseEcuFeature;
import org.eclipse.cpsim.configurator.features.CreateCanFeature;
import org.eclipse.cpsim.configurator.features.CreateCarFeature;
import org.eclipse.cpsim.configurator.features.CreateComponentsConnectionFeature;
import org.eclipse.cpsim.configurator.features.CreateEcuFeature;
import org.eclipse.cpsim.configurator.features.CreateSwcFeature;
import org.eclipse.cpsim.configurator.features.DeleteFeature;
import org.eclipse.cpsim.configurator.features.DrillDownOpenFolder;
import org.eclipse.cpsim.configurator.features.DrillDownOpenPropertiesFeature;
import org.eclipse.cpsim.configurator.features.DrillDownSWCMATLABCodeMapping;
import org.eclipse.cpsim.configurator.features.LayoutCarFeature;
import org.eclipse.cpsim.configurator.features.MakeGrayscaleFeature;
import org.eclipse.cpsim.configurator.features.MoveAnchorFeature;
import org.eclipse.cpsim.configurator.features.MoveComShapeFeature;
import org.eclipse.cpsim.configurator.features.ResizeShapeFeature;
import org.eclipse.cpsim.configurator.features.SwitchEcuFeature;
import org.eclipse.cpsim.configurator.features.SwitchSwcFeature;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveAnchorFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveAnchorContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;
import org.eclipse.ui.internal.ObjectActionContributorManager;

public class HyundaiConfiguratorGraphitiFeatureProvider extends DefaultFeatureProvider {

	public HyundaiConfiguratorGraphitiFeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		return new ICreateFeature[] { new CreateCanFeature(this), new CreateCarFeature(this),
				new CreateEcuFeature(this), new CreateSwcFeature(this), new SwitchEcuFeature(this),
				new SwitchSwcFeature(this), new CoreIncreaseEcuFeature(this), new CoreDecreaseEcuFeature(this), };
	}

	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		ObjectActionContributorManager.getManager().unregisterAllContributors();
		return new ICustomFeature[] { new DrillDownSWCMATLABCodeMapping(this), new DrillDownOpenFolder(this),
				new DrillDownOpenPropertiesFeature(this), new MakeGrayscaleFeature(this), // new
																							// DrillDownOpenControlPanel(this),
		};
	}

	@Override
	public IDeleteFeature getDeleteFeature(IDeleteContext context) {
		return new DeleteFeature(this);
	}

	@Override
	public ICreateConnectionFeature[] getCreateConnectionFeatures() {
		return new ICreateConnectionFeature[] { new CreateComponentsConnectionFeature(this) };
	}

	@Override
	public IMoveAnchorFeature getMoveAnchorFeature(IMoveAnchorContext context) {
		return new MoveAnchorFeature(this);
	}

	@Override
	public IMoveShapeFeature getMoveShapeFeature(IMoveShapeContext context) {
		return new MoveComShapeFeature(this);
	}

	@Override
	public IResizeShapeFeature getResizeShapeFeature(IResizeShapeContext context) {
		return new ResizeShapeFeature(this);
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		Object newObject = context.getNewObject();
		if (context instanceof IAddConnectionContext) {
			return new AddComponentsConnectionFeature(this);
		} else if (newObject instanceof CAR) {
			return new AddCarFeature(this);
		} else if (newObject instanceof CAN) {
			return new AddCanFeature(this);
		} else if (newObject instanceof ECU) {
			return new AddEcuFeature(this);
		} else if (newObject instanceof SWC) {
			return new AddSwcFeature(this);
		}

		return super.getAddFeature(context);
	}

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
		if (context.getPictogramElement() instanceof ContainerShape) {
			return new LayoutCarFeature(this);
		}

		return super.getLayoutFeature(context);
	}
}