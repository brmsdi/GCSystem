import ButtonMenuOrderServiceTable from "components/Button/ButtonMenuOrderServiceTable";
import Alert from "components/messages";
import ModalDetailsOrderService from "components/Modal/ModalDetailsOrderService";
import { useSelector } from "react-redux";
import { selectAllOrderServices } from "store/OrderServices/order-services.selector";
import { OrderService, PaginationOrderService } from "types/order-service";
import { formatDateForView } from "utils/textFormt";

const TableOrderService = () => {
  const page: PaginationOrderService = useSelector(selectAllOrderServices);

  return (
    <>
      <ModalDetailsOrderService title="Detalhes" />
      <div className="table-responsive table-responsive-order">
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
                  <th scope="col">Data</th>
                  <th scope="col">Data reservada</th>
                  <th scope="col">Data de finalização</th>
                  <th scope="col">Status</th>
                  <th scope="col">#</th>
                </tr>
              </thead>
              <tbody>
                {
                  page?.content?.map((item: OrderService) => {
                    return <ItemTable
                      key={item.id}
                      item={item} />;
                  })
                }
              </tbody>
            </table>
        }
      </div>
    </>
  )
}

interface IProps {
  item: OrderService
}

const ItemTable = (props: IProps) => {
  let item = props.item;
  return (
    <tr>
      <th className="thead-min">ID</th>
      <td>{item.id}</td>
      <th className="thead-min">Data</th>
      <td>{formatDateForView(item.generationDate)}</td>
      <th className="thead-min">Data reservada</th>
      <td>{formatDateForView(item.reservedDate)}</td>
      <th className="thead-min">Data de finalização</th>
      <td>{formatDateForView(item.completionDate)}</td>
      <th className="thead-min">Status</th>
      <td>{item.status.name}</td>
      <th className="thead-min">Opções</th>
      <td className="td-options">
        <ButtonMenuOrderServiceTable item={item} />
      </td>
    </tr>
  )
}

export default TableOrderService;


