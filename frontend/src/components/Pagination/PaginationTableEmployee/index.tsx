import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { findByCPFService, getAllEmployees } from "services/employee";
import { paginationEmployeeTableAction, updateEmployeesTable } from "store/Employees/employees.actions";
import { selectCurrentPaginationTableEmployees, selectUpdateTableEmployeeCurrentState } from "store/Employees/employees.selectors";
import Swal from "sweetalert2";
import { PaginationTableAction, PropsPagination } from "types/pagination";
import PaginationItem from "../pagination-item";

const PaginationTableEmployee = () => {
  const dispatch = useDispatch()
  const currentPaginationTable: PaginationTableAction = useSelector(selectCurrentPaginationTableEmployees)
  const [propsPagination, setPropsPagination] = useState<PropsPagination>()
  const updateTableEmployeeCurrentState = useSelector(selectUpdateTableEmployeeCurrentState);

  useEffect(() => {
    try {
      if (currentPaginationTable.search) {
        findByCPFService(currentPaginationTable.search)
          .then((response) => {
            createNumberPages(currentPaginationTable.currentPage, response.data.totalPages);
            dispatch(updateEmployeesTable(currentPaginationTable.currentPage, response.data));
          })
      } else {
        getAllEmployees(currentPaginationTable.currentPage)
          .then((response) => {
            createNumberPages(currentPaginationTable.currentPage, response.data.totalPages);
            dispatch(updateEmployeesTable(currentPaginationTable.currentPage, response.data));
          })
      }

    } catch (error: any) {
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor", "error");
      }
    }

  }, [currentPaginationTable, dispatch, updateTableEmployeeCurrentState]);

  const changeNumberPage = (pageNumber: number) => {
    dispatch(paginationEmployeeTableAction({
      ...currentPaginationTable,
      currentPage: pageNumber
    }));
  }

  function createNumberPages(currentPage: number, totalPages: number, max: number = 5) {
    const totalNumberPages = Math.ceil(totalPages / max);
    for (var index = 1; index <= totalNumberPages; index++) {
      let maxNumberOfPage = (index * 5);
      let minNumberOfPage = maxNumberOfPage - 4;
      let tempNumbers: number[] = [];
      if (currentPage >= minNumberOfPage && currentPage <= maxNumberOfPage && !(maxNumberOfPage > totalPages)) {
        for (let pageMin = minNumberOfPage; pageMin <= maxNumberOfPage; pageMin++) {
          tempNumbers.push(pageMin)
        }

        setPropsPagination({
          pagesNumbers: tempNumbers,
          currentPage: currentPage,
          next: maxNumberOfPage < totalPages ? true : false,
          first: minNumberOfPage === 1 ? true : false,
          nextPage: (maxNumberOfPage + 1),
          previousPagination: (minNumberOfPage)
        })
        break
      } else if (maxNumberOfPage > totalPages) {
        for (let pageMin = minNumberOfPage; pageMin <= totalPages; pageMin++) {
          tempNumbers.push(pageMin)
        }
        setPropsPagination({
          pagesNumbers: tempNumbers,
          currentPage: currentPage,
          next: false,
          first: minNumberOfPage === 1 ? true : false,
          nextPage: totalPages,
          previousPagination: (minNumberOfPage - 1)
        })
        break
      }
    } // end for index
  }

  if (propsPagination === undefined) return null;
  return (
    <PaginationItem
      propsPagination={propsPagination}
      changeNumberPage={changeNumberPage}
    />
  )
}

export default PaginationTableEmployee;
