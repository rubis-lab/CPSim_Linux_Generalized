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
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class SWCPropertiesDialog extends PropertiesDialog {

	protected static final String DEFAULT_TITLE = "SWC Properties";
	protected static final String DEFAULT_MESSAGE = "Set SWC Properties";

	private Text periodText;
	private Text deadlineText;
	private Text wcetText;
	private Text bcetText;
	private Text phaseText;
	private String readConText="0";
	private String writeConText="0";
	private Text proconText;

	private Button readConYes;
	private Button readConNo;
	private Button writeConYes;
	private Button writeConNo;
	
	
	private boolean periodValid;
	private boolean deadlineValid;
	private boolean wcetValid;
	private boolean bcetValid;
	private boolean phaseValid;
	private boolean proconValid;
	private boolean readConValid;
	private boolean writeConValid;
	
	private String flag = null;

	SWC swc;

	public SWCPropertiesDialog(Shell parentShell, EObject eobj) {
		super(parentShell);

		if (eobj instanceof SWC) {
			swc = (SWC) eobj;
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
		periodValid = validator(periodText.getText(), Message.PATTERN_DIGIT) ? true : false;
		deadlineValid = validator(deadlineText.getText(), Message.PATTERN_DIGIT) ? true : false;
		wcetValid = validator(wcetText.getText(), Message.PATTERN_DIGIT) ? true : false;
		bcetValid = validator(bcetText.getText(), Message.PATTERN_DIGIT) ? true : false;
		phaseValid = validator(phaseText.getText(), Message.PATTERN_DIGIT) ? true : false;

		idText.addModifyListener(new ModifyListener() {
			// duplicated ID check needed ??
			public void modifyText(ModifyEvent e) {
				idValid = validator(idText.getText(), Message.PATTERN_ID) ? true : false;
				verifyOKButton();
			}
		});
		periodText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				periodValid = validator(periodText.getText(), Message.PATTERN_DIGIT) ? true : false;
				verifyOKButton();
			}
		});
		deadlineText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				deadlineValid = validator(deadlineText.getText(), Message.PATTERN_DIGIT) ? true : false;
				verifyOKButton();
			}
		});
		wcetText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				wcetValid = validator(wcetText.getText(), Message.PATTERN_DIGIT) ? true : false;
				verifyOKButton();
			}
		});
		bcetText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				bcetValid = validator(bcetText.getText(), Message.PATTERN_DIGIT) ? true : false;
				verifyOKButton();
			}
		});
		phaseText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				phaseValid = validator(phaseText.getText(), Message.PATTERN_DIGIT) ? true : false;
				verifyOKButton();
			}
		});
//		readConText.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
////				readConValid = validator(readConText.getText(), Message.PATTERN_DIGIT) ? true : false;
////				verifyOKButton();
//			}
//		});
//		writeConText.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
////				writeConValid = validator(writeConText.getText(), Message.PATTERN_DIGIT) ? true : false;
////				verifyOKButton();
//			}
//		});
		proconText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
//				proconValid = validator(proconText.getText(), Message.PATTERN_DIGIT) ? true : false;
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

		createSWC(container);

		String id = swc.getId().split(";", 3)[0];
		idText.setText(id == null ? Message.DEFAULT_CAN_NAME : id);
		periodText.setText("" + swc.getPeriod());
		deadlineText.setText("" + swc.getDeadline());
		wcetText.setText("" + swc.getWcet());
		bcetText.setText("" + swc.getBcet());
		phaseText.setText("" + swc.getPhase());
		proconText.setText("" + swc.getProcon());	
				
		if(swc.getReadCon().equals("1")) {
			readConYes.setSelection(true);
		}else readConNo.setSelection(true);
		if(swc.getWriteCon().equals("1")) {
			writeConYes.setSelection(true);
		}else writeConNo.setSelection(true);


		Label lineBottom = new Label(area, SWT.SEPARATOR | SWT.HORIZONTAL);
		lineBottom.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));

		return area;
	}

	private final void createSWC(Composite parent) {
		// ID
		Label valueLabel = new Label(parent, SWT.NONE);
		valueLabel.setText("ID:");

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;

		idText = new Text(parent, SWT.BORDER);
		idText.setEditable(true);
		idText.setLayoutData(data);

		// Period
		Label periodLabel = new Label(parent, SWT.NONE);
		periodLabel.setText("Period (ms):");

		periodText = new Text(parent, SWT.BORDER);
		periodText.setEditable(true);
		periodText.setLayoutData(data);

		// Deadline
		Label deadlineLabel = new Label(parent, SWT.NONE);
		deadlineLabel.setText("Deadline (ms):");

		deadlineText = new Text(parent, SWT.BORDER);
		deadlineText.setEditable(true);
		deadlineText.setLayoutData(data);

		// WCET
		Label wcetLabel = new Label(parent, SWT.NONE);
		wcetLabel.setText("WCET (ms):");

		wcetText = new Text(parent, SWT.BORDER);
		wcetText.setEditable(true);
		wcetText.setLayoutData(data);
		
		// BCET
		Label bcetLabel = new Label(parent, SWT.NONE);
		bcetLabel.setText("BCET (ms):");

		bcetText = new Text(parent, SWT.BORDER);
		bcetText.setEditable(true);
		bcetText.setLayoutData(data);		

		// Phase
		Label phaseLabel = new Label(parent, SWT.NONE);
		phaseLabel.setText("Phase (ms):");

		phaseText = new Text(parent, SWT.BORDER);
		phaseText.setEditable(true);
		phaseText.setLayoutData(data);
		
		// producer-consumer
		Label proconLabel = new Label(parent, SWT.NONE);
		proconLabel.setText("Consumers (SWC IDs):");

		proconText = new Text(parent, SWT.BORDER);
		proconText.setEditable(true);
		proconText.setLayoutData(data);
		
		// Read constraint
		Label readConLabel = new Label(parent, SWT.NONE);
		readConLabel.setText("Read constraint");
		
		Composite area = (Composite) super.createDialogArea(parent);
		Group group1 = new Group(area, SWT.SHADOW_IN);
		group1.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		group1.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		readConYes = new Button(group1, SWT.RADIO);
		readConNo = new Button(group1, SWT.RADIO);
		
		readConText = swc.getReadCon();
		readConYes.setText("yes");
		readConYes.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				readConText = "1";
			}
		});
		
		readConNo.setText("no");
		readConNo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				readConText = "0";
			}
		});
		
		writeConText = swc.getWriteCon();
		if(swc.getReadCon().equals("0"))
			readConNo.setSelection(true);
		else readConYes.setSelection(true);
		
		
		// Write constraint
		Label writeConLabel = new Label(parent, SWT.NONE);
		writeConLabel.setText("Write constraint");
		
		Composite area2 = (Composite) super.createDialogArea(parent);
		Group group2 = new Group(area2, SWT.SHADOW_IN);
		group2.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		group2.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		writeConYes = new Button(group2, SWT.RADIO);
		writeConYes.setText("yes");
		writeConYes.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				writeConText = "1";
			}
		});
		writeConNo = new Button(group2, SWT.RADIO);
		writeConNo.setText("no");
		writeConNo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				writeConText = "0";
			}
		});

		if(swc.getWriteCon().equals("0"))
			writeConNo.setSelection(true);
		else writeConYes.setSelection(true);
		
	}

	@Override
	protected void okPressed() {
		String id = idText.getText() + ";" + swc.getId().split(";", 3)[1] + ";" + swc.getId().split(";", 3)[2];
		// System.out.println(id);
		swc.setId(id);
		swc.setPeriod(Integer.parseInt(periodText.getText()));
		swc.setDeadline(Integer.parseInt(deadlineText.getText()));
		swc.setWcet(Integer.parseInt(wcetText.getText()));
		swc.setBcet(Integer.parseInt(bcetText.getText()));
		swc.setPhase(Integer.parseInt(phaseText.getText()));
		swc.setProcon(proconText.getText());
		swc.setReadCon(readConText);
		swc.setWriteCon(writeConText);


		/* Set Text */
		IEditorPart activeeditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		DiagramEditor diagrameditor = (DiagramEditor) activeeditor;
		for (PictogramElement pic : Graphiti.getPeService()
				.getAllContainedPictogramElements(diagrameditor.getDiagramTypeProvider().getDiagram())) {
			EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
			if (swc.equals(eobj)) {
				ContainerShape containerShape = (ContainerShape) pic;
				EList<Shape> shapes = containerShape.getChildren();
				for (Shape s : shapes) {
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
			if (!swc.equals(eobj)) {
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

		if (idValid && periodValid && deadlineValid && wcetValid && bcetValid && phaseValid) {
			setMessage(DEFAULT_MESSAGE);
			okButton.setEnabled(true);
		} else if (!idValid) {
			setMessage("ID is not valid. The ID '" + idText.getText() + "' is not a valid identifier.");
			okButton.setEnabled(false);
		} else if (!periodValid) {
			setMessage("Period must be a number only.");
			okButton.setEnabled(false);
		} else if (!deadlineValid) {
			setMessage("Deadline must be a number only.");
			okButton.setEnabled(false);
		} else if (!wcetValid) {
			setMessage("Wcet must be a number only.");
			okButton.setEnabled(false);
		} else if (!bcetValid) {
			setMessage("Bcet must be a number only.");
			okButton.setEnabled(false);
		} else if (!phaseValid) {
			setMessage("Phase must be a number only.");
			okButton.setEnabled(false);
		} 
	}

}

