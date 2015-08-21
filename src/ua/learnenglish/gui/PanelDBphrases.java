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
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.DropMode;

public class PanelDBphrases extends JPanel {
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private JTextPane txtPhrase, txtTranslation, txtExample;
	private String [] arrFieldsVal = new String[3];
	private String [] arrTitle = new String[] {"Phrase","Translation", "Example"};
	
    public PanelDBphrases(JPanel cp) 
    {
    	this.contentPane = cp;
        init();
        FillInTable();
    }

    private void init() 
    {	
    	setLayout(null);
    	//setSize(800, 600);
    	
    	JPanel pPhrase = new JPanel();
    	pPhrase.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
    	pPhrase.setBounds(2, 0, 147, 540);
    	add(pPhrase);
    	pPhrase.setLayout(null);
    	
    	JLabel lblPhrase = new JLabel("Phrase");
    	lblPhrase.setHorizontalAlignment(SwingConstants.CENTER);
    	lblPhrase.setForeground(UIManager.getColor("Button.foreground"));
    	lblPhrase.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	lblPhrase.setBounds(5, 12, 138, 15);
    	pPhrase.add(lblPhrase);
    	
    	JButton btnUpdate = new JButton("Update");
    	btnUpdate.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			ChangeWordInTable();
    		}
    	});
    	btnUpdate.setBounds(5, 401, 138, 36);
    	pPhrase.add(btnUpdate);
    	
    	JButton btnCreate = new JButton("Create");
    	btnCreate.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			createNewEntries();
    		}
    	});
    	btnCreate.setBounds(5, 497, 138, 36);
    	pPhrase.add(btnCreate);
    	
    	JButton btnDelete = new JButton("Delete");
    	btnDelete.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			deleteFromTable();
    		}
    	});
    	btnDelete.setBounds(5, 449, 138, 36);
    	pPhrase.add(btnDelete);
    	
    	JScrollPane scrlPhrase = new JScrollPane();
    	scrlPhrase.setBounds(5, 33, 138, 100);
    	pPhrase.add(scrlPhrase);
    	
    	txtPhrase = new JTextPane();
    	scrlPhrase.setViewportView(txtPhrase);
    	txtPhrase.setFont(new Font("Dialog", Font.BOLD, 14));
    	txtPhrase.setForeground(Color.BLUE);
    	
    	JLabel lblTranslation = new JLabel("Translation");
    	lblTranslation.setHorizontalAlignment(SwingConstants.CENTER);
    	lblTranslation.setForeground(UIManager.getColor("Button.foreground"));
    	lblTranslation.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	lblTranslation.setBounds(5, 141, 138, 15);
    	pPhrase.add(lblTranslation);
    	
    	JScrollPane scrlTranslation = new JScrollPane();
    	scrlTranslation.setBounds(6, 163, 135, 97);
    	pPhrase.add(scrlTranslation);
    	
    	txtTranslation = new JTextPane();
    	scrlTranslation.setViewportView(txtTranslation);
    	txtTranslation.setForeground(Color.BLUE);
    	txtTranslation.setFont(new Font("Dialog", Font.BOLD, 14));
    	
    	JLabel lblExample = new JLabel("Example");
    	lblExample.setHorizontalAlignment(SwingConstants.CENTER);
    	lblExample.setForeground(UIManager.getColor("Button.foreground"));
    	lblExample.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
    	lblExample.setBounds(5, 268, 138, 15);
    	pPhrase.add(lblExample);
    	
    	JScrollPane scrlExample = new JScrollPane();
    	scrlExample.setBounds(6, 290, 135, 97);
    	pPhrase.add(scrlExample);
    	
    	txtExample = new JTextPane();
    	scrlExample.setViewportView(txtExample);
    	txtExample.setForeground(Color.BLUE);
    	txtExample.setFont(new Font("Dialog", Font.BOLD, 14));
    	
    	addTable();
    }
    protected void createNewEntries() {
    	getValueFromFields();
    	conn = sqliteConnection.dbConnector();
		String sql = "INSERT INTO `phrase`(`eng_text`,`translation`,`example`)"
				+ " VALUES (?,?,?);";
		try{
			pst = conn.prepareStatement(sql);
			pst.setString(1, arrFieldsVal[0]);
			pst.setString(2, arrFieldsVal[1]);
			pst.setString(3, arrFieldsVal[2]);

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
    	final JTable tPhrase = new JTable() {
    	      @Override
    	      public Class<?> getColumnClass(int column) {
    	        return String.class;
    	      }
    	      @Override
      		  public boolean isCellEditable(int row, int column) {
    			return false;
    	      }  
    	};
    	tPhrase.setModel(model);
    	scrollPane.setViewportView(tPhrase);
    	tPhrase.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				CleanTextFields();
				if (tPhrase.getSelectedRow() == 0){
					txtPhrase.setEditable(true);
					txtPhrase.setBackground(Color.WHITE);
				}else{
					txtPhrase.setEditable(false);
					txtPhrase.setBackground(Color.LIGHT_GRAY);
					txtPhrase.setText(ToStringVal(tPhrase.getValueAt(tPhrase.getSelectedRow(), 0)));
					txtTranslation.setText(ToStringVal(tPhrase.getValueAt(tPhrase.getSelectedRow(), 1)));
					txtExample.setText(ToStringVal(tPhrase.getValueAt(tPhrase.getSelectedRow(), 2)));
				}
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
    	txtPhrase.setText("");
		txtPhrase.setEditable(true);
		txtPhrase.setBackground(Color.WHITE);
		txtTranslation.setText("");
		txtExample.setText("");
    }
    
    private void removeAllFromTable(){
    	remove(scrollPane);
    	addTable();
        revalidate();
        FillInTable();
    }
    
    private void FillInTable(){
        //fill table
    	model.addRow(new Object[]{"","",""});
    	conn = sqliteConnection.dbConnector();
		String sql = "SELECT * FROM `phrase`";
		try{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			while(rs.next())  
			{
				String db_txtPhrase = rs.getString("eng_text");
				String db_txtTranslation = rs.getString("translation");
				String db_txtExample = rs.getString("example");
			    model.addRow(new Object[]{db_txtPhrase,db_txtTranslation,db_txtExample});
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
		String sql = "UPDATE `phrase` SET `translation`=?,`example`=? WHERE `eng_text`=? ";
		try{
			pst = conn.prepareStatement(sql);
			pst.setString(1, arrFieldsVal[1]);
			pst.setString(2, arrFieldsVal[2]);
			pst.setString(3, arrFieldsVal[0]);
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
			arrFieldsVal[0] = txtPhrase.getText().trim();
			arrFieldsVal[1] = txtTranslation.getText().trim();
			arrFieldsVal[2] = txtExample.getText().trim();
	
			return arrFieldsVal;
		}
		private void deleteFromTable(){
	    	getValueFromFields();
	    	conn = sqliteConnection.dbConnector();
			String sql = "DELETE FROM `phrase` WHERE `eng_text`=?";
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
