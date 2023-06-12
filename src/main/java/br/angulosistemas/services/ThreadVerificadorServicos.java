//package br.angulosistemas.services;
//
//import br.angulosistemas.Comunica;
//import br.angulosistemas.HttpHeader;
//import br.angulosistemas.HttpVerbo;
//import br.angulosistemas.IViewConfiguracao;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableColumn;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.List;
//
//
//public class ThreadVerificadorServicos implements Runnable {
//
//    private Comunica comunica;
//
//    private String resultadoResposta;
//
//    private String[] separadorVirgula;
//
//    private String ativo;
//
//    private JLabel label;
//
//    private Object texto;
//
//    private String serverHost;
//
//
//    private final IViewConfiguracao iViewConfiguracao;
//
//
//    public ThreadVerificadorServicos(IViewConfiguracao iViewConfiguracao) {
//        this.iViewConfiguracao = iViewConfiguracao;
//
//    }
//
//    @Override
//    public void run() {
//
//        try {
//            onIniciarVerificaoServicos();
//        } catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Não foi possivel realizar a leitura do arquivo: " + e.getMessage());
//        }
//
//
//    }
//
//    public void onIniciarVerificaoServicos() throws Exception{
//
//
//
//            if (comunica == null) comunica = new Comunica();
//
//            try {
//
//                String path = "C:\\servicosAtivosTeste.txt";
//
//                List<String> arquivo;
//
//                arquivo = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
//
//                // arquivo = (File) Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
//                System.out.println("arquivo: " + (arquivo.size() -1));
//                int tamanhoArquivo = (arquivo.size());
//
//
//                BufferedReader br = new BufferedReader(new FileReader(path));
//                String line = br.readLine();
//
//
//                String linhaLida = "";
//                while ( line != null ) {
//                    linhaLida = line;
//
//                    separadorVirgula = linhaLida.split(",");
//                    serverHost = "http://";
//                    try {
//                        resultadoResposta = comunica.callHttpGenerico(
//                                new URL(serverHost + separadorVirgula[1]),
//                                HttpVerbo.METODO_GET,
//                                false,
//                                true,
//                                HttpHeader.TEXT_PLAIN,
//                                HttpHeader.APPLICATION_JSON,
//                                null,
//                                null,
//                                15*1000,
//                                15*1000
//                        );
//                        System.out.println( "Resultado da resposta: " + resultadoResposta);
//
//                        System.out.println( "é vazio? " + resultadoResposta.isEmpty() );
//
//                        if(resultadoResposta != null ){
//                            System.out.println("diferente de vazio!");
//                            ativo = "sim";
//                        }
//
//
//
//                    }
//                    catch (Exception e){
//                        ativo = "nao";
//
//                        System.out.println("Erro ao tentar buscar objeto no webservice: " + e.getMessage());
//                    }
//
//
//                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//
//                    // Define o tamanho preferido da coluna "Está Ativo" como menor
//                    TableColumn activeColumn = jTable1.getColumnModel().getColumn(1);
//                    activeColumn.setPreferredWidth(50);
//
//                    // Define tamanhos maiores para as outras colunas
//                    TableColumn nameColumn = jTable1.getColumnModel().getColumn(0);
//                    nameColumn.setPreferredWidth(150);
//
//                    TableColumn addressColumn = jTable1.getColumnModel().getColumn(2);
//                    addressColumn.setPreferredWidth(200);
//
//
//                    if (model.getRowCount() > 0) {
//                        for (int i = jTable1.getRowCount() - tamanhoArquivo; i >= 0; i--)
//                            model.removeRow(i);
//                    }
//                    model.addRow(
//                            new Object[]{
//                                    separadorVirgula[0], ativo, separadorVirgula[1]
//                            }
//                    );
//
//                    System.out.println(linhaLida);
//
//                    line = br.readLine();
//                }
//
//            } catch (Exception e ) {
//
//                System.out.println("Não foi possivel ler o arquivo: " + e.getMessage() );
//            }
//
//
//
//
//    }
//}
