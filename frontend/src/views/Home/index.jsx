import Aside from "components/Aside";
import BarHome from "components/BarHome";
import FormEmployee from "components/Form/FormEmployee";
import MenuRouterActivity from "components/MenuRouterActivity";
import TableEmployee from "components/Table/TableEmployee";

const Home = () => {
  return (
    <div className="content">
      <header>
        <Aside />
      </header>
      <main className="content-main animate-right">
        <div className="home-header">
          <h1>{"Funcionários"}</h1>
          <div>
            <button type="button" className="btn btn-outline-secondary">
              Sair
            </button>
          </div>
        </div>
        <MenuRouterActivity />
        <BarHome />
        <div className="content-form">
          <FormEmployee />
        </div>
        <div className="content-table"> 
          <TableEmployee />
        </div>
      </main>
    </div>
  );
};

export default Home;
