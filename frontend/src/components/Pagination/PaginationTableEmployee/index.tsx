import { useDispatch, useSelector } from "react-redux";
import { getAllEmployees, setCurrentPaginationAction } from "store/Employees/Employees.actions";
import { selectCurrentPaginationTableEmployees } from "store/Employees/Employees.selectors";
import PaginationItem from "../PaginationItem";

const PaginationTableEmployee = () => {
  const dispatch = useDispatch();
  const activePage = useSelector(selectCurrentPaginationTableEmployees);
  let pages: number[] = [1, 2, 3, 4, 5];
  const changeNumberPage = (pageNumber: number) => {
    dispatch(getAllEmployees(pageNumber));
    dispatch(setCurrentPaginationAction(pageNumber))
  }
  return <PaginationItem propsPagination={{ pages, activePage, changeNumberPage }} />
};

export default PaginationTableEmployee;
