package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Charge {
    private Calendario calendario;
    private String txid;
    private Integer revisao;
    private Loc loc;
    private String status;
    private Pessoa devedor;
    private Pessoa recebedor;
    private Valor valor;
    private String chave;
    private String solicitacaoPagador;
    private List<InfoAdicionais> infoAdicionais;

    public Charge(){}

}