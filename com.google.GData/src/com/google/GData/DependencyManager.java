// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.GData;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;

/**
 * A utility class to provide various functionalities for the dependencies. 
 * 
 * @author kunalshah@google.com (Kunal Shah)
 */

public class DependencyManager {
  
  /**
   * Downloads the third party dependency jars required for the template defined by 
   * <code>TemplateConfig</code> element and according to the <code>List</code> of 
   * <code>DependencyConfig</code> elements. They will be downloaded to the local file 
   * system whose path is defined by <code>thirdPartyDependencyPath</code>. 
   * 
   * @param dependencyConfigList the List of third party dependency configurations
   * @param templateConfig the template configuration
   * @param thirdPartyDependencyPath the output local file system path
   * @param monitor the progress monitor
   * @param noOfTicks the number of ticks allocated
   * @throws CoreException
   *         If anything goes wrong while downloading
   */
  public static void downloadDependencies(List<DependencyConfig> dependencyConfigList, 
      FileTemplateConfig templateConfig, String thirdPartyDependencyPath, 
      IProgressMonitor monitor, int noOfTicks) throws CoreException {
    for(DependencyConfig dependencyConfig : dependencyConfigList) {
      List<String> dependencyRegexList = templateConfig.getThirdPartyDependencies();
      for(String dependencyRegex: dependencyRegexList) {
        dependencyRegex = ".+"+dependencyRegex.substring(1);
        if(dependencyConfig.getUrl().matches(dependencyRegex)) {
          monitor.subTask("(Downloading "+dependencyConfig.getName()+"...)");
          try {
            download(dependencyConfig.getUrl(),thirdPartyDependencyPath, monitor, noOfTicks/3);
          } catch(IOException e) {
            ExceptionHandler.throwCoreException(e.getMessage(), e);
          }
          
        }
      }
    }
  }
  
  /**
   * Returns a <code>HashSet</code> of all the Google Data Java Client libraries 
   * required by the template defined by <code>TemplateConfig</code> element found at 
   * path given by <code>javaClientLibPath</code>. 
   * 
   * @param javaClientLibPath the absolute path on the local file system
   * @param templateConfig the template configuration element
   * @return A <code>HashSet</code> of all the Google Data Java Client libraries 
   *         of type <code>IClassPathEntry</code>
   * @throws CoreException
   *         If anything goes wrong
   */
  public static HashSet<IClasspathEntry> getGoogleDataDependencies(String javaClientLibPath, 
      FileTemplateConfig templateConfig) throws CoreException {
    HashSet<IClasspathEntry> entries = new HashSet<IClasspathEntry>();
    try {
      IPath gDataJavaClientLibPath = new Path(javaClientLibPath);
      File gDataJavaClientLibDir = gDataJavaClientLibPath.toFile();
      File[] gDataJavaLibs = gDataJavaClientLibDir.listFiles();
      for(File gDataJavaLib: gDataJavaLibs){
        String gDataJavaLibName = gDataJavaLib.getName().trim();
        List<String> dependencyList = templateConfig.getDependencies();
        for(String dependency : dependencyList) {
          if(gDataJavaLib.isFile() && gDataJavaLibName.matches(dependency)) {
            gDataJavaClientLibPath = new Path(javaClientLibPath+gDataJavaLibName);
            if(entries.add(JavaCore.newLibraryEntry(gDataJavaClientLibPath, null, null))) {
            }
          }
        }
      }
    } catch(NullPointerException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException("Null Pointer Exception", e);
    }
        return entries;
  }
  
  /**
   * Returns a <code>HashSet</code> of all the Third party dependency libraries 
   * required by the template defined by <code>TemplateConfig</code> element found at 
   * path given by <code>thirdPartyDependencyPath</code>.  
   * 
   * @param thirdPartyDependencyPath the absolute path on the local file system
   * @param templateConfig the template configuration element
   * @return A <code>HashSet</code> of all the Third party dependency libraries 
   *         of type <code>IClassPathEntry</code>
   * @throws CoreException
   *         If anything goes wrong
   */
  public static HashSet<IClasspathEntry> getThirdPartyDependencies(
      String thirdPartyDependencyPath, FileTemplateConfig templateConfig) throws CoreException {
    HashSet<IClasspathEntry> entries = new HashSet<IClasspathEntry>();
    try {
      IPath thirdPartyLibPath = new Path(thirdPartyDependencyPath);
      File thirdPartyLibDir = thirdPartyLibPath.toFile();
      File[] extLibs = thirdPartyLibDir.listFiles();
      for(File extLib: extLibs){
        String extLibName = extLib.getName().trim();
        List<String> thirdPartyDependencyList = templateConfig.getThirdPartyDependencies();
        for(String dependency : thirdPartyDependencyList) {
          if(extLib.isFile() && extLibName.matches(dependency)) {
            thirdPartyLibPath = new Path(thirdPartyDependencyPath+extLibName);
            if(entries.add(JavaCore.newLibraryEntry(thirdPartyLibPath, null, null))) {
            }
          }
        }
      }
    } catch(NullPointerException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException("Null Pointer Exception", e);
    }
    
    return entries;
  }
  
  private static void download(String address, String dirName, 
      IProgressMonitor monitor, int noOfTicks) throws FileNotFoundException, IOException {
    
    int noOfTicksLeft; 
    String fileName = dirName+getFileNameFromAddress(address);
    File newDir= new File(dirName);
    File newFile= new File(fileName);
    OutputStream out = null;
    URLConnection connection = null;
    InputStream in = null;
    try {
      System.out.println(newDir.mkdir());
      monitor.worked((int)(0.1*noOfTicks));
      noOfTicksLeft = noOfTicks -(int)(0.1*noOfTicks); 
      System.out.println(newFile.createNewFile());
      monitor.worked((int)(0.1*noOfTicks));
      noOfTicksLeft -= (int)(0.1*noOfTicks);
      URL url = new URL(address);
      out = new BufferedOutputStream(new FileOutputStream(fileName));
      connection = url.openConnection();
      in = connection.getInputStream();
      System.out.println(connection.getContentLength());
      byte[] buffer = new byte[1024];
      int bytesRead;
      int totalWork = 0;
      float partialWork = 0;
      long bytesWritten = 0;
      while((bytesRead = in.read(buffer)) != -1){
        out.write(buffer, 0, bytesRead);
        bytesWritten += bytesRead;
        partialWork += ((float)bytesRead/connection.getContentLength())*noOfTicksLeft;
        if(partialWork>=10){
          totalWork += (int)partialWork;
          monitor.worked((int)partialWork);
          System.out.println("Float: "+partialWork+"\t"+"Int: "+(int)partialWork);
          partialWork = 0;
        }
      }
      System.out.println("Total Work: "+totalWork);
      System.out.println(fileName +"\t"+ bytesWritten);
    } catch(IOException e) {
      e.printStackTrace();
      throw e;
    } finally {
      try {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
        throw e;
      }
    }
  }

  private static String getFileNameFromAddress(String address) {
    
    int lastSlashIndex = address.lastIndexOf('/');
    if(lastSlashIndex >= 0 && lastSlashIndex < address.length()-1) {
      return address.substring(lastSlashIndex+1);
    } else {
      return null;
    }
  }
}
