package casino.vista;
import javax.swing.JButton;
import javax.swing.JMenuItem;

public class VentanaJuego extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaJuego.class.getName());


    public VentanaJuego() {
        initComponents();
        this.setLocationRelativeTo(null);
    }


     // Botón principal para avanzar
    public JButton getBtnAvanzar() {
        return btnAvanzar;
    }

    // --- Ítems del Menú "Partida" ---
    public JMenuItem getMenuItemPausar() {
        return menuItemPausar;
    }

    public JMenuItem getMenuItemGuardar() {
        return menuItemGuardar;
    }
    
    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    // --- Ítems del Menú "Ver" ---
    public JMenuItem getMenuItemRanking() {
        return menuItemRanking;
    }
    
    public JMenuItem getMenuItemHistorial() {
        return menuItemHistorial;
    }

    public JMenuItem getMenuItemEstadisticas() {
        return menuItemEstadisticas;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlInfoPartida = new javax.swing.JPanel();
        pnlJugadores = new javax.swing.JPanel();
        pnlLogEventos = new javax.swing.JPanel();
        btnAvanzar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemPausar = new javax.swing.JMenuItem();
        menuItemGuardar = new javax.swing.JMenuItem();
        menuItemSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuItemRanking = new javax.swing.JMenuItem();
        menuItemHistorial = new javax.swing.JMenuItem();
        menuItemEstadisticas = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlInfoPartida.setBackground(java.awt.SystemColor.inactiveCaption);

        javax.swing.GroupLayout pnlInfoPartidaLayout = new javax.swing.GroupLayout(pnlInfoPartida);
        pnlInfoPartida.setLayout(pnlInfoPartidaLayout);
        pnlInfoPartidaLayout.setHorizontalGroup(
            pnlInfoPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlInfoPartidaLayout.setVerticalGroup(
            pnlInfoPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        pnlJugadores.setBackground(java.awt.SystemColor.activeCaption);

        javax.swing.GroupLayout pnlJugadoresLayout = new javax.swing.GroupLayout(pnlJugadores);
        pnlJugadores.setLayout(pnlJugadoresLayout);
        pnlJugadoresLayout.setHorizontalGroup(
            pnlJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlJugadoresLayout.setVerticalGroup(
            pnlJugadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );

        pnlLogEventos.setBackground(java.awt.SystemColor.inactiveCaption);

        btnAvanzar.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Red"));
        btnAvanzar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAvanzar.setForeground(new java.awt.Color(255, 255, 255));
        btnAvanzar.setText("Lanzar Dados / Siguiente Ronda");
        btnAvanzar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnAvanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvanzarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLogEventosLayout = new javax.swing.GroupLayout(pnlLogEventos);
        pnlLogEventos.setLayout(pnlLogEventosLayout);
        pnlLogEventosLayout.setHorizontalGroup(
            pnlLogEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogEventosLayout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(btnAvanzar, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(254, Short.MAX_VALUE))
        );
        pnlLogEventosLayout.setVerticalGroup(
            pnlLogEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogEventosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAvanzar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jMenu1.setText("Partida");

        menuItemPausar.setText("Pausa");
        jMenu1.add(menuItemPausar);

        menuItemGuardar.setText("Guardar Partida");
        jMenu1.add(menuItemGuardar);

        menuItemSalir.setText("Salir de la Partida");
        jMenu1.add(menuItemSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ver");

        menuItemRanking.setText("Ranking Actual");
        jMenu2.add(menuItemRanking);

        menuItemHistorial.setText("Historial de Partidas");
        jMenu2.add(menuItemHistorial);

        menuItemEstadisticas.setText("Estadísticas Generales");
        jMenu2.add(menuItemEstadisticas);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInfoPartida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlLogEventos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlJugadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlInfoPartida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLogEventos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAvanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvanzarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAvanzarActionPerformed


    
    
    
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VentanaJuego().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvanzar;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem menuItemEstadisticas;
    private javax.swing.JMenuItem menuItemGuardar;
    private javax.swing.JMenuItem menuItemHistorial;
    private javax.swing.JMenuItem menuItemPausar;
    private javax.swing.JMenuItem menuItemRanking;
    private javax.swing.JMenuItem menuItemSalir;
    private javax.swing.JPanel pnlInfoPartida;
    private javax.swing.JPanel pnlJugadores;
    private javax.swing.JPanel pnlLogEventos;
    // End of variables declaration//GEN-END:variables
}
