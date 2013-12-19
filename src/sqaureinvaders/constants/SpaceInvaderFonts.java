package sqaureinvaders.constants;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class SpaceInvaderFonts
{
  /*
   * Class member variables
   */

  // Fonts used in the introduction screen 1
  public static final Font FONT_INTRO_SCREEN_MAIN_TITLE  = new Font("Comic Sans MS", Font.BOLD, 68);
  public static final Font FONT_INTRO_SCREEN1_SUB_TITLE  = new Font("Comic Sans MS", Font.BOLD, 24);
  public static final Font FONT_INTRO_SCREEN1_INST       = new Font("Courier", Font.BOLD, 20);        // Century Gothic
  public static final Font FONT_INTRO_SCREEN1_CREDIT     = new Font("Courier", Font.BOLD, 18);
  public static final Font FONT_INTRO_SCREEN1_DIFFICULTY = new Font("Comic Sans MS", Font.BOLD, 28);

  // Fonts used in the introduction screen 2
  public static final Font FONT_INTRO_SCREEN2_SCORE      = new Font("Courier", Font.BOLD, 18);
  public static final Font FONT_INTRO_SCREEN2_INST       = new Font("Century Gothic", Font.BOLD, 16);
  public static final Font FONT_INTRO_SCREEN2_INST_TYPE2 = new Font("Century Gothic", Font.PLAIN, 20);
  public static final Font FONT_INTRO_SCREEN2_SUB_TITLE  = new Font("Courier", Font.BOLD, 24);
  public static final Font FONT_INTRO_SCREEN2_ARROW      = new Font("Courier", Font.BOLD, 48);

  // Fonts used when the game is in play
  //  public static final Font   FONT_PLAYING_LEVEL = new Font("Courier", Font.BOLD, 20);
  //  public static final Font   FONT_PLAYING_SCORE = new Font("Courier", Font.BOLD, 20);
  //  public static final Font   FONT_PLAYING_LIVES = new 7Font("Courier", Font.BOLD, 20);

  public static final Font FONT_PLAYING_LEVEL            = new Font("Comic Sans MS", Font.BOLD, 20);
  public static final Font FONT_PLAYING_SCORE            = new Font("Comic Sans MS", Font.BOLD, 20);
  public static final Font FONT_PLAYING_LIVES            = new Font("Comic Sans MS", Font.BOLD, 20);

  // Not used. This will remain a class with static members
  //  public SpaceInvaderFonts()
  //  {}
  //  
  //  public static Rectangle2D computeBounds(Graphics g, String msg)
  //  {
  //    return g.getFontMetrics().getStringBounds(msg, g);
  //  }
}
