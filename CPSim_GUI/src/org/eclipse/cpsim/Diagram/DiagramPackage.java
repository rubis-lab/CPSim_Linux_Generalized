/**
 */
package org.eclipse.cpsim.Diagram;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.cpsim.Diagram.DiagramFactory
 * @model kind="package"
 * @generated
 */
public interface DiagramPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "Diagram";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http:///org/eclipse/hyundai/configurator.ecore";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.hyundai.configurator";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	DiagramPackage eINSTANCE = org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.cpsim.Diagram.impl.CANImpl
	 * <em>CAN</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.cpsim.Diagram.impl.CANImpl
	 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getCAN()
	 * @generated
	 */
	int CAN = 0;

	/**
	 * The feature id for the '<em><b>Car Ref</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAN__CAR_REF = 0;

	/**
	 * The feature id for the '<em><b>Ecu Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAN__ECU_REFS = 1;

	/**
	 * The feature id for the '<em><b>Bandwidth</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAN__BANDWIDTH = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAN__ID = 3;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAN__VERSION = 4;

	/**
	 * The feature id for the '<em><b>Can DB</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAN__CAN_DB = 5;

	/**
	 * The feature id for the '<em><b>Is CANFD</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAN__IS_CANFD = 6;

	/**
	 * The number of structural features of the '<em>CAN</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAN_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.cpsim.Diagram.impl.CARImpl
	 * <em>CAR</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.cpsim.Diagram.impl.CARImpl
	 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getCAR()
	 * @generated
	 */
	int CAR = 1;

	/**
	 * The feature id for the '<em><b>Can Ref</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAR__CAN_REF = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAR__ID = 1;

	/**
	 * The number of structural features of the '<em>CAR</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CAR_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.cpsim.Diagram.impl.ECUImpl
	 * <em>ECU</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.cpsim.Diagram.impl.ECUImpl
	 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getECU()
	 * @generated
	 */
	int ECU = 2;

	/**
	 * The feature id for the '<em><b>Swc Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU__SWC_REFS = 0;

	/**
	 * The feature id for the '<em><b>Can Ref</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU__CAN_REF = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU__ID = 2;

	/**
	 * The feature id for the '<em><b>Number Of Cores</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU__NUMBER_OF_CORES = 3;

	/**
	 * The feature id for the '<em><b>Scheduling Policy</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU__SCHEDULING_POLICY = 4;

	/**
	 * The feature id for the '<em><b>System Clock</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU__SYSTEM_CLOCK = 5;

	/**
	 * The feature id for the '<em><b>Virtual</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU__VIRTUAL = 6;

	/**
	 * The feature id for the '<em><b>Model Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU__MODEL_NAME = 7;

	/**
	 * The feature id for the '<em><b>Target Architecture</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU__TARGET_ARCHITECTURE = 8;

	/**
	 * The number of structural features of the '<em>ECU</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECU_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.cpsim.Diagram.impl.SWCImpl
	 * <em>SWC</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.cpsim.Diagram.impl.SWCImpl
	 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getSWC()
	 * @generated
	 */
	int SWC = 3;

	/**
	 * The feature id for the '<em><b>Deadline</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__DEADLINE = 0;

	/**
	 * The feature id for the '<em><b>Ecu Ref</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__ECU_REF = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__ID = 2;

	/**
	 * The feature id for the '<em><b>Period</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__PERIOD = 3;

	/**
	 * The feature id for the '<em><b>Phase</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__PHASE = 4;

	/**
	 * The feature id for the '<em><b>Virtual</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__VIRTUAL = 5;

	/**
	 * The feature id for the '<em><b>Wcet</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__WCET = 6;
	
	/**
	 * The feature id for the '<em><b>Bcet</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__BCET = 7;

	/**
	 * The feature id for the '<em><b>Recv From</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__RECV_FROM = 8;

	/**
	 * The feature id for the '<em><b>Send To</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__SEND_TO = 9;
	
	/**
	 * The feature id for the '<em><b>Procon</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__PROCON = 10;

	/**
	 * The feature id for the '<em><b>ReadCon</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__READCON = 11;
	
	/**
	 * The feature id for the '<em><b>WriteCon</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC__WRITECON = 12;

	/**
	 * The number of structural features of the '<em>SWC</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SWC_FEATURE_COUNT = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.cpsim.Diagram.impl.DualImpl
	 * <em>Dual</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.cpsim.Diagram.impl.DualImpl
	 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getDual()
	 * @generated
	 */
	int DUAL = 4;

	/**
	 * The feature id for the '<em><b>Period</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DUAL__PERIOD = 0;

	/**
	 * The number of structural features of the '<em>Dual</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DUAL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.cpsim.Diagram.VirtualCategory <em>Virtual
	 * Category</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.cpsim.Diagram.VirtualCategory
	 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getVirtualCategory()
	 * @generated
	 */
	int VIRTUAL_CATEGORY = 5;

	/**
	 * The meta object id for the '<em>Virtual Category Object</em>' data type. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.cpsim.Diagram.VirtualCategory
	 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getVirtualCategoryObject()
	 * @generated
	 */
	int VIRTUAL_CATEGORY_OBJECT = 6;

	/**
	 * Returns the meta object for class '{@link org.eclipse.cpsim.Diagram.CAN
	 * <em>CAN</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>CAN</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAN
	 * @generated
	 */
	EClass getCAN();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.cpsim.Diagram.CAN#getCarRef <em>Car Ref</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Car Ref</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAN#getCarRef()
	 * @see #getCAN()
	 * @generated
	 */
	EReference getCAN_CarRef();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.cpsim.Diagram.CAN#getEcuRefs <em>Ecu Refs</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Ecu
	 *         Refs</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAN#getEcuRefs()
	 * @see #getCAN()
	 * @generated
	 */
	EReference getCAN_EcuRefs();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.CAN#getBandwidth <em>Bandwidth</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Bandwidth</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAN#getBandwidth()
	 * @see #getCAN()
	 * @generated
	 */
	EAttribute getCAN_Bandwidth();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.CAN#getId <em>Id</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAN#getId()
	 * @see #getCAN()
	 * @generated
	 */
	EAttribute getCAN_Id();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.CAN#getVersion <em>Version</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAN#getVersion()
	 * @see #getCAN()
	 * @generated
	 */
	EAttribute getCAN_Version();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.CAN#getCanDB <em>Can DB</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Can DB</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAN#getCanDB()
	 * @see #getCAN()
	 * @generated
	 */
	EAttribute getCAN_CanDB();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.CAN#isIsCANFD <em>Is CANFD</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Is CANFD</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAN#isIsCANFD()
	 * @see #getCAN()
	 * @generated
	 */
	EAttribute getCAN_IsCANFD();

	/**
	 * Returns the meta object for class '{@link org.eclipse.cpsim.Diagram.CAR
	 * <em>CAR</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>CAR</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAR
	 * @generated
	 */
	EClass getCAR();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.cpsim.Diagram.CAR#getCanRef <em>Can Ref</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Can Ref</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAR#getCanRef()
	 * @see #getCAR()
	 * @generated
	 */
	EReference getCAR_CanRef();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.CAR#getId <em>Id</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.cpsim.Diagram.CAR#getId()
	 * @see #getCAR()
	 * @generated
	 */
	EAttribute getCAR_Id();

	/**
	 * Returns the meta object for class '{@link org.eclipse.cpsim.Diagram.ECU
	 * <em>ECU</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>ECU</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU
	 * @generated
	 */
	EClass getECU();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getSwcRefs <em>Swc Refs</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Swc
	 *         Refs</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU#getSwcRefs()
	 * @see #getECU()
	 * @generated
	 */
	EReference getECU_SwcRefs();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getCanRef <em>Can Ref</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Can Ref</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU#getCanRef()
	 * @see #getECU()
	 * @generated
	 */
	EReference getECU_CanRef();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getId <em>Id</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU#getId()
	 * @see #getECU()
	 * @generated
	 */
	EAttribute getECU_Id();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getNumberOfCores <em>Number Of
	 * Cores</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Number Of Cores</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU#getNumberOfCores()
	 * @see #getECU()
	 * @generated
	 */
	EAttribute getECU_NumberOfCores();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getSchedulingPolicy <em>Scheduling
	 * Policy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Scheduling Policy</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU#getSchedulingPolicy()
	 * @see #getECU()
	 * @generated
	 */
	EAttribute getECU_SchedulingPolicy();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getSystemClock <em>System
	 * Clock</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>System Clock</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU#getSystemClock()
	 * @see #getECU()
	 * @generated
	 */
	EAttribute getECU_SystemClock();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getVirtual <em>Virtual</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Virtual</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU#getVirtual()
	 * @see #getECU()
	 * @generated
	 */
	EAttribute getECU_Virtual();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getModelName <em>Model Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Model Name</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU#getModelName()
	 * @see #getECU()
	 * @generated
	 */
	EAttribute getECU_ModelName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.ECU#getTargetArchitecture <em>Target
	 * Architecture</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Target Architecture</em>'.
	 * @see org.eclipse.cpsim.Diagram.ECU#getTargetArchitecture()
	 * @see #getECU()
	 * @generated
	 */
	EAttribute getECU_TargetArchitecture();

	/**
	 * Returns the meta object for class '{@link org.eclipse.cpsim.Diagram.SWC
	 * <em>SWC</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>SWC</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC
	 * @generated
	 */
	EClass getSWC();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getDeadline <em>Deadline</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Deadline</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getDeadline()
	 * @see #getSWC()
	 * @generated
	 */
	EAttribute getSWC_Deadline();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getEcuRef <em>Ecu Ref</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Ecu Ref</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getEcuRef()
	 * @see #getSWC()
	 * @generated
	 */
	EReference getSWC_EcuRef();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getId <em>Id</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getId()
	 * @see #getSWC()
	 * @generated
	 */
	EAttribute getSWC_Id();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getPeriod <em>Period</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Period</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getPeriod()
	 * @see #getSWC()
	 * @generated
	 */
	EAttribute getSWC_Period();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getPhase <em>Phase</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Phase</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getPhase()
	 * @see #getSWC()
	 * @generated
	 */
	EAttribute getSWC_Phase();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getVirtual <em>Virtual</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Virtual</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getVirtual()
	 * @see #getSWC()
	 * @generated
	 */
	EAttribute getSWC_Virtual();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getWcet <em>Wcet</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Wcet</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getWcet()
	 * @see #getSWC()
	 * @generated
	 */
	EAttribute getSWC_Wcet();
	
	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getBcet <em>Bcet</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Bcet</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getBcet()
	 * @see #getSWC()
	 * @generated
	 */
	EAttribute getSWC_Bcet();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getRecvFrom <em>Recv From</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Recv From</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getRecvFrom()
	 * @see #getSWC()
	 * @generated
	 */
	EAttribute getSWC_RecvFrom();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.SWC#getSendTo <em>Send To</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Send To</em>'.
	 * @see org.eclipse.cpsim.Diagram.SWC#getSendTo()
	 * @see #getSWC()
	 * @generated
	 */
	EAttribute getSWC_SendTo();
	
	EAttribute getSWC_ProCon();
	EAttribute getSWC_ReadCon();
	EAttribute getSWC_WriteCon();

	/**
	 * Returns the meta object for class '{@link org.eclipse.cpsim.Diagram.Dual
	 * <em>Dual</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Dual</em>'.
	 * @see org.eclipse.cpsim.Diagram.Dual
	 * @generated
	 */
	EClass getDual();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.cpsim.Diagram.Dual#getPeriod <em>Period</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Period</em>'.
	 * @see org.eclipse.cpsim.Diagram.Dual#getPeriod()
	 * @see #getDual()
	 * @generated
	 */
	EAttribute getDual_Period();

	/**
	 * Returns the meta object for enum
	 * '{@link org.eclipse.cpsim.Diagram.VirtualCategory <em>Virtual
	 * Category</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Virtual Category</em>'.
	 * @see org.eclipse.cpsim.Diagram.VirtualCategory
	 * @generated
	 */
	EEnum getVirtualCategory();

	/**
	 * Returns the meta object for data type
	 * '{@link org.eclipse.cpsim.Diagram.VirtualCategory <em>Virtual Category
	 * Object</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Virtual Category Object</em>'.
	 * @see org.eclipse.cpsim.Diagram.VirtualCategory
	 * @model instanceClass="org.eclipse.hyundai.Diagram.VirtualCategory"
	 *        extendedMetaData="name='VirtualCategory:Object'
	 *        baseType='VirtualCategory'"
	 * @generated
	 */
	EDataType getVirtualCategoryObject();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DiagramFactory getDiagramFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.cpsim.Diagram.impl.CANImpl <em>CAN</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.cpsim.Diagram.impl.CANImpl
		 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getCAN()
		 * @generated
		 */
		EClass CAN = eINSTANCE.getCAN();

		/**
		 * The meta object literal for the '<em><b>Car Ref</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CAN__CAR_REF = eINSTANCE.getCAN_CarRef();

		/**
		 * The meta object literal for the '<em><b>Ecu Refs</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CAN__ECU_REFS = eINSTANCE.getCAN_EcuRefs();

		/**
		 * The meta object literal for the '<em><b>Bandwidth</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CAN__BANDWIDTH = eINSTANCE.getCAN_Bandwidth();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CAN__ID = eINSTANCE.getCAN_Id();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CAN__VERSION = eINSTANCE.getCAN_Version();

		/**
		 * The meta object literal for the '<em><b>Can DB</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CAN__CAN_DB = eINSTANCE.getCAN_CanDB();

		/**
		 * The meta object literal for the '<em><b>Is CANFD</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CAN__IS_CANFD = eINSTANCE.getCAN_IsCANFD();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.cpsim.Diagram.impl.CARImpl <em>CAR</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.cpsim.Diagram.impl.CARImpl
		 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getCAR()
		 * @generated
		 */
		EClass CAR = eINSTANCE.getCAR();

		/**
		 * The meta object literal for the '<em><b>Can Ref</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CAR__CAN_REF = eINSTANCE.getCAR_CanRef();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CAR__ID = eINSTANCE.getCAR_Id();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.cpsim.Diagram.impl.ECUImpl <em>ECU</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.cpsim.Diagram.impl.ECUImpl
		 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getECU()
		 * @generated
		 */
		EClass ECU = eINSTANCE.getECU();

		/**
		 * The meta object literal for the '<em><b>Swc Refs</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ECU__SWC_REFS = eINSTANCE.getECU_SwcRefs();

		/**
		 * The meta object literal for the '<em><b>Can Ref</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ECU__CAN_REF = eINSTANCE.getECU_CanRef();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ECU__ID = eINSTANCE.getECU_Id();

		/**
		 * The meta object literal for the '<em><b>Number Of Cores</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ECU__NUMBER_OF_CORES = eINSTANCE.getECU_NumberOfCores();

		/**
		 * The meta object literal for the '<em><b>Scheduling Policy</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ECU__SCHEDULING_POLICY = eINSTANCE.getECU_SchedulingPolicy();

		/**
		 * The meta object literal for the '<em><b>System Clock</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ECU__SYSTEM_CLOCK = eINSTANCE.getECU_SystemClock();

		/**
		 * The meta object literal for the '<em><b>Virtual</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ECU__VIRTUAL = eINSTANCE.getECU_Virtual();

		/**
		 * The meta object literal for the '<em><b>Model Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ECU__MODEL_NAME = eINSTANCE.getECU_ModelName();

		/**
		 * The meta object literal for the '<em><b>Target Architecture</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ECU__TARGET_ARCHITECTURE = eINSTANCE.getECU_TargetArchitecture();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.cpsim.Diagram.impl.SWCImpl <em>SWC</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.cpsim.Diagram.impl.SWCImpl
		 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getSWC()
		 * @generated
		 */
		EClass SWC = eINSTANCE.getSWC();

		/**
		 * The meta object literal for the '<em><b>Deadline</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SWC__DEADLINE = eINSTANCE.getSWC_Deadline();

		/**
		 * The meta object literal for the '<em><b>Ecu Ref</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SWC__ECU_REF = eINSTANCE.getSWC_EcuRef();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SWC__ID = eINSTANCE.getSWC_Id();

		/**
		 * The meta object literal for the '<em><b>Period</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SWC__PERIOD = eINSTANCE.getSWC_Period();

		/**
		 * The meta object literal for the '<em><b>Phase</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SWC__PHASE = eINSTANCE.getSWC_Phase();

		/**
		 * The meta object literal for the '<em><b>Virtual</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SWC__VIRTUAL = eINSTANCE.getSWC_Virtual();

		/**
		 * The meta object literal for the '<em><b>Wcet</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SWC__WCET = eINSTANCE.getSWC_Wcet();
		/**
		 * The meta object literal for the '<em><b>Wcet</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SWC__BCET = eINSTANCE.getSWC_Bcet();

		/**
		 * The meta object literal for the '<em><b>Recv From</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SWC__RECV_FROM = eINSTANCE.getSWC_RecvFrom();

		/**
		 * The meta object literal for the '<em><b>Send To</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SWC__SEND_TO = eINSTANCE.getSWC_SendTo();

		EAttribute SWC__PROCON = eINSTANCE.getSWC_ProCon();
		EAttribute SWC__READCON = eINSTANCE.getSWC_ReadCon();
		EAttribute SWC__WRITECON = eINSTANCE.getSWC_WriteCon();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.cpsim.Diagram.impl.DualImpl <em>Dual</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.cpsim.Diagram.impl.DualImpl
		 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getDual()
		 * @generated
		 */
		EClass DUAL = eINSTANCE.getDual();

		/**
		 * The meta object literal for the '<em><b>Period</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DUAL__PERIOD = eINSTANCE.getDual_Period();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.cpsim.Diagram.VirtualCategory <em>Virtual
		 * Category</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.cpsim.Diagram.VirtualCategory
		 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getVirtualCategory()
		 * @generated
		 */
		EEnum VIRTUAL_CATEGORY = eINSTANCE.getVirtualCategory();

		/**
		 * The meta object literal for the '<em>Virtual Category Object</em>' data type.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.cpsim.Diagram.VirtualCategory
		 * @see org.eclipse.cpsim.Diagram.impl.DiagramPackageImpl#getVirtualCategoryObject()
		 * @generated
		 */
		EDataType VIRTUAL_CATEGORY_OBJECT = eINSTANCE.getVirtualCategoryObject();

	}

} // DiagramPackage
