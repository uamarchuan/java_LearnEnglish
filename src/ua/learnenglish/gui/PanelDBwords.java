package ua.learnenglish.gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ua.learnenglish.db.sqliteConnection;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelDBwords extends JPanel {
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JPanel contentPane;
	private JTextField txtID;
	private JTextField txtEword;
	private JTextField txtTranscription;
	private JTextField txtUword1;
	private JTextField txtUword2;
	private JTextField txtUword3;
	private JTextField txtUword4;
	private JTextField txtUword5;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private String [] arrFieldsVal = new String[8];
	private String [] arrTitle = new String[] {"ID","English word", "Transcription", "Translation 1", "Translation 2", "Translation 3", "Translation 4", "Translation 5"};
	
    public PanelDBwords(JPanel cp) 
    {
    	this.contentPane = cp;
        init();
        FillInTable();
    }

    private void init() 
    {	
    	setLayout(null);
    	//setSize(800, 600);
    	
    	JPanel pWords = new JPanel();
    	pWords.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
    	pWords.setBounds(2, 0, 147, 540);
    	add(pWords);
    	pWords.setLayout(null);
    	
    	JLabel lblID = new JLabel("ID");
    	lblID.setBounds(5, 12, 70, 15);
    	pWords.add(lblID);
    	lblID.setForeground(UIManager.getColor("RadioButton.foreground"));
    	lblID.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	
    	txtID = new JTextField();
    	txtID.setEditable(false);
    	txtID.setBounds(5, 29, 70, 25);
    	pWords.add(txtID);
    	txtID.setForeground(Color.BLUE);
    	txtID.setFont(new Font("Dialog", Font.BOLD, 14));
    	txtID.setColumns(10);
    	
    	txtEword = new JTextField();
    	txtEword.setBounds(5, 94, 138, 25);
    	pWords.add(txtEword);
    	txtEword.setForeground(Color.BLUE);
    	txtEword.setFont(new Font("Dialog", Font.BOLD, 14));
    	txtEword.setColumns(10);
    	
    	JLabel lblEword = new JLabel("En Word");
    	lblEword.setBounds(5, 76, 70, 15);
    	pWords.add(lblEword);
    	lblEword.setForeground(UIManager.getColor("Button.foreground"));
    	lblEword.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	
    	JLabel lblTranscription = new JLabel("Transcription");
    	lblTranscription.setForeground(UIManager.getColor("Button.foreground"));
    	lblTranscription.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	lblTranscription.setBounds(5, 121, 138, 15);
    	pWords.add(lblTranscription);
    	
    	txtTranscription = new JTextField();
    	txtTranscription.setForeground(Color.BLUE);
    	txtTranscription.setFont(new Font("Dialog", Font.BOLD, 14));
    	txtTranscription.setColumns(10);
    	txtTranscription.setBounds(5, 138, 138, 25);
    	pWords.add(txtTranscription);
    	
    	JLabel lblUword1 = new JLabel("UA Word 1");
    	lblUword1.setForeground(UIManager.getColor("Button.foreground"));
    	lblUword1.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	lblUword1.setBounds(5, 164, 138, 15);
    	pWords.add(lblUword1);
    	
    	txtUword1 = new JTextField();
    	txtUword1.setForeground(Color.BLUE);
    	txtUword1.setFont(new Font("Dialog", Font.BOLD, 14));
    	txtUword1.setColumns(10);
    	txtUword1.setBounds(5, 181, 138, 25);
    	pWords.add(txtUword1);
    	
    	JLabel lblUword2 = new JLabel("UA Word 2");
    	lblUword2.setForeground(UIManager.getColor("Button.foreground"));
    	lblUword2.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	lblUword2.setBounds(5, 207, 138, 15);
    	pWords.add(lblUword2);
    	
    	txtUword2 = new JTextField();
    	txtUword2.setForeground(Color.BLUE);
    	txtUword2.setFont(new Font("Dialog", Font.BOLD, 14));
    	txtUword2.setColumns(10);
    	txtUword2.setBounds(5, 222, 138, 25);
    	pWords.add(txtUword2);
    	
    	JLabel lblUword3 = new JLabel("UA Word 3");
    	lblUword3.setForeground(UIManager.getColor("Button.foreground"));
    	lblUword3.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	lblUword3.setBounds(5, 248, 138, 15);
    	pWords.add(lblUword3);
    	
    	txtUword3 = new JTextField();
    	txtUword3.setForeground(Color.BLUE);
    	txtUword3.setFont(new Font("Dialog", Font.BOLD, 14));
    	txtUword3.setColumns(10);
    	txtUword3.setBounds(5, 265, 138, 25);
    	pWords.add(txtUword3);
    	
    	JLabel lblUword4 = new JLabel("UA Word 4");
    	lblUword4.setForeground(UIManager.getColor("Button.foreground"));
    	lblUword4.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	lblUword4.setBounds(5, 290, 138, 15);
    	pWords.add(lblUword4);
    	
    	txtUword4 = new JTextField();
    	txtUword4.setForeground(Color.BLUE);
    	txtUword4.setFont(new Font("Dialog", Font.BOLD, 14));
    	txtUword4.setColumns(10);
    	txtUword4.setBounds(5, 307, 138, 25);
    	pWords.add(txtUword4);
    	
    	JLabel lblUword5 = new JLabel("UA Word 5");
    	lblUword5.setForeground(UIManager.getColor("Button.foreground"));
    	lblUword5.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	lblUword5.setBounds(5, 333, 138, 15);
    	pWords.add(lblUword5);
    	
    	txtUword5 = new JTextField();
    	txtUword5.setForeground(Color.BLUE);
    	txtUword5.setFont(new Font("Dialog", Font.BOLD, 14));
    	txtUword5.setColumns(10);
    	txtUword5.setBounds(5, 350, 138, 25);
    	pWords.add(txtUword5);
    	
    	JButton btnUpdate = new JButton("Update");
    	btnUpdate.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			ChangeWordInTable();
    		}
    	});
    	btnUpdate.setBounds(5, 401, 138, 36);
    	pWords.add(btnUpdate);
    	
    	JButton btnCreate = new JButton("Create");
    	btnCreate.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			createNewEntries();
    		}
    	});
    	btnCreate.setBounds(5, 497, 138, 36);
    	pWords.add(btnCreate);
    	
    	JButton btnDelete = new JButton("Delete");
    	btnDelete.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			deleteFromTable();
    		}
    	});
    	btnDelete.setBounds(5, 449, 138, 36);
    	pWords.add(btnDelete);
    	
    	addTable();
    }
    protected void createNewEntries() {
    	getValueFromFields();
    	conn = sqliteConnection.dbConnector();
		String sql = "INSERT INTO `words`(`e_word`,`transcription`,`u_word1`,`u_word2`,`u_word3`,`u_word4`,`u_word5`)"
				+ " VALUES (?,?,?,?,?,?,?);";
		try{
			pst = conn.prepareStatement(sql);
			pst.setString(1, arrFieldsVal[1]);
			pst.setString(2, arrFieldsVal[2]);
			pst.setString(3, arrFieldsVal[3]);
			pst.setString(4, arrFieldsVal[4]);
			pst.setString(5, arrFieldsVal[5]);
			pst.setString(6, arrFieldsVal[6]);
			pst.setString(7, arrFieldsVal[7]);
			pst.execute();
				
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
				//initiate Updating
				CleanTextFields();
				removeAllFromTable();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
	}

	private void addTable(){
    	model = new DefaultTableModel(arrTitle,0);
    	scrollPane = new JScrollPane();
    	scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	scrollPane.setBounds(155, 0, 635, 540);
    	add(scrollPane);
    	final JTable tWords = new JTable() {
    	      @Override
    	      public Class<?> getColumnClass(int column) {
    	        return String.class;
    	      }
    	      @Override
      		  public boolean isCellEditable(int row, int column) {
    			return false;
    	      }  
    	};
    	tWords.setModel(model);
    	scrollPane.setViewportView(tWords);
    	tWords.getColumnModel().getColumn(0).setMinWidth(0);
    	tWords.getColumnModel().getColumn(0).setMaxWidth(0);
    	tWords.getColumnModel().getColumn(0).setWidth(0);
    	tWords.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				CleanTextFields();
				txtID.setText(ToStringVal(tWords.getValueAt(tWords.getSelectedRow(), 0)));
				txtEword.setText(ToStringVal(tWords.getValueAt(tWords.getSelectedRow(), 1)));
				txtTranscription.setText(ToStringVal(tWords.getValueAt(tWords.getSelectedRow(), 2)));
				txtUword1.setText(ToStringVal(tWords.getValueAt(tWords.getSelectedRow(), 3)));
				txtUword2.setText(ToStringVal(tWords.getValueAt(tWords.getSelectedRow(), 4)));
				txtUword3.setText(ToStringVal(tWords.getValueAt(tWords.getSelectedRow(), 5)));
				txtUword4.setText(ToStringVal(tWords.getValueAt(tWords.getSelectedRow(), 6)));
				txtUword5.setText(ToStringVal(tWords.getValueAt(tWords.getSelectedRow(), 7)));
			}
        });
    }
    
    private String ToStringVal(Object s){
    	if (!(s == null)){
    		s.toString();
    	}else{
    		s="";
    	}
		return (String) s;
    }
    private void CleanTextFields(){
		txtID.setText("");
		txtEword.setText("");
		txtTranscription.setText("");
		txtUword1.setText("");
		txtUword2.setText("");
		txtUword3.setText("");
		txtUword4.setText("");
		txtUword5.setText("");
    }
    
    private void removeAllFromTable(){
    	remove(scrollPane);
    	addTable();
        revalidate();
        FillInTable();
    }
    
    private void FillInTable(){
        //fill table
    	model.addRow(new Object[]{"","","","","","","",""});
    	conn = sqliteConnection.dbConnector();
		String sql = "SELECT * FROM words";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while(rs.next())  
			{
				String db_id = rs.getString("id");
				String db_e_word = rs.getString("e_word");
				String db_transcription = rs.getString("transcription");
				String db_u_word1 = rs.getString("u_word1");
				String db_u_word2 = rs.getString("u_word2");
				String db_u_word3 = rs.getString("u_word3");
				String db_u_word4 = rs.getString("u_word4");
				String db_u_word5 = rs.getString("u_word5"); 
			    model.addRow(new Object[]{db_id,db_e_word,db_transcription,db_u_word1,db_u_word2,db_u_word3,db_u_word4,db_u_word5});
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
    
    private void ChangeWordInTable(){
    	getValueFromFields();
    	conn = sqliteConnection.dbConnector();
		String sql = "UPDATE `words` SET `e_word`=?,`transcription`=?,`u_word1`=?,"
				+ "`u_word2`=?,`u_word3`=?,`u_word4`=?,`u_word5`=? WHERE id=?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setString(1, arrFieldsVal[1]);
			pst.setString(2, arrFieldsVal[2]);
			pst.setString(3, arrFieldsVal[3]);
			pst.setString(4, arrFieldsVal[4]);
			pst.setString(5, arrFieldsVal[5]);
			pst.setString(6, arrFieldsVal[6]);
			pst.setString(7, arrFieldsVal[7]);
			pst.setString(8, arrFieldsVal[0]);
			pst.execute();
				
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				rs.close();
				pst.close();
				conn.close();
				//initiate Updating
				CleanTextFields();
				removeAllFromTable();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
    }
   
		private String[] getValueFromFields(){
			for (int i=0;i>arrFieldsVal.length;i++) {
				arrFieldsVal[i] = "";
			}
			arrFieldsVal[0] = txtID.getText().trim();
			arrFieldsVal[1] = txtEword.getText().trim();
			arrFieldsVal[2] = txtTranscription.getText().trim();
			arrFieldsVal[3] = txtUword1.getText().trim();
			arrFieldsVal[4] = txtUword2.getText().trim();
			arrFieldsVal[5] = txtUword3.getText().trim();
			arrFieldsVal[6] = txtUword4.getText().trim();
			arrFieldsVal[7] = txtUword5.getText().trim();
	
			return arrFieldsVal;
		}
		private void deleteFromTable(){
	    	getValueFromFields();
	    	conn = sqliteConnection.dbConnector();
			String sql = "DELETE FROM `words` WHERE id=?;";
			try{
				pst = conn.prepareStatement(sql);
				pst.setString(1, arrFieldsVal[0]);
				pst.execute();
					
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			} finally {
				try {
					rs.close();
					pst.close();
					conn.close();
					//initiate Updating
					CleanTextFields();
					removeAllFromTable();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		}
}
