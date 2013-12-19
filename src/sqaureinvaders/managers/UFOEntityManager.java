package sqaureinvaders.managers;

import sqaureinvaders.constants.SpaceInvaderConstants;
import game.framework.utilities.GameUtility;

// TODO: Should this be made a singleton
public class UFOEntityManager
{
  /*
   * Class member variables
   */
  private static final long   DEFAULT_MIN_TIME_BETWEEN_UFO_LAUNCHES = 30000;  // Time is in ms
  private static final double DEFAULT_PROB_TO_LAUNCH_UFO            = 0.0005;
  private static final double EPSILON                               = 0.00001;  // Used as threshold when comparing doubles

  /*
   * Class instance variables
   */
  private boolean             ufoInFlight;

  private long                lastUFOLaunchTime;
  private long                minTimeBetweenUFOLaunches;
  private double              currentProbabilityTolaunchANewUFO;
  private double              randomProbability;

  // Change back when using a delta
  //private double[] ufoSpeeds = new double[] {50, 75, 100, 125, 150, 175, 200};

  // Change back when not using any delta
  //private double[] ufoSpeeds = new double[] {1, 2, 3, 4, 5, 6, 7};

  public UFOEntityManager()
  {
    ufoInFlight = false;
    lastUFOLaunchTime = System.currentTimeMillis();
    currentProbabilityTolaunchANewUFO = DEFAULT_PROB_TO_LAUNCH_UFO;
    minTimeBetweenUFOLaunches = DEFAULT_MIN_TIME_BETWEEN_UFO_LAUNCHES;
  }

  /*
   *  Allows the probability for generating a UFO to cross the screen to be changed during game play.
   *  This can be done as a function of game difficulty or level number (e.g., as higher levels are
   *  reached increase the probability of UFO occurrences).  
   */
  public void setNewProbabilityToLaunchUFO(double prob)
  {
    // Make sure the probability of launching a UFO is between (1.0 and DEFAULT_PROB_TO_LAUNCH_UFO], otherwise use the default value
    if ((prob > 1.0 - EPSILON) || (prob <= DEFAULT_PROB_TO_LAUNCH_UFO))
    {
      currentProbabilityTolaunchANewUFO = DEFAULT_PROB_TO_LAUNCH_UFO;
    }
    else
    {
      currentProbabilityTolaunchANewUFO = prob;
    }
  }

  /*
   * 
   */
  public void setNewMinTimeBetweenUFOLaunches(long minTimeInMs)
  {
    if (minTimeInMs <= 0)
    {
      minTimeBetweenUFOLaunches = DEFAULT_MIN_TIME_BETWEEN_UFO_LAUNCHES;
    }
    else
    {
      minTimeBetweenUFOLaunches = minTimeInMs;
    }
  }

  /*
   * Determine if a UFO should be launched
   */
  public boolean ufoShouldBeLaunched()
  {
    // Check that there is no current UFO in flight. If there is, simply exit this method
    if (ufoInFlight)
    {
      return false;
    }

    // Check if the minimum time has elapsed for launching a UFO.
    if (System.currentTimeMillis() < (lastUFOLaunchTime + minTimeBetweenUFOLaunches))
    {
      return false;
    }

    // If there is no current UFO in flight, determine if a new UFO should be launched.
    randomProbability = GameUtility.random.nextDouble();
    if (randomProbability <= currentProbabilityTolaunchANewUFO)
    {
      // Set the flag that indicates a new UFO is in flight
      ufoInFlight = true;
      lastUFOLaunchTime = System.currentTimeMillis();
      return true;
    }

    return false;
  }

  public double getUFOSpeed()
  {
    return SpaceInvaderConstants.UFO_SPEEDS[GameUtility.random.nextInt(SpaceInvaderConstants.UFO_SPEEDS.length)];
  }

  /*
   * This is used for debugging purposes 
   */
  public double getProbability()
  {
    return randomProbability;
  }

  /*
   * Once the UFO has finished its flight, this is called to clear the UFO flight flag
   */
  public void reset()
  {
    ufoInFlight = false;
  }
}
