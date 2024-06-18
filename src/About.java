import java.awt.*;
import javax.swing.*;

public class About {
	private JFrame frmAboutUs = new JFrame("About Us");
	private JPanel panel = new JPanel();
	
	public About() {
		frmAboutUs.add(panel);
		frmAboutUs.setResizable(false);
		frmAboutUs.setBounds(100, 100, 300, 450);
		frmAboutUs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAboutUs.setLocationRelativeTo(null);
		
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		JLabel lblAboutUs = new JLabel("ABOUT US");
		lblAboutUs.setHorizontalAlignment(SwingConstants.CENTER);
		lblAboutUs.setFont(new Font("Euphemia", Font.BOLD, 35));
		lblAboutUs.setBounds(10, 11, 274, 65);
		panel.add(lblAboutUs);


		ImageIcon originalIcon = new ImageIcon("imgs\\logo.png");
		Image originalImage = originalIcon.getImage();
		// Scale it to fit a specific size
		Image resizedImage = originalImage.getScaledInstance(180, 180, Image.SCALE_SMOOTH);
		// Convert it back to an ImageIcon for use in a JLabel
		ImageIcon resizedIcon = new ImageIcon(resizedImage);

		// Create a JLabel for the logo with resized image
		JLabel logoLabel = new JLabel(resizedIcon);
		logoLabel.setBounds(90, 90, 100, 100); // Center the logo in the window
        panel.add(logoLabel);


		JLabel noamFeldmanProducer = new JLabel("Noam Feldman Producer");
		noamFeldmanProducer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		noamFeldmanProducer.setBounds(10, 221, 274, 25);
		panel.add(noamFeldmanProducer);



		frmAboutUs.setVisible(true);
	}
}
