package org.eclipse.cpsim.Diagram.dialog;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.Message;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class CANPropertiesDialog extends PropertiesDialog {

	protected static final String DEFAULT_TITLE = "ETH Properties";
	protected static final String DEFAULT_MESSAGE = "Set ETH Properties";

	private Text bandwidthText;
	private Combo iscanfdCbx;
	private List candbList;

	private boolean bandwidthValid;

	CAN can;

	public CANPropertiesDialog(Shell parentShell, EObject eobj) {
		super(parentShell);

		if (eobj instanceof CAN) {
			can = (CAN) eobj;
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
//		idValid = validator(idText.getText(), Message.PATTERN_ID) ? true : false;
//		bandwidthValid = validator(bandwidthText.getText(), Message.PATTERN_DIGIT) ? true : false;

		idText.addModifyListener(new ModifyListener() {
			// duplicated ID check needed ??
			public void modifyText(ModifyEvent e) {
				idValid = validator(idText.getText(), Message.PATTERN_ID) ? true : false;
				verifyOKButton();
			}
		});
//		bandwidthText.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
//				bandwidthValid = validator(bandwidthText.getText(), Message.PATTERN_DIGIT) ? true : false;
//				verifyOKButton();
//			}
//		});
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

		createCAN(container);

		String id = can.getId();
		idText.setText(id == null ? Message.DEFAULT_CAN_NAME : id);
//		bandwidthText.setText("" + can.getBandwidth());
//		iscanfdCbx.setText(can.isIsCANFD() ? Message.IS_CANFD_LIST[1] : Message.IS_CANFD_LIST[0]);
//		String[] dir = can.getCanDB().split(";");
//		for (String s : dir) {
//			if (!s.isEmpty())
//				candbList.add(s);
//		}

		Label lineBottom = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineBottom.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));

		return area;
	}

	private final void createCAN(Composite parent) {
		// ID
		Label idLabel = new Label(parent, SWT.NONE);
		idLabel.setText("IP:");

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;

		idText = new Text(parent, SWT.BORDER);
		idText.setEditable(true);
		idText.setLayoutData(data);


		Composite candbCon = new Composite(parent, SWT.NONE);
		candbCon.setLayoutData(new GridData(GridData.FILL_VERTICAL | GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout(2, false);
		candbCon.setLayout(layout);
//		createCANDB(candbCon);
	}

	private final void createCANDB(Composite parent) {

		candbList = new List(parent, SWT.BORDER);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalSpan = 4;
		int listHeight = candbList.getItemHeight() * 4;
		Rectangle trim = candbList.computeTrim(0, 0, 0, listHeight);
		gridData.heightHint = trim.height;
		candbList.setLayoutData(gridData);

		Button addButton = new Button(parent, SWT.PUSH);
		addButton.setText("Add...");
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		addButton.setLayoutData(gridData);
		addButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (candbList.getItemCount() >= 4)
					return;

				FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SHEET);
				dialog.setFilterExtensions(new String[] { "*.dbc" });
				String dir = dialog.open(); // return the selected file or null if user cancels
				if (dir != null) {
					String[] candbArray = candbList.getItems();
					for (int i = 0; i < candbArray.length; i++) {
						if (dir.equals(candbArray[i]))
							return;
					}
					candbList.add(dir);
					candbArray = candbList.getItems();
					java.util.Arrays.sort(candbArray);
					candbList.setItems(candbArray);

				}
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
				if (candbList.getSelectionIndex() == -1)
					return;

				candbList.remove(candbList.getSelectionIndex());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// do nothing
			}
		});
	}

	@Override
	protected void okPressed() {
		can.setId(idText.getText());
//		can.setBandwidth(Integer.parseInt(bandwidthText.getText()));
//		can.setIsCANFD(iscanfdCbx.getText().equalsIgnoreCase("CAN-FD") ? true : false);

//		String[] dir = candbList.getItems();
//		String candb = "";
//		for (String s : dir)
//			candb += ";" + s;
//		can.setCanDB(candb);

		/* Set Text */
		IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		DiagramEditor diagrameditor = (DiagramEditor) activeeditor;
		for (PictogramElement pic : Graphiti.getPeService()
				.getAllContainedPictogramElements(diagrameditor.getDiagramTypeProvider().getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			if (can.equals(eobj)) {
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
			if (!can.equals(eobj)) {
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
		okButton.setEnabled(true);
	}

}
