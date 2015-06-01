# Introduction #

This page demonstrates how to create a new Google Data template of your choice in a few steps using the Google Data plug-in.

# Steps to create a new Google Data Template #

For demonstration purpose, we will create a new Google Documents template which uses the Google Documents List Data API.

  * **Step 1:** Go to File --> New --> Others

  * **Step 2:** A new project wizard will open up. Scroll until you find a folder named 'Google Data'. Expand it and click on 'Google Data Project' and then click next.

> ![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/WorkFlow/1.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/WorkFlow/1.png)

  * **Step 3:** A Google Data Project wizard page will open up which will ask for some information to create a new Google Data Project.

> ![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/WorkFlow/2.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/WorkFlow/2.png)

  * **Step 4:** We will now enter appropriate information.
    * Enter a valid project name. Say: My Document Project
    * Select the 'Documents' template from the template list. You can see a corresponding description of the selected template next to the template list.
    * Now, click on the 'Browse' button to select a directory containing the GData Java Client Lib files i.e. something like .../gdata/java/lib
    * Now, since the description says that the 'Documents' template will require external dependencies, we need to select either the 'Download dependencies to' option or the 'Select Location' option. If you do not have the required dependencies on your system, you can select to download them.

  * **Step 5:** After filling in the required information, click on 'Finish' button.

> ![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/WorkFlow/3.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/WorkFlow/3.png)

  * **Step 6:** A new Java project is created in the workspace with the project name you provided. The Documents template named 'Documents.java' will be opened for editing. It will have instructions on how to execute it. All the required dependencies are referenced as 'Referenced Libraries'. If you are a new Eclipse user, click [here](http://help.eclipse.org/help32/topic/org.eclipse.jdt.doc.user/tasks/tasks-executionArgs.htm) to know how to set the execution arguments.

> ![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/WorkFlow/4.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/WorkFlow/4.png)