import BarHome from "components/BarHome";
import FormNewContract from "components/Form/FormContract/New";
import FormUpdateContract from "components/Form/FormContract/Update";
import MenuRouterActivity from "components/MenuRouterActivity";
import PaginationTableContract from "components/Pagination/PaginationTableContract";
import SearchContracts from "components/search/SearchContract";
import TableContract from "components/Table/TableContract";
import { ReactElement, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action";
import { removeSelectedContractTableAction, setStateFormContractAction } from "store/Contracts/contracts.actions";
import { selectStateFormContract } from "store/Contracts/contracts.selector";
import { StateFormEnum } from "types/action";
import { TEXT_MENU_ITEM_ID_CONTRACT } from "utils/menu-items";

const ContractsView = () => {
    const dispatch = useDispatch();
    dispatch(changeSelectedSubMenuAsideAction(TEXT_MENU_ITEM_ID_CONTRACT))
    const pagination = <PaginationTableContract />
    var stateForm = useSelector(selectStateFormContract);
    const [currentForm, setCurrentForm] = useState<ReactElement>();

    useEffect(() => {
        switch (stateForm.activity) {
            case StateFormEnum.UPDATE:
                setCurrentForm(<FormUpdateContract />)
                break
            case StateFormEnum.NEW:
                setCurrentForm(<FormNewContract />)
                break
            default:

        }
    }, [stateForm])
    
    return ( 
        <main className="content-main animate-right">
            <div className="home-header">
                <h1>{"Contratos"}</h1>
            </div>
            <MenuRouterActivity />
            <BarHome search={<SearchContracts />}
                removeSelectedContextTableAction={removeSelectedContractTableAction}
                setStateFormContextAction={setStateFormContractAction}
                selectStateFormContext={selectStateFormContract} />
            <div className={stateForm.activity !== StateFormEnum.NOACTION ? 'content-form active' : 'content-form'}>
                {currentForm}
            </div>
            <div className="content-table">
                {pagination}
                <TableContract />
                <div className="pagination-mobile">
                    {}
                </div>
            </div>
        </main>
    )
}


export default ContractsView;