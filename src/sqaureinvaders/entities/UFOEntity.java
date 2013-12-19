package sqaureinvaders.entities;

import game.framework.utilities.*;

import java.awt.Color;

import sqaureinvaders.constants.SpaceInvaderConstants;
import sqaureinvaders.constants.SpaceInvaderConstants.EnemyTypes;
import sqaureinvaders.managers.UFOEntityManager;

public class UFOEntity extends EnemyEntity
{
  private final int        VERTICAL_POSITION = 60;

  private UFOEntityManager manager;

  public UFOEntity()
  {
    this(null);
  }

  public UFOEntity(UFOEntityManager manager)
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
    this.setPosition(0, VERTICAL_POSITION);
    //this.setVelocity(50, 0);

    this.setEnemyType(SpaceInvaderConstants.EnemyTypes.UFO);
    this.setPointValue((GameUtility.random.nextInt(10) + 1) * 100);
  }

  @Override
  public void updatePosition(double delta)
  {
    if (isAlive())
    {
      // Check if the UFO has reached the other side of the screen. if it has then mark is as being killed.
      if ((this.getPositionX() + this.getWidth()) >= (GameEngineConstants.DEFAULT_CANVAS_WIDTH))
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
