package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Parametros {
    private String inicio;
    private String fim;

    private Paginacao paginacao;
    public Parametros(){}
}
