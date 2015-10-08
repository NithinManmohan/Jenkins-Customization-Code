# Jenkins-Customization-Template
Template to customize jenkins functionalities

- Updated AbstractModelObject with the method getCurrentlyLoggedInUser to retrieve the userid of the user who is currently logged in, Location - core\src\main\java\hudson\model\AbstractModelObject.java.
- Edited the buildCaption.jelly to check if the user id of the loggedin user is same as the userid of the user who triggered the build, Location - core\src\main\resources\lib\hudson\buildCaption.jelly
- Modified executors to remove the stop-button div, Location - core\src\main\resources\lib\hudson\executors.jelly.
