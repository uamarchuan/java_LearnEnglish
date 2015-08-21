package ua.learnenglish;

import javax.swing.SwingUtilities;

import ua.learnenglish.gui.MainFrame;

public class Initial {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
            	new MainFrame();
            }
        });
	}
}