package sqaureinvaders.entities;

import game.framework.entities.Entity;
import java.awt.Color;

public class PowerUpEntity extends Entity
{

  public PowerUpEntity()
  {
    super();

    this.setColor(Color.CYAN);

    // Center the shot so that is exits the players ship at the center
    this.setDimensions(24, 16);
  }
}
