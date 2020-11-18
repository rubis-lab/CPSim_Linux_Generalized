package org.eclipse.cpsim.configurator.property;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.platform.GFPropertySection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class HConfiguratorEClassSection extends GFPropertySection implements ITabbedPropertyConstants {

	private Composite parent, carComposite, canComposite, ecuComposite, swcComposite;
	private TabbedPropertySheetPage tabbedPropertySheetPage;
	private int ruComposite = -1; /* recently used */

	private final int CAR_USED = 0;
	private final int CAN_USED = 1;
	private final int ECU_USED = 2;
	private final int SWC_USED = 3;

	private final String[] nCoresList = { "1", "2", "3", "4" };
	private final String[] schedulingList = { "RM", "DM", "EDF", "CPS" };
	private final String[] targetArchitectureList = { "SPC56EL70", "TC275" };
	private final String[] iscanfdList = { "CAN", "CAN-FD" };
//	private final String[] virtualList = { "Invisible", "Visible" };

	private Text idText;

	/* CAN */
	private final int CANDB_LIMIT = 5;
	private Text bandwidthText;
	Text[] candbText = new Text[CANDB_LIMIT];
	private CCombo iscanfdCbx; /* is CAN-FD or not */
	Button[] candbButton = new Button[CANDB_LIMIT];
	private static HConfiguratorEClassSection instance;
	// private Text versionText;

	/* ECU */
	private CCombo nCoresCbx; /* Number of cores */
	private Text sycText; /* System Clock */
	private CCombo schPolicyCbx;/* Scheduling policy */
	private CCombo virtualCbx; /* Virtual */
	private CCombo targetCbx; /* Target Architecture */

	/* SWC */
	private Text periodText;
	private Text deadlineText;
	private Text wcetText;
	private Text bcetText;
	private String readConText;
	private String writeConText;
	private Text proconText;
	private Text phaseText;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.parent = parent;
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;

		parent.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {

			}

			public void keyPressed(KeyEvent e) {

			}
		});
	}

	@Override
	public void refresh() {
		super.refresh();
		PictogramElement pe = getSelectedPictogramElement();
		if (pe != null) {
			Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
			// the filter assured, that it is a EClass
			if (obj == null)
				return;

			/* CAR */
			if (obj instanceof CAR) {
				cleanUp();
				createCAR(parent, tabbedPropertySheetPage);
				ruComposite = CAR_USED;

				CAR car = (CAR) obj;
				String name = car.getId();
				if (name == null)
					car.setId("car");
				idText.setText(name == null ? "car" : name);
			}
			/* CAN */
			else if (obj instanceof CAN) {
				cleanUp();
				createCAN(parent, tabbedPropertySheetPage);
				ruComposite = CAN_USED;
				CAN can = (CAN) obj;
				// CAN can = (CAN)obj;
				String id = can.getId();
				if (id == null) {
					int idx = -1;
					Collection<PictogramElement> pes = Graphiti.getPeService()
							.getAllContainedPictogramElements(getDiagram());
					Iterator<PictogramElement> it = pes.iterator();
					while (it.hasNext()) {
						PictogramElement npe = it.next();
						EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(npe);
						if (eobj instanceof CAN) {
							idx++;
						}
					}

					can.setId("can" + idx);
				}
				idText.setText(id == null ? "can" : id);
				bandwidthText.setText("" + can.getBandwidth());
				// clean up boxes
				adjustCanBoxBtn(can);

				boolean iscanfd = can.isIsCANFD();
				String iscanfdStr = iscanfd ? iscanfdList[1] : iscanfdList[0];
				iscanfdCbx.setText(iscanfdStr);
			}
			/* ECU */
			else if (obj instanceof ECU) {
				cleanUp();
				createECU(parent, tabbedPropertySheetPage);
				ruComposite = ECU_USED;

				setECUIdx();

				ECU ecu = (ECU) obj;
				String id = ecu.getId();
				if (id == null) {
					int idx = -1;
					Collection<PictogramElement> pes = Graphiti.getPeService()
							.getAllContainedPictogramElements(getDiagram());
					Iterator<PictogramElement> it = pes.iterator();
					while (it.hasNext()) {
						PictogramElement npe = it.next();
						EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(npe);
						if (eobj instanceof ECU) {
							idx++;
						}
					}

					ecu.setId("ecu" + idx);
				}
				idText.setText(id == null ? "ecu" : id);
				String clock = Integer.toString(ecu.getSystemClock());
				sycText.setText(clock == null ? "" : clock);
				int nCoresInt = ecu.getNumberOfCores();
				if (nCoresInt < 1)
					nCoresInt = 1;
				else if (nCoresInt > 4)
					nCoresInt = 4;
				String nCores = Integer.toString(nCoresInt);
				nCoresCbx.setText(nCores == null ? "1" : nCores);
				String policy = ecu.getSchedulingPolicy();
				if (policy == null)
					ecu.setSchedulingPolicy(schedulingList[0]);
				schPolicyCbx.setText(policy == null ? schedulingList[0] : policy);
				String target = ecu.getTargetArchitecture();
				if (target == null)
					ecu.setTargetArchitecture(targetArchitectureList[0]);
				targetCbx.setText(target == null ? targetArchitectureList[0] : target);
			}
			/* SWC */
			else if (obj instanceof SWC) {
				cleanUp();
				createSWC(parent, tabbedPropertySheetPage);
				ruComposite = SWC_USED;

				setSWCIdx();

				SWC swc = (SWC) obj;
				String id = swc.getId();
				if (id == null) {
					int idx = -1;
					Collection<PictogramElement> pes = Graphiti.getPeService()
							.getAllContainedPictogramElements(getDiagram());
					Iterator<PictogramElement> it = pes.iterator();
					while (it.hasNext()) {
						PictogramElement npe = it.next();
						EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(npe);
						if (eobj instanceof SWC) {
							idx++;
						}
					}

					swc.setId("swc" + idx);
				}
				idText.setText(id == null ? "swc" : id);
				String period = Integer.toString(swc.getPeriod());
				periodText.setText(period == null ? "0" : period);
				String deadline = Integer.toString(swc.getDeadline());
				deadlineText.setText(deadline == null ? "0" : deadline);
				String wcet = Integer.toString(swc.getWcet());
				wcetText.setText(wcet == null ? "0" : wcet);
				String bcet = Integer.toString(swc.getBcet());
				bcetText.setText(bcet == null ? "0" : bcet);
				String phase = Integer.toString(swc.getPhase());
				phaseText.setText(phase == null ? "0" : phase);
				String procon = swc.getProcon();
				phaseText.setText(phase == null ? "0" : phase);
			
			}

		}

	}

	private final void createCAR(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		if (carComposite != null)
			return;
		carComposite = factory.createFlatFormComposite(parent);
		Composite composite = carComposite;
		FormData data;

		// ID
		idText = factory.createText(composite, "");
		idText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(0, VSPACE);
		idText.setLayoutData(data);
		idText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* CAR */
					if (obj instanceof CAR) {
						CAR car = (CAR) obj;
						car.setId(idText.getText());
					}
				}
			}

			public void keyPressed(KeyEvent e) {

			}
		});

		CLabel valueLabel = factory.createCLabel(composite, "ID:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(idText, -HSPACE);
		data.top = new FormAttachment(idText, 0, SWT.CENTER);
		valueLabel.setLayoutData(data);

		carComposite.pack();
		carComposite.redraw();
	}

	public static HConfiguratorEClassSection getInstance() {
		if (instance == null) {
			// instance = new HConfiguratorEClassSection();
		}
		return instance;
	}

	private final void createCAN(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		if (canComposite != null)
			return;
		canComposite = factory.createFlatFormComposite(parent);
		Composite composite = canComposite;
		FormData data;

		// ID
		idText = factory.createText(composite, "");
		idText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 25);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(0, VSPACE);
		idText.setLayoutData(data);
		idText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* CAR */
					if (obj instanceof CAN) {
						CAN can = (CAN) obj;
						can.setId(idText.getText());
					}
				}
			}

			public void keyPressed(KeyEvent e) {

			}
		});

		CLabel valueLabel = factory.createCLabel(composite, "ID:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(idText, -HSPACE);
		data.top = new FormAttachment(idText, 0, SWT.CENTER);
		valueLabel.setLayoutData(data);

		// Bandwidth
		bandwidthText = factory.createText(composite, "");
		bandwidthText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 25);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(idText, VSPACE);
		bandwidthText.setLayoutData(data);
		bandwidthText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* CAR */
					if (obj instanceof CAN) {
						CAN can = (CAN) obj;
						can.setBandwidth(Integer.parseInt(bandwidthText.getText()));
					}
				}
			}

			public void keyPressed(KeyEvent e) {

			}
		});

		CLabel bandwidthLabel = factory.createCLabel(composite, "Bandwidth (Kbps):");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(bandwidthText, HSPACE);
		data.top = new FormAttachment(bandwidthText, 0, SWT.CENTER);
		bandwidthLabel.setLayoutData(data);

		// Version
		/*
		 * versionText = factory.createText(composite, "");
		 * versionText.setEditable(true); data = new FormData(); data.left = new
		 * FormAttachment(0, STANDARD_LABEL_WIDTH +25); data.right = new
		 * FormAttachment(0, 300); data.top = new FormAttachment( bandwidthText,
		 * VSPACE); versionText.setLayoutData(data);
		 * 
		 * CLabel versionLabel = factory.createCLabel(composite, "Version:"); data = new
		 * FormData(); data.left = new FormAttachment(0, 0); data.right = new
		 * FormAttachment(versionText, -HSPACE); data.top = new
		 * FormAttachment(versionText, 0, SWT.CENTER); versionLabel.setLayoutData(data);
		 */

		// CAN or CAN-FD
		iscanfdCbx = factory.createCCombo(composite, SWT.READ_ONLY);
		for (String s : iscanfdList)
			iscanfdCbx.add(s);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 25);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(bandwidthText, VSPACE);
		iscanfdCbx.setLayoutData(data);
		iscanfdCbx.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* CAN */
					if (obj instanceof CAN) {
						CAN can = (CAN) obj;
						can.setIsCANFD(iscanfdCbx.getText().equalsIgnoreCase("CAN-FD") ? true : false);
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		CLabel typeLabel = factory.createCLabel(composite, "Type:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(iscanfdCbx, HSPACE);
		data.top = new FormAttachment(iscanfdCbx, 0, SWT.CENTER);
		typeLabel.setLayoutData(data);

//		Edited Section Inserted		

		// CANDB

		PictogramElement pe = getSelectedPictogramElement();
		if (pe != null) {
			Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
			// the filter assured, that it is a EClass
			if (obj == null)
				return;

			/* CAR */
			if (obj instanceof CAN) {
				CAN can = (CAN) obj;

				candbText[0] = factory.createText(composite, "");
				candbText[0].setEditable(false);
				data = new FormData();
				data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 25);
				data.right = new FormAttachment(0, 300);
				data.top = new FormAttachment(iscanfdCbx, VSPACE);
				candbText[0].setLayoutData(data);

				candbButton[0] = factory.createButton(composite, "...", SWT.PUSH);
				candbButton[0].addSelectionListener(selectListener);
				data = new FormData();
				data.left = new FormAttachment(candbText[0], 10, SWT.RIGHT);
				data.top = new FormAttachment(candbText[0], -5, SWT.TOP);
				candbButton[0].setLayoutData(data);

				for (int i = 1; i < 5; i++) {
					candbText[i] = factory.createText(composite, "");
					candbText[i].setEditable(false);
					candbText[i].setVisible(false);

					data = new FormData();
					data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 25);
					data.right = new FormAttachment(0, 300);
					data.top = new FormAttachment(candbText[i - 1], VSPACE);
					candbText[i].setLayoutData(data);

					candbButton[i] = factory.createButton(composite, "...", SWT.PUSH);
					candbButton[i].addSelectionListener(selectListener);
					candbButton[i].setVisible(false);

					data = new FormData();
					data.left = new FormAttachment(candbText[i], 10, SWT.RIGHT);
					data.top = new FormAttachment(candbText[i], -5, SWT.TOP);
					candbButton[i].setLayoutData(data);
				}

				adjustCanBoxBtn(can);

			}
		}

		// Need to ask how to change Refresh() function now that textbox is also array.

		// new - new section end

		CLabel candbLabel = factory.createCLabel(composite, "CANDB (.dbc):");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(candbText[0], HSPACE);
		data.top = new FormAttachment(candbText[0], 0, SWT.CENTER);
		candbLabel.setLayoutData(data);
		// Edited Section Ending

		canComposite.pack();
		canComposite.redraw();
	}

	private final void createECU(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		if (ecuComposite != null)
			return;
		ecuComposite = factory.createFlatFormComposite(parent);
		Composite composite = ecuComposite;
		FormData data;

		// ID
		idText = factory.createText(composite, ""); //$NON-NLS-1$
		idText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 40);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(0, VSPACE);
		idText.setLayoutData(data);
		idText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof ECU) {
						ECU ecu = (ECU) obj;
						ecu.setId(idText.getText());
					}
				}
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		CLabel valueLabel = factory.createCLabel(composite, "ID:"); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(idText, HSPACE);
		data.top = new FormAttachment(idText, 0, SWT.CENTER);
		valueLabel.setLayoutData(data);

		// Number of Cores
		nCoresCbx = factory.createCCombo(composite, SWT.READ_ONLY);
		for (String s : nCoresList)
			nCoresCbx.add(s);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 40);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(idText, VSPACE);
		nCoresCbx.setEditable(false);
		nCoresCbx.setLayoutData(data);
		nCoresCbx.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof ECU) {
						ECU ecu = (ECU) obj;
						ecu.setNumberOfCores(nCoresCbx.getSelectionIndex() + 1);
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		CLabel nocLabel = factory.createCLabel(composite, "Number of Cores:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(nCoresCbx, HSPACE);
		data.top = new FormAttachment(nCoresCbx, 0, SWT.CENTER);
		nocLabel.setLayoutData(data);

		// System clock
		sycText = factory.createText(composite, "");
		sycText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 40);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(nCoresCbx, VSPACE);
		sycText.setLayoutData(data);
		sycText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof ECU) {
						ECU ecu = (ECU) obj;
						ecu.setSystemClock(Integer.parseInt(sycText.getText()));
					}
				}
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		CLabel sycLabel = factory.createCLabel(composite, "System Clock (MHz):");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(sycText, HSPACE + 10);
		data.top = new FormAttachment(sycText, 0, SWT.CENTER);
		sycLabel.setLayoutData(data);

		// Scheduling policy
		schPolicyCbx = factory.createCCombo(composite, SWT.READ_ONLY);
		for (String s : schedulingList)
			schPolicyCbx.add(s);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 40);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(sycText, VSPACE);
		schPolicyCbx.setLayoutData(data);
		schPolicyCbx.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof ECU) {
						ECU ecu = (ECU) obj;
						ecu.setSchedulingPolicy(schPolicyCbx.getText());
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		CLabel schLabel = factory.createCLabel(composite, "Scheduling Policy:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(schPolicyCbx, HSPACE);
		data.top = new FormAttachment(schPolicyCbx, 0, SWT.CENTER);
		schLabel.setLayoutData(data);

		// Virtual
		/*
		 * virtualCbx = factory.createCCombo( composite, SWT.READ_ONLY ); for ( String s
		 * : virtualList ) virtualCbx.add( s ); data = new FormData(); data.left = new
		 * FormAttachment(0, STANDARD_LABEL_WIDTH +40); data.right = new
		 * FormAttachment(0, 300); data.top = new FormAttachment( schPolicyCbx, VSPACE);
		 * virtualCbx.setLayoutData(data); virtualCbx.addSelectionListener(
		 * virtualListener );
		 * 
		 * CLabel virtualLabel = factory.createCLabel(composite, "Virtual:"); data = new
		 * FormData(); data.left = new FormAttachment(0, 0); data.right = new
		 * FormAttachment( virtualCbx, HSPACE); data.top = new FormAttachment(
		 * virtualCbx, 0, SWT.CENTER); virtualLabel.setLayoutData(data);
		 */

		// Target Architecture
		targetCbx = factory.createCCombo(composite, SWT.READ_ONLY);
		for (String s : targetArchitectureList)
			targetCbx.add(s);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH + 40);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(schPolicyCbx, VSPACE);
		targetCbx.setLayoutData(data);
		targetCbx.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof ECU) {
						ECU ecu = (ECU) obj;
						ecu.setTargetArchitecture(targetCbx.getText());
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		CLabel targetLabel = factory.createCLabel(composite, "Target Architecture:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(targetCbx, HSPACE);
		data.top = new FormAttachment(targetCbx, 0, SWT.CENTER);
		targetLabel.setLayoutData(data);

		ecuComposite.pack();
		ecuComposite.redraw();
	}

	private final void createSWC(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		TabbedPropertySheetWidgetFactory factory = getWidgetFactory();
		if (swcComposite != null)
			return;
		swcComposite = factory.createFlatFormComposite(parent);
		Composite composite = swcComposite;
		FormData data;

		// ID
		idText = factory.createText(composite, "");
		idText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(0, VSPACE);
		idText.setLayoutData(data);
		idText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof SWC) {
						SWC swc = (SWC) obj;
						swc.setId(idText.getText());
					}
				}
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		CLabel valueLabel = factory.createCLabel(composite, "ID:");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(idText, HSPACE);
		data.top = new FormAttachment(idText, 0, SWT.CENTER);
		valueLabel.setLayoutData(data);

		// Virtual
		/*
		 * virtualCbx = factory.createCCombo( composite, SWT.READ_ONLY ); for ( String s
		 * : virtualList ) virtualCbx.add( s ); data = new FormData(); data.left = new
		 * FormAttachment(0, STANDARD_LABEL_WIDTH); data.right = new FormAttachment(0,
		 * 300); data.top = new FormAttachment( idText, VSPACE);
		 * virtualCbx.setLayoutData(data);
		 * 
		 * CLabel virtualLabel = factory.createCLabel(composite, "Virtual:"); data = new
		 * FormData(); data.left = new FormAttachment(0, 0); data.right = new
		 * FormAttachment( virtualCbx, HSPACE); data.top = new FormAttachment(
		 * virtualCbx, 0, SWT.CENTER); virtualLabel.setLayoutData(data);
		 */

		// Period
		periodText = factory.createText(composite, "");
		periodText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(idText, VSPACE);
		periodText.setLayoutData(data);
		periodText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof SWC) {
						SWC swc = (SWC) obj;
						swc.setPeriod(Integer.parseInt(periodText.getText()));
					}
				}
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		CLabel periodLabel = factory.createCLabel(composite, "Period (ms):");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(periodText, HSPACE);
		data.top = new FormAttachment(periodText, 0, SWT.CENTER);
		periodLabel.setLayoutData(data);

		// Deadline
		deadlineText = factory.createText(composite, "");
		deadlineText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(periodText, VSPACE);
		deadlineText.setLayoutData(data);
		deadlineText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof SWC) {
						SWC swc = (SWC) obj;
						swc.setDeadline(Integer.parseInt(deadlineText.getText()));
					}
				}
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		CLabel deadlineLabel = factory.createCLabel(composite, "Deadline (ms):");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(deadlineText, HSPACE + 5);
		data.top = new FormAttachment(deadlineText, 0, SWT.CENTER);
		deadlineLabel.setLayoutData(data);

		// WCET
		wcetText = factory.createText(composite, "");
		wcetText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(deadlineText, VSPACE);
		wcetText.setLayoutData(data);
		wcetText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof SWC) {
						SWC swc = (SWC) obj;
						swc.setWcet(Integer.parseInt(wcetText.getText()));
					}
				}
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		CLabel wcetLabel = factory.createCLabel(composite, "WCET (ms):");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(wcetText, HSPACE);
		data.top = new FormAttachment(wcetText, 0, SWT.CENTER);
		wcetLabel.setLayoutData(data);

		// BCET
		bcetText = factory.createText(composite, "");
		bcetText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(wcetText, VSPACE);
		bcetText.setLayoutData(data);
		bcetText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof SWC) {
						SWC swc = (SWC) obj;
						swc.setBcet(Integer.parseInt(bcetText.getText()));
					}
				}
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		CLabel bcetLabel = factory.createCLabel(composite, "BECT (ms):");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(bcetText, HSPACE);
		data.top = new FormAttachment(bcetText, 0, SWT.CENTER);
		bcetLabel.setLayoutData(data);
		
		// Phase
		phaseText = factory.createText(composite, "");
		phaseText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(bcetText, VSPACE);
		phaseText.setLayoutData(data);
		phaseText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof SWC) {
						SWC swc = (SWC) obj;
						swc.setPhase(Integer.parseInt(phaseText.getText()));
					}
				}
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		CLabel phaseLabel = factory.createCLabel(composite, "Phase (ms):");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(phaseText, HSPACE);
		data.top = new FormAttachment(phaseText, 0, SWT.CENTER);
		phaseLabel.setLayoutData(data);
		
		// producer-consumer
		proconText = factory.createText(composite, "");
		proconText.setEditable(true);
		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(0, 300);
		data.top = new FormAttachment(phaseText, VSPACE);
		proconText.setLayoutData(data);
		proconText.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* ECU */
					if (obj instanceof SWC) {
						SWC swc = (SWC) obj;
						swc.setProcon(proconText.getText());
					}
				}
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		CLabel proconLabel = factory.createCLabel(composite, "Consumers (SWC IDs):");
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(proconText, HSPACE);
		data.top = new FormAttachment(proconText, 0, SWT.CENTER);
		proconLabel.setLayoutData(data);


		swcComposite.pack();
		swcComposite.redraw();
	}

	private void cleanUp() {
		if (ruComposite != CAR_USED && carComposite != null) {
			carComposite.setVisible(false);
			carComposite.redraw();
			carComposite.dispose();
			carComposite = null;
		}

		if (ruComposite != CAN_USED && canComposite != null) {
			canComposite.setVisible(false);
			canComposite.redraw();
			canComposite.dispose();
			canComposite = null;
		}

		if (ruComposite != ECU_USED && ecuComposite != null) {
			ecuComposite.setVisible(false);
			ecuComposite.redraw();
			ecuComposite.dispose();
			ecuComposite = null;
		}

		if (ruComposite != SWC_USED && swcComposite != null) {
			swcComposite.setVisible(false);
			swcComposite.redraw();
			swcComposite.dispose();
			swcComposite = null;
		}
	}

	private void setECUIdx() {
	}

	private void setSWCIdx() {
	}

	SelectionListener virtualListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			Diagram diagram = Graphiti.getPeService().getDiagramForPictogramElement(getSelectedPictogramElement());
			if (!diagram.eResource().getContents().isEmpty()) {
				/* Change the virtual value */
				ECU ecu = (ECU) Graphiti.getLinkService()
						.getBusinessObjectForLinkedPictogramElement(getSelectedPictogramElement());

				if (virtualCbx.getSelectionIndex() == 0)
					ecu.setVirtual(VirtualCategory.INVISIBLE);
				else
					ecu.setVirtual(VirtualCategory.VISIBLE);

				System.out.println(ecu);

				/* Change the image */
				/*
				 * Diagram diagram = Graphiti.getPeService().getDiagramForPictogramElement(
				 * getSelectedPictogramElement()); Diagram nDiagram =
				 * Graphiti.getPeCreateService().createDiagram( diagram.getDiagramTypeId(),
				 * diagram.getName(), true );
				 * 
				 * ContainerShape containerShape =
				 * Graphiti.getPeCreateService().createContainerShape ( diagram, false ); Image
				 * image = Graphiti.getGaService().createImage( containerShape,
				 * CustomImageProvider.IMG_SWC ); Graphiti.getGaService().setLocationAndSize(
				 * image, 0, 0, CustomImageProvider.SWC_WIDTH, CustomImageProvider.SWC_HEIGHT );
				 * 
				 * TransactionalEditingDomain domain = TransactionUtil.getEditingDomain( ecu );
				 * ICommandProxy command = new ICommandProxy ( new HConfiguratorCommand (
				 * domain, ecu ) ); command.execute();
				 */
			}
		}
	};

	SelectionListener selectListener = new SelectionListener() {
		public void widgetSelected(SelectionEvent e) {
			FileDialog dialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SHEET);
			dialog.setFilterExtensions(new String[] { "*.dbc" });
			String dir = dialog.open(); // return the selected file or null if user cancels
			if (dir != null) {

				PictogramElement pe = getSelectedPictogramElement();
				if (pe != null) {
					Object obj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pe);
					// the filter assured, that it is a EClass
					if (obj == null)
						return;

					/* CAR */
					if (obj instanceof CAN) {
						CAN can = (CAN) obj;
						String[] canDbsL = new String[CANDB_LIMIT];

						for (int i = 0; i < CANDB_LIMIT; i++) {
							if ((Button) e.getSource() == candbButton[i]) { // based on which button is selected, set
																			// text of corresponding text box and turn
																			// on new button/boxes
								if (i < CANDB_LIMIT - 1) {
									candbButton[i + 1].setVisible(true);
									candbText[i + 1].setVisible(true);
								}

								candbText[i].setText(dir);
							}
						}

						int count = 0;
						for (int i = 0; i < 5; i++) {
							if (candbText[i].getText() != null && candbText[i].getText() != "") {
								canDbsL[i] = candbText[i].getText();
								count++;
							}
						}
						String[] noNullCanDbs = new String[count];
						for (int i = 0; i < count; i++) {
							noNullCanDbs[i] = canDbsL[i];
						}
						count = 0;
						String newCanDB = String.join(";", noNullCanDbs);
						can.setCanDB(newCanDB);
						// System.out.println("Saved String from Click: " + can.getCanDB());

					}
				}

			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// do nothing

		}
	};

	public void adjustCanBoxBtn(CAN can) {
		String canDb = can.getCanDB();
		if (canDb != null) {
			// System.out.println("Previously Saved String on Start: " + canDb);
			String[] canDbs = canDb.split(";");
			// System.out.println("Number of saved strings on start: " + canDbs.length);
			int i;
			for (i = 0; i < CANDB_LIMIT; i++) {
				if (i < canDbs.length) {
					if (canDbs[i] != null) {
						candbText[i].setText(canDbs[i]);
					} else {
						candbText[i].setText("");
					}
					candbText[i].setVisible(true);
					candbButton[i].setVisible(true);
				} else if (i == canDbs.length) {
					candbText[i].setVisible(true);
					candbButton[i].setVisible(true);
					candbText[i].setText("");
				} else {
					candbText[i].setVisible(false);
					candbButton[i].setVisible(false);
					candbText[i].setText("");
				}
			}
		} else {
			candbText[0].setText("");
			for (int j = 1; j < CANDB_LIMIT; j++) {
				candbText[j].setVisible(false);
				candbButton[j].setVisible(false);
				candbText[j].setText("");
			}
		}
	}

}