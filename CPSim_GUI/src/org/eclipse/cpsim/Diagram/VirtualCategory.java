/**
 */
package org.eclipse.cpsim.Diagram;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Virtual Category</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.cpsim.Diagram.DiagramPackage#getVirtualCategory()
 * @model extendedMetaData="name='VirtualCategory'"
 * @generated
 */
public enum VirtualCategory implements Enumerator {
	/**
	 * The '<em><b>Visible</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #VISIBLE_VALUE
	 * @generated
	 * @ordered
	 */
	VISIBLE(0, "Visible", "Visible"),

	/**
	 * The '<em><b>Invisible</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #INVISIBLE_VALUE
	 * @generated
	 * @ordered
	 */
	INVISIBLE(1, "Invisible", "Invisible");

	/**
	 * The '<em><b>Visible</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Visible</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #VISIBLE
	 * @model name="Visible"
	 * @generated
	 * @ordered
	 */
	public static final int VISIBLE_VALUE = 0;

	/**
	 * The '<em><b>Invisible</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Invisible</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #INVISIBLE
	 * @model name="Invisible"
	 * @generated
	 * @ordered
	 */
	public static final int INVISIBLE_VALUE = 1;

	/**
	 * An array of all the '<em><b>Virtual Category</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final VirtualCategory[] VALUES_ARRAY = new VirtualCategory[] { VISIBLE, INVISIBLE, };

	/**
	 * A public read-only list of all the '<em><b>Virtual Category</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<VirtualCategory> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Virtual Category</b></em>' literal with the specified
	 * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static VirtualCategory get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VirtualCategory result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Virtual Category</b></em>' literal with the specified
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static VirtualCategory getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VirtualCategory result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Virtual Category</b></em>' literal with the specified
	 * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static VirtualCategory get(int value) {
		switch (value) {
		case VISIBLE_VALUE:
			return VISIBLE;
		case INVISIBLE_VALUE:
			return INVISIBLE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private VirtualCategory(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // VirtualCategory
