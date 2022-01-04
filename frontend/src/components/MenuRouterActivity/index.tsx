import { useSelector } from "react-redux";
import { selectStateForm } from "store/Employees/Employees.selectors";
import { StateFormEnum } from "types/Action";

const MenuRouterActivity = () => {
    var stateForm = useSelector(selectStateForm);

    let activity = stateForm.activity !== StateFormEnum.NOACTION ? stateForm.activity : "Home";


    return(
        <div className="menu-router-activity">
          <span>System</span>
          <span> {">"} </span>
          <span>Funcionário</span>
          <span> {">"} </span>
          <span>{activity}</span>
        </div>
    )
}


export default MenuRouterActivity;