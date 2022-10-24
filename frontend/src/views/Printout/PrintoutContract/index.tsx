import PageMessage from "components/Loader/PageLoading";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { printoutContract } from "services/contract";
import { Contract, ContractEmpty } from "types/contract";
import errorIMG from "assets/img/error.svg";
import { formatDateForView } from "utils/text-format";
import { formatCoinPTBRForView, formatInFull } from "utils/coin-format";

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

  if (fail === true) return <PageMessage title={"Oops! Erro ao gerar contrato, tente novamente."} imageForTitle={errorIMG}/>

  return contract.id === undefined ? (
    <PageMessage title="Gerando contrato" />
  ) : (
    <div className="content-contract-model">
      <div>
        <h1>CONTRATO DE LOCAÇÃO DE IMÓVEL(DADOS FICTÍCIOS)</h1>
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
            {
              `Locatário ${contract.lessee.name} portador da cédula de
              identidade R.G Nº ${contract.lessee.rg} e CPF Nº ${contract.lessee.cpf}, residirá em
              ${contract.condominium.name}, ${contract.condominium.localization.localization.road}, ${contract.condominium.localization.number} - ${contract.condominium.localization.localization.name}, Manaus - Amazonas.`
            }
          </p>
        </div>
        <section className="clausulas section-clausulas-p1">
          <legend>CLÁUSULA PRIMEIRA – DO OBJETO DE LOCAÇÃO</legend>
          <div>
            <p>
              {
                `1.1 O objeto relacionado a este contrato está localizado em rua
                ${contract.condominium.localization.localization.road}, ${contract.condominium.localization.number} - ${contract.condominium.localization.localization.name}, ${contract.condominium.localization.localization.zipCode}, Manaus - Amazonas.`
              }
            </p>
          </div>
          <legend>CLÁUSULA SEGUNDA – DO PRAZO DE CONTRATO</legend>
          <div>
            <p>
              {
                `2.1 O prazo de locação é de no mínimo 03 meses, iniciando-se em
                ${formatDateForView(contract.contractDate)} com término em ${formatDateForView(contract.contractExpirationDate)}, independentemente de
                aviso, notificação ou interpelação judicial ou extrajudicial.`
              }
            </p>
          </div>
          <legend>CLÁUSULA TERCEIRA – DA FORMA DE PAGAMENTO</legend>
          <div>
            <p>
             {` 3.1 O aluguel mensal poderá ser pago a partir do dia ${contract.monthlyPaymentDate} (${formatInFull(contract.monthlyPaymentDate)}) até
              o dia ${contract.monthlyDueDate} (${formatInFull(contract.monthlyDueDate)}) sem custos adicionais no valor deste contrato.
              O pagamento será via boleto, no valor de ${formatCoinPTBRForView(contract.contractValue)} (${formatInFull(contract.contractValue)}), reajustados anualmente, pelo índice INCP,
              reajustamento estabelecido e calculado sobre o valor do último
              aluguel pago no último mês do ano anterior.`}
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