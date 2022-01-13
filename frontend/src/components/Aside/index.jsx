import AccountInformation from "components/AccountInformation";
import ItemMenu from "./ItemMenu";
import SubMenu from "./SubMenu";

const Aside = () => {
  function clickColapseMenu() {
   
    let headerAside = document.getElementById("header-aside");
    //let main = document.querySelector(".content-main");
    headerAside.classList.toggle("open");
    //main.classList.toggle("large");
  }

  return (
    <aside>
      <div className="btn-co">
          <button onClick={clickColapseMenu}>
            <i className="bi bi-list"></i>
          </button>
      </div>
      <div className="aside-header animate-right">
        <span className="icon animate-right">
          <i className="bi bi-building"></i>
        </span>
        <span className="text animate-right">SYSTEM</span>
      </div>
      <hr />
      <div className="aside-body animate-right">
     
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
            />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              activated
              key={2}
              id="sub-menu-employee"
            />,
          ]}
        />
        
        <ItemMenu
          menuIcon="bi bi-stack"
          menuText="Gerenciar"
          key={2}
          subMenus={[
            <SubMenu
              iconSubMenu="bi bi-building"
              subMenuText="Condomínio"
              subMenuToolTip="Gerenciar condomínio"
              key={3}
              id="sub-menu-condominium2"
            />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              key={4}
              id="sub-menu-employe2"
            />,
          ]}
        />
        <ItemMenu
          menuIcon="bi bi-stack"
          menuText="Gerenciar"
          key={3}
          subMenus={[
            <SubMenu
              iconSubMenu="bi bi-building"
              subMenuText="Condomínio"
              subMenuToolTip="Gerenciar condomínio"
              key={4}
              id="sub-menu-condominium2"
            />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              key={5}
              id="sub-menu-employe2"
            />,
          ]}
        />
        <ItemMenu
          menuIcon="bi bi-stack"
          menuText="Gerenciar"
          key={4}
          subMenus={[
            <SubMenu
              iconSubMenu="bi bi-building"
              subMenuText="Condomínio"
              subMenuToolTip="Gerenciar condomínio"
              key={4}
              id="sub-menu-condominium2"
            />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              key={5}
              id="sub-menu-employe2"
            />,
          ]}
        />
        <ItemMenu
          menuIcon="bi bi-stack"
          menuText="Gerenciar"
          key={6}
          subMenus={[
            <SubMenu
              iconSubMenu="bi bi-building"
              subMenuText="Condomínio"
              subMenuToolTip="Gerenciar condomínio"
              key={6}
              id="sub-menu-condominium2"
            />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              key={7}
              id="sub-menu-employe2"
            />,
          ]}
        />
        <ItemMenu
          menuIcon="bi bi-stack"
          menuText="Gerenciar"
          key={30}
          subMenus={[
            <SubMenu
              iconSubMenu="bi bi-building"
              subMenuText="Condomínio"
              subMenuToolTip="Gerenciar condomínio"
              key={30}
              id="sub-menu-condominium2"
            />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              key={30}
              id="sub-menu-employe2"
            />,
          ]}
        />
         <ItemMenu
          menuIcon="bi bi-stack"
          menuText="Gerenciar"
          key={40}
          subMenus={[
            <SubMenu
              iconSubMenu="bi bi-building"
              subMenuText="Condomínio"
              subMenuToolTip="Gerenciar condomínio"
              key={40}
              id="sub-menu-condominium2"
            />,
            <SubMenu
              iconSubMenu="bi bi-people"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              key={50}
              id="sub-menu-employe2"
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
