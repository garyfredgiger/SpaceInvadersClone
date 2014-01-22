package squareinvaders.entities;

import squareinvaders.constants.SIConstants;
import game.framework.entities.shapes.EntityRectangle;
import game.framework.utilities.GameEngineConstants;

public class EnemyEntity extends EntityRectangle
{
  private int                    pointValue;
  private SIConstants.EnemyTypes enemyType;

  //private boolean ceaseFire;

  public EnemyEntity()
  {
    super(GameEngineConstants.EntityTypes.ENEMY);
  }

  public void doLogic()
  {}

  public void setPointValue(int value)
  {
    pointValue = value;
  }

  public int getPointValue()
  {
    return pointValue;
  }

  public void setEnemyType(SIConstants.EnemyTypes type)
  {
    enemyType = type;
  }

  public SIConstants.EnemyTypes getEnemyType()
  {
    return enemyType;
  }

  // Should the cease file be part of the enemy entities themselves or dictated by the enemy manager
  //  public void ceaseFire()
  //  {
  //    ceaseFire = true;
  //  }
  //  
  //  public void resumeFire()
  //  {
  //    ceaseFire = false;
  //  }
  //  
  //  public boolean hasCeasedFire()
  //  {
  //    return ceaseFire;
  //  }
}
