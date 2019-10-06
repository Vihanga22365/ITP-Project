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



public class Invoicecrud {
    
    Connection con = DBconnect.connect();
    PreparedStatement ps;
    
   
     public void insertUpdateDeleteOrder(char operation ,String InID, String CID , String Tot_qty , String Tot_Amount ,String paidAmount,String sBalance) throws SQLException
                                            
    {
        
        
        
        if(operation == 'i')
        {
            
            try{
                ps = con.prepareStatement("INSERT INTO `sales`(`InID`, `CID`, `Tot_qty`, `Tot_Amount`, `paidAmount`, `sBalance`) VALUES (?,?,?,?,?,?)");
                
                ps.setString(1, InID);
                ps.setString(2, CID);
                ps.setString(3, Tot_qty);
                ps.setString(4, Tot_Amount);
                ps.setString(5, paidAmount);
                ps.setString(6, sBalance);
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Added New Invoice");
                }
                
            }catch(Exception e)
            {
                
            }
            
        }
            
         if(operation == 'u')
            {
            
            try {
                ps = con.prepareStatement("UPDATE `sales` SET `InID`=?,`CID`=?,`Tot_qty`=?,`Tot_Amount`=?,`paidAmount`=?,`sBalance`=? WHERE `InID`=?");
                
                ps.setString(1, InID);
                ps.setString(2, CID);
                ps.setString(3, Tot_qty);
                ps.setString(4, Tot_Amount);
                ps.setString(5, paidAmount);
                ps.setString(6, sBalance);
                ps.setString(7, InID);
                
                
                if(ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null,"Updated Invoice Details");
                }
                
            } catch (Exception e){
                
                JOptionPane.showMessageDialog(null,"Can not update");
                
            }
            
            }
               
                
                
            
            
       
        
        if(operation == 'd')
        {
            
            try {
                ps = con.prepareStatement("DELETE FROM `sales` WHERE `InID` = ?");
                
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

       
        
 
      
     
        
        
        
        
        
 
        
        
     
    



