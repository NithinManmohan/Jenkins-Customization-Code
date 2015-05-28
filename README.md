# Jenkins-Customization-Template
Template to customize jenkins functionalities

- Updated AbstractModelObject with the method getCurrentlyLoggedInUser to retrieve the userid of the user who is currently logged in.
- Edited te buildCaption.jelly to check if the user id of the loggedin user is same as the userid of the user who triggered the build.
- Modified executors to remove the stop-button div.
