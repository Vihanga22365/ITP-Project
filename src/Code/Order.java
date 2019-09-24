
package Code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Order {
    public void insertUpdateDeleteOrder(char operation , String oid , String oproduct , String odate ,
                                            String oqty , String oamount)
    {
        Connection con = DBconnect.connect();
        PreparedStatement ps;
        
        if(operation == 'i')
        {
            
            try {
                ps = con.prepareStatement("INSERT INTO orderdetails(oid, oproduct, odate, oqty, oamount) VALUES (?,?,?,?,?)");
                
                ps.setString(1, oid);
                ps.setString(2, oproduct);
                ps.setString(3, odate);
                ps.setString(4, oqty);
                ps.setString(5, oamount);
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Added New Order");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
            
            
        }
        
        if(operation == 'u')
        {
            
            try {
                ps = con.prepareStatement("UPDATE orderdetails SET oid = ? ,oproduct = ? ,odate = ? ,oqty = ? ,oamount = ? WHERE oid = ?");
                
                ps.setString(1, oid);
                ps.setString(2, oproduct);
                ps.setString(3, odate);
                ps.setString(4, oqty);
                ps.setString(5, oamount);
                ps.setString(6, oid);
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Updated Order Details");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
            
            
        }
        
        if(operation == 'd')
        {
            
            try {
                ps = con.prepareStatement("DELETE FROM `orderdetails` WHERE `oid` = ?");
                
                ps.setString(1, oid);
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Order Deleted");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                
            
            
        }
    }
    
    public void fillOrderTable(JTable table , String valueToSearch)
    {
        Connection con = DBconnect.connect();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("SELECT * FROM `orderdetails` WHERE CONCAT (`oid`, `oproduct`, `odate`, `oqty`, `oamount`) LIKE ?");
            ps.setString(1, "%" + valueToSearch + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[5];
                
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                
                
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
