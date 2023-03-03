package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QRCode {
    private String imagemQrcode;
    private String qrcode;

    public QRCode() {}
}
