/**
 */
package org.eclipse.cpsim.Diagram;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>ECU</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.cpsim.Diagram.ECU#getSwcRefs <em>Swc Refs</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.ECU#getCanRef <em>Can Ref</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.ECU#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.ECU#getNumberOfCores <em>Number Of
 * Cores</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.ECU#getSchedulingPolicy <em>Scheduling
 * Policy</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.ECU#getSystemClock <em>System
 * Clock</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.ECU#getVirtual <em>Virtual</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.ECU#getModelName <em>Model
 * Name</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.ECU#getTargetArchitecture <em>Target
 * Architecture</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU()
 * @model extendedMetaData="name='ECU' kind='elementOnly'"
 * @generated
 */
public interface ECU extends EObject {
	/**
	 * Returns the value of the '<em><b>Swc Refs</b></em>' containment reference
	 * list. The list contents are of type {@link org.eclipse.cpsim.Diagram.SWC}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Swc Refs</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Swc Refs</em>' containment reference list.
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU_SwcRefs()
	 * @model containment="true" extendedMetaData="kind='element' name='swcRefs'"
	 * @generated
	 */
	EList<SWC> getSwcRefs();

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
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU_CanRef()
	 * @model extendedMetaData="kind='attribute' name='canRef'"
	 * @generated
	 */
	CAN getCanRef();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.ECU#getCanRef
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
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU_Id()
	 * @model extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.ECU#getId
	 * <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Number Of Cores</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Cores</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Number Of Cores</em>' attribute.
	 * @see #isSetNumberOfCores()
	 * @see #unsetNumberOfCores()
	 * @see #setNumberOfCores(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU_NumberOfCores()
	 * @model unsettable="true" extendedMetaData="kind='attribute'
	 *        name='numberOfCores'"
	 * @generated
	 */
	int getNumberOfCores();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getNumberOfCores <em>Number Of
	 * Cores</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Number Of Cores</em>' attribute.
	 * @see #isSetNumberOfCores()
	 * @see #unsetNumberOfCores()
	 * @see #getNumberOfCores()
	 * @generated
	 */
	void setNumberOfCores(int value);

	/**
	 * Unsets the value of the
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getNumberOfCores <em>Number Of
	 * Cores</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetNumberOfCores()
	 * @see #getNumberOfCores()
	 * @see #setNumberOfCores(int)
	 * @generated
	 */
	void unsetNumberOfCores();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getNumberOfCores <em>Number Of
	 * Cores</em>}' attribute is set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Number Of Cores</em>' attribute is set.
	 * @see #unsetNumberOfCores()
	 * @see #getNumberOfCores()
	 * @see #setNumberOfCores(int)
	 * @generated
	 */
	boolean isSetNumberOfCores();

	/**
	 * Returns the value of the '<em><b>Scheduling Policy</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scheduling Policy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Scheduling Policy</em>' attribute.
	 * @see #setSchedulingPolicy(String)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU_SchedulingPolicy()
	 * @model extendedMetaData="kind='attribute' name='schedulingPolicy'"
	 * @generated
	 */
	String getSchedulingPolicy();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getSchedulingPolicy <em>Scheduling
	 * Policy</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Scheduling Policy</em>' attribute.
	 * @see #getSchedulingPolicy()
	 * @generated
	 */
	void setSchedulingPolicy(String value);

	/**
	 * Returns the value of the '<em><b>System Clock</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>System Clock</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>System Clock</em>' attribute.
	 * @see #isSetSystemClock()
	 * @see #unsetSystemClock()
	 * @see #setSystemClock(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU_SystemClock()
	 * @model unsettable="true" extendedMetaData="kind='attribute'
	 *        name='systemClock'"
	 * @generated
	 */
	int getSystemClock();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.ECU#getSystemClock
	 * <em>System Clock</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>System Clock</em>' attribute.
	 * @see #isSetSystemClock()
	 * @see #unsetSystemClock()
	 * @see #getSystemClock()
	 * @generated
	 */
	void setSystemClock(int value);

	/**
	 * Unsets the value of the
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getSystemClock <em>System
	 * Clock</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetSystemClock()
	 * @see #getSystemClock()
	 * @see #setSystemClock(int)
	 * @generated
	 */
	void unsetSystemClock();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getSystemClock <em>System
	 * Clock</em>}' attribute is set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>System Clock</em>' attribute is set.
	 * @see #unsetSystemClock()
	 * @see #getSystemClock()
	 * @see #setSystemClock(int)
	 * @generated
	 */
	boolean isSetSystemClock();

	/**
	 * Returns the value of the '<em><b>Virtual</b></em>' attribute. The literals
	 * are from the enumeration {@link org.eclipse.cpsim.Diagram.VirtualCategory}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Virtual</em>' attribute.
	 * @see org.eclipse.cpsim.Diagram.VirtualCategory
	 * @see #setVirtual(VirtualCategory)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU_Virtual()
	 * @model extendedMetaData="kind='attribute' name='virtual'"
	 * @generated
	 */
	VirtualCategory getVirtual();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.ECU#getVirtual
	 * <em>Virtual</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Virtual</em>' attribute.
	 * @see org.eclipse.cpsim.Diagram.VirtualCategory
	 * @see #getVirtual()
	 * @generated
	 */
	void setVirtual(VirtualCategory value);

	/**
	 * Returns the value of the '<em><b>Model Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Name</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Model Name</em>' attribute.
	 * @see #setModelName(String)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU_ModelName()
	 * @model
	 * @generated
	 */
	String getModelName();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.ECU#getModelName
	 * <em>Model Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Model Name</em>' attribute.
	 * @see #getModelName()
	 * @generated
	 */
	void setModelName(String value);

	/**
	 * Returns the value of the '<em><b>Target Architecture</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Architecture</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Target Architecture</em>' attribute.
	 * @see #setTargetArchitecture(String)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getECU_TargetArchitecture()
	 * @model
	 * @generated
	 */
	String getTargetArchitecture();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getTargetArchitecture <em>Target
	 * Architecture</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Target Architecture</em>' attribute.
	 * @see #getTargetArchitecture()
	 * @generated
	 */
	void setTargetArchitecture(String value);

} // ECU
