import BarHome from "components/BarHome";
import FormNewOrderService from "components/Form/FormOrderService/New";
import FormUpdateOrderService from "components/Form/FormOrderService/Update";
import MenuRouterActivity from "components/MenuRouterActivity";
import PaginationTableOrderService from "components/Pagination/PaginationTableOrderService";
import SearchOrderService from "components/search/SearchOrderService";
import TableOrderService from "components/Table/TableOrderService";
import { ReactElement, useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { changeSelectedSubMenuAsideAction } from "store/Aside/aside.action";
import { removeSelectedOrderServiceTableAction, setStateFormOrderServiceAction } from "store/OrderServices/order-services.actions";
import { selectStateFormOrderService } from "store/OrderServices/order-services.selector";
import { StateFormEnum } from "types/action";
import { TEXT_MENU_ITEM_ID_ORDER_SERVICE } from "utils/menu-items";

const OrderServiceView = () => {
    const dispatch = useDispatch();
    dispatch(changeSelectedSubMenuAsideAction(TEXT_MENU_ITEM_ID_ORDER_SERVICE))
    const pagination = <PaginationTableOrderService />
    var stateForm = useSelector(selectStateFormOrderService);
    const [currentForm, setCurrentForm] = useState<ReactElement>();

    useEffect(() => {
        switch (stateForm.activity) {
            case StateFormEnum.UPDATE:
               setCurrentForm(<FormUpdateOrderService />)
                break
            case StateFormEnum.NEW:
               setCurrentForm(<FormNewOrderService />)
                break
            default:
        }
    }, [stateForm])
    
    return ( 
        <main className="content-main animate-right">
            <div className="home-header">
                <h1>{"Ordens de serviço"}</h1>
            </div>
            <MenuRouterActivity stateForm={selectStateFormOrderService} />
            <BarHome 
                titleButtonNew="Nova ordem de serviço" 
                search={<SearchOrderService />}
                removeSelectedContextTableAction={removeSelectedOrderServiceTableAction}
                setStateFormContextAction={setStateFormOrderServiceAction}
                selectStateFormContext={selectStateFormOrderService} />
            
            <div className={stateForm.activity !== StateFormEnum.NOACTION ? 'content-form active' : 'content-form'}>
                { currentForm }
            </div>
            <div className="content-table">
                {pagination}
                <TableOrderService />
                <div className="pagination-mobile">
                    {}
                </div>
            </div>
            
        </main>
    )
}


export default OrderServiceView;