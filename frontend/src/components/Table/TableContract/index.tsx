import Alert from "components/messages";
import { useDispatch, useSelector } from "react-redux";
import { deleteContract } from "services/contract";
import { selectContractTableAction, setStateFormContractAction, updateContractTableAction } from "store/Contracts/contracts.actions";
import { selectAllContracts } from "store/Contracts/contracts.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Contract, PaginationContract } from "types/contract";
import { formatCoinPTBRForView } from "utils/coin-format";
import { formatDateForView } from "utils/textFormt";
const TableContract= () => {
  const dispatch = useDispatch();
  const page: PaginationContract = useSelector(selectAllContracts);
  async function clickButtonUpdate(selected: Contract | undefined) {
    let form = document.querySelector(".content-form");
    if (form != null && selected) {
      if (!form.classList.contains('active')) {
        form.classList.toggle('active')
      }
      dispatch(selectContractTableAction(selected))
      dispatch(setStateFormContractAction(StateFormEnum.UPDATE))
    }
  }

  async function clickButtonDelete(ID: number) {
    const result = await Swal.fire({
      title: 'Você deseja deletar esse registro?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    })

    if (result.isConfirmed) {
      try {
        const data = await deleteContract(ID)
        Swal.fire('Êbaa!', '' + data, 'success')
        dispatch(updateContractTableAction())
      } catch (error: any) {
        if (!error.response) {
          Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
        } else {
          let message = error.response.data.errors[0].message;
          Swal.fire("Oops!", "" + message, "error");
        }
      }
    }
  }

  return (
    <div className="table-responsive">
      {
        (page.empty === true) ?
          (
            (<Alert msg="Nenhum registro encontrado!" />)
          )
          :
          <table className="table table-striped">
            <thead className="thead-max">
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Nome do locatário</th>
                <th scope="col">Nome do condomínio</th>
                <th scope="col">Valor do contrato</th>
                <th scope="col">Data do contrato</th>
                <th scope="col">Dia de pagamento</th>
                <th scope="col">Dia de vencimento</th>
                <th scope="col">Data de validade</th>
                <th scope="col">Nº do apartamento</th>
                <th scope="col">Status</th>
                <th scope="col">#</th>
              </tr>
            </thead>
            <tbody>
              {
                page?.content?.map((item: Contract) => {
                  return <ItemTable key={item.id} item={item} toogleClass={clickButtonUpdate} clickButtonDelete={clickButtonDelete} />;
                })
              }
            </tbody>
          </table>
      }
    </div> // end table-responsive
  )
}

interface IProps {
  item: Contract, 
  toogleClass: Function, 
  clickButtonDelete: Function
}
const ItemTable = (props: IProps) => {
  let item = props.item;
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td>{item.id}</td>
      <th className="thead-min">Nome do locatário</th>
      <td>{item.lessee.name}</td>
      <th className="thead-min">Nome do condmínio</th>
      <td>{item.condominium.name}</td>
      <th className="thead-min">Valor do contrato</th>
      <td>{formatCoinPTBRForView(item.contractValue)}</td>
      <th className="thead-min">Data do contrato</th>
      <td>{formatDateForView(item.contractDate)}</td>
      <th className="thead-min">Dia de pagamento</th>
      <td>{item.monthlyPaymentDate}</td>
      <th className="thead-min">Dia de vencimento</th>
      <td>{item.monthlyDueDate}</td>
      <th className="thead-min">Data de validade</th>
      <td>{formatDateForView(item.contractExpirationDate)}</td>
      <th className="thead-min">Nº do apartamento</th>
      <td>{item.apartmentNumber}</td>
      <th className="thead-min">Status</th>
      <td>{item.status.name}</td>
      <th className="thead-min">Opções</th>
      <td>
        <button
          id="btn-table-contract-update"
          type="button"
          aria-label="Atualizar esse contrato"
          title="Atualizar esse contrato"
          className="btn btn-primary btn-table-options"
          onClick={(e) => props.toogleClass(item)}><span aria-hidden="true" ><i className="bi bi-clipboard-data"></i></span>
        </button>
        <button
          id="btn-table-contract-pdf"
          type="button"
          aria-label="Gerar arquivo pdf do contrato"
          title="Gerar arquivo pdf do contrato"
          className="btn btn-secondary btn-table-options"><span aria-hidden="true"><i className="bi bi-file-earmark-pdf"></i></span>
        </button>
        <button
          id="btn-table-contract-delete"
          type="button"
          aria-label="Deletar esse contrato"
          title="Deletar esse contrato"
          className="btn btn-danger btn-table-options"
          onClick={() => props.clickButtonDelete(item.id)}><span aria-hidden="true"><i className="bi bi-trash"></i></span>
        </button>
        
      </td>
    </tr>
  )
}

export default TableContract;
