import Alert from "components/messages";
import { useDispatch, useSelector } from "react-redux";
import { deleteDebt } from "services/debt";
import {
  selectDebtTableAction,
  setStateFormDebtAction,
  updateDebtTableAction,
} from "store/Debts/debts.actions";
import { selectAllDebts } from "store/Debts/debts.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Debt, PaginationDebt } from "types/debt";
import { formatCoinPTBRForView } from "utils/coin-format";
import { formatDateForView } from "utils/textFormt";
const TableDebt = () => {
  const dispatch = useDispatch();
  const page: PaginationDebt = useSelector(selectAllDebts);
  async function clickButtonUpdate(selected: Debt | undefined) {
    let form = document.querySelector(".content-form");
    if (form != null && selected) {
      if (!form.classList.contains("active")) {
        form.classList.toggle("active");
      }
      dispatch(selectDebtTableAction(selected));
      dispatch(setStateFormDebtAction(StateFormEnum.UPDATE));
    }
  }

  async function clickButtonDelete(ID: number) {
    const result = await Swal.fire({
      title: "Você deseja deletar esse registro?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Confirmar",
      cancelButtonText: "Cancelar",
    });

    if (result.isConfirmed) {
      try {
        const data = await deleteDebt(ID);
        Swal.fire("Êbaa!", "" + data, "success");
        dispatch(updateDebtTableAction());
      } catch (error: any) {
        if (!error.response) {
          Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
        } else {
          Swal.fire("Oops!", "Erro desconhecido", "error");
        }
      }
    }
  }

  return (
    <div className="table-responsive">
      {page.empty === true ? (
        <Alert msg="Nenhum registro encontrado!" />
      ) : (
        <table className="table table-striped">
          <thead className="thead-max">
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Nome do locatário</th>
              <th scope="col">Valor do débito</th>
              <th scope="col">Data de vencimento</th>
              <th scope="col">Status</th>
              <th scope="col">#</th>
            </tr>
          </thead>
          <tbody>
            {page?.content?.map((item: Debt) => {
              return (
                <ItemTable
                  key={item.id}
                  item={item}
                  toogleClass={clickButtonUpdate}
                  clickButtonDelete={clickButtonDelete}
                />
              );
            })}
          </tbody>
        </table>
      )}
    </div> // end table-responsive
  );
};

interface IProps {
  item: Debt;
  toogleClass: Function;
  clickButtonDelete: Function;
}
const ItemTable = (props: IProps) => {
  let item = props.item;
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td>{item.id}</td>
      <th className="thead-min">Nome do locatário</th>
      <td>{item.lessee.name}</td>
      <th className="thead-min">valor do débito</th>
      <td>{formatCoinPTBRForView(item.value)}</td>
      <th className="thead-min">Data de vencimento</th>
      <td>{formatDateForView(item.dueDate)}</td>
      <th className="thead-min">Status</th>
      <td>{item.status.name}</td>
      <th className="thead-min">Opções</th>
      <td>
        <button
          id="btn-table-debt-update"
          type="button"
          aria-label="Atualizar esse débito"
          title="Atualizar esse débito"
          className="btn btn-primary btn-table-options"
          onClick={(e) => props.toogleClass(item)}
        >
          <span aria-hidden="true">
            <i className="bi bi-clipboard-data"></i>
          </span>
        </button>
        <button
          id="btn-table-debt-pdf"
          type="button"
          aria-label="Gerar arquivo pdf do débito"
          title="Gerar arquivo pdf do débito"
          className="btn btn-secondary btn-table-options"
        >
          <span aria-hidden="true">
            <i className="bi bi-file-earmark-pdf"></i>
          </span>
        </button>
        <button
          id="btn-table-debt-delete"
          type="button"
          aria-label="Deletar esse débito"
          title="Deletar esse débito"
          className="btn btn-danger btn-table-options"
          onClick={() => props.clickButtonDelete(item.id)}
        >
          <span aria-hidden="true">
            <i className="bi bi-trash"></i>
          </span>
        </button>
      </td>
    </tr>
  );
};

export default TableDebt;
