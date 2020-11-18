package org.eclipse.cpsim.Diagram.dialog;

import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public abstract class PropertiesDialog extends TitleAreaDialog {

	protected Text idText;
	protected Button okButton;

	protected boolean idValid;

	public PropertiesDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		okButton = getButton(IDialogConstants.OK_ID);
	}

	protected boolean validator(String str, String regex) {
		return Pattern.matches(regex, str);
	}

	protected abstract void verifyOKButton();

	// should we need Diagram??
	// because the component Id and other member-data are already setup
	// so we need not to initialize in this dialog!

}
