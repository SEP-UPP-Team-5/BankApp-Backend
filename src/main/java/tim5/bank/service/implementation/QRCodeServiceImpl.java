package tim5.bank.service.implementation;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;
import tim5.bank.service.template.QRCodeService;

import java.io.File;
import java.io.IOException;
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
    public String readQR(String path, String charset, Map hashMap) {
        return null;
    }
}
