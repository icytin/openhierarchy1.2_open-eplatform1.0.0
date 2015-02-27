#Get started with OpenHierarchy and the Open ePlatform

This is a fork of the OpenHierarchy by Nordic Peak and the platform on top - Open-ePlatform.
* http://openhierarchy.org/
* https://github.com/Sundsvallskommun/Open-ePlatform
* http://www.oeplatform.org/

The purpose with this project is to simplify the steps needed to get started working with this platform.

## Set up the project
1. Eclipse (Tested with Juno)
2. Open Perspective SVN Repository Exploring (Window -> Open Perspective -> Other..)
3. Add this project as reference https://github.com/icytin/openhierarchy1.2_open-eplatform1.0.0
4. Right click the trunk and choose checkout as.. then select "find projects in the children of the selected resource" and finish
5. In the next step choose "Check out as a projects into workspace", also make sure all projects are selected. Hit the finish button
6. Lay back and relax...
7. Open the Java-Perspective if you´re not already there

### Working sets
To categorize the projects - OH by them self and ePlatform in a different place.. You may create working sets, from the package explorer, mark those projects you want to gather then click the "arrow down" and choose "configure working sets...".

##### Open-ePlatform
The following projects are included in the Open-ePlatform: AuthifyClient, BaseMapQuery, demo.oeplatform.org, FlowEngine, FlowInstancePDFGenerator, mapclient-riges, MinimalUserSAMLAdapter, MultiGeometryMapQuery, PBLKnowledgeBank, PUDMApQuery, PUDQuerym SearchLMRMI, SinglePolygonMapQuery

##### OH
All other projects but the ProjectTemplate, which is a custom template project

### JRE
1. Window -> Preference -> Java -> Installed JRE
2. You may have jre7 installed and marked. But you also need jre6 for some of the projects. Download and install it, click "Add.." locate the jre6 folder on your disk. The jre6 installer can be found in the ProjectTemplate.

### Server Configurations
1. Set up TomCat Server. Window -> Show View -> Other -> Servers
2. Click "new server wizard"
3. Choose "Tomcat v6.0 Server". If you are missing tomcat server. You can find a .zip file inside the ProjectTemplate. Just extract the content under C:/ for example. Then locate it in the wizard..
4. You may locate the Runtime Environments from: Window -> Preferences -> Server -> Runtime Environments
5. Make sure the demo.oeplatform.org project is added to the TomCat Server
6. Also make sure that you have the mysql-connector placed in your Apache server. In the ProjectTemplate locate the mysql-connector-java-5.1.5-bin.jar and copy it to your Apache in the bin-directory.

### The database

#####Install some db-environment. For example HeidiSQL.

1. File -> Session Manager -> New
2. networktype: MySQL (TCP/IP), IP: 127.0.0.1, user: root, password: root, port: 3306
3. Click Open
4. Mark root@localhost -> Right click and choose: Create New -> Database. Name it "flowengine-system"
5. Select the database and add new query window
6. Paste the SQL-code from the "Open ePlatForm 1.9.ansi.sql". Located in FlowEngine -> docs -> db
7. Execute the script
9. Database is ready! Also check the config.xml in demo.oeplatform.org -> WebContent -> WEB-INF. Make sure the Url is pointing to the db you´ve just added, in this example "<Url>jdbc:mysql://127.0.0.1:3306/flowengine-system</Url>"

### Start the demo

1. Check the port number by double click the tomcat6 Server in the Servers view. Check the HTTP-port under Ports.
2. Start the TomCat Server
3. Navigate to the site(same name as the project name) in this example: http://localhost:8080/demo.oeplatform.org/
4. Sign in using the default login admin\admin

## Source references

##### This is not everything. Additional features may be added from OH:
* svn://unlogic.se/openhierarchy
* svn://unlogic.se/utils

##### Or if you´d like to create your own project from scratch. This is what you need:
* https://github.com/Sundsvallskommun/Open-ePlatform
* svn://unlogic.se/openhierarchy
* svn://unlogic.se/purecaptcha
* svn://unlogic.se/utils

## FAQ

##### How do I resolve XSL-errors?
1. Window -> Preferences -> XML -> XSL -> Validation. Switch all to ignore and click apply

##### How to handle "The type javax.servlet.http.HttpServletRequest cannot be resolved."?
Make sure TomCat6 is installed in your solution and configured for that project correctly.

##### Cannot resolve getParentLogger of the OpenHierarchy-project?
Check project properties -> Java Build Path -> Libraries. Check if you have jre7, in this case you may try to downgrade to jre6. You may also solve it by add this function stub.

##### How can I get information about the .xml for the current page?
For this demo project go to: file:///PATH_TO_MYPROJECT/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/demo.oeplatform.org/WEB-INF/module_xmldebug.xml

##### What about logging runtime errors?
Look in the same directory as above:
PATH_TO_YOUR_PROJECT_DIRECTORY\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\demo.oeplatform.org\WEB-INF

###### ...

###### Please feel free to contribute!


