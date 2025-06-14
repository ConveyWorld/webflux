package com.webflux.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class MergeImage {

    public static void main(String[] args) throws IOException {
        String matTruoc = "src/main/resources/images/mattruoc.png";
//        String matSau = "src/main/resources/images/matsau.png";
        String matSau = "src/main/resources/images/avt.png";
        String base64 = handleImagesFacePng(matTruoc, matSau);
        System.out.println(base64);
    }

    private static String handleNationalIdImage(String firstImage, String secondImage) throws IOException {
        var front = ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(Paths.get(firstImage))));
        var back = ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(Paths.get(secondImage))));

        int width = Math.max(front.getWidth(), back.getWidth());
        BufferedImage img = new BufferedImage(width, front.getHeight() + back.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        Graphics g = img.getGraphics();
        g.drawImage(front, 0, 0, null);
        g.drawImage(back, 0, front.getHeight(), null);
        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        var imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

        private static String handleImagesFacePng(String firstImage, String secondImage) throws IOException {
            // Đọc ảnh nền và ảnh avatar từ đường dẫn
            BufferedImage background = ImageIO.read(new File(firstImage));
            BufferedImage avatar = ImageIO.read(new File(secondImage));

            // Resize avatar thành hình (200x300)
            Image scaledAvatar = avatar.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            BufferedImage resizedAvatar = new BufferedImage(200, 300, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedAvatar.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(scaledAvatar, 0, 0, null);
            g2d.dispose();

            // Ghép avatar vào vị trí trong ảnh nền
            Graphics2D g = background.createGraphics();
            int avatarX = 45;  // Có thể cần chỉnh lên 55-60
            int avatarY = 180; // Có thể cần chỉnh lên 115-130
            g.drawImage(resizedAvatar, avatarX, avatarY, null);
            g.dispose();

            // Debug: Ghi ảnh merge ra file để kiểm tra
            ImageIO.write(background, "png", new File("merged-preview.png"));

            // Convert sang base64
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(background, "png", outputStream);
            byte[] mergedBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(mergedBytes);
        }

    private static String handleImageFaceJpg(String firstImage, String secondImage) throws IOException {
        // Đọc ảnh nền và avatar
        BufferedImage background = ImageIO.read(new File(firstImage));
        BufferedImage avatar = ImageIO.read(new File(secondImage));

        // Resize avatar thành hình (200x300)
        Image scaledAvatar = avatar.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        BufferedImage resizedAvatar = new BufferedImage(200, 300, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resizedAvatar.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(scaledAvatar, 0, 0, null);
        g2d.dispose();

        // Ghép avatar vào ảnh nền
        Graphics2D g = background.createGraphics();
        int avatarX = 45;
        int avatarY = 180;
        g.drawImage(resizedAvatar, avatarX, avatarY, null);
        g.dispose();

        //Chuyển ảnh nền sang RGB để ghi ra JPG
        BufferedImage rgbImage = new BufferedImage(
                background.getWidth(),
                background.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D gRgb = rgbImage.createGraphics();
        gRgb.setPaint(Color.WHITE); // Nền trắng nếu có pixel trong suốt
        gRgb.fillRect(0, 0, rgbImage.getWidth(), rgbImage.getHeight());
        gRgb.drawImage(background, 0, 0, null);
        gRgb.dispose();

        // Ghi ra ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(rgbImage, "jpg", outputStream);
        byte[] mergedBytes = outputStream.toByteArray();

        return Base64.getEncoder().encodeToString(mergedBytes);
    }

}
