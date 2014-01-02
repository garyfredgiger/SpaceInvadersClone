package squareinvaders.managers;

import squareinvaders.constants.SIConstants;

public class ScoreManager
{
  private static int currentScore;
  public static int previousNewLifeValue;
  
  private static boolean eligibleForAnExtraLife = false;

  public static void reset()
  {
    currentScore = 0;
    previousNewLifeValue = 0;
    eligibleForAnExtraLife = false;
  }

  public static void incrementScore(int value)
  {
    currentScore += value;
    
    // Take current score and div it by the new life score increment
    int currentNewLifeValue = currentScore / SIConstants.NEW_LIFE_SCORE;

    // If the value is one more than the previous value, that means that another 10000 has been reached and a new life is eligible
    if (currentNewLifeValue > previousNewLifeValue)
    {
      previousNewLifeValue = currentNewLifeValue;
      eligibleForAnExtraLife = true;
    }
  }

  public static int getScore()
  {
    return currentScore;
  }
  
  public static boolean checkForExtraLifeEligibility()
  {
    return eligibleForAnExtraLife;
  }

  public static void extraLifeAdded()
  {
    eligibleForAnExtraLife = false;
  }  
}
