SpaceInvadersClone
==================

## About this Project

This is my first game that uses the *[2D Game Framework](https://github.com/garyfredgiger/GameFramework.git)* that I have been developing and evolving since Sept 2013. This game is another clone of the classic arcade game Space Invaders. 
##A Few Preliminary Notes

This game is a work in progress. It started off as a prototype game to see how easy/hard it would be to create and develop a game usng my 2D Game Gramework. If you clone the repo and look over the code, some parts of it are ugly and need to be refactored. Other parts are still imcomplete. For the most part this game is 90% or so finished with regard to gameplay. I still need to add a few more features in order to make it 100% completed. These features include:

  * Sounds - There are currently no sounds in this game.
  * Power-ups - I plan on adding randomly spawned power-ups that give the player better weapons during game play
  * Tweak - existing difficulty features and add new ones that are dependent on the selected difficulty level and level progression.

But overall the game does run and is playable. To see a game that is more refined and polished, please checkout my second game *[here](https://github.com/garyfredgiger/GalacticWarReboot)*, which is a clone of the classic arcade game Asteroids.

Also note that even though my intention it to release this game and corresponding framework as an Open Source project, I have yet to choose a specific open source license under which to release it.

###Goal for this Game

My goal it to release this game so that it can be played within a browser (e.g., as an Applet/JApplet or using Java Web Start). Currently the only way to play this game is by cloning this project and building it and running it on your local machine as a Java Application. Below in a later section there are detailed instructions on how to setup this project on your local machine.

###Become a Contributor

This game was only tested on my laptop with Ubuntu vesion 12.10 within the Eclipse IDE running it as a Java application. If you have expereince with deploying Java software as Applet/JApplets, using Java Web Start and/or simply want to become a tester and you are looking for something to do in your free time, become a contributor. What is in it for you? Pride, a resume builder and you can brag to your circle of friends that you are involevd with yet another open source project.

###Debugging

Since this game is still in development, there is a debugging features that can be enabled. During game play press *SHIFT* + *~* (tilda) to display debugging information about the current game.

Also, since I have not had a chance yet to add power-ups, I added a few cheats to give the player a bit more of an advantage. You can select different levels of fire power by using the following keys below:

q - Normal, Fires one shot at a time
w - Double Shot, Fires two shots at a time
e - Triple Shot, Fires three shots at a time
r - Spread, Fires five shots at a time

## Setting up and Running the Project for use in Eclipse

Below are the steps needed for getting this project up and running on your own machine. This project was devloped and built using Eclipse (Kepler Release Build id: 20130614-0229) with Java 1.6. Note: If you are new to Eclipse and need instructions on how to install and set it up, refer to this link *[here](http://wiki.eclipse.org/Eclipse/Installation)*.

###Step 1: Cloning this Project

First, you need to clone this project. Open up a terminal window and in a local directory on your machine, clone this project using the command below.

    git clone https://github.com/garyfredgiger/SpaceInvadersClone.git

Note that when you clone this project it will only pull the master branch, which contains the lastest release version of the game.

###Step 2: Cloning the GameFramework Project

Note: If you already cloned this project by following the directions for setting up my other games (e.g., my asteroids clone), then you can skip this step.

You will also need the 2D Game Framework *[here](https://github.com/garyfredgiger/GameFramework.git)*. Again, in your terminal window enter the command below.

    git clone https://github.com/garyfredgiger/GameFramework.git

At this point there should be two sub folders in your current directory as shown below.

    drwxr-xr-x 5 ggiger ggiger 4096 Mar  7 23:20 GameFramework
    drwxr-xr-x 5 ggiger ggiger 4096 Mar  7 23:37 SpaceInvadersClone

###Step 3: Importing both Projects

After both projects are cloned, they need to be imported into the Eclipse workspace. To do this, follow the steps below:

1. Right-click on *Package Explorer* and select *Import*, then select *General -> Existing Projects into Workspace* and click on Next. The Import dialog will then appear.
2. Click on the Browse button and locate the directories of each project (Note: only one project can be added at a time).
3. Once you select one of the projects click on the OK button, it will then appear in the Projects text area in the Import dialog. Click on the Finish button and it will appear in the Package Explorer.
4. Perform the same steps to import the other project.

After importing both projects you may notice a build error. To correct this error we may need to add the GameFramework project to this project.

###Step 4: Adding the GameFramework Project

The GameFramework project will need to be added to the SpaceInvaersClone project. To do this, follow the steps below:

1. Go back into Eclispe and right-click on the project *SpaceInvaersClone* and select the option *Build Path -> Configure Build Path*. This will bring up the Properties dialog with the Java Build Path highlighted on the left.
2. Next select the *Projects* tab on the right of the dialog and click on the Add button, this will bring up the Required Project Selection dialog.
3. Select the *GameFramework* project from the list then click on the OK button, this will take you back to the *Projects* tab where the GamrFramework project now exists under the *Projects* tab.
4. Last, click on the OK button to accept all changes. Now, when you build the project it should build without any errors since all of the required dependecies have been added to the *SpaceInvaersClone* project.

###Step 5: Running the Game

Note, you may need to force a refresh first before running the game so all the image and audio files are loaded into the IDE. To do this highlight the *SpaceInvaersClone* project and press F5. To run the game from within Eclipse, follow the steps below:

1. Expand the *SpaceInvaersClone* project directory structure and locate the file Main.java (located under src -> squareinvaers)
2. Right-click on the file Main.java and select the option *Run As -> Run Configurations...*. This will bring up the Run Configurations dialog box.
3. Select the parameters tab and add 800 for the width and 600 for the height.
4. Click on the run button at the bottom of the dialog, this will launch the game.

Note that the next time you want to run the game you can simply click on the "play" icon button in the Eclispe IDE. The steps above need to be done the first time to tell eclipse where the entry point is in order to run the application.

## Notes About the Game:

There are currently no known major bugs in this game (i.e., bugs that impact the gameplay, game performance or cause the game to crash severly). That is not to say that there are no bugs at all in this game. I have tested it extensively and everything appears to be working as designed.

### If You Encounter Problems?

If you do find a bug you can submit a new issue under this repo.

...OR...

If you are unable to sumbit a new issue for some unknown reason please email me at garyfredgiger@gmail.com and include in the subject line SPACE INVADERS CLONE BUG. In the email please provide a detailed description of the bug and the steps that you followed to arrive at the bug. If I am unable to reproduce the problem then I will not be able to fix it.

### Additional Notes

NOTE: I included the eclipse project files (.project and .classpath) in this project so you can simply import this project without having to create a new project from your cloned copy of the code. The classpath file may already contain the reference to the GameFramework project.
