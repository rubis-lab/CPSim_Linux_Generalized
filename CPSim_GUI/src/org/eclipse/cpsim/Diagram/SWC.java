/**
 */
package org.eclipse.cpsim.Diagram;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>SWC</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.cpsim.Diagram.SWC#getDeadline
 * <em>Deadline</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.SWC#getEcuRef <em>Ecu Ref</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.SWC#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.SWC#getPeriod <em>Period</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.SWC#getPhase <em>Phase</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.SWC#getVirtual <em>Virtual</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.SWC#getWcet <em>Wcet</em>}</li>
 *  * <li>{@link org.eclipse.cpsim.Diagram.SWC#getBcet <em>Bcet</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.SWC#getRecvFrom <em>Recv
 * From</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.SWC#getSendTo <em>Send To</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC()
 * @model extendedMetaData="name='SWC' kind='empty'"
 * @generated
 */
public interface SWC extends EObject {
	/**
	 * Returns the value of the '<em><b>Deadline</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deadline</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Deadline</em>' attribute.
	 * @see #isSetDeadline()
	 * @see #unsetDeadline()
	 * @see #setDeadline(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_Deadline()
	 * @model unsettable="true" extendedMetaData="kind='attribute' name='deadline'"
	 * @generated
	 */
	int getDeadline();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getDeadline
	 * <em>Deadline</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Deadline</em>' attribute.
	 * @see #isSetDeadline()
	 * @see #unsetDeadline()
	 * @see #getDeadline()
	 * @generated
	 */
	void setDeadline(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getDeadline
	 * <em>Deadline</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetDeadline()
	 * @see #getDeadline()
	 * @see #setDeadline(int)
	 * @generated
	 */
	void unsetDeadline();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getDeadline <em>Deadline</em>}'
	 * attribute is set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Deadline</em>' attribute is set.
	 * @see #unsetDeadline()
	 * @see #getDeadline()
	 * @see #setDeadline(int)
	 * @generated
	 */
	boolean isSetDeadline();

	/**
	 * Returns the value of the '<em><b>Ecu Ref</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ecu Ref</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ecu Ref</em>' reference.
	 * @see #setEcuRef(ECU)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_EcuRef()
	 * @model extendedMetaData="kind='attribute' name='ecuRef'"
	 * @generated
	 */
	ECU getEcuRef();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getEcuRef
	 * <em>Ecu Ref</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Ecu Ref</em>' reference.
	 * @see #getEcuRef()
	 * @generated
	 */
	void setEcuRef(ECU value);

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
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_Id()
	 * @model extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getId
	 * <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see #isSetPeriod()
	 * @see #unsetPeriod()
	 * @see #setPeriod(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_Period()
	 * @model unsettable="true" extendedMetaData="kind='attribute' name='period'"
	 * @generated
	 */
	int getPeriod();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getPeriod
	 * <em>Period</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Period</em>' attribute.
	 * @see #isSetPeriod()
	 * @see #unsetPeriod()
	 * @see #getPeriod()
	 * @generated
	 */
	void setPeriod(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getPeriod
	 * <em>Period</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetPeriod()
	 * @see #getPeriod()
	 * @see #setPeriod(int)
	 * @generated
	 */
	void unsetPeriod();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getPeriod <em>Period</em>}' attribute
	 * is set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Period</em>' attribute is set.
	 * @see #unsetPeriod()
	 * @see #getPeriod()
	 * @see #setPeriod(int)
	 * @generated
	 */
	boolean isSetPeriod();

	/**
	 * Returns the value of the '<em><b>Phase</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Phase</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Phase</em>' attribute.
	 * @see #isSetPhase()
	 * @see #unsetPhase()
	 * @see #setPhase(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_Phase()
	 * @model unsettable="true" extendedMetaData="kind='attribute' name='phase'"
	 * @generated
	 */
	int getPhase();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getPhase
	 * <em>Phase</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Phase</em>' attribute.
	 * @see #isSetPhase()
	 * @see #unsetPhase()
	 * @see #getPhase()
	 * @generated
	 */
	void setPhase(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getPhase
	 * <em>Phase</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetPhase()
	 * @see #getPhase()
	 * @see #setPhase(int)
	 * @generated
	 */
	void unsetPhase();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getPhase <em>Phase</em>}' attribute
	 * is set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Phase</em>' attribute is set.
	 * @see #unsetPhase()
	 * @see #getPhase()
	 * @see #setPhase(int)
	 * @generated
	 */
	boolean isSetPhase();

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
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_Virtual()
	 * @model extendedMetaData="kind='attribute' name='virtual'"
	 * @generated
	 */
	VirtualCategory getVirtual();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getVirtual
	 * <em>Virtual</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Virtual</em>' attribute.
	 * @see org.eclipse.cpsim.Diagram.VirtualCategory
	 * @see #getVirtual()
	 * @generated
	 */
	void setVirtual(VirtualCategory value);

	/**
	 * Returns the value of the '<em><b>Wcet</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wcet</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Wcet</em>' attribute.
	 * @see #isSetWcet()
	 * @see #unsetWcet()
	 * @see #setWcet(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_Wcet()
	 * @model unsettable="true" extendedMetaData="kind='attribute' name='wcet'"
	 * @generated
	 */
	int getWcet();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getWcet
	 * <em>Wcet</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Wcet</em>' attribute.
	 * @see #isSetWcet()
	 * @see #unsetWcet()
	 * @see #getWcet()
	 * @generated
	 */
	void setWcet(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getWcet
	 * <em>Wcet</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetWcet()
	 * @see #getWcet()
	 * @see #setWcet(int)
	 * @generated
	 */
	void unsetWcet();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getWcet <em>Wcet</em>}' attribute is
	 * set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Wcet</em>' attribute is set.
	 * @see #unsetWcet()
	 * @see #getWcet()
	 * @see #setWcet(int)
	 * @generated
	 */
	boolean isSetWcet();
	
	
	/**
	 * Returns the value of the '<em><b>Bcet</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bcet</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Bcet</em>' attribute.
	 * @see #isSetBcet()
	 * @see #unsetBcet()
	 * @see #setBcet(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_Bcet()
	 * @model unsettable="true" extendedMetaData="kind='attribute' name='bcet'"
	 * @generated
	 */
	
	int getBcet();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getBcet
	 * <em>Bcet</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Bcet</em>' attribute.
	 * @see #isSetBcet()
	 * @see #unsetBcet()
	 * @see #getBcet()
	 * @generated
	 */
	void setBcet(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getBcet
	 * <em>Bcet</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetBcet()
	 * @see #getBcet()
	 * @see #setBcet(int)
	 * @generated
	 */
	void unsetBcet();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getBcet <em>Bcet</em>}' attribute is
	 * set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Bcet</em>' attribute is set.
	 * @see #unsetBcet()
	 * @see #getBcet()
	 * @see #setBcet(int)
	 * @generated
	 */
	boolean isSetBcet();



	/**
	 * Returns the value of the '<em><b>Procon</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Procon</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Procon</em>' attribute.
	 * @see #isSetProcon()
	 * @see #unsetProcon()
	 * @see #setProcon(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_Procon()
	 * @model unsettable="true" extendedMetaData="kind='attribute' name='procon'"
	 * @generated
	 */
	
	String getProcon();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getProcon
	 * <em>Procon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Procon</em>' attribute.
	 * @see #isSetProcon()
	 * @see #unsetProcon()
	 * @see #getProcon()
	 * @generated
	 */
	void setProcon(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getProcon
	 * <em>Procon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetProcon()
	 * @see #getProcon()
	 * @see #setProcon(int)
	 * @generated
	 */
	void unsetProcon();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getProcon <em>Procon</em>}' attribute is
	 * set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>Procon</em>' attribute is set.
	 * @see #unsetProcon()
	 * @see #getProcon()
	 * @see #setProcon(int)
	 * @generated
	 */
	boolean isSetProcon();


	/**
	 * Returns the value of the '<em><b>ReadCon</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ReadCon</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>ReadCon</em>' attribute.
	 * @see #isSetReadCon()
	 * @see #unsetReadCon()
	 * @see #setReadCon(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_ReadCon()
	 * @model unsettable="true" extendedMetaData="kind='attribute' name='ReadCon'"
	 * @generated
	 */
	
	String getReadCon();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getReadCon
	 * <em>ReadCon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>ReadCon</em>' attribute.
	 * @see #isSetReadCon()
	 * @see #unsetReadCon()
	 * @see #getReadCon()
	 * @generated
	 */
	void setReadCon(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getReadCon
	 * <em>ReadCon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetReadCon()
	 * @see #getReadCon()
	 * @see #setReadCon(int)
	 * @generated
	 */
	void unsetReadCon();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getProcon <em>ReadCon</em>}' attribute is
	 * set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>ReadCon</em>' attribute is set.
	 * @see #unsetReadCon()
	 * @see #getReadCon()
	 * @see #setReadCon(int)
	 * @generated
	 */
	boolean isSetReadCon();


	/**
	 * Returns the value of the '<em><b>ReadCon</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ReadCon</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>ReadCon</em>' attribute.
	 * @see #isSetReadCon()
	 * @see #unsetReadCon()
	 * @see #setReadCon(int)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_ReadCon()
	 * @model unsettable="true" extendedMetaData="kind='attribute' name='ReadCon'"
	 * @generated
	 */
	
	String getWriteCon();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getReadCon
	 * <em>ReadCon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>ReadCon</em>' attribute.
	 * @see #isSetReadCon()
	 * @see #unsetReadCon()
	 * @see #getReadCon()
	 * @generated
	 */
	void setWriteCon(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getReadCon
	 * <em>ReadCon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSetReadCon()
	 * @see #getReadCon()
	 * @see #setReadCon(int)
	 * @generated
	 */
	void unsetWriteCon();

	/**
	 * Returns whether the value of the
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getProcon <em>ReadCon</em>}' attribute is
	 * set. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return whether the value of the '<em>ReadCon</em>' attribute is set.
	 * @see #unsetReadCon()
	 * @see #getReadCon()
	 * @see #setReadCon(int)
	 * @generated
	 */
	boolean isSetWriteCon();

	
	/**
	 * Returns the value of the '<em><b>Recv From</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recv From</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Recv From</em>' attribute.
	 * @see #setRecvFrom(String)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_RecvFrom()
	 * @model
	 * @generated
	 */
	String getRecvFrom();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getRecvFrom
	 * <em>Recv From</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Recv From</em>' attribute.
	 * @see #getRecvFrom()
	 * @generated
	 */
	void setRecvFrom(String value);

	/**
	 * Returns the value of the '<em><b>Send To</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Send To</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Send To</em>' attribute.
	 * @see #setSendTo(String)
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getSWC_SendTo()
	 * @model
	 * @generated
	 */
	String getSendTo();

	/**
	 * Sets the value of the '{@link org.eclipse.cpsim.Diagram.SWC#getSendTo
	 * <em>Send To</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Send To</em>' attribute.
	 * @see #getSendTo()
	 * @generated
	 */
	void setSendTo(String value);

} // SWC
