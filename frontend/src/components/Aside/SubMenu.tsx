import { Link } from "react-router-dom";
const SubMenu = (props: { activated?: boolean; id: string; iconSubMenu: string; subMenuText: string; subMenuToolTip: string; }) => {
    let active = props.activated ? ' active ' : ' '; 
    function changeSelectedMenu(id: any) {
      let selectedmenu = document.getElementById(id);
      let subMenus = document.querySelector('.sub-menu.active')
      subMenus?.classList.remove('active')
      selectedmenu?.classList.toggle('active')
    }
    return (
      <div id={props.id} className={`sub-menu${active}animate-right`} onClick={() => changeSelectedMenu(props.id)}>
        <Link to="">
          <span className="sub-menu-icon">
            <i className={props.iconSubMenu}></i>
          </span>
          <span className="text-submenu">{props.subMenuText}</span>
          <span className="tooltip-submenu">{props.subMenuToolTip}</span>
        </Link>
      </div>
    );
  }

  export default SubMenu;