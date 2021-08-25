/*
*	Zachary Chapman
*	10/23/2020
*	Controller Class to handel moveing objects
*/

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	//Member variables
	View view;
	Model model;
	//For useing keyboard controls
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySave;
	boolean keyLoad;
	boolean keyJump;
	boolean keyFireball;
	float jumpHight;

	
	Controller(Model m)
	{
		model = m;
	}
	
	void setModel(Model m)
	{
		model = m;
	}
	

	public void actionPerformed(ActionEvent e)
	{
	}
	
	void setView(View v)
	{
		view = v;
	}
	
	//For using Mouse to animate it
	public void mousePressed(MouseEvent e)
	{
		//model.addTube(e.getX() + model.mario.x - view.marioOffset, e.getY());
	}

	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) 
	{
		if(e.getY() < 100)
		{
			System.out.println("Break here");
		}
	}
	
	//Finds when a key is pressed
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
				keyRight = true; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = true; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = true; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = true; 
				break;
			case KeyEvent.VK_CONTROL:
				keyFireball = true;
				break;
			//case KeyEvent.VK_S: 
				//keySave = true; 
				//break;
			//case KeyEvent.VK_L: 
			//	keyLoad = true; 
			//	break;
			case KeyEvent.VK_SPACE:
				keyJump = true;
				break;
		}
	}

	//Finds when a key is released
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
				keyRight = false; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = false; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = false; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = false; 
				break;
			case KeyEvent.VK_CONTROL:
				keyFireball = false;
				break;
			//case KeyEvent.VK_S: 
				//keySave = false; 
				//break;
			//case KeyEvent.VK_L: 
			//	keyLoad = false; 
			//	break;
			case KeyEvent.VK_SPACE:
				keyJump = false;
				break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}

	void update()
	{
		//Move right
		if(keyRight)
		{

			model.mario.savePastPos();	//Saves mario's past position
			model.mario.updateMovement(1);	//Moves mario to the right and starts the running animation
			
		}	
		//Move left
		if(keyLeft)
		{
			model.mario.savePastPos();	//Saves mario's past position
			model.mario.updateMovement(2);	//Moves mario to the left and starts the running animation
			
		}
		//Stop mario's animation if he is not moving
		if(!keyRight && !keyLeft)
		{
			model.mario.updateMovement(3);	//Stop mario from moving and stop the running animation.
		}
		//Save tubes file
		//if(keySave) 
		//{
		//	model.marshal().save("map.json");
		//}
		//Load tubes file
		/*
		if(keyLoad)	
		{
			Json j = Json.load("map.json");
			model.unMarshal(j);
		}*/
		//Make mario jump
		if(keyJump)
		{
			if(jumpHight < 5)	//Let the user make mario jump higher if button is pressed for longer
			{
				jumpHight++;
			}
		}
		if(!keyJump)
		{
			model.mario.jump(jumpHight);
			jumpHight = 0;	//reset mario's jump hight to zero
		}
		//If CTRL is pressed throw a fireball
		if(keyFireball)
		{
			model.fireballATK();
		}		
	}
}
