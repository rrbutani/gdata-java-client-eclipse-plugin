// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.gdata.devtools.eclipse;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * A utility class to parse the Templates configuration file and return a 
 * collection of all the template configuration elements. 
 * 
 *  @author kunalshah@google.com (Kunal Shah)
 */

public class FileTemplateConfigParser {
  
  private static final String PARENT_TAG = "TEMPLATE";
  private static final String NAME_ATTRIBUTE = "NAME";
  private static final String FILENAME_TAG = "FILE_NAME";
  private static final String DEPENDENCY_TAG = "DEPENDENCIES";
  private static final String DEPENDENCY_COUNT_TAG = "DEPENDENCY_COUNT";
  private static final String THIRD_PARTY_TAG = "THIRD_PARTY_DEPENDENCIES";
  private static final String ISREQUIRED_ATTRIBUTE = "ISREQUIRED";
  private static final String DESCRIPTION_TAG = "DESCRIPTION";
  private static final String FEED_URL_TAG = "FEED_URL";
  private static final String IMPORTS_TAG = "IMPORTS";
  private static final String ARGS_TAG = "ARGS";
  
  private static final String TEMPLATE_XML_FILE = "templates.xml";
  
  private static DocumentBuilderFactory factory;
  private static DocumentBuilder builder;
  private static Document doc;
  
  private static HashMap<String, Integer> tagMap;
  
  private static void initializeTagMap() {
    tagMap = new HashMap<String, Integer>();
    tagMap.put(FILENAME_TAG, 0);
    tagMap.put(DEPENDENCY_TAG, 1);
    tagMap.put(DEPENDENCY_COUNT_TAG, 2);
    tagMap.put(THIRD_PARTY_TAG, 3);
    tagMap.put(DESCRIPTION_TAG, 4);
    tagMap.put(FEED_URL_TAG, 5);
    tagMap.put(IMPORTS_TAG, 6);
    tagMap.put(ARGS_TAG, 7);
  }
  
  private static String getTemplateDirectory() throws NullPointerException, IOException, Exception {
    String pluginId = Activator.PLUGIN_ID;
    Bundle core = Platform.getBundle(pluginId);
    String templateDir = new String();
    String relativeDir = FileTemplateConfigParser.class.getPackage().getName().replace('.', '/');
    URL imageUrl = FileLocator.find(core, new Path(relativeDir),null);
    if(imageUrl == null){
      imageUrl = FileLocator.find(core, new Path("src/"+relativeDir),null);
    }
    templateDir = FileLocator.toFileURL(imageUrl).toString();
    templateDir = templateDir.substring("file:".length());
    return templateDir;
  }
  
  
  /**
   * Parses the template configuration file and returns a Map of template configuration
   * elements. The Map has the template name as the key and the configuration element as the object. 
   * 
   * @return  A Map of template configuration elements
   * @throws CoreException
   *         If anything goes wrong 
   */
  public static Map<String, FileTemplateConfig> getConfigElements() throws CoreException {
    FileTemplateConfigParser.initializeTagMap();
    Map<String, FileTemplateConfig> templateConfigsMap = 
      new HashMap<String, FileTemplateConfig>();
    factory = DocumentBuilderFactory.newInstance();
    try {
      builder = factory.newDocumentBuilder();
      doc = builder.parse(getTemplateDirectory()+TEMPLATE_XML_FILE);
      NodeList templateNodeList = doc.getElementsByTagName(PARENT_TAG);
      for(int i=0; i<templateNodeList.getLength(); i++) {
        Node template = templateNodeList.item(i);
        NodeList childNodeList = template.getChildNodes();
        FileTemplateConfig templateConfig = new FileTemplateConfig();
        // set Name
        templateConfig.setName(template.getAttributes().
            getNamedItem(NAME_ATTRIBUTE).getTextContent().trim());
        for(int j=1; j<childNodeList.getLength(); j+=2) {
          Node child = childNodeList.item(j);
          switch(tagMap.get(child.getNodeName())) {
            case 0: 
              templateConfig.setFileName(child.getTextContent().trim());
              break;
            case 1:
              List<String> dependenciesList = new ArrayList<String>();
              NodeList dependencyChildren = child.getChildNodes();
              for(int k=1; k<dependencyChildren.getLength(); k+=2) {
                dependenciesList.add(dependencyChildren.item(k).getTextContent().trim());
              }
              templateConfig.setDependencies(dependenciesList);
              break;
            case 2:
              templateConfig.setDependencyCount(Integer.parseInt(child.getTextContent().trim()));
              break;
            case 3:
              boolean isRequired = child.getAttributes().getNamedItem(ISREQUIRED_ATTRIBUTE).
                  getTextContent().trim().equalsIgnoreCase("true");
              templateConfig.setThirdPartyDependencyRequired(isRequired);
              if(isRequired) {
                List<String> thirdPartyDependencyList = new ArrayList<String>();
                List<String> thirdPartyDependencyNameList = new ArrayList<String>();
                NodeList thirPartyDependencyChildren = child.getChildNodes();
                for(int k=1; k<thirPartyDependencyChildren.getLength(); k+=2) {
                  Node thirdPartyDependencyChild = thirPartyDependencyChildren.item(k);
                  thirdPartyDependencyNameList.add(thirdPartyDependencyChild.getAttributes().
                      getNamedItem(NAME_ATTRIBUTE).getTextContent().trim());
                  thirdPartyDependencyList.add(thirdPartyDependencyChild.
                      getTextContent().trim());
                }
                templateConfig.setThirdPartyDependencyNames(thirdPartyDependencyNameList);
                templateConfig.setThirdPartyDependencies(thirdPartyDependencyList);
              }
              break;
            case 4:
              templateConfig.setDescription(child.getTextContent().trim());
              break;
            case 5:
              templateConfig.setFeedUrl(child.getTextContent().trim());
              break;
            case 6:
              List<String> importsList = new ArrayList<String>();
              NodeList importChildren = child.getChildNodes();
              for(int k=1; k<importChildren.getLength(); k+=2) {
                importsList.add(importChildren.item(k).getTextContent().trim());
              }
              templateConfig.setImports(importsList);
              break;
            case 7:
              List<String> argsList = new ArrayList<String>();
              NodeList argChildren = child.getChildNodes();
              for(int k=1; k<argChildren.getLength(); k+=2) {
                argsList.add(argChildren.item(k).getTextContent().trim());
              }
              templateConfig.setArgs(argsList);
              break;
            default:
          }
        }
        List<String> imports = templateConfig.getImports();
        for(int k=0;k<imports.size();k++) {
          String singleImport = imports.get(k);
          int lastDotIndex = singleImport.lastIndexOf('.');
          if(singleImport.contains("Service")) {
            templateConfig.setServiceClass(singleImport.substring(lastDotIndex+1));
          } else if(singleImport.contains("Feed")) {
            templateConfig.setFeedClass(singleImport.substring(lastDotIndex+1));
          } else if(singleImport.contains("Entry")) {
            templateConfig.setEntryClass(singleImport.substring(lastDotIndex+1));
          }
        }
        templateConfigsMap.put(templateConfig.getName(), templateConfig);
      }
    } catch(ParserConfigurationException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException(e.getMessage(),e);
    } catch(IOException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException(e.getMessage(),e);
    } catch(SAXException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException(e.getMessage(),e);
    } catch(NumberFormatException e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException(e.getMessage(),e);
    } catch(NullPointerException e) {
      ExceptionHandler.throwCoreException("Null Pointer Exception", e);
    } catch(Exception e) {
      e.printStackTrace();
      ExceptionHandler.throwCoreException(e.getMessage(),e);
    }
    return new TreeMap<String, FileTemplateConfig>(templateConfigsMap);
  }
  
}
