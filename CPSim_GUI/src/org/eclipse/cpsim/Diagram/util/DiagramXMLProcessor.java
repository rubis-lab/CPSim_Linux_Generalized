/**
 */
package org.eclipse.cpsim.Diagram.util;

import java.util.Map;

import org.eclipse.cpsim.Diagram.DiagramPackage;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class DiagramXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public DiagramXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		DiagramPackage.eINSTANCE.eClass();
	}

	/**
	 * Register for "*" and "xml" file extensions the DiagramResourceFactoryImpl
	 * factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new DiagramResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new DiagramResourceFactoryImpl());
		}
		return registrations;
	}

} // DiagramXMLProcessor
