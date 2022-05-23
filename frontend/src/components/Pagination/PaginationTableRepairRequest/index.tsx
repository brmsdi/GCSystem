import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  findByCPFService,
  getAllRepairRequests,
} from "services/repair-request";
import {
  paginationRepairRequestTableAction,
  updateRepairRequestTable,
} from "store/RepairRequests/repair-requests.actions";
import {
  selectCurrentPaginationTableRepairRequests,
  selectUpdateTableRepairRequestCurrentState,
} from "store/RepairRequests/repair-requests.selector";
import Swal from "sweetalert2";
import { PaginationTableAction, PropsPagination } from "types/pagination";
import { createNumberPages } from "../pagination-create-number-pages";
import PaginationItem from "../pagination-item";

const PaginationTableRepairRequest = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableAction = useSelector(
    selectCurrentPaginationTableRepairRequests
  );
  const [propsPagination, setPropsPagination] = useState<PropsPagination>();
  const updateTableRepairRequestCurrentState = useSelector(
    selectUpdateTableRepairRequestCurrentState
  );
  useEffect(() => {
    try {
      if (currentPaginationTable.search) {
        findByCPFService(currentPaginationTable.search).then(
          async (response) => {
            const paginationInformations = await createNumberPages(
              currentPaginationTable.currentPage,
              response.data.totalPages
            );
            setPropsPagination(paginationInformations);
            dispatch(
              updateRepairRequestTable(
                currentPaginationTable.currentPage,
                response.data
              )
            );
          }
        );
      } else {
        getAllRepairRequests(currentPaginationTable.currentPage).then(
          async (response) => {
            const paginationInformations = await createNumberPages(
              currentPaginationTable.currentPage,
              response.data.totalPages
            );
            setPropsPagination(paginationInformations);
            dispatch(
              updateRepairRequestTable(
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
  }, [currentPaginationTable, dispatch, updateTableRepairRequestCurrentState]);

  const changeNumberPage = (pageNumber: number) => {
    dispatch(
      paginationRepairRequestTableAction({
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

export default PaginationTableRepairRequest;
