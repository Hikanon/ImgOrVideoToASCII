package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public void convert(String fileName){
        BufferedImage img;
        try{
            img = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResource(fileName)));
            BufferedImage bwI = toBWI(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    BufferedImage toBWI(BufferedImage colorfulImage){
        try {
            BufferedImage bwImage = new BufferedImage(colorfulImage.getWidth(), colorfulImage.getHeight(), colorfulImage.getType());
            for (int i = 0; i < colorfulImage.getWidth(); i++){
                for(int j = 0; j < colorfulImage.getHeight(); j++){
                    int r = new Color(colorfulImage.getRGB(i, j)).getRed();
                    int g = new Color(colorfulImage.getRGB(i, j)).getGreen();
                    int b = new Color(colorfulImage.getRGB(i, j)).getBlue();
                    int color = (r+g+b)/3;
                    if(color > 127){
                        color = 255;
                    }else{
                        color = 0;
                    }
                    bwImage.setRGB(i, j, new Color(color, color, color).getRGB());

                }
            }
            ImageIO.write(bwImage ,"png" , new File("hehe.png"));
            return bwImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Main().convert("sIg.png");
    }
}