package sqaureinvaders.entities;

import java.awt.Color;

import sqaureinvaders.constants.SpaceInvaderConstants;
import game.framework.entities.Entity;

public class EnemyShotEntity extends Entity
{
  // Change back when using a delta
  //public static final int INITIAL_BULLET_SPEED = 120; // Was 100

  // Change back when not using any delta
  //public static final int INITIAL_BULLET_SPEED = 4; //120; // Was 100

  private double bottomOfScreen;

  public EnemyShotEntity(double startingPosition, double endingPosition, double bottomOfScreen)
  {
    //    super();
    //
    //    this.setColor(Color.WHITE);
    //    this.setDimensions(2, 12);
    //    
    //    // Center the shot so that is exits the players ship at the center
    //    this.setPosition(startingPosition - this.getWidth()/2, endingPosition);
    //    this.setVelocity(0, BULLET_SPEED);
    //    this.bottomOfScreen = bottomOfScreen;
    this(startingPosition, endingPosition, bottomOfScreen, 1);
  }

  public EnemyShotEntity(double startingPosition, double endingPosition, double bottomOfScreen, double speedUpValue)
  {
    super();

    this.setColor(Color.WHITE);
    this.setDimensions(2, 12);

    // Center the shot so that is exits the players ship at the center
    this.setPosition(startingPosition - this.getWidth() / 2, endingPosition);
    this.setVelocity(0, SpaceInvaderConstants.INITIAL_BULLET_SPEED * speedUpValue);
    this.bottomOfScreen = bottomOfScreen;
  }

  @Override
  public void updatePosition(double delta)
  {
    // Handle the situation where the shot reaches the top of the screen when it collides with no invaders.
    if (this.isAlive())
    {
      if (position.y > bottomOfScreen)
      {
        this.kill();
      }
    }

    super.updatePosition(delta);
  }
}