package sqaureinvaders.entities;

import java.awt.Color;

import sqaureinvaders.constants.SIConstants;
import sqaureinvaders.constants.SIConstants.EnemyTypes;
import sqaureinvaders.managers.InvaderEntityManager;
import game.framework.utilities.GameUtility;

public class InvaderEntity extends EnemyEntity
{
  /*
   * Class member variables
   */

  // Constants used in determining when a shot should be fired
  public static final int      DEFAULT_TIME_INTERVAL_BETWEEN_SHOTS = 500;                     // Space out the time enough between shots so they do not gang up
  private static final int[]   TIME_VARIANCE_FOR_SHOT_INTERVAL     = new int[] { 1, 2, 3, 4 };

  private int                  leftScreenBoundary;
  private int                  rightScreenBoundary;
  private int                  bottomOfScreen;

  private InvaderEntityManager manager;

  private long                 lastShotTime;
  private long                 currentTimeIntervalBetweenShots;

  public InvaderEntity()
  {
    this(null, 0, 0, 0);
  }

  public InvaderEntity(InvaderEntityManager manager, int leftScreenBoundary, int rightScreenBoundary, int bottomOfScreen)
  {
    super();
    this.manager = manager;
    this.leftScreenBoundary = leftScreenBoundary;
    this.rightScreenBoundary = rightScreenBoundary;
    this.bottomOfScreen = bottomOfScreen;
    this.setEnemyType(SIConstants.EnemyTypes.INVADER);

    // Initialize variables responsible for 
    lastShotTime = System.currentTimeMillis();
    currentTimeIntervalBetweenShots = DEFAULT_TIME_INTERVAL_BETWEEN_SHOTS * TIME_VARIANCE_FOR_SHOT_INTERVAL[GameUtility.random.nextInt(TIME_VARIANCE_FOR_SHOT_INTERVAL.length)];
  }

  @Override
  public void updatePosition(double delta)
  {
    // proceed with normal move
    super.updatePosition(delta);

    if (manager == null)
    {
      return;
    }

    // If we have reached the left hand side of the screen and are moving left (x velocity < 0) then request a logic update to drop down
    if ((velocity.x < 0) && (position.x < leftScreenBoundary))
    {
      //System.out.println("Logic Update Requested - Moving Left");
      manager.logicUpdateRequested();
    }

    // If we have reached the right hand side of the screen and are moving right (x velocity > 0), request a logic update to drop down
    if ((velocity.x > 0) && ((position.x + width) > rightScreenBoundary))
    {
      //System.out.println("Logic Update Requested - Moving Right");
      manager.logicUpdateRequested();
    }
  }

  /**
   * 
   * 
   * @return true if a shot should be fired, false otherwise
   */
  public boolean shouldFireShot()
  {
    if (manager == null)
    {
      return false;
    }

    if (manager.hasCeasedFire())
    {
      return false;
    }

    if (System.currentTimeMillis() < (lastShotTime + currentTimeIntervalBetweenShots))
    {
      return false;
    }

    //if (GameUtility.random.nextDouble() < ((manager.getCurrentLevel() + CHANCE) / manager.getDifficultyFactor() / RANGE))

    if (GameUtility.random.nextDouble() < (manager.getInvaderShotProbabilityForCurrentLevel()))
    {
      lastShotTime = System.currentTimeMillis();
      return true;
    }

    //    if (GameUtility.random.nextDouble() < 0.001)
    //    {
    //      lastShotTime = System.currentTimeMillis();
    //      return true;
    //    }

    return false;
  }

  /**
   * Do the logic associated with this entity. This method will be called periodically based on game events
   */
  @Override
  public void doLogic()
  {
    // Reverse the horizontal movement and move invader down to the next row
    velocity.x = -velocity.x;
    position.y += this.getHeight();

    // Get the direction from the x component of the velocity
    // TODO: Determine if there is a better way to change the direction
    manager.setDirection((velocity.x == Math.abs(velocity.x)) ? 1 : -1);

    // if we've reached the bottom of the screen then the aliens have landed and the game is over.
    if ((position.y + this.getHeight()) >= bottomOfScreen)
    {
      manager.signalInvadesHaveLanded();
    }
  }
}
