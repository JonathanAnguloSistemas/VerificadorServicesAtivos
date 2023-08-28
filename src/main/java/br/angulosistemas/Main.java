package br.angulosistemas;

import br.angulosistemas.util.Util;
import br.angulosistemas.view.ViewAplicacao;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class Main {



    private static TrayIcon trayIcon;
    public static void main(String[] args) {

        ViewAplicacao viewAplicacao = new ViewAplicacao();

        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, " SystemTray não suportado pelo sistema operacional verifique a comptabilidade com o sistema ");
            System.exit(0);
        }
        /*Adicionado icone na barra de tarefas e texto de dialogo*/
        trayIcon = new TrayIcon( Objects.requireNonNull(Util.getImage("bing.png",70,45)));
        trayIcon.setImageAutoSize(true);
        atualizarTollTipTraycon("Verificador Servicos - Angulo Sistemas");
        SystemTray systemTray = SystemTray.getSystemTray();


        /*Criando os menus do Traycon da aplicação*/
        PopupMenu popupMenu = new PopupMenu();
        MenuItem menuEncerrar = new MenuItem("Encerrar aplicacao");
        popupMenu.add(menuEncerrar);
        trayIcon.setPopupMenu( popupMenu );

        try {
            systemTray.add(trayIcon);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Erro ao adicionar traycon");
        }

        trayIcon.addActionListener( e -> viewAplicacao.setVisible( true ) );
        menuEncerrar.addActionListener( e -> System.exit( 0 ) );
        notificatTraycon("Verificador Serviços", "Iniciando a aplicação", TrayIcon.MessageType.INFO);

        viewAplicacao.setVisible(true);

    }
    
    public static void atualizarTollTipTraycon(String mensagem){
        trayIcon.setToolTip(mensagem);
    }

    public static synchronized void notificatTraycon(String titulo, String mensagem, TrayIcon.MessageType type){
        trayIcon.displayMessage(titulo,mensagem,type );
    }

    public static void atualizarIconeTrayIcon( String nome_imagem ) {
        URL imagemUrl = Main.class.getClassLoader().getResource("imagens/" + nome_imagem);
        trayIcon.setImage( new ImageIcon( Objects.requireNonNull( imagemUrl ) ).getImage() );
        trayIcon.setImageAutoSize(true);
    }
}