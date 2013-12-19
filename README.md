SpaceInvadersClone
==================

This is a Space Invaders clone using the game framework that I am in the process of developing. This Space Invaders clone shows how the game framework can be used to make 2D classic arcade games.

When you clone this project, make sure you also clone the *[GameFramework](https://github.com/garyfredgiger/GameFramework.git)* project in this same repo. The GameFramework project is required for this game. Import both projects into Eclipse and before building the project, make sure you add the GameFramework project to this project under the menu options as described below.

<ol>
<li>Right click on the project and select the option Build Path -> Configure Build Path</li>
<li>Click on the Projects tab and add the GameFramework project, then click the OK button.</li>
</ol>

A few notes about this game:

<ul>
<li>There are currently no known bugs in this game. That is not to say that there are no bugs at all in this game. I have tested it extensively and everything appears to be working as designed. If you do encounter a bug please email me at garyfredgiger@gmail.com and include in the subject line SPACE INVADERS CLONE BUG. In the email please provide a detailed description of the bug and the steps that you followed to arrive at the bug. If I am unable to reproduce the problem then I cannot fix it.</li>
<li>The game was only tested on my laptop running Ubuntu vesion 12.10 within Eclipse running it as a Java application. As I get more time for development and testing, my goal is to obviously test it on all platforms using the major browsers Chrome, IE, Firefox and Safari</li>
<li>For the most part this game is 90% or more completed. I still need to add a few more features in order to make it 100% completed. These features include:
<ul>
<li>Sounds. There are currently no sounds in this game.</li>
<li>Power-ups. I plan on adding randomly spawned power-ups that give the player better weapons during game play</li>
<li>High Score leader board</li>
<li>Tweak existing difficulty features and add new ones that are dependent on the selected difficulty level and level progression.</li>
</ul>
<li>Since this game is still in development, there are a few debugging features that can be enabled.</li>
<ul>
<li>During game play press SHIFT+~ to display debugging information about the current game.</li>
<li>The different powerups can be added to the player's entity by using the following keys:</li>
<ul>q - Normal, Fires one shot at a time</ul>
<ul>w - Double Shot, Fires two shots at a time</ul>
<ul>e - Triple Shot, Fires three shots at a time</ul>
<ul>r - Spead, Fires five shots at a time</ul>
</ul>
</ul>

NOTE: I included the eclipse project files (.project and .classpath) in this project so you can simply import this project without having to create a new project from your cloned copy of the code. Some suggest it is not a good idea to include project specific files, but I thought I would be a rebel and do it anyway.

This game was developed using Eclipse Kepler Release (Build id: 20130614-0229) with Java 1.6. 
