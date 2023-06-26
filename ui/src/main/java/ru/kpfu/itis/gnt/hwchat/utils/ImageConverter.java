package ru.kpfu.itis.gnt.hwchat.utils;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ImageConverter {

    private static final String IMAGE_FORMAT = "png";
    private static final Charset FORMAT = StandardCharsets.ISO_8859_1;

    /**
     * @param filePath Путь к файлу с изображением
     * @return Массив из байтов изображения.
     * @throws IOException В случае, если доступ к файлу не удалось получить
     */
    public static String encodeToString(String filePath) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(filePath));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, IMAGE_FORMAT, baos);
        return baos.toString(FORMAT);
    }

    /**
     * @param encodedImage Преобразованное в строку изображение.
     * @return InputStream изображения, который можно использовать в UI.
     */
    public static InputStream decodeFromString(String encodedImage) {
        byte[] bytes = encodedImage.getBytes(FORMAT);
        return new ByteArrayInputStream(bytes);
    }
}
