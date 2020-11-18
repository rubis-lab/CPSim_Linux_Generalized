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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public final class ECUPropertiesDialog extends PropertiesDialog {

	protected static final String DEFAULT_TITLE = "ECU Properties";
	protected static final String DEFAULT_MESSAGE = "Set ECU Properties";

	private Text sycText;
	private Combo schPolicyCbx;
	private Combo targetCbx;

	private boolean sycValid;

	ECU ecu;

	public ECUPropertiesDialog(Shell parentShell, EObject eobj) {
		super(parentShell);

		if (eobj instanceof ECU) {
			ecu = (ECU) eobj;
		}
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(DEFAULT_TITLE);
	}

	@Override
	public void create() {
		super.create();
		idValid = validator(idText.getText(), Message.PATTERN_ID) ? true : false;
		sycValid = validator(sycText.getText(), Message.PATTERN_DIGIT) ? true : false;

		idText.addModifyListener(new ModifyListener() {
			// duplicated ID check needed ??
			public void modifyText(ModifyEvent e) {
				idValid = validator(idText.getText(), Message.PATTERN_ID) ? true : false;
				verifyOKButton();
			}
		});
		sycText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				sycValid = validator(sycText.getText(), Message.PATTERN_DIGIT) ? true : false;
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

		Composite idContainer = new Composite(area, SWT.NONE);
		idContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout idLayout = new GridLayout(2, false);
		idContainer.setLayout(idLayout);

		createECU(idContainer);

		String id = ecu.getId();
		idText.setText(id == null ? Message.DEFAULT_ECU_NAME : id);
		sycText.setText("" + ecu.getSystemClock());
		schPolicyCbx.setText(ecu.getSchedulingPolicy());
		targetCbx.setText(ecu.getTargetArchitecture());

		Label lineBottom = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineBottom.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));

		return area;
	}

	private final void createECU(Composite parent) {
		// ID
		Label valueLabel = new Label(parent, SWT.NONE);
		valueLabel.setText("ID:");

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;

		idText = new Text(parent, SWT.BORDER);
		idText.setEditable(true);
		idText.setLayoutData(data);

		// System clock
		Label sycLabel = new Label(parent, SWT.NONE);
		sycLabel.setText("System Clock (MHz):");

		sycText = new Text(parent, SWT.BORDER);
		sycText.setEditable(true);
		sycText.setLayoutData(data);

		// Scheduling policy
		Label schLabel = new Label(parent, SWT.NONE);
		schLabel.setText("Scheduling Policy:");

		schPolicyCbx = new Combo(parent, SWT.READ_ONLY);
		for (String s : Message.SCHEDULING_LIST)
			schPolicyCbx.add(s);
		schPolicyCbx.setLayoutData(data);

		// Target Architecture
		Label targetLabel = new Label(parent, SWT.NONE);
		targetLabel.setText("Target Architecture:");

		targetCbx = new Combo(parent, SWT.READ_ONLY);
		for (String s : Message.TARGET_ARCHITECTURE_LIST)
			targetCbx.add(s);
		targetCbx.setLayoutData(data);
	}

	@Override
	protected void okPressed() {
		ecu.setId(idText.getText());
		ecu.setSystemClock(Integer.parseInt(sycText.getText()));
		ecu.setSchedulingPolicy(schPolicyCbx.getText());
		ecu.setTargetArchitecture(targetCbx.getText());

		/* Set Text */
		IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		DiagramEditor diagrameditor = (DiagramEditor) activeeditor;
		for (PictogramElement pic : Graphiti.getPeService()
				.getAllContainedPictogramElements(diagrameditor.getDiagramTypeProvider().getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			if (ecu.equals(eobj)) {
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
			if (!ecu.equals(eobj)) {
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
		if (idValid && sycValid) {
			setMessage(DEFAULT_MESSAGE);
			okButton.setEnabled(true);
		} else if (!idValid) {
			setMessage("ID is not valid. The ID '" + idText.getText() + "' is not a valid identifier.");
			okButton.setEnabled(false);
		} else if (!sycValid) {
			setMessage("System clock must be a number only.");
			okButton.setEnabled(false);
		}
	}
}