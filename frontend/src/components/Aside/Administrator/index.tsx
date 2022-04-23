import { useSelector } from "react-redux";
import { selectSelectedSubMenuAside } from "store/Aside/aside.selector";
import { TEXT_MENU_ITEM_ID_CONDOMINIUM, TEXT_MENU_ITEM_ID_CONTRACT, TEXT_MENU_ITEM_ID_DEBT, TEXT_MENU_ITEM_ID_EMPLOYEE, TEXT_MENU_ITEM_ID_LESSEE } from "utils/menu-items";
import { CONDOMINIUMS_HOME_URL, CONTRACTS_HOME_URL, DEBTS_HOME_URL, EMPLOYEES_HOME_URL, LESSEES_HOME_URL } from "utils/urls";
import Aside from "..";
import ItemMenu from "../ItemMenu";
import SubMenu from "../SubMenu";

const AdministratorAside = () => {
    const idSelectedMenu = useSelector(selectSelectedSubMenuAside)
    const data = 
      <><ItemMenu
          menuIcon="bi bi-stack"
          menuText="Gerenciar"
          key={1}
          subMenus={[
            <SubMenu
              iconSubMenu="bi bi-building"
              subMenuText="Condomínio"
              subMenuToolTip="Gerenciar condomínio"
              key={1}
              id={TEXT_MENU_ITEM_ID_CONDOMINIUM}
              idSelectedMenu={idSelectedMenu}
              to={CONDOMINIUMS_HOME_URL} />,
            <SubMenu
              iconSubMenu="bi bi-journal-bookmark-fill"
              subMenuText="Contrato"
              subMenuToolTip="Gerenciar contrato"
              key={4}
              id={TEXT_MENU_ITEM_ID_CONTRACT}
              idSelectedMenu={idSelectedMenu}
              to={CONTRACTS_HOME_URL} />,
            <SubMenu
              iconSubMenu="bi bi-cash"
              subMenuText="Débito"
              subMenuToolTip="Gerenciar débito"
              key={5}
              id={TEXT_MENU_ITEM_ID_DEBT}
              idSelectedMenu={idSelectedMenu}
              to={DEBTS_HOME_URL} />,
            <SubMenu
              iconSubMenu="bi bi-person-badge"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              key={2}
              id={TEXT_MENU_ITEM_ID_EMPLOYEE}
              idSelectedMenu={idSelectedMenu}
              to={EMPLOYEES_HOME_URL} />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Locatário"
              subMenuToolTip="Gerenciar locatário"
              key={3}
              id={TEXT_MENU_ITEM_ID_LESSEE}
              idSelectedMenu={idSelectedMenu}
              to={LESSEES_HOME_URL} />,
          ]} /></>
    return <Aside childrean={data} idSelectedMenu={idSelectedMenu}/>
}

export default AdministratorAside;