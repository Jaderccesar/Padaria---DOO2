/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.ClientController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Client;
import model.Product;

/**
 *
 * @author Daniel Coelho
 */
public class ClientListView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ClientListView.class.getName());
    private final ClientController clientController = new ClientController();
    
    /**
     * Creates new form ClientListView
     */
    public ClientListView() {
        initComponents();
        loadClients();
        
        tCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int column = tCliente.columnAtPoint(evt.getPoint());
            int row = tCliente.rowAtPoint(evt.getPoint());

            if (column == 0) { 
                editProduct(row); 
            }
        }});
        
    }

    private void editProduct(int rowIndex) {
        
        try {

        int productId = (int) tCliente.getValueAt(rowIndex, 1); 
           
        Client clientToEdit = clientController.findById(productId); 
        
        if (clientToEdit != null) {
            new ClientView(clientToEdit).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Produto com ID " + productId + " não encontrado.", "Erro de Busca", JOptionPane.ERROR_MESSAGE);
        }
        
        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(this, "Erro interno: ID da tabela não é um número válido. " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar produto: " + e.getMessage(), "Erro de Persistência", JOptionPane.ERROR_MESSAGE);
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
        btnNovoCliente = new javax.swing.JButton();
        spCliente = new javax.swing.JScrollPane();
        tCliente = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblCodigo.setText("Código");

        lblNome.setText("Nome");

        lblCPF.setText("CPF");

        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        btnNovoCliente.setText("+ Novo Cliente");
        btnNovoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoClienteActionPerformed(evt);
            }
        });

        tCliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tCliente.getTableHeader().setReorderingAllowed(false);
        spCliente.setViewportView(tCliente);

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
                        .addComponent(btnNovoCliente))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblCodigo)
                        .addGap(35, 35, 35)
                        .addComponent(lblNome)
                        .addGap(164, 164, 164)
                        .addComponent(lblCPF))
                    .addComponent(spCliente))
                .addContainerGap(17, Short.MAX_VALUE))
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
                    .addComponent(btnNovoCliente))
                .addGap(18, 18, 18)
                .addComponent(spCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        filterProducts();
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnNovoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoClienteActionPerformed
        java.awt.EventQueue.invokeLater(() -> new ClientView().setVisible(true));
    }//GEN-LAST:event_btnNovoClienteActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ClientListView().setVisible(true));
    }
    
    private void filterProducts() {
        
        int codFilter = 0;
             
        try{
            
            if (! ftfCodigo.getText().isEmpty()){
                codFilter = Integer.parseInt(ftfCodigo.getText());
            }
            
            createTable(clientController.filterClients(codFilter, ftfNome.getText(), ftfCPF.getText()));
            
        } catch (NumberFormatException e) {
            
            JOptionPane.showMessageDialog(this, "Erro: Certifique-se de que código, é um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);

        } 
        
    }
    
    private void loadClients() {
       
        createTable(clientController.findAll());
                
    } 
    
    private void createTable(List<Client> listClients){
    
        DefaultTableModel modelo = (DefaultTableModel) tCliente.getModel();
        modelo.setRowCount(0);
       
        for (Client client : listClients) {
            modelo.addRow(new Object[] {
                "Editar",
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getPhone(),
                client.getTotalPoints()
            });
        } 
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnNovoCliente;
    private javax.swing.JFormattedTextField ftfCPF;
    private javax.swing.JFormattedTextField ftfCodigo;
    private javax.swing.JFormattedTextField ftfNome;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JScrollPane spCliente;
    private javax.swing.JTable tCliente;
    // End of variables declaration//GEN-END:variables
}
