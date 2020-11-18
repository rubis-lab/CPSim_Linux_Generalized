package org.eclipse.cpsim.Diagram;

import org.eclipse.cpsim.Diagram.util.MESSAGES;

public class Message {

	public static final String DEFAULT_CAR_NAME = "CAR";
	public static final String DEFAULT_CAN_NAME = "ETH";
	public static final String DEFAULT_ECU_NAME = "ECU";
	public static final String DEFAULT_SWC_NAME = "SWC";

	public static final String[] N_CORES_LIST = { "1", "2", "3", "4" };
	public static final String[] SCHEDULING_LIST = { "RM", "DM", "EDF", "CPS" };
	public static final String[] TARGET_ARCHITECTURE_LIST = { "SPC56EL70", "TC275" };
	public static final String[] IS_CANFD_LIST = { "CAN", "CAN-FD" };

	protected static final String PREFIX = "(Y|Z|E|P|T|G|M|k|h|da|d)";

	public static final String PATTERN_ID = "^\\w+$";
	public static final String PATTERN_NAME = "";
	public static final String PATTERN_DIGIT = "^\\d+$";
	public static final String PATTERN_DIGIT_WITH_SI_PREFIX = "";
	
}
