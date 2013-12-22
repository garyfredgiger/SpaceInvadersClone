package squareinvaders.entities;

import game.framework.utilities.*;

import java.awt.Color;

import squareinvaders.constants.SIConstants;
import squareinvaders.managers.UFOEntityManager;

public class UFOEntity extends EnemyEntity
{
  private int rightScreenBoundary;
  private UFOEntityManager manager;

  public UFOEntity()
  {
    this(null, 0);
  }

  public UFOEntity(UFOEntityManager manager, int userDefinedRightScreenBoundary)
  {
    super();

    if (manager != null)
    {
      this.manager = manager;
      this.setVelocity(this.manager.getUFOSpeed(), 0);
    }

    this.setColor(Color.PINK);
    this.setDimensions(32, 16);

    // Center the shot so that is exits the players ship at the center
    this.setPosition(0, SIConstants.UFO_VERTICAL_POSITION);

    this.setEnemyType(SIConstants.EnemyTypes.UFO);
    this.setPointValue((GameUtility.random.nextInt(10) + 1) * 100);
    
    rightScreenBoundary = userDefinedRightScreenBoundary;
  }

  @Override
  public void updatePosition(double delta)
  {
    if (isAlive())
    {
      // Check if the UFO has reached the other side of the screen. if it has then mark is as being killed.
      if ((this.getPositionX() + this.getWidth()) >= rightScreenBoundary)
      {
        this.kill();
      }
    }

    super.updatePosition(delta);
  }

  @Override
  public void kill()
  {
    if (manager != null)
    {
      manager.reset();
    }
    super.kill();
  }
}
