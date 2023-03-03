package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Paginacao {
    private int paginaAtual;
    private int itensPorPagina;
    private int quantidadeDePaginas;
    private int quantidadeTotalDeItens;

    public Paginacao() {}
}
