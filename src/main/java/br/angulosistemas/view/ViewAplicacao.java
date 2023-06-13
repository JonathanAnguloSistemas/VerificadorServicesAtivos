/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.angulosistemas.view;

import br.angulosistemas.*;
import br.angulosistemas.util.Util;

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


public class ViewAplicacao extends javax.swing.JFrame     {

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
        setLocationRelativeTo( null );
        setIconImage(Util.getImage("bing.png",155,140));
        /* Adicionando eventos aos botoes da tela */
        btnVerificacao.addActionListener( e -> carregarServicosTela());

    }

    public void carregarServicosTela(){

        if (comunica == null) comunica = new Comunica();

        try {

            String path = "C:\\visualfs\\verificadorservicos\\servicosAtivoss.txt";

            List<String> arquivo;

            arquivo = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);

            int tamanhoArquivo = (arquivo.size());

            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();


            Main.notificatTraycon("Verificando..","Verificando a conexão dos serviços. Aguarde...", TrayIcon.MessageType.INFO);
            Thread.sleep(100);
            String linhaLida = "";
            int servicosLidos = 0;
            while ( line != null ) {

                linhaLida = line;
                separadorVirgula = linhaLida.split(",");

//                if ( separadorVirgula[0].contains( "Tray" ) ) {
//                     serverHost = "https://";
//                } else {
//                }

//                // Cria um SSLContext com um TrustManager que não realiza verificação
//                SSLContext sslContext = SSLContext.getInstance("TLS");
//                sslContext.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
//
//                // Configura a conexão HTTPS para usar o SSLContext com a verificação desabilitada
//                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

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

                    int codStatus = Comunica.codStatus;
                    int OK = 200;
                    int BadRequest = 404;
                    if( codStatus == OK || codStatus == BadRequest || codStatus == 400 ) {
                        ativo = "sim";
                    }
                }

                catch (Exception e){
                    System.out.println("Erro ao tentar buscar objeto no webservice: " + e.getMessage());
                    if (separadorVirgula[0].contains("Tray") && e.getMessage().contains("400") || e.getMessage().contains("404")){
                        ativo = "sim";
                    } else {
                        ativo = "nao";
                    }
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
        jLabel1 = new javax.swing.JLabel();

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

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 2, 12)); // NOI18N
        jLabel1.setText("Verificar Disponibilidade dos Serviços");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnVerificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(84, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerificacao)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
