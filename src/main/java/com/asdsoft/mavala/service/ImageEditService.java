package com.asdsoft.mavala.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class ImageEditService {
    private final static int MAX_LENGTH = 25;
    private final static int WHITE_SPACE_SIZE = 80;

    private int getXValue(String first_name, String last_name) {
        int string_len = (first_name.length() + last_name.length() + 1);
        int white_space_val = MAX_LENGTH - string_len;
        return 1700 + (int) (Math.ceil(white_space_val / 2) * WHITE_SPACE_SIZE);
    }

    public byte[] editImage(String first_name, String last_name) throws IOException {
        System.out.println(first_name.length());
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("certificate-min.webp")));
        //get the Graphics object
        Graphics g = image.getGraphics();
        //set font
        g.setFont(g.getFont().deriveFont(130f));
        g.setColor(Color.BLACK);
        //display the text at the coordinates(x=50, y=150)
        g.drawString(String.format("%s %s", StringUtils.capitalize(first_name), StringUtils.capitalize(last_name)), getXValue(first_name, last_name), 2200);
        g.dispose();
        //write the image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "webp", baos);
        image.flush();
        image = null;
        return baos.toByteArray();
    }
}
