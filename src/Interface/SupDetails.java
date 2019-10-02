/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Code.Supplier;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Vihanga
 */
public class SupDetails extends javax.swing.JFrame {

    /**
     * Creates new form SupDetails
     */
    
    Supplier s1 = new Supplier();
    DefaultTableModel model;
    
    public SupDetails() {
        initComponents();
        theader();
        getConnection();
        s1.fillSupplierTable(stable, "");
        model = (DefaultTableModel) stable.getModel();
    }
    
    public Connection getConnection()
    {
        Connection con = null;
        
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itp","root","");
            
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
    }
    
    public boolean verifText()
    {
        
        if      (sidbox.getText().equals("")||snamebox.getText().equals("")
                 ||semailbox.getText().equals("")
                //|| (!semailbox.getText().equals("") && (!Pattern.matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$",semailbox.getText()) ))
                ||smobilebox.getText().equals("")
               // || (!Pattern.matches("\\d{10}",smobilebox.getText()))
                )
            
        {
            JOptionPane.showMessageDialog(null,"One or More Field Empty.");
            return false;
        }
        
        else if ((!Pattern.matches("\\d{10}",smobilebox.getText())))
        {
            JOptionPane.showMessageDialog(null,"Phone Number Invalid Length");
            return false;
        }
        
        else if ((!semailbox.getText().equals("") && (!Pattern.matches("^[A-Za-z0-9_.]+[@][A-Za-z.]+$",semailbox.getText()) )))
        {
            JOptionPane.showMessageDialog(null,"Invalid Email");
            return false;
        }
 
        else
        {
            return true;
        }
        
            
    }
    
    private void theader(){
        JTableHeader thead = stable.getTableHeader();
        thead.setForeground(Color.BLACK);
        thead.setFont(new Font("Tahome",Font.BOLD, 14));
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
        jLabel1 = new javax.swing.JLabel();
        sidbox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        snamebox = new javax.swing.JTextField();
        semailbox = new javax.swing.JTextField();
        smobilebox = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        stable = new javax.swing.JTable();
        psearchbox = new javax.swing.JLabel();
        sserchbox = new javax.swing.JTextField();
        saddbutton = new javax.swing.JButton();
        supdate = new javax.swing.JButton();
        sdelete = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 700));

        jPanel1.setBackground(new java.awt.Color(180, 255, 150));
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 700));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 36)); // NOI18N
        jLabel1.setText("Supplier Details");

        sidbox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        sidbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 0), 2, true));
        sidbox.setPreferredSize(new java.awt.Dimension(250, 40));
        sidbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidboxActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel3.setText("Supplier ID :");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel5.setText("Supplier Name :");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel6.setText("Email Address :");

        snamebox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        snamebox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 0), 2, true));
        snamebox.setPreferredSize(new java.awt.Dimension(250, 40));
        snamebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                snameboxActionPerformed(evt);
            }
        });

        semailbox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        semailbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 0), 2, true));
        semailbox.setPreferredSize(new java.awt.Dimension(250, 40));
        semailbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                semailboxActionPerformed(evt);
            }
        });

        smobilebox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        smobilebox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 0), 2, true));
        smobilebox.setPreferredSize(new java.awt.Dimension(250, 40));
        smobilebox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smobileboxActionPerformed(evt);
            }
        });
        smobilebox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                smobileboxKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel7.setText("Mobile Number :");

        stable.setBorder(new javax.swing.border.MatteBorder(null));
        stable.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        stable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Supplier Name", "Email", "Mobile Number"
            }
        ));
        stable.setRowHeight(25);
        stable.setRowMargin(3);
        stable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stableMouseClicked(evt);
            }
        });
        stable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                stableKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stableKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(stable);

        psearchbox.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        psearchbox.setText("Search Supplier :");

        sserchbox.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        sserchbox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        sserchbox.setPreferredSize(new java.awt.Dimension(250, 40));
        sserchbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sserchboxActionPerformed(evt);
            }
        });
        sserchbox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sserchboxKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sserchboxKeyTyped(evt);
            }
        });

        saddbutton.setBackground(new java.awt.Color(8, 88, 8));
        saddbutton.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        saddbutton.setForeground(new java.awt.Color(255, 255, 255));
        saddbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add.png"))); // NOI18N
        saddbutton.setText("Insert");
        saddbutton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        saddbutton.setBorderPainted(false);
        saddbutton.setIconTextGap(10);
        saddbutton.setPreferredSize(new java.awt.Dimension(150, 35));
        saddbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saddbuttonActionPerformed(evt);
            }
        });

        supdate.setBackground(new java.awt.Color(0, 0, 102));
        supdate.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        supdate.setForeground(new java.awt.Color(255, 255, 255));
        supdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/update.png"))); // NOI18N
        supdate.setText("Update");
        supdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        supdate.setBorderPainted(false);
        supdate.setIconTextGap(10);
        supdate.setPreferredSize(new java.awt.Dimension(150, 35));
        supdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supdateActionPerformed(evt);
            }
        });

        sdelete.setBackground(new java.awt.Color(162, 11, 11));
        sdelete.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        sdelete.setForeground(new java.awt.Color(255, 255, 255));
        sdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete.png"))); // NOI18N
        sdelete.setText("Delete");
        sdelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sdelete.setBorderPainted(false);
        sdelete.setIconTextGap(10);
        sdelete.setPreferredSize(new java.awt.Dimension(150, 35));
        sdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sdeleteActionPerformed(evt);
            }
        });

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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/loupe.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(516, 516, 516)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(psearchbox, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sserchbox, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(356, 356, 356)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 34, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(jLabel3)
                                        .addGap(12, 12, 12)
                                        .addComponent(sidbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(9, 9, 9)
                                                .addComponent(jLabel5))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addComponent(jLabel6))
                                            .addComponent(jLabel7))
                                        .addGap(12, 12, 12)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(snamebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(semailbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(smobilebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(supdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(saddbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sdelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(67, 67, 67)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(psearchbox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sserchbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sidbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(snamebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(semailbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(smobilebox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addComponent(saddbutton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(supdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sdelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 102, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 111, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sidboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sidboxActionPerformed

    private void snameboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_snameboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_snameboxActionPerformed

    private void semailboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_semailboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_semailboxActionPerformed

    private void smobileboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smobileboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_smobileboxActionPerformed

    private void smobileboxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smobileboxKeyTyped
        if(!Character.isDigit(evt.getKeyChar()))
        {
            evt.consume();
        }
    }//GEN-LAST:event_smobileboxKeyTyped

    int rowIndex;
    private void stableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stableMouseClicked
        // TODO add your handling code here:
        rowIndex = stable.getSelectedRow();
        

        sidbox.setText(model.getValueAt(rowIndex, 0).toString());
        snamebox.setText(model.getValueAt(rowIndex, 1).toString());
        semailbox.setText(model.getValueAt(rowIndex, 2).toString());
        smobilebox.setText(model.getValueAt(rowIndex, 3).toString());
    }//GEN-LAST:event_stableMouseClicked

    private void stableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_stableKeyPressed

    private void stableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stableKeyTyped

    }//GEN-LAST:event_stableKeyTyped

    private void sserchboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sserchboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sserchboxActionPerformed

    private void sserchboxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sserchboxKeyPressed
        // TODO add your handling code here:
        stable.setModel(new DefaultTableModel(null,new Object[]{"Supplier ID" , "Supplier Name" , "Email" , "Mobile Number"}));
        s1.fillSupplierTable(stable, sserchbox.getText());
    }//GEN-LAST:event_sserchboxKeyPressed

    private void sserchboxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sserchboxKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_sserchboxKeyTyped

    private void saddbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saddbuttonActionPerformed
        String sid = sidbox.getText();
        String sname = snamebox.getText();
        String semail = semailbox.getText();
        String smobile = smobilebox.getText();

        if(verifText()){

            Supplier s = new Supplier();
            s.insertUpdateDeleteSupplier('i', sid, sname, semail, smobile);

            SupDetails.stable.setModel(new DefaultTableModel(null,new Object[]{"Supplier ID" , "Supplier Name" , "Email" , "Mobile Number"}));
            s.fillSupplierTable(SupDetails.stable, "");
        }
    }//GEN-LAST:event_saddbuttonActionPerformed

    private void supdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supdateActionPerformed
        // TODO add your handling code here:
        
        String sid = sidbox.getText();
        String sname = snamebox.getText();
        String semail = semailbox.getText();
        String smobile = smobilebox.getText();

        if(verifText()){

            Supplier s = new Supplier();
            s.insertUpdateDeleteSupplier('u', sid, sname, semail, smobile);
            SupDetails.stable.setModel(new DefaultTableModel(null,new Object[]{"Supplier ID" , "Supplier Name" , "Email" , "Mobile Number"}));
            s.fillSupplierTable(SupDetails.stable, "");
        }
    }//GEN-LAST:event_supdateActionPerformed

    private void sdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sdeleteActionPerformed
        // TODO add your handling code here:
        
        if(sidbox.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "No Supplier Selected");
        }
        
        else
        {
            String sid = sidbox.getText();
            Supplier s = new Supplier();
            s.insertUpdateDeleteSupplier('d', sid, null, null, null);
            
             s.fillSupplierTable(stable, "");
            stable.setModel(new DefaultTableModel(null,new Object[]{"Supplier ID" , "Supplier Name" , "Email" , "Mobile Number"}));
            s1.fillSupplierTable(stable, sserchbox.getText());
            
            sidbox.setText("");
            snamebox.setText("");
            semailbox.setText("");
            smobilebox.setText("");
        }
    }//GEN-LAST:event_sdeleteActionPerformed

    private void stableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stableKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN )
        {
                rowIndex = stable.getSelectedRow();
            
                sidbox.setText(model.getValueAt(rowIndex, 0).toString());
                snamebox.setText(model.getValueAt(rowIndex, 1).toString());
                semailbox.setText(model.getValueAt(rowIndex, 2).toString());
                smobilebox.setText(model.getValueAt(rowIndex, 3).toString());
        }
    }//GEN-LAST:event_stableKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        StockManagementMain sm = new StockManagementMain();
        sm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(SupDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SupDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SupDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SupDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SupDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel psearchbox;
    private javax.swing.JButton saddbutton;
    private javax.swing.JButton sdelete;
    private javax.swing.JTextField semailbox;
    private javax.swing.JTextField sidbox;
    private javax.swing.JTextField smobilebox;
    private javax.swing.JTextField snamebox;
    private javax.swing.JTextField sserchbox;
    public static javax.swing.JTable stable;
    private javax.swing.JButton supdate;
    // End of variables declaration//GEN-END:variables
}
