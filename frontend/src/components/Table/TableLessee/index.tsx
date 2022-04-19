import Alert from "components/messages";
import { useDispatch, useSelector } from "react-redux";
import { deleteLessee } from "services/lessee";
import { selectLesseeTableAction, setStateFormLesseeAction, updateLesseeTableAction } from "store/Lessees/lessees.actions";
import { selectAllLessees } from "store/Lessees/lessees.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { Lessee, PaginationLessee } from "types/lessee";
const TableLessee = () => {
  const dispatch = useDispatch();
  const page: PaginationLessee = useSelector(selectAllLessees);
  async function clickButtonUpdate(selected: Lessee | undefined) {
    let form = document.querySelector(".content-form");
    if (form != null && selected) {
      if (!form.classList.contains('active')) {
        form.classList.toggle('active')
      }
      dispatch(selectLesseeTableAction(selected))
      dispatch(setStateFormLesseeAction(StateFormEnum.UPDATE))
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
        const data = await deleteLessee(ID)
        Swal.fire('Êbaa!', '' + data, 'success')
        dispatch(updateLesseeTableAction())
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
                <th scope="col">Nome</th>
                <th scope="col">RG</th>
                <th scope="col">CPF</th>
                <th scope="col">E-mail</th>
                <th scope="col">Nº contato</th>
                <th scope="col">#</th>
              </tr>
            </thead>
            <tbody>
              {
                page?.content?.map((item: Lessee) => {
                  return <ItemTable key={item.id} item={item} toogleClass={clickButtonUpdate} clickButtonDelete={clickButtonDelete} />;
                })
              }
            </tbody>
          </table>
      }
    </div> // end table-responsive
  )
}
const ItemTable = (props: { item: Lessee, toogleClass: Function, clickButtonDelete: Function }) => {
  let item = props.item;
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td>{item.id}</td>
      <th className="thead-min">Nome</th>
      <td>{item.name}</td>
      <th className="thead-min">RG</th>
      <td>{item.rg}</td>
      <th className="thead-min">CPF</th>
      <td>{item.cpf}</td>
      <th className="thead-min">E-mail</th>
      <td>{item.email}</td>
      <th className="thead-min">Nº contato</th>
      <td>{item.contactNumber}</td>
      <th className="thead-min">Opções</th>
      <td>
        <button
          id="btn-table-lessee-update"
          type="button"
          aria-label="Atualizar esse locatário"
          title="Atualizar esse locatário"
          className="btn btn-primary btn-table-options"
          onClick={(e) => props.toogleClass(item)}><span aria-hidden="true"><i className="bi bi-clipboard-data"></i></span>
        </button>
        <button
          id="btn-table-lessee-delete"
          type="button"
          aria-label="Deletar esse locatário"
          title="Deletar esse locatário"
          className="btn btn-danger btn-table-options"
          onClick={() => props.clickButtonDelete(item.id)}><span aria-hidden="true"><i className="bi bi-trash"></i></span>
        </button>
      </td>
    </tr>
  )
}

export default TableLessee;
