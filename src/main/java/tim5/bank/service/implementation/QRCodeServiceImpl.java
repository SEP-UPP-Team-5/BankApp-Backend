package tim5.bank.service.implementation;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tim5.bank.service.template.QRCodeService;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeServiceImpl implements QRCodeService {
    @Override
    public File createQR(String data, String path, String charset, Map hashMap, int height, int width) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(
                new String(data.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, width, height);

        File file = new File(path);
        MatrixToImageWriter.writeToFile(
                matrix,
                path.substring(path.lastIndexOf('.') + 1),
                file);
        return file;
    }

    @Override
    public String readQR(MultipartFile file, String charset, Map hashMap) throws FileNotFoundException, IOException,
            NotFoundException{
        BinaryBitmap binaryBitmap
                = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(
                                file.getInputStream()))));

        Result result
                = new MultiFormatReader().decode(binaryBitmap);

        return result.getText();
    }

    @Override
    public String validateQR(MultipartFile file) throws FileNotFoundException, IOException, NotFoundException {
        String path = "src/main/resources/image/demo.png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<EncodeHintType,
                ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION,
                ErrorCorrectionLevel.L);

        return readQR(file, charset, hashMap);
    }
}
