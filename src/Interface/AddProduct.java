/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Code.DBconnect;
import static Interface.ProductDetails.ptable;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Vihanga
 */
public class AddProduct extends javax.swing.JFrame {

    /**
     * Creates new form Product
     */
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public AddProduct() {
        initComponents();
        
        //getConnection();
        con = DBconnect.connect();
        
        //tableload
        tableLoad();
        
         //Autocomplete supplier ComboBox
        AutoCompleteDecorator.decorate(SupplierNameComboBox);
        FillComboSupplierBox();
        theader();
        
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
    
    public void tableLoad() {
        String q = "SELECT pid, pname, pprice, pqty, psupplier FROM productdetails";
         try {
            pst = con.prepareStatement(q);
            rs = pst.executeQuery();
            
            ptable.setModel(DbUtils.resultSetToTableModel(rs));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(StockDetails2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    public void checkValidation(String requirment){
        String pid = pidbox.getText();
        String pname = pnamebox.getText();
        String pprice = ppricebox.getText();
        String supName = SupplierNameComboBox.getSelectedItem().toString();
       
       if (pid.isEmpty()) { 
            JOptionPane.showMessageDialog(null, "Please enter a Product ID", "Error", JOptionPane.ERROR_MESSAGE);
       }else if(pname.isEmpty()){
           JOptionPane.showMessageDialog(null, "Please enter a Product Name", "Error", JOptionPane.ERROR_MESSAGE);
       }else if(pprice.isEmpty()) {
           JOptionPane.showMessageDialog(null, "Please enter a Product Price", "Error", JOptionPane.ERROR_MESSAGE);
       }else if(!Pattern.matches("^[1-9]\\d*(\\.\\d+)?$",pprice)){
           JOptionPane.showMessageDialog(null, "Please enter a valid Price", "Error", JOptionPane.ERROR_MESSAGE);
       }else if (supName.equals("Choose Supplier")) {
             JOptionPane.showMessageDialog(null, "Please select Product Supllier", "Error", JOptionPane.ERROR_MESSAGE);
       } else {
           
           if(requirment.equals("CREATE")){
               createProduct();
           } else if (requirment.equals("UPDATE")) {
               updateProduct();
           } else {
           
           }
       }
      
    }
    
    public void createProduct(){
    String pid = pidbox.getText();
    String pname = pnamebox.getText();
    String pprice = ppricebox.getText();
    String pqty = "0";
    String supName = SupplierNameComboBox.getSelectedItem().toString();
     
     String q = "INSERT INTO productdetails (pid, pname, pprice, pqty, psupplier) VALUES ('"+pid+"', '"+pname+"','"+pprice+"', '"+pqty+"', '"+supName+"')";
        try {
            pst = con.prepareStatement(q);
            pst.execute();
            
            //UserDetailTable Load
            tableLoad();
            
            //clear textfields
            clearTextBoxes();
            
            JOptionPane.showMessageDialog(null, 
                              "Product create successfully", 
                              "Create New Product", 
                              JOptionPane.PLAIN_MESSAGE);
            
        } catch (SQLException ex) {
            Logger.getLogger(StockDetails2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, 
                              "Error! Product creation unsuccessful", 
                              "Create New Product", 
                              JOptionPane.WARNING_MESSAGE);
        }
    }
       
     public void updateProduct(){
         int x = JOptionPane.showConfirmDialog(null, "Do You really want to Update?");
        if(x==0){
            String pid = pidbox.getText();
            String pname = pnamebox.getText();
            String pprice = ppricebox.getText();
            String supName = SupplierNameComboBox.getSelectedItem().toString();
            
            int r = ptable.getSelectedRow();
            String tablePid = ptable.getValueAt(r, 0).toString();
                
            String q = "UPDATE productdetails SET pid='"+pid+"', pname='"+pname+"', pprice='"+pprice+"', psupplier='"+supName+"'  WHERE pid='"+tablePid+"'";
             try {
            pst = con.prepareStatement(q);
            pst.execute();
            
            //UserDetailTable Load
            tableLoad();
        
            //clear textfields
            clearTextBoxes();
            
            JOptionPane.showMessageDialog(null, 
                              "Product update successfully", 
                              "Product update", 
                              JOptionPane.PLAIN_MESSAGE);
            
        } catch (SQLException ex) {
            Logger.getLogger(StockDetails2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, 
                              "Error! Product update unsuccessful", 
                              "Update product", 
                              JOptionPane.WARNING_MESSAGE);
        }
        } else if(x==1) {
            
        } else{
            
        }
    }
    
    public void deleteProduct(){
        int x = JOptionPane.showConfirmDialog(null, "Do You really want to Delete?");
        if(x==0){
            int r = ptable.getSelectedRow();
            String tablepid= ptable.getValueAt(r, 0).toString();
              
            String q = "DELETE FROM productdetails WHERE pid='"+tablepid+"' ";
            try {
            pst = con.prepareStatement(q);
            pst.execute();
            
            //UserDetailTable Load
            tableLoad();
        
            //clear textfields
            clearTextBoxes();
            
            JOptionPane.showMessageDialog(null, 
                              "Product delete successfully", 
                              "Delete Product", 
                              JOptionPane.PLAIN_MESSAGE);
            
        } catch (SQLException ex) {
            Logger.getLogger(StockDetails2.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, 
                              "Error! Product delete unsuccessful", 
                              "Delete Product", 
                              JOptionPane.WARNING_MESSAGE);
        }
        } else if(x==1) {
            
        } else{
            
        }
    }  
     
     public void clearTextBoxes() {
         pidbox.setText("");
         pnamebox.setText("");
         ppricebox.setText("");
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
        jLabel5 = new javax.swing.JLabel();
        pnamebox = new javax.swing.JTextField();
        pidbox = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        SupplierNameComboBox = new javax.swing.JComboBox<>();
        ppricebox = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ptable = new javax.swing.JTable();
        oaddbutton = new javax.swing.JButton();
        pupdate = new javax.swing.JButton();
        pdelete = new javax.swing.JButton();
        psearch = new javax.swing.JTextField();
        psearchbox = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(180, 255, 150));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel5.setText("Product Name :");
        jLabel5.setToolTipText("");

        pnamebox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        pnamebox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        pnamebox.setPreferredSize(new java.awt.Dimension(250, 40));
        pnamebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pnameboxActionPerformed(evt);
            }
        });

        pidbox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        pidbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        pidbox.setPreferredSize(new java.awt.Dimension(250, 40));
        pidbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pidboxActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel10.setText("Product ID :");
        jLabel10.setToolTipText("");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel9.setText("Supplier Name :");
        jLabel9.setToolTipText("");

        SupplierNameComboBox.setFont(new java.awt.Font("Segoe UI Semibold", 1, 15)); // NOI18N
        SupplierNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Supplier" }));
        SupplierNameComboBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        SupplierNameComboBox.setPreferredSize(new java.awt.Dimension(250, 40));

        ppricebox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        ppricebox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        ppricebox.setPreferredSize(new java.awt.Dimension(250, 40));
        ppricebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppriceboxActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel6.setText("Price :");
        jLabel6.setToolTipText("");

        ptable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        ptable.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        ptable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Product", "Price", "Quantity", "Supplier"
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

        psearch.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        psearch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        psearch.setPreferredSize(new java.awt.Dimension(250, 40));
        psearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                psearchActionPerformed(evt);
            }
        });
        psearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                psearchKeyPressed(evt);
            }
        });

        psearchbox.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        psearchbox.setText("Search Product :");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/loupe.png"))); // NOI18N

        jButton3.setBackground(new java.awt.Color(0, 51, 0));
        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/statistics.png"))); // NOI18N
        jButton3.setText("Generate Report");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setBorderPainted(false);
        jButton3.setIconTextGap(10);
        jButton3.setPreferredSize(new java.awt.Dimension(150, 35));

        jButton4.setBackground(new java.awt.Color(180, 255, 150));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/refresh.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.setPreferredSize(new java.awt.Dimension(57, 35));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(435, 435, 435)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(356, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel10))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(36, 36, 36)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ppricebox, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(SupplierNameComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                            .addComponent(pnamebox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(pidbox, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pupdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(oaddbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pdelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(97, 97, 97)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(psearchbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(psearch, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(psearchbox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(psearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pidbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnamebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ppricebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SupplierNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addComponent(oaddbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pupdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pdelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
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
        jLabel1.setText("Add Product");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel16)))
                .addGap(12, 12, 12)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel17)
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 18, 50));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pnameboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pnameboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pnameboxActionPerformed

    private void pidboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pidboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pidboxActionPerformed

    private void ppriceboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppriceboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppriceboxActionPerformed

    private void ptableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ptableMouseClicked
        //Mouse click event
        int r = ptable.getSelectedRow();

        String pid = ptable.getValueAt(r, 0).toString();
        String pname = ptable.getValueAt(r, 1).toString();
        String pprice = ptable.getValueAt(r, 2).toString();
        String psupname = ptable.getValueAt(r, 4).toString();

        pidbox.setText(pid);
        pnamebox.setText(pname);
        ppricebox.setText(pprice);
        SupplierNameComboBox.setSelectedItem(psupname);
    }//GEN-LAST:event_ptableMouseClicked

    private void ptableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ptableKeyReleased

    }//GEN-LAST:event_ptableKeyReleased

    private void oaddbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oaddbuttonActionPerformed
        //Insert
        checkValidation("CREATE");
    }//GEN-LAST:event_oaddbuttonActionPerformed

    private void pupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pupdateActionPerformed
        //update
        checkValidation("UPDATE");
    }//GEN-LAST:event_pupdateActionPerformed

    private void pdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdeleteActionPerformed
        //Delete
        deleteProduct();
    }//GEN-LAST:event_pdeleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        StockManagementMain sm = new StockManagementMain();
        sm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void psearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_psearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_psearchActionPerformed

    private void psearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_psearchKeyPressed

    }//GEN-LAST:event_psearchKeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //Clear textfields
        clearTextBoxes();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddProduct().setVisible(true);
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton oaddbutton;
    private javax.swing.JButton pdelete;
    private javax.swing.JTextField pidbox;
    private javax.swing.JTextField pnamebox;
    private javax.swing.JTextField ppricebox;
    private javax.swing.JTextField psearch;
    private javax.swing.JLabel psearchbox;
    public static javax.swing.JTable ptable;
    private javax.swing.JButton pupdate;
    // End of variables declaration//GEN-END:variables
}
