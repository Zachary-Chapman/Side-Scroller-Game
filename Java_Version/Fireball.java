import java.awt.image.BufferedImage;
import java.awt.Graphics;

class Fireball extends Sprite
{
	double vert_vel, hor_slide;
	BufferedImage image = null;
	Model model;
	
	public Fireball(int x, int y, Model m)
	{
		this.x = x;
		this.y = y;
		h = 47;
		w = 47;
		hor_slide = 3;
		model = m;
		if(image == null)
		{
			image = View.loadImage("fireball.png");
		}
	}
	
	void update()
	{
		//Make fireball bounce
		vert_vel += 0.2;
		this.y += vert_vel;
		if(y > 540)
		{
			vert_vel -= 1.2;
		}
		
		//make fireball move to the right
		this.x += hor_slide;
		if(x > model.mario.x + 600)
		{
			offScreen = true;
		}
	}
	
	Json marshal()
	{
		Json ob = Json.newObject();
		ob.add("goomba_X", x);
		ob.add("goomba_y", y);
		return ob;
	}
	//Draw fireball to screen
	void draw(Graphics g)
	{
		g.drawImage(image, x - model.mario.x + model.mario.marioOffset, y, null);
	}
	
	boolean isFireball()
	{
		return true;
	}
	//return true if fireball is off the screen
	boolean remove()
	{
		if(offScreen)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}