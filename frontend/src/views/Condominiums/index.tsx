import BarHome from "components/BarHome"
import FormNewCondominium from "components/Form/FormCondominium/New"
import FormUpdateCondominium from "components/Form/FormCondominium/Update"
import MenuRouterActivity from "components/MenuRouterActivity"
import PaginationTableCondominium from "components/Pagination/PaginationTableCondominium"
import SearchCondominium from "components/search/SearchCondominium"
import TableCondominium from "components/Table/TableCondominium"
import { ReactElement, useEffect, useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action"
import { removeSelectedCondominiumTableAction, setStateFormCondominiumAction } from "store/Condominiums/condiminiums.actions"
import { selectStateFormCondominium } from "store/Condominiums/condiminiums.selectors"
import { StateFormEnum } from "types/action"
import { TEXT_MENU_ITEM_ID_CONDOMINIUM } from "utils/menu-items"

const CondominiumsView = () => {
    const dispatch = useDispatch();
    dispatch(changeSelectedSubMenuAsideAction(TEXT_MENU_ITEM_ID_CONDOMINIUM))
    const pagination = <PaginationTableCondominium />
    var stateForm = useSelector(selectStateFormCondominium);
    const [currentForm, setCurrentForm] = useState<ReactElement>();

    useEffect(() => {
        switch (stateForm.activity) {
            case StateFormEnum.UPDATE:
                setCurrentForm(<FormUpdateCondominium />)
                break
            case StateFormEnum.NEW:
                setCurrentForm(<FormNewCondominium />)
                break
            default:

        }
    }, [stateForm])

    return (
        <main className="content-main animate-right">
            <div className="home-header">
                <h1>{"Condominios"}</h1>
            </div>
            <MenuRouterActivity />
            <BarHome search={<SearchCondominium />}
                removeSelectedContextTableAction={removeSelectedCondominiumTableAction}
                setStateFormContextAction={setStateFormCondominiumAction}
                selectStateFormContext={selectStateFormCondominium} />
            <div className={stateForm.activity !== StateFormEnum.NOACTION ? 'content-form active' : 'content-form'}>
                {currentForm}
            </div>
            <div className="content-table">
                {pagination}
                <TableCondominium />
                <div className="pagination-mobile">
                    { }
                </div>
            </div>
        </main>
    )
}

export default CondominiumsView;