package ua.learnenglish.gui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import ua.learnenglish.Settings;

import javax.swing.JLabel;
import java.awt.Font;

public class About {
	/**
	 * @wbp.parser.entryPoint
	 */
	public About() {
        JFrame frame = new JFrame();
        frame.setTitle("About - "+ new Settings().strProgranName);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
              
        JLabel lblFoto = new JLabel();
		Image img_logo = new ImageIcon(this.getClass().getResource("/me.jpg")).getImage();
		lblFoto.setIcon(new ImageIcon(img_logo));
        
        lblFoto.setBounds(10, 11, 200, 226);
        frame.getContentPane().add(lblFoto);
        
        JLabel lblAboutMe = new JLabel("About me!");
        lblAboutMe.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblAboutMe.setBounds(308, 27, 82, 22);
        frame.getContentPane().add(lblAboutMe);
        
        JLabel lblNewLabel = new JLabel("E_mail: uamarchuan@mail.com");
        lblNewLabel.setBounds(242, 61, 214, 28);
        frame.getContentPane().add(lblNewLabel);
        
        JLabel lblMobile = new JLabel("Mobile: +38 050 3560363 ");
        lblMobile.setBounds(242, 86, 200, 22);
        frame.getContentPane().add(lblMobile);
        frame.setLocationRelativeTo(null);
		
        
        frame.setVisible(true);
	}
}
