package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pessoa {
    private String cpf;
    private String nome;
    private String email;
    private String logradouro;
    private String cidade;
    private String uf;
    private String cep;

    public Pessoa(){}
}
