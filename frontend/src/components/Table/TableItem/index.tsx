import Alert from "components/messages";
import { Item } from "types/item";
import { formatCoinPTBRForView } from "utils/coin-format";

interface IPropsTable {
    item: Item[];
    removeItem: Function;
}
const TableItem = (props: IPropsTable) => {

    var countItems = 0;

    return (
        <div className="table-responsive">
          {
            (props.item.length === 0) ?
              (
                (<Alert msg="Nenhum item adicionado!" />)
              )
              :
              <table className="table table-striped">
                <thead className="thead-max">
                  <tr>
                    <th scope="col">Descrição</th>
                    <th scope="col">Quantidade</th>
                    <th scope="col">Valor</th>
                    <th scope="col">#</th>
                  </tr>
                </thead>
                <tbody>
                  {
                    props.item?.map((item: Item) => {
                    countItems++;
                      return <ItemTable key={countItems} item={item} removeItem={props.removeItem} />;
                    })
                  }
                </tbody>
              </table>
          }
        </div> // end table-responsive
      )
}

interface IPropsItemTable {
    item: Item;
    removeItem: Function;
}
  
const ItemTable = (props: IPropsItemTable) => {
    let item = props.item;
    return (
      <tr>
        <th className="thead-min">Descrição</th>
        <td>{item.description}</td>
        <th className="thead-min">Quantidade</th>
        <td>{item.quantity}</td>
        <th className="thead-min">Valor</th>
        <td>{formatCoinPTBRForView(item.value)}</td>
        <th className="thead-min">Opções</th>
        <td>
          <button
            id="btn-table-remove-item-from-list"
            type="button"
            aria-label="Remover este item"
            title="Remover este item"
            className="btn btn-danger btn-table-options"
            onClick={() => props.removeItem(item)}
          >
            <span aria-hidden="true">
              <i className="bi bi-trash"></i>
            </span>
          </button>
        </td>
      </tr>
    );
  }

export default TableItem;