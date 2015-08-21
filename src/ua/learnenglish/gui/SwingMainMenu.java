package ua.learnenglish.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SwingMainMenu {

	private JPanel contentPane;

	/**
	 * @wbp.parser.entryPoint
	 */
	public JMenuBar getMenuBar(JPanel cp) { 
		this.contentPane = cp;
		
        JMenuBar menubar = new JMenuBar();
        
        JMenu filemenu = new JMenu("File");
        menubar.add(filemenu);
        JMenuItem fileItem1 = new JMenuItem("Exit");
        fileItem1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        JSeparator separator_1 = new JSeparator();
        filemenu.add(separator_1);
        filemenu.add(fileItem1);
        
        JMenu mnView = new JMenu("View");
        menubar.add(mnView);
        
        JMenuItem viewItem1 = new JMenuItem("Main frame");
        viewItem1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) { 		      		
        		contentPane.removeAll();
        		//contentPane.repaint();
        		contentPane.revalidate();
        		contentPane.add(new PanelMain(contentPane));
        		//contentPane.repaint();
        		//contentPane.revalidate();
        	}
        });
        mnView.add(viewItem1);
        
        JSeparator separator = new JSeparator();
        mnView.add(separator);
        
        JMenuItem viewItem2 = new JMenuItem("Add new words");
        viewItem2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		contentPane.removeAll();
        		//contentPane.repaint();
        		contentPane.revalidate();
        		contentPane.add(new PanelDBwords(contentPane));
        		//contentPane.repaint();
        		//contentPane.revalidate();
        	}
        });
        mnView.add(viewItem2);
        
        JMenuItem viewItem3 = new JMenuItem("Add new Phrases");
        viewItem3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		contentPane.removeAll();
        		//contentPane.repaint();
        		contentPane.revalidate();
        		contentPane.add(new PanelDBphrases(contentPane));
        		//contentPane.repaint();
        		//contentPane.revalidate();
        	}
        });
        mnView.add(viewItem3);
        
        JMenuItem viewItem4 = new JMenuItem("Add new Status");
        viewItem4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		contentPane.removeAll();
        		//contentPane.repaint();
        		contentPane.revalidate();
        		contentPane.add(new PanelDBStatus(contentPane));
        	}
        });
        mnView.add(viewItem4);
        
        //
        JMenu menuHelp = new JMenu("Help");
        menubar.add(menuHelp);
        JMenuItem helpItem1 = new JMenuItem("Check new version...");
        helpItem1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null,"Here will be implemented module \nfor upgrading program by the Internet :)");
        	}
        });
        menuHelp.add(helpItem1);
        menuHelp.add(new JSeparator());
        JMenuItem helpItem2 = new JMenuItem("About!");
        helpItem2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		new About();
        	}
        });
        menuHelp.add(helpItem2);

        return menubar;
    }
}
