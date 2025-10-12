/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.SellController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sell;

/**
 *
 * @author Daniel Coelho
 */
public class SellListView extends javax.swing.JFrame {

   /* private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SellListView.class.getName());
    private final SellController sellController = new SellController();

    public SellListView() {
        initComponents();
        loadSells();

        tSell.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = tSell.columnAtPoint(evt.getPoint());
                int row = tSell.rowAtPoint(evt.getPoint());

                if (column == 0) {
                    editSell(row);
                }
            }
        });

    }

    private void editSell(int rowIndex) {

        try {

            int sellId = (int) tSell.getValueAt(rowIndex, 1);

            Sell sellToEdit = sellController.findById(sellId);

            if (sellToEdit != null) {
                //new SellView(sellToEdit).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erro: Venda com ID " + sellId + " não encontrado.", "Erro de Busca", JOptionPane.ERROR_MESSAGE);
            }

        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(this, "Erro interno: ID da tabela não é um número válido. " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar venda: " + e.getMessage(), "Erro de Persistência", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCodigo = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblCPF = new javax.swing.JLabel();
        ftfCodigo = new javax.swing.JFormattedTextField();
        ftfNome = new javax.swing.JFormattedTextField();
        ftfCPF = new javax.swing.JFormattedTextField();
        btnFiltrar = new javax.swing.JButton();
        btnNovaVenda = new javax.swing.JButton();
        spVenda = new javax.swing.JScrollPane();
        tSell = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblCodigo.setText("Código");

        lblNome.setText("Nome");

        lblCPF.setText("CPF");

        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        btnNovaVenda.setText("+ Nova Venda");
        btnNovaVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaVendaActionPerformed(evt);
            }
        });

        tSell.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Ação", "Código", "Nome", "CPF", "Telefone", "Pontos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tSell.getTableHeader().setReorderingAllowed(false);
        spVenda.setViewportView(tSell);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ftfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ftfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ftfCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(btnFiltrar)
                        .addGap(18, 18, 18)
                        .addComponent(btnNovaVenda))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCodigo)
                        .addGap(35, 35, 35)
                        .addComponent(lblNome)
                        .addGap(164, 164, 164)
                        .addComponent(lblCPF))
                    .addComponent(spVenda))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblNome)
                    .addComponent(lblCPF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar)
                    .addComponent(btnNovaVenda))
                .addGap(18, 18, 18)
                .addComponent(spVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        filterSells();
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnNovaVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaVendaActionPerformed
        java.awt.EventQueue.invokeLater(() -> new SellView().setVisible(true));
    }//GEN-LAST:event_btnNovaVendaActionPerformed

    /**
     * @param args the command line arguments
     */
   /* public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            //logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> new SellListView().setVisible(true));
    }

    private void filterSells() {

        int codFilter = 0;

        try {
            if (!ftfCodigo.getText().isEmpty()) {
                codFilter = Integer.parseInt(ftfCodigo.getText());
            }

            String clientName = ftfNome.getText();
            //String date = ftfData.getText();

            createTable(SellController.filterSells(codFilter, clientName));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código inválido. Digite apenas números.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSells() {
        createTable(SellController.findAll());
    }

    private void createTable(List<Sell> listSells) {
        DefaultTableModel modelo = (DefaultTableModel) tSell.getModel();
        modelo.setRowCount(0);

        for (Sell s : listSells) {
            modelo.addRow(new Object[]{
                "Editar",
                s.getId(),
                s.getClient().getName(),
                s.getSellDate(),
                s.getPrice(),
                s.getTotal()
            });
        }
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnNovaVenda;
    private javax.swing.JFormattedTextField ftfCPF;
    private javax.swing.JFormattedTextField ftfCodigo;
    private javax.swing.JFormattedTextField ftfNome;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JScrollPane spVenda;
    private javax.swing.JTable tSell;
    // End of variables declaration//GEN-END:variables
}
