/**
 */
package org.eclipse.cpsim.Diagram.impl;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.DiagramPackage;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>CAR</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.CARImpl#getCanRef <em>Can
 * Ref</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.CARImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CARImpl extends EObjectImpl implements CAR {
	/**
	 * The cached value of the '{@link #getCanRef() <em>Can Ref</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCanRef()
	 * @generated
	 * @ordered
	 */
	protected CAN canRef;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CARImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagramPackage.Literals.CAR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CAN getCanRef() {
		if (canRef != null && canRef.eIsProxy()) {
			InternalEObject oldCanRef = (InternalEObject) canRef;
			canRef = (CAN) eResolveProxy(oldCanRef);
			if (canRef != oldCanRef) {
				/*
				 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
				 * Notification.RESOLVE, DiagramPackage.CAR__CAN_REF, oldCanRef, canRef));
				 */
			}
		}
		return canRef;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CAN basicGetCanRef() {
		return canRef;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCanRef(CAN newCanRef) {
		CAN oldCanRef = canRef;
		canRef = newCanRef;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.CAR__CAN_REF, oldCanRef, canRef));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.CAR__ID, oldId, id));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DiagramPackage.CAR__CAN_REF:
			if (resolve)
				return getCanRef();
			return basicGetCanRef();
		case DiagramPackage.CAR__ID:
			return getId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DiagramPackage.CAR__CAN_REF:
			setCanRef((CAN) newValue);
			return;
		case DiagramPackage.CAR__ID:
			setId((String) newValue);
			return;
		}
		// super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DiagramPackage.CAR__CAN_REF:
			setCanRef((CAN) null);
			return;
		case DiagramPackage.CAR__ID:
			setId(ID_EDEFAULT);
			return;
		}
		// super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DiagramPackage.CAR__CAN_REF:
			return canRef != null;
		case DiagramPackage.CAR__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		}
		// return super.eIsSet(featureID);
		return false;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} // CARImpl
