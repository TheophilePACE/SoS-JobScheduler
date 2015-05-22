/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jobscheduler.converter.tws.gui;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.antlr.runtime.RecognitionException;
import org.jobscheduler.converter.tws.Launcher;
import org.jobscheduler.converter.tws.TWSJobStream;

/**
 *
 * @author fferchic
 */
public class MigrationGUI extends javax.swing.JFrame {

    /**
     * Creates new form smecta
     */
    public MigrationGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        testSelected = new javax.swing.JCheckBox();
        jobsPath = new javax.swing.JTextField();
        schedulesPath = new javax.swing.JTextField();
        variablesPath = new javax.swing.JTextField();
        calendarPath = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel5 = new javax.swing.JLabel();
        targetPath = new javax.swing.JTextField();
        startButton = new javax.swing.JButton();
        jobsButton = new javax.swing.JButton();
        scheduleButton = new javax.swing.JButton();
        variablesButton = new javax.swing.JButton();
        calendarButton = new javax.swing.JButton();
        targetButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("TWS Jobs File Path");

        jLabel2.setText("TWS Schedules File Path");

        jLabel3.setText("TWS Variables File Path");

        jLabel4.setText("TWS Calendar File Path ");

        testSelected.setText("Test");
        testSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectedActionPerformed(evt);
            }
        });

        jobsPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jobsPathActionPerformed(evt);
            }
        });

        schedulesPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schedulesPathActionPerformed(evt);
            }
        });

        variablesPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variablesPathActionPerformed(evt);
            }
        });

        calendarPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calendarPathActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0));

        jEditorPane1.setBackground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jEditorPane1);

        jLabel5.setText("Target Directory");

        startButton.setText("Start Migration");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jobsButton.setText("choose File");
        jobsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jobsButtonActionPerformed(evt);
            }
        });

        scheduleButton.setText("choose File");
        scheduleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scheduleButtonActionPerformed(evt);
            }
        });

        variablesButton.setText("choose File");
        variablesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                variablesButtonActionPerformed(evt);
            }
        });

        calendarButton.setText("choose File");
        calendarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calendarButtonActionPerformed(evt);
            }
        });

        targetButton.setText("choose Directory");
        targetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("  © 2014 AF-C2T");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jobsPath, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                            .addComponent(calendarPath)
                            .addComponent(schedulesPath)
                            .addComponent(variablesPath)
                            .addComponent(targetPath))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jobsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scheduleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(variablesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(calendarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(targetButton, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(testSelected)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(startButton)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jobsPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jobsButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(schedulesPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scheduleButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(variablesPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(variablesButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(calendarPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calendarButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(targetPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(targetButton))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(testSelected)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(startButton)
                        .addGap(4, 4, 4)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void testSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testSelectedActionPerformed
     this.test = true ;   
    }

    private void variablesPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variablesPathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_variablesPathActionPerformed

    private void jobsPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jobsPathActionPerformed
        
    }//GEN-LAST:event_jobsPathActionPerformed

    private void schedulesPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schedulesPathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_schedulesPathActionPerformed

    private void calendarPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calendarPathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_calendarPathActionPerformed

    private void jobsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jobsButtonActionPerformed
        JFileChooser choix = new JFileChooser();
        int retour=choix.showOpenDialog(null);
         if(retour==JFileChooser.APPROVE_OPTION){
                   String path =  choix.getSelectedFile().getAbsolutePath();
         
                   jobsPath.setText(path); 
                   
                   jEditorPane1.setText(jEditorPane1.getText().concat("> Selected Job File : "+path+"\n") );
          
         } 
    }//GEN-LAST:event_jobsButtonActionPerformed

    private void scheduleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scheduleButtonActionPerformed
        JFileChooser choix = new JFileChooser();
        int retour=choix.showOpenDialog(null);
         if(retour==JFileChooser.APPROVE_OPTION){
                   schedulesPath.setText( choix.getSelectedFile().getAbsolutePath());               
          }
    }//GEN-LAST:event_scheduleButtonActionPerformed

    private void variablesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_variablesButtonActionPerformed
        JFileChooser choix = new JFileChooser();
        int retour=choix.showOpenDialog(null);
         if(retour==JFileChooser.APPROVE_OPTION){
                   variablesPath.setText( choix.getSelectedFile().getAbsolutePath());               
          }
    }//GEN-LAST:event_variablesButtonActionPerformed

    private void calendarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calendarButtonActionPerformed
        JFileChooser choix = new JFileChooser();
        int retour=choix.showOpenDialog(null);
         if(retour==JFileChooser.APPROVE_OPTION){
                   calendarPath.setText( choix.getSelectedFile().getAbsolutePath());               
          }
    }//GEN-LAST:event_calendarButtonActionPerformed

    private void targetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetButtonActionPerformed
        JFileChooser choix = new JFileChooser();
        choix.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retour=choix.showOpenDialog(null);
         if(retour==JFileChooser.APPROVE_OPTION){
                   targetPath.setText( choix.getSelectedFile().getAbsolutePath());               
          }
    }//GEN-LAST:event_targetButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
    	try{
    	Launcher.parseJobs(jobsPath.getText(),this.test) ;
		Launcher.getVariables(this.variablesPath.getText()) ;
		Launcher.setBseDir(targetPath.getText()+"/" ) ;
			Launcher.streams = Launcher.parseSchedule(schedulesPath.getText()) ;

		Launcher.logger.info(Launcher.every_orders +" every order generated") ;
		for(TWSJobStream stream : Launcher.streams.values()){
			if(stream.prevChains.size() >0)
			stream.setPreviousChains() ;
		}
		
		for(String jobId : Launcher.jobs.keySet()){
			Launcher.marshallJob(jobId, Launcher.jobs.get(jobId)) ;
		}
		
		for(TWSJobStream stream : Launcher.streams.values()){
			Launcher.generateJobChain(stream, stream.id.split("#")[0]) ;
		}
		Launcher.marshalSubmitEvents() ;
		Launcher.marshalCheckEvents() ;
		Launcher.marshalEveryCheckEvents() ;
		Launcher.marshalEverySubmitEvents() ;
		System.out.println("migration finished") ;
		jEditorPane1.setText(jEditorPane1.getText().concat("> Migration finished"+"\n") );
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (RecognitionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }//GEN-LAST:event_startButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MigrationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MigrationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MigrationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MigrationGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MigrationGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calendarButton;
    private javax.swing.JTextField calendarPath;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jobsButton;
    private javax.swing.JTextField jobsPath;
    private javax.swing.JButton scheduleButton;
    private javax.swing.JTextField schedulesPath;
    private javax.swing.JButton startButton;
    private javax.swing.JButton targetButton;
    private javax.swing.JTextField targetPath;
    private javax.swing.JCheckBox testSelected;
    private javax.swing.JButton variablesButton;
    private javax.swing.JTextField variablesPath;
    private boolean test ;
    // End of variables declaration//GEN-END:variables
}