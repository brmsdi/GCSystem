import AccountInformation from "components/AccountInformation";
import Aside from "components/Aside";
import BarHome from "components/BarHome";
import FormEmployee from "components/Form/FormEmployee";
import MenuRouterActivity from "components/MenuRouterActivity";
import PaginationTableEmployee from "components/Pagination/PaginationTableEmployee";
import TableEmployee from "components/Table/TableEmployee";

const Home = () => {
  //var windowWidth = window.innerWidth;
  //var windowHeight = window.innerHeight;
  function setSelectedAccount() {
    let accountInfo = document.getElementById("id-account-info");
    let infoClick = document.getElementById("info-click");
    accountInfo?.classList.toggle("active");
    if(accountInfo?.classList.contains("active")) {
     // accountInfo.focus();
      infoClick?.classList.add("active");
    } else {
      infoClick?.classList.remove("active");
    }
  } 

  const pagination =  <PaginationTableEmployee />;

  return (
    <div className="content">
      <div id="info-click" onClick={() => setSelectedAccount()}></div>
      <AccountInformation />
      <header id="header-aside">
        <Aside />
      </header>
      <main className="content-main animate-right">
        <div className="home-header">
          <h1>{"Funcionários"}</h1>
          <div className="div-btn-account">
            <button 
            id="btn-account" 
            className="btn btn-outline-secondary"
            onClick={() => setSelectedAccount()}>
            <i className="bi bi-person-workspace"></i>
            </button>
          </div>
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
