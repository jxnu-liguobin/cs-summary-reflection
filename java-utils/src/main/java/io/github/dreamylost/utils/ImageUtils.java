/* All Contributors (C) 2020 */
package io.github.dreamylost.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 图片压缩处理
 *
 * @author 梦境迷离
 * @since 2020/11/26
 */
public class ImageUtils {

    private Image img;
    private int width;
    private int height;

    /**
     * 构造函数
     *
     * @param fileName 文件名，abc.jpg
     * @param fileFullPath 文件全路径名，/usr/local/tmp/abc.jpg
     * @throws IOException
     */
    public ImageUtils(String fileName, String fileFullPath) throws IOException {
        File file = new File(fileFullPath);
        img = ImageIO.read(file);
        width = img.getWidth(null);
        height = img.getHeight(null);
    }

    /**
     * @param file 文件
     * @throws IOException
     */
    public ImageUtils(File file) throws IOException {
        img = ImageIO.read(file);
        width = img.getWidth(null);
        height = img.getHeight(null);
    }

    /**
     * @param in 流
     * @throws IOException
     */
    public ImageUtils(InputStream in) throws IOException {
        img = ImageIO.read(in);
        width = img.getWidth(null);
        height = img.getHeight(null);
    }

    /**
     * 按照宽度还是高度进行压缩
     *
     * @param w int 最大宽度
     * @param h int 最大高度
     */
    public void resizeFix(int w, int h, String desFileFullName) {
        if (width / height > w / h) {
            resizeByWidth(w, desFileFullName);
        } else {
            resizeByHeight(h, desFileFullName);
        }
    }

    /**
     * 以宽度为基准，等比例放缩图片
     *
     * @param w int 新宽度
     */
    private void resizeByWidth(int w, String desFileFullName) {
        int h = (int) (height * w / width);
        resize(w, h, desFileFullName);
    }

    /**
     * 以高度为基准，等比例缩放图片
     *
     * @param h int 新高度
     */
    private void resizeByHeight(int h, String desFileFullName) {
        int w = (int) (width * h / height);
        resize(w, h, desFileFullName);
    }

    /**
     * 强制压缩/放大图片到固定的大小
     *
     * @param w int 新宽度
     * @param h int 新高度
     */
    private void resize(int w, int h, String desFileFullName) {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        File destFile = new File(desFileFullName);
        try {
            String formatName = desFileFullName.substring(desFileFullName.lastIndexOf(".") + 1);
            ImageIO.write(image, formatName, destFile);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.WARNING, "", e);
        }
    }

    /**
     * 校验图片格式
     *
     * @param in 被校验文件输入流
     * @param fileType 校验格式,如png或jpeg
     */
    public static boolean checkType(InputStream in, String fileType) {
        if (StringUtils.isBlank(fileType)) {
            fileType = "jpeg";
        }
        try (ImageInputStream iis = ImageIO.createImageInputStream(in)) {
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                Logger.getGlobal().log(Level.WARNING, "校验图片类型，无效的输入流.");
                return false;
            }
            ImageReader reader = iter.next();
            return fileType.equalsIgnoreCase(reader.getFormatName());
        } catch (IOException e) {
            Logger.getGlobal().log(Level.WARNING, "校验图片类型，读入文件异常.");
            Logger.getGlobal().log(Level.WARNING, "", e);
            return false;
        }
    }

    /**
     * 校验图片格式
     *
     * @param file 被校验文件输入流
     * @param fileType 校验格式,如png或jpeg
     */
    public static boolean checkType(File file, String fileType) {
        if (StringUtils.isBlank(fileType)) {
            fileType = "jpeg";
        }

        try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                Logger.getGlobal().log(Level.WARNING, "校验图片类型，无效的输入流.");
                return false;
            }
            ImageReader reader = iter.next();
            return fileType.equalsIgnoreCase(reader.getFormatName());
        } catch (IOException e) {
            Logger.getGlobal().log(Level.WARNING, "校验图片类型，读入文件异常.");
            Logger.getGlobal().log(Level.WARNING, "", e);
            return false;
        }
    }
}
