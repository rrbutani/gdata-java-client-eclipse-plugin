// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.GData.devtools.eclipse;

import java.util.List;

/**
 * A <code>FileTemplateConfig</code> object holds all the information pertaining 
 * to a particular template.
 * 
 * @author kunalshah@google.com (Kunal Shah)
 */

public class FileTemplateConfig {

  private String name;
  private String fileName;
  private List<String> dependencies;
  private int dependencyCount;
  private boolean isThirdPartyDependencyRequired;
  private List<String> thirdPartyDependencyNames;
  private List<String> thirdPartyDependencies;
  private String description;
  private String feedUrl;
  private List<String> imports;
  private List<String> args;
  private String serviceClass;
  private String feedClass;
  private String entryClass;
  
  protected FileTemplateConfig() {
    
  }
  
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  
  /**
   * @param name the name to set
   */
  void setName(String name) {
    this.name = name;
  }
  
  /**
   * @return the fileName
   */
  public String getFileName() {
    return fileName;
  }
  
  /**
   * @param fileName the fileName to set
   */
  void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  /**
   * @return the dependency
   */
  public List<String> getDependencies() {
    return dependencies;
  }
  
  /**
   * @param dependency the dependency to set
   */
  void setDependencies(List<String> dependency) {
    this.dependencies = dependency;
  }
  
  /**
   * @return the dependencyCount
   */
  public int getDependencyCount() {
    return dependencyCount;
  }
  
  /**
   * @param dependencyCount the dependencyCount to set
   */
  void setDependencyCount(int dependencyCount) {
    this.dependencyCount = dependencyCount;
  }
  
  /**
   * @return the isThirdPartyDependencyRequired
   */
  public boolean isThirdPartyDependencyRequired() {
    return isThirdPartyDependencyRequired;
  }
  
  /**
   * @param isThirdPartyDependencyRequired the isThirdPartyDependencyRequired to set
   */
  void setThirdPartyDependencyRequired(
      boolean isThirdPartyDependencyRequired) {
    this.isThirdPartyDependencyRequired = isThirdPartyDependencyRequired;
  }
  
  /**
   * @return the thirdPartyDependencyNames
   */
  public List<String> getThirdPartyDependencyNames() {
    return thirdPartyDependencyNames;
  }

  /**
   * @param thirdPartyDependencyNames the thirdPartyDependencyNames to set
   */
  void setThirdPartyDependencyNames(List<String> thirdPartyDependencyNames) {
    this.thirdPartyDependencyNames = thirdPartyDependencyNames;
  }

  /**
   * @return the thirdPartyDependencies
   */
  public List<String> getThirdPartyDependencies() {
    return thirdPartyDependencies;
  }

  /**
   * @param thirdPartyDependencies the thirdPartyDependencies to set
   */
  void setThirdPartyDependencies(List<String> thirdPartyDependencies) {
    this.thirdPartyDependencies = thirdPartyDependencies;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * @param description the description to set
   */
  void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * @return the feedUrl
   */
  public String getFeedUrl() {
    return feedUrl;
  }
  
  /**
   * @param feedUrl the feedUrl to set
   */
  void setFeedUrl(String feedUrl) {
    this.feedUrl = feedUrl;
  }
  
  /**
   * @return the imports
   */
  public List<String> getImports() {
    return imports;
  }
  
  /**
   * @param imports the imports to set
   */
  void setImports(List<String> imports) {
    this.imports = imports;
  }
  
  /**
   * @return the args
   */
  public List<String> getArgs() {
    return args;
  }
  
  /**
   * @param args the args to set
   */
  void setArgs(List<String> args) {
    this.args = args;
  }

  /**
   * @return the serviceClass
   */
  public String getServiceClass() {
    return serviceClass;
  }

  /**
   * @param serviceClass the serviceClass to set
   */
  void setServiceClass(String serviceClass) {
    this.serviceClass = serviceClass;
  }

  /**
   * @return the feedClass
   */
  public String getFeedClass() {
    return feedClass;
  }

  /**
   * @param feedClass the feedClass to set
   */
  void setFeedClass(String feedClass) {
    this.feedClass = feedClass;
  }

  /**
   * @return the entryClass
   */
  public String getEntryClass() {
    return entryClass;
  }

  /**
   * @param entryClass the entryClass to set
   */
  void setEntryClass(String entryClass) {
    this.entryClass = entryClass;
  }
  
}
