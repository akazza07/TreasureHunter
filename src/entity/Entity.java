package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity { // this stores variables that will be used in player , monster and NPC classes.
    
	public int worldX,worldY;
	public int speed;
	
	public BufferedImage up1 , up2 , down1 , down2 , left1 , left2 , right1 , right2 ; // BufferedImage = it describes an image with an accessible buffer of image data. (we use this to store our image files)
	
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collision = false;
	public boolean collisionOn;
}
