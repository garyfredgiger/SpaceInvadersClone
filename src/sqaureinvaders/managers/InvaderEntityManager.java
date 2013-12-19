package sqaureinvaders.managers;

import sqaureinvaders.constants.SpaceInvaderConstants;
import sqaureinvaders.constants.SpaceInvaderConstants.DifficultyLevels;

public class InvaderEntityManager
{
  /*
   * Class member variables
   */
  //private static final double INVADER_SPEEDUP_FACTOR         = 1.05;

  // Change back when using a delta
  //public static final int    INVADER_INITIAL_VELOCITY       = 20;

  // Change back when not using any delta
  //public static final int    INVADER_INITIAL_VELOCITY       = 1;

  //private static final int    INVADER_SHOT_PROBABILITY_RANGE = 5000;

  /*
   * Class instance variables
   */
  private boolean updateInvaderLogic;
  private boolean invaderSpeedupRequest;
  private double  invaderSpeedupFactor;
  private boolean invadersHaveLanded;
  private int     direction;
  private boolean ceaseFire;

  // The values in these variables are used in determining if invader should fire a shot or not
  private int     currentLevel;

  private double  userSpecifiedNumberOfShotsDifficultyFactor;
  private double  userSpecifiedShotProbabilityDifficultyFactor;

  private double  currentNumberOfShotsDifficultyFactor;
  private int     currentNumbeOfShots;
  private double  currentShotProbabilityDifficultyFactor;
  private double  invaderShotProbability;

  public InvaderEntityManager()
  {
    userSpecifiedNumberOfShotsDifficultyFactor = SpaceInvaderConstants.INVADER_NUMBER_OF_SHOTS_DIFFICULTY_FACTOR_EASY;
    userSpecifiedShotProbabilityDifficultyFactor = SpaceInvaderConstants.INVADER_SHOT_PROBABILITY_DIFFICULTY_FACTOR_EASY;
    reset();
  }

  /////////////////////////////////////////////////////////////////////////////
  //    ____                        _____                 _     __  __                                                   _   
  //   / ___| __ _ _ __ ___   ___  | ____|_   _____ _ __ | |_  |  \/  | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_ 
  //  | |  _ / _` | '_ ` _ \ / _ \ |  _| \ \ / / _ \ '_ \| __| | |\/| |/ _` | '_ \ / _` |/ _` |/ _ \ '_ ` _ \ / _ \ '_ \| __|
  //  | |_| | (_| | | | | | |  __/ | |___ \ V /  __/ | | | |_  | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_ 
  //   \____|\__,_|_| |_| |_|\___| |_____| \_/ \___|_| |_|\__| |_|  |_|\__,_|_| |_|\__,_|\__, |\___|_| |_| |_|\___|_| |_|\__|
  //                                                                                     |___/                               
  /////////////////////////////////////////////////////////////////////////////

  /*
   * Used to update and manage invader direction
   */

  // Called from within the InvaderEntity class when a specific invader reaches either side of the game 
  // screen. This notifies the manager (and the game) that an update is required. In this case, all 
  // invaders will be dropped down one row closer to the player. 
  public void logicUpdateRequested()
  {
    updateInvaderLogic = true;
  }

  public boolean isLogicUpdateRequired()
  {
    return updateInvaderLogic;
  }

  public void logicUpdateRequestFulfilled()
  {
    updateInvaderLogic = false;
  }

  public void invaderSpeedupRequested()
  {
    invaderSpeedupRequest = true;
  }

  public boolean isInvaderSpeedupRequired()
  {
    return invaderSpeedupRequest;
  }

  public void invaderSpeedupRequestFulfilled()
  {
    invaderSpeedupRequest = false;
  }

  public void setDirection(int direction)
  {
    this.direction = direction;
  }

  public int getDirection()
  {
    return direction;
  }

  //  public double getHorizontalSpeedupFactor()
  //  {
  //    return INVADER_SPEEDUP_FACTOR;
  //  }

  /*
   * Used to indicate when invaders have landed
   */
  public void signalInvadesHaveLanded()
  {
    invadersHaveLanded = true;
  }

  public boolean invadersHaveLanded()
  {
    return invadersHaveLanded;
  }

  /*
   * Called when the player begins a new game.
   */
  public void reset()
  {
    updateInvaderLogic = false;
    invadersHaveLanded = false;
    ceaseFire = false;
    direction = 1;    // Assume invaders are moving right when starting
    currentLevel = 1;
    currentNumberOfShotsDifficultyFactor = userSpecifiedNumberOfShotsDifficultyFactor;
    currentShotProbabilityDifficultyFactor = userSpecifiedShotProbabilityDifficultyFactor;
  }

  /////////////////////////////////////////////////////////////////////////////
  //   ____  _  __  __ _            _ _                           _   _                   _   __  __                                                   _   
  //  |  _ \(_)/ _|/ _(_) ___ _   _| | |_ _   _    __ _ _ __   __| | | |    _____   _____| | |  \/  | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_ 
  //  | | | | | |_| |_| |/ __| | | | | __| | | |  / _` | '_ \ / _` | | |   / _ \ \ / / _ \ | | |\/| |/ _` | '_ \ / _` |/ _` |/ _ \ '_ ` _ \ / _ \ '_ \| __|
  //  | |_| | |  _|  _| | (__| |_| | | |_| |_| | | (_| | | | | (_| | | |__|  __/\ V /  __/ | | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_ 
  //  |____/|_|_| |_| |_|\___|\__,_|_|\__|\__, |  \__,_|_| |_|\__,_| |_____\___| \_/ \___|_| |_|  |_|\__,_|_| |_|\__,_|\__, |\___|_| |_| |_|\___|_| |_|\__|
  //                                      |___/                                                                        |___/                               
  //
  /////////////////////////////////////////////////////////////////////////////

  public void setNumberOfShotsDifficultyFactor(SpaceInvaderConstants.DifficultyLevels level)
  {
    switch (level)
    {
      case INTERMEDIATE:
        invaderSpeedupFactor = SpaceInvaderConstants.INVADER_SPEEDUP_FACTOR_INTERMEDIATE;
        currentNumbeOfShots = SpaceInvaderConstants.INVADER_STARTING_NUMBER_OF_SHOTS_INTERMEDIATE;
        userSpecifiedNumberOfShotsDifficultyFactor = SpaceInvaderConstants.INVADER_NUMBER_OF_SHOTS_DIFFICULTY_FACTOR_INTERMEDIATE;
        break;

      case ADVANCED:
        invaderSpeedupFactor = SpaceInvaderConstants.INVADER_SPEEDUP_FACTOR_ADVANCED;
        currentNumbeOfShots = SpaceInvaderConstants.INVADER_STARTING_NUMBER_OF_SHOTS_ADVANCED;
        userSpecifiedNumberOfShotsDifficultyFactor = SpaceInvaderConstants.INVADER_NUMBER_OF_SHOTS_DIFFICULTY_FACTOR_ADVANCED;
        break;

      default:
        invaderSpeedupFactor = SpaceInvaderConstants.INVADER_SPEEDUP_FACTOR_EASY;
        currentNumbeOfShots = SpaceInvaderConstants.INVADER_STARTING_NUMBER_OF_SHOTS_EASY;
        userSpecifiedNumberOfShotsDifficultyFactor = SpaceInvaderConstants.INVADER_NUMBER_OF_SHOTS_DIFFICULTY_FACTOR_EASY;
    }
  }

  public void setShotProbabilityDifficultyFactor(SpaceInvaderConstants.DifficultyLevels level)
  {
    switch (level)
    {
      case INTERMEDIATE:
        userSpecifiedShotProbabilityDifficultyFactor = SpaceInvaderConstants.INVADER_SHOT_PROBABILITY_DIFFICULTY_FACTOR_INTERMEDIATE;
        break;

      case ADVANCED:
        userSpecifiedShotProbabilityDifficultyFactor = SpaceInvaderConstants.INVADER_SHOT_PROBABILITY_DIFFICULTY_FACTOR_ADVANCED;
        break;

      default:

        userSpecifiedShotProbabilityDifficultyFactor = SpaceInvaderConstants.INVADER_SHOT_PROBABILITY_DIFFICULTY_FACTOR_EASY;
    }
  }

  public double getNumberOfShotsDifficultyFactor()
  {
    return currentNumberOfShotsDifficultyFactor;
  }

  public double getShotProbabilityDifficultyFactor()
  {
    return currentShotProbabilityDifficultyFactor;
  }

  public int getCurrentLevel()
  {
    return currentLevel;
  }

  public void setCurrentLevel(int level)
  {
    this.currentLevel = level;
  }

  public double getInvaderSpeedupFactor()
  {
    return invaderSpeedupFactor;
  }

  public int computeMaxShots(int level, int numberInvaders)
  {
    currentNumbeOfShots += userSpecifiedNumberOfShotsDifficultyFactor;
    return currentNumbeOfShots;
    //return (int) Math.ceil(((currentLevel + numberInvaders * 1.0) / currentNumberOfShotsDifficultyFactor) + currentLevel);
  }

  public void computeInvaderShotProbabilityForCurrentLevel()
  {
    invaderShotProbability = currentLevel * currentShotProbabilityDifficultyFactor;
    //invaderShotProbability = (currentLevel + currentShotProbabilityDifficultyFactor) / (currentShotProbabilityDifficultyFactor + currentLevel + INVADER_SHOT_PROBABILITY_RANGE);
  }

  public double getInvaderShotProbabilityForCurrentLevel()
  {
    return invaderShotProbability;
  }

  /////////////////////////////////////////////////////////////////////////////
  //   _____ _            __  __                                                   _   
  //  |  ___(_)_ __ ___  |  \/  | __ _ _ __   __ _  __ _  ___ _ __ ___   ___ _ __ | |_ 
  //  | |_  | | '__/ _ \ | |\/| |/ _` | '_ \ / _` |/ _` |/ _ \ '_ ` _ \ / _ \ '_ \| __|
  //  |  _| | | | |  __/ | |  | | (_| | | | | (_| | (_| |  __/ | | | | |  __/ | | | |_ 
  //  |_|   |_|_|  \___| |_|  |_|\__,_|_| |_|\__,_|\__, |\___|_| |_| |_|\___|_| |_|\__|
  //                                               |___/                               
  //
  /////////////////////////////////////////////////////////////////////////////

  // Used to stop all invader fire after the player is hit by an enemy invader shot
  public void ceaseFire()
  {
    ceaseFire = true;
  }

  // Used to resume all invader fire after the players life is restored from a previous hit
  public void resumeFire()
  {
    ceaseFire = false;
  }

  // Used to check if the invaders have ceased fire by each invader
  public boolean hasCeasedFire()
  {
    return ceaseFire;
  }
}
