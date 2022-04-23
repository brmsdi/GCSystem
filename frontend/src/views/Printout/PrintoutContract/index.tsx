import PageLoading from "components/Loader/PageLoading";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { printoutContract } from "services/contract";
import { Contract, ContractEmpty } from "types/contract";

const PrintoutContract = () => {
  const [contract, setContract] = useState<Contract>(ContractEmpty);
  const [fail, setFail] = useState(false);
  const params = useParams();
  useEffect(() => {
    if (params.id === undefined || isNaN(parseInt(params.id))) {
      setFail(true)
      return
    }
    printoutContract(parseInt(params.id))
      .then(response => {
        setContract(response.data)
        setFail(false)
      })
      .catch(() => {
        setFail(true)
      })

  }, [params.id])

  if (fail === true) return (<div>Erro ao gerar contrato</div>)

  return contract.id === undefined ? (
    <PageLoading title="Gerando contrato" />
  ) : (
    <div className="content-contract-model">
      <div>
        <h1>CONTRATO DE LOCAÇÃO DE IMÓVEL</h1>
      </div>
      <header>
        <div>
          <p>
            <strong>Nome da empresa</strong>
          </p>
          <p>CNPJ: 2039828982989839</p>
          <p>Barão do Rio Branco, 100 – Flores</p>
          <p>Manaus - AM</p>
          <p>Telefone: 92 99107-1491</p>
          <p>E-mail: srmarquesms@gmail.com</p>
        </div>
      </header>
      <div className="content-principal-contract-model">
        <div>
          <p>
            Locatário Wisley Bruno Marques Franca portador da cédula de
            identidade R.G Nº 2787802-3 e CPF Nº 023.429.322-5, residirá em
            condomínio-A1, 920, R. Barão de Suruí - Flores, Manaus - Amazonas.
          </p>
        </div>
        <section className="clausulas section-clausulas-p1">
          <legend>CLÁUSULA PRIMEIRA – DO OBJETO DE LOCAÇÃO</legend>
          <div>
            <p>
              1.1 O objeto relacionado a este contrato está localizado em rua
              Barão de Suruí, 920, Flores, 69058-260, Manaus, Amazonas.
            </p>
          </div>
          <legend>CLÁUSULA SEGUNDA – DO PRAZO DE CONTRATO</legend>
          <div>
            <p>
              2.1 O prazo de locação é de no mínimo 03 meses, iniciando-se em
              01/02/2021 com término em 01/05/2021, independentemente de
              aviso, notificação ou interpelação judicial ou extrajudicial.
            </p>
          </div>
          <legend>CLÁUSULA TERCEIRA – DA FORMA DE PAGAMENTO</legend>
          <div>
            <p>
              3.1 O aluguel mensal poderá ser pago a partir do dia 01 (um) até
              o dia 05 (cinco) sem custos adicionais no valor deste contrato.
              O pagamento será via boleto, no valor de R$ 1.500,00 (mil e
              quinhentos reais), reajustados anualmente, pelo índice INCP,
              reajustamento estabelecido e calculado sobre o valor do último
              aluguel pago no último mês do ano anterior.
            </p>
          </div>
        </section>
        <section className="clausulas section-clausulas-p2">
          <div>
            <p>
              3.2 O aluguel não pago até o primeiro dia útil após a data de
              vencimento mensal, será cobrado o valor de 0.2% por dia sobre o
              valor deste contrato.
            </p>
          </div>
          <legend>CLÁUSULA QUARTA – DAS TAXAS E TRIBUTOS</legend>
          <div>
            <p>
              4.1 Todas as taxas e atributos incidentes sobre o imóvel, tais
              como condomínio, IPTU, bem como despesas ordinárias de
              condomínio e quaisquer outras despesas que recaírem sobre o
              imóvel, serão de responsabilidade do LOCADOR, o qual arcará com
              as despesas, tais como consumo de energia, força, água e gás que
              serão pagas diretamente ás empresas concessionárias dos
              referidos serviços, que serão devidos a partir desta data
              independente da troca de titularidade.
            </p>
          </div>
          <section className="footer-ass">
            <br />
            <hr />
            <p>Assinatura do responsável</p>
            <hr />
            <p>Assinatura do locatário</p>
          </section>
        </section>
      </div>
    </div>
  ); 
}

export default PrintoutContract;