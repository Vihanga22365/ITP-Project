
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


public class Product {
    
     public void insertUpdateDeleteProduct(char operation , String pid , String pname , String pmanudate ,
                                            String pexpdate , String pprice , String pqty , String psupname)
    {
        Connection con = DBconnect.connect();
        PreparedStatement ps;
        
        if(operation == 'i')
        {
            try {
                ps = con.prepareStatement("INSERT INTO product(pid, pname, pmanudate, pexdate, pprice, pqty, psupname) VALUES (?,?,?,?,?,?,?)");
                ps.setString(1, pid);
                ps.setString(2, pname);
                ps.setString(3, pmanudate);
                ps.setString(4, pexpdate);
                ps.setString(5, pprice);
                ps.setString(6, pqty);
                ps.setString(7, psupname);
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Added New Product");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        if(operation == 'u')
        {
            try {
                ps = con.prepareStatement("UPDATE product SET pid = ? ,pname =  ? ,pmanudate = ? ,pexdate = ? ,pprice = ? ,pqty = ? ,psupname= ?  WHERE pid = ?");
                ps.setString(1, pid);
                ps.setString(2, pname);
                ps.setString(3, pmanudate);
                ps.setString(4, pexpdate);
                ps.setString(5, pprice);
                ps.setString(6, pqty);
                ps.setString(7, psupname);
                ps.setString(8, pid);
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Product Details Updated");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        if(operation == 'd')
        {
            try {
                ps = con.prepareStatement("DELETE FROM `product` WHERE `pid` = ?");
                ps.setString(1, pid);
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Product Deleted");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void fillProductTable(JTable table , String valueToSearch)
    {
        Connection con = DBconnect.connect();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("SELECT * FROM `product` WHERE CONCAT (`pid`, `pname`, `pmanudate`, `pexdate`, `pprice`, `pqty`, `psupname`) LIKE ?");
            ps.setString(1, "%" + valueToSearch + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[7];
                
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
