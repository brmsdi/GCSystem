import AccountInformation from "components/AccountInformation";
import { useSelector } from "react-redux";
import { selectSelectedSubMenuAside } from "store/Aside/aside.selector";
import { TEXT_MENU_ITEM_ID_CONDOMINIUM, TEXT_MENU_ITEM_ID_CONTRACT, TEXT_MENU_ITEM_ID_EMPLOYEE, TEXT_MENU_ITEM_ID_HOME, TEXT_MENU_ITEM_ID_LESSEE } from "utils/menu-items";
import { CONDOMINIUMS_HOME_URL, CONTRACTS_HOME_URL, EMPLOYEES_HOME_URL, HOME_URL, LESSEES_HOME_URL } from "utils/urls";
import ItemMenu from "./ItemMenu";
import SubMenu from "./SubMenu";

const Aside = () => {
  const idSelectedMenu = useSelector(selectSelectedSubMenuAside)
  function clickColapseMenu() {
    document.getElementById('header-aside')?.classList.toggle('open');
  }
  return (
    <aside>
      <div className="aside-header">
        <span className="icon animate-right">
          <i className="bi bi-building"></i>
        </span>
        <span className="text animate-right">SYSTEM</span>
        <div className="btn-co">
          <button onClick={() => clickColapseMenu()}>
            <i className="bi bi-list"></i>
          </button>
        </div>
      </div>
      <hr />
      <div className="aside-body animate-right">

        <ItemMenu
          menuIcon="bi bi-house"
          menuText="Home"
          key={90}
          subMenus={[
            <SubMenu
              iconSubMenu="bi bi-house"
              subMenuText="Inicio"
              subMenuToolTip="Página inicial"
              key={90}
              id={TEXT_MENU_ITEM_ID_HOME}
              idSelectedMenu={idSelectedMenu}
              to={HOME_URL}
            />
          ]}
        />

        <ItemMenu
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
              to={CONDOMINIUMS_HOME_URL}
            />,
            <SubMenu
              iconSubMenu="bi bi-journal-bookmark-fill"
              subMenuText="Contrato"
              subMenuToolTip="Gerenciar contrato"
              key={4}
              id={TEXT_MENU_ITEM_ID_CONTRACT}
              idSelectedMenu={idSelectedMenu}
              to={CONTRACTS_HOME_URL}
            />,
            <SubMenu
              iconSubMenu="bi bi-person-badge"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              key={2}
              id={TEXT_MENU_ITEM_ID_EMPLOYEE}
              idSelectedMenu={idSelectedMenu}
              to={EMPLOYEES_HOME_URL}
            />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Locatário"
              subMenuToolTip="Gerenciar locatário"
              key={3}
              id={TEXT_MENU_ITEM_ID_LESSEE}
              idSelectedMenu={idSelectedMenu}
              to={LESSEES_HOME_URL}
            />,
          ]}
        />
      </div>
      <div className="aside-info-user">
        <AccountInformation />
      </div>
    </aside>
  )
}

export default Aside;
