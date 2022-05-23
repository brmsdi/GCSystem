import Alert from "components/messages";
import { useDispatch, useSelector } from "react-redux";
import { deleteCondominium } from "services/condominium";
import {
  selectCondominiumTableAction,
  setStateFormCondominiumAction,
  updateCondominiumTableAction,
} from "store/Condominiums/condiminiums.actions";
import { selectAllCondominiums } from "store/Condominiums/condiminiums.selectors";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Condominium, PaginationCondominium } from "types/condominium";
const TableCondominium = () => {
  const dispatch = useDispatch();
  const page: PaginationCondominium = useSelector(selectAllCondominiums);
  async function clickButtonUpdate(selected: Condominium | undefined) {
    let form = document.querySelector(".content-form");
    if (form != null && selected) {
      if (!form.classList.contains("active")) {
        form.classList.toggle("active");
      }
      dispatch(selectCondominiumTableAction(selected));
      dispatch(setStateFormCondominiumAction(StateFormEnum.UPDATE));
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
        const data = await deleteCondominium(ID);
        Swal.fire("Êbaa!", "" + data, "success");
        dispatch(updateCondominiumTableAction());
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
              <th scope="col">Nome</th>
              <th scope="col">Descrição</th>
              <th scope="col">Nº de apartamentos</th>
              <th scope="col">Cep da rua</th>
              <th scope="col">Nome da rua</th>
              <th scope="col">Nome do bairro</th>
              <th scope="col">Número</th>
              <th scope="col">#</th>
            </tr>
          </thead>
          <tbody>
            {page?.content?.map((item: Condominium) => {
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

interface IPropsItemTable {
  item: Condominium;
  toogleClass: Function;
  clickButtonDelete: Function;
}

const ItemTable = (props: IPropsItemTable) => {
  let item = props.item;
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td>{item.id}</td>
      <th className="thead-min">Nome</th>
      <td>{item.name}</td>
      <th className="thead-min">Descrição</th>
      <td>{item.description}</td>
      <th className="thead-min">Nº apartamentos</th>
      <td>{item.numberApartments}</td>
      <th className="thead-min">Cep da rua</th>
      <td>{item.localization.localization.zipCode}</td>
      <th className="thead-min">Rua</th>
      <td>{item.localization.localization.road}</td>
      <th className="thead-min">Bairro</th>
      <td>{item.localization.localization.name}</td>
      <th className="thead-min">Número</th>
      <td>{item.localization.number}</td>
      <th className="thead-min">Opções</th>
      <td>
        <button
          id={`btn-table-condominium-update-${item.id}`}
          type="button"
          aria-label="Atualizar esse condomínio"
          title="Atualizar esse condomínio"
          className="btn btn-primary btn-table-options"
          onClick={(e) => props.toogleClass(item)}
        >
          <span aria-hidden="true">
            <i className="bi bi-clipboard-data"></i>
          </span>
        </button>
        <button
          id={`btn-table-condominium-delete-${item.id}`}
          type="button"
          aria-label="Deletar esse condomínio"
          title="Deletar esse condomínio"
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

export default TableCondominium;
