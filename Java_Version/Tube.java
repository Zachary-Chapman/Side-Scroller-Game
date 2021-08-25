/*
*	Zachary Chapman
*	10/23/2020
*	Tube Class 
*/

import java.awt.image.BufferedImage;
import java.awt.Graphics;

class Tube extends Sprite
{
	//Member variables
	BufferedImage image = null;
	Model model;
	
	
	public Tube(int new_x, int new_y, Model m)
	{
		x = new_x;
		y = new_y;
		w = 55;
		h = 400;
		model = m;
		if(image == null)
		{
			image = View.loadImage("tube.png");
		}
	
	}
	
	void update()
	{
	}
	
	Tube(Json ob, Model m)
	{
		x = (int)ob.getLong("tube_X");
		y = (int)ob.getLong("tube_y");
		w = 55;
		h = 400;
		model = m;
		if(image == null)
		{
			image = View.loadImage("tube.png");
		}
	}
	
	
	boolean tube_Collision(int user_x, int user_y)
	{
		if(user_x < x)
			return false;
		if(user_x > x + w)
			return false;
		if(user_y < y)
			return false;
		if(user_y > y + h)
			return false;
		return true;
	}
	
	Json marshal()
	{
		Json ob = Json.newObject();
		ob.add("tube_X", x);
		ob.add("tube_y", y);
		return ob;
	}
	//Draw tube to scree
	void draw(Graphics g)
	{
		g.drawImage(image, x - model.mario.x + model.mario.marioOffset, y, null);
	}
	
	boolean isTube()
	{
		return true;
	}
}
