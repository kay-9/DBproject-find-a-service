/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app_functions;

import Admin.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 
 */
public class InterfaceUser extends javax.swing.JFrame {

    /**
     * Creates new form StatisticsOfService
     */
    public InterfaceUser(String ser) {
        initComponents();
        jLabel20.setText(jLabel20.getText()+ser);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb?useTimezone=true&serverTimezone=UTC", "root", "")) {
                                
                Statement stat = con.createStatement();
               
                String r2 = "Select count(*) from Employee where service=?";      
                PreparedStatement stmt2=con.prepareStatement(r2);
                stmt2.setString(1,ser);
                ResultSet res=stmt2.executeQuery();          
                if(res.next())
                    jTextField2.setText(res.getString("count(*)")+" Employees");
                
                String r3 = "Select count(*) from Contract as c,Employee as e where c.id_employee=e.id_employee and e.service=?";      
                stmt2=con.prepareStatement(r3);
                stmt2.setString(1,ser);
                res=stmt2.executeQuery();        
                if(res.next())
                    jTextField3.setText(res.getString("count(*)")+" Contracts");
                
               
                String r7 = "Select min(priceC) from Contract as c,Employee as e where c.id_employee=e.id_employee and e.service=?";      
                stmt2=con.prepareStatement(r7);
                stmt2.setString(1,ser);
                res=stmt2.executeQuery();        
                String t6="price from : ";
                if(res.next())
                    t6+=res.getString("min(priceC)")+" to ";
                String r6 = "Select max(priceC) from Contract as c,Employee as e where c.id_employee=e.id_employee and e.service=?";      
                stmt2=con.prepareStatement(r6);
                stmt2.setString(1,ser);
                res=stmt2.executeQuery();        
                if(res.next())
                    t6+=res.getString("max(priceC)");
                   jTextField6.setText(t6);
                
                String r12 = "Select sum(priceC) from Contract as c,Employee as e where c.id_employee=e.id_employee and e.service=?";      
                stmt2=con.prepareStatement(r12);
                stmt2.setString(1,ser);
                res=stmt2.executeQuery();        
                if(res.next())
                    jTextField7.setText("Cost of all contract: "+res.getString("sum(priceC)")); 
                
                
                String r8 = "create or replace view v1(moy,name,lastname) as select sum(o.stars)/count(*) ,e.name,e.lastname from opinion as o,contract as c,employee as e where o.idC=c.idc and c.id_employee=e.id_employee and e.service=? group by(c.id_employee)";                
                PreparedStatement stmt8 = con.prepareStatement(r8);  
                stmt8.setString(1,ser);
                stmt8.executeUpdate();
                        
                String r9 = "select max(moy),name,lastname from v1";                
                res=stat.executeQuery(r9);
                
                if(res.next()){
                    jTextField14.setText("name"+res.getString("name")+" "+res.getString("lastname"));   
                    jTextField15.setText("stars : "+res.getString("max(moy)")+"/5");
                    System.out.println(res.getString("name"));
                }
                
                String r10 = "create or replace view v2(nbr,name,lastname) as select count(*) ,e.name,e.lastname from contract as c,employee as e where c.id_employee=e.id_employee and service=? group by(e.id_employee)";                
                PreparedStatement stmt10 = con.prepareStatement(r10);  
                stmt10.setString(1,ser);
                stmt10.executeUpdate();
                String r11 = "select max(nbr),name,lastname from v2";                
                res=stat.executeQuery(r11);
                if(res.next()){
                    jTextField12.setText("name:"+res.getString("name")+" "+res.getString("lastname"));
                    jTextField13.setText("had "+res.getString("max(nbr)")+" contracts");               
                }
                
                         DefaultTableModel model = (DefaultTableModel) tt.getModel();
                         model.setRowCount(0);
                         
                String sql = "select id_employee, name, lastname from employee where service=?";
                PreparedStatement stmt = con.prepareStatement(sql);  
                stmt.setString(1,ser);
                ResultSet rs = stmt.executeQuery();
                       
               while(rs.next()){
                        String id   = rs.getString("id_employee");
                        String nm   = rs.getString("name");
                        String lt   = rs.getString("lastname");
                        String d="0",e="0",f="0";
                        String sq2 = "select count(idc)  ,sum(priceC) from contract as c,employee as e where c.id_employee=e.id_employee and e.id_employee=?";
                        PreparedStatement stmt1 = con.prepareStatement(sq2);  
                        stmt1.setString(1,id);
                        ResultSet rs2 = stmt1.executeQuery();
                        if(rs2.next()){
                            d=rs2.getString("count(idc)");
                            e=rs2.getString("sum(priceC)");
                        }
                        if(d.equals("0")){e="-";f="-";}
                        else{
                            String sq3 = "select sum(stars)/count(c.id_employee) from opinion as o,contract as c where o.idC=c.idC and c.id_employee=?";
                            PreparedStatement stmt3 = con.prepareStatement(sq3);  
                            stmt3.setString(1,id);
                            ResultSet rs3 = stmt3.executeQuery();
                            if(rs3.next()){
                                f=rs3.getString("sum(stars)/count(c.id_employee)");
                            }
                        }
                        Object [] data = {id,nm,lt,d,e,f};
                        

                          model.addRow(data);
    
               }
            }
             jLabel1.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
                GeneralStatistics gs = new GeneralStatistics();
                        gs.setVisible(true);
                        setVisible(false);
        }
    }); 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
      
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tt = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/user.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel20.setText("j");

        jLabel1.setFont(new java.awt.Font("Snap ITC", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/return.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(218, 218, 218))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
        );

        jTextField3.setBackground(new java.awt.Color(255, 0, 102));
        jTextField3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/contract.png"))); // NOI18N
        jTextField3.setText("Submit a review");
        jTextField3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 163, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );

        tt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "name ", "Last name", "num contract", "cost", "starts"
            }
        ));
        tt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ttMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(621, 621, 621)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(190, 190, 190))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ttMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ttMouseClicked
           
    }//GEN-LAST:event_ttMouseClicked
  
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
            java.util.logging.Logger.getLogger(InterfaceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceUser("78").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jTextField3;
    private javax.swing.JTable tt;
    // End of variables declaration//GEN-END:variables
}
