import javax.swing.*;
import java.awt.*;

	// Class to manage the display of a timetable in a graphical user interface.
class PrintTable {
	Table[][][] ttable; // Three-dimensional array to store timetable data for multiple groups.
	JFrame tableframe; // JFrame to display the timetable.
	int stgrp; // The number of student groups.
	TablePanel panel; // Custom panel to display the timetable data.
	JPanel south; // A panel for additional GUI components, if needed.
	InputData input; // Object containing all necessary data for the timetable.
	JMenuBar menuBar = new JMenuBar(); // Menu bar for the frame.
	JMenu file = new JMenu("File"); // Menu item for file operations.
	JMenuItem save = new JMenuItem("Save"); // Menu item to save the timetable.

	// Constructor to initialize the PrintTable with the timetable data, number of groups, and input data.
	public PrintTable(Table[][][] t, int nostgrp, InputData input1) {
		ttable = t; // Assign the timetable data.
		tableframe = new JFrame("TimeTable"); // Title for the JFrame.
		stgrp = nostgrp; // Set the number of student groups.
		south = new JPanel(); // Initialize the panel for additional components.
		input = input1; // Assign the input data.
	}

	// Method to configure and display the JFrame.
	void print() {
		panel = new TablePanel(ttable, stgrp, input, save); // Initialize the custom panel with the timetable.

		tableframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Set default close operation.
		tableframe.setLayout(new BorderLayout()); // Set the layout for the JFrame.
		tableframe.setJMenuBar(menuBar); // Add the menu bar to the frame.
		tableframe.add(panel, BorderLayout.CENTER); // Add the custom panel to the center of the layout.

		menuBar.add(file); // Add the "File" menu to the menu bar.
		file.add(save); // Add the "Save" item to the "File" menu.

		tableframe.pack(); // Pack the components within the frame.
		tableframe.setLocationRelativeTo(null); // Center the frame relative to the screen.
		tableframe.setVisible(true); // Make the frame visible.
	}
}
