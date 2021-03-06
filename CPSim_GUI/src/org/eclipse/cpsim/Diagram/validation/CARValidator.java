/**
 *
 * $Id$
 */
package org.eclipse.cpsim.Diagram.validation;

import org.eclipse.cpsim.Diagram.CAN;

/**
 * A sample validator interface for {@link org.eclipse.cpsim.Diagram.CAR}.
 * This doesn't really do anything, and it's not a real EMF artifact. It was
 * generated by the org.eclipse.emf.examples.generator.validator plug-in to
 * illustrate how EMF's code generator can be extended. This can be disabled
 * with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface CARValidator {
	boolean validate();

	boolean validateCanRef(CAN value);

	boolean validateId(String value);
}
