/**
 */
package org.eclipse.cpsim.Diagram.impl;

import org.eclipse.cpsim.Diagram.DiagramPackage;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>SWC</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.SWCImpl#getDeadline
 * <em>Deadline</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.SWCImpl#getEcuRef <em>Ecu
 * Ref</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.SWCImpl#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.SWCImpl#getPeriod
 * <em>Period</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.SWCImpl#getPhase
 * <em>Phase</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.SWCImpl#getVirtual
 * <em>Virtual</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.SWCImpl#getWcet
 * <em>Wcet</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.SWCImpl#getRecvFrom <em>Recv
 * From</em>}</li>
 * <li>{@link org.eclipse.cpsim.Diagram.impl.SWCImpl#getSendTo <em>Send
 * To</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SWCImpl extends EObjectImpl implements SWC {
	/**
	 * The default value of the '{@link #getDeadline() <em>Deadline</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDeadline()
	 * @generated
	 * @ordered
	 */
	protected static final int DEADLINE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDeadline() <em>Deadline</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDeadline()
	 * @generated
	 * @ordered
	 */
	protected int deadline = DEADLINE_EDEFAULT;

	/**
	 * This is true if the Deadline attribute has been set. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean deadlineESet;

	/**
	 * The cached value of the '{@link #getEcuRef() <em>Ecu Ref</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEcuRef()
	 * @generated
	 * @ordered
	 */
	protected ECU ecuRef;

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
	 * The default value of the '{@link #getPeriod() <em>Period</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPeriod()
	 * @generated
	 * @ordered
	 */
	protected static final int PERIOD_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPeriod() <em>Period</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPeriod()
	 * @generated
	 * @ordered
	 */
	protected int period = PERIOD_EDEFAULT;

	/**
	 * This is true if the Period attribute has been set. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean periodESet;

	/**
	 * The default value of the '{@link #getPhase() <em>Phase</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPhase()
	 * @generated
	 * @ordered
	 */
	protected static final int PHASE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPhase() <em>Phase</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPhase()
	 * @generated
	 * @ordered
	 */
	protected int phase = PHASE_EDEFAULT;

	/**
	 * This is true if the Phase attribute has been set. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean phaseESet;

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
	 * The default value of the '{@link #getWcet() <em>Wcet</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWcet()
	 * @generated
	 * @ordered
	 */
	protected static final int WCET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWcet() <em>Wcet</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWcet()
	 * @generated
	 * @ordered
	 */
	protected int wcet = WCET_EDEFAULT;

	/**
	 * This is true if the Wcet attribute has been set. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean wcetESet;

	/**
	 * The default value of the '{@link #getBcet() <em>Bcet</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBcet()
	 * @generated
	 * @ordered
	 */
	protected static final int BCET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWcet() <em>Bcet</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWcet()
	 * @generated
	 * @ordered
	 */
	protected int bcet = BCET_EDEFAULT;

	/**
	 * This is true if the  attribute has been set. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean bcetESet;
	
	/**
	 * The default value of the '{@link #getProcon() <em>Procon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProcon()
	 * @generated
	 * @ordered
	 */
	protected static final String PROCON_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getProcon() <em>Procon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProcon()
	 * @generated
	 * @ordered
	 */
	protected String procon = PROCON_EDEFAULT;

	/**
	 * This is true if the Procon attribute has been set. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean proconESet;


	/**
	 * The default value of the '{@link #getProcon() <em>ReadCon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReadCon()
	 * @generated
	 * @ordered
	 */
	protected static final String READCON_EDEFAULT = "0";

	/**
	 * The cached value of the '{@link #getProcon() <em>Procon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProcon()
	 * @generated
	 * @ordered
	 */
	protected String readCon = READCON_EDEFAULT;

	/**
	 * This is true if the ReadCon attribute has been set. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean readConESet;
	

	/**
	 * The default value of the '{@link #getWriteCon() <em>WriteCon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWriteCon()
	 * @generated
	 * @ordered
	 */
	protected static final String WRITECON_EDEFAULT = "0";

	/**
	 * The cached value of the '{@link #getWriteCon() <em>WriteCon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getWriteCon()
	 * @generated
	 * @ordered
	 */
	protected String writeCon = WRITECON_EDEFAULT;

	/**
	 * This is true if the ReadCon attribute has been set. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean writeConESet;
	
	
	/**
	 * The default value of the '{@link #getRecvFrom() <em>Recv From</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRecvFrom()
	 * @generated
	 * @ordered
	 */
	protected static final String RECV_FROM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRecvFrom() <em>Recv From</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRecvFrom()
	 * @generated
	 * @ordered
	 */
	protected String recvFrom = RECV_FROM_EDEFAULT;

	/**
	 * The default value of the '{@link #getSendTo() <em>Send To</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSendTo()
	 * @generated
	 * @ordered
	 */
	protected static final String SEND_TO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSendTo() <em>Send To</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSendTo()
	 * @generated
	 * @ordered
	 */
	protected String sendTo = SEND_TO_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SWCImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DiagramPackage.Literals.SWC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getDeadline() {
		return deadline;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDeadline(int newDeadline) {
		int oldDeadline = deadline;
		deadline = newDeadline;
		boolean oldDeadlineESet = deadlineESet;
		deadlineESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__DEADLINE, oldDeadline, deadline,
		 * !oldDeadlineESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetDeadline() {
		int oldDeadline = deadline;
		boolean oldDeadlineESet = deadlineESet;
		deadline = DEADLINE_EDEFAULT;
		deadlineESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.SWC__DEADLINE, oldDeadline,
		 * DEADLINE_EDEFAULT, oldDeadlineESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetDeadline() {
		return deadlineESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ECU getEcuRef() {
		if (ecuRef != null && ecuRef.eIsProxy()) {
			InternalEObject oldEcuRef = (InternalEObject) ecuRef;
			ecuRef = (ECU) eResolveProxy(oldEcuRef);
			if (ecuRef != oldEcuRef) {
				/*
				 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
				 * Notification.RESOLVE, DiagramPackage.SWC__ECU_REF, oldEcuRef, ecuRef));
				 */
			}
		}
		return ecuRef;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ECU basicGetEcuRef() {
		return ecuRef;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setEcuRef(ECU newEcuRef) {
		ECU oldEcuRef = ecuRef;
		ecuRef = newEcuRef;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__ECU_REF, oldEcuRef, ecuRef));
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
		 * Notification.SET, DiagramPackage.SWC__ID, oldId, id));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPeriod(int newPeriod) {
		int oldPeriod = period;
		period = newPeriod;
		boolean oldPeriodESet = periodESet;
		periodESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__PERIOD, oldPeriod, period,
		 * !oldPeriodESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetPeriod() {
		int oldPeriod = period;
		boolean oldPeriodESet = periodESet;
		period = PERIOD_EDEFAULT;
		periodESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.SWC__PERIOD, oldPeriod, PERIOD_EDEFAULT,
		 * oldPeriodESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetPeriod() {
		return periodESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getPhase() {
		return phase;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPhase(int newPhase) {
		int oldPhase = phase;
		phase = newPhase;
		boolean oldPhaseESet = phaseESet;
		phaseESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__PHASE, oldPhase, phase,
		 * !oldPhaseESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetPhase() {
		int oldPhase = phase;
		boolean oldPhaseESet = phaseESet;
		phase = PHASE_EDEFAULT;
		phaseESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.SWC__PHASE, oldPhase, PHASE_EDEFAULT,
		 * oldPhaseESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetPhase() {
		return phaseESet;
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
		 * Notification.SET, DiagramPackage.SWC__VIRTUAL, oldVirtual, virtual));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getWcet() {
		return wcet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setWcet(int newWcet) {
		int oldWcet = wcet;
		wcet = newWcet;
		boolean oldWcetESet = wcetESet;
		wcetESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__WCET, oldWcet, wcet, !oldWcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetWcet() {
		int oldWcet = wcet;
		boolean oldWcetESet = wcetESet;
		wcet = WCET_EDEFAULT;
		wcetESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.SWC__WCET, oldWcet, WCET_EDEFAULT,
		 * oldWcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetWcet() {
		return wcetESet;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getBcet() {
		return bcet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setBcet(int newBcet) {
		int oldBcet = bcet;
		bcet = newBcet;
		boolean oldBcetESet = bcetESet;
		bcetESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__WCET, oldBcet, bcet, !oldBcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetBcet() {
		int oldBcet = bcet;
		boolean oldBcetESet = bcetESet;
		bcet = BCET_EDEFAULT;
		bcetESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.SWC__BCET, oldBcet, BCET_EDEFAULT,
		 * oldBcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetBcet() {
		return bcetESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getProcon() {
		return procon;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setProcon(String newProcon) {
		String oldProcon = procon;
		procon = newProcon;
		boolean oldProconESet = proconESet;
		proconESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__WCET, oldBcet, bcet, !oldBcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetProcon() {
		String oldProcon = procon;
		boolean oldProconESet = proconESet;
		procon = PROCON_EDEFAULT;
		proconESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.SWC__BCET, oldBcet, BCET_EDEFAULT,
		 * oldBcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetProcon() {
		return proconESet;
	}
	

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getReadCon() {
		return readCon;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setReadCon(String newReadCon) {
		String oldReadCon = readCon;
		readCon = newReadCon;
		boolean oldReadConESet = readConESet;
		readConESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__WCET, oldBcet, bcet, !oldBcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetReadCon() {
		String oldReadCon = readCon;
		boolean oldReadConESet = readConESet;
		readCon = READCON_EDEFAULT;
		readConESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.SWC__BCET, oldBcet, BCET_EDEFAULT,
		 * oldBcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetReadCon() {
		return readConESet;
	}
	


	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getWriteCon() {
		return writeCon;
	}
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setWriteCon(String newWriteCon) {
		String oldWriteCon = writeCon;
		writeCon = newWriteCon;
		boolean oldWriteConESet = writeConESet;
		writeConESet = true;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__WCET, oldBcet, bcet, !oldBcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetWriteCon() {
		String oldWriteCon = writeCon;
		boolean oldWriteConESet = writeConESet;
		writeCon = WRITECON_EDEFAULT;
		writeConESet = false;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.UNSET, DiagramPackage.SWC__BCET, oldBcet, BCET_EDEFAULT,
		 * oldBcetESet));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetWriteCon() {
		return writeConESet;
	}
	
	
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getRecvFrom() {
		return recvFrom;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setRecvFrom(String newRecvFrom) {
		String oldRecvFrom = recvFrom;
		recvFrom = newRecvFrom;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__RECV_FROM, oldRecvFrom, recvFrom));
		 */
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getSendTo() {
		return sendTo;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSendTo(String newSendTo) {
		String oldSendTo = sendTo;
		sendTo = newSendTo;
		/*
		 * if (eNotificationRequired()) eNotify(new ENotificationImpl(this,
		 * Notification.SET, DiagramPackage.SWC__SEND_TO, oldSendTo, sendTo));
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
		case DiagramPackage.SWC__DEADLINE:
			return getDeadline();
		case DiagramPackage.SWC__ECU_REF:
			if (resolve)
				return getEcuRef();
			return basicGetEcuRef();
		case DiagramPackage.SWC__ID:
			return getId();
		case DiagramPackage.SWC__PERIOD:
			return getPeriod();
		case DiagramPackage.SWC__PHASE:
			return getPhase();
		case DiagramPackage.SWC__VIRTUAL:
			return getVirtual();
		case DiagramPackage.SWC__WCET:
			return getWcet();
		case DiagramPackage.SWC__BCET:
			return getBcet();
		case DiagramPackage.SWC__RECV_FROM:
			return getRecvFrom();
		case DiagramPackage.SWC__SEND_TO:
			return getSendTo();
		case DiagramPackage.SWC__PROCON:
			return getProcon();
		case DiagramPackage.SWC__READCON:
			return getReadCon();
		case DiagramPackage.SWC__WRITECON:
			return getWriteCon();
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
		case DiagramPackage.SWC__DEADLINE:
			setDeadline((Integer) newValue);
			return;
		case DiagramPackage.SWC__ECU_REF:
			setEcuRef((ECU) newValue);
			return;
		case DiagramPackage.SWC__ID:
			setId((String) newValue);
			return;
		case DiagramPackage.SWC__PERIOD:
			setPeriod((Integer) newValue);
			return;
		case DiagramPackage.SWC__PHASE:
			setPhase((Integer) newValue);
			return;
		case DiagramPackage.SWC__VIRTUAL:
			setVirtual((VirtualCategory) newValue);
			return;
		case DiagramPackage.SWC__WCET:
			setWcet((Integer) newValue);
			return;
		case DiagramPackage.SWC__BCET:
			setBcet((Integer) newValue);
			return;
		case DiagramPackage.SWC__RECV_FROM:
			setRecvFrom((String) newValue);
			return;
		case DiagramPackage.SWC__SEND_TO:
			setSendTo((String) newValue);
			return;
		case DiagramPackage.SWC__PROCON:
			setProcon((String) newValue);
			return;
		case DiagramPackage.SWC__READCON:
			setReadCon((String) newValue);
			return;
		case DiagramPackage.SWC__WRITECON:
			setWriteCon((String) newValue);
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
		case DiagramPackage.SWC__DEADLINE:
			unsetDeadline();
			return;
		case DiagramPackage.SWC__ECU_REF:
			setEcuRef((ECU) null);
			return;
		case DiagramPackage.SWC__ID:
			setId(ID_EDEFAULT);
			return;
		case DiagramPackage.SWC__PERIOD:
			unsetPeriod();
			return;
		case DiagramPackage.SWC__PHASE:
			unsetPhase();
			return;
		case DiagramPackage.SWC__VIRTUAL:
			setVirtual(VIRTUAL_EDEFAULT);
			return;
		case DiagramPackage.SWC__WCET:
			unsetWcet();
			return;
		case DiagramPackage.SWC__BCET:
			unsetBcet();
			return;
		case DiagramPackage.SWC__RECV_FROM:
			setRecvFrom(RECV_FROM_EDEFAULT);
			return;
		case DiagramPackage.SWC__SEND_TO:
			setSendTo(SEND_TO_EDEFAULT);
			return;
		case DiagramPackage.SWC__PROCON:
			setProcon(PROCON_EDEFAULT);
			return;
		case DiagramPackage.SWC__READCON:
			setReadCon(READCON_EDEFAULT);
			return;
		case DiagramPackage.SWC__WRITECON:
			setWriteCon(WRITECON_EDEFAULT);
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
		case DiagramPackage.SWC__DEADLINE:
			return isSetDeadline();
		case DiagramPackage.SWC__ECU_REF:
			return ecuRef != null;
		case DiagramPackage.SWC__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case DiagramPackage.SWC__PERIOD:
			return isSetPeriod();
		case DiagramPackage.SWC__PHASE:
			return isSetPhase();
		case DiagramPackage.SWC__VIRTUAL:
			return virtual != VIRTUAL_EDEFAULT;
		case DiagramPackage.SWC__WCET:
			return isSetWcet();
		case DiagramPackage.SWC__BCET:
			return isSetBcet();
		case DiagramPackage.SWC__RECV_FROM:
			return RECV_FROM_EDEFAULT == null ? recvFrom != null : !RECV_FROM_EDEFAULT.equals(recvFrom);
		case DiagramPackage.SWC__SEND_TO:
			return SEND_TO_EDEFAULT == null ? sendTo != null : !SEND_TO_EDEFAULT.equals(sendTo);
		case DiagramPackage.SWC__PROCON:
			return PROCON_EDEFAULT == null? procon != null : !PROCON_EDEFAULT.equals(procon);
		case DiagramPackage.SWC__READCON:
			return READCON_EDEFAULT == null? readCon != null : !READCON_EDEFAULT.equals(readCon);
		case DiagramPackage.SWC__WRITECON:
			return WRITECON_EDEFAULT == null? writeCon != null : !WRITECON_EDEFAULT.equals(writeCon);

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
		result.append(" (deadline: ");
		if (deadlineESet)
			result.append(deadline);
		else
			result.append("<unset>");
		result.append(", id: ");
		result.append(id);
		result.append(", period: ");
		if (periodESet)
			result.append(period);
		else
			result.append("<unset>");
		result.append(", phase: ");
		if (phaseESet)
			result.append(phase);
		else
			result.append("<unset>");
		result.append(", virtual: ");
		result.append(virtual);
		result.append(", wcet: ");
		if (wcetESet)
			result.append(wcet);
		else
			result.append("<unset>");
		result.append(", bcet: ");
		if (bcetESet)
			result.append(bcet);
		else
			result.append("<unset>");
		result.append(", recvFrom: ");
		result.append(recvFrom);
		result.append(", sendTo: ");
		result.append(sendTo);
		result.append(')');
		return result.toString();
	}

} // SWCImpl
