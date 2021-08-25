/*
*	Zachary Chapman
*	10/23/2020
*	Model class to hadel models of objects
*/

import java.util.ArrayList;
import java.util.*;


class Model
{
	ArrayList<Sprite> sprites;	//ArrayList holding all sprites
	int model_x;
	int model_y;
	int dest_x;
	int dest_y;
	Mario mario;

	Model()
	{
		sprites = new ArrayList<Sprite>();
		mario = new Mario(200, 50);
		sprites.add(mario);
		
	}
	
	void unMarshal(Json ob)
	{
		
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
		Json spriteList = ob.get("spritesArray");
		Json tubeList = spriteList.get("tubesArray");
		Json GoombaList = spriteList.get("goombaArray");
			for (int i = 0; i < tubeList.size(); i++)
			{
				sprites.add(new Tube(tubeList.get(i), this));
			}
			for (int i = 0; i < GoombaList.size(); i++)
			{
				sprites.add(new Goomba(GoombaList.get(i), this));
			}
		
	}
	
	//add fireball method
	void fireballATK()
	{
			sprites.add(new Fireball(mario.x + mario.w, mario.y, this));
	}

	public void update()
	{
		//Using polymorphism update all sprites
		for(int i = 0; i < sprites.size(); i++)
		{
			sprites.get(i).update();
		}
		
		//Loop through the sprites
		for(int i = 0; i < sprites.size(); i++)
		{
			//Test to see if sprite(i) is a mario
			if(sprites.get(i).isMario())
			{
				//Loop to find a tube sprite
				for(int j = 0; j < sprites.size(); j++)
				{
					if(sprites.get(j).isTube())
					{
						sprites.get(i).collision(sprites.get(j)); //Test is mario is colliding with a tube
					}
				}
			}
			//Test to see if sprite(i) is a goomba
			if(sprites.get(i).isGoomba())
			{
				//loop to find a tube sprite
				for(int j = 0; j < sprites.size(); j++)
				{
					if(sprites.get(j).isTube())
					{
						//If the goomba collides with a tube
						if(sprites.get(i).collision(sprites.get(j)))
						{
							sprites.get(i).changeDirection();	//Change the direction the goomba moves
						}
					}
				}
				//If the goomba has been on fire for atleast 50 frames it will be removed
				if(sprites.get(i).remove())
				{
					sprites.remove(i);
				}
			}
			//Test to see if sprite(i) is a fireball
			if(sprites.get(i).isFireball())
			{
				//loop to find a goomba sprite
				for(int j = 0; j < sprites.size(); j++)
				{
					if(sprites.get(j).isGoomba())
					{
						//if the fireball collides with a goomba
						if(sprites.get(i).collision(sprites.get(j)))
						{
							sprites.get(j).setOnFire();		//set the goomba on fire
						}
					}
				}
				//If the fireball goes off the screen then it gets removed
				if(sprites.get(i).remove())
				{
					sprites.remove(i);
				}
			}
		}	
	}

	public void addTube(int x, int y)
	{
		boolean newTube = true;	//Boolean variable to determin if a new tube will be added
		Iterator<Sprite> tubeIterator = sprites.iterator();	
		while(tubeIterator.hasNext())
		//for(int i = 0; i < sprites.size(); i++)
		{
			if(tubeIterator.next() instanceof Tube)
			{
				Tube temp = (Tube)tubeIterator.next();		//Set tube temp to the next tube in sprites array

				if(temp.tube_Collision(x, y) == true)	// Check if temp tube is colliding with another tube
				{
					tubeIterator.remove();
					newTube = false;

				}
			}
			
		}	
		
		if(newTube)	//If newTube is true make and add a new tube
			{
				Tube t = new Tube(x, y, this);
				sprites.add(t);
			}

	}
	
	Json marshal()
	{
		Json ob = Json.newObject();
		Json spritesOb = Json.newObject();
		Json obList = Json.newList();
		ob.add("spritesArray", spritesOb);
		spritesOb.add("tubesArray", obList);
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).isTube())
			{
				Tube t = (Tube) sprites.get(i);
				obList.add(t.marshal());
			}
		}
		obList = Json.newObject();
		spritesOb.add("goombaArray", obList);
		for(int i = 0; i < sprites.size(); i++)
		{
			if(sprites.get(i).isGoomba())
			{
				Goomba g = (Goomba) sprites.get(i);
				obList.add(g.marshal());
			}
		}
		
		return ob;
	}
	
}