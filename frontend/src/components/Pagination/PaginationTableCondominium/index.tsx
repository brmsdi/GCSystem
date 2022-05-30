import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { findByNameService, getAllCondominiums } from "services/condominium";
import {
  paginationCondominiumTableAction,
  updateCondominiumTable,
} from "store/Condominiums/condiminiums.actions";
import {
  selectCurrentPaginationTableCondominiums,
  selectUpdateTableCondominiumCurrentState,
} from "store/Condominiums/condiminiums.selectors";
import Swal from "sweetalert2";
import { PaginationTableAction, PropsPagination } from "types/pagination";
import { createNumberPages } from "../pagination-create-number-pages";
import PaginationItem from "../pagination-item";

const PaginationTableCondominium = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableAction = useSelector(
    selectCurrentPaginationTableCondominiums
  );
  const [propsPagination, setPropsPagination] = useState<PropsPagination>();
  const updateTableCondominiumCurrentState = useSelector(
    selectUpdateTableCondominiumCurrentState
  );

  useEffect(() => {
    try {
      if (currentPaginationTable.search) {
        findByNameService(currentPaginationTable.search).then(async (response) => {
         const paginationInformations = await createNumberPages(
            currentPaginationTable.currentPage,
            response.data.totalPages
          );
          setPropsPagination(paginationInformations)
          dispatch(
            updateCondominiumTable(
              currentPaginationTable.currentPage,
              response.data
            )
          );
        });
      } else {
        getAllCondominiums(currentPaginationTable.currentPage).then(
          async (response) => {
            const paginationInformations = await createNumberPages(
              currentPaginationTable.currentPage,
              response.data.totalPages
            );
            setPropsPagination(paginationInformations)
            dispatch(
              updateCondominiumTable(
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
  }, [currentPaginationTable, dispatch, updateTableCondominiumCurrentState]);

  const changeNumberPage = (pageNumber: number) => {
    dispatch(
      paginationCondominiumTableAction({
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

export default PaginationTableCondominium;
