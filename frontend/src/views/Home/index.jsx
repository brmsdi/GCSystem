import Aside from "../../components/Aside";
import FormEmployee from "../../components/Form/FormEmployee";
import SearchEmployee from "../../components/search/SearchEmployee";
import TableEmployee from "../../components/Table/TableEmployee";

function toogleClass(id) {
  let bt = document.getElementById(id);
  let form = document.querySelector('.content-form');
  form.classList.toggle('active');
  console.log(bt)
}

const Home = () => {
  return (
    <div className="content">
      <header>
        <Aside />
      </header>
      <main className="content-main ">
        <div className="home-header">
          <h1>{"Funcionário"}</h1>
          <div>
            <button type="button" className="btn btn-outline-secondary">
              Sair
            </button>
          </div>
        </div>
        <div className="menu-router-activity">
          <span>System</span>
          <span> {">"} </span>
          <span>Funcionário</span>
        </div>
        <div className="bar-home">
          <button id="bar-home-btn-new" className="btn btn-dark btn-new-employee" onClick={() => toogleClass('bar-home-btn-new')}>Novo</button>
          <SearchEmployee />
        </div>
        <div className="content-form animate-down">
          <FormEmployee />
        </div>

        <div> 
          <TableEmployee />
        </div>
      </main>
    </div>
  );
};

export default Home;
