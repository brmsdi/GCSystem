import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { findByCPFService, getAllContracts } from "services/contract";
import { paginationContractTableAction, updateContractsTable } from "store/Contracts/contracts.actions";
import { selectCurrentPaginationTableContracts, selectUpdateTableContractCurrentState } from "store/Contracts/contracts.selector";
import Swal from "sweetalert2";
import { PaginationTableAction, PropsPagination } from "types/pagination";
import { createNumberPages } from "../pagination-create-number-pages";
import PaginationItem from "../pagination-item";

const PaginationTableContract = () => {
  const dispatch = useDispatch()
  const currentPaginationTable: PaginationTableAction = useSelector(selectCurrentPaginationTableContracts)
  const [propsPagination, setPropsPagination] = useState<PropsPagination>()
  const updateTableContractCurrentState = useSelector(selectUpdateTableContractCurrentState);
  useEffect(() => {
    try {
      if (currentPaginationTable.search) {
        findByCPFService(currentPaginationTable.search)
          .then(async (response) => {
            const paginationInformations = await createNumberPages(currentPaginationTable.currentPage, response.data.totalPages)
            setPropsPagination(paginationInformations)
             dispatch(updateContractsTable(currentPaginationTable.currentPage, response.data))
          })
      } else {
        getAllContracts(currentPaginationTable.currentPage)
          .then(async (response) =>  {
            const paginationInformations = await createNumberPages(currentPaginationTable.currentPage, response.data.totalPages)
            setPropsPagination(paginationInformations)
            dispatch(updateContractsTable(currentPaginationTable.currentPage, response.data))
          })
      }
    } catch (error: any) {
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor", "error");
      }
    }
  }, [currentPaginationTable, dispatch, updateTableContractCurrentState]);

  const changeNumberPage = (pageNumber: number) => {
    dispatch(paginationContractTableAction({
      ...currentPaginationTable,
      currentPage: pageNumber
    }));
  }
  if (propsPagination === undefined) return null;
  return (
    <PaginationItem
      propsPagination={propsPagination}
      changeNumberPage={changeNumberPage}
    />
  )
}

export default PaginationTableContract;
