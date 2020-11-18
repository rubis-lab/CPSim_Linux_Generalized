package org.eclipse.cpsim.configurator.property;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.AbstractPropertySectionFilter;

public class HConfiguratorEClassFilter extends AbstractPropertySectionFilter {

	@Override
	protected boolean accept(PictogramElement pe) {
		EObject bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);

		if (bo instanceof CAR) {
			return true;
		}
		if (bo instanceof CAN) {
			return true;
		}
		if (bo instanceof ECU) {
			return true;
		}
		if (bo instanceof SWC) {
			return true;
		}

		return false;
	}

}
