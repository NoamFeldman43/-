import java.awt.*; // Import Java AWT package for building graphical interfaces.
import javax.swing.*; // Import Swing library for more comprehensive GUI components.
import java.awt.event.*; // Import AWT event package for handling user events.
import java.util.Arrays; // Import Arrays utility from java.util for array operations.

public class Login {
	private JFrame frmLogin = new JFrame("LOGIN"); // Main frame for the login window.
	private JTextField textField; // Text field for username input.
	private JPasswordField passwordField; // Password field for secure password input.
	private String dialogMessage, dialogs; // Strings for storing dialog messages.
	private JProgressBar progressBar; // Progress bar to show loading or processing progress.

	public Login() {
		frmLogin.setResizable(false); // Disables the ability to resize the login frame.
		frmLogin.setBackground(Color.WHITE); // Sets the background color of the frame.
		frmLogin.setBounds(100, 100, 470, 350); // Sets the size and position of the frame.
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ends the program when the window is closed.
		frmLogin.setLayout(new BorderLayout(0, 0)); // Uses BorderLayout manager for layout.
		frmLogin.setLocationRelativeTo(null); // Centers the window on the screen.

		ImageIcon originalIcon = new ImageIcon("imgs\\logo.png"); // Loads an image for the logo.
		Image originalImage = originalIcon.getImage(); // Converts the icon to an Image object.
		// Scale it to fit a specific size
		Image resizedImage = originalImage.getScaledInstance(135, 135, Image.SCALE_SMOOTH); // Resizes the image.
		// Convert it back to an ImageIcon for use in a JLabel
		ImageIcon resizedIcon = new ImageIcon(resizedImage); // Converts the resized image back to an ImageIcon.

		// Create a JLabel for the logo with resized image
		JLabel logoLabel = new JLabel(resizedIcon);
		logoLabel.setBounds(180, 10, 100, 100); // Places and sizes the logo label within the frame.
		frmLogin.add(logoLabel);

		JLabel backImage = new JLabel(new ImageIcon("imgs\\back.png")); // Background image for the login form.
		frmLogin.add(backImage);
		backImage.setLayout(null); // Uses absolute positioning within the label.

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(70, 145, 78, 14);
		backImage.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1.setBounds(70, 182, 78, 14);
		backImage.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(158, 144, 206, 20);
		backImage.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(158, 181, 206, 20);
		backImage.add(passwordField);

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loginname = textField.getText(); // Gets username from text field.
				char[] loginpass = passwordField.getPassword(); // Gets password from password field.
				dialogMessage = "Welcome - " + loginname.toUpperCase(); // Prepares a welcome message.
				if( (loginname.equals("admin") && Arrays.equals("admin".toCharArray(), loginpass))  ) {
					new Thread(new PBar(loginname)).start(); // Starts a new thread to handle the progress bar.
				}
				else {
					JOptionPane.showMessageDialog(null, "Invalid User Name and Password!", "ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
					textField.setText("");
					passwordField.setText("");
				}
			}
		});
		btnNewButton.setBounds(130, 215, 89, 23);
		backImage.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(""); // Clears the text field.
				passwordField.setText(""); // Clears the password field.
			}
		});
		btnNewButton_1.setBounds(265, 215, 89, 23);
		backImage.add(btnNewButton_1);

		progressBar = new JProgressBar(0, 100); // Initializes the progress bar with min and max values.
		progressBar.setBounds(100, 270, 294, 14); // Sets the position and size of the progress bar.
		backImage.add(progressBar);

		frmLogin.setVisible(true); // Makes the frame visible.
	}

	class PBar implements Runnable {
		String name; // Variable to hold the username.

		public PBar(String s) {
			name = s; // Constructor assigns the username.
		}

		public void run() {
			for (int i=0; i<=100; i++) { // Runs a loop to simulate progress.
				progressBar.setValue(i); // Updates the progress bar value.
				progressBar.repaint(); // Forces the progress bar to repaint.
				try {
					Thread.sleep(15); // Pauses the thread to simulate a loading process.
				}
				catch (Exception e) {
				}
			}

			JOptionPane.showMessageDialog(null, dialogMessage, dialogs, JOptionPane.INFORMATION_MESSAGE); // Displays a welcome dialog.
			frmLogin.dispose(); // Closes the login frame.

			if(name.equals("admin")) // Checks if the username is admin.
				new AdminLogin(); // Opens the admin login interface.
		}
	}
}
