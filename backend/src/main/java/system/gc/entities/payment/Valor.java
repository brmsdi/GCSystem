package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Valor {
    private String original;
    private Abatimento abatimento;
    private Desconto desconto;
    private Juros juros;
    private Multa multa;

    public Valor(){}
}
