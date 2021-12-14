import Aside from "../../components/Aside";
import FormEmployee from "../../components/Form/FormEmployee";
import TableEmployee from "../../components/Table/TableEmployee";

const Home = () => {
    return(
        <div className="container">
            <Aside />
            <div className="menu-selected">selected</div>
            <div className="menu-router-activity">
                <span>System</span>
                <span> {'>'} </span>
                <span>Home</span>
            </div>
            <div>
                <button className="btn btn-primary">Novo</button>
            </div>
            <div>
                <FormEmployee />
            </div>
            <div>
                <TableEmployee />
            </div>

        </div>
        
    )
}

export default Home;