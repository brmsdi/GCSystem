package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Multa {
    private Integer modalidade;
    private String valorPerc;

    public Multa(){}
}
