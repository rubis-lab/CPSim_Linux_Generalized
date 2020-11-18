/**
 */
package org.eclipse.cpsim.Diagram;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>CAN</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.cpsim.Diagram.CAN#getCarRef <em>Car Ref</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.CAN#getEcuRefs <em>Ecu Refs</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.CAN#getBandwidth
 * <em>Bandwidth</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.CAN#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.CAN#getVersion <em>Version</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.CAN#getCanDB <em>Can DB</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.CAN#isIsCANFD <em>Is CANFD</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAN()
 * @model extendedMetaData="name='CAN' kind='elementOnly'"
 * @generated
 */
public interface CAN extends EObject {
	/**
	 * Returns the value of the '<em><b>Car Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Car Ref</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Car Ref</em>' containment reference.
	 * @see #setCarRef(CAR)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAN_CarRef()
	 * @model containment="true" extendedMetaData="kind='element' name='carRef'"
	 * @generated
	 */
	CAR getCarRef();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.CAN#getCarRef
	 * <em>Car Ref</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Car Ref</em>' containment reference.
	 * @see #getCarRef()
	 * @generated
	 */
	void setCarRef(CAR value);

	/**
	 * Returns the value of the '<em><b>Ecu Refs</b></em>' containment reference
	 * list. The list contents are of type {@link org.eclipse.cpsim.Diagram.ECU}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ecu Refs</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ecu Refs</em>' containment reference list.
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAN_EcuRefs()
	 * @model containment="true" extendedMetaData="kind='element' name='ecuRefs'"
	 * @generated
	 */
	EList<ECU> getEcuRefs();

	/**
	 * Returns the value of the '<em><b>Bandwidth</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bandwidth</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Bandwidth</em>' attribute.
	 * @see #isSetBandwidth()
	 * @see #unsetBandwidth()
	 * @see #setBandwidth(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAN_Bandwidth()
	 * @model unsettable="true" extendedMetaData="kind='attribute' name='bandwidth'"
	 * @generated
	 */
	int getBandwidth();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.CAN#getBandwidth
	 * <em>Bandwidth</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Bandwidth</em>' attribute.
	 * @see #isSetBandwidth()
	 * @see #unsetBandwidth()
	 * @see #getBandwidth()
	 * @generated
	 */
	void setBandwidth(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.cpsim.Diagram.CAN#getBandwidth
	 * <em>Bandwidth</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetBandwidth()
	 * @see #getBandwidth()
	 * @see #setBandwidth(int)
	 * @generated
	 */
	void unsetBandwidth();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.CAN#getBandwidth <em>Bandwidth</em>}'
	 * attribute is set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Bandwidth</em>' attribute is set.
	 * @see #unsetBandwidth()
	 * @see #getBandwidth()
	 * @see #setBandwidth(int)
	 * @generated
	 */
	boolean isSetBandwidth();

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
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAN_Id()
	 * @model extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.CAN#getId
	 * <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAN_Version()
	 * @model extendedMetaData="kind='attribute' name='version'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.CAN#getVersion
	 * <em>Version</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Can DB</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Can DB</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Can DB</em>' attribute.
	 * @see #setCanDB(String)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAN_CanDB()
	 * @model
	 * @generated
	 */
	String getCanDB();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.CAN#getCanDB
	 * <em>Can DB</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Can DB</em>' attribute.
	 * @see #getCanDB()
	 * @generated
	 */
	void setCanDB(String value);

	/**
	 * Returns the value of the '<em><b>Is CANFD</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is CANFD</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Is CANFD</em>' attribute.
	 * @see #setIsCANFD(boolean)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getCAN_IsCANFD()
	 * @model
	 * @generated
	 */
	boolean isIsCANFD();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.CAN#isIsCANFD
	 * <em>Is CANFD</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Is CANFD</em>' attribute.
	 * @see #isIsCANFD()
	 * @generated
	 */
	void setIsCANFD(boolean value);

} // CAN
