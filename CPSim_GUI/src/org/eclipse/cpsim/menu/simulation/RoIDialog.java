package org.eclipse.cpsim.menu.simulation;

import java.util.Arrays;
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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class RoIDialog extends TitleAreaDialog {

	protected static final String DEFAULT_TITLE = "Create RoI";
	protected static final String DEFAULT_MESSAGE = "Set RoI Options";

	private Text StartText;
	private Text EndText;
	private Text ThresholdText;

	private boolean StartValid;
	private boolean EndValid;
	private boolean ThresholdValid;
	private boolean StartErrorValid;

	private List RoIList;

	private Button okButton;
	private Button cancelButton;

	public RoIDialog(Shell parentShell, List roilist) {
		super(parentShell);
		RoIList = roilist;
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
		okButton.setEnabled(false);
		cancelButton = getButton(IDialogConstants.CANCEL_ID);

		cancelButton.setFocus();

		StartValid = validator(StartText.getText(), Message.PATTERN_DIGIT) ? true : false;
		EndValid = validator(EndText.getText(), Message.PATTERN_DIGIT) ? true : false;
		ThresholdValid = validators(ThresholdText.getText(), Message.PATTERN_DIGIT) ? true : false;
		StartErrorValid = true;

		StartText.addModifyListener(new ModifyListener() {
			// duplicated ID check needed ??
			public void modifyText(ModifyEvent e) {
				StartValid = validator(StartText.getText(), Message.PATTERN_DIGIT) ? true : false;
				EndValid = validator(EndText.getText(), Message.PATTERN_DIGIT) ? true : false;
				if (StartValid && EndValid) {
					int start = Integer.parseInt(StartText.getText());
					int end = Integer.parseInt(EndText.getText());
					StartErrorValid = true;
					if (start >= end)
						StartErrorValid = false;
				}

				verifyOKButton();
			}
		});
		EndText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				StartValid = validator(StartText.getText(), Message.PATTERN_DIGIT) ? true : false;
				EndValid = validator(EndText.getText(), Message.PATTERN_DIGIT) ? true : false;
				if (StartValid && EndValid) {
					int start = Integer.parseInt(StartText.getText());
					int end = Integer.parseInt(EndText.getText());
					StartErrorValid = true;
					if (start >= end)
						StartErrorValid = false;
				}
				verifyOKButton();
			}
		});
		ThresholdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				ThresholdValid = validators(ThresholdText.getText(), Message.PATTERN_DIGIT) ? true : false;
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
		StartText.setMessage("Digit");
		EndText.setMessage("Digit");
		ThresholdText.setMessage("0");

		Label lineBottom = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineBottom.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));

		return area;
	}

	private final void createDialog(Composite parent) {
		// Start
		Label StartLabel = new Label(parent, SWT.NONE);
		StartLabel.setText("Interval Start(ms):");

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;

		StartText = new Text(parent, SWT.BORDER);
		StartText.setEditable(true);
		StartText.setLayoutData(data);

		// End
		Label EndLabel = new Label(parent, SWT.NONE);
		EndLabel.setText("Interval End(ms):");

		EndText = new Text(parent, SWT.BORDER);
		EndText.setEditable(true);
		EndText.setLayoutData(data);

		// Interval
		Label ThresholdLabel = new Label(parent, SWT.NONE);
		ThresholdLabel.setText("Local Threshold:");

		ThresholdText = new Text(parent, SWT.BORDER);
		ThresholdText.setEditable(true);
		ThresholdText.setLayoutData(data);
	}

	@Override
	protected void okPressed() {
		// TODO
		String newRoI = "[" + StartText.getText() + ":" + EndText.getText();
		if (!ThresholdText.getText().isEmpty())
			newRoI += ":" + ThresholdText.getText();
		else
			newRoI += ":0";
		newRoI += "]";
		String[] RoIArray = RoIList.getItems();
		for (int i = 0; i < RoIArray.length; i++)
			if (newRoI.equals(RoIArray[i])) {
				super.okPressed();
				return;
			}

		RoIList.add(newRoI);
		RoIArray = RoIList.getItems();
		Arrays.sort(RoIArray, new RoIComparator());
		RoIList.setItems(RoIArray);
		super.okPressed();
	}

	protected boolean validator(String str, String regex) {
		return Pattern.matches(regex, str);
	}

	protected boolean validators(String str, String regex) {
		return str.isEmpty() || validator(str, regex);
	}

	protected void verifyOKButton() {

		if (StartValid && EndValid && ThresholdValid && StartErrorValid) {
			setMessage(DEFAULT_MESSAGE);
			okButton.setEnabled(true);
		} else if (!StartValid) {
			setMessage(MESSAGES.MESSAGE_START_VALID);
			okButton.setEnabled(false);
		} else if (!EndValid) {
			setMessage(MESSAGES.MESSAGE_END_VALID);
			okButton.setEnabled(false);
		} else if (!ThresholdValid) {
			setMessage(MESSAGES.MESSAGE_THRESHOLD_VALID);
			okButton.setEnabled(false);
		} else if (!StartErrorValid) {
			setMessage(MESSAGES.MESSAGE_START_ERROR_VALID);
			okButton.setEnabled(false);
		}
	}

}
