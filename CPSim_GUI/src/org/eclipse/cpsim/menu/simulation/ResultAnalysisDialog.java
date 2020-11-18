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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class ResultAnalysisDialog extends TitleAreaDialog {

	protected static final String DEFAULT_TITLE = "Result Analysis";
	protected static final String DEFAULT_MESSAGE = "Set Result Analysis Options";

	private Button okButton;
	private List RoIList;
	private Text ThresholdText;

	private boolean ThresholdValid;

	private final String env_string = MESSAGES.CPSIM_PATH;
	private final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	private final String config_string = MESSAGES.CPSIM_CONFIG;
	private final String[] config_line = { MESSAGES.ROI, MESSAGES.THRESHOLD };

	public ResultAnalysisDialog(Shell parentShell) {
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
		okButton.setText("Analyze");
		okButton.setFocus();
		ThresholdValid = validator(ThresholdText.getText(), Message.PATTERN_DIGIT) ? true : false;

		ThresholdText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				ThresholdValid = validator(ThresholdText.getText(), Message.PATTERN_DIGIT) ? true : false;
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
		ThresholdText.setMessage("0");
		SetTexts();

		Label lineBottom = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineBottom.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));

		return area;
	}

	private final void createDialog(Composite parent) {
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;

		// Threshold
		Label ThresholdLabel = new Label(parent, SWT.NONE);
		ThresholdLabel.setText("Global Threshold:");

		ThresholdText = new Text(parent, SWT.BORDER);
		ThresholdText.setEditable(true);
		ThresholdText.setLayoutData(data);

		// RoI DB
		Label RoIdbLabel = new Label(parent, SWT.NONE);
		RoIdbLabel.setText("RoI list:");

		Composite RoIdbCon = new Composite(parent, SWT.NONE);
		RoIdbCon.setLayoutData(new GridData(GridData.FILL_VERTICAL | GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout(2, false);
		RoIdbCon.setLayout(layout);
		createRoIDB(RoIdbCon);
	}

	private final void createRoIDB(Composite parent) {

		RoIList = new List(parent, SWT.BORDER | SWT.V_SCROLL);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalSpan = 4;
		int listHeight = RoIList.getItemHeight() * 10;
		Rectangle trim = RoIList.computeTrim(0, 0, 0, listHeight);
		gridData.heightHint = trim.height;
		RoIList.setLayoutData(gridData);

		Button addButton = new Button(parent, SWT.PUSH);
		addButton.setText("Add...");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		addButton.setLayoutData(gridData);
		addButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				TitleAreaDialog dialog = new RoIDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						RoIList);
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
			}
		});

		Button removeButton = new Button(parent, SWT.PUSH);
		removeButton.setText("Remove");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING);
		removeButton.setLayoutData(gridData);
		removeButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (RoIList.getSelectionIndex() == -1)
					return;

				RoIList.remove(RoIList.getSelectionIndex());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
			}
		});
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
					String[] dir = val.split(" ");
					for (String s : dir) {
						if (!s.isEmpty())
							RoIList.add(s);
					}
				} else if (line.startsWith(config_line[1])) {
					ThresholdText.setText(val);
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

		boolean isRoI = RoIList.getItemCount() != 0;
		boolean isThreshold = !ThresholdText.getText().isEmpty();

		String RoI = "";
		for (int i = 0; i < RoIList.getItemCount(); i++) {
			RoI += RoIList.getItem(i) + " ";
		}
		int Threshold = ThresholdText.getText().isEmpty() ? 0 : Integer.parseInt(ThresholdText.getText());

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			String line;
			while ((line = br.readLine()) != null) {

				if (line.startsWith(config_line[0]) || line.startsWith(config_line[1])) {
				} else {
					temp += line + "\r\n";
				}
			}
			br.close();

			if (isRoI)
				temp += config_line[0] + RoI + "\r\n";
			if (isThreshold)
				temp += config_line[1] + Threshold + "\r\n";

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
		if (ThresholdValid) {
			setMessage(DEFAULT_MESSAGE);
			okButton.setEnabled(true);
		} else if (!ThresholdValid) {
			setMessage(MESSAGES.MESSAGE_THRESHOLD_VALID);
			okButton.setEnabled(false);
		}

	}

}
