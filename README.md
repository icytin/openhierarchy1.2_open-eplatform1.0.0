#Get started with OpenHierarchy and the Open ePlatform

## Set up the project
1. Eclipse (Tested with Juno)
2. Open Perspective SVN Repositories (Window -> Open Perspective -> Other..)
3. Add this project as reference https://github.com/icytin/openhierarchy1.2_open-eplatform1.0.0
4. Right click the trunk and choose checkout as.. then select "find projects in the children of the selected resource" and finish
5. In the next step choose "Check out as a projects into workspace", also make sure all projects are selected. Hit the finish button
6. Open the Java-Perspective if you´re not already there

### JRE
1. Window -> Preference -> Java -> Installed JRE
2. You should have jre7 installed and marked
3. You also need jre6 for some of the projects. Install it, click "Add.." locate the jre6 folder on your disk.

### Server Configurations
1. Set up TomCat Server. Window -> Show View -> Other -> Servers
2. Click "new server wizard"
3. Choose "Tomcat v6.0 Server". If you are missing tomcat server. You can find a .zip file inside the ProjectTemplate. Just extract the content under C:/ for example. Then locate it in the wizard..
4. You may locate the Runtime Environments from: Window -> Preferences -> Server -> Runtime Environments


### The database
..

## FAQ

#### How do I resolve XSL-errors?
1. Window -> Preferences -> XML -> XSL -> Validation. Switch all to ignore and click apply

#### How to resolve "The type javax.servlet.http.HttpServletRequest cannot be resolved."
1. Right click the project and go to properties. Check if the TomCat is checked. Clean and rebuild project.

