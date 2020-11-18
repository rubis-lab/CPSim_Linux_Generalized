package org.eclipse.cpsim.Diagram;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class CustomImageProvider extends AbstractImageProvider {

	/* The prefix for all identifiers of this image provider */
	protected static final String PREFIX = "org.eclipse.hyundai.Diagram.";

	/* The image identifier for an EReference. */
	public static final String IMG_EREFERENCE = PREFIX + "ereference";

	/* The image identifier for CAN */
	public static final String IMG_CAN = PREFIX + "can";
	public static final String IMG_CAN0 = PREFIX + "can0";
	public static final String IMG_CAN1 = PREFIX + "can1";
	public static final String IMG_CAN2 = PREFIX + "can2";
	public static final String IMG_CAN3 = PREFIX + "can3";
	public static final String IMG_CAR = PREFIX + "car";
	public static final String IMG_CAN0G = PREFIX + "can0_g";
	public static final String IMG_CAN1G = PREFIX + "can1_g";
	public static final String IMG_CAN2G = PREFIX + "can2_g";
	public static final String IMG_CAN3G = PREFIX + "can3_g";
	public static final String IMG_CARG = PREFIX + "car_g";

	/* The image identifier for ECU */
	public static final String IMG_ECU = PREFIX + "ecu";
	public static final String IMG_MC1_ECU = PREFIX + "mc1ecu";
	public static final String IMG_MC2_ECU = PREFIX + "mc2ecu";
	public static final String IMG_MC3_ECU = PREFIX + "mc3ecu";
	public static final String IMG_MC4_ECU = PREFIX + "mc4ecu";
	public static final String IMG_REAL_ECU = PREFIX + "realecu";
	public static final String IMG_REAL_MC1_ECU = PREFIX + "realmc1ecu";
	public static final String IMG_REAL_MC2_ECU = PREFIX + "realmc2ecu";
	public static final String IMG_REAL_MC3_ECU = PREFIX + "realmc3ecu";
	public static final String IMG_REAL_MC4_ECU = PREFIX + "realmc4ecu";
	public static final String IMG_MC1_ECUG = PREFIX + "mc1ecu_g";
	public static final String IMG_MC2_ECUG = PREFIX + "mc2ecu_g";
	public static final String IMG_MC3_ECUG = PREFIX + "mc3ecu_g";
	public static final String IMG_MC4_ECUG = PREFIX + "mc4ecu_g";
	public static final String IMG_REAL_MC1_ECUG = PREFIX + "realmc1ecu_g";
	public static final String IMG_REAL_MC2_ECUG = PREFIX + "realmc2ecu_g";
	public static final String IMG_REAL_MC3_ECUG = PREFIX + "realmc3ecu_g";
	public static final String IMG_REAL_MC4_ECUG = PREFIX + "realmc4ecu_g";

	/* The image identifier for SWC */
	public static final String IMG_SWC = PREFIX + "swc";
	public static final String IMG_REAL_SWC = PREFIX + "realswc";
	public static final String IMG_DUAL_SWC00 = PREFIX + "dualswc00";
	public static final String IMG_DUAL_SWC01 = PREFIX + "dualswc01";
	public static final String IMG_DUAL_SWC10 = PREFIX + "dualswc10";
	public static final String IMG_DUAL_SWC11 = PREFIX + "dualswc11";
	public static final String IMG_SWCG = PREFIX + "swc_g";
	public static final String IMG_REAL_SWCG = PREFIX + "realswc_g";

	public static final String IMG_CAN_ICON = PREFIX + "canicon";
	public static final String IMG_CAR_ICON = PREFIX + "caricon";
	public static final String IMG_ECU_ICON = PREFIX + "ecuicon";
	public static final String IMG_SWC_ICON = PREFIX + "swcicon";
	public static final String IMG_CORE = PREFIX + "core";
	public static final String IMG_REAL_CORE = PREFIX + "realcore";

	/* The image identifier for ECU Icon */
	public static final String IMG_VIRTUAL_ICON = PREFIX + "virtualicon";
	public static final String IMG_ECU_INC_ICON = PREFIX + "ecuincicon";
	public static final String IMG_ECU_DEC_ICON = PREFIX + "ecudecicon";

	/* The size of images */
	public static final int CAR_WIDTH = 269;
	public static final int CAR_HEIGHT = 122;
	public static final int CAN_WIDTH = 400;
	public static final int CAN_HEIGHT = 35;
	public static final int NCAN_WIDTH = 1000;
	public static final int NCAN_HEIGHT = 17;
	public static final int ECU_WIDTH = 100;
	public static final int ECU_HEIGHT = 66;
	public static final int MECU_WIDTH = 100;
	public static final int MECU_HEIGHT = 86;
	public static final int SWC_WIDTH = 81;
	public static final int SWC_HEIGHT = 64;
	public static final int DUAL_WIDTH = 170;
	public static final int DUAL_HEIGHT = 64;
	/*
	 * public static final int SWC_WIDTH = 113; public static final int SWC_HEIGHT =
	 * 88;
	 */

	@Override
	protected void addAvailableImages() {
		// register the path for each image identifier
		addImageFilePath(IMG_EREFERENCE, "icons/ereference.gif");
		addImageFilePath(IMG_CAR, "icons/car.png");
		addImageFilePath(IMG_CAN, "icons/CAN.png");
		addImageFilePath(IMG_CAN0, "icons/CAN0.png");
		addImageFilePath(IMG_CAN1, "icons/CAN1.png");
		addImageFilePath(IMG_CAN2, "icons/CAN2.png");
		addImageFilePath(IMG_CAN3, "icons/CAN3.png");
		addImageFilePath(IMG_ECU, "icons/simul_ECU.png");
		addImageFilePath(IMG_SWC, "icons/simul_task.png");
		addImageFilePath(IMG_REAL_ECU, "icons/real_ECU.png");
		addImageFilePath(IMG_MC1_ECU, "icons/MC1_ECU.png");
		addImageFilePath(IMG_MC2_ECU, "icons/MC2_ECU.png");
		addImageFilePath(IMG_MC3_ECU, "icons/MC3_ECU.png");
		addImageFilePath(IMG_MC4_ECU, "icons/MC4_ECU.png");
		addImageFilePath(IMG_REAL_SWC, "icons/real_task.png");
		addImageFilePath(IMG_REAL_MC1_ECU, "icons/real_MC1_ECU.png");
		addImageFilePath(IMG_REAL_MC2_ECU, "icons/real_MC2_ECU.png");
		addImageFilePath(IMG_REAL_MC3_ECU, "icons/real_MC3_ECU.png");
		addImageFilePath(IMG_REAL_MC4_ECU, "icons/real_MC4_ECU.png");
		addImageFilePath(IMG_CAR_ICON, "icons/caricon.png");
		addImageFilePath(IMG_CAN_ICON, "icons/canicon.png");
		addImageFilePath(IMG_ECU_ICON, "icons/ecuicon.png");
		addImageFilePath(IMG_SWC_ICON, "icons/swcicon.png");
		addImageFilePath(IMG_VIRTUAL_ICON, "icons/virtualicon.png");
		addImageFilePath(IMG_ECU_INC_ICON, "icons/coreIncrease.png");
		addImageFilePath(IMG_ECU_DEC_ICON, "icons/coreDecrease.png");
		addImageFilePath(IMG_CORE, "icons/CORE.png");
		addImageFilePath(IMG_REAL_CORE, "icons/REAL_CORE.png");
		addImageFilePath(IMG_CARG, "icons/car_g.png");
		addImageFilePath(IMG_CAN0G, "icons/CAN0_g.png");
		addImageFilePath(IMG_CAN1G, "icons/CAN1_g.png");
		addImageFilePath(IMG_CAN2G, "icons/CAN2_g.png");
		addImageFilePath(IMG_CAN3G, "icons/CAN3_g.png");
		addImageFilePath(IMG_SWCG, "icons/simul_task_g.png");
		addImageFilePath(IMG_MC1_ECUG, "icons/MC1_ECU_g.png");
		addImageFilePath(IMG_MC2_ECUG, "icons/MC2_ECU_g.png");
		addImageFilePath(IMG_MC3_ECUG, "icons/MC3_ECU_g.png");
		addImageFilePath(IMG_MC4_ECUG, "icons/MC4_ECU_g.png");
		addImageFilePath(IMG_REAL_SWCG, "icons/real_task_g.png");
		addImageFilePath(IMG_REAL_MC1_ECUG, "icons/real_MC1_ECU_g.png");
		addImageFilePath(IMG_REAL_MC2_ECUG, "icons/real_MC2_ECU_g.png");
		addImageFilePath(IMG_REAL_MC3_ECUG, "icons/real_MC3_ECU_g.png");
		addImageFilePath(IMG_REAL_MC4_ECUG, "icons/real_MC4_ECU_g.png");
	}
}
