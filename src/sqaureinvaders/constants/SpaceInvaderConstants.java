package sqaureinvaders.constants;

import java.awt.Color;

public class SpaceInvaderConstants
{
  public static enum EnemyTypes
  {
    INVADER, UFO
  };

  public static enum DifficultyLevels
  {
    BEGINNER, INTERMEDIATE, ADVANCED;

    public DifficultyLevels getNext()
    {
      return values()[(ordinal() + 1) % values().length];
    }
  };

  /*
   * Constants for enemy entities (both UFOs and Invaders)
   */
  public static final Color    VIOLET                                                  = new Color(138, 43, 226);
  public static final Color    INDIGO                                                  = new Color(75, 0, 130);
  public static final Color[]  INVADER_COLORS                                          = new Color[] { Color.RED, VIOLET, Color.BLUE, Color.GREEN, Color.ORANGE, Color.YELLOW };
  public static final Color[]  STAR_COLORS                                             = new Color[] { Color.WHITE, Color.LIGHT_GRAY, Color.DARK_GRAY, Color.RED.darker(), Color.ORANGE.darker(), Color.YELLOW.darker(), Color.GREEN.darker(), Color.BLUE.darker(), INDIGO.darker(), VIOLET.darker() };
  public static final int      INITIAL_BULLET_SPEED                                    = 120;                                                                                                                                                                                                          // Invader shot speed
  public static final int      INVADER_INITIAL_VELOCITY                                = 20;
  public static final int      NUM_INVADER_ROWS                                        = SpaceInvaderConstants.INVADER_COLORS.length;
  public static final int      NUM_INVADER_COLS                                        = 12;
  public static final int      NUM_INVADERS                                            = NUM_INVADER_ROWS * NUM_INVADER_COLS;
  public static final double[] UFO_SPEEDS                                              = new double[] { 50, 75, 100, 125, 150, 175, 200 };
  public static final int                       HIGHEST_INVADER_SCORE      = 60;
  public static final int                       INVADER_SCORE_INCREMENT    = 10;
  
  /*
   *  Constants for the player's entity 
   */
  public static final int      PLAYER_VELOCITY                                         = 100;
  public static final int      BULLET_SPEED                                            = -200;
  public static final Color    PLAYER_COLOR                                            = Color.LIGHT_GRAY;

  /*
   * Numeric constants used in the game
   */

  // Difficulty parameters for number of invader shots
  // NOTE: Higher number represents easier factor since this number is used to divide into the number of invaders
  public static final double   INVADER_NUMBER_OF_SHOTS_DIFFICULTY_FACTOR_EASY          = 1.0;
  public static final double   INVADER_NUMBER_OF_SHOTS_DIFFICULTY_FACTOR_INTERMEDIATE  = 1.0;
  public static final double   INVADER_NUMBER_OF_SHOTS_DIFFICULTY_FACTOR_ADVANCED      = 2.0;

  public static final int      INVADER_STARTING_NUMBER_OF_SHOTS_EASY                   = 5;
  public static final int      INVADER_STARTING_NUMBER_OF_SHOTS_INTERMEDIATE           = 8;
  public static final int      INVADER_STARTING_NUMBER_OF_SHOTS_ADVANCED               = 10;

  // Difficulty parameters for number of invader shots
  // NOTE: Higher number represents easier factor since this number is used to divide into the number of invaders
  public static final double   INVADER_SHOT_PROBABILITY_DIFFICULTY_FACTOR_EASY         = 0.0005;                                                                                                                                                                                                       //1.0;  // Was 1 
  public static final double   INVADER_SHOT_PROBABILITY_DIFFICULTY_FACTOR_INTERMEDIATE = 0.001;                                                                                                                                                                                                        //5.0;
  public static final double   INVADER_SHOT_PROBABILITY_DIFFICULTY_FACTOR_ADVANCED     = 0.002;                                                                                                                                                                                                        //10.0;

  // TODO: Consider adding a speedup factor increment each time a new level is reached. e.g., for easy the increment could be 0.05 added to the easy speedup factor for each new level

  // Values used when delta is used
  public static final double   INVADER_SPEEDUP_FACTOR_EASY                             = 2.00;
  public static final double   INVADER_SPEEDUP_FACTOR_INTERMEDIATE                     = 3.00;
  public static final double   INVADER_SPEEDUP_FACTOR_ADVANCED                         = 4.00;

  // Values used without the delta
  //  public static final double   INVADER_SPEEDUP_FACTOR_EASY                             = 0.025;
  //  public static final double   INVADER_SPEEDUP_FACTOR_INTERMEDIATE                     = 0.035;
  //  public static final double   INVADER_SPEEDUP_FACTOR_ADVANCED                         = 0.050;

  /*
   * String constants used in the game
   */

  // Main title
  public static final String   MSG_INTRO_SCREEN_MAIN_TITLE                             = "Square Invaders";

  // Messages for the introduction screen 1 (Main Screen)
  public static final String   MSG_INTRO_SCREEN1_SUB_TITLE                             = "Another Clone of a Great Arcade Classic";
  public static final String   MSG_INTRO_SCREEN1_PRESS_SPACE                           = "Press 'SPACE' to Begin Game";
  public static final String   MSG_INTRO_SCREEN1_PRESS_I                               = "Press 'I' for Game Instructions";
  public static final String   MSG_INTRO_SCREEN1_PRESS_D                               = "Press 'D' to Change Game Difficulty";
  public static final String   MSG_INTRO_SCREEN1_CREDIT                                = "Written and Developed by Gary Giger";
  public static final String   MSG_INTRO_SCREEN1_COPYRIGHT                             = "\u00a9 2013";
  public static final String   MSG_INTRO_SCREEN1_DIFFICULTY_LABEL                      = "Selected Difficulty Level";
  public static final String   MSG_INTRO_SCREEN1_DIFFICULTY_BEGINNER                   = "BEGINNER";
  public static final String   MSG_INTRO_SCREEN1_DIFFICULTY_INTERMEDIATE               = "INTERMEDIATE";
  public static final String   MSG_INTRO_SCREEN1_DIFFICULTY_ADVANCED                   = "ADVANCED";

  // Messages for the introduction screen 2 (Instructions)
  public static final String   MSG_INTRO_SCREEN2_POINT_VALUES                          = "Invader Point Values";
  public static final String   MSG_INTRO_SCREEN2_POINTS                                = " Points";
  public static final String   MSG_INTRO_SCREEN2_MYSTERY                               = "??";
  public static final String   MSG_INTRO_SCREEN2_INST_TITLE                            = "Instructions";
  public static final String   MSG_INTRO_SCREEN2_LEFT_ARROW                            = "\u21E6";
  public static final String   MSG_INTRO_SCREEN2_RIGHT_ARROW                           = "\u21E8";
  public static final String   MSG_INTRO_SCREEN2_INST_MOVE_LEFT                        = " - Move Left";                                                                                                                                                                                               //"Use Left/Right Arrow Keys to Move";
  public static final String   MSG_INTRO_SCREEN2_INST_MOVE_RIGHT                       = " - Move Right";                                                                                                                                                                                              //"Use Left/Right Arrow Keys to Move";
  public static final String   MSG_INTRO_SCREEN2_INST_FIRE                             = "SPACE - Fire";                                                                                                                                                                                               //"Use Space Bar to Shoot at Invaders";
  public static final String   MSG_INTRO_SCREEN2_INST_PAUSE                            = "P - Pause Game";
  public static final String   MSG_INTRO_SCREEN2_PRESS_ENTER                           = "Press 'ENTER/RETURN' to return to main menu";

  // Messages for the game screen
}
