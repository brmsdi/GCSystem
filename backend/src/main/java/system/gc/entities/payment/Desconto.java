package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Desconto {
    private Integer modalidade;
    private List<DescontoDataFixa> descontoDataFixa;

    public Desconto(){}
}
