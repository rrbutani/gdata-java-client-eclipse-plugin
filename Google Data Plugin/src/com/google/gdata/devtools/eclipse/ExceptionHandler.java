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


package com.google.gdata.devtools.eclipse;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchPlugin;


/**
 * A utility class to provide exception handling.
 */
public class ExceptionHandler {

  /**
   * Throws a new {@link CoreException} generated from the given exception.
   * 
   * @param message the message for the {@link Status} object
   * @param exception the exception to be converted to {@link CoreException}
   * @throws CoreException
   */
  public static void throwCoreException(String message, Exception exception) throws CoreException {
    IStatus status =
        new Status(IStatus.ERROR, "com.google.gdata", IStatus.OK, message, exception);
    throw new CoreException(status);
  }
  
  /**
   * Returns a new {@link CoreException} generated from the given exception.
   * 
   * @param message the message for the {@link Status} object
   * @param exception the exception to be converted to {@link CoreException}
   * @return  A {@link CoreException} generated from the given exception
   */
  public static CoreException getNewCoreException(String message, Exception exception) {
    IStatus status =
        new Status(IStatus.ERROR, "com.google.GData", IStatus.OK, message, exception);
    return new CoreException(status);
  }
  
  /**
   * Opens an error dialog with an error message and logs the error 
   * to the default log.
   * 
   * @param title the title of the error dialog
   * @param message the message to be displayed in the error dialog
   * @param coreException the {@link CoreException} to be logged
   */
  public static void handleCoreException(String title, String message, 
      CoreException coreException) {
    ErrorDialog.openError(Workbench.getInstance().getActiveWorkbenchWindow().getShell(),
        title, message, coreException.getStatus());
    WorkbenchPlugin.log(coreException.getStatus());
  }
  
}
