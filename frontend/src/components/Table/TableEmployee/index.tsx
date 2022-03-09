import { useSelector } from "react-redux";
import { selectAllEmployees } from "store/Employees/Employees.selectors";
import { Employee } from "types/Employee";
const TableEmployee = () => {
  //const pageSelected : number = useSelector(selectCurrentPaginationTableEmployees)
  /*
  const[page, setPage] = useState<Pagination>()

  useEffect(() => {
    getAllEmployees(pageSelected)
    .then(response => {
      setPage(response.data)
    })
    .catch(error => {
      if (!error.response) {
        Swal.fire('Oops!', 'Sem conexão com o servidor', 'error')
      }
    })
  }, [pageSelected])  */
 const page = useSelector(selectAllEmployees);
  
  return (
    <div className="table-responsive">
      <table className="table table-striped">
        <thead className="thead-max">
          <tr>
            <th scope="col">#</th>
            <th scope="col">Nome</th>
            <th scope="col">RG</th>
            <th scope="col">CPF</th>
            <th scope="col">E-mail</th>
            <th scope="col">Cargo</th>
            <th scope="col">Especialidade</th>
            <th scope="col">Contratação</th>
          </tr>
        </thead>
        <tbody>
        {
          page?.content?.map((item : Employee) => {
            return <ItemTable key={item.id} item={item} />;
          }) 
        }
        </tbody>
      </table>
    </div>
  )
}
const ItemTable = (props: {item: Employee }) => {
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
      <td>{item.hiringDate}</td>
    </tr>
  ) 
} 

export default TableEmployee;
