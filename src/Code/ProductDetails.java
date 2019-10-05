/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vihanga
 */
public class ProductDetails {
    public void fillProductTable(JTable table , String valueToSearch)
    {
        Connection con = DBconnect.connect();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("SELECT * FROM `productdetails` WHERE CONCAT (`pid`, `pname`, `pprice`, `pqty`, `psupplier`) LIKE ?");
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
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
