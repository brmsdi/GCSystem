import Aside from "../../components/Aside";
import FormEmployee from "../../components/Form/FormEmployee";
import SearchEmployee from "../../components/search/SearchEmployee";
import TableEmployee from "../../components/Table/TableEmployee";

const Home = () => {
    return(
        <div className="content">
            <Aside />
            <main className="content-main animate-right">
            <div className="menu-selected"><h1>selected</h1></div>
            <div className="menu-router-activity">
                <span>System</span>
                <span> {'>'} </span> 
                <span>Home</span>
            </div>
            <div>
                <button className="btn btn-primary">Novo</button>
            </div>
            
            <FormEmployee />
            <SearchEmployee />
            <TableEmployee />
            </main>
        </div>
    )
}

export default Home;