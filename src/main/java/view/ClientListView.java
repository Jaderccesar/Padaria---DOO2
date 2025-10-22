/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.ClientController;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Client;

/**
 * **ClientListView.java**
 * * Esta classe representa a tela de listagem de clientes (JFrame). 
 * Ela exibe uma tabela com todos os clientes e permite operações de 
 * busca (filtro), adição, edição e remoção de clientes.
 *
 */
public class ClientListView extends javax.swing.JFrame {
    
    // Logger para fins de depuração e registro de erros.
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ClientListView.class.getName());
    
    // Instância do controlador para interagir com a lógica de negócio dos clientes.
    private final ClientController clientController = new ClientController();
    
    /**
     * Construtor padrão da classe ClientListView.
     * Inicializa os componentes da GUI, carrega os dados dos clientes 
     * e aplica estilos visuais.
     */
    public ClientListView() {
        initComponents();
        loadClients();
        aplicarEstilo();
        
        // Adiciona um listener de mouse à tabela para capturar cliques nas colunas 
        // de "Editar" (coluna 0) e "Remover" (coluna 1).
        tCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int column = tCliente.columnAtPoint(evt.getPoint());
            int row = tCliente.rowAtPoint(evt.getPoint());

            if (column == 0) { 
                editClient(row); 
            } else if (column == 1) { 
                removeClient(row);
            }
        }});
        
    }

    //Abre a tela de edição do cliente selecionado.
    private void editClient(int rowIndex) {
        
        try {

        // O ID do cliente está na terceira coluna (índice 2) da tabela.
        int clientId = (int) tCliente.getValueAt(rowIndex, 2); 
           
        // Busca o objeto Client completo usando o ID obtido.
        Client clientToEdit = clientController.findById(clientId); 
        
        if (clientToEdit != null) {
            // Abre a tela ClientView, passando o objeto Client para edição.
            new ClientView(clientToEdit).setVisible(true);
        } else {
            // Exibe uma mensagem de erro se o cliente não for encontrado.
            JOptionPane.showMessageDialog(this, "Erro: Produto com ID " + clientId + " não encontrado.", "Erro de Busca", JOptionPane.ERROR_MESSAGE);
        }
        
        } catch (ClassCastException e) {
            // Tratamento de erro para quando o valor da célula não é um inteiro.
            JOptionPane.showMessageDialog(this, "Erro interno: ID da tabela não é um número válido. " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            // Tratamento de erros de persistência (ex: falha de conexão com o banco).
            JOptionPane.showMessageDialog(this, "Erro ao buscar produto: " + e.getMessage(), "Erro de Persistência", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Remove o cliente selecionado após confirmação do usuário.
    private void removeClient(int rowIndex){
    
        // Obtém o ID do cliente da coluna 2.
        int clientId = (int) tCliente.getValueAt(rowIndex, 2); 
        
        // Solicita confirmação ao usuário.
        int confirm = JOptionPane.showConfirmDialog(this, 
        "Tem certeza que deseja remover o cliente ID " + clientId + "?", 
        "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Chama o controlador para realizar a exclusão.
                clientController.deleteClient(clientId); 
                loadClients();// Recarrega a lista para atualizar a tabela.
                JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!");
            } catch (Exception e) {
                // Tratamento de erros durante o processo de remoção.
                JOptionPane.showMessageDialog(this, "Erro ao remover: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } 
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
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Editar", "Remover", "Código", "Nome", "CPF", "Telefone", "Pontos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        if (tCliente.getColumnModel().getColumnCount() > 0) {
            tCliente.getColumnModel().getColumn(0).setMaxWidth(60);
            tCliente.getColumnModel().getColumn(1).setMaxWidth(60);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(spCliente)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(lblCPF))))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
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
                .addComponent(spCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        filterClients();
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
    
    /**
     * Realiza a filtragem dos clientes com base nos campos de filtro da tela.
     */
    private void filterClients() {
        
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
    
    /**
     * Carrega todos os clientes e preenche a tabela.
     */
    private void loadClients() {
       
        createTable(clientController.findAll());
                
    } 
    
    /**
     * Preenche a JTable com a lista de objetos Client fornecida.
     */
    private void createTable(List<Client> listClients){
    
        DefaultTableModel modelo = (DefaultTableModel) tCliente.getModel();
        modelo.setRowCount(0);
       
        for (Client client : listClients) {
            modelo.addRow(new Object[] {
                "<html>✏️️</html>",
                "<html>❌</html>",
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getPhone(),
                client.getTotalPoints()
            });
        } 
    }
    
    /**
     * Aplica um estilo visual personalizado aos componentes da tela (cores, bordas).
     */
    private void aplicarEstilo() {

        getContentPane().setBackground(new Color(250, 245, 235)); 

        lblCodigo.setForeground(new Color(90, 70, 50));
        lblNome.setForeground(new Color(90, 70, 50));
        lblCPF.setForeground(new Color(90, 70, 50));

        ftfCodigo.setBackground(new Color(255, 252, 245));
        ftfCodigo.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 150)));

        ftfNome.setBackground(new Color(255, 252, 245));
        ftfNome.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 150)));

        ftfCPF.setBackground(new Color(255, 252, 245));
        ftfCPF.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 150)));

        estilizarBotao(btnFiltrar);
        estilizarBotao(btnNovoCliente);

        tCliente.setBackground(new Color(255, 250, 240));
        tCliente.setForeground(new Color(60, 60, 60));
        tCliente.setSelectionBackground(new Color(200, 180, 150));
        tCliente.setSelectionForeground(Color.BLACK);
        tCliente.setGridColor(new Color(220, 210, 200));
    }
    
    /**
     * Aplica um estilo padrão e efeitos de hover a um JButton.
     */
    private void estilizarBotao(JButton botao) {
        Color corNormal = new Color(180, 150, 120);
        Color corHover = new Color(160, 130, 100);

        botao.setBackground(corNormal);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                botao.setBackground(corHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                botao.setBackground(corNormal);
            }
        });
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
