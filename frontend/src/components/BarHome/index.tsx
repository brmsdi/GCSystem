import { useDispatch } from "react-redux";
import { setStateFormAction } from "store/Employees/Employees.actions";
import { StateFormEnum } from "types/Action";

const BarHome = (props: {search: any}) => {
  const dispatch = useDispatch();

  function toogleClass() {
    let form = document.querySelector(".content-form");
    if (form != null) {
      form.classList.toggle("active");
      form.classList.contains("active")
        ? dispatch(setStateFormAction(StateFormEnum.SAVING))
        : dispatch(setStateFormAction(StateFormEnum.NOACTION));
    }
  }
  return (
    <div className="bar-home">
      <button
        id="bar-home-btn-new"
        className="btn btn-success btn-new-employee"
        onClick={() => toogleClass()}>
        Novo
      </button>
      {props.search}
    </div>
  )
}

export default BarHome;
