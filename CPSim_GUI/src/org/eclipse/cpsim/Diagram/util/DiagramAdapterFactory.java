/**
 */
package org.eclipse.cpsim.Diagram.util;

import org.eclipse.cpsim.Diagram.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see org.eclipse.cpsim.Diagram.DiagramPackage
 * @generated
 */
public class DiagramAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static DiagramPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DiagramPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!--
	 * begin-user-doc --> This implementation returns <code>true</code> if the
	 * object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DiagramSwitch<Adapter> modelSwitch = new DiagramSwitch<Adapter>() {
		@Override
		public Adapter caseCAN(CAN object) {
			return createCANAdapter();
		}

		@Override
		public Adapter caseCAR(CAR object) {
			return createCARAdapter();
		}

		@Override
		public Adapter caseECU(ECU object) {
			return createECUAdapter();
		}

		@Override
		public Adapter caseSWC(SWC object) {
			return createSWCAdapter();
		}

		@Override
		public Adapter caseDual(Dual object) {
			return createDualAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.cpsim.Diagram.CAN <em>CAN</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.cpsim.Diagram.CAN
	 * @generated
	 */
	public Adapter createCANAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.cpsim.Diagram.CAR <em>CAR</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.cpsim.Diagram.CAR
	 * @generated
	 */
	public Adapter createCARAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.cpsim.Diagram.ECU <em>ECU</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.cpsim.Diagram.ECU
	 * @generated
	 */
	public Adapter createECUAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.cpsim.Diagram.SWC <em>SWC</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.cpsim.Diagram.SWC
	 * @generated
	 */
	public Adapter createSWCAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.cpsim.Diagram.Dual <em>Dual</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.cpsim.Diagram.Dual
	 * @generated
	 */
	public Adapter createDualAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // DiagramAdapterFactory
