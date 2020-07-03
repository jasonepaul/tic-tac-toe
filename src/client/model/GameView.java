package client.model;
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;

/**
 * Provides the GUI for the game.
 * @author Jason Paul
 * @version 1.0
 * @since Nov 6, 2019
 *
 */
public class GameView extends JFrame{
	
	private JTextArea messageArea;
	private JButton[] xoButtons;
	private JTextField playerMarkField;
	private JTextField playerNameField;
	private JButton nameButton;
	
	/**
	 * Constructs a GUI for the Tic-Tac-Toe game.
	 * @param title The title of the GUI.
	 */
	public GameView(String title) {
		super(title);
		
		JLabel messageAreaLabel = new JLabel("Message Window");
		messageArea = new JTextArea(14, 30);
		messageArea.setEditable(false);
		xoButtons = new JButton[9];
		JLabel playerMarkLabel = new JLabel("Player:");
		playerMarkField = new JTextField(3);
		playerMarkField.setEditable(false);
		JLabel playerNameLabel = new JLabel("User Name:");
		playerNameField = new JTextField(15);
		nameButton = new JButton("Enter");

		setLayout (new BorderLayout());
		Container c = getContentPane();
		
		JPanel messagePanel = new JPanel();
		messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel playerMarkPanel = new JPanel();
		playerMarkPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel playerNamePanel = new JPanel();
		playerNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel playerInfoPanel = new JPanel();
		
		buttonPanel.setSize(20, 20);

		// Add widgets to panels.
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add("North", messageAreaLabel);
		messagePanel.add("Center", messageArea);
		buttonPanel.setLayout(new GridLayout(3, 3, 2, 2));
		for(int i = 0; i < 9; i++) {
			JButton btn = new JButton();
		    btn.setPreferredSize(new Dimension(50, 50));
		    btn.setFont(new Font("Arial", Font.PLAIN, 20));
			xoButtons[i] = btn;
			buttonPanel.add(btn);
		}
		setXoButtonStatus(false);
		
		playerMarkPanel.add(playerMarkLabel);
		playerMarkPanel.add(playerMarkField);
		playerNamePanel.add(playerNameLabel);
		playerNamePanel.add(playerNameField);
		playerNamePanel.add(nameButton);
		playerInfoPanel.setLayout(new BorderLayout());
		playerInfoPanel.add("North", playerMarkPanel);
		playerInfoPanel.add("South", playerNamePanel);	
		
		// Add panels to frame.
		c.add("Center", buttonPanel);
		c.add("East", messagePanel);
		c.add("South", playerInfoPanel);

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messageArea.setText("Please enter your name and press enter.");
	}
	
	/**
	 * Adds action listener to the array of buttons that serve as the X and O
	 * grid.
	 * @param listenForXoButton The listener for the X and O buttons.
	 */
	public void addXoButtonListener(ActionListener listenForXoButton) {
		for(int i = 0; i < xoButtons.length; i++) {
			xoButtons[i].addActionListener(listenForXoButton);
		}
	}
	
	/**
	 * Adds action listener to the Name button. 
	 * @param listenForNameButton The listener for the name button.
	 */
	public void addNameButtonListener(ActionListener listenForNameButton) {
		nameButton.addActionListener(listenForNameButton);
	}
	
	/**
	 * A wrapper for the private method that sets the active status for the X and 
	 * O button grid.
	 * @param active
	 */
	public void setButtonStatus(Boolean active) {
		setXoButtonStatus(active);
	}
	
	/**
	 * Sets the active status for the X and O button grid.
	 * @param active
	 */
	private void setXoButtonStatus(Boolean active) {
		for(int i = 0; i < xoButtons.length; i++) {
			xoButtons[i].setEnabled(active);
		}
	}
	
	// **************** Getters and setters ************************
	
	/**
	 * Gets the player mark field text.
	 * @return The player mark field text.
	 */
	public String getPlayerMarkField() {
		return playerMarkField.getText();
	}
	
	/**
	 * Sets the player mark field text.
	 * @param s
	 */
	public void setPlayerMarkField(String s) {
		playerMarkField.setText(s);
	}
	
	/**
	 * Gets the player name field text.
	 * @return The player name field text.
	 */
	public String getPlayerNameField() {
		return playerNameField.getText();
	}
	
	/**
	 * Sets the player name field text.
	 * @param s The player name field text.
	 */
	public void setPlayerNameField(String s) {
		playerNameField.setText(s);
	}

	public void setPlayerNameFieldStatus(String s) {
		playerNameField.setText(s);
	}
	
	/**
	 * Sets the Message box text.
	 * @param s The text to display.
	 */
	public void setMessageText(String s) {
		messageArea.append("\n"+s);
	}
	
	/**
	 * Gets the X and O buttons.
	 * @return The X and O buttons.
	 */
	public JButton[] getXoButtons() {
		return xoButtons;
	}
	
	/**
	 * Gets the Name button.
	 * @return The name button.
	 */
	public JButton getNameButton() {
		return nameButton;
	} 
	
}
