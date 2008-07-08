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

/**
 * A {@link DependencyConfig} object holds all the information pertaining 
 * to a particular external dependency.
 * <p> Please refer to the 
 * <a href="http://gdata-java-client-eclipse-plugin.googlecode.com/svn/ExtDependencies.xml">
 * configuration file</a>.
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
