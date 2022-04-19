import { useSelector } from "react-redux";
import { CurrentStateForm, StateFormEnum } from "types/action";
interface IProps {
  stateForm: any
}
const MenuRouterActivity = (props: IProps) => {
    var stateForm: CurrentStateForm = useSelector(props.stateForm);
    let activity = stateForm.activity !== StateFormEnum.NOACTION ? stateForm.activity : "Home";
    return(
        <div className="menu-router-activity">
          <div>
            <span>&#x3E;</span>
            <span id="menu-router-activity">{activity}</span>
          </div>
        </div>
    )
}

export default MenuRouterActivity;