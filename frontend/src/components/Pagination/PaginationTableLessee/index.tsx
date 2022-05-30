import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { findByCPFService, getAllLessees } from "services/lessee";
import {
  paginationLesseeTableAction,
  updateLesseesTable,
} from "store/Lessees/lessees.actions";
import {
  selectCurrentPaginationTableLessees,
  selectUpdateTableLesseeCurrentState,
} from "store/Lessees/lessees.selector";
import Swal from "sweetalert2";
import { PaginationTableAction, PropsPagination } from "types/pagination";
import { createNumberPages } from "../pagination-create-number-pages";
import PaginationItem from "../pagination-item";

const PaginationTableLessee = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableAction = useSelector(
    selectCurrentPaginationTableLessees
  );
  const [propsPagination, setPropsPagination] = useState<PropsPagination>();
  const updateTableLesseeCurrentState = useSelector(
    selectUpdateTableLesseeCurrentState
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
              updateLesseesTable(
                currentPaginationTable.currentPage,
                response.data
              )
            );
          }
        );
      } else {
        getAllLessees(currentPaginationTable.currentPage).then(
          async (response) => {
            const paginationInformations = await createNumberPages(
              currentPaginationTable.currentPage,
              response.data.totalPages
            );
            setPropsPagination(paginationInformations);
            dispatch(
              updateLesseesTable(
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
  }, [currentPaginationTable, dispatch, updateTableLesseeCurrentState]);

  const changeNumberPage = (pageNumber: number) => {
    dispatch(
      paginationLesseeTableAction({
        ...currentPaginationTable,
        currentPage: pageNumber,
      })
    );
  };
  return (
    <PaginationItem
      propsPagination={propsPagination}
      changeNumberPage={changeNumberPage}
    />
  );
};

export default PaginationTableLessee;
