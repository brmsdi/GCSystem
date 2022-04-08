import BarHome from "components/BarHome"
import FormNewEmployee from "components/Form/FormEmployee/New"
import FormUpdateEmployee from "components/Form/FormEmployee/Update"
import MenuRouterActivity from "components/MenuRouterActivity"
import PaginationTableEmployee from "components/Pagination/PaginationTableEmployee"
import SearchEmployee from "components/search/SearchEmployee"
import TableEmployee from "components/Table/TableEmployee"
import { ReactElement, useEffect, useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action"
import { removeSelectedEmployeeTableAction, setStateFormEmployeeAction } from "store/Employees/employees.actions"
import { selectStateFormEmployee } from "store/Employees/employees.selectors"
import { StateFormAction, StateFormEnum } from "types/action"
import { TEXT_MENU_ITEM_ID_EMPLOYEE } from "utils/menu-items"

const EmployeesView = () => {
    const dispatch = useDispatch();
    dispatch(changeSelectedSubMenuAsideAction(TEXT_MENU_ITEM_ID_EMPLOYEE))
    const pagination = <PaginationTableEmployee />
    var stateForm: StateFormAction = useSelector(selectStateFormEmployee);
    const [currentForm, setCurrentForm] = useState<ReactElement>(); 
    useEffect(() => {
        switch (stateForm.activity) {
            case StateFormEnum.UPDATE:
                setCurrentForm(<FormUpdateEmployee />)
            break
            case StateFormEnum.NEW:
                setCurrentForm(<FormNewEmployee />)
            break
            default:
                
        }
    }, [stateForm])
    
    return (
        <main className="content-main animate-right">
            <div className="home-header">
                <h1>{"Funcionários"}</h1>
            </div>
            <MenuRouterActivity />
            <BarHome search={<SearchEmployee />} 
            removeSelectedContextTableAction={removeSelectedEmployeeTableAction} 
            setStateFormContextAction={setStateFormEmployeeAction} 
            selectStateFormContext={selectStateFormEmployee}/>
            <div className={stateForm.activity !== StateFormEnum.NOACTION ? 'content-form active' : 'content-form'} >
                { currentForm }
            </div>
            <div className="content-table">
                {pagination}
                <TableEmployee />
                <div className="pagination-mobile">
                    { }
                </div>
            </div>
        </main>
    )
}

export default EmployeesView;