import java.awt.*;// ייבוא חבילות לעבודה עם גרפיקה ב-Java
import java.awt.event.*;// ייבוא חבילות לעבודה עם אירועים בגרפיקה

import javax.swing.*;// ייבוא חבילות לעבודה עם ממשק משתמש גרפי

public class Start {
	private JFrame frmStart = new JFrame("TimeTable Generation System");// יצירת חלון חדש
	private JMenuBar menuBar = new JMenuBar();// יצירת סרגל תפריט
	private JMenu mnFile = new JMenu("File");// יצירת תפריט קובץ



	private JLabel backImage = new JLabel(new ImageIcon("imgs\\back.jpg"));// יצירת תווית עם תמונת רקע
	private JLabel title = new JLabel("TimeTable Generation System");// יצירת תווית עם כותרת
	private JButton btnLogin = new JButton("Login", new ImageIcon("imgs\\licon.png"));// יצירת כפתור כניסה עם אייקון
	private JButton btnExit = new JButton("Exit", new ImageIcon("imgs\\exit.png"));// יצירת כפתור יציאה עם אייקון

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // הגדרת נראות הממשק למראה המערכת
		}
		catch(Exception e) {
		}

		new Start();// יצירת אובייקט של המחלקה הנוכחית
	}

	public Start() {
		frmStart.setJMenuBar(menuBar); // הוספת סרגל התפריט לחלון
		ImageIcon originalIcon = new ImageIcon("imgs\\logo.png"); // טעינת תמונת לוגו
		Image originalImage = originalIcon.getImage();// הופכת את ImageIcon ל-Image

		// שינוי גודל התמונה להתאמה מסוימת
		Image resizedImage = originalImage.getScaledInstance(135, 135, Image.SCALE_SMOOTH);
		// המרה חזרה ל-ImageIcon לשימוש ב-JLabel
		ImageIcon resizedIcon = new ImageIcon(resizedImage);

		// יצירת תווית ללוגו עם התמונה ששונתה
		JLabel logoLabel = new JLabel(resizedIcon);
		logoLabel.setBounds(180, 75, 100, 100); // מיקום מרכזי של הלוגו בחלון
		frmStart.add(logoLabel);
		frmStart.add(backImage);// הוספת התמונת רקע לחלון
		frmStart.setResizable(false);// מניעת שינוי גודל החלון
		frmStart.setBounds(100, 100, 480, 350);// קביעת גודל ומיקום החלון
		frmStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// קביעת פעולת סגירה ליציאה מהתוכנית
		frmStart.setLocationRelativeTo(null);// מיקום החלון במרכז המסך

		menuBar.add(mnFile);// הוספת תפריט הקובץ לסרגל התפריט


		JMenuItem mntmAboutUs = new JMenuItem("About Us");// יצירת פריט תפריט עלינו
		mntmAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new About();// יצירת חלון עלינו בלחיצה
			}
		});
		mnFile.add(mntmAboutUs);// הוספת פריט התפריט לתפריט הקובץ

		JMenuItem mntmExit = new JMenuItem("Exit");// יצירת פריט תפריט יציאה
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}// סגירת התוכנית
		});
		mnFile.add(mntmExit);// הוספת פריט התפריט לתפריט הקובץ

		backImage.setLayout(null);// הגדרת תצורת התצוגה של התמונת רקע

		title.setBounds(-12, 0, 500, 90); // מיקום וגודל של הכותרת
		title.setFont(new Font("Chiller", Font.BOLD, 45));// סגנון הגופן של הכותרת
		title.setHorizontalAlignment(SwingConstants.CENTER);// מרכוז הכותרת
		backImage.add(title);// הוספת הכותרת לתמונת הרקע

		btnLogin.setBounds(129, 186, 230, 50);// קביעת מיקום וגודל כפתור הכניסה
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmStart.dispose();// סגירת החלון הנוכחי
				new Login();// יצירת חלון הכניסה
			}
		});
		backImage.add(btnLogin);// הוספת כפתור הכניסה לתמונת הרקע

		btnExit.setBounds(129, 240, 230, 50);// קביעת מיקום וגודל כפתור היציאה
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}// סגירת התוכנית
		});
		backImage.add(btnExit);// הוספת כפתור היציאה לתמונת הרקע
		
		frmStart.setVisible(true);// הפעלת החלון להצגה
	}
}
