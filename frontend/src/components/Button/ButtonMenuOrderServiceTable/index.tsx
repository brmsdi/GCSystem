import { useDispatch } from "react-redux";
import { deleteOrderService } from "services/order-service";
import { detailsModalOrderService, selectOrderServiceTableAction, setStateFormOrderServiceAction, updateOrderServiceTableAction } from "store/OrderServices/order-services.actions";
import Swal from "sweetalert2";
import { StateFormEnum } from "types/action";
import { DropDownMenuItem } from "types/dropdown";
import { OrderService } from "types/order-service";
import ButtonDropDown from "../ButtonDropDown";

interface IProps {
    item: OrderService
}

const ButtonMenuOrderServiceTable = (props: IProps) => {
  const dispatch = useDispatch()

  async function clickButtonUpdate() {
    let form = document.querySelector(".content-form");
    if (form != null && props.item) {
      if (!form.classList.contains('active')) {
        form.classList.toggle('active')
      }
      dispatch(selectOrderServiceTableAction(props.item))
      dispatch(setStateFormOrderServiceAction(StateFormEnum.UPDATE))
    }
  }

  async function clickButtonDelete() {
    if (!props.item.id) return 
    const result = await Swal.fire({
      title: 'Você deseja deletar esse registro?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar'
    })

    if (result.isConfirmed) {
      try {
        const data = await deleteOrderService(props.item.id)
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

  // Alterar 
  // Alterar funcionários
  // Alterar reparos
  // Detalhes
  // Excluir
  let dropMenus : DropDownMenuItem[] = 
  [{
    key: 1,
    title: 'Atualizar OS',
    action: () => clickButtonUpdate()
  },
  {
    key: 4,
    title: 'Detalhes',
    action: () => dispatch(detailsModalOrderService({...props.item}))
  },
  {
    key: 5,
    title: 'Excluir',
    action: () => clickButtonDelete()
  }
  ]
    return <ButtonDropDown ulID={`drop-menu-order-service-${props.item.id}`} itemsMenu={dropMenus} />
}

export default ButtonMenuOrderServiceTable;