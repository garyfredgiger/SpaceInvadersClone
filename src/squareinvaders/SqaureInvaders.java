package squareinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import squareinvaders.constants.SIConstants;
import squareinvaders.constants.SIDebugConstants;
import squareinvaders.constants.SIFonts;
import squareinvaders.constants.SIStrings;
import squareinvaders.entities.EnemyEntity;
import squareinvaders.entities.EnemyShotEntity;
import squareinvaders.entities.InvaderEntity;
import squareinvaders.entities.InvaderEntityMultipleHits;
import squareinvaders.entities.PlayerEntity;
import squareinvaders.entities.PlayerShotEntity;
import squareinvaders.entities.UFOEntity;
import squareinvaders.managers.InvaderEntityManager;
import squareinvaders.managers.ScoreManager;
import squareinvaders.managers.UFOEntityManager;
import game.framework.GameEngine;
import game.framework.entities.Entity;
import game.framework.interfaces.IRender;
import game.framework.utilities.GameEngineConstants;
import game.framework.utilities.GameUtility;
import game.framework.utilities.input.GameInputMovement;

// public class GameJFrameSqaureInvaders extends GameJFrame
public class SqaureInvaders extends GameEngine
{
  /*
   * Class member variables
   */
  private static final Color           EARTH_COLOR                = Color.ORANGE;

  private static final long            TIME_BETWEEN_INTRO_DEMO    = 30000;
  private static final int             NUMBER_OF_BACKGROUND_STARS = 150;

  private static final int             STARTING_LEVEL             = 0;

  /*
   * Class instance variables
   */

  // Invader variables
  private int                          invaderCount;

  // Different Managers used within the game
  InvaderEntityManager                 invaderEntityManager;
  UFOEntityManager                     ufoEntityManager;
  GameInputMovement                    playerMovement;

  // General game variables
  private int                          currentLevel;
  private SIConstants.DifficultyLevels currentDifficultyLevel;                              // Used to set the difficult level in the InvaderEntityManager object and to display the difficulty level to the screen

  // Variables used for the intro screens
  private ArrayList<InvaderEntity>     introInvaderScoreList;
  private UFOEntity                    ufoForIntroScreen;

  // Variables used for the game screens
  private PlayerEntity                 playerLives;

  // Player variables 
  private SIConstants.ShotTypes        currentShotType;

  // Variables to manage different game events and user input states 
  private boolean                      shotFired;
  private boolean                      invokeGameStartState;
  private boolean                      invokePlayerDeadState;
  private boolean                      startDemo;
  private boolean                      changeDifficultyLevels;

  // Variables to manage game state transitions and timing between the transitions
  private long                         stateGameStartTime;
  private long                         stateNextLevelTime;
  private long                         stateGameOverTime;
  private long                         statePlayerDeadTime;
  private long                         stateStartIntroDemoTime;
  private long                         stateStopIntroDemoTime;

  private boolean                      introScreenMainDisplayed;
  private boolean                      introScreenInstDisplayed;
  private boolean                      gotoIntroMainScreen;
  private boolean                      gotoIntroInstScreen;
  private boolean                      gamePaused;
  private boolean                      requestToQuitPlayingGame;
  private boolean                      quitPlayingGame;
  private boolean                      doNotQuitPlayingGame;

  // Variables used for background effects
  private ArrayList<Entity>            starryBackground           = new ArrayList<Entity>();                    // List to hold the different stars

  // Level dependent variables
  private int                          maxNumberInvaderShots;
  private int                          invaderStartingRowPosition;

  public SqaureInvaders(IRender renderer)
  {
    super(renderer);
  }

  public SqaureInvaders(IRender renderer, int userDefinedScreenWidth, int userDefinedScreenHeight)
  {
    super(renderer, userDefinedScreenWidth, userDefinedScreenHeight);
  }
  
  // TODO: Add a constructor that also specifies the width and height
  // TODO: If the additional constructor is added, all references to GameEngineConstants.DEFAULT_CANVAS_HEIGHT and GameEngineConstants.DEFAULT_CANVAS_WIDTH will need to be replaced. 

  @Override
  public void userGameInit()
  {
    /*
     *  Create instances of each class used in this game
     */

    // Keeps track of current play input
    playerMovement = new GameInputMovement();

    // Objects to keep track of enemies
    // TODO: Could these classes be combined into one?
    invaderEntityManager = new InvaderEntityManager();
    ufoEntityManager = new UFOEntityManager();

    // Setup the player to start in the bottom center of the screen.
    // NOTE: The players default setting has both its alive and visible flags set to true. Therefore, to avoid it from 
    //       showing up before the game begins, the player ship needs to have both flags set to false via the kill
    //       method. Remember that it needs to have these flags set again When the game begins using the reset method.  
    //PlayerEntity player = new PlayerEntity(GameEngineConstants.DEFAULT_CANVAS_WIDTH / 2, 560, 0, GameEngineConstants.DEFAULT_CANVAS_WIDTH);
    PlayerEntity player = new PlayerEntity(screenWidth / 2, 560, 0, screenWidth);
    player.setColor(SIConstants.PLAYER_COLOR);
    player.setVisible(false);     // When the game loads, we do not want the player entity to be seen until the player begins playing the game.
    setNewPlayerEntity(player);

    // TODO: This may need to be changed
    // Setup the entity representing the players lives
    playerLives = new PlayerEntity();
    playerLives.setColor(SIConstants.PLAYER_COLOR);
    //playerLives.setPositionY(GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.083 - playerLives.getHeight());
    playerLives.setPositionY(screenHeight * 0.083 - playerLives.getHeight());

    // Initialize the invaders for the score list that is displayed on the instructions introduction screen
    double rowsSpacing = 0.36;
    introInvaderScoreList = new ArrayList<InvaderEntity>();
    int pointValue = SIConstants.HIGHEST_INVADER_SCORE;
    for (int row = 0; row < SIConstants.NUM_INVADER_ROWS; row++)
    {
      InvaderEntity invader = new InvaderEntity();
      //invader.setPosition(GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.42, GameEngineConstants.DEFAULT_CANVAS_HEIGHT * rowsSpacing);
      invader.setPosition(screenWidth * 0.42, screenHeight * rowsSpacing);
      invader.reset();
      invader.setPointValue(pointValue);
      invader.setColor(SIConstants.INVADER_COLORS[row]);
      introInvaderScoreList.add(invader);

      rowsSpacing += 0.05;
      pointValue -= SIConstants.INVADER_SCORE_INCREMENT;
    }

    ufoForIntroScreen = new UFOEntity();
    //ufoForIntroScreen.setPosition(GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.42 - (ufoForIntroScreen.getWidth() / 4), GameEngineConstants.DEFAULT_CANVAS_HEIGHT * rowsSpacing);
    ufoForIntroScreen.setPosition(screenWidth * 0.42 - (ufoForIntroScreen.getWidth() / 4), screenHeight * rowsSpacing);
    ufoForIntroScreen.setVelocity(0, 0);
    ufoForIntroScreen.reset();

    introScreenMainDisplayed = true;
    introScreenInstDisplayed = false;

    // Flags used to indicate which screens to switch to when in the introduction state
    gotoIntroMainScreen = false;
    gotoIntroInstScreen = false;

    // Set the initial the difficulty level
    currentDifficultyLevel = SIConstants.DifficultyLevels.BEGINNER;
    invaderEntityManager.setNumberOfShotsDifficultyFactor(currentDifficultyLevel);
    invaderEntityManager.setShotProbabilityDifficultyFactor(currentDifficultyLevel);

    // Configure which entity lists from which to remove dead entities during game play for each level
    // For this game we will only remove dead entities from the player shots and enemy shots lists
    this.removeDeadEnemyShotsFromEntityList();
    this.removeDeadPlayerShotsFromEntityList();
    this.removeDeadEnemiesFromEntityList();
  }

  public void userGameShutdown()
  {}

  // TODO: There needs to be separate methods to reset the game and to move to the next level

  /*
   * (non-Javadoc)
   * @see game.engine.jframe.GameJFrame#userGameStart()
   */
  @Override
  public void userGameStart()
  {
    stateStartIntroDemoTime = System.currentTimeMillis();
    startDemo = false;

    //    shotFired = false;
    //    currentShotType = ShotTypes.NORMAL;
    //    currentLevel = 1;
    //
    //    // Setup score manager
    //    ScoreManager.reset(); // TODO: Should this be in the method to initialize the invaders?
    //
    //    // Prepare the enemies
    //    initializeInvaders();
    //
    //    // Setup the player to start in the bottom center of the screen.
    //    PlayerEntity player = new PlayerEntity(GameEngineConstants.DEFAULT_CANVAS_WIDTH / 2, 560);
    //    setNewPlayerEntity(player);
  }

  /*
   * (non-Javadoc)
   * @see game.engine.jframe.GameJFrame#userGamePreUpdate()
   * 
   *  This method is used for any pre-processing of entities before the actual update of each entity occurs.
   * 
   *  Examples of pre-processing include updating the logic of entities before their positions are updated 
   *  and checking for transitions to other states.
   * 
   *  NOTE: The paused state is an internal state of the parent game class and can be entered/exited by the 
   *        public methods pauseGame() and unpauseGame(). When the game is paused this method (userGamePreUpdate)
   *        is not called since no updates should take place when the game is paused. Therefore adding logic to
   *        the switch statement for the paused statement in this method will have no affect on the game. However,
   *        the game entities will continued to be drawn to the screen and logic can be added to the switch
   *        statement for the PAUSED case in the method userGamePreDraw(). One may want to add the word "Paused"
   *        to the screen to indicate the screen is paused. 
   */
  public void userGamePreUpdate()
  {
    switch (state)
    {
      case INTRODUCTION:

        /*
         * This is the starting state for the game.
         * 
         * Next States      Transition Trigger 
         * GAME_START       User key press
         */

        // This section begins a short animation after a specific amount of time has elapsed. This 
        // is to add some professionalism to the game as was seen in the old 2D arcade games that 
        // show some animation when the introduction screen is displayed for a period of time.
        if (introScreenMainDisplayed)
        {
          if (System.currentTimeMillis() > (stateStartIntroDemoTime + TIME_BETWEEN_INTRO_DEMO))
          {
            startDemo = true;
            stateStartIntroDemoTime = System.currentTimeMillis();
          }

          if (startDemo)
          {
            createDemoForIntroductionScreen();
            startDemo = false;
          }
        }

        // NOTE: Do nothing here for the moment since there is no update actions to perform for this state
        break;

      case GAME_START:
        /*
         * Next States       Transition Trigger
         * PLAYING           Timer expires
         */

        // Check if the timer for displaying the game start screen has expired. If it has, set the game state to PLAYING
        if (System.currentTimeMillis() > (stateGameStartTime + 3000))
        {
          state = GameEngineConstants.GameState.PLAYING;
          resetGame();
        }

        break;

      case PLAYING:

        /*
         * Next States       Transition Trigger
         * PAUSED            User key press
         * NEXT_LEVEL        Game Event - All invaders have been destroyed
         * PLAYER_DEAD       Game Event - The player has been killed
         */

        // Game Event - Check for the transition to the state NEXT_LEVEL if all invaders have
        if (invaderCount <= 0)
        {
          state = GameEngineConstants.GameState.LEVEL_NEXT;
          stateNextLevelTime = System.currentTimeMillis();

          getPlayer().setVelocity(0, 0);

          clearEnemyShot();
          return;
        }

        // Game Event - Check if the game needs to end because the invaders have landed
        // NOTE: The flag in the invaderEntityManager is set within the Invaders doLogic method if one of them hits the bottom of the screen.
        if (invaderEntityManager.invadersHaveLanded())
        {
          state = GameEngineConstants.GameState.GAMEOVER;
          stateGameOverTime = System.currentTimeMillis();

          // Kill the player, which will make the player entity invisible
          getPlayer().kill();

          stopEnemiesFromMoving(true);
        }

        // Game Event - If player was killed, move to the player dead state
        if (invokePlayerDeadState)
        {
          state = GameEngineConstants.GameState.PLAYER_DEAD;
          statePlayerDeadTime = System.currentTimeMillis();
          stopEnemiesFromMoving(true);
          clearEnemyShot();
        }

        // If any of the invaders have collided with either side of the screen, reverse their direction
        // by updating the logic for each invader. The flag to indicate an update is required is set in
        // the invader entity's updatePosition method.
        if (invaderEntityManager.isLogicUpdateRequired())
        {
          Iterator<Entity> enemyIterator = getEnemies().iterator();

          while (enemyIterator.hasNext())
          {
            EnemyEntity currentEnemy = (EnemyEntity) enemyIterator.next();

            if (currentEnemy.isAlive())
            {
              currentEnemy.doLogic();
            }
          }

          // Clear the update request flag
          invaderEntityManager.logicUpdateRequestFulfilled();
        }

        if (invaderEntityManager.isInvaderSpeedupRequired())
        {
          Iterator<Entity> enemyIterator = getEnemies().iterator();

          double newInvaderVelocity = invaderEntityManager.getDirection() * ((SIConstants.NUM_INVADERS - invaderCount) * invaderEntityManager.getInvaderSpeedupFactor() + SIConstants.INVADER_INITIAL_VELOCITY);

          while (enemyIterator.hasNext())
          {
            EnemyEntity currentEnemy = (EnemyEntity) enemyIterator.next();

            if (currentEnemy.isAlive())
            {
              if (currentEnemy.getEnemyType() == SIConstants.EnemyTypes.INVADER)
              {
                // This is not a consistent speed up function
                //currentEnemy.setVelocityX(currentEnemy.getVelocityX() * SpaceInvaderConstants.INVADER_SPEEDUP_FACTOR);
                currentEnemy.setVelocityX(newInvaderVelocity);
              }
            }
          }

          invaderEntityManager.invaderSpeedupRequestFulfilled();
        }

        /*
         * Determine if a UFO will be launched 
         */
        if (ufoEntityManager.ufoShouldBeLaunched())
        {
          UFOEntity ufo = new UFOEntity(ufoEntityManager, screenWidth);
          System.out.println("Launching UFO with parameters:");
          System.out.println(ufo.toString());
          addEnemy(ufo);
        }

        break;

      // Next state for LEVEL_NEXT is 
      case LEVEL_NEXT:
        /*
         * Next States       Transition Trigger
         */

        if (System.currentTimeMillis() > (stateNextLevelTime + 3000))
        {
          // TODO: Here add parameters to increase the difficulty of the next level  
          state = GameEngineConstants.GameState.PLAYING;
          nextLevel();
        }

        break;

      case PAUSED:
        /*
         * Next States       Transition Trigger
         */

        // NOTE: Any logic added to this case statement will no be executed when the game is paused. This is just here
        //       to document the flow of the game states and their transitions.

        break;

      case GAMEOVER:
        /*
         * Next States       Transition Trigger
         */
        if (System.currentTimeMillis() > (stateGameOverTime + 3000))
        {
          // Clear the entities so the introduction screen can be displayed properly
          resetEntityLists();

          getPlayer().kill();

          state = GameEngineConstants.GameState.INTRODUCTION;
        }

        break;

      case PLAYER_DEAD:
        /*
         * Next States       Transition Trigger
         */
        if (System.currentTimeMillis() > (statePlayerDeadTime + 3000))
        {
          invokePlayerDeadState = false;  // Reset the flag since we have just entered this state

          if (((PlayerEntity) getPlayer()).getNumberOfLives() > 0)
          {
            state = GameEngineConstants.GameState.PLAYING;

            // Call method here for PlayerEntity that will make the player visible, move the player to the home position and set the visible flag            
            ((PlayerEntity) getPlayer()).restore();

            playerMovement.reset();

            resumeEnemyMovement((double) invaderEntityManager.getDirection() * ((SIConstants.NUM_INVADERS - invaderCount) * invaderEntityManager.getInvaderSpeedupFactor() + SIConstants.INVADER_INITIAL_VELOCITY), 0.0);
          }
          else
          {
            state = GameEngineConstants.GameState.GAMEOVER;
            stateGameOverTime = System.currentTimeMillis();
          }
        }

        break;

      // The default case is when the game is in the INTRODUCTION state 
      default:
    }
  }

  /*
   * (non-Javadoc)
   * @see game.engine.jframe.GameJFrame#userGameUpdateEntity(game.entities.Entity)
   * 
   * This method is called from with in the game engine update method for each entity.
   * 
   * Provide an update of each entity
   */
  @Override
  public void userGameUpdateEntity(Entity entity)
  {
    // Simply exit if entity is not alive since it is pointless to update a dead entity (At least in this game).
    if (!entity.isAlive())
    {
      return;
    }

    // Determine if the current entity is an enemy and if so, determine if the enemy entity is an invader.
    // If the current enemy is an invader, determine if the invader should fire.
    // NOTE: The InvaderEntity class contains the method fireShot(), which is not defined in its parent class
    //       therefore it needs to be downcast from Entity to InvaderEntity to call the method.
    if (entity.getEntityType() == GameEngineConstants.EntityTypes.ENEMY)
    {
      // Since the entity type is an enemy, downcast the enemy entity so the fire method can be accessed.
      EnemyEntity currentEnemy = (EnemyEntity) entity;

      // TODO: Is it better to use the Entity type to filter the entity or use the instanceof method?
      // if (currentEnemy instanceof InvaderEntity)
      if (currentEnemy.getEnemyType() == SIConstants.EnemyTypes.INVADER)
      {
        if (getEnemyShots().size() < maxNumberInvaderShots)
        {
          // Determine if the current invader should fire a shot
          if (((InvaderEntity) currentEnemy).shouldFireShot())
          {
            EnemyShotEntity enemyShot = new EnemyShotEntity(currentEnemy.getPositionX() + (currentEnemy.getWidth() / 2), currentEnemy.getPositionY(), 560);
            addEnemyShot(enemyShot);
          }
        }
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see game.engine.jframe.GameJFrame#userHandleEntityCollision(game.entities.Entity, game.entities.Entity)
   * 
   * Handle the collisions for each entity. The collisions that are supported are:
   * 
   * Player     <-> Enemy
   * Player     <-> EnemyShot
   * Player     <-> PowerUp
   * PlayerShot <-> Enemy
   */
  @Override
  public void userHandleEntityCollision(Entity entity1, Entity entity2)
  {
    // Handle player shot collision with an invader
    if (entity1.getEntityType() == GameEngineConstants.EntityTypes.PLAYER_SHOT)
    {
      if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY)
      {
        entity1.kill();
        entity2.kill();

        //if (entity2 instanceof EnemyEntity)
        //{ 
        // This seems to work for now, but if an exception occurs where the enemy type condition is met, but the entity is not an EnemyEntity, then the above logic will need to be added
        if (((EnemyEntity) entity2).getEnemyType() == SIConstants.EnemyTypes.INVADER)
        {
          if (!entity2.isAlive())
          {
            invaderCount--;

            // TODO: Test if the invaders should be sped up given the remaining count
            invaderEntityManager.invaderSpeedupRequested();
          }
        }
        //}

        if (!entity2.isAlive())
        {
          ScoreManager.incrementScore(((EnemyEntity) entity2).getPointValue());
        }
      }
    }

    // Handle player shot collision with an invader
    if (entity1.getEntityType() == GameEngineConstants.EntityTypes.PLAYER)
    {
      if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY_SHOT)
      {
        invokePlayerDeadState = true; // Set flag that will be used to transition to the PLAYER_DEAD state 
        getPlayer().kill();
      }
    }
  }

  /*
   * (non-Javadoc)
   * @see game.engine.jframe.GameJFrame#userGamePreDraw(java.awt.Graphics)
   * 
   * Any drawing logic that needs to occur before the screen is rendered will go here. Examples of drawing logic
   * includes:
   * 
   * Drawing game information
   * 
   */
  @Override
  public void userGamePreDraw(Graphics2D g) // Changed from graphics
  {
    switch (state)
    {
      case INTRODUCTION:

        // Draw the game title - Displayed in Yellow
        g.setFont(SIFonts.FONT_INTRO_SCREEN_MAIN_TITLE);
        g.setColor(Color.YELLOW);
        Rectangle2D boundsIntroScreenTitle = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN_MAIN_TITLE, g);
        //g.drawString(SIStrings.MSG_INTRO_SCREEN_MAIN_TITLE, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreenTitle.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.20)));
        g.drawString(SIStrings.MSG_INTRO_SCREEN_MAIN_TITLE, (int) ((screenWidth - boundsIntroScreenTitle.getWidth()) / 2), (int) ((screenHeight * 0.20)));

        if (introScreenMainDisplayed)
        {
          // Draw the sub title - Displayed in Yellow
          g.setFont(SIFonts.FONT_INTRO_SCREEN1_SUB_TITLE);
          g.setColor(Color.ORANGE);
          Rectangle2D boundsIntroScreen1SubTitle = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN1_SUB_TITLE, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN1_SUB_TITLE, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen1SubTitle.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.30)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN1_SUB_TITLE, (int) ((screenWidth - boundsIntroScreen1SubTitle.getWidth()) / 2), (int) ((screenHeight * 0.30)));

          // Display user instructions to view game instructions
          g.setFont(SIFonts.FONT_INTRO_SCREEN1_DIFFICULTY);
          g.setColor(Color.WHITE);
          Rectangle2D boundsIntroScreen1DifficultyLabel = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN1_DIFFICULTY_LABEL, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN1_DIFFICULTY_LABEL, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen1DifficultyLabel.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.50)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN1_DIFFICULTY_LABEL, (int) ((screenWidth - boundsIntroScreen1DifficultyLabel.getWidth()) / 2), (int) ((screenHeight * 0.50)));

          g.setFont(SIFonts.FONT_INTRO_SCREEN1_DIFFICULTY);
          g.setColor(Color.RED);
          Rectangle2D boundsIntroScreen1Difficulty = g.getFontMetrics().getStringBounds(currentDifficultyLevel.toString(), g);
          //g.drawString(currentDifficultyLevel.toString(), (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen1Difficulty.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.57)));
          g.drawString(currentDifficultyLevel.toString(), (int) ((screenWidth - boundsIntroScreen1Difficulty.getWidth()) / 2), (int) ((screenHeight * 0.57)));

          // Draw the user instructions to begin game
          g.setFont(SIFonts.FONT_INTRO_SCREEN1_INST);
          g.setColor(Color.BLUE);
          Rectangle2D boundsIntroScreen1PressSpace = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN1_PRESS_SPACE, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN1_PRESS_SPACE, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen1PressSpace.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.67)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN1_PRESS_SPACE, (int) ((screenWidth - boundsIntroScreen1PressSpace.getWidth()) / 2), (int) ((screenHeight * 0.67)));

          // Display user instructions to view game instructions
          g.setFont(SIFonts.FONT_INTRO_SCREEN1_INST);
          g.setColor(SIConstants.VIOLET);
          Rectangle2D boundsIntroScreen1PressI = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN1_PRESS_I, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN1_PRESS_I, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen1PressI.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.71)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN1_PRESS_I, (int) ((screenWidth - boundsIntroScreen1PressI.getWidth()) / 2), (int) ((screenHeight * 0.71)));
          
          // Display user instructions to view game instructions
          g.setFont(SIFonts.FONT_INTRO_SCREEN1_INST);
          g.setColor(Color.CYAN);
          Rectangle2D boundsIntroScreen1PressD = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN1_PRESS_D, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN1_PRESS_D, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen1PressD.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.75)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN1_PRESS_D, (int) ((screenWidth - boundsIntroScreen1PressD.getWidth()) / 2), (int) ((screenHeight * 0.75)));
          
          // Display the credits and copyright 
          g.setFont(SIFonts.FONT_INTRO_SCREEN1_CREDIT);
          g.setColor(Color.WHITE);
          Rectangle2D boundsIntroScreen1Credits = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN1_CREDIT, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN1_CREDIT, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen1Credits.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.86)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN1_CREDIT, (int) ((screenWidth - boundsIntroScreen1Credits.getWidth()) / 2), (int) ((screenHeight * 0.86)));

          
          g.setFont(SIFonts.FONT_INTRO_SCREEN1_CREDIT);
          g.setColor(Color.WHITE);
          Rectangle2D boundsIntroScreen1Copyright = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN1_COPYRIGHT, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN1_COPYRIGHT, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen1Copyright.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.91)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN1_COPYRIGHT, (int) ((screenWidth - boundsIntroScreen1Copyright.getWidth()) / 2), (int) ((screenHeight * 0.91)));
        }

        if (introScreenInstDisplayed)
        {
          // Display score heading
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_SUB_TITLE);
          g.setColor(Color.ORANGE);
          Rectangle2D boundsIntroScreen2SubTitle = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN2_POINT_VALUES, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN2_POINT_VALUES, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen2SubTitle.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.30)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN2_POINT_VALUES, (int) ((screenWidth - boundsIntroScreen2SubTitle.getWidth()) / 2), (int) ((screenHeight * 0.30)));

          // Display score value for invader list
          for (int i = 0; i < introInvaderScoreList.size(); i++)
          {
            introInvaderScoreList.get(i).draw(g);
            String scoreMsg = introInvaderScoreList.get(i).getPointValue() + SIStrings.MSG_INTRO_SCREEN2_POINTS;

            g.setFont(SIFonts.FONT_INTRO_SCREEN2_SCORE);
            g.setColor(Color.WHITE);
            //g.drawString(scoreMsg, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.47), (int) (introInvaderScoreList.get(i).getPositionY() + introInvaderScoreList.get(i).getHeight()));
            g.drawString(scoreMsg, (int) (screenWidth * 0.47), (int) (introInvaderScoreList.get(i).getPositionY() + introInvaderScoreList.get(i).getHeight()));
          }

          // Display the score value of the ufo
          ufoForIntroScreen.draw(g);
          String ufoScoreMsg = SIStrings.MSG_INTRO_SCREEN2_MYSTERY + SIStrings.MSG_INTRO_SCREEN2_POINTS;
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_SCORE);
          g.setColor(Color.WHITE);
          //g.drawString(ufoScoreMsg, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.47), (int) (ufoForIntroScreen.getPositionY() + ufoForIntroScreen.getHeight()));
          g.drawString(ufoScoreMsg, (int) (screenWidth * 0.47), (int) (ufoForIntroScreen.getPositionY() + ufoForIntroScreen.getHeight()));
          
          // Display instructions

          // Display instructions heading
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_SUB_TITLE);
          g.setColor(Color.BLUE);
          Rectangle2D boundsIntroScreenInstTitle = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN2_INST_TITLE, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_TITLE, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreenInstTitle.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.73)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_TITLE, (int) ((screenWidth - boundsIntroScreenInstTitle.getWidth()) / 2), (int) ((screenHeight * 0.73)));

          // Display Left movement arrow
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_ARROW);
          g.setColor(Color.WHITE);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN2_LEFT_ARROW, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.10), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.80)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN2_LEFT_ARROW, (int) (screenWidth * 0.10), (int) ((screenHeight * 0.80)));

          // Display Right movement arrow
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_ARROW);
          g.setColor(Color.WHITE);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN2_RIGHT_ARROW, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.10), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.84)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN2_RIGHT_ARROW, (int) (screenWidth * 0.10), (int) ((screenHeight * 0.84)));

          // Display Left movement text
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_INST);
          g.setColor(Color.WHITE);
          Rectangle2D boundsIntroScreen2InstLeft = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN2_INST_MOVE_LEFT, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_MOVE_LEFT, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.13), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.80) - (boundsIntroScreen2InstLeft.getHeight() / 2) + 2));
          g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_MOVE_LEFT, (int) (screenWidth * 0.13), (int) ((screenHeight * 0.80) - (boundsIntroScreen2InstLeft.getHeight() / 2) + 2));

          // Display Right movement text
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_INST);
          g.setColor(Color.WHITE);
          Rectangle2D boundsIntroScreen2InstRight = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN2_INST_MOVE_RIGHT, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_MOVE_RIGHT, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.13), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.84) - (boundsIntroScreen2InstRight.getHeight() / 2) + 2));
          g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_MOVE_RIGHT, (int) (screenWidth * 0.13), (int) ((screenHeight * 0.84) - (boundsIntroScreen2InstRight.getHeight() / 2) + 2));

          // Display Fire command 
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_INST);
          g.setColor(Color.WHITE);
          Rectangle2D boundsIntroScreen2InstFire = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN2_INST_FIRE, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_FIRE, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.35), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.80) - (boundsIntroScreen2InstFire.getHeight() / 2) + 2));
          g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_FIRE, (int) (screenWidth * 0.35), (int) ((screenHeight * 0.80) - (boundsIntroScreen2InstFire.getHeight() / 2) + 2));

          // Display Fire command 
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_INST);
          g.setColor(Color.WHITE);
          Rectangle2D boundsIntroScreen2InstPause = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN2_INST_PAUSE, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_PAUSE, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.35), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.84) - (boundsIntroScreen2InstPause.getHeight() / 2) + 2));
          g.drawString(SIStrings.MSG_INTRO_SCREEN2_INST_PAUSE, (int) (screenWidth * 0.35), (int) ((screenHeight * 0.84) - (boundsIntroScreen2InstPause.getHeight() / 2) + 2));

          // Draw the user instructions to begin game
          g.setFont(SIFonts.FONT_INTRO_SCREEN2_INST_TYPE2);
          g.setColor(Color.YELLOW);
          Rectangle2D boundsIntroScreen1PressEsc = g.getFontMetrics().getStringBounds(SIStrings.MSG_INTRO_SCREEN2_PRESS_ENTER, g);
          //g.drawString(SIStrings.MSG_INTRO_SCREEN2_PRESS_ENTER, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntroScreen1PressEsc.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.91)));
          g.drawString(SIStrings.MSG_INTRO_SCREEN2_PRESS_ENTER, (int) ((screenWidth - boundsIntroScreen1PressEsc.getWidth()) / 2), (int) ((screenHeight * 0.91)));
        }

        break;

      case GAME_START:

        // Display the text "Starting Game" above the text "Get Ready"
        g.setFont(SIFonts.FONT_GAME_START);
        g.setColor(Color.WHITE);
        Rectangle2D boundsLine1 = g.getFontMetrics().getStringBounds(SIStrings.MSG_GAME_START_SCREEN_STARTING, g);
        Rectangle2D boundsLine2 = g.getFontMetrics().getStringBounds(SIStrings.MSG_GAME_START_SCREEN_READY, g);
//        g.drawString(SIStrings.MSG_GAME_START_SCREEN_STARTING, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsLine1.getWidth()) / 2), 300);
//        g.drawString(SIStrings.MSG_GAME_START_SCREEN_READY, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsLine2.getWidth()) / 2), 400);

        g.drawString(SIStrings.MSG_GAME_START_SCREEN_STARTING, (int) ((screenWidth - boundsLine1.getWidth()) / 2), 300);
        g.drawString(SIStrings.MSG_GAME_START_SCREEN_READY, (int) ((screenWidth - boundsLine2.getWidth()) / 2), 400);
        
        break;

      case LEVEL_NEXT:

        // NOTE: For this particular game, no pre-draw action need to be performed for this state.

      case PAUSED:

        // NOTE: For this particular game, no pre-draw action need to be performed for this state.

      case GAMEOVER:

        // Use this state to keep displaying the screen even when the game is over before returning to the introduction screen

      case PLAYER_DEAD:

        // Use this state to keep displaying the screen even when the game is over before returning to the introduction screen

      case EXIT_PLAYING_GAME:

      case PLAYING:

        drawCustomEntityList(g);

        // Draw the Level number
        g.setFont(SIFonts.FONT_PLAYING_LEVEL);
        g.setColor(Color.WHITE);
        String levelMsg = SIStrings.MSG_PLAYING_LEVEL + GameUtility.lPadZero(currentLevel, 2);
        //g.drawString(levelMsg, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.10), (int) (GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.083));
        g.drawString(levelMsg, (int) (screenWidth * 0.10), (int) (screenHeight * 0.083));
        
        // Draw the score
        g.setFont(SIFonts.FONT_PLAYING_SCORE);
        g.setColor(Color.WHITE);
        String scoreMsg = SIStrings.MSG_PLAYING_SCORE + GameUtility.lPadZero(ScoreManager.getScore(), 6);
        Rectangle2D boundsScore = g.getFontMetrics().getStringBounds(scoreMsg, g);
        //g.drawString(scoreMsg, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsScore.getWidth()) / 2), (int) (GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.083));
        g.drawString(scoreMsg, (int) ((screenWidth - boundsScore.getWidth()) / 2), (int) (screenHeight * 0.083));

        // Draw the players lives
        g.setFont(SIFonts.FONT_PLAYING_LIVES);
        g.setColor(Color.WHITE);
        //g.drawString(SIStrings.MSG_PLAYING_LIVES, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.70), (int) (GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.083));
        g.drawString(SIStrings.MSG_PLAYING_LIVES, (int) (screenWidth * 0.70), (int) (screenHeight * 0.083));
        
        int xOffset = (int) (screenWidth * 0.80);
        for (int i = 0; i < ((PlayerEntity) getPlayer()).getNumberOfLives(); i++)
        {
          playerLives.setPositionX(xOffset);
          playerLives.draw(g);
          xOffset += (playerLives.getWidth() * 2);
        }

        drawEarth(g);

        break;

      default:
    }
  }

  /*
   * (non-Javadoc)
   * @see game.engine.jframe.GameJFrame#userGamePostDraw(java.awt.Graphics)
   * 
   * This method is used to draw anything to the screen after all entities have been drawn to the screen.
   * 
   * NOTE: The most recent items drawn to the screen are displayed on top. Since the "GAME PAUSED" message
   *       should not be obstructed by any of the other items drawn on the screen, it is the only thing
   *       that is drawn in the post draw user method.  
   * 
   */
  @Override
  public void userGamePostDraw(Graphics2D g) // Changed from graphics
  {
    // For debugging purposes
    int line = 300;

    if (displayDebugInfo)
    {
      g.setFont(SIDebugConstants.FONT_DEBUG);
      g.setColor(Color.WHITE);
      g.drawString(SIDebugConstants.DEBUG_MSG_STATE + state.toString(), 560, line);
      line += 16;
    }

    switch (state)
    {
      case GAMEOVER:

        // Draw the "GAME OVER" message
        g.setFont(SIFonts.FONT_PLAYING_GAMEOVER);
        g.setColor(Color.WHITE);
        Rectangle2D boundsGameOver = g.getFontMetrics().getStringBounds(SIStrings.MSG_PLAYING_GAMEOVER, g);
        //g.drawString(SIStrings.MSG_PLAYING_GAMEOVER, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsGameOver.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT - boundsGameOver.getHeight()) / 2));
        g.drawString(SIStrings.MSG_PLAYING_GAMEOVER, (int) ((screenWidth - boundsGameOver.getWidth()) / 2), (int) ((screenHeight - boundsGameOver.getHeight()) / 2));

        break;

      case LEVEL_NEXT:

        // Display the text "Starting Game" above the text "Get Ready"
        String msgNextLevelLine1 = SIStrings.MSG_PLAYING_NEXT_LEVEL + (currentLevel + 1);

        g.setFont(SIFonts.FONT_PLAYING_NEXT_LEVEL);
        g.setColor(Color.WHITE);
        Rectangle2D boundsNextLevelLine1 = g.getFontMetrics().getStringBounds(msgNextLevelLine1, g);
        //g.drawString(msgNextLevelLine1, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsNextLevelLine1.getWidth()) / 2), 300);
        g.drawString(msgNextLevelLine1, (int) ((screenWidth - boundsNextLevelLine1.getWidth()) / 2), 300);
        
        break;

      case PLAYING:

        if (displayDebugInfo)
        {
          g.setFont(SIDebugConstants.FONT_DEBUG);
          g.setColor(Color.WHITE);
          g.drawString(SIDebugConstants.DEBUG_MSG_NUM_INVADERS + invaderCount, 560, line);
          line += 16;
          DecimalFormat probf = new DecimalFormat(SIDebugConstants.DEBUG_DECIMAL_FORMAT);  // this will helps you to always keeps in two decimal places
          g.drawString(SIDebugConstants.DEBUG_MSG_UFO_PROBABILITY + probf.format(ufoEntityManager.getProbability()), 560, line);
          line += 16;
          g.drawString(SIDebugConstants.DEBUG_MSG_NUM_SHOTS_DIFF_FACTOR + invaderEntityManager.getNumberOfShotsDifficultyFactor(), 560, line);
          line += 16;
          g.drawString(SIDebugConstants.DEBUG_MSG_SHOT_PROB_DIFF_FACTOR + invaderEntityManager.getShotProbabilityDifficultyFactor(), 560, line);
          line += 16;
          g.drawString(SIDebugConstants.DEBUG_MSG_MAX_INVADER_SHOTS + maxNumberInvaderShots, 560, line);
          line += 16;
          probf = new DecimalFormat(SIDebugConstants.DEBUG_DECIMAL_FORMAT);
          g.drawString(SIDebugConstants.DEBUG_MSG_INVADER_SHOT_PROB + probf.format(invaderEntityManager.getInvaderShotProbabilityForCurrentLevel()), 560, line);
          line += 16;
          g.drawString(SIDebugConstants.DEBUG_MSG_INVADER_INTERVAL_TIME + SIConstants.DEFAULT_TIME_INTERVAL_BETWEEN_SHOTS, 560, line);
          line += 16;

          if (getEnemies().size() == 0)
            g.drawString(SIDebugConstants.DEBUG_MSG_INVADER_VELOCITY_ALL_DEAD, 560, line);
          else
            g.drawString(SIDebugConstants.DEBUG_MSG_INVADER_VELOCITY + getEnemies().get(0).getVelocityX(), 560, line);
        }

        break;

      case PAUSED:

        // Draw the paused message
        g.setFont(SIFonts.FONT_PLAYING_PAUSED);
        g.setColor(Color.WHITE);
        Rectangle2D boundsPaused = g.getFontMetrics().getStringBounds(SIStrings.MSG_PLAYING_PAUSED, g);
        //g.drawString(SIStrings.MSG_PLAYING_PAUSED, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsPaused.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT - boundsPaused.getHeight()) / 2));
        g.drawString(SIStrings.MSG_PLAYING_PAUSED, (int) ((screenWidth - boundsPaused.getWidth()) / 2), (int) ((screenHeight - boundsPaused.getHeight()) / 2));
        
        break;

      case EXIT_PLAYING_GAME:

        // Draw the message asking player if they want to exit from plating the game. 
        g.setFont(SIFonts.FONT_PLAYING_EXIT_PLAYING_GAME);
        g.setColor(Color.WHITE);
        Rectangle2D boundsExitPlaying = g.getFontMetrics().getStringBounds(SIStrings.MSG_PLAYING_EXIT_PLAYING_GAME, g);
        //g.drawString(SIStrings.MSG_PLAYING_EXIT_PLAYING_GAME, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsExitPlaying.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT - boundsExitPlaying.getHeight()) / 2));
        g.drawString(SIStrings.MSG_PLAYING_EXIT_PLAYING_GAME, (int) ((screenWidth - boundsExitPlaying.getWidth()) / 2), (int) ((screenHeight - boundsExitPlaying.getHeight()) / 2));
        
        break;

      case PLAYER_DEAD:

        // Draw the player dead message after they were hit with an enemy shot.
        g.setFont(SIFonts.FONT_PLAYING_PLAYER_DEAD);
        g.setColor(Color.WHITE);
        Rectangle2D boundsPlayerDead = g.getFontMetrics().getStringBounds(SIStrings.MSG_PLAYING_PLAYER_DEAD, g);
        //g.drawString(SIStrings.MSG_PLAYING_PLAYER_DEAD, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsPlayerDead.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT - boundsPlayerDead.getHeight()) / 2));
        g.drawString(SIStrings.MSG_PLAYING_PLAYER_DEAD, (int) ((screenWidth - boundsPlayerDead.getWidth()) / 2), (int) ((screenHeight - boundsPlayerDead.getHeight()) / 2));

        break;

      default:
    }
  }

  @Override
  public void userProcessInput()
  {
    switch (state)
    {
      case INTRODUCTION:

        // Do not transition to the PLAYING state until the user has pressed the space to begin playing the game. 
        if (invokeGameStartState)
        {
          clearEnemies();
          state = GameEngineConstants.GameState.GAME_START;

          // Set the GAME_START timer variable to the current time. Storing the current time will allow the GAME_START 
          // state to persist for a brief period before transitioning to its next state, which is the PLAYING state. 
          stateGameStartTime = System.currentTimeMillis();
          invokeGameStartState = false;
        }
        else if (gotoIntroMainScreen)
        {
          stateStartIntroDemoTime = System.currentTimeMillis();
          startDemo = false;
          introScreenMainDisplayed = true;
          introScreenInstDisplayed = false;

          gotoIntroMainScreen = false;
        }
        else if (gotoIntroInstScreen)
        {
          clearEnemies();
          introScreenMainDisplayed = false;
          introScreenInstDisplayed = true;

          gotoIntroInstScreen = false;
        }

        if (changeDifficultyLevels)
        {
          currentDifficultyLevel = currentDifficultyLevel.getNext();
          invaderEntityManager.setNumberOfShotsDifficultyFactor(currentDifficultyLevel);
          invaderEntityManager.setShotProbabilityDifficultyFactor(currentDifficultyLevel);

          changeDifficultyLevels = false;
        }

        break;

      case GAME_START:

        // NOTE: There are no key presses to monitor during the GAME_START state
        break;

      case PLAYING:

        // This is all valid when the game state is set to PLAYING
        double deltaX = 0;

        if (gamePaused)
        {
          state = GameEngineConstants.GameState.PAUSED;
          this.pauseGame();
        }

        if (requestToQuitPlayingGame)
        {
          state = GameEngineConstants.GameState.EXIT_PLAYING_GAME;
          this.pauseGame();
        }

        if (playerMovement.keyLeft())
        {
          deltaX -= SIConstants.PLAYER_VELOCITY;
        }

        if (playerMovement.keyRight())
        {
          deltaX += SIConstants.PLAYER_VELOCITY;
        }

        getPlayer().setVelocityX(deltaX);

        if (shotFired)
        {
          if (SIConstants.ShotTypes.NORMAL == currentShotType)
          {
            // Single Shot
            PlayerShotEntity playerShot = new PlayerShotEntity(getPlayer().getPositionX() + (getPlayer().getWidth() / 2), getPlayer().getPositionY(), 20);
            addPlayerShot(playerShot);
          }
          else if (SIConstants.ShotTypes.DOUBLE == currentShotType)
          {
            // Twin Shot
            PlayerShotEntity playerShotLeft = new PlayerShotEntity(getPlayer().getPositionX(), getPlayer().getPositionY(), 20);
            PlayerShotEntity playerShotRight = new PlayerShotEntity(getPlayer().getPositionX() + getPlayer().getWidth(), getPlayer().getPositionY(), 20);
            addPlayerShot(playerShotLeft);
            addPlayerShot(playerShotRight);
          }
          else if (SIConstants.ShotTypes.THREE_SPREAD == currentShotType)
          {
            // Spread Three Shot
            PlayerShotEntity playerShotLeftAngled = new PlayerShotEntity(getPlayer().getPositionX() + (getPlayer().getWidth() / 2), getPlayer().getPositionY(), 20);
            PlayerShotEntity playerShotRightAngled = new PlayerShotEntity(getPlayer().getPositionX() + (getPlayer().getWidth() / 2), getPlayer().getPositionY(), 20);
            PlayerShotEntity playerShotStraight = new PlayerShotEntity(getPlayer().getPositionX() + (getPlayer().getWidth() / 2), getPlayer().getPositionY(), 20);

            adjustDirection(playerShotLeftAngled, 10);
            adjustDirection(playerShotRightAngled, -10);

            addPlayerShot(playerShotLeftAngled);
            addPlayerShot(playerShotRightAngled);
            addPlayerShot(playerShotStraight);
          }
          else if (SIConstants.ShotTypes.FIVE_SPREAD == currentShotType)
          {
            // Spread Five Shot
            PlayerShotEntity playerShotLeftAngled1 = new PlayerShotEntity(getPlayer().getPositionX() + (getPlayer().getWidth() / 2), getPlayer().getPositionY(), 20);
            PlayerShotEntity playerShotLeftAngled2 = new PlayerShotEntity(getPlayer().getPositionX() + (getPlayer().getWidth() / 2), getPlayer().getPositionY(), 20);

            PlayerShotEntity playerShotRightAngled1 = new PlayerShotEntity(getPlayer().getPositionX() + (getPlayer().getWidth() / 2), getPlayer().getPositionY(), 20);
            PlayerShotEntity playerShotRightAngled2 = new PlayerShotEntity(getPlayer().getPositionX() + (getPlayer().getWidth() / 2), getPlayer().getPositionY(), 20);

            PlayerShotEntity playerShotStraight = new PlayerShotEntity(getPlayer().getPositionX() + (getPlayer().getWidth() / 2), getPlayer().getPositionY(), 20);

            adjustDirection(playerShotLeftAngled1, 5);
            adjustDirection(playerShotLeftAngled2, 10);
            adjustDirection(playerShotRightAngled1, -5);
            adjustDirection(playerShotRightAngled2, -10);

            addPlayerShot(playerShotLeftAngled1);
            addPlayerShot(playerShotLeftAngled2);
            addPlayerShot(playerShotRightAngled1);
            addPlayerShot(playerShotRightAngled2);
            addPlayerShot(playerShotStraight);
          }

          shotFired = false;
        }
        break;

      case EXIT_PLAYING_GAME:

        if (quitPlayingGame)
        {
          quitPlayingGame = false;
          state = GameEngineConstants.GameState.GAMEOVER;
          stateGameOverTime = System.currentTimeMillis();
        }

        if (doNotQuitPlayingGame)
        {
          state = GameEngineConstants.GameState.PLAYING;
          doNotQuitPlayingGame = false;
          requestToQuitPlayingGame = false;
          this.unpauseGame();
        }

        break;

      case PAUSED:

        if (!gamePaused)
        {
          state = GameEngineConstants.GameState.PLAYING;
          unpauseGame();
        }

        break;

      default:
    }
  }

  /*
   * (non-Javadoc)
   * @see game.engine.jframe.GameJFrame#gameKeyPressed(int)
   * 
   * With respect to the game
   */
  @Override
  public void gameKeyPressed(int keyCode)
  {
    switch (state)
    {
      case INTRODUCTION:

        if (keyCode == KeyEvent.VK_SPACE)
        {
          /*
           * Set the game state variables to begin the transition to the GAME_START state
           */
          invokeGameStartState = true;
        }

        if (keyCode == KeyEvent.VK_I)
        {
          gotoIntroInstScreen = true;
        }

        if (keyCode == KeyEvent.VK_D)
        {
          changeDifficultyLevels = true;
        }

        if (keyCode == KeyEvent.VK_ENTER)
        {
          gotoIntroMainScreen = true;
        }

        break;

      case GAME_START:

        // NOTE: There are no key presses to monitor during the GAME_START state
        break;

      case PLAYING:

        playerMovement.pressed(keyCode);

        if (keyCode == KeyEvent.VK_SPACE)
        {
          shotFired = true;
        }

        break;

      default:
    }
  }

  @Override
  public void gameKeyReleased(int keyCode)
  {
    switch (state)
    {
      case INTRODUCTION:

        break;

      case PLAYING:

        playerMovement.released(keyCode);

        if (keyCode == KeyEvent.VK_Q)
        {
          currentShotType = SIConstants.ShotTypes.NORMAL;
        }

        if (keyCode == KeyEvent.VK_W)
        {
          currentShotType = SIConstants.ShotTypes.DOUBLE;
        }

        if (keyCode == KeyEvent.VK_E)
        {
          currentShotType = SIConstants.ShotTypes.THREE_SPREAD;
        }

        if (keyCode == KeyEvent.VK_R)
        {
          currentShotType = SIConstants.ShotTypes.FIVE_SPREAD;
        }

        if (keyCode == KeyEvent.VK_P)
        {
          gamePaused = true;
        }

        // Handle exiting the game play
        if (keyCode == KeyEvent.VK_ESCAPE)
        {
          requestToQuitPlayingGame = true;
        }

        break;

      case PAUSED:

        if (keyCode == KeyEvent.VK_P)
        {
          gamePaused = false;
        }

        break;

      case EXIT_PLAYING_GAME:

        if (keyCode == KeyEvent.VK_Y)
        {
          quitPlayingGame = true;
        }

        if (keyCode == KeyEvent.VK_N)
        {
          doNotQuitPlayingGame = true;
        }

        break;

      default:
    }
  }

  @Override
  public void gameKeyTyped(int keyCode)
  {
    // NOTE: This method is not currently used
  }

  /////////////////////////////////////////////////////////////////////////////
  //    ____                        __  __      _   _               _     
  //   / ___| __ _ _ __ ___   ___  |  \/  | ___| |_| |__   ___   __| |___ 
  //  | |  _ / _` | '_ ` _ \ / _ \ | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
  //  | |_| | (_| | | | | | |  __/ | |  | |  __/ |_| | | | (_) | (_| \__ \
  //   \____|\__,_|_| |_| |_|\___| |_|  |_|\___|\__|_| |_|\___/ \__,_|___/
  //                                                                      
  /////////////////////////////////////////////////////////////////////////////

  /*
   * Custom draw methods
   */
  private void drawCustomEntityList(Graphics2D g) // Changed from graphics
  {
    // Draw the enemy shots 
    for (int i = 0; i < starryBackground.size(); i++)
    {
      Entity currentStar = starryBackground.get(i);
      currentStar.draw(g);
    }
  }

  /*
   * Methods used for introduction screen
   */

  private void createDemoForIntroductionScreen()
  {
    // Create a small block of aliens.
    clearEnemies();

    for (int row = 0; row < 2; row++)
    {
      //int rowPosition = (int) (GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.36);
      int rowPosition = (int) (screenHeight * 0.36);

      for (int col = 0; col < 6; col++)
      {
        InvaderEntity demoInvader = new InvaderEntity();
        demoInvader.setPosition(-300 + (col * 50), (rowPosition) + row * 30);
        demoInvader.setVelocity(SIConstants.INVADER_INITIAL_VELOCITY * 3.0, 0);
        demoInvader.setColor(SIConstants.INVADER_COLORS[col]);
        addEnemy(demoInvader);
      }
    }
  }

  // TODO: This function might be better suited for the invader entity manager
  private void resumeEnemyMovement(double velocityX, double velocityY)
  {
    invaderEntityManager.resumeFire();

    for (int i = 0; i < getEnemies().size(); i++)
    {
      EnemyEntity currentEnemy = (EnemyEntity) getEnemies().get(i);

      if (currentEnemy.getEnemyType() == SIConstants.EnemyTypes.INVADER)
      {
        currentEnemy.setVelocity(velocityX, velocityY);
      }
    }
  }

  // TODO: This function might be better suited for the invader entity manager
  private void stopEnemiesFromMoving(boolean ceaseFire)
  {
    if (ceaseFire)
    {
      invaderEntityManager.ceaseFire();
    }

    for (int i = 0; i < getEnemies().size(); i++)
    {
      EnemyEntity currentEnemy = (EnemyEntity) getEnemies().get(i);

      if (currentEnemy.getEnemyType() == SIConstants.EnemyTypes.INVADER)
      {
        currentEnemy.setVelocity(0.0, 0.0);
      }
    }
  }

  private void initializeInvaders()
  {
    int pointValue = SIConstants.HIGHEST_INVADER_SCORE;

    // Create a block of aliens (5 rows, by 12 aliens, spaced evenly)
    clearEnemies();
    invaderCount = 0;

    for (int row = 0; row < SIConstants.NUM_INVADER_ROWS; row++)
    {
      for (int col = 0; col < SIConstants.NUM_INVADER_COLS; col++)
      {
        InvaderEntity invader = new InvaderEntity(invaderEntityManager, 20, 780, 560 + getPlayer().getHeight());
        invader.setPosition(32 + (col * 48), (invaderStartingRowPosition) + row * 32);
        invader.setVelocity(SIConstants.INVADER_INITIAL_VELOCITY, 0);
        invader.setColor(SIConstants.INVADER_COLORS[row]);
        invader.setPointValue(pointValue);
        addEnemy(invader);
        invaderCount++;
      }
      pointValue -= SIConstants.INVADER_SCORE_INCREMENT;
    }
  }

  private void initializeMultiHitInvaders()
  {
    int pointValue = SIConstants.HIGHEST_INVADER_SCORE;

    // Create a block of aliens (5 rows, by 12 aliens, spaced evenly)
    clearEnemies();
    invaderCount = 0;

    for (int row = 0; row < SIConstants.NUM_INVADER_ROWS; row++)
    {
      for (int col = 0; col < SIConstants.NUM_INVADER_COLS; col++)
      {
        InvaderEntityMultipleHits invader = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
        invader.setPosition(32 + (col * 48), (invaderStartingRowPosition) + row * 32);
        invader.setVelocity(SIConstants.INVADER_INITIAL_VELOCITY, 0);
        invader.setColor(SIConstants.INVADER_COLORS[row]);
        invader.setNumHits(row);
        invader.setPointValue(pointValue);
        addEnemy(invader);
        invaderCount++;
      }
      pointValue -= SIConstants.INVADER_SCORE_INCREMENT;
    }
  }

  public void drawEarth(Graphics g)
  {
    g.setColor(EARTH_COLOR);
    //g.fillRect(0, (560 + getPlayer().getHeight()), GameEngineConstants.DEFAULT_CANVAS_WIDTH, GameEngineConstants.DEFAULT_CANVAS_HEIGHT);
    g.fillRect(0, (560 + getPlayer().getHeight()), screenWidth, screenHeight);
  }

  // NOTE: This method is not currently used.
  public void drawString(Graphics g, Font font, Color color, String msg, int x, int y)
  {
    // Display the copyright and credits 
    g.setFont(font);
    g.setColor(color);
    g.drawString(msg, x, y);
  }

  public void resetGame()
  {
    // Reset player variables

    // TODO: Consider creating a player manager to encapsulate these actions into a reset method
    invokePlayerDeadState = false;
    gamePaused = false;
    unpauseGame();
    requestToQuitPlayingGame = false;
    quitPlayingGame = false;
    doNotQuitPlayingGame = false;

    getPlayer().reset();
    currentShotType = SIConstants.ShotTypes.NORMAL;

    currentLevel = STARTING_LEVEL;
    invaderStartingRowPosition = 80;

    // Reset the background stars
    starryBackground.clear();
    for (int i = 0; i < NUMBER_OF_BACKGROUND_STARS; i++)
    {
      Entity star = new Entity();
      star.setColor(SIConstants.STAR_COLORS[GameUtility.random.nextInt(SIConstants.STAR_COLORS.length)]);
      int starDimensions = GameUtility.random.nextInt(3) + 1;
      star.setDimensions(starDimensions, starDimensions);
      //star.setPosition(GameUtility.random.nextInt(GameEngineConstants.DEFAULT_CANVAS_WIDTH), GameUtility.random.nextInt(GameEngineConstants.DEFAULT_CANVAS_HEIGHT - 40));
      star.setPosition(GameUtility.random.nextInt(screenWidth), GameUtility.random.nextInt(screenHeight - 40));
      starryBackground.add(star);
    }

    // Reset the managers
    ScoreManager.reset();
    invaderEntityManager.reset();

    nextLevel();
  }

  public void nextLevel()
  {
    shotFired = false;
    currentLevel++;

    // TODO: Depending on the difficult level, maybe start the invaders closer every three levels for beginner, closer every 2 levels for intermediate and closer each level for advanced.

    // Start the invaders a little closer with each successive level. But stop making them closer when the maximum is reached.
    invaderStartingRowPosition += 16;
    if (invaderStartingRowPosition >= 336)
    {
      invaderStartingRowPosition = 336;
    }

    // Clear the player shots when moving to the next level. This will clear the screen before the intro screen for the next level is displayed
    // This is more or less for eye candy to make a clean transition to the next level.
    clearPlayerShot();

    //    if (currentDifficultyLevel == SpaceInvaderConstants.DifficultyLevels.ADVANCED)
    //    {
    //      initializeMultiHitInvaders();
    //    }
    //    else
    //    {
    initializeInvaders();
    //    }

    // Make the player entity visible, alive and in its home position. 
    ((PlayerEntity) getPlayer()).restore();

    playerMovement.reset();
    ufoEntityManager.reset();

    // Prepare the invader entity manager for the next level
    invaderEntityManager.setCurrentLevel(currentLevel);
    maxNumberInvaderShots = invaderEntityManager.computeMaxShots(currentLevel, getEnemies().size());
    invaderEntityManager.computeInvaderShotProbabilityForCurrentLevel();
    invaderEntityManager.setDirection(1);  // Always start the invaders moving right, denoted by the +1 value.
  }

  // NOTE: This method could be moved to the GameUtility class
  protected double calcAngleMoveX(double angle)
  {
    return (double) (Math.cos(angle * Math.PI / 180));
  }

  // NOTE: This method could be moved to the GameUtility class
  protected double calcAngleMoveY(double angle)
  {
    return (double) (Math.sin(angle * Math.PI / 180));
  }

  // NOTE: This method could be moved to the GameUtility class
  private void adjustDirection(Entity sprite, double angle)
  {
    angle = 90 + angle;

    double svx = calcAngleMoveX(angle) * SIConstants.BULLET_SPEED;
    double svy = calcAngleMoveY(angle) * SIConstants.BULLET_SPEED;
    sprite.setVelocity(svx, svy);
  }
}
