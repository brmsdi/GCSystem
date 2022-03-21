import Alert from "components/messages";
import { useDispatch, useSelector } from "react-redux";
import { deleteEmployee } from "services/Employee";
import { selectEmployeeTableAction, setStateFormAction } from "store/Employees/Employees.actions";
import { selectAllEmployees } from "store/Employees/Employees.selectors";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/Action";
import { Employee, Pagination } from "types/Employee";
import { formatDateForView } from "utils/textFormt";
const TableEmployee = () => {
  const dispatch = useDispatch();
  const page: Pagination = useSelector(selectAllEmployees);
  async function clickButtonUpdate(selected: Employee | undefined) {
    let form = document.querySelector(".content-form");
    if (form != null && selected) {
      if (!form.classList.contains('active')) {
        form.classList.toggle('active')
      }
      dispatch(selectEmployeeTableAction(selected))
      dispatch(setStateFormAction(StateFormEnum.UPDATE))
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
        const data = await deleteEmployee(ID)
        Swal.fire('Êbaa!', '' + data, 'success')
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
                <th scope="col">Cargo</th>
                <th scope="col">Especialidade</th>
                <th scope="col">Contratação</th>
                <th scope="col">#</th>
              </tr>
            </thead>
            <tbody>
              {
                page?.content?.map((item: Employee) => {
                  return <ItemTable key={item.id} item={item} toogleClass={clickButtonUpdate} clickButtonDelete={clickButtonDelete} />;
                })
              }
            </tbody>
          </table>
      }
    </div> // end table-responsive
  )
}
const ItemTable = (props: { item: Employee, toogleClass: Function, clickButtonDelete: Function }) => {
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
      <th className="thead-min">Cargo</th>
      <td>{item.role.name}</td>
      <th className="thead-min">Especialidade</th>
      <td>{
        item.specialties?.map((specialty) => specialty.name)
      }</td>
      <th className="thead-min">Contratação</th>
      <td>{formatDateForView(item.hiringDate)}</td>
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

export default TableEmployee;
