import { useDispatch } from "react-redux";
import { detailsModalOrderService } from "store/OrderServices/order-services.actions";
import { DropDownMenuItem } from "types/dropdown";
import { OrderService } from "types/order-service";
import ButtonDropDown from "../ButtonDropDown";

interface IProps {
    item: OrderService
}

const ButtonMenuOrderServiceTable = (props: IProps) => {
    const dispatch = useDispatch()
  // Alterar 
  // Alterar funcionários
  // Alterar reparos
  // Detalhes
  // Excluir
  let dropMenus : DropDownMenuItem[] = 
  [{
    key: 1,
    title: 'Atualizar OS',
    action: () => {} 
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