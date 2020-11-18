/**
 */
package org.eclipse.cpsim.Diagram;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.cpsim.Diagram.DiagramPackage
 * @generated
 */
public interface DiagramFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	DiagramFactory eINSTANCE = org.eclipse.cpsim.Diagram.impl.DiagramFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>CAN</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>CAN</em>'.
	 * @generated
	 */
	CAN createCAN();

	/**
	 * Returns a new object of class '<em>CAR</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>CAR</em>'.
	 * @generated
	 */
	CAR createCAR();

	/**
	 * Returns a new object of class '<em>ECU</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>ECU</em>'.
	 * @generated
	 */
	ECU createECU();

	/**
	 * Returns a new object of class '<em>SWC</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>SWC</em>'.
	 * @generated
	 */
	SWC createSWC();

	/**
	 * Returns a new object of class '<em>Dual</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Dual</em>'.
	 * @generated
	 */
	Dual createDual();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	DiagramPackage getDiagramPackage();

} // DiagramFactory
