const ItemMenu = (props) => {
    return (
      <div className="menu">
        <div className="menu-title">
          <span className="menu-icon">
            <ion-icon name={props.menuIcon}></ion-icon>
          </span>
          <span className="menu-text animate-opac">{props.menuText}</span>
        </div>
        {props.subMenus}
      </div>
    );
  }

  export default ItemMenu;