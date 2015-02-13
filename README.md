#Get started with OpenHierarchy and the Open ePlatform

This is a fork of the OpenHierarchy by Nordic Peak and the on-top-platform Open-ePlatform.
* http://openhierarchy.org/
* https://github.com/Sundsvallskommun/Open-ePlatform
* http://www.oeplatform.org/

The purpose with this project is to simplify the steps needed to get started working with this framework and addons.

## Set up the project
1. Eclipse (Tested with Juno)
2. Open Perspective SVN Repository Exploring (Window -> Open Perspective -> Other..)
3. Add this project as reference https://github.com/icytin/openhierarchy1.2_open-eplatform1.0.0
4. Right click the trunk and choose checkout as.. then select "find projects in the children of the selected resource" and finish
5. In the next step choose "Check out as a projects into workspace", also make sure all projects are selected. Hit the finish button
6. Lay back and relax...
7. Open the Java-Perspective if you´re not already there

### Working sets
To place the projects categorized - OH by them self and ePlatform in a different place.. You may create working sets, from the package explorer, mark those projects you want to gather then click the "arrow down" and choose "configure working sets...".

##### Open-ePlatform
The following projects are included in the Open-ePlatform: AuthifyClient, BaseMapQuery, demo.oeplatform.org, FlowEngine, FlowInstancePDFGenerator, mapclient-riges, MinimalUserSAMLAdapter, MultiGeometryMapQuery, PBLKnowledgeBank, PUDMApQuery, PUDQuerym SearchLMRMI, SinglePolygonMapQuery

##### OH
All other projects but the ProjectTemplate, which is a custom template project

### JRE
1. Window -> Preference -> Java -> Installed JRE
2. You should have jre7 installed and marked
3. You also need jre6 for some of the projects. Download and install it, click "Add.." locate the jre6 folder on your disk. The jre6 installer is located in the ProjectTemplate.

### Server Configurations
1. Set up TomCat Server. Window -> Show View -> Other -> Servers
2. Click "new server wizard"
3. Choose "Tomcat v6.0 Server". If you are missing tomcat server. You can find a .zip file inside the ProjectTemplate. Just extract the content under C:/ for example. Then locate it in the wizard..
4. You may locate the Runtime Environments from: Window -> Preferences -> Server -> Runtime Environments


### The database

#####Install some db-environment. For example HeidiSQL.

1. File -> Session Manager -> New
2. networktype: MySQL (TCP/IP), IP: 127.0.0.1, user: root, password: root, port: 3306
3. Click Open
4. Mark root@localhost -> Right click and choose: Create New -> Database. Name it "flowengine-system"
5. Select the database and add new query window
6. Paste the SQL-code from the "Open ePlatForm 1.9.ansi.sql". Located in FlowEngine -> docs -> db
7. Execute
8. Make sure the demo.oeplatform.org project is added to the TomCat Server.
9. Also check the config.xml in demo.oeplatform.org -> WebContent -> WEB-INF. Make sure the Url is pointing to the db you´ve just added "<Url>jdbc:mysql://127.0.0.1:3306/flowengine-system</Url>"

## FAQ

#### How do I resolve XSL-errors?
1. Window -> Preferences -> XML -> XSL -> Validation. Switch all to ignore and click apply

#### How to resolve "The type javax.servlet.http.HttpServletRequest cannot be resolved."?
... uhmm

#### What does "The project was not built since its build path is incomplete. Cannot find the class file for javax.servlet.http.HttpServletRequest..." means?
Check properties of the project and make sure the tomcat is selected for that project

#### Cannot resolve getParentLogger of the OpenHierarchy-project?
Check project properties -> Java Build Path -> Libraries. Check if you have jre7, in this case you may try to downgrade to jre6.

....


