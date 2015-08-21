package ua.learnenglish.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import ua.learnenglish.db.sqliteConnection;


public class MFPhrasesPane {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JTabbedPane tabbedPane;
	private JButton btnFinish, btnStart, btnNext;
	private JTextField txtPhrase, txtExample, txtTranslation, txtName;
	private String db_phrase, db_translation, db_example;
	private JLabel lblStatus, lblTotal, lblScore, lblCorr;
	private JProgressBar progressBar;
	private int barVal, iTotal;
	private boolean cheskMyself = true;
		
	public MFPhrasesPane (JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
		panePhrase();
	}
	
	private void StartLearnWord() {
		txtName.setEditable(false);
		btnStart.setEnabled(false);
		txtTranslation.setEditable(true);
		btnFinish.setEnabled(true);
		btnNext.setEnabled(true);
		lblTotal.setText("0");
		lblScore.setText("0");
		cleanValuesAndFormat();
		getPhrase();
	}
	
	private void StopLearnWord() {
		cleanValuesAndFormat();
		txtTranslation.setBackground(Color.LIGHT_GRAY);
		txtTranslation.setEditable(false);
		btnNext.setEnabled(false);
		btnFinish.setEnabled(false);
		txtName.setText(null);
		txtName.setEditable(true);
		btnStart.setEnabled(true);
		lblStatus.setText("");
		lblTotal.setText("0");
		lblScore.setText("0");
		lblCorr.setText("0");
		updProdressBar(0);
	}
	
	private void getPhrase(){
		conn = sqliteConnection.dbConnector();
		String sql = "SELECT * FROM `phrase` ORDER BY RANDOM() LIMIT 1";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			//int db_id = rs.getInt("id");
			db_phrase = rs.getString("eng_text");
			db_example = rs.getString("example");
			db_translation = rs.getString("translation");
			
			txtPhrase.setText(db_phrase);		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	
	private void panePhrase() {
		JLayeredPane panePhrase = new JLayeredPane();
		panePhrase.setSize(800, 600);
        tabbedPane.addTab("Phrases", null, panePhrase, null);
        panePhrase.setBackground(new Color(255, 0, 0));
        
        JPanel panelInit = new JPanel();
        panelInit.setBorder(new TitledBorder(null, "Initial frame", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelInit.setBounds(0, 5, 790, 98);
        panePhrase.add(panelInit);
        panelInit.setLayout(null);
        
        txtName = new JTextField();
        txtName.setHorizontalAlignment(SwingConstants.CENTER);
        txtName.setFont(new Font("Dialog", Font.BOLD, 12));
        txtName.setBounds(282, 42, 180, 23);
        panelInit.add(txtName);
        txtName.setColumns(10);
        
        JLabel lblName = new JLabel("Please enter your name:");
        lblName.setBounds(84, 46, 180, 14);
        panelInit.add(lblName);
        
        btnStart = new JButton("Start");
        btnStart.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnStart.addActionListener(new StartPhrase());
        btnStart.setBounds(540, 16, 211, 71);
        panelInit.add(btnStart);
        
        JPanel panelBody = new JPanel();
        panelBody.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Learn frame", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelBody.setBounds(0, 109, 790, 396);
        panePhrase.add(panelBody);
        panelBody.setLayout(null);
        
        progressBar = new JProgressBar();
        progressBar.setBounds(37, 317, 709, 14);
        progressBar.setStringPainted(true);
        panelBody.add(progressBar);
        
        txtPhrase = new JTextField();
        txtPhrase.setEditable(false);
        txtPhrase.setForeground(SystemColor.textHighlight);
        txtPhrase.setBounds(37, 36, 709, 23);
        panelBody.add(txtPhrase);
        txtPhrase.setFont(new Font("Verdana", Font.BOLD, 14));
        txtPhrase.setColumns(10);
        
        txtTranslation = new JTextField();
        txtTranslation.setBackground(Color.LIGHT_GRAY);
        txtTranslation.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
    			if (txtName.getText().trim().length() != 0){
    				checkAns();
    			}
        	}
        });
        txtTranslation.setEditable(false);
        txtTranslation.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtTranslation.setBounds(37, 161, 709, 54);
        panelBody.add(txtTranslation);
        txtTranslation.setColumns(10);
        
        btnFinish = new JButton("Finish");
        btnFinish.setEnabled(false);
        btnFinish.setBounds(625, 254, 121, 32);
        btnFinish.addActionListener(new FinishPhrases());
        panelBody.add(btnFinish);
        
        btnNext = new JButton("Next");
        btnNext.setBounds(500, 254, 121, 32);
        btnNext.addActionListener(new NextPhrase());
        panelBody.add(btnNext);
        btnNext.setEnabled(false);
        
        JLabel lblPhrase = new JLabel("Phrase");
        lblPhrase.setForeground(Color.RED);
        lblPhrase.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        lblPhrase.setBounds(37, 22, 89, 14);
        panelBody.add(lblPhrase);
        
        txtExample = new JTextField();
        txtExample.setForeground(new Color(128, 128, 128));
        txtExample.setFont(new Font("Verdana", Font.BOLD, 14));
        txtExample.setEditable(false);
        txtExample.setColumns(10);
        txtExample.setBounds(37, 93, 709, 23);
        panelBody.add(txtExample);
        
        JLabel lblExample = new JLabel("Example");
        lblExample.setForeground(Color.RED);
        lblExample.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        lblExample.setBounds(37, 79, 103, 14);
        panelBody.add(lblExample);
        
        JLabel lblTranslation = new JLabel("Translation");
        lblTranslation.setForeground(new Color(0, 255, 0));
        lblTranslation.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblTranslation.setBounds(37, 144, 89, 14);
        panelBody.add(lblTranslation);
        
        JLabel lblStatText = new JLabel("Status: ");
        lblStatText.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblStatText.setBounds(50, 355, 56, 14);
        panelBody.add(lblStatText);
        
        lblStatus = new JLabel("Let's try to win :)");
        lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
        lblStatus.setBounds(106, 355, 615, 14);
        panelBody.add(lblStatus);
        
        JLabel lblTotalCount = new JLabel("Total: ");
        lblTotalCount.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTotalCount.setBounds(37, 261, 56, 14);
        panelBody.add(lblTotalCount);
        
        lblTotal = new JLabel("0");
        lblTotal.setForeground(new Color(199, 21, 133));
        lblTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTotal.setBounds(100, 261, 56, 14);
        panelBody.add(lblTotal);
        
        JLabel lblCorrCount = new JLabel("Correct: ");
        lblCorrCount.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCorrCount.setBounds(156, 261, 79, 14);
        panelBody.add(lblCorrCount);
        
        lblCorr = new JLabel("0");
        lblCorr.setForeground(new Color(199, 21, 133));
        lblCorr.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCorr.setBounds(240, 261, 56, 14);
        panelBody.add(lblCorr);
        
        JLabel lblScoreCount = new JLabel("Score: ");
        lblScoreCount.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblScoreCount.setBounds(304, 261, 66, 14);
        panelBody.add(lblScoreCount);
        
        lblScore = new JLabel("0");
        lblScore.setForeground(new Color(199, 21, 133));
        lblScore.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblScore.setBounds(370, 261, 56, 14);
        panelBody.add(lblScore);
	}
	
	class StartPhrase implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (txtName.getText().trim().length() != 0){
				StartLearnWord();
			}else{
				JOptionPane.showMessageDialog(null, "Please enter your name!");
			}
		}
	}
	class NextPhrase implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			checkAns();
			if (iTotal == 100){
				statInfo();
				StopLearnWord();
			}else{
				cheskMyself = true;
				cleanValuesAndFormat();
				getPhrase();
			}
		}
	}
	
	private void cleanValuesAndFormat(){
		txtPhrase.setText(null);
		txtExample.setText(null);
		txtTranslation.setText(null);
        txtTranslation.setBackground(Color.WHITE);
        txtTranslation.setForeground(Color.BLACK);
	}
	
	public void updProdressBar (int value){
		progressBar.setValue(value);
		if ( progressBar.getMaximum() <= value ) {
		    Toolkit.getDefaultToolkit().beep();
		}
	}
	

	public void checkAns() {
		if (cheskMyself == true) {
			String valResult = txtTranslation.getText().trim().toLowerCase();
			iTotal = 1 + Integer.parseInt(lblTotal.getText());
			lblTotal.setText(Integer.toString(iTotal));
			int iCorr = Integer.parseInt(lblCorr.getText());
			
			if (valResult.equalsIgnoreCase(db_translation)) {
				txtExample.setText(db_example);
		        txtTranslation.setBackground(Color.GREEN);
		        txtTranslation.setForeground(Color.BLACK);
				iCorr += 1;
				lblCorr.setText(Integer.toString(iCorr));
		        barVal += 1;
		        lblScore.setText(Integer.toString(barVal));
		        updProdressBar(iTotal);
				lblStatus.setText(getStatusText(1));
			}else {
				txtExample.setText(db_example);
		        txtTranslation.setBackground(Color.RED);
		        txtTranslation.setForeground(Color.BLACK);
		        txtTranslation.setText("Your answer is: \""+valResult+"\"; But correct is: \""+db_translation.toLowerCase()+"\"");
				barVal -= 2;
				lblScore.setText(Integer.toString(barVal));
				updProdressBar(iTotal);
				lblStatus.setText(getStatusText(2));
			}

			cheskMyself = false;
		}
	}

	private void statInfo() {
		int iTotal = Integer.parseInt(lblTotal.getText());
		if (iTotal > 0) {
			int iCorr = Integer.parseInt(lblCorr.getText());
			int iScore = Integer.parseInt(lblScore.getText());
			String dbUser = txtName.getText().trim();
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date date = new Date();
			String iDate = dateFormat.format(date);
			
			conn = sqliteConnection.dbConnector();
			String sql = "INSERT INTO `score`(`date`,`game`,`user`,`words_all`,`words_correct`,`score`)"
					+ " VALUES (?,?,?,?,?,?);";
			try{
				pst = conn.prepareStatement(sql);
				pst.setString(1, iDate);
				pst.setInt(2, 2);
				pst.setString(3, dbUser);
				pst.setInt(4, iTotal);
				pst.setInt(5, iCorr);
				pst.setInt(6, iScore);
	
				pst.execute();
					
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			} finally {
				try {
					rs.close();
					pst.close();
					conn.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
			
			Float corPercentages = (float) ((iCorr * 100)/iTotal);
			JOptionPane.showMessageDialog(null, "Dear: "+dbUser+"\nTotal words was: "+iTotal+"\nYou ans correctly was: "+
					iCorr+"\nYour progress: "+corPercentages+"%");
		}
	}

	private String getStatusText(int i) {
		String StatusText = null;
		conn = sqliteConnection.dbConnector();
		String sql = "SELECT en_text FROM status WHERE reason=? ORDER BY RANDOM() LIMIT 1";
		try{
			pst = conn.prepareStatement(sql);
			pst.setString(1, Integer.toString(i));
			rs = pst.executeQuery();

			StatusText = rs.getString("en_text");
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return StatusText;
	}

	class FinishPhrases implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			statInfo();
			StopLearnWord();
		}
	}	
}
