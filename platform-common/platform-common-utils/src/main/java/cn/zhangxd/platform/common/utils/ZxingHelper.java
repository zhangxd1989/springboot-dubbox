package cn.zhangxd.platform.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EnumMap;

/**
 * 条形码和二维码编码解码
 *
 * @author zhangxd
 */
public final class ZxingHelper {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZxingHelper.class);

    private ZxingHelper() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * 条形码编码
     * <p>
     * String imgPath = "target/zxing.png";
     * String contents = "6923450657713";
     * int width = 105, height = 50;
     * ZxingHelper.encode(contents, width, height, imgPath);
     *
     * @param contents 内容
     * @param width    宽度
     * @param height   高度
     * @param imgPath  生成图片路径
     */
    public static void encode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
            (7 * 6) + // left bars
            5 + // middle guard
            (7 * 6) + // right bars
            3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                BarcodeFormat.EAN_13, codeWidth, height, null);

            MatrixToImageWriter
                .writeToPath(bitMatrix, "png", new File(imgPath).toPath());

        } catch (Exception e) {
            LOGGER.error("生成条形码错误", e);
        }
    }

    /**
     * 条形码解码
     *
     * @param imgPath 文件路径
     * @return String string
     */
    public static String decode(String imgPath) {
        BufferedImage image;
        Result result;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                LOGGER.error("the decode image may be not exit.");
                return null;
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            LOGGER.error("条形码解析错误", e);
        }
        return null;
    }

    /**
     * 二维码编码
     * <p>
     * String imgPath2 = "target/zxing2.png";
     * String contents2 = "Hello Zxing!";
     * int width2 = 300, height2 = 300;
     * ZxingHelper.encode2(contents2, width2, height2, imgPath2);
     *
     * @param contents 内容
     * @param width    宽度
     * @param height   高度
     * @param imgPath  生成图片路径
     */
    public static void encode2(String contents, int width, int height, String imgPath) {
        EnumMap<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                BarcodeFormat.QR_CODE, width, height, hints);

            MatrixToImageWriter
                .writeToPath(bitMatrix, "png", new File(imgPath).toPath());

        } catch (Exception e) {
            LOGGER.error("生成二维码错误", e);
        }
    }

    /**
     * 二维码解码
     *
     * @param imgPath 文件路径
     * @return String string
     */
    public static String decode2(String imgPath) {
        BufferedImage image;
        Result result;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                LOGGER.error("the decode image may be not exit.");
                return null;
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            EnumMap<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
            hints.put(DecodeHintType.CHARACTER_SET, "GBK");

            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            LOGGER.error("二维码解码错误", e);
        }
        return null;
    }

}