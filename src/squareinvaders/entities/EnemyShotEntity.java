package squareinvaders.entities;

import java.awt.Color;

import squareinvaders.constants.SIConstants;
import game.framework.entities.shapes.EntityRectangle;;;

public class EnemyShotEntity extends EntityRectangle
{
  private double bottomOfScreen;

  public EnemyShotEntity(double startingPosition, double endingPosition, double bottomOfScreen)
  {
    this(startingPosition, endingPosition, bottomOfScreen, 1);
  }

  public EnemyShotEntity(double startingPosition, double endingPosition, double bottomOfScreen, double speedUpValue)
  {
    super();

    this.setColor(Color.WHITE);
    
    // 
    this.setDimensions(SIConstants.INVADER_SHOT_WIDTH, SIConstants.INVADER_SHOT_LENGTH);

    // Center the shot so that is exits the players ship at the center
    this.setPosition(startingPosition - this.getWidth() / 2, endingPosition);
    this.setVelocity(0, SIConstants.INITIAL_BULLET_SPEED * speedUpValue);
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
