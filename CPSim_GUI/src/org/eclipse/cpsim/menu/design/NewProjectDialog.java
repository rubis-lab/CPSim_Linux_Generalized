package org.eclipse.cpsim.menu.design;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

// TODO NEED TO REFACTORING
public class NewProjectDialog extends TitleAreaDialog {

	private Text txtProjectName;

	public NewProjectDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("New Project");
	}

	@Override
	public void create() {
		super.create();
		setTitle("Graphiti Project");
		setMessage("Create a new Graphiti Project resource.");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setHelpAvailable(false);

		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createProjectName(container);

		Label lineBottom = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineBottom.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));

		return area;
	}

	private void createProjectName(Composite container) {
		Label lbtProjectName = new Label(container, SWT.NONE);
		lbtProjectName.setText("Project name:");

		GridData dataProjectName = new GridData();
		dataProjectName.grabExcessHorizontalSpace = true;
		dataProjectName.horizontalAlignment = GridData.FILL;

		txtProjectName = new Text(container, SWT.BORDER);
		txtProjectName.setLayoutData(dataProjectName);
	}

	@Override
	protected boolean isResizable() {
		return false;
	}

	@Override
	protected void okPressed() {
		MakeUpProject.createProject(txtProjectName.getText());
		super.okPressed();
	}
}