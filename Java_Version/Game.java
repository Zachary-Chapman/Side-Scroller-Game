/*
*	Zachary Chapman
*	10/23/2020
*	Game Class to handel the main program
*/

import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	//Member Variables
	Model model;
	View view;
	Controller controller;
	
	public Game()
	{
		this.model = new Model();
		this.controller = new Controller(model);
		this.view = new View(controller, model);
		this.setTitle("Side-Scroller");
		this.setSize(800, 700);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);//Let the controller know that it is in charge of handeling mouse clicks
		this.addKeyListener(controller);//Let the conrtoller know that it is in charge of handeling key events
		//Json ob = Json.newObject();
		Json j = Json.load("map.Json");//ob.load("map.Json");
		model.unMarshal(j);
	}

	public static void main(String[] args)
	{
		//Json ob = Json.newObject();
		//ob.load("map.Json");
		Game g = new Game();
		g.run();
	}
	
	void setModel(Model m)
	{
		model = m;
	}
	
	//Runs the game
	public void run()
	{
		controller.keyLoad = true;

		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 miliseconds for 25FPS
			try
			{
				Thread.sleep(40);
			} 
			catch(Exception e) 
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
}
