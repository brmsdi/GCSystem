import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getAllEmployees } from "services/Employee";
import { setCurrentPaginationAction, updateEmployeesTable } from "store/Employees/Employees.actions";
import { selectCurrentPaginationTableEmployees } from "store/Employees/Employees.selectors";
import Swal from "sweetalert2";
import PaginationItem from "../PaginationItem";

const PaginationTableEmployee = () => {
  const dispatch = useDispatch();
  const activePage = useSelector(selectCurrentPaginationTableEmployees);
  //let pages: number[] = [1, 2, 3, 4, 5];
  const[pagesNumbers, setPagesNumbers] = useState<number[]>([])

  useEffect(() => {
    getAllEmployees(activePage)
    .then(response => {
      createNumberPages(activePage, response.data.totalPages)
      //updateEmployeesData(response.data)
      //setPagination(response.data)
      dispatch(updateEmployeesTable(activePage, response.data))
    })
    .catch(error => {
      if (!error.response) {
        Swal.fire('Oops!', 'Sem conexão com o servidor', 'error')
      }
    })
    //if (pagination && pagination.content) dispatch(updateEmployeesTable(activePage, pagination))

  }, [activePage, dispatch]) 

  const changeNumberPage = (pageNumber: number) => {
    //dispatch(getAllEmployeesAction(pageNumber))
    dispatch(setCurrentPaginationAction(pageNumber))
    //if (pagination && pagination.content) dispatch(updateEmployeesTable(1, pagination.content))
  }

  const createNumberPages = (currentPage: number, totalPages: number) => {
      if (totalPages <= 5 && currentPage <= 5) {
        let tempNumbers : number[] = [];
        for (var number = 1; number <= totalPages; number++) {
          tempNumbers.push(number)
        }
        setPagesNumbers(tempNumbers)
      }
  }
  return <PaginationItem propsPagination={{ pagesNumbers, activePage, changeNumberPage }} />
}

export default PaginationTableEmployee;
