package ua.learnenglish.gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import ua.learnenglish.db.sqliteConnection;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MFScorePane {
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JTabbedPane tabbedPane;
	private JTable tWords, tPhrase;
	private String [] arrTitle = new String[] {"Date","User", "Total Words", "Correct", "Score"};
	private DefaultTableModel modelW, modelP;
	private JLayeredPane paneScore;
	private JScrollPane scrlTableWords, scrlTablePhrase;
	
	public MFScorePane (JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
		init();
	}
	
	private void init(){
		paneScore = new JLayeredPane();
        tabbedPane.addTab("Score", null, paneScore, null);
        //paneScore.setSize(800, 600);
        paneScore.setLayout(null);
        
        tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				paneScore.remove(scrlTableWords);
				paneScore.remove(scrlTablePhrase);
				addTables();				
			}
        });
        
        JLabel lblWords = new JLabel("Table Words");
        lblWords.setBounds(140, 2, 100, 15);
        paneScore.add(lblWords);
        
        JLabel lblPhrases = new JLabel("Table Phrases");
        lblPhrases.setBounds(542, 2, 107, 15);
        paneScore.add(lblPhrases);
        
        JButton btnCleanWords = new JButton("clean table");
        btnCleanWords.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		CleanTable(1);
        	}
        });
        btnCleanWords.setBounds(276, 2, 115, 15);
        paneScore.add(btnCleanWords);
        
        JButton btnCleanPhrases = new JButton("clean table");
        btnCleanPhrases.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		CleanTable(2);
        	}
        });
        btnCleanPhrases.setBounds(671, 2, 115, 15);
        paneScore.add(btnCleanPhrases);
        
        addTables();
	}
	
	private void addTables(){
		modelW = new DefaultTableModel(arrTitle,0);
        scrlTableWords = new JScrollPane();
        scrlTableWords.setBounds(2, 20, 391, 490);
        paneScore.add(scrlTableWords);
        
        tWords = new JTable();
        scrlTableWords.setViewportView(tWords);
        tWords.setModel(modelW);
        
        modelP = new DefaultTableModel(arrTitle,0);
        scrlTablePhrase = new JScrollPane();
        scrlTablePhrase.setBounds(395, 20, 391, 490);
        paneScore.add(scrlTablePhrase);
        
        tPhrase = new JTable();
        scrlTablePhrase.setViewportView(tPhrase);
        tPhrase.setModel(modelP);
        
		FillInTableWords();
		FillInTablePhrases();
	}
	
    protected void CleanTable(int i) {
    	conn = sqliteConnection.dbConnector();
    	String sql = "DELETE FROM `score` WHERE `game`=?;";
		try{
			pst = conn.prepareStatement(sql);
			pst.setInt(1, i);
			pst.execute();
				
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				rs.close();
				pst.close();
				paneScore.remove(scrlTableWords);
				paneScore.remove(scrlTablePhrase);
				addTables();
				conn.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
	}

	private void FillInTableWords(){
        //fill table
		conn = sqliteConnection.dbConnector();
		String sql = "SELECT * FROM `score` WHERE `game`=1 ORDER BY `words_correct`,`words_all`,`score` DESC";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while(rs.next())  
			{
				String db_date = rs.getString("date");
				String db_user = rs.getString("user");
				String db_words_all = rs.getString("words_all");
				String db_words_correct = rs.getString("words_correct");
				String db_score = rs.getString("score");
				modelW.addRow(new Object[]{db_date,db_user,db_words_all,db_words_correct,db_score});
			}
			
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
    private void FillInTablePhrases(){
        //fill table
    	conn = sqliteConnection.dbConnector();
		String sql = "SELECT * FROM `score` WHERE `game`=2 ORDER BY `words_correct`,`words_all`,`score` DESC";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while(rs.next())  
			{
				String db_date = rs.getString("date");
				String db_user = rs.getString("user");
				String db_words_all = rs.getString("words_all");
				String db_words_correct = rs.getString("words_correct");
				String db_score = rs.getString("score");
				modelP.addRow(new Object[]{db_date,db_user,db_words_all,db_words_correct,db_score});
			}
			
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
}
