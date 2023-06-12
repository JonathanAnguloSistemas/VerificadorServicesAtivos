/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.angulosistemas.view;

import br.angulosistemas.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author User
 */


public class ViewAplicacao extends javax.swing.JFrame implements IViewConfiguracao    {

    private Comunica comunica;


    private String resultadoResposta;

    private String[] separadorVirgula;

    private String ativo;

    private JLabel label;

    private Object texto;

    private String serverHost;

    /**
     * Creates new form ViewAplicacao
     */
    public ViewAplicacao() {
        initComponents();

        /* Adicionando eventos aos botoes da tela */
        btnVerificacao.addActionListener( e -> carregarServicosTela());

//        addComponentListener(new ComponentListener() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//
//            }
//
//            @Override
//            public void componentMoved(ComponentEvent e) {
//
//            }
//
//            @Override
//            public void componentShown(ComponentEvent e) {
//                carregarServicosTela();
//                corNaLinha();
//
//            }
//
//            @Override
//            public void componentHidden(ComponentEvent e) {
//
//            }
//        });

    }

    public void escreverArquivo(){
        String path = "C:\\visualfs\\verificadorservicos\\servicosAtivosTeste.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Exemplo de conteúdo do arquivo de texto.");
            writer.newLine();
            writer.write("Linha 2");
            writer.newLine();
            writer.write("Linha 3");
            writer.newLine();
            // Adicione mais linhas conforme necessário

            System.out.println("Arquivo de texto foi escrito com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarServicosTela(){

        if (comunica == null) comunica = new Comunica();

        try {
          //  iViewConfiguracao.atualizarProcessoAplicacao("Aplicacao Iniciada...");
//            atualizarProcessoAplicacao("Aplicacao Iniciada...");
//            Thread.sleep(1000);

            String path = "C:\\visualfs\\verificadorservicos\\servicosAtivosTeste.txt";

            List<String> arquivo;

            arquivo = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            int tamanhoArquivo = (arquivo.size());

            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();

            atualizarProcessoAplicacao("Verificando o status dos serviços. Aguarde...");
            Main.notificatTraycon("Verificando..","Verificando a conexão dos serviços. Aguarde...", TrayIcon.MessageType.INFO);
            Thread.sleep(100);
            String linhaLida = "";
            int servicosLidos = 0;
            while ( line != null ) {



                linhaLida = line;
                separadorVirgula = linhaLida.split(",");
                serverHost = "http://";
                try {
                    resultadoResposta = comunica.callHttpGenerico(
                            new URL(serverHost + separadorVirgula[1] ),
                            HttpVerbo.METODO_GET,
                            false,
                            true,
                            HttpHeader.TEXT_PLAIN,
                            HttpHeader.APPLICATION_JSON,
                            null,
                            null,
                            8*1000,
                            8*1000
                    );
                      System.out.println( "Resultado da resposta: " + resultadoResposta);




                    if( resultadoResposta != null ){
                        ativo = "sim";
                    }

                }

                catch (Exception e){
                    ativo = "nao";

                    System.out.println("Erro ao tentar buscar objeto no webservice: " + e.getMessage());
                }


                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                // Define o tamanho preferido da coluna "Está Ativo" como menor
                TableColumn activeColumn = jTable1.getColumnModel().getColumn(1);
                activeColumn.setPreferredWidth(50);

                // Define tamanhos maiores para as outras colunas
                TableColumn nameColumn = jTable1.getColumnModel().getColumn(0);
                nameColumn.setPreferredWidth(150);

                TableColumn addressColumn = jTable1.getColumnModel().getColumn(2);
                addressColumn.setPreferredWidth(200);


                if (model.getRowCount() > 0) {
                    for (int i = jTable1.getRowCount() - tamanhoArquivo; i >= 0; i--)
                        model.removeRow(i);
                }
                    model.addRow(
                            new Object[]{
                                    separadorVirgula[0], ativo, separadorVirgula[1]
                            }
                    );

                line = br.readLine();

                servicosLidos++;
            }

            Main.notificatTraycon("Serviços ","foi testado a conexão com " + servicosLidos + " serviços, sucesso" , TrayIcon.MessageType.INFO);
            corNaLinha();

        } catch (Exception e ) {

            System.out.println("Não foi possivel ler o arquivo: " + e.getMessage() );
        }

    }


    public void corNaLinha(){

        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
               // return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                 label = (JLabel) super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);

                Color c = Color.WHITE;
                texto = table.getValueAt(row,1);

               // TableColumn tc = jTable1.getColumn(1);

                if (texto.equals("sim") ){
                    c = Color.GREEN;
                   label.setBackground(c);

                    //table.setSelectionForeground(Color.RED);
                } else {
                    c = Color.RED;
                    label.setBackground(c);
                }

                return label;
            }

        });

//        DefaultTableCellRenderer coluna = new DefaultTableCellRenderer();
//
//        System.out.println("resultado do texto " + texto);
//        if (textoo.equals( "sim" ) ){
//            //coluna.setForeground(Color.BLUE);
//            coluna.setBackground(Color.GREEN); // fundo amarelo
//        }
//        coluna.setBackground(Color.RED);
//        jTable1.getColumnModel().getColumn(1).setCellRenderer(coluna);




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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnVerificacao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome", "Está Ativo?", "Endereço"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnVerificacao.setText("Verificar ");

        btnVerificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVerificacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnVerificacao)
                        .addGap(207, 229, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnVerificacaoActionPerformed

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
            java.util.logging.Logger.getLogger(ViewAplicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewAplicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewAplicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewAplicacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewAplicacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVerificacao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;

    @Override
    public void atualizarProcessoAplicacao(String texto) {

    }
    // End of variables declaration//GEN-END:variables
}
