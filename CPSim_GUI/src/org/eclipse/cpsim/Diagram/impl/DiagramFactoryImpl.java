/**
 */
package org.eclipse.cpsim.Diagram.impl;

import org.eclipse.cpsim.Diagram.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class DiagramFactoryImpl extends EFactoryImpl implements DiagramFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static DiagramFactory init() {
		try {
			DiagramFactory theDiagramFactory = (DiagramFactory) EPackage.Registry.INSTANCE
					.getEFactory(DiagramPackage.eNS_URI);
			if (theDiagramFactory != null) {
				return theDiagramFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DiagramFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public DiagramFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case DiagramPackage.CAN:
			return createCAN();
		case DiagramPackage.CAR:
			return createCAR();
		case DiagramPackage.ECU:
			return createECU();
		case DiagramPackage.SWC:
			return createSWC();
		case DiagramPackage.DUAL:
			return createDual();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case DiagramPackage.VIRTUAL_CATEGORY:
			return createVirtualCategoryFromString(eDataType, initialValue);
		case DiagramPackage.VIRTUAL_CATEGORY_OBJECT:
			return createVirtualCategoryObjectFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case DiagramPackage.VIRTUAL_CATEGORY:
			return convertVirtualCategoryToString(eDataType, instanceValue);
		case DiagramPackage.VIRTUAL_CATEGORY_OBJECT:
			return convertVirtualCategoryObjectToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CAN createCAN() {
		CANImpl can = new CANImpl();
		return can;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CAR createCAR() {
		CARImpl car = new CARImpl();
		return car;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ECU createECU() {
		ECUImpl ecu = new ECUImpl();
		return ecu;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SWC createSWC() {
		SWCImpl swc = new SWCImpl();
		return swc;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Dual createDual() {
		DualImpl dual = new DualImpl();
		return dual;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VirtualCategory createVirtualCategoryFromString(EDataType eDataType, String initialValue) {
		VirtualCategory result = VirtualCategory.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertVirtualCategoryToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VirtualCategory createVirtualCategoryObjectFromString(EDataType eDataType, String initialValue) {
		return createVirtualCategoryFromString(DiagramPackage.Literals.VIRTUAL_CATEGORY, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertVirtualCategoryObjectToString(EDataType eDataType, Object instanceValue) {
		return convertVirtualCategoryToString(DiagramPackage.Literals.VIRTUAL_CATEGORY, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramPackage getDiagramPackage() {
		return (DiagramPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DiagramPackage getPackage() {
		return DiagramPackage.eINSTANCE;
	}

} // DiagramFactoryImpl
