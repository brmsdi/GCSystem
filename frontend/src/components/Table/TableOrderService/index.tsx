import ButtonMenuOrderServiceTable from "components/Button/ButtonMenuOrderServiceTable";
import Alert from "components/messages";
import ModalDetailsOrderService from "components/Modal/ModalDetailsOrderService";
import { useDispatch, useSelector } from "react-redux";
import { deleteOrderService } from "services/order-service";
import { updateOrderServiceTableAction } from "store/OrderServices/order-services.actions";
import { selectAllOrderServices } from "store/OrderServices/order-services.selector";
import Swal from "sweetalert2";
import { OrderService, PaginationOrderService } from "types/order-service";
import { formatDateForView } from "utils/textFormt";

const TableOrderService = () => {
  const dispatch = useDispatch();
  const page: PaginationOrderService = useSelector(selectAllOrderServices);

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
        const data = await deleteOrderService(ID)
        Swal.fire('Êbaa!', '' + data, 'success')
        dispatch(updateOrderServiceTableAction())
      } catch (error: any) {
        if (!error.response) {
          Swal.fire("Oops!", "Sem conexão com o servidor!", "error");
        } else if (error.response.data.errors) {
          let message = error.response.data.errors[0].message;
          Swal.fire("Oops!", "" + message, "error");
        } else {
          Swal.fire("Oops!", "Erro desconhecido. Consulte o log", "error");
        }
      }
    }
  }

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
                      item={item}
                      clickButtonDelete={clickButtonDelete} />;
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
  item: OrderService,
  clickButtonDelete: Function
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


