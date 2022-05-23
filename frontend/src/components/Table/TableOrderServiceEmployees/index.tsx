import Alert from "components/messages";
import { Employee } from "types/employee";

interface IPropsTable {
  item: Employee[];
}

const TableOrderServiceEmployees = (props: IPropsTable) => {
  return (
    <div className="table-responsive">
      {props.item.length === 0 ? (
        <Alert msg="Nenhum funcionário adicionado!" />
      ) : (
        <table className="table table-striped">
          <thead className="thead-max">
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Nome do funcionário</th>
              <th scope="col">Cargo</th>
            </tr>
          </thead>
          <tbody>
            {props.item?.map((item: Employee) => {
              return <ItemTable key={item.id} item={item} />;
            })}
          </tbody>
        </table>
      )}
    </div> // end table-responsive
  );
};

interface IPropsItemTable {
  item: Employee;
}

const ItemTable = (props: IPropsItemTable) => {
  let item = props.item;
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td className="td-column-simple">{item.id}</td>
      <th className="thead-min">Nome do funcionário</th>
      <td className="td-column-simple">{item.name}</td>
      <th className="thead-min">Cargo</th>
      <td className="td-column-simple">{item.role.name}</td>
    </tr>
  );
};

export default TableOrderServiceEmployees;
