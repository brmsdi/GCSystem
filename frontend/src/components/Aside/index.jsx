import ItemMenu from "./ItemMenu";
import SubMenu from "./SubMenu";

const Aside = () => {
  function clickColapseMenu() {
    let asidebar = document.querySelector(".asidebar");
    let main = document.querySelector(".content-main");
    asidebar.classList.toggle("active");
    main.classList.toggle("large");
  }

  return (
    <aside className="asidebar animate-right">
      <div className="aside-header animate-right">
        <span className="icon animate-right">
          <ion-icon name="business-outline"></ion-icon>
        </span>
        <span className="text animate-right">SYSTEM</span>
        <div className="btn-co">
          <button className="btn-menu" onClick={clickColapseMenu}>
            <ion-icon name="menu-outline"></ion-icon>
          </button>
        </div>
      </div>
      <hr />
      <div className="aside-body animate-right">
        <ItemMenu
          menuIcon="layers-outline"
          menuText="Gerenciar"
          key={1}
          subMenus={[
            <SubMenu
              iconSubMenu="business-outline"
              subMenuText="Condomínio"
              subMenuToolTip="Gerenciar condomínio"
              key={1}
              id="sub-menu-condominium"
            />,
            <SubMenu
              iconSubMenu="people-outline"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              activated
              key={2}
              id="sub-menu-employee"
            />
          ]}
        />
        <ItemMenu
          menuIcon="layers-outline"
          menuText="Gerenciar"
          key={2}
          subMenus={[
            <SubMenu
              iconSubMenu="business-outline"
              subMenuText="Condomínio"
              subMenuToolTip="Gerenciar condomínio"
              key={3}
              id="sub-menu-condominium2"
            />,
            <SubMenu
              iconSubMenu="people-outline"
              subMenuText="Funcionário"
              subMenuToolTip="Gerenciar funcionário"
              key={4}
              id="sub-menu-employe2"
            />
          ]}
        />
      </div>
    </aside>
  );
}


export default Aside;
