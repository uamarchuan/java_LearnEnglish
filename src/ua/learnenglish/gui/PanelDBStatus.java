package ua.learnenglish.gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class PanelDBStatus extends JPanel {
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private String [] arrFieldsVal = new String[2];
	private String [] arrTitle = new String[] {"Status","Statement"};
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel contentPane;
	private JTextPane txtStatement;
	private JRadioButton rdbtnPositive, rdbtnNegative;
    
	public PanelDBStatus(JPanel cp) 
	{
	this.contentPane = cp;
    init();
    FillInTable();
}

private void init() 
{	
	setLayout(null);
	//setSize(800, 600);
	
	JPanel pStatus = new JPanel();
	pStatus.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
	pStatus.setBounds(2, 0, 147, 540);
	add(pStatus);
	pStatus.setLayout(null);
	
	JButton btnCreate = new JButton("Create");
	btnCreate.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			createNewEntries();
		}
	});
	btnCreate.setBounds(5, 497, 138, 36);
	pStatus.add(btnCreate);
	
	JButton btnDelete = new JButton("Delete");
	btnDelete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			deleteFromTable();
		}
	});
	btnDelete.setBounds(5, 449, 138, 36);
	pStatus.add(btnDelete);
		
	JLabel lblStatement = new JLabel("Statement");
	lblStatement.setHorizontalAlignment(SwingConstants.CENTER);
	lblStatement.setForeground(UIManager.getColor("Button.foreground"));
	lblStatement.setFont(new Font("Courier 10 Pitch", Font.BOLD | Font.ITALIC, 16));
	lblStatement.setBounds(5, 141, 138, 15);
	pStatus.add(lblStatement);
	
	JScrollPane scrlStatement = new JScrollPane();
	scrlStatement.setBounds(6, 163, 135, 97);
	pStatus.add(scrlStatement);
	
	txtStatement = new JTextPane();
	scrlStatement.setViewportView(txtStatement);
	txtStatement.setFont(new Font("Dialog", Font.BOLD, 14));
	txtStatement.setForeground(Color.BLUE);
	
	rdbtnPositive = new JRadioButton("Positive :)");
	buttonGroup.add(rdbtnPositive);
	rdbtnPositive.setBounds(5, 58, 134, 23);
	pStatus.add(rdbtnPositive);
	
	rdbtnNegative = new JRadioButton("Negative :(");
	buttonGroup.add(rdbtnNegative);
	rdbtnNegative.setBounds(5, 85, 134, 23);
	pStatus.add(rdbtnNegative);
	
	addTable();
}
protected void createNewEntries() {
	getValueFromFields();
	conn = sqliteConnection.dbConnector();
	String sql = "INSERT INTO `status`(`reason`,`en_text`)"
			+ " VALUES (?,?);";
	try{
		pst = conn.prepareStatement(sql);
		pst.setString(1, arrFieldsVal[0]);
		pst.setString(2, arrFieldsVal[1]);

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
	final JTable tStatus = new JTable() {
	      @Override
	      public Class<?> getColumnClass(int column) {
	        return String.class;
	      }
	      @Override
  		  public boolean isCellEditable(int row, int column) {
			return false;
	      }  
	};
	tStatus.setModel(model);
	scrollPane.setViewportView(tStatus);
	tStatus.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		@Override
		public void valueChanged(ListSelectionEvent e) {
			CleanTextFields();
			if (tStatus.getSelectedRow() == 0){
				txtStatement.setEditable(true);
				txtStatement.setBackground(Color.WHITE);
				rdbtnPositive.setEnabled(true);
				rdbtnNegative.setEnabled(true);
			}else{
				txtStatement.setEditable(false);
				txtStatement.setBackground(Color.LIGHT_GRAY);
				rdbtnPositive.setEnabled(false);
				rdbtnNegative.setEnabled(false);
				switch (tStatus.getValueAt(tStatus.getSelectedRow(), 0).toString()) {
				case "Positive":
					rdbtnPositive.setSelected(true);
					break;
				case "Negative":
					rdbtnNegative.setSelected(true);
					break;
				}
				txtStatement.setText(tStatus.getValueAt(tStatus.getSelectedRow(), 1).toString().trim());
			}
		}
    });
}

private void CleanTextFields(){
	rdbtnPositive.setSelected(true);
	txtStatement.setText("");
	txtStatement.setEditable(true);
	txtStatement.setBackground(Color.WHITE);
}

private void removeAllFromTable(){
	remove(scrollPane);
	addTable();
    revalidate();
    FillInTable();
}

private void FillInTable(){
    //fill table
	model.addRow(new Object[]{"",""});
	conn = sqliteConnection.dbConnector();
	String sql = "SELECT * FROM `status`;";
	String db_reason;
	try{
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();

		while(rs.next())  
		{
			if (rs.getInt("reason") == 1) {
				db_reason = "Positive";
			}else{
				db_reason = "Negative";
			}
			
			String db_en_text = rs.getString("en_text");
		    model.addRow(new Object[]{db_reason,db_en_text});
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

	private String[] getValueFromFields(){
		for (int i=0;i>arrFieldsVal.length;i++) {
			arrFieldsVal[i] = "";
		}
		if (rdbtnPositive.isSelected()){
			arrFieldsVal[0] = "1";
		}else{
			arrFieldsVal[0] = "2";
		}
		arrFieldsVal[1] = txtStatement.getText().trim();

		return arrFieldsVal;
	}
	private void deleteFromTable(){
    	getValueFromFields();
    	conn = sqliteConnection.dbConnector();
		String sql = "DELETE FROM `status` WHERE `en_text`=?";
		try{
			pst = conn.prepareStatement(sql);
			pst.setString(1, arrFieldsVal[1]);
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