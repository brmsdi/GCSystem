import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  findByIdOrderService,
  getAllOrderServices,
} from "services/order-service";
import {
  paginationOrderServiceTableAction,
  updateOrderServiceTable,
} from "store/OrderServices/order-services.actions";
import {
  selectCurrentPaginationTableOrderServices,
  selectUpdateTableOrderServiceCurrentState,
} from "store/OrderServices/order-services.selector";
import Swal from "sweetalert2";
import {
  PaginationTableActionSearchPerNumber,
  PropsPagination,
} from "types/pagination";
import { createNumberPages } from "../pagination-create-number-pages";
import PaginationItem from "../pagination-item";

const PaginationTableOrderService = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableActionSearchPerNumber =
    useSelector(selectCurrentPaginationTableOrderServices);
  const [propsPagination, setPropsPagination] = useState<PropsPagination>();
  const updateTableOrderServiceCurrentState = useSelector(
    selectUpdateTableOrderServiceCurrentState
  );
  useEffect(() => {
    try {
      if (currentPaginationTable.search) {
        findByIdOrderService(currentPaginationTable.search).then(
          async (response) => {
            const paginationInformations = await createNumberPages(
              currentPaginationTable.currentPage,
              response.data.totalPages
            );
            setPropsPagination(paginationInformations);
            dispatch(
              updateOrderServiceTable(
                currentPaginationTable.currentPage,
                response.data
              )
            );
          }
        );
      } else {
        getAllOrderServices(currentPaginationTable.currentPage).then(
          async (response) => {
            const paginationInformations = await createNumberPages(
              currentPaginationTable.currentPage,
              response.data.totalPages
            );
            setPropsPagination(paginationInformations);
            dispatch(
              updateOrderServiceTable(
                currentPaginationTable.currentPage,
                response.data
              )
            );
          }
        );
      }
    } catch (error: any) {
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor", "error");
      }
    }
  }, [currentPaginationTable, dispatch, updateTableOrderServiceCurrentState]);

  const changeNumberPage = (pageNumber: number) => {
    dispatch(
      paginationOrderServiceTableAction({
        ...currentPaginationTable,
        currentPage: pageNumber,
      })
    );
  };
  if (propsPagination === undefined) return null;
  return (
    <PaginationItem
      propsPagination={propsPagination}
      changeNumberPage={changeNumberPage}
    />
  );
};

export default PaginationTableOrderService;
