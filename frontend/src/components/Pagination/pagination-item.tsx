import PaginationLoading from "components/Loader/PaginationLoading";
import { Link } from "react-router-dom";
import { PropsPagination } from "types/pagination";

interface IProps {
  propsPagination: PropsPagination | undefined
  changeNumberPage: Function;
}

const PaginationItem = (props: IProps) => {
  let propsPagination = props.propsPagination;
  if (propsPagination === undefined) return <PaginationLoading />;
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
          className={first === true ? "page-item disabled" : "page-item"}
        >
          <Link title="Página anterior" className="page-link" to="#" onClick={() => changeNumberPage(previousPage)}>
            <span aria-hidden="true">&laquo;</span>
          </Link>
        </li>
        {pages.map((number: number) => (
          <li
            key={number}
            className={activePage === number ? "page-item active" : "page-item"}
            
          >
            <Link title={`Página ${number}`} className="page-link" to="#" onClick={() => changeNumberPage(number)}>
              {number}
            </Link>
          </li>
        ))}
        <li
          id="page-link-next"
          className={next === true ? "page-item" : "page-item disabled"}
        >
          <Link title="Próxima página" className="page-link" to="#" onClick={() => changeNumberPage(nextPage)}>
            <span aria-hidden="true">&raquo;</span>
          </Link>
        </li>
      </ul>
    </nav>
  );
};

export default PaginationItem;
