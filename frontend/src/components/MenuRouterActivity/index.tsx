import { useSelector } from "react-redux";
import { selectStateFormEmployee } from "store/Employees/employees.selectors";
import { StateFormEnum } from "types/action";
const MenuRouterActivity = () => {
    var stateForm = useSelector(selectStateFormEmployee);
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