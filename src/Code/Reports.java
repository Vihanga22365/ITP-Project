/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.awt.Container;
import java.sql.Connection;
import java.util.HashMap;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Vihanga
 */

public class Reports extends JFrame
{
    public Reports(String fileName)
    {
        this(fileName, null);
    }
    public Reports(String fileName, HashMap para)
    {
       
        super("POS Management system");
        
        DBconnect dba = new DBconnect();
        Connection conn = dba.connect();
        

        try
        {
            JasperPrint print = JasperFillManager.fillReport(fileName, para, conn);
            JRViewer viewer = new JRViewer(print);
            Container c = getContentPane();
            c.add(viewer);            
        } 
        catch (JRException jRException)
        {
            
        }
         setBounds(10, 10, 900, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
}
