import { Link } from "react-router-dom";

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
        <span className="icon">
          <ion-icon name="business-outline"></ion-icon>
        </span>
        <span className="text">SYSTEM</span>
        <div className="btn-co">
          <button className="btn btn-dark btn-menu" onClick={clickColapseMenu}>
            <span>
              <ion-icon name="menu-outline"></ion-icon>
            </span>
          </button>
        </div>
      </div>
      <hr />
      <div className="aside-body animate-right">
        <div className="menu">
          <div className="menu-title">
            <span className="menu-icon">
              <ion-icon name="layers-outline"></ion-icon>
            </span>
            <span className="menu-text">Gerenciar</span>
          </div>
          <div className="sub-menu">
            <Link to="">
              <span className="sub-menu-icon">
                <ion-icon name="business-outline"></ion-icon>
              </span>
              <span className="text-submenu">Condomínio</span>
              <span className="tooltip-submenu">Gerenciar condomínio</span>
            </Link>
          </div>
          <div className="sub-menu">
          <Link to="">
            <span className="sub-menu-icon">
              <ion-icon name="people-outline"></ion-icon>
            </span>
            <span className="text-submenu">Funcionário</span>
            <span className="tooltip-submenu">Gerenciar funcionário</span>
          </Link>
          </div>
        </div>
        <div className="menu">
          <div className="menu-title">
            <span className="menu-icon">
              <ion-icon name="layers-outline"></ion-icon>
            </span>
            <span className="menu-text">Gerenciar</span>
          </div>
          <div className="sub-menu">
            <Link to="">
              <span className="sub-menu-icon">
                <ion-icon name="business-outline"></ion-icon>
              </span>
              <span className="text-submenu">Condomínio</span>
              <span className="tooltip-submenu">Gerenciar condomínio</span>
            </Link>
          </div>
          <div className="sub-menu">
          <Link to="">
            <span className="sub-menu-icon">
              <ion-icon name="people-outline"></ion-icon>
            </span>
            <span className="text-submenu">Funcionário</span>
            <span className="tooltip-submenu">Gerenciar funcionário</span>
          </Link>
          </div>
        </div>
      </div>
    </aside>
  );
};

export default Aside;
