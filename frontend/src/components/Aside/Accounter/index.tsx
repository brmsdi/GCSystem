import { useSelector } from "react-redux";
import { selectSelectedSubMenuAside } from "store/Aside/aside.selector";
import { TEXT_MENU_ITEM_ID_DEBT } from "utils/menu-items";
import { DEBTS_HOME_URL } from "utils/urls";
import Aside from "..";
import ItemMenu from "../ItemMenu";
import SubMenu from "../SubMenu";

const CounterAside = () => {
    const idSelectedMenu = useSelector(selectSelectedSubMenuAside)
    const data = 
      <><ItemMenu
          menuIcon="bi bi-stack"
          menuText="Gerenciar"
          key={1}
          subMenus={[
            <SubMenu
              iconSubMenu="bi bi-cash"
              subMenuText="Débitos"
              subMenuToolTip="Gerenciar débitos"
              key={5}
              id={TEXT_MENU_ITEM_ID_DEBT}
              idSelectedMenu={idSelectedMenu}
              to={DEBTS_HOME_URL} />,
          ]} /></>
    return <Aside childrean={data} idSelectedMenu={idSelectedMenu}/>
}

export default CounterAside;