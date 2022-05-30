import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { findByCPFService, getAllEmployees } from "services/employee";
import {
  paginationEmployeeTableAction,
  updateEmployeesTable,
} from "store/Employees/employees.actions";
import {
  selectCurrentPaginationTableEmployees,
  selectUpdateTableEmployeeCurrentState,
} from "store/Employees/employees.selectors";
import Swal from "sweetalert2";
import { PaginationTableAction, PropsPagination } from "types/pagination";
import { createNumberPages } from "../pagination-create-number-pages";
import PaginationItem from "../pagination-item";

const PaginationTableEmployee = () => {
  const dispatch = useDispatch();
  const currentPaginationTable: PaginationTableAction = useSelector(
    selectCurrentPaginationTableEmployees
  );
  const [propsPagination, setPropsPagination] = useState<PropsPagination>();
  const updateTableEmployeeCurrentState = useSelector(
    selectUpdateTableEmployeeCurrentState
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
              updateEmployeesTable(
                currentPaginationTable.currentPage,
                response.data
              )
            );
          }
        );
      } else {
        getAllEmployees(currentPaginationTable.currentPage).then(
          async (response) => {
            const paginationInformations = await createNumberPages(
              currentPaginationTable.currentPage,
              response.data.totalPages
            );
            setPropsPagination(paginationInformations);
            dispatch(
              updateEmployeesTable(
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
  }, [currentPaginationTable, dispatch, updateTableEmployeeCurrentState]);

  const changeNumberPage = (pageNumber: number) => {
    dispatch(
      paginationEmployeeTableAction({
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

export default PaginationTableEmployee;
