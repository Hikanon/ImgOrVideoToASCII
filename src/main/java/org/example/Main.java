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
            BufferedImage workingImage = repairImage(toScale(toBlackWhiteImage(img), 2));
            int charNumHor = (int) Math.round((double) img.getWidth()/glyphWidth);
            int charNumVer = (int) Math.round((double) img.getHeight()/glyphHeight);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    BufferedImage repairImage(BufferedImage repairedImage) throws IOException {
        BufferedImage repairImage = new BufferedImage(repairedImage.getWidth() + glyphWidth - (repairedImage.getWidth() % glyphWidth),
                (repairedImage.getHeight() + glyphHeight - (repairedImage.getHeight() % glyphHeight)), repairedImage.getType());
        for (int i = 0; i < repairImage.getWidth(); i++){
            for (int j = 0; j< repairImage.getHeight(); j++){
                if(i > repairedImage.getWidth() -1 || j > repairedImage.getHeight() -1){
                    repairImage.setRGB(i, j, Color.WHITE.getRGB());
                }else {
                    repairImage.setRGB(i, j, repairedImage.getRGB(i, j));
                }
            }
        }
        ImageIO.write(repairImage, "png", new File("repairImage.png"));
        return repairedImage;
    }

    BufferedImage toScale(BufferedImage imageToScale, int scaledBy) throws IOException {
        BufferedImage scaledImage = new BufferedImage(imageToScale.getWidth()/scaledBy , imageToScale.getHeight()/scaledBy, imageToScale.getType());
        Graphics2D g2D = scaledImage.createGraphics();
        g2D.drawImage(imageToScale, 0, 0, imageToScale.getWidth()/scaledBy, imageToScale.getHeight()/scaledBy, null);
        g2D.dispose();
        ImageIO.write(scaledImage, "png", new File("scaledImage.png"));
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
        ImageIO.write(bwImage ,"png" , new File("blackWhiteImage.png"));
        return bwImage;
    }

    public static void main(String[] args) {
        new Main().convert("sourceImage.png");
    }
}