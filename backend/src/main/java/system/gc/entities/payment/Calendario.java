package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Calendario {
    private String criacao;
    private String dataDeVencimento;
    private Integer validadeAposVencimento;

    public Calendario(){}

    public Calendario(String dataDeVencimento, Integer validadeAposVencimento) {
        this.dataDeVencimento = dataDeVencimento;
        this.validadeAposVencimento = validadeAposVencimento;
    }
}
