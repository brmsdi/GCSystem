import { Link } from "react-router-dom";
const SubMenu = (props) => {
    let active = props.activated ? ' active ' : ' '; 
    function changeSelectedMenu(id) {
      let selectedmenu = document.getElementById(id);
      let subMenus = document.querySelector('.sub-menu.active')
      subMenus.classList.remove('active')
      selectedmenu.classList.toggle('active')
    }
    return (
      <div id={props.id} className={`sub-menu${active}animate-right`} onClick={() => changeSelectedMenu(props.id)}>
        <Link to="">
          <span className="sub-menu-icon">
            <ion-icon name={props.iconSubMenu}></ion-icon>
          </span>
          <span className="text-submenu animate-opac">{props.subMenuText}</span>
          <span className="tooltip-submenu">{props.subMenuToolTip}</span>
        </Link>
      </div>
    );
  }

  export default SubMenu;