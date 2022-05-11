import ButtonDropDown from "components/Button/ButtonDropDown";
import Alert from "components/messages";
import ModalOrderService from "components/Modal/ModalOrderService";
import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { deleteOrderService } from "services/order-service";
import { selectOrderServiceTableAction, setStateFormOrderServiceAction, updateOrderServiceTableAction } from "store/OrderServices/order-services.actions";
import { selectAllOrderServices } from "store/OrderServices/order-services.selector";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { OrderService, OrderServiceEmpty, PaginationOrderService } from "types/order-service";
import { formatDateForView } from "utils/textFormt";

const TableOrderService= () => {
  const dispatch = useDispatch();
  const page: PaginationOrderService = useSelector(selectAllOrderServices);
  const [modalIsOpen, setIsOpen] = useState(false);
  const [orderServiceSelectedFromModal, setOrderServiceSelectedFromModal] = useState<OrderService>(OrderServiceEmpty);

  async function clickButtonUpdate(selected: OrderService | undefined) {
    let form = document.querySelector(".content-form");
    if (form != null && selected) {
      if (!form.classList.contains('active')) {
        form.classList.toggle('active')
      }
      dispatch(selectOrderServiceTableAction(selected))
      dispatch(setStateFormOrderServiceAction(StateFormEnum.UPDATE))
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

  function plusInformations(orderService: OrderService) {
    changeModal()
    setOrderServiceSelectedFromModal({...orderService})
  }

  function changeModal() {
    setIsOpen(!modalIsOpen)
    if (modalIsOpen === false) setOrderServiceSelectedFromModal(OrderServiceEmpty)
  }

  return (
    <>
    {
      modalIsOpen && orderServiceSelectedFromModal.id !== 0 ? 
      (<ModalOrderService 
        modalIsOpen={modalIsOpen}
        openModal={changeModal}
        closeModal={changeModal}
        title={"Informações"}
        item={orderServiceSelectedFromModal} />) : (null)
    }
    
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
                  toogleClass={clickButtonUpdate} 
                  clickButtonDelete={clickButtonDelete} 
                  plusInformations={plusInformations} />;
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
  toogleClass: Function, 
  clickButtonDelete: Function,
  plusInformations: Function
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
      <td>
        <ButtonDropDown ulID={`drop-menu-order-service-${item.id}`} item={[ { key: 1, title: 'Mais informações', action: () => {} },
        { key: 2, title: 'Mais informações 2', action: () => {} } ]} />
      </td>
      
    </tr>
    
  )
}

export default TableOrderService;