package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoAdicionais {
    private String nome;
    private String valor;
    public InfoAdicionais(){}
}
