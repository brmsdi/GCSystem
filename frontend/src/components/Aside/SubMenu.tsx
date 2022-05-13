import { useDispatch } from "react-redux";
import { NavLink } from "react-router-dom";
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action";
interface IProps {
  activated?: boolean;
  id: string;
  idSelectedMenu: string,
  iconSubMenu: string;
  subMenuText: string;
  subMenuToolTip: string;
  to: string
}
const SubMenu = (props: IProps) => {
  const dispatch = useDispatch();
  function changeSelectedMenu(id: any) {
    dispatch(changeSelectedSubMenuAsideAction(id))
    var width = window.innerWidth;
    if (width < 800) document.getElementById('header-aside')?.classList.toggle('open');
  }
  return (
    <div id={props.id} className={`sub-menu ${props.id === props.idSelectedMenu ? 'active' : ''} animate-right`} onClick={() => changeSelectedMenu(props.id)}>
      <NavLink 
      title={props.subMenuToolTip}
      to={props.to}>
        <span className="sub-menu-icon">
          <i className={props.iconSubMenu}></i>
        </span>
        <span className="text-submenu">{props.subMenuText}</span>
        <span className="tooltip-submenu">{props.subMenuToolTip}</span>
      </NavLink>
    </div>
  )
}

export default SubMenu;