const ItemMenu = (props: {
  menuIcon: string;
  menuText: string;
  subMenus: {};
}) => {
  return (
    <div className="menu">
      <div className="menu-title">
        <span className="menu-icon">
          <i className={props.menuIcon}></i>
        </span>
        <span className="menu-text animate-opac">{props.menuText}</span>
      </div>
      {props.subMenus}
    </div>
  );
};

export default ItemMenu;
