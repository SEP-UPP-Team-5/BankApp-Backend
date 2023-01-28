package tim5.bank.service.template;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface QRCodeService {
    File createQR(String data, String path, String charset, Map hashMap,
                  int height, int width) throws WriterException, IOException;
    String readQR(String path, String charset, Map hashMap) throws FileNotFoundException, IOException,
            NotFoundException;
}
