/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import app_functions.login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author 10
 */
public class GeneralStatistics extends javax.swing.JFrame {

    /**
     * Creates new form GeneralStatistics
     */
    public GeneralStatistics() {
        initComponents();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb?useTimezone=true&serverTimezone=UTC", "root", "")) {
                                
                Statement stat = con.createStatement();
                String r1 = "Select count(*) from user";                
                ResultSet res=stat.executeQuery(r1);
                if(res.next())
                    jTextField1.setText(res.getString("count(*)")+" Users");
                
                String r2 = "Select count(*) from Employee";                
                res=stat.executeQuery(r2);
                if(res.next())
                    jTextField2.setText(res.getString("count(*)")+" Employees");
                
                String r3 = "Select count(*) from Contract";                
                res=stat.executeQuery(r3);
                if(res.next())
                    jTextField3.setText(res.getString("count(*)")+" Contracts");
                
                String r4 = "Select count(distinct(service)) from Employee";                
                res=stat.executeQuery(r4);
                if(res.next())
                    jTextField4.setText(res.getString("count(distinct(service))")+" Services");
                
                String r5 = "Select count(*) from opinion";                
                res=stat.executeQuery(r5);
                if(res.next())
                    jTextField5.setText(res.getString("count(*)")+" Comments");
                
                
                String r7 = "Select min(priceC) from Contract";                
                res=stat.executeQuery(r7);
                String t6="price from : ";
                if(res.next())
                    t6+=res.getString("min(priceC)")+" to ";
                String r6 = "Select max(priceC) from Contract";                
                res=stat.executeQuery(r6);
                if(res.next())
                    t6+=res.getString("max(priceC)");
                   jTextField6.setText(t6);
                
                String r12 = "Select sum(priceC) from Contract";                
                res=stat.executeQuery(r12);
                if(res.next())
                    jTextField7.setText("Cost of all contract: "+res.getString("sum(priceC)")); 
                
                
                String r8 = "create or replace view v1(moy,name,lastname,service) as select sum(o.stars)/count(*) ,e.name,e.lastname,e.service from opinion as o,contract as c,employee as e where o.idC=c.idc and c.id_employee=e.id_employee group by(c.id_employee)";                
                stat.executeUpdate(r8);
                String r9 = "select max(moy),name,lastname,service from v1";                
                res=stat.executeQuery(r9);
                String t8="",t9="",t10="";
                if(res.next()){
                    t8=res.getString("name")+" "+res.getString("lastname");
                    t9="service : "+res.getString("service");                    
                    t10="stars : "+res.getString("max(moy)")+"/5";
                    jTextField8.setText(t8);
                   // jTextField9.setText(t9);
                    jTextField10.setText(t10);
                }
                
                String r10 = "create or replace view v2(nbr,name,lastname) as select count(*) ,u.name,u.lastname from user as u,contract as c where  c.id_user=u.id_user group by(c.id_user)";                
                stat.executeUpdate(r10);
                String r11 = "select max(nbr),name,lastname from v2";                
                res=stat.executeQuery(r11);
                if(res.next()){
                    jTextField11.setText(res.getString("name")+" "+res.getString("lastname"));
                    jTextField12.setText("used "+res.getString("max(nbr)")+" services");               
                }
                

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
                
                    
              
        
        /*jTextField1.disable();
        jTextField2.disable();
        jTextField3.disable();
        jTextField4.disable();
        jTextField4.disable();
        jTextField5.disable();
        jTextField6.disable();
        jTextField7.disable();
        jTextField8.disable();
        jTextField9.disable();
        jTextField10.disable();
        jTextField11.disable();
        jTextField12.disable();
        jTextField13.disable();
        jTextField14.disable();
        jTextField15.disable();*/
       
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
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/podium.png"))); // NOI18N
        jLabel8.setText("best employee");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTextField8.setText("jLabel1");

        jTextField10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTextField10.setText("jLabel1");

        jTextField7.setBackground(new java.awt.Color(255, 0, 102));
        jTextField7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/money-bag.png"))); // NOI18N
        jTextField7.setText("jLabel1");
        jTextField7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(1, 1, 1)
                .addComponent(jTextField8)
                .addGap(1, 1, 1)
                .addComponent(jTextField10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/stat1.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel20.setText("General Statistic");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116)
                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextField1.setBackground(new java.awt.Color(255, 0, 102));
        jTextField1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/teamwork.png"))); // NOI18N
        jTextField1.setText("jLabel1");
        jTextField1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField2.setBackground(new java.awt.Color(255, 0, 102));
        jTextField2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/employee (2).png"))); // NOI18N
        jTextField2.setText("jLabel1");
        jTextField2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField3.setBackground(new java.awt.Color(255, 0, 102));
        jTextField3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/contract.png"))); // NOI18N
        jTextField3.setText("jLabel1");
        jTextField3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField4.setBackground(new java.awt.Color(255, 0, 102));
        jTextField4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/customer-service.png"))); // NOI18N
        jTextField4.setText("jLabel1");
        jTextField4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField5.setBackground(new java.awt.Color(255, 0, 102));
        jTextField5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/comment.png"))); // NOI18N
        jTextField5.setText("jLabel1");
        jTextField5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField6.setBackground(new java.awt.Color(255, 0, 102));
        jTextField6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/budget.png"))); // NOI18N
        jTextField6.setText("jLabel1");
        jTextField6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/user.png"))); // NOI18N
        jLabel9.setText("best user");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTextField11.setText("jLabel1");

        jTextField12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTextField12.setText("jLabel1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(30, 30, 30))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(1, 1, 1)
                .addComponent(jTextField11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField12)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTextField1.getAccessibleContext().setAccessibleName("jTextField1");

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(GeneralStatistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeneralStatistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeneralStatistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeneralStatistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GeneralStatistics().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel jTextField1;
    private javax.swing.JLabel jTextField10;
    private javax.swing.JLabel jTextField11;
    private javax.swing.JLabel jTextField12;
    private javax.swing.JLabel jTextField2;
    private javax.swing.JLabel jTextField3;
    private javax.swing.JLabel jTextField4;
    private javax.swing.JLabel jTextField5;
    private javax.swing.JLabel jTextField6;
    private javax.swing.JLabel jTextField7;
    private javax.swing.JLabel jTextField8;
    // End of variables declaration//GEN-END:variables
}
