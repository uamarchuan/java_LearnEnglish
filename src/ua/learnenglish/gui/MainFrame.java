package ua.learnenglish.gui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ua.learnenglish.Settings;
import java.awt.CardLayout;



public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	
	//private FirstCard panel1;
	//private SecondCard panel2;
	
	public MainFrame() {
		createAndShowGUI();
	}
	
	
    private void createAndShowGUI() {

        // Create and set up the window.
        frame = new JFrame();
        frame.setTitle(new Settings().strProgranName);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
      
        
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());
        
        // Add Main Menu
        SwingMainMenu swingMenu = new SwingMainMenu();
        frame.setJMenuBar(swingMenu.getMenuBar(contentPane));
        
        // Add content first JPanel

        contentPane.add(new PanelMain(contentPane));


        // Display the window.
//        frame.pack();
        frame.setContentPane(contentPane);   
        frame.setVisible(true);
    }
}
