package squareinvaders.entities;

import game.framework.entities.shapes.EntityRectangle;

import java.awt.Color;

public class PowerUpEntity extends EntityRectangle
{

  public PowerUpEntity()
  {
    super();

    this.setColor(Color.CYAN);

    // Center the shot so that is exits the players ship at the center
    this.setDimensions(24, 16);
  }
}
