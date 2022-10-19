package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main {

    int glyphWidth = 11;
    int glyphHeight = 23;

    public void convert(String fileName){
        BufferedImage img;
        try{
            img = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResource(fileName)));
            BufferedImage scaledBWImage = toScale(toBlackWhiteImage(img));
            int charNumHor = (int) Math.round((double) img.getWidth()/glyphWidth);
            int charNumVer = (int) Math.round((double) img.getHeight()/glyphHeight);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    BufferedImage toScale(BufferedImage imageToScale) throws IOException {
        int scaled = 2;
        BufferedImage scaledImage = new BufferedImage(imageToScale.getWidth()/scaled , imageToScale.getHeight()/scaled, imageToScale.getType());
        Graphics2D g2D = scaledImage.createGraphics();
        g2D.drawImage(imageToScale, 0, 0, imageToScale.getWidth()/scaled, imageToScale.getHeight()/scaled, null);
        g2D.dispose();
        ImageIO.write(scaledImage, "png", new File("scaledBW.png"));
        return scaledImage;
    }

    BufferedImage toBlackWhiteImage(BufferedImage colorfulImage) throws IOException {
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
    }

    public static void main(String[] args) {
        new Main().convert("sIg.png");
    }
}