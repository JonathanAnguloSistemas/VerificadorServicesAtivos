package br.angulosistemas.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public interface Util {

    Object String = "";

    public static Image getImage(String nomeImagem, Integer largura, Integer altura ) {

        final String recurso = "imagens/";
        String recursoCompleto = recurso + nomeImagem;

        try {
            URL imagemUrl = Util.class.getClassLoader().getResource(recursoCompleto);
            Image image = ImageIO.read(imagemUrl);
            return image.getScaledInstance(largura, altura, Image.SCALE_DEFAULT);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Erro ao carregar a imagem do projeto: " + recursoCompleto + "\n");
           // info(Util.class,"Erro ao carregar a imagem do projeto: " + recursoCompleto + "\n" + e.getMessage(), Level.FATAL);
            return null;
        }
    }

    public static Image getImagemLogo(String nome_imagem ) {
        URL image = Util.class.getClassLoader().getResource( "imagens/" + nome_imagem );

        if ( image == null ) {
            System.out.println("Imagem não existe");
            return null;
        }
        else
            return ( new ImageIcon( image, nome_imagem ) ).getImage();
    }

}
