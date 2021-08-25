import java.awt.image.BufferedImage;
import java.awt.Graphics;

class Goomba extends Sprite
{
	int framesOnFire;
	double hor_slide;
	//boolean movementRight = true;
	boolean onFire = false;
	BufferedImage image = null;
	BufferedImage imageFire = null;
	Model model;
	
	public Goomba(int x, int y, Model m)
	{
		this.x = x;
		this.y = y;
		h = 118;
		w = 99;
		hor_slide = 3;
		model = m;
		if(image == null)
		{
			image = View.loadImage("goomba.png");
		}
		if(imageFire == null)
		{
			imageFire = View.loadImage("goomba_fire.png");
		}
	}
	
	Goomba (Json ob, Model m)
	{
		x = (int) ob.getLong("goomba_X");
		y = (int) ob.getLong("goomba_Y");
		h = 118;
		w = 99;
		hor_slide = 3;
		model = m;
		if(image == null)
		{
			image = View.loadImage("goomba.png");
		}
		if(imageFire == null)
		{
			imageFire = View.loadImage("goomba_fire.png");
		}
	}
	
	void update()
	{
		//Move left and right
		if(movementRight)
		{
			x += 4;
		}
		else
		{
			x -= 4;
		}
		
		//count frames when goomba is on fire
		if(onFire)
		{
			framesOnFire++;
		}
		else
		{
			framesOnFire = 0;	//reset count to 0
		}
	}
	
	Json marshal()
	{
		Json ob = Json.newObject();
		return ob;
	}
	//Draw goombas to screen
	void draw(Graphics g)
	{
		if(onFire)
		{
			g.drawImage(imageFire, x - model.mario.x + model.mario.marioOffset, y, null);
		}
		else
		{
			g.drawImage(image, x - model.mario.x + model.mario.marioOffset, y, null);
		}
	}
	
	boolean isGoomba()
	{
		return true;
	}
	//Change direction of goomba
	void changeDirection()
	{
		movementRight = !movementRight;
	}
	//Set goomba on fire when hit with fireball
	void setOnFire()
	{
		onFire = true;
	}
	//remove goomba when it has been on fire for more than 50 frames.
	boolean remove()
	{
		if(framesOnFire > 50)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}