import { useDispatch } from "react-redux";
import { detailsModalOrderService, selectOrderServiceTableAction, setStateFormOrderServiceAction } from "store/OrderServices/order-services.actions";
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
    key: 2,
    title: 'Alterar funcionários',
    action: () => {}
  },
  {
    key: 3,
    title: 'Alterar reparos',
    action: () => {}
  },
  {
    key: 4,
    title: 'Detalhes',
    action: () => dispatch(detailsModalOrderService({...props.item}))
  },
  {
    key: 5,
    title: 'Excluir',
    action: () => {}
  }
  ]
    return <ButtonDropDown ulID={`drop-menu-order-service-${props.item.id}`} itemsMenu={dropMenus} />
}

export default ButtonMenuOrderServiceTable;