package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import play.Play;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

public class BarcodeManager {

	public static void generateCode128(String textToEncode, String targetPath) {
		int width = 250;//350; 430
		int height = 40;//100;
		BitMatrix bitMatrix;
		try {
			bitMatrix = new Code128Writer().encode(textToEncode,
					BarcodeFormat.CODE_128, width, height, null);
			FileOutputStream outputStream = new FileOutputStream(Play.getFile(targetPath));
			MatrixToImageWriter.writeToStream(bitMatrix, "jpeg",outputStream);
			outputStream.close();
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void generateQRCode(String textToEncode, String targetPath) throws WriterException,
			FileNotFoundException, IOException {
		int width = 300;
		int height = 400;
		String imageFormat = "jpeg"; // could be "gif", "tiff", "jpeg"
		//ByteMatrix bitMatrix = new QRCodeWriter().encode(textToEncode,
			//	BarcodeFormat.QR_CODE, width, height);
		//MatrixToImageWriter.writeToStream(bitMatrix, imageFormat,
			//	new FileOutputStream(Play.getFile(targetPath)));
	}

	public static String readBarcode(String barcodePath) throws IOException, ReaderException {
		InputStream barCodeInputStream = new FileInputStream(
				Play.getFile(barcodePath));
		BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);
		LuminanceSource source = new BufferedImageLuminanceSource(
				barCodeBufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Reader reader = new MultiFormatReader();
		com.google.zxing.Result result = reader.decode(bitmap);
		barCodeInputStream.close();
		return result.getText();
	}

	public static void main(String args[]) throws IOException, WriterException {
		generateCode128( "dknfreffejfejf ffjrfhrehff rfhfrfhfffjfj",
				"C:/Users/user/Documents/withoutsizes.png");
		generateQRCode("amani","C:/Users/user/Documents/withoutsizes1.png");
	}
}
