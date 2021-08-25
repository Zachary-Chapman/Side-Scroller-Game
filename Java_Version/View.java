/*
*	Zachary Chapman
*	10/23/2020
*	View class to allow the user to view objects
*/

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	Model model;
	int scrollPos;
	BufferedImage ground;
	

	View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
	}
	
	//Load in a image
	static BufferedImage loadImage(String fileName)
	{
		BufferedImage temp = null;
		try
		{
			temp = ImageIO.read(new File(fileName));
			//System.out.println(fileName + " has been loaded.");
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return temp;
	}
	
	void setModel(Model m)
	{
		model = m;
	}
		
	//The paint comonent to make all models visable
	public void paintComponent(Graphics g)
	{
		//Sets sky color
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//Iterates through all sprties and draws them
		for(int i = 0; i < model.sprites.size(); i++)
		{
			model.sprites.get(i).draw(g);
		}
		//Lazy load ground.png
		if(ground == null)
		{
			ground = loadImage("ground.png");
		}
		//Draw the ground
		g.drawImage(ground, 0, 440, null);
		g.drawImage(ground, 400, 440, null);
	}
	
}
