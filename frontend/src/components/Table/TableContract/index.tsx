import Alert from "components/messages";
import { useDispatch, useSelector } from "react-redux";
import { deleteContract } from "services/contract";
import { selectContractTableAction, setStateFormContractAction, updateContractTableAction } from "store/Contracts/contracts.actions";
import { selectAllContracts } from "store/Contracts/contracts.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Contract, PaginationContract } from "types/contract";
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
                <th scope="col">Locatário</th>
                <th scope="col">Condominio</th>
                <th scope="col">ID Contrato</th>
                <th scope="col">DT. Contrato</th>
                <th scope="col">DT. Pagamento/Mês</th>
                <th scope="col">DT. Vencimento</th>
                <th scope="col">DT. Validade</th>
                <th scope="col">Nº APT</th>
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
      <th className="thead-min">Locatário</th>
      <td>{item.lessee.name}</td>
      <th className="thead-min">Condominio</th>
      <td>{item.condominium.name}</td>
      <th className="thead-min">ID Contrato</th>
      <td>{item.id}</td>
      <th className="thead-min">DT. Contrato</th>
      <td>{item.contractDate}</td>
      <th className="thead-min">DT. Pagamento/Mês</th>
      <td>{item.monthlyPaymentDate}</td>
      <th className="thead-min">DT. Vencimento</th>
      <td>{item.monthlyDueDate}</td>
      <th className="thead-min">DT. Validade</th>
      <td>{item.contractExpirationDate}</td>
      <th className="thead-min">Nº APT</th>
      <td>{item.apartmentNumber}</td>
      <th className="thead-min">Status</th>
      <td>{item.status.name}</td>
      <th className="thead-min">Opções</th>
      <td>
        <button
          className="btn btn-primary btn-table-options"
          onClick={(e) => props.toogleClass(item)}><span><i className="bi bi-clipboard-data"></i></span>
        </button>
        <button
          className="btn btn-danger btn-table-options"
          onClick={() => props.clickButtonDelete(item.id)}><span><i className="bi bi-trash"></i></span>
        </button>
      </td>
    </tr>
  )
}

export default TableContract;
