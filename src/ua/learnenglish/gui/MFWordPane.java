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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import ua.learnenglish.db.sqliteConnection;
import javax.swing.SwingConstants;


public class MFWordPane {
	
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JTabbedPane tabbedPane;
	private JButton btnFinish, btnStart, btnNext;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtWord, txtTranscription, txtTranslation, txtName;
	private String db_e_word, db_transcription,	db_u_word1, db_u_word2, db_u_word3, db_u_word4, db_u_word5;
	private JLabel lblStatus, lblTotal, lblScore, lblCorr;
	private Byte selVariant = 1; 
	private JRadioButton rbEnglish, rbUkraine, rbMix;
	private JProgressBar progressBar;
	private int barVal;
	private boolean cheskMyself = true;
	private int iTotal;
	private ArrayList<String> arrWords = new ArrayList<String>();
		
	public MFWordPane (JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
		paneWord();
	}
	
	private void StartLearnWord() {
		txtName.setEditable(false);
		btnStart.setEnabled(false);
		txtTranslation.setEditable(true);
		btnFinish.setEnabled(true);
		btnNext.setEnabled(true);
		rbEnglish.setEnabled(false);
		rbUkraine.setEnabled(false);
		rbMix.setEnabled(false);
		lblTotal.setText("0");
		lblScore.setText("0");
		cleanValuesAndFormat();
		getWords();
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
		rbEnglish.setEnabled(true);
		rbUkraine.setEnabled(true);
		rbMix.setEnabled(true);
		lblStatus.setText("");
		lblTotal.setText("0");
		lblScore.setText("0");
		lblCorr.setText("0");
		updProdressBar(0);
	}
	
	private void getWords(){
		arrWords.clear();
		
		conn = sqliteConnection.dbConnector();
		String sql = "SELECT * FROM words ORDER BY RANDOM() LIMIT 1";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			db_e_word = rs.getString("e_word");
			db_transcription = rs.getString("transcription");
			db_u_word1 = rs.getString("u_word1");
			db_u_word2 = rs.getString("u_word2");
			db_u_word3 = rs.getString("u_word3");
			db_u_word4 = rs.getString("u_word4");
			db_u_word5 = rs.getString("u_word5");
			String s = null;
			Object key = null;
			boolean otherDesc = false;
			switch (selVariant) {
				case 1:
					txtWord.setText(db_e_word);
					txtTranscription.setText(db_transcription);
					arrWords.add(db_u_word1); // init first element in array
					s = db_u_word1+";";
					if (db_u_word2 != null && !db_u_word2.isEmpty()) {s=s+" "+db_u_word2+";"; otherDesc = true;}
					if (db_u_word3 != null && !db_u_word3.isEmpty()) {s=s+" "+db_u_word3+";"; otherDesc = true;}
					if (db_u_word4 != null && !db_u_word4.isEmpty()) {s=s+" "+db_u_word4+";"; otherDesc = true;}
					if (db_u_word5 != null && !db_u_word5.isEmpty()) {s=s+" "+db_u_word5+";"; otherDesc = true;}
					if(otherDesc == true){arrWords.add(s);}
					break;
				case 2:
					arrWords.add(db_u_word1);
					s = db_u_word1+";";
					if (db_u_word2 != null && !db_u_word2.isEmpty()) {arrWords.add(db_u_word2); s=s+" "+db_u_word2+";"; otherDesc = true;}
					if (db_u_word3 != null && !db_u_word3.isEmpty()) {arrWords.add(db_u_word3); s=s+" "+db_u_word3+";"; otherDesc = true;}
					if (db_u_word4 != null && !db_u_word4.isEmpty()) {arrWords.add(db_u_word4); s=s+" "+db_u_word4+";"; otherDesc = true;}
					if (db_u_word5 != null && !db_u_word5.isEmpty()) {arrWords.add(db_u_word5); s=s+" "+db_u_word5+";"; otherDesc = true;}
					if(otherDesc == true){arrWords.add(s);}
					arrWords.removeAll(Collections.singleton(null));
					arrWords.removeAll(Collections.singleton(""));
					key = new Random().nextInt(arrWords.size());
					txtWord.setText(arrWords.get((int) key));
					txtTranscription.setText("You mast know it!");
					break;
				case 3:
					arrWords.add(db_e_word);
					arrWords.add(db_u_word1);
					s = db_u_word1+";";
					if (db_u_word2 != null && !db_u_word2.isEmpty()) {arrWords.add(db_u_word2); s=s+" "+db_u_word2+";"; otherDesc = true;}
					if (db_u_word3 != null && !db_u_word3.isEmpty()) {arrWords.add(db_u_word3); s=s+" "+db_u_word3+";"; otherDesc = true;}
					if (db_u_word4 != null && !db_u_word4.isEmpty()) {arrWords.add(db_u_word4); s=s+" "+db_u_word4+";"; otherDesc = true;}
					if (db_u_word5 != null && !db_u_word5.isEmpty()) {arrWords.add(db_u_word5); s=s+" "+db_u_word5+";"; otherDesc = true;}
					if(otherDesc == true){arrWords.add(s);}
					
					//System.out.println(s);
					
					arrWords.removeAll(Collections.singleton(null));
					arrWords.removeAll(Collections.singleton(""));
					key = new Random().nextInt(arrWords.size());
					if ((int)key == 0) {
						txtWord.setText(arrWords.get((int) key));
						txtTranscription.setText(db_transcription);
					}else{
						txtWord.setText(arrWords.get((int) key));
						txtTranscription.setText("You mast know it!");
					}
					break;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			
//        } catch (Throwable any) {
//            System.out.println("Java ERROR: "+any);
//            any.printStackTrace();
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

	
	private void paneWord() {
		JLayeredPane paneWords = new JLayeredPane();
		paneWords.setSize(800, 600);
        tabbedPane.addTab("Words", null, paneWords, null);
        paneWords.setBackground(new Color(255, 0, 0));
        
        JPanel panelInit = new JPanel();
        panelInit.setBorder(new TitledBorder(null, "Initial frame", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelInit.setBounds(0, 5, 790, 98);
        paneWords.add(panelInit);
        panelInit.setLayout(null);
        
        rbEnglish = new JRadioButton("English words");
        rbEnglish.setSelected(true);
        rbEnglish.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		selVariant = 1;
        	}
        });
        buttonGroup.add(rbEnglish);
        rbEnglish.setBounds(6, 16, 124, 23);
        panelInit.add(rbEnglish);
        
        rbUkraine = new JRadioButton("Ukrainian words");
        rbUkraine.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		selVariant=2;
        	}
        });
        buttonGroup.add(rbUkraine);
        rbUkraine.setBounds(6, 42, 155, 23);
        panelInit.add(rbUkraine);
        
        rbMix = new JRadioButton("English + Ukrainian words");
        rbMix.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		selVariant = 3;
        	}
        });
        buttonGroup.add(rbMix);
        rbMix.setBounds(6, 68, 211, 23);
        panelInit.add(rbMix);
        
        txtName = new JTextField();
        txtName.setHorizontalAlignment(SwingConstants.CENTER);
        txtName.setFont(new Font("Dialog", Font.BOLD, 12));
        txtName.setBounds(282, 42, 180, 23);
        panelInit.add(txtName);
        txtName.setColumns(10);
        
        JLabel lblName = new JLabel("Please enter your name:");
        lblName.setBounds(282, 20, 180, 14);
        panelInit.add(lblName);
        
        btnStart = new JButton("Start");
        btnStart.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnStart.addActionListener(new StartWords());
        btnStart.setBounds(540, 16, 211, 71);
        panelInit.add(btnStart);
        
        JPanel panelBody = new JPanel();
        panelBody.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Learn frame", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelBody.setBounds(0, 109, 790, 396);
        paneWords.add(panelBody);
        panelBody.setLayout(null);
        
        progressBar = new JProgressBar();
        progressBar.setBounds(37, 317, 709, 14);
        progressBar.setStringPainted(true);
        panelBody.add(progressBar);
        
        txtWord = new JTextField();
        txtWord.setForeground(SystemColor.textHighlight);
        txtWord.setBounds(37, 36, 709, 23);
        panelBody.add(txtWord);
        txtWord.setFont(new Font("Verdana", Font.BOLD, 14));
        txtWord.setEditable(false);
        txtWord.setColumns(10);
        
        txtTranslation = new JTextField();
        txtTranslation.setForeground(Color.BLACK);
        txtTranslation.setBackground(Color.LIGHT_GRAY);
        txtTranslation.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
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
        btnFinish.addActionListener(new FinishWords());
        panelBody.add(btnFinish);
        
        btnNext = new JButton("Next");
        btnNext.setBounds(500, 254, 121, 32);
        btnNext.addActionListener(new NextWords());
        panelBody.add(btnNext);
        btnNext.setEnabled(false);
        
        JLabel lblWord = new JLabel("Word");
        lblWord.setForeground(Color.RED);
        lblWord.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        lblWord.setBounds(37, 22, 46, 14);
        panelBody.add(lblWord);
        
        txtTranscription = new JTextField();
        txtTranscription.setForeground(new Color(128, 128, 128));
        txtTranscription.setFont(new Font("Verdana", Font.BOLD, 14));
        txtTranscription.setEditable(false);
        txtTranscription.setColumns(10);
        txtTranscription.setBounds(37, 93, 709, 23);
        panelBody.add(txtTranscription);
        
        JLabel lblTranscription = new JLabel("Transcription");
        lblTranscription.setForeground(Color.RED);
        lblTranscription.setFont(new Font("Times New Roman", Font.ITALIC, 14));
        lblTranscription.setBounds(37, 79, 103, 14);
        panelBody.add(lblTranscription);
        
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
	
	class StartWords implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (txtName.getText().trim().length() != 0){
				StartLearnWord();
			}else{
				JOptionPane.showMessageDialog(null, "Please enter your name!");
			}
		}
	}
	class NextWords implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			checkAns();
			if (iTotal == 100){
				statInfo();
				StopLearnWord();
			}else{
				cheskMyself = true;
				cleanValuesAndFormat();
				getWords();
			}
		}
	}
	
	private void cleanValuesAndFormat() {
		txtWord.setText(null);
		txtTranscription.setText(null);
		txtTranslation.setText(null);
        txtTranslation.setBackground(Color.WHITE);
	}
	
	private void positiveFormat() {
        txtTranslation.setBackground(Color.GREEN);
	}
	
	private void negativeFormat() {
        txtTranslation.setBackground(Color.RED);
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
			if (!valResult.equalsIgnoreCase(txtWord.getText())){		
				switch (selVariant) {
					case 1:		
						if (valResult.equalsIgnoreCase(db_u_word1)) {
							barVal += 1;
							lblScore.setText(Integer.toString(barVal));
							iCorr += 1;
							lblCorr.setText(Integer.toString(iCorr));
							updProdressBar(iTotal);
							lblStatus.setText(getStatusText(1));
							positiveFormat();
						} else if (valResult.equalsIgnoreCase(db_u_word2)) {
							barVal += 1;
							lblScore.setText(Integer.toString(barVal));
							iCorr += 1;
							lblCorr.setText(Integer.toString(iCorr));
							updProdressBar(iTotal);
							lblStatus.setText(getStatusText(1));
							positiveFormat();
						} else if (valResult.equalsIgnoreCase(db_u_word3)) {
							barVal += 1;
							lblScore.setText(Integer.toString(barVal));
							iCorr += 1;
							lblCorr.setText(Integer.toString(iCorr));
							updProdressBar(iTotal);
							lblStatus.setText(getStatusText(1));
							positiveFormat();
						} else if (valResult.equalsIgnoreCase(db_u_word4)) {
							barVal += 1;
							lblScore.setText(Integer.toString(barVal));
							iCorr += 1;
							lblCorr.setText(Integer.toString(iCorr));
							updProdressBar(iTotal);
							lblStatus.setText(getStatusText(1));
							positiveFormat();
						} else if (valResult.equalsIgnoreCase(db_u_word5)) {
							barVal += 1;
							lblScore.setText(Integer.toString(barVal));
							iCorr += 1;
							lblCorr.setText(Integer.toString(iCorr));
							updProdressBar(iTotal);
							lblStatus.setText(getStatusText(1));
							positiveFormat();
						}else {
							barVal -= 2;
							lblScore.setText(Integer.toString(barVal));
							updProdressBar(iTotal);
							lblStatus.setText(getStatusText(2));
							negativeFormat();
							txtTranslation.setText("Your answer is: \""+valResult+"\"; But correct is: \""+arrWords.get(arrWords.size()-1)+"\"");
						}
						break;
					case 2:
						if (valResult.equalsIgnoreCase(db_e_word)){
							barVal += 1;
							lblScore.setText(Integer.toString(barVal));
							iCorr += 1;
							lblCorr.setText(Integer.toString(iCorr));
							updProdressBar(iTotal);
							lblStatus.setText(getStatusText(1));
							positiveFormat();
						}else{
							barVal -= 2;
							lblScore.setText(Integer.toString(barVal));
							updProdressBar(iTotal);
							lblStatus.setText(getStatusText(2));
							negativeFormat();
							txtTranslation.setText("Your answer is: \""+valResult+"\"; But correct is: \""+db_e_word+"\"");
						}
						break;
					case 3:
						if (!txtWord.getText().equalsIgnoreCase(db_e_word)){
							if (valResult.equalsIgnoreCase(db_e_word)) {
								barVal += 1;
								lblScore.setText(Integer.toString(barVal));
								iCorr += 1;
								lblCorr.setText(Integer.toString(iCorr));
								updProdressBar(iTotal);
								lblStatus.setText(getStatusText(1));
								positiveFormat();
							}else {
								barVal -= 2;
								lblScore.setText(Integer.toString(barVal));
								updProdressBar(iTotal);
								lblStatus.setText(getStatusText(2));
								negativeFormat();
								txtTranslation.setText("Your answer is: \""+valResult+"\"; But correct is: \""+db_e_word+"\"");
							}
						}else{
							if (valResult.equalsIgnoreCase(db_u_word1)) {
								barVal += 1;
								lblScore.setText(Integer.toString(barVal));
								iCorr += 1;
								lblCorr.setText(Integer.toString(iCorr));
								updProdressBar(iTotal);
								lblStatus.setText(getStatusText(1));
								positiveFormat();
							} else if (valResult.equalsIgnoreCase(db_u_word2)) {
								barVal += 1;
								lblScore.setText(Integer.toString(barVal));
								iCorr += 1;
								lblCorr.setText(Integer.toString(iCorr));
								updProdressBar(iTotal);
								lblStatus.setText(getStatusText(1));
								positiveFormat();
							} else if (valResult.equalsIgnoreCase(db_u_word3)) {
								barVal += 1;
								lblScore.setText(Integer.toString(barVal));
								iCorr += 1;
								lblCorr.setText(Integer.toString(iCorr));
								updProdressBar(iTotal);
								lblStatus.setText(getStatusText(1));
								positiveFormat();
							} else if (valResult.equalsIgnoreCase(db_u_word4)) {
								barVal += 1;
								lblScore.setText(Integer.toString(barVal));
								iCorr += 1;
								lblCorr.setText(Integer.toString(iCorr));
								updProdressBar(iTotal);
								lblStatus.setText(getStatusText(1));
								positiveFormat();
							} else if (valResult.equalsIgnoreCase(db_u_word5)) {
								barVal += 1;
								lblScore.setText(Integer.toString(barVal));
								iCorr += 1;
								lblCorr.setText(Integer.toString(iCorr));
								updProdressBar(iTotal);
								lblStatus.setText(getStatusText(1));
								positiveFormat();
							} else{
								barVal -= 2;
								lblScore.setText(Integer.toString(barVal));
								updProdressBar(iTotal);
								lblStatus.setText(getStatusText(2));
								negativeFormat();
								txtTranslation.setText("Your answer is: \""+valResult+"\"; But correct is: \""+arrWords.get(arrWords.size()-1)+"\"");
							}
						}
					break;
				}
			}else{
				barVal -= 10;
				lblScore.setText(Integer.toString(barVal));
				updProdressBar(iTotal);
				negativeFormat();
				txtTranslation.setText("Don't press enter before typing anything!");
				lblStatus.setText("You are loser, STOP cheating!!!");
			}
			cheskMyself = false;
		}
	}

	private void statInfo() {
		int iTotal = Integer.parseInt(lblTotal.getText());
		if (iTotal>0) {
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
				pst.setInt(2, 1);
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

	class FinishWords implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			statInfo();
			StopLearnWord();
		}
	}
}
