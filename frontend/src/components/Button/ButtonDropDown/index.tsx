import { Link } from "react-router-dom";
import { DropDownMenuItem } from "types/dropdown";

interface IProps {
  ulID: string;
  itemsMenu: DropDownMenuItem[];
}

const ButtonDropDown = (props: IProps) => {
  function openAndCloseDrop() {
    const element = document.getElementById(props.ulID);
    if (element) {
      element.classList.toggle("show");
      document
        .getElementById("menu-drop-" + props.ulID)
        ?.classList.toggle("show");
      if (element.classList.contains("show")) {
        document
          .getElementById("menu-drop-open-and-close-" + props.ulID)
          ?.classList.toggle("open");
      } else {
        document
          .getElementById("menu-drop-open-and-close-" + props.ulID)
          ?.classList.remove("open");
      }
    }
  }

  function clickItemList(action: Function) {
    openAndCloseDrop();
    action();
  }

  return (
    <>
      <div
        id={`menu-drop-open-and-close-${props.ulID}`}
        className="menu-drop-open-and-close"
        onClick={openAndCloseDrop}
      ></div>
      <div id={`menu-drop-${props.ulID}`} className="menu-drop">
        <button
          className="btn btn-secondary"
          type="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
          aria-label="Opções"
          title="Opções"
          onClick={openAndCloseDrop}
        >
          <span aria-hidden="true">
            <i className="bi bi-three-dots"></i>
          </span>
        </button>
        <ul id={`${props.ulID}`} className="menu-drop-list">
          {props.itemsMenu.map((item) => (
            <li key={item.key} onClick={() => clickItemList(item.action)}>
              <Link className="menu-drop-item" to={""}>
                {item.title}
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
};

export default ButtonDropDown;
