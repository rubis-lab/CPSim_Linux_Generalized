/**
 */
package org.eclipse.cpsim.Diagram.impl;

import java.util.Collection;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.DiagramPackage;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>CAN</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.CANImpl#getCarRef <em>Car
 * Ref</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.CANImpl#getEcuRefs <em>Ecu
 * Refs</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.CANImpl#getBandwidth
 * <em>Bandwidth</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.CANImpl#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.CANImpl#getVersion
 * <em>Version</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.CANImpl#getCanDB <em>Can
 * DB</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.CANImpl#isIsCANFD <em>Is
 * CANFD</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CANImpl extends EObjectImpl implements CAN {
	/**
	 * The cached value of the '{@link #getCarRef() <em>Car Ref</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCarRef()
	 * @generated
	 * @ordered
	 */
	protected CAR carRef;

	/**
	 * The cached value of the '{@link #getEcuRefs() <em>Ecu Refs</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEcuRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<ECU> ecuRefs;

	/**
	 * The default value of the '{@link #getBandwidth() <em>Bandwidth</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBandwidth()
	 * @generated
	 * @ordered
	 */
	protected static final int BANDWIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getBandwidth() <em>Bandwidth</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBandwidth()
	 * @generated
	 * @ordered
	 */
	protected int bandwidth = BANDWIDTH_EDEFAULT;

	/**
	 * This is true if the Bandwidth attribute has been set. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean bandwidthESet;

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
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getCanDB() <em>Can DB</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCanDB()
	 * @generated
	 * @ordered
	 */
	protected static final String CAN_DB_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCanDB() <em>Can DB</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCanDB()
	 * @generated
	 * @ordered
	 */
	protected String canDB = CAN_DB_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsCANFD() <em>Is CANFD</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isIsCANFD()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_CANFD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsCANFD() <em>Is CANFD</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isIsCANFD()
	 * @generated
	 * @ordered
	 */
	protected boolean isCANFD = IS_CANFD_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CANImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagramPackage.Literals.CAN;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CAR getCarRef() {
		return carRef;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCarRef(CAR newCarRef, NotificationChain msgs) {
		CAR oldCarRef = carRef;
		carRef = newCarRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.CAN__CAR_REF,
					oldCarRef, newCarRef);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCarRef(CAR newCarRef) {
		if (newCarRef != carRef) {
			NotificationChain msgs = null;
			if (carRef != null)
				msgs = ((InternalEObject) carRef).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - DiagramPackage.CAN__CAR_REF, null, msgs);
			if (newCarRef != null)
				msgs = ((InternalEObject) newCarRef).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - DiagramPackage.CAN__CAR_REF, null, msgs);
			msgs = basicSetCarRef(newCarRef, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		/*
		 * else if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.CAN__CAR_REF, newCarRef, newCarRef));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ECU> getEcuRefs() {
		if (ecuRefs == null) {
			ecuRefs = new EObjectContainmentEList<ECU>(ECU.class, this, DiagramPackage.CAN__ECU_REFS);
		}
		return ecuRefs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getBandwidth() {
		return bandwidth;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setBandwidth(int newBandwidth) {
		int oldBandwidth = bandwidth;
		bandwidth = newBandwidth;
		boolean oldBandwidthESet = bandwidthESet;
		bandwidthESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.CAN__BANDWIDTH, oldBandwidth, bandwidth,
		 * !oldBandwidthESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetBandwidth() {
		int oldBandwidth = bandwidth;
		boolean oldBandwidthESet = bandwidthESet;
		bandwidth = BANDWIDTH_EDEFAULT;
		bandwidthESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.CAN__BANDWIDTH, oldBandwidth,
		 * BANDWIDTH_EDEFAULT, oldBandwidthESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetBandwidth() {
		return bandwidthESet;
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
		 * Notification.SET, DiagramPackage.CAN__ID, oldId, id));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.CAN__VERSION, oldVersion, version));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getCanDB() {
		return canDB;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCanDB(String newCanDB) {
		String oldCanDB = canDB;
		canDB = newCanDB;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.CAN__CAN_DB, oldCanDB, canDB));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isIsCANFD() {
		return isCANFD;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setIsCANFD(boolean newIsCANFD) {
		boolean oldIsCANFD = isCANFD;
		isCANFD = newIsCANFD;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.CAN__IS_CANFD, oldIsCANFD, isCANFD));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DiagramPackage.CAN__CAR_REF:
			return basicSetCarRef(null, msgs);
		case DiagramPackage.CAN__ECU_REFS:
			return ((InternalEList<?>) getEcuRefs()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DiagramPackage.CAN__CAR_REF:
			return getCarRef();
		case DiagramPackage.CAN__ECU_REFS:
			return getEcuRefs();
		case DiagramPackage.CAN__BANDWIDTH:
			return getBandwidth();
		case DiagramPackage.CAN__ID:
			return getId();
		case DiagramPackage.CAN__VERSION:
			return getVersion();
		case DiagramPackage.CAN__CAN_DB:
			return getCanDB();
		case DiagramPackage.CAN__IS_CANFD:
			return isIsCANFD();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DiagramPackage.CAN__CAR_REF:
			setCarRef((CAR) newValue);
			return;
		case DiagramPackage.CAN__ECU_REFS:
			getEcuRefs().clear();
			getEcuRefs().addAll((Collection<? extends ECU>) newValue);
			return;
		case DiagramPackage.CAN__BANDWIDTH:
			setBandwidth((Integer) newValue);
			return;
		case DiagramPackage.CAN__ID:
			setId((String) newValue);
			return;
		case DiagramPackage.CAN__VERSION:
			setVersion((String) newValue);
			return;
		case DiagramPackage.CAN__CAN_DB:
			setCanDB((String) newValue);
			return;
		case DiagramPackage.CAN__IS_CANFD:
			setIsCANFD((Boolean) newValue);
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
		case DiagramPackage.CAN__CAR_REF:
			setCarRef((CAR) null);
			return;
		case DiagramPackage.CAN__ECU_REFS:
			getEcuRefs().clear();
			return;
		case DiagramPackage.CAN__BANDWIDTH:
			unsetBandwidth();
			return;
		case DiagramPackage.CAN__ID:
			setId(ID_EDEFAULT);
			return;
		case DiagramPackage.CAN__VERSION:
			setVersion(VERSION_EDEFAULT);
			return;
		case DiagramPackage.CAN__CAN_DB:
			setCanDB(CAN_DB_EDEFAULT);
			return;
		case DiagramPackage.CAN__IS_CANFD:
			setIsCANFD(IS_CANFD_EDEFAULT);
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
		case DiagramPackage.CAN__CAR_REF:
			return carRef != null;
		case DiagramPackage.CAN__ECU_REFS:
			return ecuRefs != null && !ecuRefs.isEmpty();
		case DiagramPackage.CAN__BANDWIDTH:
			return isSetBandwidth();
		case DiagramPackage.CAN__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case DiagramPackage.CAN__VERSION:
			return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
		case DiagramPackage.CAN__CAN_DB:
			return CAN_DB_EDEFAULT == null ? canDB != null : !CAN_DB_EDEFAULT.equals(canDB);
		case DiagramPackage.CAN__IS_CANFD:
			return isCANFD != IS_CANFD_EDEFAULT;
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
		result.append(" (bandwidth: ");
		if (bandwidthESet)
			result.append(bandwidth);
		else
			result.append("<unset>");
		result.append(", id: ");
		result.append(id);
		result.append(", version: ");
		result.append(version);
		result.append(", canDB: ");
		result.append(canDB);
		result.append(", isCANFD: ");
		result.append(isCANFD);
		result.append(')');
		return result.toString();
	}

} // CANImpl
