import { BrowserRouter} from "react-router-dom";
import Home from "../../views/Home";
import PrivateRoutes from "../PrivateRoutes";

const RoutesDefault = () => {
    return(
        <BrowserRouter>
            <PrivateRoutes path="/" component={() => <Home/>} />
        </BrowserRouter>
    )
}

export default RoutesDefault;
