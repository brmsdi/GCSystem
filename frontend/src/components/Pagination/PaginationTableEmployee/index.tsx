import { useState } from "react";
import { useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { getAllEmployees } from "store/Employees/Employees.actions";

const PaginationTableEmployee = () => {
  /**
   * <li className="page-item active">
            <span className="page-link">
              2<span className="sr-only"></span>
            </span>
          </li>
   */
  const[activePage, setActivePage] = useState(1);
  const dispatch =useDispatch();
  let pages: number[] = [1, 2];

  const changeNumberPage = (pageNumber: number) => {
    dispatch(getAllEmployees(pageNumber));
    setActivePage(pageNumber);
  }

  return (
    <nav aria-label="...">
      <ul className="pagination justify-content-center">
        <li className="page-item disabled">
          <span className="page-link">Anterior</span>
        </li>
        {pages.map((number: number) => (
          <li 
          key={number} 
          className={ activePage === number ? "page-item active" : "page-item"}
          onClick={() => changeNumberPage(number)} >
            <Link className="page-link" to="#">
              {number}
            </Link>
          </li>
        ))}
        <li className="page-item">
          <Link className="page-link" to="#">
            Próximo
          </Link>
        </li>
      </ul>
    </nav>
  );
};

export default PaginationTableEmployee;
