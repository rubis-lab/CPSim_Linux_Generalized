/**
 *
 * $Id$
 */
package org.eclipse.cpsim.Diagram.validation;

import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.VirtualCategory;

/**
 * A sample validator interface for {@link org.eclipse.cpsim.Diagram.SWC}.
 * This doesn't really do anything, and it's not a real EMF artifact. It was
 * generated by the org.eclipse.emf.examples.generator.validator plug-in to
 * illustrate how EMF's code generator can be extended. This can be disabled
 * with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface SWCValidator {
	boolean validate();

	boolean validateDeadline(int value);

	boolean validateEcuRef(ECU value);

	boolean validateId(String value);

	boolean validatePeriod(int value);

	boolean validatePhase(int value);

	boolean validateVirtual(VirtualCategory value);

	boolean validateWcet(int value);

	boolean validateRecvFrom(String value);

	boolean validateSendTo(String value);
	
}
