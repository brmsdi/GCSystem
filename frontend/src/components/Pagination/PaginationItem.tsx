import { Link } from "react-router-dom";
import { PropsPagination } from "types/Pagination";
const PaginationItem = (props: { propsPagination: PropsPagination, changeNumberPage: Function }) => {
  let propsPagination = props.propsPagination;
  let pages = propsPagination.pagesNumbers;
  let activePage = propsPagination.currentPage;
  let next = propsPagination.next;
  let first = propsPagination.first;
  let previousPage = propsPagination.previousPagination;
  let nextPage = propsPagination.nextPage;
  const changeNumberPage = props.changeNumberPage;
  return (
    <nav aria-label="...">
      <ul className="pagination pagination-sm justify-content-center">
        <li 
        id="page-link-previous" 
        className= {first === true ?  "page-item disabled" : "page-item"}
        onClick={() => changeNumberPage(previousPage)} >
          <Link className="page-link" to="#">
            <span aria-hidden="true">&laquo;</span>
          </Link>
        </li>
        {pages.map((number: number) => (
          <li
            key={number}
            className={activePage === number ? "page-item active" : "page-item"}
            onClick={() => changeNumberPage(number)}>
            <Link className="page-link" to="#">
              {number}
            </Link>
          </li>
        ))}
        <li 
        id="page-link-next" 
        className= {next === true ? "page-item" : "page-item disabled"} 
        onClick={() => changeNumberPage(nextPage)}>
          <Link className="page-link" to="#">
            <span aria-hidden="true">&raquo;</span>
          </Link>
        </li>
      </ul>
    </nav>
  );
};

export default PaginationItem;
