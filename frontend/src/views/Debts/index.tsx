import BarHome from "components/BarHome";
import FormNewDebt from "components/Form/FormDebt/New";
import FormUpdateDebt from "components/Form/FormDebt/Update";
import MenuRouterActivity from "components/MenuRouterActivity";
import PaginationTableDebt from "components/Pagination/PaginationTableDebt";
import SearchDebts from "components/search/SearchDebt";
import TableDebt from "components/Table/TableDebt";
import { ReactElement, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action";
import { removeSelectedDebtTableAction, setStateFormDebtAction } from "store/Debts/debts.actions";
import { selectStateFormDebt } from "store/Debts/debts.selector";
import { StateFormEnum } from "types/action";
import { TEXT_MENU_ITEM_ID_DEBT } from "utils/menu-items";

const DebtsView = () => {
    const dispatch = useDispatch();
    dispatch(changeSelectedSubMenuAsideAction(TEXT_MENU_ITEM_ID_DEBT))
    const pagination = <PaginationTableDebt />
    var stateForm = useSelector(selectStateFormDebt);
    const [currentForm, setCurrentForm] = useState<ReactElement>();

    useEffect(() => {
        switch (stateForm.activity) {
            case StateFormEnum.UPDATE:
                setCurrentForm(<FormUpdateDebt />)
                break
            case StateFormEnum.NEW:
                setCurrentForm(<FormNewDebt />)
                break
            default:

        }
    }, [stateForm])
    
    return ( 
        <main className="content-main animate-right">
            <div className="home-header">
                <h1>{"Débitos"}</h1>
            </div>
            <MenuRouterActivity stateForm={selectStateFormDebt} />
            <BarHome 
                titleButtonNew="Novo registro de débito" 
                search={<SearchDebts />}
                removeSelectedContextTableAction={removeSelectedDebtTableAction}
                setStateFormContextAction={setStateFormDebtAction}
                selectStateFormContext={selectStateFormDebt} />
            <div className={stateForm.activity !== StateFormEnum.NOACTION ? 'content-form active' : 'content-form'}>
                {currentForm}
            </div>
            <div className="content-table">
                {pagination}
                <TableDebt />
                <div className="pagination-mobile">
                    {}
                </div>
            </div>
        </main>
    )
}


export default DebtsView;