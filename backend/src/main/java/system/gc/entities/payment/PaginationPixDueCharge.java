package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginationPixDueCharge {
    private Parametros parametros;
    private List<Charge> cobs;

    public PaginationPixDueCharge() {}
}
