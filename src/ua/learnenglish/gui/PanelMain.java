package ua.learnenglish.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

class PanelMain extends javax.swing.JPanel
{
    private JPanel contentPane;
    private JTabbedPane tabbedPane;

    public PanelMain(JPanel cp) 
    {
    	this.contentPane = cp;
        init();
    }

    private void init() 
    {	
	    // Add content
		setLayout(null);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 5, 794, 544);
		add(tabbedPane);
		new MFScorePane(tabbedPane);
		new MFWordPane(tabbedPane);
		new MFPhrasesPane(tabbedPane);
    }
}
