package GuessingGame;

// awt
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

// io
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

// imageio
import javax.imageio.ImageIO;

// sound
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
// swing
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GuessingGameView extends JPanel implements ActionListener {
	// text area for instruction
	private JTextArea intro;
	private JTextArea instruction;
	private JTextArea question;

	// JPanel to hold the buttons
	private JPanel buttonPanel;

	// JLabel to hold the picture
	private JLabel picLabel;

	// buttons
	private JButton yes;
	private JButton no;

	// input file
	private FileInputStream fin;
	private int k;

	// game controller
	private GuessingGameController controller;

	/**
	 * Constructor
	 */
	public GuessingGameView() {
		// call the parent constructor
		super();
		// initialize controller class
		controller = new GuessingGameController(this);
		// initialize first frame
		init1st();
		// play the music
		playMusic();
	}

	/**
	 * initialize 1st frame
	 */
	private void init1st() {
		// use a border layout
		setLayout(new BorderLayout());

		// add intro panel to CENTER
		add(createIntro(), BorderLayout.CENTER);

		// initialize start button
		JButton start = new JButton("Start!");
		start.addActionListener(
		// anonymous action listener class to call restart
		new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// when the restart button is clicked, restart
				startGame();
			}
		});
		// add buttons panel to SOUTH
		add(start, BorderLayout.SOUTH);
	}

	/**
	 * Create introduction panel
	 */
	private JPanel createIntro() {
		// initialize a new JPanel
		JPanel panel = new JPanel(new GridLayout(2, 1));
		// initialize picture
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO
					.read(new File("supportFiles/mediaFiles/VN.jpg"));
		} catch (IOException e) {
			// print out error
			e.printStackTrace();
		}
		// add picture to JLabel
		JLabel label = new JLabel(new ImageIcon(myPicture));
		panel.add(label);

		// initialize intro text area
		intro = new JTextArea();
		// Set font to JTextArea
		intro.setFont(new Font("Serif", Font.ITALIC, 20));
		// Set margin to JTextArea
		intro.setMargin(new Insets(10, 10, 10, 10));
		// can't be edit
		intro.setEditable(false);
		try {
			// Create FileInputStream pointing this file.
			fin = new FileInputStream("supportFiles/textFiles/SlowLoadText.txt");
			// Create a new thread
			new Thread(new Runnable() {
				public void run() {
					try {
						// Read and store every byte in k till end of file
						while ((k = fin.read()) != -1) {
							try {

								// Append a char, string is needed so "" is
								// attached
								intro.append("" + (char) k);

								// Set caret position to last for the effect
								intro.setCaretPosition(intro.getText().length());

								// Sleep for sometime for slow effect
								Thread.sleep(50);

								// read() and Thread.sleep() throws checked
								// exceptions, so that's why..
							} catch (Exception e) {
							}
						}
					} catch (Exception e) {
					}
				}
				// Start the thread
			}).start();
			// exceptions
		} catch (Exception e) {
		}

		panel.add(intro);
		return panel;
	}

	/**
	 * Start game
	 */
	private void startGame() {
		// clear the 1st screen, remove everything
		removeAll();
		// initialize 2nd GUI screen
		init2nd();
		revalidate();
	}

	/**
	 * Initialize 2nd GUI screen
	 */
	private void init2nd() {
		// set new borderlayout
		setLayout(new BorderLayout());

		// add instruction panel to NORTH
		add(createInstruction(), BorderLayout.NORTH);

		picLabel = createYesPic();
		// add pictures and questions to the center
		add(picLabel, BorderLayout.CENTER);

		// add buttons panel to SOUTH
		add(createSouthPanel(), BorderLayout.SOUTH);
	}

	/**
	 * Create instruction text area
	 */
	private JTextArea createInstruction() {
		// initialize text area
		instruction = new JTextArea();
		// allows words to go to next line
		instruction.setLineWrap(true);
		// prevents words from splitting
		instruction.setWrapStyleWord(true);
		// can't be edit
		instruction.setEditable(false);
		// set margin (top, left, bottom, right)
		instruction.setMargin(new Insets(10, 10, 10, 10));
		// Set font to JTextArea
		instruction.setFont(new Font("Serif", Font.ITALIC, 15));
		// set color to it
		instruction.setBackground(new Color(232, 216, 62));
		// set text to it
		instruction
				.setText("Suggested places to visit:"
						+ "\nHanoi Capital, Ho Chi Minh City, Hue City, Sa Pa, Hoi An Ancient Town, "
						+ "Da Nang Beach, Nha Trang Beach, Da lat, Phu Quoc Island, Ha Long Bay, "
						+ "Mui Ne, Cat Ba Island, Trang An, Mai Chau, MeKong Delta, Ca Mau");
		// return the instruction
		return instruction;
	}

	/**
	 * Create photo and questions
	 * 
	 * @return JPanel
	 */
	private JLabel createYesPic() {
		// initialize picture
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File(
					"supportFiles/mediaFiles/RainbowThinker.jpg"));
		} catch (IOException e) {
			// print out error
			e.printStackTrace();
		}
		// add picture to JLabel
		picLabel = new JLabel(new ImageIcon(myPicture));
		return picLabel;
	}

	private JLabel createNoPic() {
		// initialize picture
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File(
					"supportFiles/mediaFiles/Thinker.jpg"));
		} catch (IOException e) {
			// print out error
			e.printStackTrace();
		}
		// add picture to JLabel
		picLabel = new JLabel(new ImageIcon(myPicture));
		return picLabel;
	}

	/**
	 * Create button panel with yes and no buttons
	 * 
	 * @return JPanel
	 */
	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new BorderLayout());

		// add question to the right
		question = new JTextArea();
		// allows words to go to next line
		question.setLineWrap(true);
		// prevents words from splitting
		question.setWrapStyleWord(true);
		// can't be edit
		question.setEditable(false);
		// set margin (top, left, bottom, right)
		question.setMargin(new Insets(10, 10, 10, 10));
		// Set font to JTextArea
		question.setFont(new Font("Serif", Font.BOLD, 20));
		// set question text
		question.setText((String) controller.getCurrentNode().getData());
		// add JTextArea to JPanel
		southPanel.add(question, BorderLayout.NORTH);

		buttonPanel = new JPanel(new GridLayout(0, 2));
		// initialize yes button
		yes = new JButton("Yes");
		yes.addActionListener(this);
		// initialize no button
		no = new JButton("No");
		no.addActionListener(this);
		// add buttons to the panel
		buttonPanel.add(yes);
		buttonPanel.add(no);
		// add to the south panel
		southPanel.add(buttonPanel, BorderLayout.SOUTH);
		return southPanel;
	}

	/**
	 * Method to create restart button acting as start button
	 */
	public void restartGame() {
		// create a new controller
		controller = new GuessingGameController(this);
		// create a new button
		JButton restart = new JButton("Restart!");
		// add action listener
		restart.addActionListener(
		// anonymous action listener class to call restart
		new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// when the restart button is clicked, restart
				startGame();
			}
		});
		// replace yes/no buttons with restart button
		buttonPanel.removeAll();
		buttonPanel.add(restart);
		revalidate();
		repaint();
	}

	/**
	 * Method called to update the picture on screen
	 */
	private void updatePic(boolean clickYes) {
		remove(picLabel);
		// if user click yes
		if (clickYes)
			// add pictures and questions to the center
			add(createYesPic(), BorderLayout.CENTER);
		else
			// add pictures and questions to the center
			add(createNoPic(), BorderLayout.CENTER);
		revalidate();
	}

	/**
	 * Method to play music
	 */
	public void playMusic() {
		try {
			// use .wav file
			AudioInputStream audio = AudioSystem.getAudioInputStream(new File(
					"supportFiles/mediaFiles/Chieu_Nay_Khong_Co_Mua_Bay.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (UnsupportedAudioFileException uae) {
			System.out.println(uae);
		} catch (IOException ioe) {
			System.out.println(ioe);
		} catch (LineUnavailableException lua) {
			System.out.println(lua);
		}
	}

	/**
	 * @return the question text area
	 */
	public JTextArea getQuestion() {
		return question;
	}

	/**
	 * Action performed when button is clicked
	 * 
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		// get source of button clicked
		JButton buttonPressed = (JButton) e.getSource();
		// if user clicked yes
		if (buttonPressed.equals(yes)) {
			// call the method from the controller
			controller.choseYes();
			// call the update picture method
			updatePic(true);
		}
		// if user clicked no
		else {
			// call the method from the controller
			controller.choseNo();
			// call the update picture method
			updatePic(false);
		}
	}
}
