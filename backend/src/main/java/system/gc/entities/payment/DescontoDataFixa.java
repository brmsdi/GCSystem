package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DescontoDataFixa {
    private String data;
    private String valorPerc;

    public DescontoDataFixa(){}
}
