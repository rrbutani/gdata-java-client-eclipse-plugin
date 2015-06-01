# Introduction #

This page demonstrates how to add a new template to the list of existing ones.


# Details #

To add a new template to the existing ones, you just need to edit the template configuration file. To see how it looks just click [here](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/trunk/Google%20Data%20Plugin/src/com/google/gdata/devtools/eclipse/templates.xml). The xml blob structure (part of the template configuration file) which you need to understand is below. It is explained using the configuration for Picasa Web Album template as an example.

```

  <TEMPLATE NAME="Picasa Web Albums"> <!-- Name of the template -->

    <FILE_NAME>GeneralTemplate.javajet</FILE_NAME> <!-- Name of JET template file to be used -->

    <DEPENDENCIES> <!-- Regex of dependency files from GData Java Client Library -->
    
      <DEPENDENCY>^gdata\-photos(\-meta)?\-[1-9]\.[0-9](\.[0-9])?\.jar$</DEPENDENCY>
      
      <DEPENDENCY>^gdata\-core\-[1-9]\.[0-9](\.[0-9])?\.jar$</DEPENDENCY>
      
      <DEPENDENCY>^gdata\-client(\-meta)?\-[1-9]\.[0-9](\.[0-9])?\.jar$</DEPENDENCY>
      
      <DEPENDENCY>^gdata\-media\-[1-9]\.[0-9](\.[0-9])?\.jar$</DEPENDENCY>
      
    </DEPENDENCIES>
    
    <DEPENDENCY_COUNT>6</DEPENDENCY_COUNT> <!-- Count of the the dependencies listed in DEPENDENCY elements -->

    <EXT_DEPENDENCIES ISREQUIRED="True"> <!-- Regex of the external dependencies -->
    
      <EXT_DEPENDENCY NAME="Activation.jar">^activation(\-[1-9](\.[0-9])?(\.[0-9])?)?\.jar$</EXT_DEPENDENCY>
   
      <EXT_DEPENDENCY NAME="Mail.jar">^mail(\-[1-9](\.[0-9])?(\.[0-9])?)?\.jar$</EXT_DEPENDENCY>
    
    </EXT_DEPENDENCIES>

    <DESCRIPTION>This template creates a console application which interacts with the Picasa Web Album Data API to retrieve a list of all Albums for a particular user.</DESCRIPTION> <!-- Short description of what this template does -->

    <FEED_URL>http://picasaweb.google.com/data/feed/api/user/"+args[0]+"?kind=album</FEED_URL> <!-- Feed URL to be used in the template -->

    <IMPORTS> <!-- Imports to be added in the template -->

      <IMPORT>com.google.gdata.client.photos.PicasawebService</IMPORT>

      <IMPORT>com.google.gdata.data.photos.GphotoEntry</IMPORT>

      <IMPORT>com.google.gdata.data.photos.UserFeed</IMPORT>

    </IMPORTS>

    <ARGS> <!-- Arguments required -->

      <ARG>username</ARG>

      <ARG>password</ARG>

    </ARGS>

  </TEMPLATE>
```

## Explanation ##

The use of most of the configuration elements is pretty straight forward. Only some need to be addressed here.

  * **FILE\_NAME** - Name of the JET template file to be used.
> If a JET template file other than [GeneralTemplate.javajet](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/trunk/Google%20Data%20Plugin/src/com/google/gdata/devtools/eclipse/GeneralTemplate.javajet) is used, please make sure to have the @jet directive attributes same as those in [GeneralTemplate.javajet](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/trunk/Google%20Data%20Plugin/src/com/google/gdata/devtools/eclipse/GeneralTemplate.javajet) i.e.

```
<%@ jet
    class = "TemporaryTemplateCreator"  
    imports="java.util.* com.google.gdata.devtools.eclipse.FileTemplateConfig"
    skeleton="TemplateSkeleton.skeleton"
%>
```

> You can add more `imports` if needed.
> Also, please note that the [GeneralTemplate.javajet](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/trunk/Google%20Data%20Plugin/src/com/google/gdata/devtools/eclipse/GeneralTemplate.javajet) takes in two arguments. Please make sure that the JET template file which you are adding takes in those two arguments (then it's upto you whether you want to use them in the template or not). They are shown below.

```
<% FileTemplateConfig templateConfig = (FileTemplateConfig)argument1; %>
<%String errorMessage = (String)argument2;%>
```

  * **DEPENDENCIES** - Regex of dependency files from GData Java Client Library.
> Note that these dependecies are the jar files from the GData Java Client Library. Please do not include any external dependencies.

  * **DEPENDENCY\_COUNT** - Count of the dependencies listed in the `DEPENDENCIES` element.
> Please note that this count is not just the count of `DEPENDENCY` elements. In our example, you can see that there are only 4 `DEPENDENCY` elements. But due to the regex pattern, you will have 6 dependencies referenced. So the value of `DEPENDENCY_COUNT` is 6.

  * **EXT\_DEPENDENCIES** - Regex of the external dependencies.
> Does this template require external dependencies? If yes, then set the `ISREQUIRED` attribute to `True` and set the regex of external dependencies as the value of the `EXT_DEPENDENCY` elements. If no, then set the `ISREQUIRED` attribute to `False` and do not add `EXT_DEPENDENCY` children elements at all.

  * **ARGS** - Arguments required
> The value of `ARG` elements can be used as a part of execution instruction displayed to the user in the generated templates.

## What you need to do? ##

What you need to do is just edit the [template configuration file](http://gdata-java-client-eclipse-plugin.googlecode.com/svn/trunk/Google%20Data%20Plugin/src/com/google/gdata/devtools/eclipse/templates.xml) to add a new template sub-tree with appropriate configuration data as explained above.

That's it! You are all set to add a new template.