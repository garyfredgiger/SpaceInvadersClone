package squareinvaders.managers;

import java.awt.Color;

import squareinvaders.entities.InvaderEntityMultipleHits;

// TODO: Should this class be static?

public class BossManager
{
  // Create Boss 1
  public void createBossOne()
  {
//    // Test Logic for a big boss.
//    InvaderEntityMultipleHits block01 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block02 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block03 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block04 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block05 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block06 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block07 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block08 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block09 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block10 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block11 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block12 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block13 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block14 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block15 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block16 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block17 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block18 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block19 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block20 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);        
//    InvaderEntityMultipleHits block21 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block22 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block23 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block24 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block25 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block26 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block27 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block28 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block29 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block30 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block31 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block32 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block33 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block34 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block35 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block36 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block37 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block38 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block39 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block40 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block41 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block42 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block43 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block44 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block45 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block46 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block47 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    InvaderEntityMultipleHits block48 = new InvaderEntityMultipleHits(invaderEntityManager, 20, 780, 560);
//    
//    block01.setDimensions(32, 32);
//    block02.setDimensions(32, 32);
//    block03.setDimensions(32, 32);
//    block04.setDimensions(32, 32);
//    block05.setDimensions(32, 32);
//    block06.setDimensions(32, 32);
//    block07.setDimensions(32, 32);
//    block08.setDimensions(32, 32);
//    block09.setDimensions(32, 32);
//    block10.setDimensions(32, 32);
//    block11.setDimensions(32, 32);
//    block12.setDimensions(32, 32);
//    block13.setDimensions(32, 32);
//    block14.setDimensions(32, 32);
//    block15.setDimensions(32, 32);
//    block16.setDimensions(32, 32);
//    block17.setDimensions(32, 32);
//    block18.setDimensions(32, 32);
//    block19.setDimensions(32, 32);
//    block20.setDimensions(32, 32);        
//    block21.setDimensions(32, 32);
//    block22.setDimensions(32, 32);
//    block23.setDimensions(32, 32);
//    block24.setDimensions(32, 32);
//    block25.setDimensions(32, 32);
//    block26.setDimensions(32, 32);
//    block27.setDimensions(32, 32);
//    block28.setDimensions(32, 32);
//    block29.setDimensions(32, 32);
//    block30.setDimensions(32, 32);
//    block31.setDimensions(32, 32);
//    block32.setDimensions(32, 32);
//    block33.setDimensions(32, 32);
//    block34.setDimensions(32, 32);
//    block35.setDimensions(32, 32);
//    block36.setDimensions(32, 32);
//    block37.setDimensions(32, 32);
//    block38.setDimensions(32, 32);
//    block39.setDimensions(32, 32);
//    block40.setDimensions(32, 32);
//    block41.setDimensions(32, 32);
//    block42.setDimensions(32, 32);
//    block43.setDimensions(32, 32);
//    block44.setDimensions(32, 32);
//    block45.setDimensions(32, 32);
//    block46.setDimensions(32, 32);
//    block47.setDimensions(32, 32);
//    block48.setDimensions(32, 32);
//    
//    block01.setColor(Color.RED);
//    block02.setColor(Color.RED);
//    block03.setColor(Color.RED);
//    block04.setColor(Color.RED);
//    block05.setColor(Color.RED);
//    block06.setColor(Color.RED);
//    block07.setColor(Color.RED);
//    block08.setColor(Color.RED);
//    block09.setColor(Color.RED);
//    block10.setColor(Color.RED);
//    block11.setColor(Color.RED);
//    block12.setColor(Color.RED);
//    block13.setColor(Color.RED);
//    block14.setColor(Color.RED);
//    block15.setColor(Color.RED);
//    block16.setColor(Color.RED);
//    block17.setColor(Color.RED);
//    block18.setColor(Color.RED);
//    block19.setColor(Color.RED);
//    block20.setColor(Color.RED);
//    block21.setColor(Color.RED);
//    block22.setColor(Color.RED);
//    block23.setColor(Color.RED);
//    block24.setColor(Color.RED);
//    block25.setColor(Color.RED);
//    block26.setColor(Color.RED);
//    block27.setColor(Color.RED);
//    block28.setColor(Color.RED);
//    block29.setColor(Color.RED);
//    block30.setColor(Color.RED);
//    block31.setColor(Color.RED);
//    block32.setColor(Color.RED);
//    block33.setColor(Color.RED);
//    block34.setColor(Color.RED);
//    block35.setColor(Color.RED);
//    block36.setColor(Color.RED);
//    block37.setColor(Color.RED);
//    block38.setColor(Color.RED);
//    block39.setColor(Color.RED);
//    block40.setColor(Color.RED);
//    block41.setColor(Color.RED);
//    block42.setColor(Color.RED);
//    block43.setColor(Color.RED);
//    block44.setColor(Color.RED);
//    block45.setColor(Color.RED);
//    block46.setColor(Color.RED);
//    block47.setColor(Color.RED);
//    block48.setColor(Color.RED);
//    
//    int startingPosX = 128;
//    int startingPosY = 64;
//    int bossDimensionX = 32;
//    int bossDimensionY = 32;
//
//    // Row 1
//    block01.setPosition(startingPosX + 3*bossDimensionX, startingPosY + bossDimensionY);
//    block02.setPosition(startingPosX + 9*bossDimensionX, startingPosY + bossDimensionY);
//
//    // Row 2
//    block03.setPosition(startingPosX + 4*bossDimensionX, startingPosY + 2*bossDimensionY);
//    block04.setPosition(startingPosX + 8*bossDimensionX, startingPosY + 2*bossDimensionY);
//
//    // Row 3
//    block05.setPosition(startingPosX + 3*bossDimensionX, startingPosY + 3*bossDimensionY);
//    block06.setPosition(startingPosX + 4*bossDimensionX, startingPosY + 3*bossDimensionY);
//    block07.setPosition(startingPosX + 5*bossDimensionX, startingPosY + 3*bossDimensionY);
//    block08.setPosition(startingPosX + 6*bossDimensionX, startingPosY + 3*bossDimensionY);
//    block09.setPosition(startingPosX + 7*bossDimensionX, startingPosY + 3*bossDimensionY);
//    block10.setPosition(startingPosX + 8*bossDimensionX, startingPosY + 3*bossDimensionY);
//    block11.setPosition(startingPosX + 9*bossDimensionX, startingPosY + 3*bossDimensionY);
//
//    // Row 4
//    block12.setPosition(startingPosX + 2*bossDimensionX, startingPosY + 4*bossDimensionY);
//    block13.setPosition(startingPosX + 3*bossDimensionX, startingPosY + 4*bossDimensionY);
//    block14.setPosition(startingPosX + 5*bossDimensionX, startingPosY + 4*bossDimensionY);
//    block15.setPosition(startingPosX + 6*bossDimensionX, startingPosY + 4*bossDimensionY);
//    block16.setPosition(startingPosX + 7*bossDimensionX, startingPosY + 4*bossDimensionY);
//    block17.setPosition(startingPosX + 9*bossDimensionX, startingPosY + 4*bossDimensionY);
//    block18.setPosition(startingPosX + 10*bossDimensionX, startingPosY + 4*bossDimensionY);
//    
//    // Row 5
//    block19.setPosition(startingPosX + 1*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block20.setPosition(startingPosX + 2*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block21.setPosition(startingPosX + 3*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block22.setPosition(startingPosX + 4*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block23.setPosition(startingPosX + 5*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block24.setPosition(startingPosX + 6*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block25.setPosition(startingPosX + 7*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block26.setPosition(startingPosX + 8*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block27.setPosition(startingPosX + 9*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block28.setPosition(startingPosX + 10*bossDimensionX, startingPosY + 5*bossDimensionY);
//    block29.setPosition(startingPosX + 11*bossDimensionX, startingPosY + 5*bossDimensionY);
//
//    // Row 6
//    block30.setPosition(startingPosX + 1*bossDimensionX, startingPosY + 6*bossDimensionY);
//    block31.setPosition(startingPosX + 3*bossDimensionX, startingPosY + 6*bossDimensionY);
//    block32.setPosition(startingPosX + 4*bossDimensionX, startingPosY + 6*bossDimensionY);
//    block33.setPosition(startingPosX + 5*bossDimensionX, startingPosY + 6*bossDimensionY);
//    block34.setPosition(startingPosX + 6*bossDimensionX, startingPosY + 6*bossDimensionY);
//    block35.setPosition(startingPosX + 7*bossDimensionX, startingPosY + 6*bossDimensionY);
//    block36.setPosition(startingPosX + 8*bossDimensionX, startingPosY + 6*bossDimensionY);
//    block37.setPosition(startingPosX + 9*bossDimensionX, startingPosY + 6*bossDimensionY);
//    block38.setPosition(startingPosX + 11*bossDimensionX, startingPosY + 6*bossDimensionY);
//
//    // Row 7
//    block39.setPosition(startingPosX + 1*bossDimensionX, startingPosY + 7*bossDimensionY);
//    block40.setPosition(startingPosX + 3*bossDimensionX, startingPosY + 7*bossDimensionY);
//    block41.setPosition(startingPosX + 9*bossDimensionX, startingPosY + 7*bossDimensionY);
//    block42.setPosition(startingPosX + 11*bossDimensionX, startingPosY + 7*bossDimensionY);
//
//    // Row 8
//    block43.setPosition(startingPosX + 4*bossDimensionX, startingPosY + 8*bossDimensionY);
//    block44.setPosition(startingPosX + 5*bossDimensionX, startingPosY + 8*bossDimensionY);
//    block45.setPosition(startingPosX + 7*bossDimensionX, startingPosY + 8*bossDimensionY);
//    block46.setPosition(startingPosX + 8*bossDimensionX, startingPosY + 8*bossDimensionY);
//    
//    block01.setVelocity(0, 0);
//    block02.setVelocity(0, 0);
//    block03.setVelocity(0, 0);
//    block04.setVelocity(0, 0);
//    block05.setVelocity(0, 0);
//    block06.setVelocity(0, 0);
//    block07.setVelocity(0, 0);
//    block08.setVelocity(0, 0);
//    block09.setVelocity(0, 0);
//    block10.setVelocity(0, 0);
//    block11.setVelocity(0, 0);
//    block12.setVelocity(0, 0);
//    block13.setVelocity(0, 0);
//    block14.setVelocity(0, 0);
//    block15.setVelocity(0, 0);
//    block16.setVelocity(0, 0);
//    block17.setVelocity(0, 0);
//    block18.setVelocity(0, 0);
//    block19.setVelocity(0, 0);
//    block20.setVelocity(0, 0);        
//    block21.setVelocity(0, 0);
//    block22.setVelocity(0, 0);
//    block23.setVelocity(0, 0);
//    block24.setVelocity(0, 0);
//    block25.setVelocity(0, 0);
//    block26.setVelocity(0, 0);
//    block27.setVelocity(0, 0);
//    block28.setVelocity(0, 0);
//    block29.setVelocity(0, 0);
//    block30.setVelocity(0, 0);
//    block31.setVelocity(0, 0);
//    block32.setVelocity(0, 0);
//    block33.setVelocity(0, 0);
//    block34.setVelocity(0, 0);
//    block35.setVelocity(0, 0);
//    block36.setVelocity(0, 0);
//    block37.setVelocity(0, 0);
//    block38.setVelocity(0, 0);
//    block39.setVelocity(0, 0);
//    block40.setVelocity(0, 0);
//    block41.setVelocity(0, 0);
//    block42.setVelocity(0, 0);
//    block43.setVelocity(0, 0);
//    block44.setVelocity(0, 0);
//    block45.setVelocity(0, 0);
//    block46.setVelocity(0, 0); 
//
//    addEnemy(block01);
//    addEnemy(block02);
//    addEnemy(block03);
//    addEnemy(block04);
//    addEnemy(block05);
//    addEnemy(block06);
//    addEnemy(block07);
//    addEnemy(block08);
//    addEnemy(block09);
//    addEnemy(block10);
//    addEnemy(block11);
//    addEnemy(block12);
//    addEnemy(block13);
//    addEnemy(block14);
//    addEnemy(block15);
//    addEnemy(block16);
//    addEnemy(block17);
//    addEnemy(block18);
//    addEnemy(block19);
//    addEnemy(block20);
//    addEnemy(block21);
//    addEnemy(block22);
//    addEnemy(block23);
//    addEnemy(block24);
//    addEnemy(block25);
//    addEnemy(block26);
//    addEnemy(block27);
//    addEnemy(block28);
//    addEnemy(block29);
//    addEnemy(block30);
//    addEnemy(block31);
//    addEnemy(block32);
//    addEnemy(block33);
//    addEnemy(block34);
//    addEnemy(block35);
//    addEnemy(block36);
//    addEnemy(block37);
//    addEnemy(block38);
//    addEnemy(block39);
//    addEnemy(block40);
//    addEnemy(block41);
//    addEnemy(block42);
//    addEnemy(block43);
//    addEnemy(block44);
//    addEnemy(block45);
//    addEnemy(block46);
  }
}
