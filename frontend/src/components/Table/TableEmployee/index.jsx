import { useSelector } from "react-redux";
import { selectAllEmployees } from "store/Employees/Employees.selectors";

const TableEmployee = () => {
  const employees = useSelector(selectAllEmployees);
  var data = employees.map(item => {
    return <ItemTable key={item.id} item={item} />;
  })
  
  return (
    <div className="table-responsive">
      <table className="table table-striped">
        <thead>
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
          {data}
        </tbody>
      </table>
    </div>
  );
}

const ItemTable = ({ item }) => {
  return (
    <tr>
      <th scope="row">{item.id}</th>
      <td>{item.name}</td>
      <td>{item.RG}</td>
      <td>{item.CPF}</td>
      <td>{item.email}</td>
      <td>{item.role}</td>
      <td>{item.specialty}</td>
      <td>{item.hiringDate}</td>
    </tr>
  );
};

export default TableEmployee;
