import Aside from "components/Aside";
import BarHome from "components/BarHome";
import FormEmployee from "components/Form/FormEmployee";
import MenuRouterActivity from "components/MenuRouterActivity";
import PaginationTableEmployee from "components/Pagination/PaginationTableEmployee";
import TableEmployee from "components/Table/TableEmployee";

const Home = () => {
  //var windowWidth = window.innerWidth;
  //var windowHeight = window.innerHeight;
  function headerOpenOrClose() {
    document.getElementById('header-aside')?.classList.toggle('open')
  } 

  const pagination =  <PaginationTableEmployee />;

  return (
    <div className="content">
      <header id="header-aside">
        <div id="headerEvent" onClick={() => headerOpenOrClose()}></div>
        <Aside />
      </header>
      <main className="content-main animate-right">
        <div className="home-header">
          <h1>{"Funcionários"}</h1>
        </div>
        <MenuRouterActivity />
        <BarHome />
        <div className="content-form">
          <FormEmployee />
        </div>
        <div className="content-table">
          { pagination }
          <TableEmployee />
          <div className="pagination-mobile">
          { pagination }
          </div>
        </div>
      </main>
    </div>
  );
};

export default Home;
