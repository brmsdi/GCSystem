interface IProps {
  menuIcon: string;
  menuText: string;
  subMenus: {};
}
const ItemMenu = (props: IProps) => {
  return (
    <div className="menu">
      <div className="menu-title">
        <span className="menu-icon">
          <i className={props.menuIcon}></i>
        </span>
        <span className="menu-text">{props.menuText}</span>
      </div>
      {props.subMenus}
    </div>
  );
};

export default ItemMenu;
