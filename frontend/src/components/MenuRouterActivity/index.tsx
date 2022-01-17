import { useSelector } from "react-redux";
import { selectStateForm } from "store/Employees/Employees.selectors";
import { StateFormEnum } from "types/Action";
const MenuRouterActivity = () => {
    var stateForm = useSelector(selectStateForm);
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