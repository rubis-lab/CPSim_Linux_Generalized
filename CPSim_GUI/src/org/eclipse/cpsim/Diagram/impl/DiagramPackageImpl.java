/**
 */
package org.eclipse.cpsim.Diagram.impl;

import org.eclipse.cpsim.Diagram.DiagramFactory;
import org.eclipse.cpsim.Diagram.DiagramPackage;
import org.eclipse.cpsim.Diagram.Dual;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class DiagramPackageImpl extends EPackageImpl implements DiagramPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass canEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass carEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ecuEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass swcEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass dualEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum virtualCategoryEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType virtualCategoryObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method
	 * {@link #init init()}, which also performs initialization of the package, or
	 * returns the registered package, if one already exists. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.cpsim.Diagram.DiagramPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DiagramPackageImpl() {
		super(eNS_URI, DiagramFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and
	 * for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link DiagramPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DiagramPackage init() {
		if (isInited)
			return (DiagramPackage) EPackage.Registry.INSTANCE.getEPackage(DiagramPackage.eNS_URI);

		// Obtain or create and register package
		DiagramPackageImpl theDiagramPackage = (DiagramPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof DiagramPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new DiagramPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		EcorePackageImpl theEcorePackage = (EcorePackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI)
						: EcorePackage.eINSTANCE);

		// Create package meta-data objects
		theDiagramPackage.createPackageContents();
		theEcorePackage.createPackageContents();

		// Initialize created meta-data
		theDiagramPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDiagramPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DiagramPackage.eNS_URI, theDiagramPackage);
		return theDiagramPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCAN() {
		return canEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCAN_CarRef() {
		return (EReference) canEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCAN_EcuRefs() {
		return (EReference) canEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCAN_Bandwidth() {
		return (EAttribute) canEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generatedj
	 */
	public EAttribute getCAN_Id() {
		return (EAttribute) canEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCAN_Version() {
		return (EAttribute) canEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCAN_CanDB() {
		return (EAttribute) canEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCAN_IsCANFD() {
		return (EAttribute) canEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCAR() {
		return carEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCAR_CanRef() {
		return (EReference) carEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCAR_Id() {
		return (EAttribute) carEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getECU() {
		return ecuEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getECU_SwcRefs() {
		return (EReference) ecuEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getECU_CanRef() {
		return (EReference) ecuEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getECU_Id() {
		return (EAttribute) ecuEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getECU_NumberOfCores() {
		return (EAttribute) ecuEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getECU_SchedulingPolicy() {
		return (EAttribute) ecuEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getECU_SystemClock() {
		return (EAttribute) ecuEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getECU_Virtual() {
		return (EAttribute) ecuEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getECU_ModelName() {
		return (EAttribute) ecuEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getECU_TargetArchitecture() {
		return (EAttribute) ecuEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSWC() {
		return swcEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSWC_Deadline() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getSWC_EcuRef() {
		return (EReference) swcEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSWC_Id() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSWC_Period() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSWC_Phase() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSWC_Virtual() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSWC_Wcet() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSWC_Bcet() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(7);
	}

	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSWC_RecvFrom() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSWC_SendTo() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(9);
	}
	
	public EAttribute getSWC_ProCon() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(10);
	}
	public EAttribute getSWC_ReadCon() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(11);
	}
	public EAttribute getSWC_WriteCon() {
		return (EAttribute) swcEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDual() {
		return dualEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getDual_Period() {
		return (EAttribute) dualEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getVirtualCategory() {
		return virtualCategoryEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getVirtualCategoryObject() {
		return virtualCategoryObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramFactory getDiagramFactory() {
		return (DiagramFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		canEClass = createEClass(CAN);
		createEReference(canEClass, CAN__CAR_REF);
		createEReference(canEClass, CAN__ECU_REFS);
		createEAttribute(canEClass, CAN__BANDWIDTH);
		createEAttribute(canEClass, CAN__ID);
		createEAttribute(canEClass, CAN__VERSION);
		createEAttribute(canEClass, CAN__CAN_DB);
		createEAttribute(canEClass, CAN__IS_CANFD);

		carEClass = createEClass(CAR);
		createEReference(carEClass, CAR__CAN_REF);
		createEAttribute(carEClass, CAR__ID);

		ecuEClass = createEClass(ECU);
		createEReference(ecuEClass, ECU__SWC_REFS);
		createEReference(ecuEClass, ECU__CAN_REF);
		createEAttribute(ecuEClass, ECU__ID);
		createEAttribute(ecuEClass, ECU__NUMBER_OF_CORES);
		createEAttribute(ecuEClass, ECU__SCHEDULING_POLICY);
		createEAttribute(ecuEClass, ECU__SYSTEM_CLOCK);
		createEAttribute(ecuEClass, ECU__VIRTUAL);
		createEAttribute(ecuEClass, ECU__MODEL_NAME);
		createEAttribute(ecuEClass, ECU__TARGET_ARCHITECTURE);

		swcEClass = createEClass(SWC);
		createEAttribute(swcEClass, SWC__DEADLINE);
		createEReference(swcEClass, SWC__ECU_REF);
		createEAttribute(swcEClass, SWC__ID);
		createEAttribute(swcEClass, SWC__PERIOD);
		createEAttribute(swcEClass, SWC__PHASE);
		createEAttribute(swcEClass, SWC__VIRTUAL);
		createEAttribute(swcEClass, SWC__WCET);
		createEAttribute(swcEClass, SWC__BCET);
		createEAttribute(swcEClass, SWC__RECV_FROM);
		createEAttribute(swcEClass, SWC__SEND_TO);
		createEAttribute(swcEClass, SWC__PROCON);
		createEAttribute(swcEClass, SWC__READCON);
		createEAttribute(swcEClass, SWC__WRITECON);

		
		dualEClass = createEClass(DUAL);
		createEAttribute(dualEClass, DUAL__PERIOD);

		// Create enums
		virtualCategoryEEnum = createEEnum(VIRTUAL_CATEGORY);

		// Create data types
		virtualCategoryObjectEDataType = createEDataType(VIRTUAL_CATEGORY_OBJECT);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is
	 * guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(canEClass, org.eclipse.cpsim.Diagram.CAN.class, "ETH", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCAN_CarRef(), this.getCAR(), null, "carRef", null, 0, 1,
				org.eclipse.cpsim.Diagram.CAN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCAN_EcuRefs(), this.getECU(), null, "ecuRefs", null, 0, -1,
				org.eclipse.cpsim.Diagram.CAN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCAN_Bandwidth(), theEcorePackage.getEInt(), "bandwidth", null, 0, 1,
				org.eclipse.cpsim.Diagram.CAN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCAN_Id(), theEcorePackage.getEString(), "id", null, 0, 1,
				org.eclipse.cpsim.Diagram.CAN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCAN_Version(), theEcorePackage.getEString(), "version", null, 0, 1,
				org.eclipse.cpsim.Diagram.CAN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCAN_CanDB(), theEcorePackage.getEString(), "canDB", null, 0, 1,
				org.eclipse.cpsim.Diagram.CAN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCAN_IsCANFD(), theEcorePackage.getEBoolean(), "isCANFD", null, 0, 1,
				org.eclipse.cpsim.Diagram.CAN.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(carEClass, org.eclipse.cpsim.Diagram.CAR.class, "CAR", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCAR_CanRef(), this.getCAN(), null, "canRef", null, 1, 1,
				org.eclipse.cpsim.Diagram.CAR.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCAR_Id(), theEcorePackage.getEString(), "id", null, 0, 1,
				org.eclipse.cpsim.Diagram.CAR.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ecuEClass, org.eclipse.cpsim.Diagram.ECU.class, "ECU", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getECU_SwcRefs(), this.getSWC(), null, "swcRefs", null, 0, -1,
				org.eclipse.cpsim.Diagram.ECU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getECU_CanRef(), this.getCAN(), null, "canRef", null, 0, 1,
				org.eclipse.cpsim.Diagram.ECU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getECU_Id(), theEcorePackage.getEString(), "id", null, 0, 1,
				org.eclipse.cpsim.Diagram.ECU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getECU_NumberOfCores(), theEcorePackage.getEInt(), "numberOfCores", null, 0, 1,
				org.eclipse.cpsim.Diagram.ECU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getECU_SchedulingPolicy(), theEcorePackage.getEString(), "schedulingPolicy", null, 0, 1,
				org.eclipse.cpsim.Diagram.ECU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getECU_SystemClock(), theEcorePackage.getEInt(), "systemClock", null, 0, 1,
				org.eclipse.cpsim.Diagram.ECU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getECU_Virtual(), this.getVirtualCategory(), "virtual", null, 0, 1,
				org.eclipse.cpsim.Diagram.ECU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getECU_ModelName(), theEcorePackage.getEString(), "modelName", null, 0, 1,
				org.eclipse.cpsim.Diagram.ECU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getECU_TargetArchitecture(), theEcorePackage.getEString(), "targetArchitecture", null, 0, 1,
				org.eclipse.cpsim.Diagram.ECU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(swcEClass, org.eclipse.cpsim.Diagram.SWC.class, "SWC", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSWC_Deadline(), theEcorePackage.getEInt(), "deadline", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSWC_EcuRef(), this.getECU(), null, "ecuRef", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_Id(), theEcorePackage.getEString(), "id", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_Period(), theEcorePackage.getEInt(), "period", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_Phase(), theEcorePackage.getEInt(), "phase", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_Virtual(), this.getVirtualCategory(), "virtual", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_Wcet(), theEcorePackage.getEInt(), "wcet", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_Bcet(), theEcorePackage.getEInt(), "bcet", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_RecvFrom(), theEcorePackage.getEString(), "recvFrom", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_SendTo(), theEcorePackage.getEString(), "sendTo", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_ProCon(), theEcorePackage.getEString(), "proCon", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_ReadCon(), theEcorePackage.getEString(), "readCon", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSWC_WriteCon(), theEcorePackage.getEString(), "writeCon", null, 0, 1,
				org.eclipse.cpsim.Diagram.SWC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dualEClass, Dual.class, "Dual", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDual_Period(), theEcorePackage.getEInt(), "period", null, 0, 1, Dual.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(virtualCategoryEEnum, VirtualCategory.class, "VirtualCategory");
		addEEnumLiteral(virtualCategoryEEnum, VirtualCategory.VISIBLE);
		addEEnumLiteral(virtualCategoryEEnum, VirtualCategory.INVISIBLE);

		// Initialize data types
		initEDataType(virtualCategoryObjectEDataType, VirtualCategory.class, "VirtualCategoryObject", IS_SERIALIZABLE,
				IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for
	 * <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation(canEClass, source, new String[] { "name", "ETH", "kind", "elementOnly" });
		addAnnotation(getCAN_CarRef(), source, new String[] { "kind", "element", "name", "carRef" });
		addAnnotation(getCAN_EcuRefs(), source, new String[] { "kind", "element", "name", "ecuRefs" });
		addAnnotation(getCAN_Bandwidth(), source, new String[] { "kind", "attribute", "name", "bandwidth" });
		addAnnotation(getCAN_Id(), source, new String[] { "kind", "attribute", "name", "id" });
		addAnnotation(getCAN_Version(), source, new String[] { "kind", "attribute", "name", "version" });
		addAnnotation(carEClass, source, new String[] { "name", "CAR", "kind", "empty" });
		addAnnotation(getCAR_CanRef(), source, new String[] { "kind", "attribute", "name", "canRef" });
		addAnnotation(getCAR_Id(), source, new String[] { "kind", "attribute", "name", "id" });
		addAnnotation(ecuEClass, source, new String[] { "name", "ECU", "kind", "elementOnly" });
		addAnnotation(getECU_SwcRefs(), source, new String[] { "kind", "element", "name", "swcRefs" });
		addAnnotation(getECU_CanRef(), source, new String[] { "kind", "attribute", "name", "canRef" });
		addAnnotation(getECU_Id(), source, new String[] { "kind", "attribute", "name", "id" });
		addAnnotation(getECU_NumberOfCores(), source, new String[] { "kind", "attribute", "name", "numberOfCores" });
		addAnnotation(getECU_SchedulingPolicy(), source,
				new String[] { "kind", "attribute", "name", "schedulingPolicy" });
		addAnnotation(getECU_SystemClock(), source, new String[] { "kind", "attribute", "name", "systemClock" });
		addAnnotation(getECU_Virtual(), source, new String[] { "kind", "attribute", "name", "virtual" });
		addAnnotation(swcEClass, source, new String[] { "name", "SWC", "kind", "empty" });
		addAnnotation(getSWC_Deadline(), source, new String[] { "kind", "attribute", "name", "deadline" });
		addAnnotation(getSWC_EcuRef(), source, new String[] { "kind", "attribute", "name", "ecuRef" });
		addAnnotation(getSWC_Id(), source, new String[] { "kind", "attribute", "name", "id" });
		addAnnotation(getSWC_Period(), source, new String[] { "kind", "attribute", "name", "period" });
		addAnnotation(getSWC_Phase(), source, new String[] { "kind", "attribute", "name", "phase" });
		addAnnotation(getSWC_Virtual(), source, new String[] { "kind", "attribute", "name", "virtual" });
		addAnnotation(getSWC_Wcet(), source, new String[] { "kind", "attribute", "name", "wcet" });
		addAnnotation(getSWC_Bcet(), source, new String[] { "kind", "attribute", "name", "bcet" });
		addAnnotation(getSWC_ProCon(), source, new String[] { "kind", "attribute", "name", "procon" });
		addAnnotation(getSWC_ReadCon(), source, new String[] { "kind", "attribute", "name", "readCon" });
		addAnnotation(getSWC_WriteCon(), source, new String[] { "kind", "attribute", "name", "writeCon" });

		addAnnotation(virtualCategoryEEnum, source, new String[] { "name", "VirtualCategory" });
		addAnnotation(virtualCategoryObjectEDataType, source,
				new String[] { "name", "VirtualCategory:Object", "baseType", "VirtualCategory" });
	}

} // DiagramPackageImpl
