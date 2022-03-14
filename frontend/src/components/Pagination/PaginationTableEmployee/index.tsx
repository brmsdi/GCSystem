import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { findByCPFService, getAllEmployees } from "services/Employee";
import { paginationTableAction, updateEmployeesTable } from "store/Employees/Employees.actions";
import { selectCurrentPaginationTableEmployees } from "store/Employees/Employees.selectors";
import Swal from "sweetalert2";
import { PaginationTableAction, PropsPagination } from "types/Pagination";
import PaginationItem from "../PaginationItem";

const PaginationTableEmployee = () => {
  const dispatch = useDispatch()
  const statePaginationTable: PaginationTableAction = useSelector(selectCurrentPaginationTableEmployees)
  const [propsPagination, setPropsPagination] = useState<PropsPagination>()

  useEffect(() => {
    try {
      if (statePaginationTable.search) {
        findByCPFService(statePaginationTable.search)
          .then((response) => {
            createNumberPages(statePaginationTable.currentPage, response.data.totalPages);
            dispatch(updateEmployeesTable(statePaginationTable.currentPage, response.data));
          })
      } else {
        getAllEmployees(statePaginationTable.currentPage)
          .then((response) => {
            createNumberPages(statePaginationTable.currentPage, response.data.totalPages);
            dispatch(updateEmployeesTable(statePaginationTable.currentPage, response.data));
          })
      }

    } catch (error: any) {
      if (!error.response) {
        Swal.fire("Oops!", "Sem conexão com o servidor", "error");
      }
    }

  }, [statePaginationTable, dispatch]);

  const changeNumberPage = (pageNumber: number) => {
    dispatch(paginationTableAction({
      ...statePaginationTable,
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
