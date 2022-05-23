import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { findByCPFService, getAllDebts } from "services/debt";
import {
  paginationDebtTableAction,
  updateDebtsTable,
} from "store/Debts/debts.actions";
import {
  selectCurrentPaginationTableDebts,
  selectUpdateTableDebtCurrentState,
} from "store/Debts/debts.selector";
import Swal from "sweetalert2";
import { PaginationTableAction, PropsPagination } from "types/pagination";
import { createNumberPages } from "../pagination-create-number-pages";
import PaginationItem from "../pagination-item";

const PaginationTableDebt = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableAction = useSelector(
    selectCurrentPaginationTableDebts
  );
  const [propsPagination, setPropsPagination] = useState<PropsPagination>();
  const updateTableDebtCurrentState = useSelector(
    selectUpdateTableDebtCurrentState
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
              updateDebtsTable(
                currentPaginationTable.currentPage,
                response.data
              )
            );
          }
        );
      } else {
        getAllDebts(currentPaginationTable.currentPage).then(
          async (response) => {
            const paginationInformations = await createNumberPages(
              currentPaginationTable.currentPage,
              response.data.totalPages
            );
            setPropsPagination(paginationInformations);
            dispatch(
              updateDebtsTable(
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
  }, [currentPaginationTable, dispatch, updateTableDebtCurrentState]);

  const changeNumberPage = (pageNumber: number) => {
    dispatch(
      paginationDebtTableAction({
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

export default PaginationTableDebt;
