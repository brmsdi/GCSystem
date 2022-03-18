import { useDispatch, useSelector } from "react-redux";
import { removeSelectedEmployeeTableAction, setStateFormAction } from "store/Employees/Employees.actions";
import { selectStateForm } from "store/Employees/Employees.selectors";
import { CurrentStateForm, StateFormEnum } from "types/Action";

const BarHome = (props: {search: any}) => {
  const dispatch = useDispatch();
  const stateForm: CurrentStateForm = useSelector(selectStateForm)
  function toogleClass() {
    let form = document.querySelector(".content-form");
    if (form != null) {
      form.classList.toggle("active");
      if (form.classList.contains('active')) {
        dispatch(setStateFormAction(StateFormEnum.SAVING))

      } else {
        dispatch(removeSelectedEmployeeTableAction())
        dispatch(setStateFormAction(StateFormEnum.NOACTION))
      }
    }
  }
  return (
    <div className="bar-home">
      <button
        id="bar-home-btn-new"
        className="btn btn-success btn-new-employee"
        onClick={() => toogleClass()}>
        { stateForm.activity === StateFormEnum.NOACTION ? 'Novo' : 'Voltar' }
      </button>
      {props.search}
    </div>
  )
}

export default BarHome;