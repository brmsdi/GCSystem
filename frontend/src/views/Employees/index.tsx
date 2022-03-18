import BarHome from "components/BarHome"
import FormNewEmployee from "components/Form/FormEmployee/New"
import FormUpdateEmployee from "components/Form/FormEmployee/Update"
import MenuRouterActivity from "components/MenuRouterActivity"
import PaginationTableEmployee from "components/Pagination/PaginationTableEmployee"
import SearchEmployee from "components/search/SearchEmployee"
import TableEmployee from "components/Table/TableEmployee"
import { ReactElement, useEffect, useState } from "react"
import { useSelector } from "react-redux"
import { selectStateForm } from "store/Employees/Employees.selectors"
import { StateFormEnum } from "types/Action"

const EmployeesView = () => {
    const pagination = <PaginationTableEmployee />
    var stateForm = useSelector(selectStateForm);
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
            <BarHome search={<SearchEmployee />} />
            <div className="content-form">
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