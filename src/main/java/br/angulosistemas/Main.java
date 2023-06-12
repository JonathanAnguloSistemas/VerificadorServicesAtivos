package br.angulosistemas;


import br.angulosistemas.util.Util;
import br.angulosistemas.view.ViewAplicacao;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
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

        trayIcon = new TrayIcon( Objects.requireNonNull(Util.getImage("comparadorIcon.png",70,45)));
        trayIcon.setImageAutoSize(true);
        SystemTray systemTray = SystemTray.getSystemTray();

        /*Criando os menus do Traycon da aplicação*/
        PopupMenu popupMenu = new PopupMenu();
        MenuItem menuPrincipal = new MenuItem("Principal");
        MenuItem menuEncerrar = new MenuItem("Encerrar aplicacao");

        popupMenu.add(menuPrincipal);
        popupMenu.add(menuEncerrar);
        trayIcon.setPopupMenu( popupMenu );

        try {
            systemTray.add(trayIcon);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Erro ao adicionar traycon");
        }


        trayIcon.addActionListener( e -> viewAplicacao.setVisible( true ) );
        menuPrincipal.addActionListener( e -> viewAplicacao.setVisible( true ) );
        menuEncerrar.addActionListener( e -> System.exit( 0 ) );

        viewAplicacao.setVisible(true);

    }
}