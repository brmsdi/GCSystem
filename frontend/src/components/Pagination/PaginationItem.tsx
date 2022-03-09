import { Link } from "react-router-dom";
import { PropsPagination } from "types/Pagination";
const PaginationItem = (props: { propsPagination: PropsPagination }) => {
  let propsPagination = props.propsPagination;
  let pages = propsPagination.pagesNumbers;
  let activePage = propsPagination.activePage;
  return (
    <nav aria-label="...">
      <ul className="pagination pagination-sm justify-content-center">
        <li id="page-link-previous" className="page-item disabled">
          <Link className="page-link" to="#">
            <span aria-hidden="true">&laquo;</span>
          </Link>
        </li>
        {pages.map((number: number) => (
          <li
            key={number}
            className={activePage === number ? "page-item active" : "page-item"}
            onClick={() => propsPagination.changeNumberPage(number)}
          >
            <Link className="page-link" to="#">
              {number}
            </Link>
          </li>
        ))}
        <li id="page-link-next" className="page-item">
          <Link className="page-link" to="#">
            <span aria-hidden="true">&raquo;</span>
          </Link>
        </li>
      </ul>
    </nav>
  );
};

export default PaginationItem;
