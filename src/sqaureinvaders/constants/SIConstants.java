package sqaureinvaders.constants;

import java.awt.Color;

public class SIConstants
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
  public static final int      NUM_INVADER_ROWS                                        = SIConstants.INVADER_COLORS.length;
  public static final int      NUM_INVADER_COLS                                        = 12;
  public static final int      NUM_INVADERS                                            = NUM_INVADER_ROWS * NUM_INVADER_COLS;
  public static final double[] UFO_SPEEDS                                              = new double[] { 50, 75, 100, 125, 150, 175, 200 };
  public static final int      HIGHEST_INVADER_SCORE                                   = 60;
  public static final int      INVADER_SCORE_INCREMENT                                 = 10;

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
}
