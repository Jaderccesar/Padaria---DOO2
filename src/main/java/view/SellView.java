
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.ClientController;
import controller.ProductController;
import controller.SellController;
import dao.ClientDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Client;
import model.Product;
import model.Sell;

/**
 *
 * @author Usuario
 */
public class SellView extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SellView.class.getName());
    private final ClientController clientController = new ClientController();
    private final ProductController productController = new ProductController();
    private final SellController sellController = new SellController();
    
    private Client currentClient = null;
    private List<Product> selectedProducts = new ArrayList<>();

    /**
     * Creates new form SellView
     */
     public SellView() {
       initComponents();
       DefaultTableModel model = (DefaultTableModel) tProduto1.getModel();
       model.setRowCount(0); 
       loadAvailableProducts(productController.findAll());
       aplicarEstilo();
    }
      
    //private JTextField tfCPF;
    private JTable tAvailable, tSelected;
    private JLabel lbClientInfo;
    
     private void buscarCliente() {
        try {
            String cpf = tfCPF.getText().trim();

            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o CPF do cliente.");
                return;
            }

            Client c = clientController.findByCpf(cpf);
            if (c != null) {
                currentClient = c;
                lbClienteNome.setText("Cliente: " + c.getName() + " | Pontos: " + c.getTotalPoints());
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao buscar cliente", e);
            JOptionPane.showMessageDialog(this, "Erro ao buscar cliente: " + e.getMessage());
        }
    }

     
     
     private void loadAvailableProducts(List<Product> list) {
        try {
            DefaultTableModel model = (DefaultTableModel) tProduto.getModel();
            model.setRowCount(0);

            for (Product p : list) {
                model.addRow(new Object[]{   
                    p.getId(),
                    p.getName(),
                    p.getType(),
                    p.getPrice(),
                    p.getStockQuantity(),
                    p.getPointCost()
                });
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao carregar produtos", e);
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }
     
     public SellView(Sell sell) {
        this(); // Chama o construtor padrão para inicializar componentes
        if (sell != null) {
            // Preenche o cliente
            currentClient = sell.getClient();
            lbClienteNome.setText("Cliente: " + currentClient.getName() + " | Pontos: " + currentClient.getTotalPoints());

            // Preenche os produtos selecionados
            selectedProducts.clear();
            DefaultTableModel model = (DefaultTableModel) tProduto1.getModel();
            model.setRowCount(0);

            for (Product p : sell.getProducts()) { // Supondo que você tenha um método getProducts() que retorna List<Product> da venda
                selectedProducts.add(p);

                model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getType(),
                    p.getPrice() * p.getSoldQuantity(),
                    p.getSoldQuantity(),
                    p.getPrice() * p.getSoldQuantity() / 10
                });
            }

            atualizarValorCompra();

            // Preenche a tabela de produtos disponíveis, atualizando estoque
            loadAvailableProducts(productController.findAll());
            for (Product p : selectedProducts) {
                // Remove do estoque disponível os produtos já selecionados
                for (int row = 0; row < tProduto.getRowCount(); row++) {
                    int prodId = (int) tProduto.getValueAt(row, 0);
                    if (prodId == p.getId()) {
                        int estoqueAtual = ((Number) tProduto.getValueAt(row, 4)).intValue();
                        tProduto.setValueAt(estoqueAtual - p.getSoldQuantity(), row, 4);
                    }
                }
            }
        }
    }

    
    private void adicionarProduto() {
        int row = tProduto.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para adicionar.");
            return;
        }

        int id = (int) tProduto.getValueAt(row, 0);
        String name = (String) tProduto.getValueAt(row, 1);
        String type = (String) tProduto.getValueAt(row, 2);
        double price = (double) tProduto.getValueAt(row, 3);
        int stock = ((Number) tProduto.getValueAt(row, 4)).intValue();
        
        if (stock <= 0) {
            JOptionPane.showMessageDialog(this, "Produto esgotado! Não é possível adicionar.");
            return;
        }

        String qtdStr = JOptionPane.showInputDialog("Informe a quantidade:");
        if (qtdStr == null || qtdStr.isEmpty()) return;

        int qtd = Integer.parseInt(qtdStr);
        
        if (qtd <= 0) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            return;
        }
        
        if (qtd > stock) {
            JOptionPane.showMessageDialog(this, "Quantidade solicitada maior que o estoque disponível.");
            return;
        }

        int newStock = stock - qtd;
        
        tProduto.setValueAt(newStock, row, 4);

        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setPrice(price);
        p.setType(type);
        p.setSoldQuantity(qtd); 
        p.setStockQuantity(newStock); 

        selectedProducts.add(p);

        DefaultTableModel model = (DefaultTableModel) tProduto1.getModel();
        model.addRow(new Object[]{
            id,
            name,
            type,
            price * qtd,
            qtd,
            price * qtd / 10
        });

        atualizarValorCompra();
    }
    
    private void removerProduto() {
        int row = tProduto1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.");
            return;
        }

        int id = (int) tProduto1.getValueAt(row, 0);
        int qtdRemover = (int) tProduto1.getValueAt(row, 4);
        
        selectedProducts.removeIf(p -> p.getId() == id);

        DefaultTableModel modelAvailable = (DefaultTableModel) tProduto.getModel();
        for (int i = 0; i < modelAvailable.getRowCount(); i++) {
            int prodId = (int) modelAvailable.getValueAt(i, 0);
            if (prodId == id) {
                int currentStock = (int) modelAvailable.getValueAt(i, 4);
                modelAvailable.setValueAt(currentStock + qtdRemover, i, 4);
                break;
            }
        }

        DefaultTableModel modelSelected = (DefaultTableModel) tProduto1.getModel();
        modelSelected.removeRow(row);

        atualizarValorCompra();
    }
    
    private void atualizarValorCompra() {
        double total = selectedProducts.stream().mapToDouble(p -> p.getPrice() * p.getSoldQuantity()).sum();
        lvValorCompra.setText("Valor da Compra: R$ " + String.format("%.2f", total));
    }
    
    private void finalizarVenda() {
        if (currentClient == null) {
            JOptionPane.showMessageDialog(this, "Busque um cliente antes de finalizar a venda.");
            return;
        }

        if (selectedProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto selecionado.");
            return;
        }

        try {
            double total = selectedProducts.stream()
                    .mapToDouble(p -> p.getPrice() * p.getSoldQuantity())
                    .sum();

            // Pergunta ao usuário a forma de pagamento
            String[] options = {"Dinheiro", "Pontos"};
            int choice = JOptionPane.showOptionDialog(this,
                    "Como deseja pagar a compra?\nTotal: R$ " + String.format("%.2f", total),
                    "Forma de Pagamento",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == -1) {
                return;
            }

            if (choice == 1) { 
                int requiredPoints = (int) (total / 10);
                int clientPoints = currentClient.getTotalPoints() != null ? currentClient.getTotalPoints() : 0;

                if (clientPoints < requiredPoints) {
                    JOptionPane.showMessageDialog(this,
                            "Cliente não possui pontos suficientes.\n" +
                            "Total necessário: " + requiredPoints + " | Possui: " + clientPoints);
                    return;
                } else {
                    currentClient.setTotalPoints(clientPoints - (requiredPoints * 2));
                    ClientDAO clientDAO = new ClientDAO();
                    clientDAO.updateTotalPoints(currentClient);
                }
            }

            Sell newSell = new Sell();
            newSell.setClient(currentClient);
            newSell.setTotal(total);
            newSell.setPrice(total);
            newSell.setSellDate(new java.util.Date());

            int sellId = sellController.save(newSell);

            if (choice == 0) {
                sellController.applyLoyaltyPoints(newSell);
            }

            for (Product p : selectedProducts) {
                int qtdVendida = p.getSoldQuantity();
                double valorUnitario = p.getPrice();

                sellController.addProductToSell(sellId, p, qtdVendida);
            }

            for (Product p : selectedProducts) {
                int qtdVendida = p.getSoldQuantity() + p.getSoldQuantity();
                productController.updateStock(p.getId(), qtdVendida);
            }

            JOptionPane.showMessageDialog(this, "Venda registrada com sucesso!");
            dispose();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao finalizar venda", e);
            JOptionPane.showMessageDialog(this, "Erro ao registrar venda: " + e.getMessage());
        }
    }

    
    private void filterProducts() {

        String tipoSelecionado = (String) cmbTipo.getSelectedItem();
        int codFilter = 0;

        try {

            if (!ftfCodigoProduto.getText().isEmpty()) {
                codFilter = Integer.parseInt(ftfCodigoProduto.getText());
            }

            if (tipoSelecionado.equals("Todos")) {
                tipoSelecionado = "";
            }

            loadAvailableProducts(productController.filterProducts(codFilter, ftfNomeProduto.getText(), tipoSelecionado));

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this, "Erro: Certifique-se de que código, é um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void aplicarEstilo() {
        getContentPane().setBackground(new Color(245, 240, 230));

        aplicarCoresComponentes();
    }

    private void aplicarCoresComponentes() {
        getContentPane().setBackground(new Color(245, 240, 230));

        estilizarBotao(btnAdicionar, new Color(193, 154, 107)); // marrom claro
        estilizarBotao(btnRemover, new Color(193, 154, 107));
        estilizarBotao(btnFiltrar, new Color(180, 140, 90));
        estilizarBotao(btnFinalizar, new Color(160, 130, 90));
        estilizarBotao(btnPesquisarCliente, new Color(180, 150, 110));

        estilizarTabela(tProduto);
        estilizarTabela(tProduto1);

        lbClienteNome.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbClienteNome.setForeground(new Color(60, 45, 30));

        lvValorCompra.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lvValorCompra.setForeground(new Color(70, 55, 40));
    }

    private void estilizarBotao(JButton botao, Color corBase) {
        botao.setBackground(corBase);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
    }

    private void estilizarTabela(JTable tabela) {
        tabela.getTableHeader().setBackground(new Color(180, 150, 110));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setRowHeight(25);

        tabela.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(250, 247, 240) : Color.WHITE);
                    c.setForeground(new Color(60, 50, 40));
                } else {
                    c.setBackground(new Color(193, 154, 107));
                    c.setForeground(Color.WHITE);
                }

                return c;
            }
        });
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
        tfCPF = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        ftfCodigoProduto = new javax.swing.JFormattedTextField();
        ftfNomeProduto = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        btnFiltrar = new javax.swing.JButton();
        spProdutos = new javax.swing.JScrollPane();
        tProduto = new javax.swing.JTable();
        btnPesquisarCliente = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        spProdutos1 = new javax.swing.JScrollPane();
        tProduto1 = new javax.swing.JTable();
        btnFinalizar = new javax.swing.JButton();
        lbClienteNome = new javax.swing.JLabel();
        lvValorCompra = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("CPF");

        lblCodigo.setText("Código");

        lblNome.setText("Nome");

        jLabel3.setText("Tipo");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Pão salgado", "Pão doce", "Salgado", "Doce/confeitaria", "Bebida", "Frios/laticínios", "Mercearia" }));

        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        tProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "Tipo", "Valor", "Estoque", "Pontos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Long.class, java.lang.Long.class
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
        tProduto.getTableHeader().setReorderingAllowed(false);
        spProdutos.setViewportView(tProduto);

        btnPesquisarCliente.setText("Pesquisar");
        btnPesquisarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarClienteActionPerformed(evt);
            }
        });

        btnAdicionar.setText("ADICIONAR");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnRemover.setText("REMOVER");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        tProduto1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "Tipo", "Valor", "Estoque", "Pontos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Long.class, java.lang.Long.class
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
        tProduto1.getTableHeader().setReorderingAllowed(false);
        spProdutos1.setViewportView(tProduto1);

        btnFinalizar.setText("FINALIZAR COMPRA");
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });

        lbClienteNome.setText("Cliente - ");

        lvValorCompra.setText("Valor da Compra:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lvValorCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnFinalizar))
                                .addComponent(spProdutos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(tfCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnPesquisarCliente)
                                    .addGap(18, 18, 18)
                                    .addComponent(lbClienteNome, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(spProdutos1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ftfCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCodigo))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addComponent(ftfNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(btnFiltrar))
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(btnRemover)
                        .addGap(133, 133, 133)
                        .addComponent(btnAdicionar)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisarCliente)
                    .addComponent(lbClienteNome, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(lblNome)
                    .addComponent(jLabel3))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftfCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltrar))
                .addGap(18, 18, 18)
                .addComponent(spProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnRemover))
                .addGap(29, 29, 29)
                .addComponent(spProdutos1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFinalizar)
                    .addComponent(lvValorCompra))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        filterProducts();
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnPesquisarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarClienteActionPerformed
        buscarCliente();
    }//GEN-LAST:event_btnPesquisarClienteActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        adicionarProduto();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        removerProduto();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        finalizarVenda();
    }//GEN-LAST:event_btnFinalizarActionPerformed

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnPesquisarCliente;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JFormattedTextField ftfCodigoProduto;
    private javax.swing.JFormattedTextField ftfNomeProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lbClienteNome;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lvValorCompra;
    private javax.swing.JScrollPane spProdutos;
    private javax.swing.JScrollPane spProdutos1;
    private javax.swing.JTable tProduto;
    private javax.swing.JTable tProduto1;
    private javax.swing.JTextField tfCPF;
    // End of variables declaration//GEN-END:variables
}
