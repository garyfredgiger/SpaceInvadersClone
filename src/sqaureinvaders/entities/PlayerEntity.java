package sqaureinvaders.entities;

import game.framework.entities.Entity;
import game.framework.utilities.GameEngineConstants;

/**
 * 
 * @author ggiger
 * 
 *         This class represents the player's entity in the game.
 * 
 */
public class PlayerEntity extends Entity
{
  /*
   * Class member variables
   */
  private static final int DEFAULT_STARTING_PLAYER_LIVES = 3;

  /*
   * Class instance variables
   */

  // Limits the player movement so the player entity does not move off either side of the screen
  private double           leftScreenBoundary;
  private double           rightScreenBoundary;

  // The initial starting position of the player when the game begins and each time after the player is killed 
  private double           homePositionX;
  private double           homePositionY;

  private int              numberOfLives;

  // TODO: What variables should be required to differentiate between a player being killed after being hit by an enemy and the playter being killed after all lives have been used

  public PlayerEntity()
  {}

  public PlayerEntity(int homePositionX, int homePositionY, double leftScreenBoundary, double rightScreenBoundary)
  {
    super();

    this.homePositionX = homePositionX;
    this.homePositionY = homePositionY;
    this.leftScreenBoundary = leftScreenBoundary;
    this.rightScreenBoundary = rightScreenBoundary;

    this.reset();
  }

  @Override
  public void setVelocityX(double x)
  {
    double playerXPosition = this.position.x;

    // Catch if the player's movement has exceeded the screen bounds, if so zero the velocity so the player will not move past the defined boundaries77
    if (playerXPosition < (leftScreenBoundary + this.getWidth()))
    {
      x = 0;  // This causes the player's entity to stop at boundary and not bounce/jitter when up against the hard boundary.
      playerXPosition = leftScreenBoundary + this.getWidth();
    }

    if ((playerXPosition + this.getWidth()) > (rightScreenBoundary - this.getWidth()))  // 
    {
      x = 0;  // This causes the player's entity to stop at boundary and not bounce/jitter when up against the hard boundary.
      playerXPosition = rightScreenBoundary - 2 * this.getWidth();
    }

    super.setVelocityX(x);
    super.setPositionX(playerXPosition);
  }

  public int getNumberOfLives()
  {
    return numberOfLives;
  }

  /*
   * This method is called when the player still has remaining lives and the next life needs to
   * appear in the home position after being killed previously.  
   */
  public void restore()
  {
    moveToHomePosition();

    // TODO: Determine if it is wise to call the parent class reset() method rather than the individual setAlive() and setVisisble() methods since reset() already provides this functionality.
    super.reset();
  }

  /*
   * (non-Javadoc)
   * @see game.entities.Entity#kill()
   *
   * Each time the player is killed during game play, this method will be called. However, since the player 
   * has multiple lives during game play, another method will need to be added to this class that will set 
   * the alive and visible flags and will return the player entity to the home position.
   */
  @Override
  public void kill()
  {
    // Decrement the number of lives and update the additional lives flag 
    numberOfLives--;

    // A call to the parent method will clear both the visible and dead flags of the player entity.
    super.kill();
  }

  /*
   * (non-Javadoc)
   * @see game.entities.Entity#reset()
   * 
   * This method should be called a new game is started. This method is responsible for the following actions:
   *   - Sets the number of remaining lives as defined by the constant  DEFAULT_STARTING_PLAYER_LIVES and 
   *     sets the flag to indicate that the player has additional lives.
   *   - Moves the players entity to the home position as defined by the x-y coordinates defined in the constructor.
   *   - The reset() method of the parent class is called.
   */
  @Override
  public void reset()
  {
    numberOfLives = DEFAULT_STARTING_PLAYER_LIVES;

    moveToHomePosition();
    super.reset();
  }

  // NOTE: This may need to be made public again.
  // TODO: Consider making this private since it is only called from within other methods in this class
  private void moveToHomePosition()
  {
    position.set(homePositionX, homePositionY);
  }
}
