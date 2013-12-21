package sqaureinvaders.entities;

import java.awt.Color;

import sqaureinvaders.constants.SIConstants;
import sqaureinvaders.managers.InvaderEntityManager;

public class InvaderEntityMultipleHits extends InvaderEntity
{
  // Code to handle number of hits for the invader
  private int numberOfHits;

  public InvaderEntityMultipleHits(InvaderEntityManager manager, int leftScreenBoundary, int rightScreenBoundary, int bottomOfScreen)
  {
    super(manager, leftScreenBoundary, rightScreenBoundary, bottomOfScreen);

    // Code to handle multiple hits
    numberOfHits = 0;
  }

  public void setNumHits(int hits)
  {
    numberOfHits = hits;
  }

  /*
   * (non-Javadoc)
   * @see game.entities.Entity#kill()
   * 
   * { Color.RED, VIOLET, Color.BLUE, Color.GREEN, Color.ORANGE, Color.YELLOW }
   */
  @Override
  public void kill()
  {
    numberOfHits++;

    switch (numberOfHits)
    {
    // Change color from RED to VIOLET
      case 1:
        this.setColor(SIConstants.INVADER_COLORS[1]);
        break;

      // Change color from VIOLET to BLUE
      case 2:
        this.setColor(SIConstants.INVADER_COLORS[2]);
        break;

      // Change color from BLUE to GREEN  
      case 3:
        this.setColor(SIConstants.INVADER_COLORS[3]);
        break;

      // Change color from GREEN to ORANGE
      case 4:
        this.setColor(SIConstants.INVADER_COLORS[4]);
        break;
      // Change color from ORANGE to YELLOW  
      case 5:
        this.setColor(SIConstants.INVADER_COLORS[5]);
        break;

      // Change color from YELLOW to WHITE
      case 6:
        this.setColor(Color.WHITE);
        break;

      // Once the invader is white, it is ready to die the next time it is hit
      default:
        super.kill();
    }
  }
}
