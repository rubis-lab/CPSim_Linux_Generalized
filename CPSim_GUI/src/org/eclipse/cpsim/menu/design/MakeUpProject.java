package org.eclipse.cpsim.menu.design;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

// TODO NEED TO REFACTORING
public class MakeUpProject {
	public static IProject createProject(String projectName) {
		IProject project = createBaseProject(projectName);
		return project;
	}

	public static IProject createBaseProject(String projectName) {
		IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (!newProject.exists()) {
			URI location = ResourcesPlugin.getWorkspace().getRoot().getLocationURI();
			IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
			if (location != null && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location)) {
				location = null;
			}
			desc.setLocationURI(location);
			try {
				newProject.create(desc, null);
				if (!newProject.isOpen()) {
					newProject.open(null);
					IFolder srcFolder = newProject.getFolder("src");
					srcFolder.create(false, false, null);
//					srcFolder.createLink(newProject.getLocationURI(), IResource.FOLDER, null);
					IFolder diagramsFolder = srcFolder.getFolder("diagrams");
					diagramsFolder.create(false, false, null);
//					diagramsFolder.createLink(srcFolder.getLocationURI(), IResource.FOLDER, null);
					IFile newFile = diagramsFolder.getFile(projectName + ".diagram");
					String contents = "<?xml version=\"1.0\" encoding=\"ASCII\"?>\n<pi:Diagram xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:al=\"http://eclipse.org/graphiti/mm/algorithms\" xmlns:pi=\"http://eclipse.org/graphiti/mm/pictograms\" visible=\"true\" gridUnit=\"10\" diagramTypeId=\"hyundaiConfiguratorGraphiti\" name=\""
							+ projectName
							+ "\" snapToGrid=\"true\" version=\"0.14.0\">\n  <graphicsAlgorithm xsi:type=\"al:Rectangle\" background=\"//@colors.1\" foreground=\"//@colors.0\" lineWidth=\"1\" transparency=\"0.0\" width=\"1000\" height=\"1000\"/>\n  <colors red=\"227\" green=\"238\" blue=\"249\"/>\n  <colors red=\"255\" green=\"255\" blue=\"255\"/>\n</pi:Diagram>";
					InputStream stream;
					try {
						stream = new ByteArrayInputStream(contents.getBytes("UTF-8"));
						newFile.create(stream, false, null);
//						newFile.createLink(diagramsFolder.getLocationURI(), IResource.FILE, null);
//        
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, newFile, true);

//
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return newProject;
	}
}
