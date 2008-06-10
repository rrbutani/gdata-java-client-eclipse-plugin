// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.GData.devtools.eclipse;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchPlugin;

/**
 * A utility class to provide exception handling.
 *  
 * @author kunalshah@google.com (Kunal Shah)
 */

public class ExceptionHandler {

  /**
   * Throws a new <code>CoreException</code> generated from the given exception.
   * 
   * @param message the message for the <code>Status</code> object
   * @param exception the exception to be converted to <code>CoreException</code>
   * @throws CoreException
   */
  public static void throwCoreException(String message, Exception exception) throws CoreException {
    IStatus status =
      new Status(IStatus.ERROR, "com.google.GData", IStatus.OK, message, exception);
    throw new CoreException(status);
  }
  
  /**
   * Returns a new <code>CoreException</code> generated from the given exception.
   * 
   * @param message the message for the <code>Status</code> object
   * @param exception the exception to be converted to <code>CoreException</code>
   * @return  A <code>CoreException</code> generated from the given exception
   */
  public static CoreException getNewCoreException(String message, Exception exception) {
    IStatus status =
      new Status(IStatus.ERROR, "com.google.GData", IStatus.OK, message, exception);
    return new CoreException(status);
  }
  
  /**
   * Opens an <code>ErrorDialog</code> with an error message and logs the error 
   * to the default log.
   * 
   * @param title the title of the <code>ErrorDialog</code>
   * @param message the message to be displayed in the <code>ErrorDialog</code>
   * @param coreException the <code>CoreException</code> to be logged
   */
  public static void handleCoreException(String title, String message, 
      CoreException coreException) {
    ErrorDialog.openError(Workbench.getInstance().getActiveWorkbenchWindow().getShell(),
        title, message, coreException.getStatus());
    WorkbenchPlugin.log(coreException.getStatus());
  }
  
}
