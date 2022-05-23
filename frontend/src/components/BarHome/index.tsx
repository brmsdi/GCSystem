import { useDispatch, useSelector } from "react-redux";
import { CurrentStateForm, StateFormEnum } from "types/action";

interface IProps {
  search: any;
  removeSelectedContextTableAction: Function;
  setStateFormContextAction: Function;
  selectStateFormContext: any;
  titleButtonNew: string;
}

// VERIFICAR
const BarHome = (props: IProps) => {
  const dispatch = useDispatch();
  const stateForm: CurrentStateForm = useSelector(props.selectStateFormContext);
  function toogleClass() {
    let form = document.querySelector(".content-form");
    if (form != null) {
      form.classList.toggle("active");
      if (form.classList.contains("active")) {
        dispatch(props.setStateFormContextAction(StateFormEnum.SAVING));
      } else {
        dispatch(props.removeSelectedContextTableAction());
        dispatch(props.setStateFormContextAction(StateFormEnum.NOACTION));
      }
    }
  }
  return (
    <div className="bar-home">
      <button
        id="bar-home-btn-new"
        type="button"
        title={props.titleButtonNew}
        aria-label={props.titleButtonNew}
        className="btn btn-new-employee"
        onClick={() => toogleClass()}
      >
        {stateForm.activity === StateFormEnum.NOACTION ? "Novo" : "Voltar"}
      </button>
      {props.search}
    </div>
  );
};

export default BarHome;
