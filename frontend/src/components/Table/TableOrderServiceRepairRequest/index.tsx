import Alert from "components/messages";
import { RepairRequest } from "types/repair-request";

interface IPropsTable {
  item: RepairRequest[];
}

const TableOrderServiceRepairRequest = (props: IPropsTable) => {
  return (
    <div className="table-responsive">
      {props.item.length === 0 ? (
        <Alert msg="Nenhuma solicitação de reparo adicionada!" />
      ) : (
        <table className="table table-striped">
          <thead className="thead-max">
            <tr>
              <th scope="col">ID</th>
              <th scope="col">Descrição</th>
              <th scope="col">Tipo de problema</th>
            </tr>
          </thead>
          <tbody>
            {props.item?.map((item: RepairRequest) => {
              return <ItemTable key={item.id} item={item} />;
            })}
          </tbody>
        </table>
      )}
    </div> // end table-responsive
  );
};

interface IPropsRepairRequestTable {
  item: RepairRequest;
}

const ItemTable = (props: IPropsRepairRequestTable) => {
  let item = props.item;
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td className="td-column-simple">{item.id}</td>
      <th className="thead-min">Descrição</th>
      <td className="td-column-simple">{item.problemDescription}</td>
      <th className="thead-min">Tipo de problema</th>
      <td className="td-column-simple">{item.typeProblem.name}</td>
    </tr>
  );
};

export default TableOrderServiceRepairRequest;
