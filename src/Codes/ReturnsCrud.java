/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Codes;

import Code.DBconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ReturnsCrud {
    
   Connection con=DBconnect.connect();
   PreparedStatement ps;
   
   public void insertUpdateDeleteOrder(char operation ,String InID, String PrID , String PrName , String CusID ,String CusName, String Reason,String PDate,String RDate,String Action) throws SQLException{
       
        if(operation == 'i')
        {
            
            try{
                ps = con.prepareStatement("INSERT INTO `returns`(`InID`,`PrID`, `PrName`, `CusID`, `CusName`, `Reason`, `PDate`, `RDate`, `Action`) VALUES (?,?,?,?,?,?,?,?,?)");
                
                ps.setString(1, InID);
                ps.setString(2, PrID);
                ps.setString(3, PrName);
                ps.setString(4, CusID);
                ps.setString(5, CusName);
                ps.setString(6, Reason);
                ps.setString(7, PDate);
                ps.setString(8, RDate);
                ps.setString(9, Action);
                
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Added New Return Invoice");
                }
                
            }catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,"could not addd");
            }
        }
        
         if(operation == 'u')
            {
            
            try {
                ps = con.prepareStatement("UPDATE `returns` SET `InID`=?,`PrID`=?,`PrName`=?,`CusID`=?,`CusName`=?,`Reason`=?,`PDate`=?,`RDate`=?,`Action`=? WHERE `InID`=?");
                
                ps.setString(1, InID);
                ps.setString(2, PrID);
                ps.setString(3, PrName);
                ps.setString(4, CusID);
                ps.setString(5, CusName);
                ps.setString(6, Reason);
                ps.setString(7, PDate);
                ps.setString(8, RDate);
                ps.setString(9, Action);
                ps.setString(10, InID);
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Updated Return Details");
                }
                
            } catch (Exception e){
                
                JOptionPane.showMessageDialog(null,"Can not update");
                
            }
            
            }
         
         
         if(operation == 'd')
        {
            
            try {
                ps = con.prepareStatement("DELETE FROM `returns` WHERE `InID` = ?");
                
                ps.setString(1, InID);
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Invoice Deleted");
                }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Can not delete");
            }
                
                
            
            
        }
    
   }
}
