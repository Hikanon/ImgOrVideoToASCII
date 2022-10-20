package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageConverter {

    private final int glyphWidth ;
    private final int glyphHeight;
    private BufferedImage workingImage;

    public ImageConverter(int glyphWidth, int glyphHeight) {
        this.glyphHeight = glyphHeight;
        this.glyphWidth = glyphWidth;
    }

    public StringBuilder convertToASCII(String fileName, int scaledBy){

        try{
            BufferedImage img = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResource(fileName)));
            workingImage = repairImage(toScale(toNegativeImage(img), scaledBy));
            StringBuilder asciiText = new StringBuilder(workingImage.getWidth() * workingImage.getHeight());
            for (int j = 0; j < workingImage.getHeight(); j++){
                if(!asciiText.isEmpty()){
                    asciiText.append("\n");
                }
                for (int i = 0; i< workingImage.getWidth(); i++) {
                    Color pixelColor = new Color(workingImage.getRGB(i, j));
                    double grey = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
                    char c = getASCIIChar(grey);
                    asciiText.append(c);
                }
            }
            return asciiText;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private char getASCIIChar(double g)
    {
        char c;

        if (g >= 230.0) {
            c = ' ';
        } else if (g >= 200.0) {
            c = '.';
        } else if (g >= 180.0) {
            c = '*';
        } else if (g >= 160.0) {
            c = ':';
        } else if (g >= 130.0) {
            c = 'o';
        } else if (g >= 100.0) {
            c = '&';
        } else if (g >= 70.0) {
            c = '8';
        } else if (g >= 50.0) {
            c = '#';
        } else {
            c = '@';
        }
        return c;

    }

    private BufferedImage repairImage(BufferedImage repairedImage) throws IOException {
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
        return repairedImage;
    }

    public BufferedImage toScale(BufferedImage imageToScale, int scaledBy) throws IOException {
        BufferedImage scaledImage = new BufferedImage(imageToScale.getWidth()/scaledBy , imageToScale.getHeight()/scaledBy, imageToScale.getType());
        Graphics2D g2D = scaledImage.createGraphics();
        g2D.drawImage(imageToScale, 0, 0, imageToScale.getWidth()/scaledBy, imageToScale.getHeight()/scaledBy, null);
        g2D.dispose();
        return scaledImage;
    }

    public BufferedImage toNegativeImage(BufferedImage colorfulImage) throws IOException {
        BufferedImage negativeImage = new BufferedImage(colorfulImage.getWidth(), colorfulImage.getHeight(), colorfulImage.getType());
        for (int i = 0; i < colorfulImage.getWidth(); i++){
            for(int j = 0; j < colorfulImage.getHeight(); j++){
                Color pixelColor = new Color(colorfulImage.getRGB(i,j));
                negativeImage.setRGB(i, j, new Color(255 - pixelColor.getRed(), 255 - pixelColor.getGreen(), 255 - pixelColor.getBlue()).getRGB());
            }
        }
        return negativeImage;
    }

    public BufferedImage getWorkingImage() {
        return workingImage;
    }
}
