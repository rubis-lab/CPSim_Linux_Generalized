/**
 */
package org.eclipse.cpsim.Diagram.impl;

import java.util.Collection;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.DiagramPackage;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.VirtualCategory;
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
 * '<em><b>ECU</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.ECUImpl#getSwcRefs <em>Swc
 * Refs</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.ECUImpl#getCanRef <em>Can
 * Ref</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.ECUImpl#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.ECUImpl#getNumberOfCores
 * <em>Number Of Cores</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.ECUImpl#getSchedulingPolicy
 * <em>Scheduling Policy</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.ECUImpl#getSystemClock <em>System
 * Clock</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.ECUImpl#getVirtual
 * <em>Virtual</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.ECUImpl#getModelName <em>Model
 * Name</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.ECUImpl#getTargetArchitecture
 * <em>Target Architecture</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ECUImpl extends EObjectImpl implements ECU {
	/**
	 * The cached value of the '{@link #getSwcRefs() <em>Swc Refs</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSwcRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<SWC> swcRefs;

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
	 * The default value of the '{@link #getNumberOfCores() <em>Number Of
	 * Cores</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNumberOfCores()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_CORES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfCores() <em>Number Of
	 * Cores</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNumberOfCores()
	 * @generated
	 * @ordered
	 */
	protected int numberOfCores = NUMBER_OF_CORES_EDEFAULT;

	/**
	 * This is true if the Number Of Cores attribute has been set. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean numberOfCoresESet;

	/**
	 * The default value of the '{@link #getSchedulingPolicy() <em>Scheduling
	 * Policy</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSchedulingPolicy()
	 * @generated
	 * @ordered
	 */
	protected static final String SCHEDULING_POLICY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSchedulingPolicy() <em>Scheduling
	 * Policy</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSchedulingPolicy()
	 * @generated
	 * @ordered
	 */
	protected String schedulingPolicy = SCHEDULING_POLICY_EDEFAULT;

	/**
	 * The default value of the '{@link #getSystemClock() <em>System Clock</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSystemClock()
	 * @generated
	 * @ordered
	 */
	protected static final int SYSTEM_CLOCK_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSystemClock() <em>System Clock</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSystemClock()
	 * @generated
	 * @ordered
	 */
	protected int systemClock = SYSTEM_CLOCK_EDEFAULT;

	/**
	 * This is true if the System Clock attribute has been set. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean systemClockESet;

	/**
	 * The default value of the '{@link #getVirtual() <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVirtual()
	 * @generated
	 * @ordered
	 */
	protected static final VirtualCategory VIRTUAL_EDEFAULT = VirtualCategory.VISIBLE;

	/**
	 * The cached value of the '{@link #getVirtual() <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVirtual()
	 * @generated
	 * @ordered
	 */
	protected VirtualCategory virtual = VIRTUAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getModelName() <em>Model Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModelName()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModelName() <em>Model Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModelName()
	 * @generated
	 * @ordered
	 */
	protected String modelName = MODEL_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetArchitecture() <em>Target
	 * Architecture</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTargetArchitecture()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_ARCHITECTURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetArchitecture() <em>Target
	 * Architecture</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTargetArchitecture()
	 * @generated
	 * @ordered
	 */
	protected String targetArchitecture = TARGET_ARCHITECTURE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ECUImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagramPackage.Literals.ECU;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<SWC> getSwcRefs() {
		if (swcRefs == null) {
			swcRefs = new EObjectContainmentEList<SWC>(SWC.class, this, DiagramPackage.ECU__SWC_REFS);
		}
		return swcRefs;
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
				 * Notification.RESOLVE, DiagramPackage.ECU__CAN_REF, oldCanRef, canRef));
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
		 * Notification.SET, DiagramPackage.ECU__CAN_REF, oldCanRef, canRef));
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
		 * Notification.SET, DiagramPackage.ECU__ID, oldId, id));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getNumberOfCores() {
		return numberOfCores;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setNumberOfCores(int newNumberOfCores) {
		int oldNumberOfCores = numberOfCores;
		numberOfCores = newNumberOfCores;
		boolean oldNumberOfCoresESet = numberOfCoresESet;
		numberOfCoresESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.ECU__NUMBER_OF_CORES, oldNumberOfCores,
		 * numberOfCores, !oldNumberOfCoresESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetNumberOfCores() {
		int oldNumberOfCores = numberOfCores;
		boolean oldNumberOfCoresESet = numberOfCoresESet;
		numberOfCores = NUMBER_OF_CORES_EDEFAULT;
		numberOfCoresESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.ECU__NUMBER_OF_CORES, oldNumberOfCores,
		 * NUMBER_OF_CORES_EDEFAULT, oldNumberOfCoresESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetNumberOfCores() {
		return numberOfCoresESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getSchedulingPolicy() {
		return schedulingPolicy;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSchedulingPolicy(String newSchedulingPolicy) {
		String oldSchedulingPolicy = schedulingPolicy;
		schedulingPolicy = newSchedulingPolicy;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.ECU__SCHEDULING_POLICY, oldSchedulingPolicy,
		 * schedulingPolicy));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getSystemClock() {
		return systemClock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSystemClock(int newSystemClock) {
		int oldSystemClock = systemClock;
		systemClock = newSystemClock;
		boolean oldSystemClockESet = systemClockESet;
		systemClockESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.ECU__SYSTEM_CLOCK, oldSystemClock,
		 * systemClock, !oldSystemClockESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetSystemClock() {
		int oldSystemClock = systemClock;
		boolean oldSystemClockESet = systemClockESet;
		systemClock = SYSTEM_CLOCK_EDEFAULT;
		systemClockESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.ECU__SYSTEM_CLOCK, oldSystemClock,
		 * SYSTEM_CLOCK_EDEFAULT, oldSystemClockESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetSystemClock() {
		return systemClockESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VirtualCategory getVirtual() {
		return virtual;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setVirtual(VirtualCategory newVirtual) {
		VirtualCategory oldVirtual = virtual;
		virtual = newVirtual == null ? VIRTUAL_EDEFAULT : newVirtual;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.ECU__VIRTUAL, oldVirtual, virtual));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setModelName(String newModelName) {
		String oldModelName = modelName;
		modelName = newModelName;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.ECU__MODEL_NAME, oldModelName, modelName));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getTargetArchitecture() {
		return targetArchitecture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTargetArchitecture(String newTargetArchitecture) {
		String oldTargetArchitecture = targetArchitecture;
		targetArchitecture = newTargetArchitecture;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.ECU__TARGET_ARCHITECTURE,
		 * oldTargetArchitecture, targetArchitecture));
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
		case DiagramPackage.ECU__SWC_REFS:
			return ((InternalEList<?>) getSwcRefs()).basicRemove(otherEnd, msgs);
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
		case DiagramPackage.ECU__SWC_REFS:
			return getSwcRefs();
		case DiagramPackage.ECU__CAN_REF:
			if (resolve)
				return getCanRef();
			return basicGetCanRef();
		case DiagramPackage.ECU__ID:
			return getId();
		case DiagramPackage.ECU__NUMBER_OF_CORES:
			return getNumberOfCores();
		case DiagramPackage.ECU__SCHEDULING_POLICY:
			return getSchedulingPolicy();
		case DiagramPackage.ECU__SYSTEM_CLOCK:
			return getSystemClock();
		case DiagramPackage.ECU__VIRTUAL:
			return getVirtual();
		case DiagramPackage.ECU__MODEL_NAME:
			return getModelName();
		case DiagramPackage.ECU__TARGET_ARCHITECTURE:
			return getTargetArchitecture();
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
		case DiagramPackage.ECU__SWC_REFS:
			getSwcRefs().clear();
			getSwcRefs().addAll((Collection<? extends SWC>) newValue);
			return;
		case DiagramPackage.ECU__CAN_REF:
			setCanRef((CAN) newValue);
			return;
		case DiagramPackage.ECU__ID:
			setId((String) newValue);
			return;
		case DiagramPackage.ECU__NUMBER_OF_CORES:
			setNumberOfCores((Integer) newValue);
			return;
		case DiagramPackage.ECU__SCHEDULING_POLICY:
			setSchedulingPolicy((String) newValue);
			return;
		case DiagramPackage.ECU__SYSTEM_CLOCK:
			setSystemClock((Integer) newValue);
			return;
		case DiagramPackage.ECU__VIRTUAL:
			setVirtual((VirtualCategory) newValue);
			return;
		case DiagramPackage.ECU__MODEL_NAME:
			setModelName((String) newValue);
			return;
		case DiagramPackage.ECU__TARGET_ARCHITECTURE:
			setTargetArchitecture((String) newValue);
			return;
		}
		// super.eSet(featureID, newValue);
		return;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DiagramPackage.ECU__SWC_REFS:
			getSwcRefs().clear();
			return;
		case DiagramPackage.ECU__CAN_REF:
			setCanRef((CAN) null);
			return;
		case DiagramPackage.ECU__ID:
			setId(ID_EDEFAULT);
			return;
		case DiagramPackage.ECU__NUMBER_OF_CORES:
			unsetNumberOfCores();
			return;
		case DiagramPackage.ECU__SCHEDULING_POLICY:
			setSchedulingPolicy(SCHEDULING_POLICY_EDEFAULT);
			return;
		case DiagramPackage.ECU__SYSTEM_CLOCK:
			unsetSystemClock();
			return;
		case DiagramPackage.ECU__VIRTUAL:
			setVirtual(VIRTUAL_EDEFAULT);
			return;
		case DiagramPackage.ECU__MODEL_NAME:
			setModelName(MODEL_NAME_EDEFAULT);
			return;
		case DiagramPackage.ECU__TARGET_ARCHITECTURE:
			setTargetArchitecture(TARGET_ARCHITECTURE_EDEFAULT);
			return;
		}
		// super.eUnset(featureID);
		return;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DiagramPackage.ECU__SWC_REFS:
			return swcRefs != null && !swcRefs.isEmpty();
		case DiagramPackage.ECU__CAN_REF:
			return canRef != null;
		case DiagramPackage.ECU__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case DiagramPackage.ECU__NUMBER_OF_CORES:
			return isSetNumberOfCores();
		case DiagramPackage.ECU__SCHEDULING_POLICY:
			return SCHEDULING_POLICY_EDEFAULT == null ? schedulingPolicy != null
					: !SCHEDULING_POLICY_EDEFAULT.equals(schedulingPolicy);
		case DiagramPackage.ECU__SYSTEM_CLOCK:
			return isSetSystemClock();
		case DiagramPackage.ECU__VIRTUAL:
			return virtual != VIRTUAL_EDEFAULT;
		case DiagramPackage.ECU__MODEL_NAME:
			return MODEL_NAME_EDEFAULT == null ? modelName != null : !MODEL_NAME_EDEFAULT.equals(modelName);
		case DiagramPackage.ECU__TARGET_ARCHITECTURE:
			return TARGET_ARCHITECTURE_EDEFAULT == null ? targetArchitecture != null
					: !TARGET_ARCHITECTURE_EDEFAULT.equals(targetArchitecture);
		}
		return false;
		// return super.eIsSet(featureID);
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
		result.append(", numberOfCores: ");
		if (numberOfCoresESet)
			result.append(numberOfCores);
		else
			result.append("<unset>");
		result.append(", schedulingPolicy: ");
		result.append(schedulingPolicy);
		result.append(", systemClock: ");
		if (systemClockESet)
			result.append(systemClock);
		else
			result.append("<unset>");
		result.append(", virtual: ");
		result.append(virtual);
		result.append(", modelName: ");
		result.append(modelName);
		result.append(", targetArchitecture: ");
		result.append(targetArchitecture);
		result.append(')');
		return result.toString();
	}

} // ECUImpl
