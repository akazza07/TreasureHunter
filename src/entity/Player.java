package entity;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
  
	GamePanel gp;
	KeyHandler KeyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;
	int standCounter = 0;
	
	
	 public Player(GamePanel gp , KeyHandler KeyH) {
		 
		 this.gp = gp;
		 this.KeyH = KeyH;
		 
		 screenX = gp.screenWidth/2 - (gp.tileSize/2); 
		 screenY = gp.screenHeight/2 - (gp.tileSize/2); // this return halfway of screen
		 
		 solidArea = new Rectangle();
		 solidArea.x = 8;    // we made solid area under the character tile , so we easily pass through the collision tile.
		 solidArea.y = 16;
		 solidAreaDefaultX = solidArea.x;
		 solidAreaDefaultY = solidArea.y;
		 solidArea.width = 32;
		 solidArea.height = 32;
		 
		 setDefaultValues();
		 getPlayerImage();
	 }
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "down";
	}
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right2.png"));
		}
		catch(IOException e){
			e.printStackTrace();
		
		}
	}
	public void update() {
		
		if(KeyH.upPressed == true || KeyH.downPressed == true || // manually start moving character only this statement
				KeyH.leftPressed == true || KeyH.rightPressed == true) {
			
			
			
			if(KeyH.upPressed == true) {
				direction = "up";
          
			}
			else if (KeyH.downPressed == true) {
				direction = "down";
		   
			}
			else if (KeyH.leftPressed == true) {
				direction = "left";
			
			}
			else if (KeyH.rightPressed == true) {
				direction = "right";
			
			}
			// check tile collision 
			
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// check object collision 
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			 // if collision is false , player can move 
			if (collisionOn == false) {
				switch(direction) {
				case "up":  worldY -= speed;	
					break;
				case "down": worldY += speed;
					break;
				case "left": worldX -= speed;
					break;
				case "right": worldX += speed;
					break;
				}
			}
			
			spriteCounter++;  // basically it means the player changes in every 10 frames
			if(spriteCounter > 12) { // motion of character by how much speed moving
				if(spriteNum == 1 ) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				
				spriteCounter = 0;
			}  
		}
		else {
			
			standCounter++;
			
			if(standCounter == 20) {
				spriteNum = 1;
				standCounter = 0;
			}
			
		}
	 }
	
	public void pickUpObject(int i) {
		if(i != 999) {
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case"Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You got a Key!!");
				break;
			case"Door":
				if(hasKey > 0) {
					gp.playSE(3);
					gp.obj[i] = null;
				
					hasKey--;
					gp.ui.showMessage("You opened the door!");
				}else {
					gp.ui.showMessage("You need a Key!");
				}
				
				break;
				
			case "Carrot":
				gp.playSE(2);
				speed += 1.7;  // How much power get the player
				gp.obj[i] = null;
				gp.ui.showMessage("Speed Up!");
				break;
			
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {
  //      g2.setColor(Color.white);
		
  //		g2.fillRect(x, y, gp.tileSize, gp.tileSize); // this need to public 
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
			image = up1;
		}   
			if(spriteNum == 2){
			image = up2;
		}
			
		break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}   
				if(spriteNum == 2){
				image = down2;
				}
		break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}   
				if(spriteNum == 2){
				image = left2;
				}
		break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}   
				if(spriteNum == 2){
				image = right2;
				}
		break;
		}
		g2.drawImage(image,screenX,screenY,gp.tileSize,gp.tileSize,null);
	}
}
