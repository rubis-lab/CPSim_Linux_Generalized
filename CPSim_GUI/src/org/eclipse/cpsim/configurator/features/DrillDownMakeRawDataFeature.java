package org.eclipse.cpsim.configurator.features;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.cpsim.Diagram.CAN;
import org.eclipse.cpsim.Diagram.CAR;
import org.eclipse.cpsim.Diagram.ECU;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.VirtualCategory;
import org.eclipse.cpsim.Diagram.util.DiagramUtil;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.BoxRelativeAnchor;
import org.eclipse.graphiti.mm.pictograms.ChopboxAnchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DrillDownMakeRawDataFeature extends AbstractDrillDownFeature {
	public DrillDownMakeRawDataFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Make Raw Data";
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
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		try {
			/* Find the file path */
			IProject project = null;
			Resource resource = getDiagram().eResource();
			URI uri = resource.getURI();
			URI uriTrimmed = uri.trimFragment();
			if (uriTrimmed.isPlatformResource()) {
				String platformString = uriTrimmed.toPlatformString(true);
				IResource fileResource = ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
				if (fileResource != null) {
					project = fileResource.getProject();
				} else
					return;
			}

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();

			Element root = doc.createElement("Simulation");
			doc.appendChild(root);

			Element cansElement = doc.createElement("ETH");
			root.appendChild(cansElement);
			Element carElement = doc.createElement("CAR");
			root.appendChild(carElement);
			Element ecusElement = doc.createElement("ECUs");
			root.appendChild(ecusElement);
			Element swcsElement = doc.createElement("SWCs");
			root.appendChild(swcsElement);

			for (PictogramElement pic : Graphiti.getPeService().getAllContainedPictogramElements(getDiagram())) {
				EObject eobj = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(pic);
				/* CAR */
				if (eobj instanceof CAR) {
					CAR car = (CAR) eobj;

					carElement.setAttribute("id", car.getId());

					Element xPosition = doc.createElement("X_position");
					int x = pic.getGraphicsAlgorithm().getX();
					xPosition.appendChild(doc.createTextNode(Integer.toString(x)));
					carElement.appendChild(xPosition);

					Element yPosition = doc.createElement("Y_position");
					int y = pic.getGraphicsAlgorithm().getY();
					yPosition.appendChild(doc.createTextNode(Integer.toString(y)));
					carElement.appendChild(yPosition);

					Element x1Position = doc.createElement("X1_position");
					x1Position.appendChild(doc.createTextNode(Integer.toString(x)));
					carElement.appendChild(x1Position);

					Element y1Position = doc.createElement("Y1_position");
					y1Position.appendChild(doc.createTextNode(Integer.toString(y)));
					carElement.appendChild(y1Position);

					Element sendTo = doc.createElement("Send_to");
					sendTo.appendChild(doc.createTextNode("None"));
					carElement.appendChild(sendTo);
				}

				/* CAN */
				else if (eobj instanceof CAN) {
					CAN can = (CAN) eobj;

					Element canElement = doc.createElement("ETH");
					cansElement.appendChild(canElement);
					canElement.setAttribute("id", can.getId());

					Element bandwidth = doc.createElement("Bandwidth");
					canElement.appendChild(bandwidth);

					Element max = doc.createElement("max");
					bandwidth.appendChild(max);

					max.setAttribute("unit", "Kbps");

					max.appendChild(doc.createTextNode((Integer.toString(can.getBandwidth())).replaceAll(" Kbps", "")));

					Element dist = doc.createElement("distribution");
					bandwidth.appendChild(dist);

					Element version = doc.createElement("Version");
					version.appendChild(doc.createTextNode("2.0A"));
					canElement.appendChild(version);

					Element xPosition = doc.createElement("X_position");
					xPosition.appendChild(doc.createTextNode("20"));
					canElement.appendChild(xPosition);

					Element yPosition = doc.createElement("Y_position");
					int y = pic.getGraphicsAlgorithm().getY();
					yPosition.appendChild(doc.createTextNode(Integer.toString(y)));
					canElement.appendChild(yPosition);
				}

				/* ECU */
				else if (eobj instanceof ECU) {
					ECU ecu = (ECU) eobj;

					Element ecuElement = doc.createElement("ECU");
					ecusElement.appendChild(ecuElement);
					ecuElement.setAttribute("id", ecu.getId());

					Element xPosition = doc.createElement("X_position");
					int x = pic.getGraphicsAlgorithm().getX();
					xPosition.appendChild(doc.createTextNode(Integer.toString(x)));
					ecuElement.appendChild(xPosition);

					Element yPosition = doc.createElement("Y_position");
					int y = pic.getGraphicsAlgorithm().getY();
					yPosition.appendChild(doc.createTextNode(Integer.toString(y)));
					ecuElement.appendChild(yPosition);

					Element modelName = doc.createElement("ModelName");
					modelName.appendChild(doc.createTextNode("DefaultModel"));
					ecuElement.appendChild(modelName);

					Element numberOfCores = doc.createElement("Number_of_cores");
					numberOfCores.appendChild(doc.createTextNode(Integer.toString(ecu.getNumberOfCores())));
					ecuElement.appendChild(numberOfCores);

					Element systemClock = doc.createElement("SystemClock");
					systemClock.setAttribute("unit", "MHz");
					systemClock.appendChild(doc.createTextNode(Integer.toString(ecu.getSystemClock())));
					ecuElement.appendChild(systemClock);

					Element policy = doc.createElement("SchedulingPolicy");
					policy.appendChild(doc.createTextNode(ecu.getSchedulingPolicy()));
					ecuElement.appendChild(policy);

					Element virtual = doc.createElement("Virtual");
					String strVirtual;
					if (ecu.getVirtual() == VirtualCategory.VISIBLE)
						strVirtual = "1";
					else
						strVirtual = "0";
					virtual.appendChild(doc.createTextNode(strVirtual));
					ecuElement.appendChild(virtual);

					String connectedCANStr = "";
					List<Connection> connections = Graphiti.getPeService().getAllConnections((AnchorContainer) pic);
					Iterator<Connection> conIter = connections.iterator();
					while (conIter.hasNext()) {
						Connection con = conIter.next();
						EObject eobjS = Graphiti.getLinkService()
								.getBusinessObjectForLinkedPictogramElement(con.getStart().getParent());
						EObject eobjE = Graphiti.getLinkService()
								.getBusinessObjectForLinkedPictogramElement(con.getEnd().getParent());

						if (eobjS instanceof CAN) {
							if (connectedCANStr.length() > 1)
								connectedCANStr += ",";
							connectedCANStr += ((CAN) eobjS).getId();
						} else if (eobjE instanceof CAN) {
							if (connectedCANStr.length() > 1)
								connectedCANStr += ",";
							connectedCANStr += ((CAN) eobjE).getId();
						}
					}

					Element connectedCAN = doc.createElement("Connected_CAN");
					connectedCAN.appendChild(doc.createTextNode(connectedCANStr));
					ecuElement.appendChild(connectedCAN);

				}

				/* SWC */
				else if (eobj instanceof SWC) {
					SWC swc = (SWC) eobj;

					Element swcElement = doc.createElement("SWC");
					swcsElement.appendChild(swcElement);
					swcElement.setAttribute("id", swc.getId());

					Element xPosition = doc.createElement("X_position");
					int x = pic.getGraphicsAlgorithm().getX();
					xPosition.appendChild(doc.createTextNode(Integer.toString(x)));
					swcElement.appendChild(xPosition);

					Element yPosition = doc.createElement("Y_position");
					int y = pic.getGraphicsAlgorithm().getY();
					yPosition.appendChild(doc.createTextNode(Integer.toString(y)));
					swcElement.appendChild(yPosition);

					Element x1Position = doc.createElement("X1_position");
					int x1 = pic.getGraphicsAlgorithm().getX();
					x1Position.appendChild(doc.createTextNode(Integer.toString(x1)));
					swcElement.appendChild(x1Position);

					Element y1Position = doc.createElement("Y1_position");
					int y1 = pic.getGraphicsAlgorithm().getY();
					y1Position.appendChild(doc.createTextNode(Integer.toString(y1)));
					swcElement.appendChild(y1Position);

					Element virtual = doc.createElement("Virtual");
					String strVirtual;
					if (swc.getVirtual() == VirtualCategory.VISIBLE)
						strVirtual = "1";
					else
						strVirtual = "0";
					virtual.appendChild(doc.createTextNode(strVirtual));
					swcElement.appendChild(virtual);

					Element recvFrom = doc.createElement("Recv_from");
					recvFrom.appendChild(doc.createTextNode("module name"));
					swcElement.appendChild(recvFrom);

					Element sendTo = doc.createElement("Send_to");
					sendTo.appendChild(doc.createTextNode("module name"));
					swcElement.appendChild(sendTo);

					Element period = doc.createElement("Period");
					period.setAttribute("unit", "ms");
					period.appendChild(doc.createTextNode(Integer.toString(swc.getPeriod())));
					swcElement.appendChild(period);

					Element wcet = doc.createElement("WCET");
					wcet.setAttribute("unit", "ms");
					wcet.appendChild(doc.createTextNode(Integer.toString(swc.getWcet())));
					swcElement.appendChild(wcet);
					
					Element bcet = doc.createElement("BCET");
					bcet.setAttribute("unit", "ms");
					bcet.appendChild(doc.createTextNode(Integer.toString(swc.getBcet())));
					swcElement.appendChild(bcet);

					Element deadline = doc.createElement("Deadline");
					deadline.setAttribute("unit", "ms");
					deadline.appendChild(doc.createTextNode(Integer.toString(swc.getDeadline())));
					swcElement.appendChild(deadline);

					Element phase = doc.createElement("Phase");
					phase.setAttribute("unit", "ms");
					phase.appendChild(doc.createTextNode(Integer.toString(swc.getPhase())));
					swcElement.appendChild(phase);
					
					Element procon = doc.createElement("PROCON");
					procon.setAttribute("task", "");
					procon.appendChild(doc.createTextNode(swc.getProcon()));
					swcElement.appendChild(procon);
					
					Element readCon = doc.createElement("READCON");
					bcet.setAttribute("y/n", "");
					bcet.appendChild(doc.createTextNode(swc.getReadCon()));
					swcElement.appendChild(readCon);
					
					Element writeCon = doc.createElement("WRITECON");
					bcet.setAttribute("y/n", "");
					bcet.appendChild(doc.createTextNode(swc.getWriteCon()));
					swcElement.appendChild(writeCon);

					ECU connectedEcuRef = swc.getEcuRef();

					Element connectedEcu = doc.createElement("Connected_ECU");
					connectedEcu.setAttribute("id", connectedEcuRef.getId());
					swcElement.appendChild(connectedEcu);

					/* Specify core's anchor */
					Anchor iac = null;
					Collection<PictogramElement> swcInnerPics = Graphiti.getPeService()
							.getAllContainedPictogramElements(pic);
					Iterator<PictogramElement> swcPicIt = swcInnerPics.iterator();
					while (swcPicIt.hasNext()) {
						PictogramElement cPic = swcPicIt.next();
						if (cPic instanceof ChopboxAnchor) {
							iac = (Anchor) cPic;
						}
					}

					Anchor oac = null;
					EList<Connection> inConnections = iac.getIncomingConnections();
					for (int i = 0; i < inConnections.size(); i++) {
						Connection connection = inConnections.get(i);
						if (connection.getStart() instanceof BoxRelativeAnchor) {
							oac = connection.getStart();
						}
					}
					EList<Connection> outConnections = iac.getOutgoingConnections();
					for (int i = 0; i < outConnections.size(); i++) {
						Connection connection = outConnections.get(i);
						if (connection.getEnd() instanceof BoxRelativeAnchor) {
							oac = connection.getEnd();
						}
					}

					/* Finding the connected core of ECU */
					int idx = 1;
					if (oac != null) {
						BoxRelativeAnchor bac = (BoxRelativeAnchor) oac;
						idx = (int) ((bac.getRelativeWidth() + 0.15) / 0.15);
					}

					/*
					 * int idx=1; for ( PictogramElement findPic :
					 * Graphiti.getPeService().getAllContainedPictogramElements( getDiagram() ) ) {
					 * EObject findEobj =
					 * Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement( findPic
					 * ); if ( findEobj != null && findEobj.equals( connectedEcuRef ) ) {
					 * Collection<PictogramElement> childPics =
					 * Graphiti.getPeService().getAllContainedPictogramElements(findPic);
					 * Iterator<PictogramElement> it = childPics.iterator(); while( it.hasNext() ) {
					 * PictogramElement cPic = it.next(); if ( cPic instanceof BoxRelativeAnchor ) {
					 * if ( cPic.equals( oac ) ) break; idx++; } }
					 * 
					 * } }
					 */

					Element allocatedEcuCore = doc.createElement("Allocated_ECU_Core");
					allocatedEcuCore.appendChild(doc.createTextNode(Integer.toString(idx)));
					swcElement.appendChild(allocatedEcuCore);
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "7");

			DOMSource source = new DOMSource(doc);

			String filepath = project.getLocation().toPortableString() + "/a.hxml";
			System.out.println(filepath);
			File resultFile = new File(filepath);
			StreamResult result2 = new StreamResult(new FileOutputStream(resultFile));

			transformer.transform(source, result2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
