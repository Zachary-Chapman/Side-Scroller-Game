/*
*	Zachary Chapman
*	10/23/2020
*	Sprite class to hadel all sprites
*/
import java.awt.Graphics;

abstract class Sprite
{
	//Variables for the sprite class
	int x, y, w, h;
	boolean offScreen;
	boolean movementRight = true;
	
	//abstract methods
	abstract void update();
	abstract Json marshal();
	abstract void draw(Graphics g);
	
	//methods to determ what type of sprite each sprite is
	boolean isGoomba() {return false;}
	boolean isTube() {return false;}
	boolean isMario() {return false;}
	boolean isFireball() {return false;}
	
	//Extra functions
	void getOutOfTube(Sprite temp) {};	//function to get mario out of a tube
	void changeDirection() {};			//function to change the direction of a sprite
	void setOnFire() {};				//function to set a sprite on fire
	boolean remove() {return false;}	//function to check if the sprite is readdy to be removed.
	
	//Function to determing is a sprite is colliding with another sprite
	boolean collision(Sprite temp)
	{
		boolean collisionOccur = true;		//True when this.sprite is colliding with temp sprite false when he is not
		if(this.x + this.w < temp.x)	//If this.sprite is to the left of temp sprite it is not colliding with temp sprite
		{
			collisionOccur = false;
		}
		if(this.x > temp.x + temp.w)	//If this.sprite is to the right of temp sprite it is not colliding with temp sprite
		{
			collisionOccur = false;
		}
		if(this.y + this.h < temp.y)	//If this.sprite is above temp sprite it is not colliding with temp sprite
		{
			collisionOccur = false;
		}
		if(this.y > temp.y + temp.h)	//If this.sprite is below temp sprite it is not colliding with temp sprite
		{
			collisionOccur = false;
		}
		if(collisionOccur)	//If this sprite is colliding with temp sprite run getOutOfTube	
		{
			this.getOutOfTube(temp);	
		}
		return collisionOccur;
	}		
}