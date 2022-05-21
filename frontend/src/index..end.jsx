import reactDom from 'react-dom';
import { BrowserRouter } from "react-router-dom";
import { Provider } from 'react-redux'
import store from 'store';
import 'bootstrap/dist/css/bootstrap.css'
import "bootstrap-icons/font/bootstrap-icons.css";
import 'assets/css/styles.scss';
import "react-datepicker/dist/react-datepicker.css";
import { AuthenticatedUserRouters } from 'routes/PrivateRoutes';

reactDom.render(
        <Provider store={store}>
            <BrowserRouter>
                <AuthenticatedUserRouters />
            </BrowserRouter>
        </Provider>,
   document.getElementById("root")
);