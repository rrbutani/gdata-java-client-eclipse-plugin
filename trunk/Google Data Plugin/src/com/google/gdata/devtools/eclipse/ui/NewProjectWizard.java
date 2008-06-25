/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.google.gdata.devtools.eclipse.ui;

import com.google.gdata.devtools.eclipse.Activator;
import com.google.gdata.devtools.eclipse.DependencyConfig;
import com.google.gdata.devtools.eclipse.DependencyConfigParser;
import com.google.gdata.devtools.eclipse.DependencyManager;
import com.google.gdata.devtools.eclipse.ExceptionHandler;
import com.google.gdata.devtools.eclipse.FileTemplateConfig;
import com.google.gdata.devtools.eclipse.FileTemplateConfigParser;
import com.google.gdata.devtools.eclipse.ProjectCreator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.osgi.framework.Bundle;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * This is a new Google Data project wizard. Its role is to create a new java
 * project with a Google Data API template and the required dependencies.  
 */
public class NewProjectWizard extends Wizard implements INewWizard, 
    IExecutableExtension {

  private NewProjectWizardPage mainPage;
  private IProject newGDataProject;
  private IConfigurationElement config;
  
  private Map<String, FileTemplateConfig> templateConfigMap;
  private List<DependencyConfig> dependencyConfigList;
  private FileTemplateConfig selectedTemplateConfig; 

  private boolean extDependencyDesired;
  private boolean dependencyParsingError;
  private String extDependencyPath;
  private String javaClientLibPath;

  private static boolean foundAllExtDependencies;
  private static boolean foundAllGoogleDataDependencies;
  
  /**
   * Constructor for NewProjectWizard.
   */
  public NewProjectWizard() {
    super();
	setNeedsProgressMonitor(true);
	extDependencyDesired = false;
	dependencyParsingError = false;
	try {
	  templateConfigMap = FileTemplateConfigParser.getConfigElements();
	} catch(CoreException e) {
	  ExceptionHandler.handleCoreException("ERROR", "Please report the stack trace to Google", e);
	  getShell().close();
	}
  }
	
  /**
   * Adds a new project wizard page to the wizard.
   */
  @Override
  public void addPages() {
    ImageDescriptor image = null;
    try {
      image = ImageDescriptor.createFromImage(new Image(null, getImageLocation()));
    } catch(IOException e) {
      ExceptionHandler.handleCoreException("ERROR", "Image Descriptor Error", 
          ExceptionHandler.getNewCoreException("I/O Exception", e));
    } catch(NullPointerException e) {
      ExceptionHandler.handleCoreException("ERROR", "Image Descriptor Error", 
          ExceptionHandler.getNewCoreException("Null Pointer Exception", e));
    }
	mainPage = new NewProjectWizardPage("NewGoogleDataProjectPage1", "New Google Data Project",
	    "Create a new Google Data project.", image, templateConfigMap);
	addPage(mainPage);
  }

  
  /**
   * Instigates a new Google Data Project creation operation.
   * <p>This method is called when 'Finish' button is pressed in
   * the wizard. We will create an operation and run it
   * using wizard as execution context.
   */
  @Override
  public boolean performFinish() {
    WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
      
      @Override
	  public void execute(IProgressMonitor monitor) throws CoreException, 
	      OperationCanceledException {
        createGDataProject(monitor);
	  }
    };
	try {
	  getContainer().run(false, true, op);
	} catch (InvocationTargetException e) {
	  e.printStackTrace();
	  return false;
	} catch (InterruptedException e) {
	  e.printStackTrace();
	  return false;
    }
	    
    if(this.config != null) {
      BasicNewProjectResourceWizard.updatePerspective(this.config);
      BasicNewProjectResourceWizard.selectAndReveal(this.newGDataProject, 
          PlatformUI.getWorkbench().getActiveWorkbenchWindow());
    }
    return true;
  }
	
  /**
   * Handles the creation of a new Google Data Project with the selected template in the workspace.
   * @param monitor the progress monitor
   * @throws CoreException
   *         if anything goes wrong
   */
  private void createGDataProject(IProgressMonitor monitor) throws CoreException {
    
    Vector<IClasspathEntry> dependencyClasspaths = new Vector<IClasspathEntry>();
	int totalNoOfTicks = 10000;
    monitor.beginTask("Creating a new Google Data Project", totalNoOfTicks);

    // get the template config of the selected template
    selectedTemplateConfig = templateConfigMap.get(mainPage.getSelectedTemplateName());
    
    // handle dependencies
    handleExtDependencyOptions(monitor, (int) (0.4 * totalNoOfTicks)); //40% work
    dependencyClasspaths = getDependencyClasspaths();
	   
    String templateContent = new String();
    try {
      ProjectCreator jetGateWay = new ProjectCreator(mainPage.getProjectName(),
          selectedTemplateConfig);
      templateContent = jetGateWay.generate(
          new SubProgressMonitor(monitor, (int) (0.4 * totalNoOfTicks)), dependencyClasspaths, 
          getErrorMessage()); // 40% work
      IFile templateFile = jetGateWay.addTemplateToProject(mainPage.getProjectName() + "/src", 
          templateContent, monitor, (int) (0.05 * totalNoOfTicks)); // 5% work
      jetGateWay.openFile(templateFile, getShell(), monitor, (int) (0.15 * totalNoOfTicks)); // 15% work
    } catch(CoreException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException(e.getMessage(), e);
    } finally {
      monitor.done();
    }
  }
  
  
  /**
   * Takes action according to the dependency option selected by the user.
   * @param monitor the progress monitor
   * @param noOfTicks the number of ticks allocated
   */
  private void handleExtDependencyOptions(IProgressMonitor monitor, int noOfTicks) {
    switch(mainPage.getSelectedDependencyOption()) {
      case EXT_DEPENDENCY_NOT_DESIRED:
        extDependencyDesired = false;
        break;
        
      case DOWNLOAD_EXT_DEPENDENCIES:
        extDependencyPath = mainPage.getDownloadDependencyDirText() + "/";
        extDependencyDesired = true;
        extDependencyPath += "/GoogleDataExtDependencies/";
        if(selectedTemplateConfig.isExtDependencyRequired()) {
          try {
            dependencyConfigList = DependencyConfigParser.getConfigElements();
            DependencyManager.downloadDependencies(dependencyConfigList, selectedTemplateConfig, 
                extDependencyPath, monitor, noOfTicks);
          } catch(CoreException e) {
            dependencyParsingError = true;
            ExceptionHandler.handleCoreException("ERROR", "Dependency Error", e);
          }
        }
        break;
        
      case LOCATE_EXT_DEPENDENCIES:
        extDependencyDesired = true;
        extDependencyPath = mainPage.getExtDependencyDirText() + "/";
        break;
        
      default:
    }
  }
  
  /**
   * @return the required dependecny classpaths
   */
  private Vector<IClasspathEntry> getDependencyClasspaths() {
    HashSet<IClasspathEntry> entries = new HashSet<IClasspathEntry>();
    int noOfExtDependenciesFound = 0;
    int noOfGoogleDataLibsFound= 0;
    // Get the external dependency classpaths
    try {
      if(selectedTemplateConfig.isExtDependencyRequired() && extDependencyDesired 
          && !dependencyParsingError) {
        entries.addAll(DependencyManager.getExtDependencies(extDependencyPath,
            selectedTemplateConfig));
        noOfExtDependenciesFound = entries.size();
        foundAllExtDependencies = (noOfExtDependenciesFound >=
            selectedTemplateConfig.getExtDependencies().size());
      } else {
        foundAllExtDependencies = false;
      }
    } catch(CoreException e) {
      e.printStackTrace();
      ExceptionHandler.handleCoreException("ERROR", "External dependency path not found!", e);
    }
    
    // Get the Google Data lib paths
    javaClientLibPath = mainPage.getJavaClientLibPath() + "/";
    try {
      entries.addAll(DependencyManager.getGoogleDataDependencies(
          javaClientLibPath,selectedTemplateConfig));
      noOfGoogleDataLibsFound = entries.size() - noOfExtDependenciesFound;
      foundAllGoogleDataDependencies = (noOfGoogleDataLibsFound == 
          selectedTemplateConfig.getDependencyCount());
    } catch(CoreException e) {
      ExceptionHandler.handleCoreException("ERROR", "Google Data Java Client Lib path not found!", e);
    }
        return new Vector<IClasspathEntry>(Arrays.asList(
        entries.toArray(new IClasspathEntry[entries.size()])));
  }
  
  /**
   * Returns an error message if some of the required dependencies are not found.
   * @return the error message
   */
  private String getErrorMessage() {
    String errorMessage = new String();
    if(!foundAllGoogleDataDependencies) {
      errorMessage = "Could not find all the Google Data dependencies required for" +
            " this template from the directory provided.\n";
    }
    if(!foundAllExtDependencies && 
        selectedTemplateConfig.isExtDependencyRequired()) {
      if(errorMessage.length() > 0) {
        errorMessage += "\n";
      }
      errorMessage += "Could not find all the external dependencies. " 
                    + "\nThere are following possibilities:\n"
                    + "1. Either a wrong directory for external dependencies was provided\n"
                    + "2. Or there was an error downloading the files\n"
                    + "3. Or some files are missing from the directory.\n";
    }
    if(errorMessage.length() > 0) {
      errorMessage += "\nYou may not be able to execute this template (or your "
                    + "future application) without adding the required dependencies.";
    }
    return errorMessage;
  }
	
  /**
   * Accepts the selection in the workbench to see if
   * we can initialize from it.
   * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
   */
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IExecutableExtension
   *#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
   */
  public void setInitializationData(IConfigurationElement config,
      String propertyName, Object data) {
    this.config = config;
  }
   
  /**
   * @return    a the absolute location of the icon image 
   *            or null if an exception occurs
   */
  private String getImageLocation() throws IOException, NullPointerException {
    String pluginId = Activator.PLUGIN_ID;
    Bundle core = Platform.getBundle(pluginId);
    String imageLocation = new String();
    imageLocation = FileLocator.toFileURL((FileLocator.find(core, 
        new Path("icons/icon.png"),null))).toString();
    imageLocation = imageLocation.substring("file:".length());
    return imageLocation;
  }
}