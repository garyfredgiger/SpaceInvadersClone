package sqaureinvaders.managers;

import sqaureinvaders.constants.SIConstants;
import game.framework.utilities.GameUtility;

// TODO: Should this be made a singleton
public class UFOEntityManager
{
  /*
   * Class instance variables
   */
  private boolean             ufoInFlight;

  private long                lastUFOLaunchTime;
  private long                minTimeBetweenUFOLaunches;
  private double              currentProbabilityTolaunchANewUFO;
  private double              randomProbability;

  public UFOEntityManager()
  {
    ufoInFlight = false;
    lastUFOLaunchTime = System.currentTimeMillis();
    currentProbabilityTolaunchANewUFO = SIConstants.DEFAULT_PROB_TO_LAUNCH_UFO;
    minTimeBetweenUFOLaunches = SIConstants.DEFAULT_MIN_TIME_BETWEEN_UFO_LAUNCHES;
  }

  /*
   *  Allows the probability for generating a UFO to cross the screen to be changed during game play.
   *  This can be done as a function of game difficulty or level number (e.g., as higher levels are
   *  reached increase the probability of UFO occurrences).  
   */
  public void setNewProbabilityToLaunchUFO(double prob)
  {
    // Make sure the probability of launching a UFO is between (1.0 and DEFAULT_PROB_TO_LAUNCH_UFO], otherwise use the default value
    if ((prob > 1.0 - SIConstants.EPSILON) || (prob <= SIConstants.DEFAULT_PROB_TO_LAUNCH_UFO))
    {
      currentProbabilityTolaunchANewUFO = SIConstants.DEFAULT_PROB_TO_LAUNCH_UFO;
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
      minTimeBetweenUFOLaunches = SIConstants.DEFAULT_MIN_TIME_BETWEEN_UFO_LAUNCHES;
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
    return SIConstants.UFO_SPEEDS[GameUtility.random.nextInt(SIConstants.UFO_SPEEDS.length)];
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
