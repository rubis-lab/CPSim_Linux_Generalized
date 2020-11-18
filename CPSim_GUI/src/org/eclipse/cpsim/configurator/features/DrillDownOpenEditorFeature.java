package org.eclipse.cpsim.configurator.features;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.cpsim.Diagram.SWC;
import org.eclipse.cpsim.Diagram.impl.SWCImpl;
import org.eclipse.cpsim.Diagram.util.DiagramUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractDrillDownFeature;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class DrillDownOpenEditorFeature extends AbstractDrillDownFeature {
	IDoubleClickContext context;

	public DrillDownOpenEditorFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Open Code Editor";
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
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof SWC || bo instanceof SWCImpl) {
				ret = true;
			}
		}

		return ret;
	}

	@Override
	public void execute(ICustomContext context) {
		String name;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes == null || pes.length > 1)
			return;

		EObject eobj = (EObject) getBusinessObjectForPictogramElement(pes[0]);
		if (eobj instanceof SWC) {
			String s = ((SWC) eobj).getId();
			name = s == null ? "noname" : s;
		} else
			return;

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

		IFile ifile_c = project.getFile(name + ".c");
		IFile ifile_cpp = project.getFile(name + ".cpp");
		
		/* If non-exists */
		if (!ifile_c.exists() && !ifile_cpp.exists()) {
			String contents = initURIDiagram();
			try {
				InputStream source = new ByteArrayInputStream(contents.getBytes());
				ifile_c.create(source, false, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(!ifile_c.exists())
		{
			/* Open the diagram */
			URI diagramURI = URI.createFileURI(ifile_cpp.toString());

			if (diagramURI != null) {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

				try {
					System.out.println("cpp file");
					/*
					 * page.openEditor(new DiagramEditorInput ( diagramURI,
					 * getDiagramEditor().getDiagramTypeProvider().getProviderId() ),
					 * "org.eclipse.graphiti.ui.editor.DiagramEditor");
					 */
					page.openEditor(new FileEditorInput(ifile_cpp), "org.eclipse.cdt.ui.editor.CPPEditor");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else if(!ifile_cpp.exists())
		{
			/* Open the diagram */
			URI diagramURI = URI.createFileURI(ifile_c.toString());

			if (diagramURI != null) {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

				try {
					/*
					 * page.openEditor(new DiagramEditorInput ( diagramURI,
					 * getDiagramEditor().getDiagramTypeProvider().getProviderId() ),
					 * "org.eclipse.graphiti.ui.editor.DiagramEditor");
					 */
					System.out.println("c file");
					page.openEditor(new FileEditorInput(ifile_c), "org.eclipse.cdt.ui.editor.CEditor");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String initURIDiagram() {
		return "";
	}
}
