import AccountInformation from "components/AccountInformation";
import { TEXT_MENU_ITEM_ID_HOME } from "utils/menu-items";
import { HOME_URL } from "utils/urls";
import ItemMenu from "./ItemMenu";
import SubMenu from "./SubMenu";

interface IProps {
  childrean: any;
  idSelectedMenu: string;
}

const Aside = (props: IProps) => {
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
          <button 
            id="btn-close-open-aside"
            onClick={() => clickColapseMenu()}>
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
              subMenuText="Início"
              subMenuToolTip="Página inicial"
              key={90}
              id={TEXT_MENU_ITEM_ID_HOME}
              idSelectedMenu={props.idSelectedMenu}
              to={HOME_URL} />
          ]} />
        {props.childrean}
      </div>
      <div className="aside-info-user">
        <AccountInformation />
      </div>
    </aside>
  )
}

export default Aside;
