// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.gdata.devtools.eclipse;

/**
 * A <code>DependencyConfig</code> object holds all the information pertaining 
 * to a particular third party dependency.
 * 
 * @author kunalshah@google.com (Kunal Shah)
 */
public class DependencyConfig {

  private String name;
  private String url;
  
  protected DependencyConfig() {
    
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
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url the url to set
   */
  void setUrl(String url) {
    this.url = url;
  }
  
}
