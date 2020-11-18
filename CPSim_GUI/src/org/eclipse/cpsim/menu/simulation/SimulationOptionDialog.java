package org.eclipse.cpsim.menu.simulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Pattern;

import org.eclipse.cpsim.Diagram.Message;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SimulationOptionDialog extends TitleAreaDialog {

	protected static final String DEFAULT_TITLE = "Simulation Options";
	protected static final String DEFAULT_MESSAGE = "Set Simulation Options";

	private Text StartText;
	private Text EndText;
	private Text IntervalText;
	private Text RuntimeText;

	private Button mult;

	private boolean StartValid;
	private boolean EndValid;
	private boolean IntervalValid;
	private boolean RuntimeValid;
	private boolean StartErrorValid;
	private boolean IntervalErrorValid;
	private boolean IntervalZeroValid;
	private boolean RuntimeZeroValid;

	private boolean multirun_check;

	private final String env_string = MESSAGES.CPSIM_PATH;
	private final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	private final String config_string = MESSAGES.CPSIM_CONFIG;
	private final String[] config_line = { MESSAGES.START, MESSAGES.END, MESSAGES.INTERVAL, MESSAGES.RUNTIME };
	private final String multi_run = MESSAGES.MULTI_RUN;

	protected Button okButton;

	public SimulationOptionDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(DEFAULT_TITLE);
	}

	@Override
	public void create() {
		super.create();
		okButton = getButton(IDialogConstants.OK_ID);
		okButton.setFocus();

		StartValid = validator(StartText.getText(), Message.PATTERN_DIGIT) ? true : false;
		EndValid = validator(EndText.getText(), Message.PATTERN_DIGIT) ? true : false;
		IntervalValid = validator(IntervalText.getText(), Message.PATTERN_DIGIT) ? true : false;
		RuntimeValid = validator(RuntimeText.getText(), Message.PATTERN_DIGIT) ? true : false;
		StartErrorValid = true;
		IntervalErrorValid = true;
		IntervalZeroValid = true;
		RuntimeZeroValid = true;

		StartText.addModifyListener(new ModifyListener() {
			// duplicated ID check needed ??
			public void modifyText(ModifyEvent e) {
				StartValid = validator(StartText.getText(), Message.PATTERN_DIGIT) ? true : false;

				if (StartValid) {
					int start = (StartText.getText().isEmpty()) ? 0 : Integer.parseInt(StartText.getText());
					int end = (EndText.getText().isEmpty()) ? 5 : Integer.parseInt(EndText.getText());
					int interval = (IntervalText.getText().isEmpty()) ? 1 : Integer.parseInt(IntervalText.getText());
					StartErrorValid = true;
					IntervalErrorValid = true;
					if (start >= end)
						StartErrorValid = false;
					else if (interval > (end - start))
						IntervalErrorValid = false;
				}

				verifyOKButton();
			}
		});
		EndText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				EndValid = validator(EndText.getText(), Message.PATTERN_DIGIT) ? true : false;
				if (EndValid) {
					int start = (StartText.getText().isEmpty()) ? 0 : Integer.parseInt(StartText.getText());
					int end = (EndText.getText().isEmpty()) ? 5 : Integer.parseInt(EndText.getText());
					int interval = (IntervalText.getText().isEmpty()) ? 1 : Integer.parseInt(IntervalText.getText());
					StartErrorValid = true;
					IntervalErrorValid = true;
					if (start >= end)
						StartErrorValid = false;
					else if (interval > (end - start))
						IntervalErrorValid = false;
				}
				verifyOKButton();
			}
		});
		IntervalText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				IntervalValid = validator(IntervalText.getText(), Message.PATTERN_DIGIT) ? true : false;
				if (IntervalValid) {
					int start = (StartText.getText().isEmpty()) ? 0 : Integer.parseInt(StartText.getText());
					int end = (EndText.getText().isEmpty()) ? 5 : Integer.parseInt(EndText.getText());
					int interval = (IntervalText.getText().isEmpty()) ? 1 : Integer.parseInt(IntervalText.getText());
					StartErrorValid = true;
					IntervalErrorValid = true;
					IntervalZeroValid = true;
					if (start >= end)
						StartErrorValid = false;
					else if (interval > (end - start))
						IntervalErrorValid = false;
					else if (interval <= 0)
						IntervalZeroValid = false;
				}
				verifyOKButton();
			}
		});
		RuntimeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				RuntimeValid = validator(RuntimeText.getText(), Message.PATTERN_DIGIT) ? true : false;
				RuntimeZeroValid = true;
				if (RuntimeValid) {
					int runtime = (RuntimeText.getText().isEmpty()) ? 1000 : Integer.parseInt(RuntimeText.getText());
					if (runtime <= 0)
						RuntimeZeroValid = false;
				}
				verifyOKButton();
			}
		});
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle(DEFAULT_TITLE);
		setMessage(DEFAULT_MESSAGE);
		// setTitleImage(new Image(null));
		setHelpAvailable(false);

		Composite area = (Composite) super.createDialogArea(parent);

		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createDialog(container);
		StartText.setMessage("0");
		EndText.setMessage("5");
		IntervalText.setMessage("1");
		RuntimeText.setMessage("1000");
		SetTexts();

		Label lineBottom = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineBottom.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));

		return area;
	}

	private final void createDialog(Composite parent) {
		mult = new Button(parent, SWT.CHECK);
		mult.setText("Multi Run");
		new Label(parent, SWT.NONE);

		// Start
		Label StartLabel = new Label(parent, SWT.NONE);
		StartLabel.setText("Global Offset Start(ms):");

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;

		StartText = new Text(parent, SWT.BORDER);
		StartText.setEditable(true);
		StartText.setLayoutData(data);

		// End
		Label EndLabel = new Label(parent, SWT.NONE);
		EndLabel.setText("Global Offset End(ms):");

		EndText = new Text(parent, SWT.BORDER);
		EndText.setEditable(true);
		EndText.setLayoutData(data);

		// Interval
		Label IntervalLabel = new Label(parent, SWT.NONE);
		IntervalLabel.setText("Global Offset Interval(ms):");

		IntervalText = new Text(parent, SWT.BORDER);
		IntervalText.setEditable(true);
		IntervalText.setLayoutData(data);

		// Running time
		Label RuntimeLabel = new Label(parent, SWT.NONE);
		RuntimeLabel.setText("Running Time(ms):");

		RuntimeText = new Text(parent, SWT.BORDER);
		RuntimeText.setEditable(true);
		RuntimeText.setLayoutData(data);

	}

	public String getCPSIMenv() {
		try {
			String line = System.getenv(env_string);

			// check there are several paths
			if (line.indexOf(";") < 0)
				return line.trim();

			line = line.substring(0, line.indexOf(";"));
			return line.trim();

		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

	}

	private void SetTexts() {
		String path = getCPSIMenv() + "\\" + folder_string + "\\" + config_string;

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			String line;
			while ((line = br.readLine()) != null) {
				int idx = line.lastIndexOf('=') + 1;
				String val = "";
				if (idx != -1)
					val = line.substring(idx);

				if (line.startsWith(config_line[0])) {
					StartText.setText(val);
				} else if (line.startsWith(config_line[1])) {
					EndText.setText(val);
				} else if (line.startsWith(config_line[2])) {
					IntervalText.setText(val);
				} else if (line.startsWith(config_line[3])) {
					RuntimeText.setText(val);
				} else if (line.startsWith(multi_run)) {
					if (line.split("=").length == 2 && line.split("=")[1].equals("1"))
						mult.setSelection(true);
				}
			}

			br.close();
		} catch (Exception e) {
		}
	}

	@Override
	protected void okPressed() {

		String path = getCPSIMenv() + "\\" + folder_string + "\\" + config_string;
		String temp = "";

		boolean isstart = !StartText.getText().isEmpty();
		boolean isend = !EndText.getText().isEmpty();
		boolean isinterval = !IntervalText.getText().isEmpty();
		boolean isruntime = !RuntimeText.getText().isEmpty();

		int start = (StartText.getText().isEmpty()) ? 0 : Integer.parseInt(StartText.getText());
		int end = (EndText.getText().isEmpty()) ? 5 : Integer.parseInt(EndText.getText());
		int interval = (IntervalText.getText().isEmpty()) ? 1 : Integer.parseInt(IntervalText.getText());
		int runtime = (RuntimeText.getText().isEmpty()) ? 1000 : Integer.parseInt(RuntimeText.getText());

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			String line;
			while ((line = br.readLine()) != null) {

				if (line.startsWith(config_line[0])) {
					temp += config_line[0];
					if (isstart)
						temp += start;
					temp += "\r\n";
				} else if (line.startsWith(config_line[1])) {
					temp += config_line[1];
					if (isend)
						temp += end;
					temp += "\r\n";
				} else if (line.startsWith(config_line[2])) {
					temp += config_line[2];
					if (isinterval)
						temp += interval;
					temp += "\r\n";
				} else if (line.startsWith(config_line[3])) {
					temp += config_line[3];
					if (isruntime)
						temp += runtime;
					temp += "\r\n";
				} else if (line.startsWith(multi_run)) {
					temp += multi_run;
					temp += mult.getSelection() ? 1 : 0;
					temp += "\r\n";
				} else {
					temp += line + "\r\n";
				}
			}

			br.close();

			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write(temp);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.okPressed();
	}

	protected boolean validator(String str, String regex) {
		return str.isEmpty() || Pattern.matches(regex, str);
	}

	protected void verifyOKButton() {

		if (StartValid && EndValid && IntervalValid && RuntimeValid && StartErrorValid && IntervalErrorValid
				&& IntervalZeroValid && RuntimeZeroValid) {
			setMessage(DEFAULT_MESSAGE);
			okButton.setEnabled(true);
		} else if (!StartValid) {
			setMessage(MESSAGES.MESSAGE_START_VALID);
			okButton.setEnabled(false);
		} else if (!EndValid) {
			setMessage(MESSAGES.MESSAGE_END_VALID);
			okButton.setEnabled(false);
		} else if (!IntervalValid) {
			setMessage(MESSAGES.MESSAGE_INTERVAL_VALID);
			okButton.setEnabled(false);
		} else if (!RuntimeValid) {
			setMessage(MESSAGES.MESSAGE_RUNTIME_VALID);
			okButton.setEnabled(false);
		} else if (!StartErrorValid) {
			setMessage(MESSAGES.MESSAGE_START_ERROR_VALID);
			okButton.setEnabled(false);
		} else if (!IntervalZeroValid) {
			setMessage(MESSAGES.MESSAGE_INTERVAL_ZERO_VALID);
			okButton.setEnabled(false);
		} else if (!IntervalErrorValid) {
			setMessage(MESSAGES.MESSAGE_INTERVAL_ERROR_VALID);
			okButton.setEnabled(false);
		} else if (!RuntimeZeroValid) {
			setMessage(MESSAGES.MESSAGE_RUNTIME_ZERO_VALID);
			okButton.setEnabled(false);
		}
	}

}
