# Introduction #

This page lists out the instructions on how to create the Eclipse plug-in project using the source code in the [project svn](http://code.google.com/p/gdata-java-client-eclipse-plugin/source/browse/#svn) for working on it. Just follow the 9 step procedure to start playing with it.

# Instructions #

Note: Following instructions are for Eclipse v3.3.2. For other versions, they may differ a bit or may be not.

**Step 1:** Checkout the project source as mentioned in the [source tab](http://code.google.com/p/gdata-java-client-eclipse-plugin/source/checkout). Let's say you checked the source out to $HOME/gdata-java-client-eclipse-plugin.

**Step 2:** Now, open Eclipse. Go to File --> New --> Project. You can see the following window. In that look out for 'Plug-in Project'. Select 'Plug-in Project' and click on 'Next' button.

![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/1.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/1.png)

**Step 3:** In the window which appears, we need to enter appropriate information.

  * Project name: Let's say 'Google\_Data\_Plugin'.
  * Project settings: Make sure they look the same as in the picture below i.e. 'Create a Java Project' should be checked, the 'Source folder' should say 'src' and 'Output folder' can be named anything you like but 'bin' is more appropriate.
  * Target Platform: 'Eclipse version' should be checked and value should be '3.3' or above.

Now, hit 'Next'.

![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/2.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/2.png)

**Step 4:** You can see the window shown below. Let 'Plug-in Properties' be as it is since it will eventually get overwritten. For 'Plug-in Options' uncheck both the options. For 'Rich Client Application' check 'No' and hit 'Finish' button.

![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/3.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/3.png)

**Step 5:** You may be asked if you want to open the 'Plug-in Perspective'. If so, hit 'Yes'.

**Step 6:** Now, after the project is created, right-click on the project folder named 'Google\_Data\_Plugin' (if that's what you have named the project) in the 'Package Explorer' and click on 'Import' as shown below.

![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/4.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/4.png)

**Step 7:** On this page, expand the folder named 'General' and select 'File System' as shown below and then hit 'Next'.

![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/5.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/5.png)

**Step 8:**

  * For the first directory field, hit on the 'Browse' button to browse upto $HOME/gdata-java-client-eclipse-plugin/, select folder named 'Google Data Plugin' and hit OK.
  * Now, check 'Google Data Plugin' as shown in the picture below.
  * Let's jump to options. In 'Options', check 'Overwrite exisitng resources without warning' and 'Create selected folders only'.
  * Hit 'Finish'.

![http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/6.png](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/images/ProjectFrmSrc/6.png)

**Step 9:** Now the plug-in project is created in Eclipse. To run it as a plug-in, right-click on the project folder in the 'Package Explorer' and go to Run As --> Eclipse Application. A new instance of Eclipse will be opened and you can test the plug-in there.

Now, you are ready to work on the plug-in your way. Good luck!