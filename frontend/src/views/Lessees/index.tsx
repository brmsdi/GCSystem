import BarHome from "components/BarHome";
import FormNewLessee from "components/Form/FormLessee/New";
import FormUpdateLessee from "components/Form/FormLessee/Update";
import MenuRouterActivity from "components/MenuRouterActivity";
import PaginationTableLessee from "components/Pagination/PaginationTableLessee";
import SearchLessee from "components/search/SearchLessee";
import TableLessee from "components/Table/TableLessee";
import { ReactElement, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action";
import { removeSelectedLesseeTableAction, setStateFormLesseeAction } from "store/Lessees/lessees.actions";
import { selectStateFormLessee } from "store/Lessees/lessees.selector";
import { StateFormEnum } from "types/action";
import { TEXT_MENU_ITEM_ID_LESSEE } from "utils/menu-items";

const LesseeView = () => {
    const dispatch = useDispatch();
    dispatch(changeSelectedSubMenuAsideAction(TEXT_MENU_ITEM_ID_LESSEE))
    const pagination = <PaginationTableLessee />
    var stateForm = useSelector(selectStateFormLessee);
    const [currentForm, setCurrentForm] = useState<ReactElement>(); 

    useEffect(() => {
        switch (stateForm.activity) {
            case StateFormEnum.UPDATE:
                setCurrentForm(<FormUpdateLessee />)
            break
            case StateFormEnum.NEW:
                setCurrentForm(<FormNewLessee />)
            break
            default:
        }
    }, [stateForm])

    return (
        <main className="content-main animate-right">
            <div className="home-header">
                <h1>{"Locatários"}</h1>
            </div>
            <MenuRouterActivity />
            <BarHome search={<SearchLessee />} 
            removeSelectedContextTableAction={removeSelectedLesseeTableAction} 
            setStateFormContextAction={setStateFormLesseeAction}
            selectStateFormContext={selectStateFormLessee}/>
            <div className="content-form">
                {currentForm}
            </div>
            <div className="content-table">
                {pagination}
                <TableLessee />
                <div className="pagination-mobile">
                    { }
                </div>
            </div>
        </main>
    )

}

export default LesseeView;