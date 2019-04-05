/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

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
public class StatisticsOfService extends javax.swing.JFrame {
    String sr="";
    String dt="";
    /**
     * Creates new form StatisticsOfService
     */
    public StatisticsOfService(String ser,String y,String m) {
        initComponents();
        sr=ser;
        dt="";
        if(!y.equals(""))dt=y+"-"+m;
        dt+="%";
        String af=jLabel20.getText()+ser;
        if(!y.equals(""))af+=" in "+y;
        if(!m.equals(""))af+=" - "+m;
        jLabel20.setText(af);
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
                
                String r3 = "Select count(*) from Contract as c,Employee as e where c.id_employee=e.id_employee and e.service=? and c.date like ?";      
                stmt2=con.prepareStatement(r3);
                stmt2.setString(1,ser);
                stmt2.setString(2,dt);
                res=stmt2.executeQuery();        
                if(res.next())
                    jTextField3.setText(res.getString("count(*)")+" Contracts");
                
               
                String r7 = "Select min(serviceprice) from Contract as c,Employee as e where c.id_employee=e.id_employee and e.service=? and c.date like ?";      
                stmt2=con.prepareStatement(r7);
                stmt2.setString(1,ser);
                stmt2.setString(2,dt);
                res=stmt2.executeQuery();        
                String t6="price from : ";
                if(res.next())
                    t6+=res.getString("min(serviceprice)")+" to ";
                String r6 = "Select max(serviceprice) from Contract as c,Employee as e where c.id_employee=e.id_employee and e.service=? and c.date like ?";      
                stmt2=con.prepareStatement(r6);
                stmt2.setString(1,ser);
                stmt2.setString(2,dt);
                res=stmt2.executeQuery();        
                if(res.next())
                    t6+=res.getString("max(serviceprice)");
                   jTextField6.setText(t6);
                
                String r12 = "Select sum(priceC) from Contract as c,Employee as e where c.id_employee=e.id_employee and e.service=? and c.date like ?";      
                stmt2=con.prepareStatement(r12);
                stmt2.setString(1,ser);
                stmt2.setString(2,dt);
                res=stmt2.executeQuery();        
                if(res.next())
                    jTextField7.setText("Cost of all contract: "+res.getString("sum(priceC)")); 
                
                
                String r8 = "create or replace view v1(moy,name,lastname) as select sum(o.stars)/count(*) ,e.name,e.lastname from opinion as o,contract as c,employee as e where o.idC=c.idc and c.id_employee=e.id_employee and e.service=? and c.date like ? group by(c.id_employee)";                
                PreparedStatement stmt8 = con.prepareStatement(r8);  
                stmt8.setString(1,ser);
                stmt8.setString(2,dt);
                stmt8.executeUpdate();
                        
                String r9 = "select max(moy),name,lastname from v1";                
                res=stat.executeQuery(r9);
                
                if(res.next()){
                    jTextField14.setText("name"+res.getString("name")+" "+res.getString("lastname"));   
                    jTextField15.setText("stars : "+String.format( "%.2f",Double.parseDouble(res.getString("max(moy)")))+"/5");
                    System.out.println(res.getString("name"));
                }
                
                String r10 = "create or replace view v2(nbr,name,lastname) as select count(*) ,e.name,e.lastname from contract as c,employee as e where c.id_employee=e.id_employee and service=? and c.date like ? group by(e.id_employee)";                
                PreparedStatement stmt10 = con.prepareStatement(r10);  
                stmt10.setString(1,ser);
                stmt10.setString(2,dt);
                stmt10.executeUpdate();
                String r11 = "select max(nbr),name,lastname from v2";                
                res=stat.executeQuery(r11);
                if(res.next()){
                    jTextField12.setText("name:"+res.getString("name")+" "+res.getString("lastname"));
                    jTextField13.setText("had "+res.getString("max(nbr)")+" contracts");               
                }
                
                         DefaultTableModel model = (DefaultTableModel) tt.getModel();
                         model.setRowCount(0);
                         
                String sql = "select id_employee, name, lastname from employee where service=? ";
                PreparedStatement stmt = con.prepareStatement(sql);  
                stmt.setString(1,ser);
                ResultSet rs = stmt.executeQuery();
                       
               while(rs.next()){
                        String id   = rs.getString("id_employee");
                        String nm   = rs.getString("name");
                        String lt   = rs.getString("lastname");
                        String d="0",e="0",f="0";
                        String sq2 = "select count(idc)  ,sum(priceC) from contract as c,employee as e where c.id_employee=e.id_employee and e.id_employee=? and c.date like ?";
                        PreparedStatement stmt1 = con.prepareStatement(sq2);  
                        stmt1.setString(1,id);
                        stmt1.setString(2,dt);
                        ResultSet rs2 = stmt1.executeQuery();
                        if(rs2.next()){
                            d=rs2.getString("count(idc)");
                            e=rs2.getString("sum(priceC)");
                        }
                        if(d.equals("0")){e="-";f="-";}
                        else{
                            String sq3 = "select sum(stars)/count(c.id_employee) from opinion as o,contract as c where o.idC=c.idC and c.id_employee=? and c.date like ?";
                            PreparedStatement stmt3 = con.prepareStatement(sq3);  
                            stmt3.setString(1,id);
                            stmt3.setString(2,dt);
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
        jTextField2 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tt = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(153, 153, 153));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/stat1.png"))); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel20.setText("Statistic of service :");

        jLabel1.setFont(new java.awt.Font("Snap ITC", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/return.png"))); // NOI18N
        jLabel1.setText("Return");

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
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addContainerGap())
        );

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

        jTextField6.setBackground(new java.awt.Color(255, 0, 102));
        jTextField6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/budget.png"))); // NOI18N
        jTextField6.setText("jLabel1");
        jTextField6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField7.setBackground(new java.awt.Color(255, 0, 102));
        jTextField7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTextField7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/money-bag.png"))); // NOI18N
        jTextField7.setText("jLabel1");
        jTextField7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jTextField7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

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

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/podium.png"))); // NOI18N
        jLabel10.setText("The Biggest Activist");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTextField12.setText("jLabel1");

        jTextField13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTextField13.setText("jLabel1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel10))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField12)
                .addGap(1, 1, 1)
                .addComponent(jTextField13)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin/user.png"))); // NOI18N
        jLabel11.setText("favorite employee");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jTextField14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTextField14.setText("jLabel1");

        jTextField15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jTextField15.setText("jLabel1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(19, 19, 19))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(17, 17, 17)
                .addComponent(jTextField14)
                .addGap(1, 1, 1)
                .addComponent(jTextField15)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel2.setText("Statistic in period");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Year", "2018", "2019", "2020" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jButton1.setText("Show");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Employees");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ttMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ttMouseClicked
           
    }//GEN-LAST:event_ttMouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String selectedItem = (String) jComboBox1.getSelectedItem();
        if("Year".equals(selectedItem)){selectedItem="" ;System.out.print("qskdjf");
        StatisticsOfService sos = new StatisticsOfService(sr,"","");
                        sos.setVisible(true);
                        setVisible(false);}
        else{
            String selectedItem2 = (String) jComboBox2.getSelectedItem();
        if("Month".equals(selectedItem2)){selectedItem2="" ;}
        StatisticsOfService sos = new StatisticsOfService(sr,selectedItem,selectedItem2);
                        sos.setVisible(true);
                        setVisible(false);
        
    }//GEN-LAST:event_jButton1ActionPerformed
    }
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
            java.util.logging.Logger.getLogger(StatisticsOfService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StatisticsOfService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StatisticsOfService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StatisticsOfService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StatisticsOfService("1","","").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jTextField12;
    private javax.swing.JLabel jTextField13;
    private javax.swing.JLabel jTextField14;
    private javax.swing.JLabel jTextField15;
    private javax.swing.JLabel jTextField2;
    private javax.swing.JLabel jTextField3;
    private javax.swing.JLabel jTextField6;
    private javax.swing.JLabel jTextField7;
    private javax.swing.JTable tt;
    // End of variables declaration//GEN-END:variables
}
