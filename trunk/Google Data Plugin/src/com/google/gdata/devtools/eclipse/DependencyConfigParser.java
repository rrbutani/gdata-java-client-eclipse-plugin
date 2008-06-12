// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.gdata.devtools.eclipse;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * A utility class to parse the Third Party Dependency configuration file and return a 
 * collection of all the dependency configuration elements. 
 * 
 *  @author kunalshah@google.com (Kunal Shah)
 */
public class DependencyConfigParser {

  private static final String PARENT_TAG = "JAR";
  private static final String NAME_ATTRIBUTE = "NAME";
  private static final String URL_TAG = "URL";
  
  private static DocumentBuilderFactory factory;
  private static DocumentBuilder builder;
  private static Document doc;
  
  private static final String THIRD_PARTY_DEPENDENCIES_XML_URL = 
    "http://www.corp.google.com/~kunalshah/GDataPlugin/Library.xml";
  
  /**
   * Parses the Third Party Dependency configuration file and returns a List of 
   * dependency configuration elements. 
   * 
   * @return  A List of dependency configuration elements
   * @throws CoreException
   *         If anything goes wrong 
   */
  public static List<DependencyConfig> getConfigElements() throws CoreException {
    List<DependencyConfig> dependencyConfigList = new ArrayList<DependencyConfig>();
    factory = DocumentBuilderFactory.newInstance();
    try {
      builder = factory.newDocumentBuilder();
      doc = builder.parse(THIRD_PARTY_DEPENDENCIES_XML_URL);
      NodeList dependencyNodeList = doc.getElementsByTagName(PARENT_TAG);
      for(int i=0;i<dependencyNodeList.getLength(); i++) {
        Node dependency = dependencyNodeList.item(i);
        DependencyConfig dependencyConfig = new DependencyConfig();
        // set name
        dependencyConfig.setName(dependency.getAttributes().
            getNamedItem(NAME_ATTRIBUTE).getTextContent().trim());
        // set url
        Node urlNode = dependency.getChildNodes().item(1);
        if(urlNode.getNodeName().equalsIgnoreCase(URL_TAG)){
          dependencyConfig.setUrl(urlNode.getTextContent().trim());
        }
        dependencyConfigList.add(dependencyConfig);
      }
    } catch(ParserConfigurationException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException(e.getMessage(), e);
    } catch(IOException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException(e.getMessage(), e);
    } catch(SAXException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException(e.getMessage(), e);
    } catch(NullPointerException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException("Null Pointer Exception", e);
    }
    return dependencyConfigList;
  }
}
