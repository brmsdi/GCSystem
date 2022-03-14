import AccountInformation from "components/AccountInformation";
import { EMPLOYEES_HOME_URL, HOME_URL } from "utils/urls";
import ItemMenu from "./ItemMenu";
import SubMenu from "./SubMenu";

const Aside = () => {
  function clickColapseMenu() {
    document.getElementById('header-aside')?.classList.toggle('open');
  }

  // <i class="bi bi-house"></i>
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
              id="sub-menu-home"
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
              id="sub-menu-condominium"
              to=""
            />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              activated
              key={2}
              id="sub-menu-employee"
              to={EMPLOYEES_HOME_URL}
            />,
          ]}
        />
      </div>
      <div className="aside-info-user">
        <AccountInformation />
      </div>
    </aside>
  );
};

export default Aside;
