package org.eclipse.cpsim.menu.design;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class DevelopmentSetting extends AbstractHandler {
	private final String env_string = MESSAGES.CPSIM_PATH;
	private final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	private final String env_cmd = MESSAGES.CPSIM_ENV_CMD;
	private final String simul_drive = MESSAGES.ENV_SIMUL_DRIVE;
	private final String simul_path = MESSAGES.ENV_SIMUL_PATH;
	private final String visual_drive = MESSAGES.ENV_VISUAL_DRIVE;
	private final String visual_path = MESSAGES.ENV_VISUAL_PATH;
	private final String matlab_path = MESSAGES.ENV_MATLAB_PATH;

	private String cpsim_path = null;
	private String targetdir;
	private String targetdir1;

	private final String[] default_text = MESSAGES.ENV_DEFAULT_STRING;

	public String getCPSIMenv() {
		if (cpsim_path != null)
			return cpsim_path;

		try {
			String line = System.getenv(env_string);

			// check there are several paths
			if (line.indexOf(";") < 0)
				return line.trim();

			line = line.substring(0, line.indexOf(";"));
			cpsim_path = line.trim();
			return cpsim_path;

		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

	}

	public boolean ReadEnvFile() {
		if (getCPSIMenv() == null) {
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "ERROR",
					"Check the following environment variable: " + env_string);
			return false;
		}

		String path = getCPSIMenv() + "\\" + folder_string + "\\" + env_cmd;
		File f = new File(path);

		if (f.isFile()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(path));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.startsWith(visual_path)) {
						if (line.split("=").length == 2)
							targetdir = line.split("=")[1];
					} else if (line.startsWith(matlab_path)) {
						if (line.split("=").length == 2)
							targetdir1 = line.split("=")[1];
					}
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
				return true;
			}
		}

		return true;
	}

	public boolean WriteEnvFile() {
		String path = getCPSIMenv() + "\\" + folder_string + "\\" + env_cmd;
		File f = new File(path);

		if (!f.exists() || f.isFile()) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(path));

				bw.write(simul_drive + getCPSIMenv().split("\\\\")[0]);
				bw.newLine();
				bw.write(simul_path + getCPSIMenv());
				bw.newLine();
				bw.write(visual_drive + targetdir.split("\\\\")[0]);
				bw.newLine();
				bw.write(visual_path + targetdir);
				bw.newLine();
				bw.write(matlab_path + targetdir1);
				bw.newLine();
				for (String s : default_text) {
					bw.write(s);
					bw.newLine();
				}

				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		targetdir = "";
		targetdir1 = "";

		/* There is no environment variable */
		if (!ReadEnvFile()) {
			return null;
		}

		if (targetdir == null)
			targetdir = "";
		if (targetdir1 == null)
			targetdir1 = "";

		int open = new TitleAreaDialog(new Shell()) {

			private Text txtProjectName;
			private Text txtProjectName1;
			protected Button okButton;

			@Override
			public void create() {
				super.create();
				okButton = getButton(IDialogConstants.OK_ID);
				if (targetdir.equals("") || targetdir1.equals(""))
					okButton.setEnabled(false);
			}

			@Override
			protected void configureShell(Shell shell) {
				super.configureShell(shell);
				shell.setText("Develop Environment Settings");
			}

			@Override
			protected Control createDialogArea(Composite parent) {
				setTitle("Develop Environment Settings");
				setMessage("Select Directory");
				setHelpAvailable(false);

				Composite area = (Composite) super.createDialogArea(parent);
				area.setLayout(null);
				Label lbtProjectName = new Label(area, SWT.NONE);
				lbtProjectName.setBounds(5, 10, 100, 20);
				lbtProjectName.setText("Visual Studio Path");

				txtProjectName = new Text(area, SWT.BORDER | SWT.READ_ONLY);
				txtProjectName.setBounds(105, 10, 270, 20);
				txtProjectName.setText(targetdir);

				Button DirectorySelector = new Button(area, SWT.CENTER);
				DirectorySelector.setBounds(375, 8, 60, 24);
				DirectorySelector.setText("Browse");
				DirectorySelector.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						DirectoryDialog dialog = new DirectoryDialog(new Shell(), SWT.OPEN);
						dialog.setText("Visual Studio Path");
						dialog.setMessage("Select a directory");
						if (txtProjectName.getText() != null)
							dialog.setFilterPath(txtProjectName.getText());
						targetdir = dialog.open();
						txtProjectName.setText(targetdir);

						if (targetdir == null) {
							return;
						}

						if (!targetdir.equals("") && !targetdir1.equals(""))
							okButton.setEnabled(true);
					}
				});

				Label lbtProjectName1 = new Label(area, SWT.NONE);
				lbtProjectName1.setBounds(5, 40, 80, 20);
				lbtProjectName1.setText("MATLAB Path");

				txtProjectName1 = new Text(area, SWT.BORDER | SWT.READ_ONLY);
				txtProjectName1.setBounds(105, 40, 270, 20);
				txtProjectName1.setText(targetdir1);

				Button DirectorySelector1 = new Button(area, SWT.CENTER);
				DirectorySelector1.setBounds(375, 38, 60, 24);
				DirectorySelector1.setText("Browse");
				DirectorySelector1.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						DirectoryDialog dialog = new DirectoryDialog(new Shell(), SWT.OPEN);
						dialog.setText("MATLAB Path");
						dialog.setMessage("Select a directory");
						if (txtProjectName1.getText() != null)
							dialog.setFilterPath(txtProjectName1.getText());
						targetdir1 = dialog.open();
						txtProjectName1.setText(targetdir1);

						if (targetdir1 == null) {
							return;
						}

						if (!targetdir.equals("") && !targetdir1.equals(""))
							okButton.setEnabled(true);
					}
				});

				return area;
			}

			@Override
			protected Point getInitialSize() {
				return new Point(450, 230);
			}

			@Override
			protected void okPressed() {
				try {
					if (txtProjectName.getText() == null)
						throw new Exception();
				} catch (Exception e) {
					e.printStackTrace();
				}

				super.okPressed();
			}

		}.open();

		if (open == 0) {
			if (!WriteEnvFile())
				System.out.println("Write Fail");
		}

		return null;
	}
}
