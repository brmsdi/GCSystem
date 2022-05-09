import BarHome from "components/BarHome";
import BoxInformations from "components/BoxInformations";
import FormNewRepairRequest from "components/Form/FormRepairRequest/New";
import FormUpdateRepairRequest from "components/Form/FormRepairRequest/Update";
import MenuRouterActivity from "components/MenuRouterActivity";
import PaginationTableRepairRequest from "components/Pagination/PaginationTableRepairRequest";
import SearchRepairRequest from "components/search/SearchRepairRequest";
import TableRepairRequest from "components/Table/TableRepairRequest";
import { ReactElement, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { perStatusRepairRequestService } from "services/repair-request";
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action";
import { removeSelectedRepairRequestTableAction, setStateFormRepairRequestAction } from "store/RepairRequests/repair-requests.actions";
import { selectStateFormRepairRequest } from "store/RepairRequests/repair-requests.selector";
import { StateFormEnum } from "types/action";
import { OpenAndProgressAndLateRepairRequest, OpenAndProgressAndLateRepairRequestEmpty } from "types/open-progress-late-repair-request";
import { TypeActivityText } from "types/text-information";
import { TEXT_MENU_ITEM_ID_REPAIR_REQUEST } from "utils/menu-items";

const RepairRequestsView = () => {
    const dispatch = useDispatch();
    dispatch(changeSelectedSubMenuAsideAction(TEXT_MENU_ITEM_ID_REPAIR_REQUEST))
    const pagination = <PaginationTableRepairRequest />
    var stateForm = useSelector(selectStateFormRepairRequest);
    const [currentForm, setCurrentForm] = useState<ReactElement>();
    const [openAndProgressAndLateRepairRequest, setOpenAndProgressAndLateRepairRequest] = useState<OpenAndProgressAndLateRepairRequest>({...OpenAndProgressAndLateRepairRequestEmpty});
    
    useEffect(() => {
        perStatusRepairRequestService().then(response => setOpenAndProgressAndLateRepairRequest(response.data))
        switch (stateForm.activity) {
            case StateFormEnum.UPDATE:
                setCurrentForm(<FormUpdateRepairRequest />)
                break
            case StateFormEnum.NEW:
                setCurrentForm(<FormNewRepairRequest />)
                break
            default:
        }
    }, [stateForm])
    
    return ( 
        <main className="content-main animate-right">
            <div className="home-header">
                <h1>{"Reparos"}</h1>
            </div>
            <MenuRouterActivity stateForm={selectStateFormRepairRequest} />
            <BarHome 
                titleButtonNew="Nova solicitação de reparo" 
                search={<SearchRepairRequest />}
                removeSelectedContextTableAction={removeSelectedRepairRequestTableAction}
                setStateFormContextAction={setStateFormRepairRequestAction}
                selectStateFormContext={selectStateFormRepairRequest} />
            
            <div className={stateForm.activity !== StateFormEnum.NOACTION ? 'content-form active' : 'content-form'}>
                {currentForm}
            </div>
            <div className="content-table">
                <div className="content-box-informations">
                    <BoxInformations 
                    quantity={openAndProgressAndLateRepairRequest.openRepairRequest} 
                    message="Solicitado" 
                    format 
                    icon="bi bi-file-plus" 
                    activity={TypeActivityText.ACTIVITY_OPEN } />
                    <BoxInformations 
                    quantity={openAndProgressAndLateRepairRequest.progressRepairRequest} 
                    message="Em andamento" 
                    icon="bi bi-activity"
                    activity={TypeActivityText.ACTIVITY_PROGRESS }/>
                    <BoxInformations 
                    quantity={openAndProgressAndLateRepairRequest.lateRepairRequest} 
                    message="Atrasado" 
                    format 
                    icon="bi bi-exclamation-triangle-fill"
                    activity={TypeActivityText.ACTIVITY_LATE}/>
                </div>
                {pagination}
                <TableRepairRequest />
                <div className="pagination-mobile">
                    {}
                </div>
            </div>
        </main>
    )
}


export default RepairRequestsView;