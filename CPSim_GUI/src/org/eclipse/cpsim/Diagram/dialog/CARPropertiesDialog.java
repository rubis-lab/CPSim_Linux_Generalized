package org.eclipse.cpsim.Diagram.dialog;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.Message;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public final class CARPropertiesDialog extends PropertiesDialog {

	CAR car;

	public CARPropertiesDialog(Shell parentShell, EObject eobj) {
		super(parentShell);

		if (eobj instanceof CAR) {
			car = (CAR) eobj;
		}
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("CAR Properties");
	}

	@Override
	public void create() {
		super.create();
		idText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (validator(idText.getText(), Message.PATTERN_ID)) {
					setMessage("Set CAR Properties");
					okButton.setEnabled(true);
					;
				} else {
					setMessage("ID is not valid. The ID '" + idText.getText() + "' is not a valid identifier.");
					okButton.setEnabled(false);
				}
			}
		});
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("CAR properties");
		setMessage("Set CAR Properties");
		// setTitleImage(new Image(null));
		setHelpAvailable(false);

		Composite area = (Composite) super.createDialogArea(parent);

		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createCAR(container);

		String name = car.getId();
		idText.setText(name == null ? Message.DEFAULT_CAR_NAME : name);

		Label lineBottom = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineBottom.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));

		return area;
	}

	private final void createCAR(Composite parent) {
		// ID
		Label valueLabel = new Label(parent, SWT.NONE);
		valueLabel.setText("ID:");

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;

		idText = new Text(parent, SWT.BORDER);
		idText.setEditable(true);
		idText.setLayoutData(data);

	}

	@Override
	protected void okPressed() {
		car.setId(idText.getText());

		/* Set Text */
		IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		DiagramEditor diagrameditor = (DiagramEditor) activeeditor;
		for (PictogramElement pic : Graphiti.getPeService()
				.getAllContainedPictogramElements(diagrameditor.getDiagramTypeProvider().getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			if (car.equals(eobj)) {
				ContainerShape containerShape = (ContainerShape) pic;
				EList<Shape> shapes = containerShape.getChildren();
				for (Shape s : shapes) {
					// System.out.print(s.toString() + " : ");

					if (s.getGraphicsAlgorithm() instanceof org.eclipse.graphiti.mm.algorithms.Text) {
						org.eclipse.graphiti.mm.algorithms.Text temp = (org.eclipse.graphiti.mm.algorithms.Text) s
								.getGraphicsAlgorithm();
						temp.setValue(idText.getText());
					}
					;
				}
			}
		}

		super.okPressed();
	}

	@Override
	protected boolean validator(String str, String regex) {
		IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		DiagramEditor diagrameditor = (DiagramEditor) activeeditor;
		for (PictogramElement pic : Graphiti.getPeService()
				.getAllContainedPictogramElements(diagrameditor.getDiagramTypeProvider().getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			if (!car.equals(eobj)) {
				if (eobj instanceof ECU && str.equals(((ECU) eobj).getId()))
					return false;
				else if (eobj instanceof CAR && str.equals(((CAR) eobj).getId()))
					return false;
				else if (eobj instanceof CAN && str.equals(((CAN) eobj).getId()))
					return false;
				else if (eobj instanceof SWC && str.equals(((SWC) eobj).getId().split(";")[0]))
					return false;

			}
		}
		return super.validator(str, regex);
	}

	@Override
	protected void verifyOKButton() {
		// TODO Auto-generated method stub

	}
}