package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Abatimento {
    private Integer modalidade;
    private String valorperc;

    public Abatimento(){}
}
