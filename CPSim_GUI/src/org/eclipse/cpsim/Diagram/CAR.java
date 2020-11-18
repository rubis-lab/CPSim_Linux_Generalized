/**
 */
package org.eclipse.cpsim.Diagram;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>CAR</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.cpsim.Diagram.CAR#getCanRef <em>Can Ref</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.CAR#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAR()
 * @model extendedMetaData="name='CAR' kind='empty'"
 * @generated
 */
public interface CAR extends EObject {
	/**
	 * Returns the value of the '<em><b>Can Ref</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Can Ref</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Can Ref</em>' reference.
	 * @see #setCanRef(CAN)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAR_CanRef()
	 * @model required="true" extendedMetaData="kind='attribute' name='canRef'"
	 * @generated
	 */
	CAN getCanRef();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.CAR#getCanRef
	 * <em>Can Ref</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Can Ref</em>' reference.
	 * @see #getCanRef()
	 * @generated
	 */
	void setCanRef(CAN value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAR_Id()
	 * @model extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.CAR#getId
	 * <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

} // CAR
