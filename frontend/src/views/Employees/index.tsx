import BarHome from "components/BarHome"
import FormEmployee from "components/Form/FormEmployee"
import MenuRouterActivity from "components/MenuRouterActivity"
import PaginationTableEmployee from "components/Pagination/PaginationTableEmployee"
import SearchEmployee from "components/search/SearchEmployee"
import TableEmployee from "components/Table/TableEmployee"

const EmployeesView = () => {
    const pagination = <PaginationTableEmployee />
    return (
        <main className="content-main animate-right">
            <div className="home-header">
                <h1>{"Funcionários"}</h1>
            </div>
            <MenuRouterActivity />
            <BarHome search={<SearchEmployee />} />
            <div className="content-form">
                <FormEmployee />
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