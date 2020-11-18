package org.eclipse.cpsim.Diagram.util;

public class MESSAGES {
	public static final String FIRST_LINE = "[engine]";
	public static final String CPSIM_PATH = "CPSIM_PATH";
	public static final String DOXYGEN_PATH = "";
	public static final String CPSIM_FOLDER_TOOL = "";
	public static final String CPSIM_FOLDER_PARSER = "Parser";
	public static final String CPSIM_CONFIG = "Config";
	public static final String CPSIM_RUN_SHELL = "start.sh";
	public static final String CPSIM_STOP_SHELL = "stop.sh";
	public static final String CPSIM_SM_CMD = "smAnalysis.cmd";
	public static final String CPSIM_SM_LOG = "smLog.txt";
	public static final String CPSIM_ECU_CMD = "ecuCodeGen.cmd";
	public static final String CPSIM_PLOTTER_CMD = "plotter.cmd";
	public static final String CPSIM_RAW_DESIGN = "design.xml";
	public static final String CPSIM_ENV_CMD = "env.cmd";
//	public static final String CPSIM_MODEL_PATH = "\\tools\\TC275GenerationTool\\RESULT\\SourceCode\\APP\\inc\\model.hh";
	public static final String CPSIM_MODEL_PATH = "";
			
	public static final String CPSIM_ICON_PNG = "/icons/cpsim.png";

	public static final String START = "global_offset_start=";
	public static final String END = "global_offset_end=";
	public static final String INTERVAL = "global_offset_interval=";
	public static final String RUNTIME = "running_time=";
	public static final String MULTI_RUN = "multi_run=";

	public static final String ROI = "roi=";
	public static final String THRESHOLD = "threshold=";

	public static final String WAIT_SIGNAL = "wait_signal=";
	public static final String DOXYGEN_SYMBOL_PATH = "doxygen_symbol_path=";

	public static final String PROCESS_NAME = "real-time_simulator.exe";

	public static final String MESSAGE_START_VALID = "Start must be a number only.";
	public static final String MESSAGE_END_VALID = "End must be a number only.";
	public static final String MESSAGE_INTERVAL_VALID = "Interval must be a number only.";
	public static final String MESSAGE_RUNTIME_VALID = "Runtime must be a number only.";
	public static final String MESSAGE_START_ERROR_VALID = "End must be bigger than Start.";
	public static final String MESSAGE_INTERVAL_ZERO_VALID = "Interval must be bigger than 0";
	public static final String MESSAGE_INTERVAL_ERROR_VALID = "Interval must be smaller than (End - Start)";
	public static final String MESSAGE_RUNTIME_ZERO_VALID = "Runtime must be bigger than 0";

	public static final String MESSAGE_THRESHOLD_VALID = "Threshold must be a number only.";

	public static final String ENV_SIMUL_DRIVE = "set simulDrive=";
	public static final String ENV_SIMUL_PATH = "set simulPath=";
	public static final String ENV_VISUAL_DRIVE = "set visualDrive=";
	public static final String ENV_VISUAL_PATH = "set visualPath=";
	public static final String ENV_MATLAB_PATH = "set matlabPath=";
	public static final String[] ENV_DEFAULT_STRING = { "set algorithmPath=%simulPath%\\Algorithm",
			"set parserPath=%simulPath%\\Parser", "set simulatorPath=%simulPath%\\Simulator",
			"set simulatorName=real-time_simulator.exe",
			"set INCLUDE=%matlabPath%\\simulink\\include;%matlabPath%\\extern\\include;%simulPath%\\auto_generated_files;%matlabPath%\\rtw\\c\\src;%matlabPath%\\rtw\\c\\src\\ext_mode\\common;" };
}
