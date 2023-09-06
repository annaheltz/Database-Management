In order to run the BeSocial Application, a BeSocial object must be created. If you would like it to run as an application, you must create the object like: 
- BeSocial application = new BeSocial(userName, password, false);

the userName is a string corresponding to your datagrip username, and the password is a string corresponding to your datagrip password. False is a boolean that is false because we are not manually testing the application. Therefore, if you would like to test the application, you must do:
- BeSocial tester = new BeSocial(userName, password, true);

Where userName and password correspond to your datagrip username and password and true is a boolean meaning that we do not want the application to run as it would to a user, we would just like to test the application.

These BeSocial objects are both found in the Driver.java file.

There are two functions that make our application easy to use by the user. These are applicationNoUserLoggedIn() and applicationUserLoggedIn(). If the user is logged in, applicationUserLoggedIn() will run and show the user what actions that can take, like sending a message and other functions. If the user is not logged in, they can only do 2 things, login or exit, so applicationNoUserLoggedIn() takes care of that.

That basically sums up our application. The functions are implemented as stated in the project pdf.

Our schema, triggers, and sample data for phase 2 are inside the folder named phase2. This folder also has our code for the driver.java and besocial.java. 

Happy socializing!
