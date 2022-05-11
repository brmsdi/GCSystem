import { Link } from "react-router-dom";

export type Item = {
    key: number;
    title: string;
    action: Function;
} 

interface IProps {
    ulID: string;
    item: Item[]
}

const ButtonDropDown = (props: IProps) => {

    function openAndCloseDrop() {
        document.getElementById(props.ulID)?.classList.toggle('show')
        document.getElementById('menu-drop-' + props.ulID)?.classList.toggle('show')
    }

    function blurDrop() 
    {
        document.getElementById(props.ulID)?.classList.remove('show')
        document.getElementById('menu-drop-' + props.ulID)?.classList.remove('show')
    }

    return (
      <div id={`menu-drop-${props.ulID}`} className="menu-drop">
        <button
          className="btn btn-secondary"
          type="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
          aria-label="Opções"
          title="Opções"
          onClick={openAndCloseDrop}
          onBlur={blurDrop}
        ><span aria-hidden="true"><i className="bi bi-three-dots"></i></span>
        </button>
        <ul
            id={`${props.ulID}`}
            className="menu-drop-list"
        >
          {props.item.map((item) => (
            <li key={item.key}>
              <Link className="menu-drop-item" to="#">
                {item.title}
              </Link>
            </li>
          ))}
        </ul>
      </div>
    );
}

export default ButtonDropDown;