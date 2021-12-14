import { Route, Routes } from "react-router-dom";
import Login from "../../views/Login";

const isValidToken = () => {
    return true;
}

const PrivateRoutes = ({ component: Component, ...rest }) => ( 
    <Routes>
        <Route 
            { ...rest } 
            element = { 
                isValidToken() ? 
                (
                    <Component />
                ) : (            
                    <Login />
                )
            }
        />
    </Routes>)
    
    export default PrivateRoutes;

