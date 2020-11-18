/**
 */
package org.eclipse.cpsim.Diagram;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Dual</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.cpsim.Diagram.Dual#getPeriod <em>Period</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getDual()
 * @model
 * @generated
 */
public interface Dual extends EObject {
	/**
	 * Returns the value of the '<em><b>Period</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Period</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Period</em>' attribute.
	 * @see #setPeriod(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getDual_Period()
	 * @model
	 * @generated
	 */
	int getPeriod();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.Dual#getPeriod
	 * <em>Period</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Period</em>' attribute.
	 * @see #getPeriod()
	 * @generated
	 */
	void setPeriod(int value);

} // Dual
