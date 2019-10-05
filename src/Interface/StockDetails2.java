/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Code.DBconnect;
import Code.Reports;
import static Interface.AddProduct.ptable;
import static Interface.SupDetails.stable;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Vihanga
 */
public class StockDetails2 extends javax.swing.JFrame {

    /**
     * Creates new form StockDetails2
     */
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public StockDetails2() {
        initComponents();
        
        //getConnection();
        con = DBconnect.connect();
        
        //tableload
        tableLoad();
        theader();
        
         //Autocomplete supplier ComboBox
        AutoCompleteDecorator.decorate(SupplierNameComboBox);
        FillComboSupplierBox();
        
        //Autocomplete pid ComboBox
        AutoCompleteDecorator.decorate(proIDComboBox);
        FillComboProIDBox();
        
        //Autocomplete pid ComboBox
        AutoCompleteDecorator.decorate(proNameComboBox);
        FillComboProNameBox();
        
    }
    
    private void theader(){
        JTableHeader thead = ptable.getTableHeader();
        thead.setForeground(Color.BLACK);
        thead.setFont(new Font("Tahome",Font.BOLD, 14));
    }
    
     private void FillComboSupplierBox(){
            try{
                    String sql = "SELECT * from supplier"; 
                    pst = con.prepareStatement(sql);
                    rs=pst.executeQuery();

                    while(rs.next()){

                    String suppliername = rs.getString("sname");
                    SupplierNameComboBox.addItem(suppliername);
               }
            }
            catch(Exception e){
                        JOptionPane.showConfirmDialog(null,e);
                }
    }
     
      private void FillComboProIDBox() {
              try{
                    String sql = "SELECT * from productdetails"; 
                    pst = con.prepareStatement(sql);
                    rs=pst.executeQuery();

                    while(rs.next()){

                    String productid = rs.getString("pid");
                    proIDComboBox.addItem(productid);
               }
            }
            catch(Exception e){
                     JOptionPane.showConfirmDialog(null,e);
                }
    }
      
      private void FillComboProNameBox() {
              try{
                    String sql = "SELECT * from productdetails"; 
                    pst = con.prepareStatement(sql);
                    rs=pst.executeQuery();

                    while(rs.next()){

                    String productname = rs.getString("pname");
                    proNameComboBox.addItem(productname);
               }
            }
            catch(Exception e){
                     JOptionPane.showConfirmDialog(null,e);
                }
    }
    
    public void tableLoad() {
        String q = "SELECT sid,pid, pname, pmanudate, pexdate, pprice, pqty, psupname FROM product";
         try {
            pst = con.prepareStatement(q);
            rs = pst.executeQuery();
            
            ptable.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(StockDetails2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    public void createStock(){
    String sid = sidbox.getText();
   // String pid = pidbox.getText();
    String pid = proIDComboBox.getSelectedItem().toString();
    //String pname = pnamebox.getText();
    String pname = proNameComboBox.getSelectedItem().toString();
    String pprice = ppricebox.getText();
    String pqty = pqtybox.getText();
    String supName = SupplierNameComboBox.getSelectedItem().toString();
    String pmanudate = dateFormat.format(pmanudatebox.getDate());
    String pexpdate = dateFormat.format(pexpdatebox.getDate());
     
     String q = "INSERT INTO product (sid,pid, pname, pmanudate, pexdate, pprice, pqty, psupname) VALUES ('"+sid+"', '"+pid+"', '"+pname+"','"+pmanudate+"', '"+pexpdate+"', '"+pprice+"', '"+pqty+"', '"+supName+"')";
        try {
            pst = con.prepareStatement(q);
            pst.execute();
            
            //UserDetailTable Load
            tableLoad();
            
             //
            updateProductdetailTable();
            
            JOptionPane.showMessageDialog(null, 
                              "Stock create successfully", 
                              "Create New Stock", 
                              JOptionPane.PLAIN_MESSAGE);
            
        } catch (SQLException ex) {
            Logger.getLogger(StockDetails2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, 
                              "Error! Stock creation unsuccessful", 
                              "Create New Stock", 
                              JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void updateStock(){
         int x = JOptionPane.showConfirmDialog(null, "Do You really want to Update?");
        if(x==0){
            String sid = sidbox.getText();
           // String pid = pidbox.getText();
           String pid = proIDComboBox.getSelectedItem().toString();
            //String pname = pnamebox.getText();
            String pname = proNameComboBox.getSelectedItem().toString();
            String pprice = ppricebox.getText();
            String pqty = pqtybox.getText();
            String supName = SupplierNameComboBox.getSelectedItem().toString();
            String pmanudate = dateFormat.format(pmanudatebox.getDate());
            String pexpdate = dateFormat.format(pexpdatebox.getDate());
            
            int r = ptable.getSelectedRow();
            String tableSid= ptable.getValueAt(r, 0).toString();
                
            String q = "UPDATE product SET  sid='"+sid+"', pid='"+pid+"', pname='"+pname+"', pmanudate='"+pmanudate+"', pexdate='"+pexpdate+"', pprice='"+pprice+"', pqty='"+pqty+"', psupname='"+supName+"'  WHERE sid='"+tableSid+"'";
             try {
            pst = con.prepareStatement(q);
            pst.execute();
            
           
            //get selected table row Qty
            String rowQty= ptable.getValueAt(r, 6).toString();
            System.out.println("rowQty"+rowQty);
            
            String q2 = "SELECT * FROM productdetails WHERE pid='"+pid+"' ";
            pst = con.prepareStatement(q2);
            rs = pst.executeQuery();
                        while(rs.next()){

                            String qtyInDB = rs.getString("pqty");
                            System.out.println("qtyInDB"+qtyInDB);
                            
                            int qtyInDBint = Integer.parseInt(qtyInDB);
                            int qtyInRowint = Integer.parseInt(rowQty);
                            int newQtyint = Integer.parseInt(pqty);
                              System.out.println("newQtyint"+newQtyint);
                            
                            int updatedQty = (qtyInDBint - qtyInRowint) + newQtyint;
                            String updatedQtyStr = String.valueOf(updatedQty);
                            System.out.println("updatedQty"+updatedQtyStr);
                            
                        String q3 = "UPDATE productdetails SET pqty='"+updatedQtyStr+"' WHERE pid='"+pid+"' ";
                        pst = con.prepareStatement(q3);
                        pst.execute();
                        
                         //UserDetailTable Load
                         tableLoad();
            
                        clearTextBoxes();
                        
                       }
            
            JOptionPane.showMessageDialog(null, 
                              "Stock update successfully", 
                              "Stock update", 
                              JOptionPane.PLAIN_MESSAGE);
            
        } catch (SQLException ex) {
            Logger.getLogger(StockDetails2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, 
                              "Error! Stock update unsuccessful", 
                              "Update Stock", 
                              JOptionPane.WARNING_MESSAGE);
        }
        } else if(x==1) {
            
        } else{
            
        }
    }
    
    public void deleteUser(){
        int x = JOptionPane.showConfirmDialog(null, "Do You really want to Delete?");
        if(x==0){
           // String pid = pidbox.getText();
           String pid = proIDComboBox.getSelectedItem().toString();
            int r = ptable.getSelectedRow();
            String tableSid= ptable.getValueAt(r, 0).toString();
              
            String q = "DELETE FROM product WHERE sid='"+tableSid+"' ";
            try {
            pst = con.prepareStatement(q);
            pst.execute();
            
            DeleteProductdetailTable();
         
            
            JOptionPane.showMessageDialog(null, 
                              "Stock delete successfully", 
                              "Delete Stock", 
                              JOptionPane.PLAIN_MESSAGE);
            
        } catch (SQLException ex) {
            Logger.getLogger(StockDetails2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, 
                              "Error! Stock delete unsuccessful", 
                              "Delete Stock", 
                              JOptionPane.WARNING_MESSAGE);
        }
        } else if(x==1) {
            
        } else{
            
        }
    } 
    
    public void updateProductdetailTable(){
         //String pid1 = pidbox.getText();
         String pid1 = proIDComboBox.getSelectedItem().toString();
         String pqty = pqtybox.getText();
         String q = "SELECT * FROM productdetails WHERE pid='"+pid1+"' ";
         try {
                       
                        pst = con.prepareStatement(q);
                        rs = pst.executeQuery();
                        while(rs.next()){

                            String qtyInTable = rs.getString("pqty");
                            System.out.println("qtyInTable"+qtyInTable);
                            
                            int qtyInTableint = Integer.parseInt(qtyInTable);
                            int addingQtyint = Integer.parseInt(pqty);
                            int updatedQty = qtyInTableint + addingQtyint;
                            String updatedQtyStr = String.valueOf(updatedQty);
                            System.out.println("updatedQty"+updatedQtyStr);
                            
                        String q2 = "UPDATE productdetails SET pqty='"+updatedQtyStr+"' WHERE pid='"+pid1+"' ";
                        pst = con.prepareStatement(q2);
                        pst.execute();
                        
                        clearTextBoxes();
                        
                       }
                    } catch (SQLException e) {
                    System.out.println("error");
                    }
    }
    
     public void DeleteProductdetailTable(){
         //String pid1 = pidbox.getText();
         String pid1 = proIDComboBox.getSelectedItem().toString();
         String pqty = pqtybox.getText();
         String q = "SELECT * FROM productdetails WHERE pid='"+pid1+"' ";
         try {
                       
                        pst = con.prepareStatement(q);
                        rs = pst.executeQuery();
                        while(rs.next()){

                            String qtyInTable = rs.getString("pqty");
                            int qtyInTableint = Integer.parseInt(qtyInTable);
                            System.out.println("qtyInTable"+qtyInTable);
                            
                            int qtyInTextboxint = Integer.parseInt(pqty);
                            System.out.println("qtyInTextbox"+pqty);
                           
                            int updatedQty = qtyInTableint - qtyInTextboxint;
                            String updatedQtyStr = String.valueOf(updatedQty);
                            System.out.println("updatedQty"+updatedQtyStr);
                            
                        String q2 = "UPDATE productdetails SET pqty='"+updatedQtyStr+"' WHERE pid='"+pid1+"' ";
                        pst = con.prepareStatement(q2);
                        pst.execute();
                        
                           
                        //UserDetailTable Load
                        tableLoad();
            
                        clearTextBoxes();
                        
                       }
                    } catch (SQLException e) {
                    System.out.println("error");
                    }
    }
    
    
     public void clearTextBoxes() {
         sidbox.setText("");
        // pidbox.setText("");
          proIDComboBox.setSelectedItem("Choose Product Id");
         //pnamebox.setText("");
          proNameComboBox.setSelectedItem("Choose Product Name");
         ppricebox.setText("");
         pqtybox.setText("");
         pmanudatebox.setDate(null);
         pexpdatebox.setDate(null);
         SupplierNameComboBox.setSelectedItem("Choose Supplier");
    }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        oaddbutton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ptable = new javax.swing.JTable();
        pqtybox = new javax.swing.JTextField();
        ppricebox = new javax.swing.JTextField();
        pexpdatebox = new com.toedter.calendar.JDateChooser();
        pmanudatebox = new com.toedter.calendar.JDateChooser();
        pupdate = new javax.swing.JButton();
        pdelete = new javax.swing.JButton();
        searchbox = new javax.swing.JTextField();
        psearchbox = new javax.swing.JLabel();
        SupplierNameComboBox = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        sidbox = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        pidtext = new javax.swing.JTextField();
        proIDComboBox = new javax.swing.JComboBox<>();
        proNameComboBox = new javax.swing.JComboBox<>();
        searchbtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(214, 170, 36));
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 700));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel2.setText("Stock ID :");
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel3.setText("Manufacture Date :");
        jLabel3.setToolTipText("");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel4.setText("Expiry Date :");
        jLabel4.setToolTipText("");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel5.setText("Product Name :");
        jLabel5.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel6.setText("Price :");
        jLabel6.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel7.setText("Quantity :");
        jLabel7.setToolTipText("");

        oaddbutton.setBackground(new java.awt.Color(8, 88, 8));
        oaddbutton.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        oaddbutton.setForeground(new java.awt.Color(255, 255, 255));
        oaddbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add.png"))); // NOI18N
        oaddbutton.setText("Insert");
        oaddbutton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        oaddbutton.setBorderPainted(false);
        oaddbutton.setIconTextGap(10);
        oaddbutton.setPreferredSize(new java.awt.Dimension(150, 35));
        oaddbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oaddbuttonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel9.setText("Supplier Name :");
        jLabel9.setToolTipText("");

        ptable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        ptable.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        ptable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock ID", "Product ID", "Product", "Manu. Date", "Expiry Date", "Price", "Quantity", "Supplier"
            }
        ));
        ptable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ptable.setRowHeight(25);
        ptable.setRowMargin(3);
        ptable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ptableMouseClicked(evt);
            }
        });
        ptable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ptableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(ptable);

        pqtybox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        pqtybox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        pqtybox.setPreferredSize(new java.awt.Dimension(250, 40));
        pqtybox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pqtyboxActionPerformed(evt);
            }
        });
        pqtybox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pqtyboxKeyTyped(evt);
            }
        });

        ppricebox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        ppricebox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        ppricebox.setPreferredSize(new java.awt.Dimension(250, 40));
        ppricebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppriceboxActionPerformed(evt);
            }
        });

        pexpdatebox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        pexpdatebox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        pexpdatebox.setPreferredSize(new java.awt.Dimension(250, 40));

        pmanudatebox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        pmanudatebox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        pmanudatebox.setPreferredSize(new java.awt.Dimension(250, 40));

        pupdate.setBackground(new java.awt.Color(0, 0, 102));
        pupdate.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        pupdate.setForeground(new java.awt.Color(255, 255, 255));
        pupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/update.png"))); // NOI18N
        pupdate.setText("Update");
        pupdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pupdate.setBorderPainted(false);
        pupdate.setIconTextGap(10);
        pupdate.setPreferredSize(new java.awt.Dimension(150, 35));
        pupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pupdateActionPerformed(evt);
            }
        });

        pdelete.setBackground(new java.awt.Color(162, 11, 11));
        pdelete.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        pdelete.setForeground(new java.awt.Color(255, 255, 255));
        pdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete.png"))); // NOI18N
        pdelete.setText("Delete");
        pdelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pdelete.setBorderPainted(false);
        pdelete.setIconTextGap(10);
        pdelete.setPreferredSize(new java.awt.Dimension(150, 35));
        pdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdeleteActionPerformed(evt);
            }
        });

        searchbox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        searchbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        searchbox.setPreferredSize(new java.awt.Dimension(250, 40));
        searchbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchboxActionPerformed(evt);
            }
        });
        searchbox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchboxKeyPressed(evt);
            }
        });

        psearchbox.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        psearchbox.setText("Search Product :");

        SupplierNameComboBox.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        SupplierNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Supplier" }));
        SupplierNameComboBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        SupplierNameComboBox.setPreferredSize(new java.awt.Dimension(250, 40));

        jButton3.setBackground(new java.awt.Color(0, 51, 0));
        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/statistics.png"))); // NOI18N
        jButton3.setText("Generate Report");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setBorderPainted(false);
        jButton3.setIconTextGap(10);
        jButton3.setPreferredSize(new java.awt.Dimension(150, 35));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(180, 255, 150));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/refresh.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.setPreferredSize(new java.awt.Dimension(57, 35));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        sidbox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        sidbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        sidbox.setPreferredSize(new java.awt.Dimension(250, 40));
        sidbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidboxActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel10.setText("Product ID :");
        jLabel10.setToolTipText("");

        pidtext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pidtextActionPerformed(evt);
            }
        });

        proIDComboBox.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        proIDComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Product Id" }));
        proIDComboBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        proIDComboBox.setPreferredSize(new java.awt.Dimension(250, 40));
        proIDComboBox.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                proIDComboBoxPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        proNameComboBox.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        proNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Product Name" }));
        proNameComboBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        proNameComboBox.setPreferredSize(new java.awt.Dimension(250, 40));

        searchbtn.setBackground(new java.awt.Color(8, 88, 8));
        searchbtn.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        searchbtn.setForeground(new java.awt.Color(255, 255, 255));
        searchbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search.png"))); // NOI18N
        searchbtn.setText("Search");
        searchbtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchbtn.setBorderPainted(false);
        searchbtn.setIconTextGap(10);
        searchbtn.setPreferredSize(new java.awt.Dimension(150, 35));
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sidbox, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(198, 198, 198)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(pupdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(oaddbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pdelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel5))
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(56, 56, 56)
                                        .addComponent(jLabel4))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(109, 109, 109)
                                        .addComponent(jLabel6))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(77, 77, 77)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(jLabel9)))
                                .addComponent(jLabel10))
                            .addGap(12, 12, 12)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(pqtybox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                .addComponent(ppricebox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(pexpdatebox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(pmanudatebox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(SupplierNameComboBox, 0, 0, Short.MAX_VALUE)
                                .addComponent(proIDComboBox, 0, 1, Short.MAX_VALUE)
                                .addComponent(proNameComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 1, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pidtext, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(psearchbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchbox, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(psearchbox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sidbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(proIDComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(proNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(pmanudatebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(pexpdatebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(ppricebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(pqtybox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SupplierNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addComponent(oaddbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pupdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pdelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(17, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pidtext))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel3.setBackground(new java.awt.Color(51, 0, 0));

        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));

        jLabel12.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));

        jLabel13.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));

        jLabel14.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));

        jLabel15.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI Light", 1, 48)); // NOI18N
        jLabel16.setText("Bio Foods ");

        jLabel17.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel17.setText("Point of Sales System");

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/left-arrow.png"))); // NOI18N
        jButton1.setText("Back to Stock Management Main");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jButton1.setIconTextGap(10);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jLabel1.setText("Stock Details");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel17))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(98, 98, 98)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(134, 134, 134))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel17)))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, 683, 683, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void oaddbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oaddbuttonActionPerformed
        //Insert
        createStock();
    }//GEN-LAST:event_oaddbuttonActionPerformed

    private void ptableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ptableMouseClicked
         //Mouse click event
        int r = ptable.getSelectedRow();
        
        String sid = ptable.getValueAt(r, 0).toString();
        String pid = ptable.getValueAt(r, 1).toString();
        String pname = ptable.getValueAt(r, 2).toString();
        Date pmanudate = (Date) ptable.getValueAt(r, 3);
        Date pexdate = (Date) ptable.getValueAt(r, 4);
        String pprice = ptable.getValueAt(r, 5).toString();
        String pqty = ptable.getValueAt(r, 6).toString();
        String psupname = ptable.getValueAt(r, 7).toString();
        
        sidbox.setText(sid);
        //pidbox.setText(pid);
        proIDComboBox.setSelectedItem(pid);
        //pnamebox.setText(pname);
        proNameComboBox.setSelectedItem(pname);
        pmanudatebox.setDate(pmanudate);
        pexpdatebox.setDate(pexdate);
        ppricebox.setText(pprice);
        pqtybox.setText(pqty);
        SupplierNameComboBox.setSelectedItem(psupname);
    }//GEN-LAST:event_ptableMouseClicked

    
    
    
    private void ptableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ptableKeyReleased
       
    }//GEN-LAST:event_ptableKeyReleased

    private void pqtyboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pqtyboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pqtyboxActionPerformed

    private void pqtyboxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pqtyboxKeyTyped

    }//GEN-LAST:event_pqtyboxKeyTyped

    private void ppriceboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppriceboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppriceboxActionPerformed

    private void pupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pupdateActionPerformed
        //update
        updateStock();
    }//GEN-LAST:event_pupdateActionPerformed

    private void pdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdeleteActionPerformed
        //Delete
        deleteUser();
    }//GEN-LAST:event_pdeleteActionPerformed

    private void searchboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchboxActionPerformed

    private void searchboxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchboxKeyPressed
               
    }//GEN-LAST:event_searchboxKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        StockManagementMain sm = new StockManagementMain();
        sm.setVisible(true);
        this.dispose();   
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    
    }//GEN-LAST:event_jButton4ActionPerformed

    private void sidboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sidboxActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Report
        String pidPara = pidtext.getText();
        HashMap para = new HashMap();
        para.put("pid", pidPara);
        

        Reports rpt = new Reports("C:\\Users\\Vihanga\\Desktop\\POS_Reports\\productWiseStockAdding.jasper",para);
        rpt.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void pidtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pidtextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pidtextActionPerformed

    private void proIDComboBoxPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_proIDComboBoxPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
        
        String auto = proIDComboBox.getSelectedItem().toString();
        String sql = "select * from productdetails where pid = ?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, auto);
            rs = pst.executeQuery();
            
            if(rs.next())
            {
                String add1 = rs.getString("pname");
                proNameComboBox.setSelectedItem(add1);
                
                String add2 = rs.getString("pprice");
                ppricebox.setText(add2);
                
                String add3 = rs.getString("psupplier");
                SupplierNameComboBox.setSelectedItem(add3);
             
                
            }
        }catch(Exception e){
            System.out.println("error");
        }
    }//GEN-LAST:event_proIDComboBoxPopupMenuWillBecomeInvisible

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        // TODO add your handling code here:
        
        String search = searchbox.getText();
        
        String sql = "SELECT sid,pid, pname, pmanudate, pexdate, pprice, pqty, psupname FROM product WHERE sid='"+search+"' ";
        
        try{
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            ptable.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            
        }
        
        
    }//GEN-LAST:event_searchbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StockDetails2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StockDetails2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StockDetails2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StockDetails2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StockDetails2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> SupplierNameComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton oaddbutton;
    private javax.swing.JButton pdelete;
    private com.toedter.calendar.JDateChooser pexpdatebox;
    private javax.swing.JTextField pidtext;
    private com.toedter.calendar.JDateChooser pmanudatebox;
    private javax.swing.JTextField ppricebox;
    private javax.swing.JTextField pqtybox;
    private javax.swing.JComboBox<String> proIDComboBox;
    private javax.swing.JComboBox<String> proNameComboBox;
    private javax.swing.JLabel psearchbox;
    public static javax.swing.JTable ptable;
    private javax.swing.JButton pupdate;
    private javax.swing.JTextField searchbox;
    private javax.swing.JButton searchbtn;
    private javax.swing.JTextField sidbox;
    // End of variables declaration//GEN-END:variables
}
