/*
*	Zachary Chapman
*	10/7/20
*	Mario Class to handel methods for the mario model
*/

import java.awt.image.BufferedImage;
import java.awt.Graphics;

class Mario extends Sprite
{
	int preX;
	int preY;
	int marioImageNum;
	double vert_vel;
	boolean marioMovement;
	boolean marioGrounded = false;
	BufferedImage[] mario_images;
	int marioOffset = 150;
	
	public Mario(int x, int y)
	{
		this.x = x;
		this.y = y;
		h = 96;
		w = 60;
		
		//Lazy load images
		mario_images = new BufferedImage[5];
		marioImageNum = 0;
		if(mario_images[0] == null)
		{
			mario_images[0] = View.loadImage("mario1.png");
		}
		if(mario_images[1] == null)
		{
			mario_images[1] = View.loadImage("mario2.png");
		}
		if(mario_images[2] == null)
		{
			mario_images[2] = View.loadImage("mario3.png");
		}
		if(mario_images[3] == null)
		{
			mario_images[3] = View.loadImage("mario4.png");
		}
		if(mario_images[4] == null)
		{
			mario_images[4] = View.loadImage("mario5.png");
		}
	}
	
	
	//Function to animate mario moving and move his model
	void updateMovement(int moving)
	{
		switch(moving)
		{
			case 1: //Mario moving to the right
				marioMovement = true;
				x += 5;
				break;
			
			case 2: //Mario moving to the left
				marioMovement = true;
				x -= 5;
				break;
			default: //Mario not moving
				marioMovement = false;
		}
	}
	
	//Function to save past postion to allow the getOutOfTube funtion to work
	void savePastPos()
	{
		preX = x;
		preY = y;
	}
	
	//Moves mario out of the tube depending on what side he entered the toob at
	void getOutOfTube(Sprite temp)
	{
		if(preX + w <= temp.x)
		{
			x = temp.x - w;
		}
		if(preX >= temp.x + temp.w)
		{
			x = temp.x + temp.w;
		}
		if(preY >= temp.y + temp.h)
		{
			y = temp.y + temp.h;
			
		}
		if(preY + h <= temp.y)
		{
			y = temp.y - h;
			marioGrounded = true;
		}
	}
	
	//Let mario jump based on the jumph paramater and if he is grounded
	void jump(float jumph)
	{
		if(marioGrounded)
		{
			jumph *= .5;
			vert_vel = -15 * jumph;
		}
		
	}
	
	
	void update()
	{
		if(marioMovement)	//If statment to make mario look like he is moving when keys are pressed
		{
			marioImageNum++;
			if(marioImageNum > 4)
			{
				marioImageNum = 0;
			}
			
		}
		
		//Gravity component
		vert_vel += 1.2;
		this.y += vert_vel;
		
		//if mario is below the ground set him on ground leve
		if(y > 500)
		{
			vert_vel = 0.0;
			y = 500;
		}	
		
		//If mario is on the ground he can jump
		if(y == 500)
		{
			marioGrounded = true; 
		}
		else
		{
			marioGrounded = false;	//If mario is in the air he cannot jump.
		}
		
	}
	
	Json marshal()
	{
		Json ob = Json.newObject();
		return ob;
	}
	
	//Draw mario to the screen
	void draw(Graphics g)
	{
		g.drawImage(mario_images[marioImageNum], x - x + marioOffset, y, null);
	}

	boolean isMario()
	{
		return true; 
	}
	
}
