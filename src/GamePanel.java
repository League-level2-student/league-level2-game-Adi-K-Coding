import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
	@Override
	public void paintComponent(Graphics g) {
		if (currentState == MENU) {
			drawMenuState(g);
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawEndState(g);
		}
	}

	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	int currentState = MENU;
	Font titleFont;
	Font titleFontTwo;
	Timer frameDraw;
	// BUTTONS ON MENU
	GameButton startButton = new GameButton(272, 250, "Start", 367, 250, "YELLOW");
	GameButton difficultyButton = new GameButton(272, 300, "Choose Difficulty", 287, 250, "YELLOW");
	GameButton instructionButton = new GameButton(272, 350, "Instructions", 321, 250, "YELLOW");
	GameButton levelSelectButton = new GameButton(272, 400, "Level Select", 326, 250, "YELLOW");
	boolean levelSelect = false;
	// BUTTONS IN-GAME
	GameButton nextLevelButton = new GameButton(272, 150, "Next Level", 367, 250, "YELLOW");
	GameButton duringPlayMenuButton = new GameButton(705, 10, "Menu", 712, 80, "YELLOW");
	GameButton levelOneButton = new GameButton(50, 300, "Level One", 121, 200, "YELLOW");
	GameButton levelTwoButton = new GameButton(300, 300, "Level Two", 367, 200, "YELLOW");
	GameButton levelThreeButton = new GameButton(550, 300, "Level Three", 617, 200, "YELLOW");
	GameButton levelFourButton = new GameButton(50, 350, "Level Four", 119, 200, "YELLOW");
	GameButton levelFiveButton = new GameButton(300, 350, "Level Five", 367, 200, "YELLOW");
	GameButton levelSixButton = new GameButton(550, 350, "Level Six", 626, 200, "YELLOW");
	// BUTTONS ON END-STATE
	GameButton bTMButton = new GameButton(10, 10, "Menu", 40, 100, "YELLOW");
	// PLAYER OBJECT
	Player player = new Player(0, 0, 0);
	ObjectManager objectManager = new ObjectManager(player);

	// GamePanel Constructor

	GamePanel() {
		titleFont = new Font("Roboto", Font.PLAIN, 72);
		titleFontTwo = new Font("Roboto", Font.CENTER_BASELINE, 24);
		frameDraw = new Timer(1000 / 60, this);
		frameDraw.start();
	}

	void updateMenuState() {

	}

	void updateGameState() {
		objectManager.update();
		if (player.isAlive == false) {
			currentState = END;
		}
	}

	void updateEndState() {

	}

	void drawMenuState(Graphics g) {
		// these two lines change background color of menu use for game and end states
		// as well
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, Escape.WIDTH, Escape.HEIGHT);
		// next two lines set font, font color, third is string and location
		g.setFont(titleFont);
		g.setColor(Color.YELLOW);
		g.drawString("Escape", 275, 150);
		// Start, Change Character, and Instruction Buttons
		g.setFont(titleFontTwo);
		startButton.draw(g);
		difficultyButton.draw(g);
		instructionButton.draw(g);
		levelSelectButton.draw(g);
	}

	void drawGameState(Graphics g) {
		objectManager.draw(g);

		if (objectManager.levelNumber == 0) {
			// drawing all transition state buttons
			g.setColor(Color.darkGray);
			g.fillRect(0, 0, Escape.WIDTH, Escape.HEIGHT);
			g.setColor(Color.YELLOW);
			bTMButton.draw(g);
			levelOneButton.draw(g);
			levelTwoButton.draw(g);
			levelThreeButton.draw(g);
			levelFourButton.draw(g);
			levelFiveButton.draw(g);
			levelSixButton.draw(g);
			if (levelSelect == false) {
				nextLevelButton.draw(g);
			}
		}
		if (objectManager.levelNumber != 0) {
			g.setColor(Color.DARK_GRAY);
			g.setFont(titleFontTwo);
			g.drawString("Level " + objectManager.levelNumber, 10, 30);
			duringPlayMenuButton.draw(g);

		}
	}

	void drawEndState(Graphics g) {
		if (objectManager.player.isAlive == true) {
			g.setColor(Color.cyan);
			g.fillRect(0, 0, Escape.WIDTH, Escape.HEIGHT);
			bTMButton.draw(g);
		} else {
			g.setColor(Color.cyan);
			g.setColor(Color.cyan);
			g.fillRect(0, 0, Escape.WIDTH, Escape.HEIGHT);
			g.setColor(Color.DARK_GRAY);
			bTMButton.draw(g);
			g.setFont(titleFont);
			g.drawString("You died", 200, 200);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (currentState == MENU) {
			updateMenuState();
		} else if (currentState == GAME) {
			updateGameState();
		} else if (currentState == END) {
			updateEndState();
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (currentState == GAME) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				System.out.println("UP");

				player.up = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				System.out.println("DOWN");
				player.down = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				System.out.println("LEFT");
				player.left = true;

			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				System.out.println("RIGHT");
				player.right = true;

			}
			// temporary code for switching screens
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				currentState = MENU;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				//DELETE
				player.x=3200;
				player.y=-900;
			}
			if (e.getKeyCode() == KeyEvent.VK_H) {
				//DELETE
				player.x=2500;
				player.y=-1900;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_0) {
			objectManager.levelNumber = 0;
			objectManager.createLevel(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_1) {
			objectManager.levelNumber = 1;
			objectManager.createLevel(1);
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			objectManager.levelNumber = 2;
			objectManager.createLevel(2);
		}
		if (e.getKeyCode() == KeyEvent.VK_3) {
			objectManager.levelNumber = 3;
			objectManager.createLevel(3);
		}
		if (e.getKeyCode() == KeyEvent.VK_4) {
			objectManager.levelNumber = 4;
			objectManager.createLevel(4);
		}
		if (e.getKeyCode() == KeyEvent.VK_5) {
			objectManager.levelNumber = 5;
			objectManager.createLevel(5);
		}
		if (e.getKeyCode() == KeyEvent.VK_6) {
			objectManager.levelNumber = 6;
			objectManager.createLevel(6);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (currentState == GAME) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				System.out.println("UP");

				player.up = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				System.out.println("DOWN");
				player.down = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				System.out.println("LEFT");
				player.left = false;

			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				System.out.println("RIGHT");
				player.right = false;

			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (currentState == MENU) {
			if (startButton.isOnButton(e.getX(), e.getY())) {
				objectManager.levelNumber = 1;
				objectManager.createLevel(objectManager.levelNumber);
				currentState = GAME;
				System.out.println("Start");
				objectManager.player.isAlive = true;
			}
			if (difficultyButton.isOnButton(e.getX(), e.getY())) {
				
				System.out.println("choose difficulty");
			}
			if (instructionButton.isOnButton(e.getX(), e.getY())) {
				System.out.println("Instructions");
				JOptionPane.showMessageDialog(null, "Your village faces extinction at the hands of a fearsome monster\n"
						+ "Manuever your character through six levels to find a path to safety for your village\n"
						+ "Luckily, before you set off on your voyage, you find a stone of invulnerability which\n"
						+ "grants you extra lives(based on difficulty)\n"
						+ "Easy-10 Lives\nHard-5 Lives\nExpert-1 Life\n" + "Move your character using the arrow keys.\n"
						+ "Avoid the deadly spikes(red) and don't fall of the platforms(green)\n"
						+ "Escape the monster and save your village. Good Luck!");
			}
			if (levelSelectButton.isOnButton(e.getX(), e.getY())) {
				objectManager.levelNumber = 0;
				objectManager.createLevel(objectManager.levelNumber);
				currentState = GAME;
				System.out.println("Level Select");
				objectManager.player.isAlive = true;
				levelSelect = true;
			}
		}
		if (currentState == GAME && objectManager.levelNumber == 0) {
			if (levelSelect == false) {
				if (nextLevelButton.isOnButton(e.getX(), e.getY())) {
					objectManager.levelNumber = 1;
					objectManager.levelNumber = objectManager.previousLevelNumber + 1;
					objectManager.createLevel(objectManager.levelNumber);
				}
			}
			if (levelOneButton.isOnButton(e.getX(), e.getY())) {
				objectManager.levelNumber = 1;
				objectManager.createLevel(objectManager.levelNumber);
			}
			if (levelTwoButton.isOnButton(e.getX(), e.getY())) {
				if (objectManager.level2Unlocked == true) {
					objectManager.levelNumber = 2;
					objectManager.createLevel(objectManager.levelNumber);
				} else {
					JOptionPane.showMessageDialog(null, "You have not unlocked this level yet");
				}
			}
			if (levelThreeButton.isOnButton(e.getX(), e.getY())) {
				if (objectManager.level3Unlocked == true) {
					objectManager.levelNumber = 3;
					objectManager.createLevel(objectManager.levelNumber);
				} else {
					JOptionPane.showMessageDialog(null, "You have not unlocked this level yet");
				}
			}

			if (levelFourButton.isOnButton(e.getX(), e.getY())) {
				if (objectManager.level4Unlocked == true) {
					objectManager.levelNumber = 4;
					objectManager.createLevel(objectManager.levelNumber);
				} else {
					JOptionPane.showMessageDialog(null, "You have not unlocked this level yet");
				}
			}
			if (levelFiveButton.isOnButton(e.getX(), e.getY())) {
				if (objectManager.level5Unlocked == true) {
					objectManager.levelNumber = 5;
					objectManager.createLevel(objectManager.levelNumber);
				} else {
					JOptionPane.showMessageDialog(null, "You have not unlocked this level yet");
				}
			}
			if (levelSixButton.isOnButton(e.getX(), e.getY())) {
				if (objectManager.level6Unlocked == true) {
					objectManager.levelNumber = 6;
					objectManager.createLevel(objectManager.levelNumber);
				} else {
					JOptionPane.showMessageDialog(null, "You have not unlocked this level yet");
				}
			}
		}
		if (currentState == END || currentState == GAME) {
			if (bTMButton.isOnButton(e.getX(), e.getY())) {
				currentState = MENU;
			}
			if (objectManager.levelNumber != 0) {
				if (duringPlayMenuButton.isOnButton(e.getX(), e.getY())) {
					currentState = MENU;
				}
			}
		}

		if (objectManager.levelNumber != 0) {
			if (bTMButton.isOnButton(e.getX(), e.getY())) {
				currentState = MENU;
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
