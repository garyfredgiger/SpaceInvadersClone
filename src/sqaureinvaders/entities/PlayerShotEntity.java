package sqaureinvaders.entities;

import java.awt.Color;

import sqaureinvaders.constants.SIConstants;
import game.framework.entities.*;

public class PlayerShotEntity extends Entity
{

  // Change back when using a delta
  //public static final int BULLET_SPEED = -200;

  // Change back when not using any delta
  //public static final int BULLET_SPEED = -2;

  private double topOfScreen;

  public PlayerShotEntity(double startingPosition, double endingPosition, double topOfScreen)
  {
    super();

    this.setColor(Color.WHITE);
    this.setDimensions(2, 8);

    // Center the shot so that is exits the players ship at the center
    this.setPosition(startingPosition - this.getWidth() / 2, endingPosition);
    this.setVelocity(0, SIConstants.BULLET_SPEED);
    this.topOfScreen = topOfScreen;
  }

  @Override
  public void updatePosition(double delta)
  {
    // Handle the situation where the shot reaches the top of the screen when it collides with no invaders.
    if (this.isAlive())
    {
      if (position.y < topOfScreen)
      {
        this.kill();
      }
    }

    super.updatePosition(delta);
  }
}
