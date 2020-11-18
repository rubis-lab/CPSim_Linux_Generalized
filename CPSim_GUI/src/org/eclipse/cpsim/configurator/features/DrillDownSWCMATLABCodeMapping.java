package org.eclipse.cpsim.configurator.features;

import java.util.*;
import java.io.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.util.DiagramUtil;
import org.eclipse.cpsim.menu.simulation.RunStopSimulation;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class DrillDownSWCMATLABCodeMapping extends AbstractDrillDownFeature {
	private boolean changed = false;

	private String m_flag = null;
	private String m_target_file;
	private String m_target_dir;
	private String m_file_name;
	private ICustomContext contex;

	public DrillDownSWCMATLABCodeMapping(IFeatureProvider fp) {
		super(fp);
	}

	/* Disable Undo because default undo doesn't support changing id */
	@Override
	public boolean canUndo(IContext context) {
		return false;
	}

	@Override
	public String getName() {
		return "SWC-MATLAB/CODE Mapping";
	}

	public String getTargetFile() {
		return m_target_file;
	}
	@Override
	protected Collection<Diagram> getDiagrams() {
		Collection<Diagram> result = Collections.emptyList();
		Resource resource = getDiagram().eResource();
		URI uri = resource.getURI();
		URI uriTrimmed = uri.trimFragment();
		if (uriTrimmed.isPlatformResource()) {
			String platformString = uriTrimmed.toPlatformString(true);
			IResource fileResource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
			if (fileResource != null) {
				IProject project = fileResource.getProject();
				result = DiagramUtil.getDiagrams(project);
			}
		}
		return result;
	}

	@Override
	public boolean isAvailable(IContext context) {
		if (RunStopSimulation.state == 1)
			return false;

		if (!(context instanceof ICustomContext))
			return false;

		ICustomContext icontext = (ICustomContext) context;

		boolean ret = false;
		PictogramElement[] pes = icontext.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SWC) {
				ret = true;
			}
		}

		return ret;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public boolean hasDoneChanges() {
		return changed;
	}

	@Override
	public void execute(ICustomContext context) {
		contex = context;
		int open = new TitleAreaDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()) {

			private Text txtProjectName;
			protected Button okButton;

			@Override
			public void create() {
				super.create();
				okButton = getButton(IDialogConstants.OK_ID);
				PictogramElement[] pes = contex.getPictogramElements();
				if (pes != null && pes.length == 1) {
					Object bo = getBusinessObjectForPictogramElement(pes[0]);
					if (bo instanceof SWC) {
						SWC swc = (SWC) bo;
						String id = swc.getId();

						String[] id_list = id.split(";");
						if (id_list.length == 3) {
							if (id_list[2] == null || id_list[2].isEmpty())
								okButton.setEnabled(false);
							else
								okButton.setEnabled(true);
						} else
							okButton.setEnabled(false);
					}
				}
			}

			@Override
			protected void configureShell(Shell shell) {
				super.configureShell(shell);
				shell.setText("SWC-CODE Mapping");
			}

			@Override
			protected Control createDialogArea(Composite parent) {
				setTitle("SWC-CODE Mapping");
				setMessage("Open/Create a File");
				setHelpAvailable(false);
				Composite area = (Composite) super.createDialogArea(parent);
				area.setBounds(50, 50, 100, 100);
				Group group1 = new Group(area, SWT.SHADOW_IN);
				group1.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, false, false, 1, 1));
				group1.setText("Select Type");
				group1.setLayout(new RowLayout(SWT.HORIZONTAL));
				Button NewFile = new Button(group1, SWT.RADIO);
				NewFile.setText("New File");
				NewFile.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						m_flag = "M";
					}
				});
				Button OpenFile = new Button(group1, SWT.RADIO);
				OpenFile.setText("Open File");
				OpenFile.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						m_flag = "C";
					}
				});

				Composite area1 = (Composite) super.createDialogArea(parent);
				area1.setLayout(null);
				Label lbtProjectName = new Label(area1, SWT.NONE);
				lbtProjectName.setBounds(5, 10, 50, 20);
				lbtProjectName.setText("File");

				txtProjectName = new Text(area1, SWT.BORDER | SWT.READ_ONLY);
				txtProjectName.setBounds(60, 10, 315, 20);

				/* Show previous settings if it is already set */
				PictogramElement[] pes = contex.getPictogramElements();
				if (pes != null && pes.length == 1) {
					Object bo = getBusinessObjectForPictogramElement(pes[0]);
					if (bo instanceof SWC) {
						SWC swc = (SWC) bo;
						String id = swc.getId();

						String[] id_list = id.split(";");
						if (id_list.length == 3) {
							if (id_list[1].compareTo("M") == 0)
								NewFile.setSelection(true);
							else if (id_list[1].compareTo("C") == 0)
								OpenFile.setSelection(true);

							m_target_file = id_list[2];
							txtProjectName.setText(m_target_file);
						}
					}
				}

				
				Button browser = new Button(area1, SWT.CENTER);
				browser.setBounds(375, 8, 60, 24);
				browser.setText("Browse");
				browser.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event e) {
						if(m_flag.equals("C"))
						{
							FileDialog dialog = new FileDialog(
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN);
							dialog.setText("SWC-MATLAB Model Mapping");
							if (txtProjectName.getText() != null)
								dialog.setFilterPath(txtProjectName.getText());
							m_target_file = dialog.open();
							if (m_target_file == null) {
								okButton.setEnabled(false);
								return;
							} else
								okButton.setEnabled(true);
							txtProjectName.setText(m_target_file);
						}
						else if(m_flag.equals("M"))
						{
							int entire_dialog = new TitleAreaDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()) {
								public Text file_name;
								protected Button okButton;

								@Override
								public void create() {
									super.create();
									okButton = getButton(IDialogConstants.OK_ID);
								};
								@Override
								protected void configureShell(Shell shell) {
									super.configureShell(shell);
									shell.setText("Enter the File Name");
								}
								@Override
								protected Control createDialogArea(Composite parent) {
									Composite area1 = (Composite) super.createDialogArea(parent);
									area1.setLayout(null);
									Label label = new Label(area1, SWT.NONE);
									label.setBounds(5, 10, 50, 20);
									label.setText("File Name");

									file_name = new Text(area1, SWT.SINGLE);
									file_name.setBounds(60, 10, 315, 20);
									return area;
								};
								@Override
								protected Point getInitialSize() {
									return new Point(450, 250);
								}

								@Override
								protected void okPressed() {
									try {
										m_file_name = file_name.getText();
										if (file_name.getText() == null)
											throw new Exception();
									} catch (Exception e) {
										e.printStackTrace();
									}

									super.okPressed();
								}
							}.open();
							DirectoryDialog dialog = new DirectoryDialog(
									PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN);
							dialog.setText("Select Directory");
							if (txtProjectName.getText() != null)
								dialog.setFilterPath(txtProjectName.getText());
							m_target_dir = dialog.open();
							if (m_target_dir == null) {
								okButton.setEnabled(false);
								return;
							} else
								okButton.setEnabled(true);
							txtProjectName.setText(m_target_dir + "/"+ m_file_name);
							Runtime obj = Runtime.getRuntime();
							String command = "gedit " + txtProjectName.getText();
							try
							{
								obj.exec(command);
							}
							catch(IOException e_io)
							{
								System.out.println(e_io);
							}
						}
					}
				});

				return area;
			}

			@Override
			protected Point getInitialSize() {
				return new Point(450, 250);
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

		if (open == 1) { // Pressed cancel button
			changed = false;
			return;
		}

		// = dialog.open();

		// shell.open();
		if(m_flag.equals("C"))
			if (m_target_file == null)
				return;
		if(m_flag.equals("M"))
			if (m_target_dir == null && m_file_name == null)
				return;
		
		changed = true;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SWC) {
				SWC swc = (SWC) bo;
				String id = swc.getId();

				String[] id_list = id.split(";");
				if(m_flag.equals("C"))
					swc.setId(id_list[0] + ";" + m_flag + ";" + m_target_file);
				else if(m_flag.equals("M"))
					swc.setId(id_list[0] + ";" + m_flag + ";" + m_target_dir + "/" + m_file_name);
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pes[0]);
				if (swc.equals(eobj)) {
					ContainerShape containerShape = (ContainerShape) pes[0];
					EList<Shape> shapes = containerShape.getChildren();
					for (Shape s : shapes) {
						// System.out.print(s.toString() + " : ");

						if (s.getGraphicsAlgorithm() instanceof org.eclipse.graphiti.mm.algorithms.Text) {
							org.eclipse.graphiti.mm.algorithms.Text temp = (org.eclipse.graphiti.mm.algorithms.Text) s
									.getGraphicsAlgorithm();
							temp.setValue("Temp_Name");
							temp.setValue(id_list[0]);
						}
						;
					}
				}

			}
		}

	}
}