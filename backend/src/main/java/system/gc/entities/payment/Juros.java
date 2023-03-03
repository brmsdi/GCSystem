package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Juros {

    private Integer modalidade;
    private String valorPerc;

    public Juros(){}
}
