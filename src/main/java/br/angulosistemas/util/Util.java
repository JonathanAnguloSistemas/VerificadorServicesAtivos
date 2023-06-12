package br.angulosistemas.util;

import org.apache.logging.log4j.Level;

import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;

public interface Util {

    public static Image getImage( String nomeImagem, Integer largura, Integer altura ) {

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
}
