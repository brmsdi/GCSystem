import SearchEmployee from "components/search/SearchEmployee";

const BarHome = () => {

    function toogleClass(id) {
        let bt = document.getElementById(id);
        let form = document.querySelector('.content-form');
        form.classList.toggle('active');
        console.log(bt)
      }
      
    return(
        <div className="bar-home">
          <button id="bar-home-btn-new" className="btn btn-dark btn-new-employee" onClick={() => toogleClass('bar-home-btn-new')}>Novo</button>
          <SearchEmployee />
        </div>
    );
}

export default BarHome;