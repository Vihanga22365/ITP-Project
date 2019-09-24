
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


public class Supplier {
    
    public void insertUpdateDeleteSupplier(char operation , String sid , String sname , String semail ,String smobile )
    {
        Connection con = DBconnect.connect();
        PreparedStatement ps;
        
        if(operation == 'i') //insert
        {
           
            try {
                ps = con.prepareStatement("INSERT INTO supplier(sid, sname, semail, smobile) VALUES (?,?,?,?)");
                ps.setString(1, sid);
                ps.setString(2, sname);
                ps.setString(3, semail);
                ps.setString(4, smobile);
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Added New Supplier");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        }
        
        if(operation == 'u') //update
        {
           
            try {
                ps = con.prepareStatement("UPDATE supplier SET sid = ? , sname = ? ,semail = ? ,smobile= ? WHERE sid = ?");
                ps.setString(1, sid);
                ps.setString(2, sname);
                ps.setString(3, semail);
                ps.setString(4, smobile);
                ps.setString(5, sid);
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Updated Supplier Details");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        }
        
        if(operation == 'd') //update
        {
           
            try {
                ps = con.prepareStatement("DELETE FROM `supplier` WHERE `sid`= ?");
                ps.setString(1, sid);
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Deleted Supplier");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        }
        
        
    }
    
    public void fillSupplierTable(JTable table , String valueToSearch)
    {
        Connection con = DBconnect.connect();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("SELECT * FROM `supplier` WHERE CONCAT (`sid`, `sname`, `semail`, `smobile`) LIKE ?");
            ps.setString(1, "%" + valueToSearch + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            
            Object[] row;
            
            while(rs.next())
            {
                row = new Object[4];
                
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillProductTable(JTable stable, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
