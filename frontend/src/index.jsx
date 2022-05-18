import reactDom from 'react-dom';
import { BrowserRouter, Route, Routes} from "react-router-dom";
import { Provider } from 'react-redux'
import store from 'store';
import 'bootstrap/dist/css/bootstrap.css'
import "bootstrap-icons/font/bootstrap-icons.css";
import 'assets/css/styles.scss';
import "react-datepicker/dist/react-datepicker.css";
import App from 'components/App';
import Login from 'views/Login';
import RecoverPasswordSendCodeEmail from 'views/Login/RecoverPasswordSendCodeEmail';
import RecoverPasswordSendCode from 'views/Login/RecoverPasswordSendCode';
import RecoverPasswordChange from 'views/Login/RecoverPasswordChange';
import 
{ 
    LESSEES_HOME_URL, 
    EMPLOYEES_HOME_URL, 
    HOME_URL, 
    LOGIN_URL, 
    RECOVER_PASSWORD_CHANGE_URL, 
    RECOVER_PASSWORD_SEND_CODE_URL, 
    RECOVER_PASSWORD_URL, 
    CONDOMINIUMS_HOME_URL, 
    CONTRACTS_HOME_URL, 
    DEBTS_HOME_URL,
    CONTRACTS_PRINTOUT_URL,
    REPAIR_REQUESTS_HOME_URL,
    ORDER_SERVICE_HOME_URL
} from 'utils/urls';
import PrivateRoute from 'routes/PrivateRoutes';
import TemplateApp from 'views/TemplateApp';
import EmployeesView from 'views/Employees';
import HomeView from 'views/Home';
import LesseeView from 'views/Lessees';
import CondominiumsView from 'views/Condominiums';
import ContractsView from 'views/Contracts';
import DebtsView from 'views/Debts';
import PrintoutContract from 'views/Printout/PrintoutContract';
import PageMessage from 'components/Loader/PageLoading';
import errorIMG from "assets/img/error.svg";
import RepairRequestsView from 'views/RepairRequests';
import OrderServiceView from 'views/OrderServices';

reactDom.render(
        <Provider store={store}>
            <BrowserRouter>
                <Routes>
                    <Route path={HOME_URL} element={<PrivateRoute children={<TemplateApp page={<HomeView />}/>} failRedirect={LOGIN_URL} />} />
                    <Route path={EMPLOYEES_HOME_URL} element={<PrivateRoute children={<TemplateApp page={<EmployeesView />}/>} failRedirect={LOGIN_URL} />} />
                    <Route path={LESSEES_HOME_URL} element={<PrivateRoute children={<TemplateApp page={<LesseeView />}/>} failRedirect={LOGIN_URL} />} />
                    <Route path={CONDOMINIUMS_HOME_URL} element={<PrivateRoute children={<TemplateApp page={<CondominiumsView />}/>} failRedirect={LOGIN_URL} />} />
                    <Route path={CONTRACTS_HOME_URL} element={<PrivateRoute children={<TemplateApp page={<ContractsView />}/>} failRedirect={LOGIN_URL} />}/>
                    <Route path={CONTRACTS_PRINTOUT_URL} element={<PrivateRoute children={<PrintoutContract />} failRedirect={LOGIN_URL} />} />
                    <Route path={DEBTS_HOME_URL} element={<PrivateRoute children={<TemplateApp page={<DebtsView />}/>} failRedirect={LOGIN_URL} />} />
                    <Route path={REPAIR_REQUESTS_HOME_URL} element={<PrivateRoute children={<TemplateApp page={<RepairRequestsView />}/>} failRedirect={LOGIN_URL} />} />
                    <Route path={ORDER_SERVICE_HOME_URL} element={<PrivateRoute children={<TemplateApp page={<OrderServiceView />}/>} failRedirect={LOGIN_URL} />} />
                    <Route path={LOGIN_URL}>
                        <Route path="" element={<App children={<Login />}/>} />
                        <Route path={RECOVER_PASSWORD_URL} element={<App children={<RecoverPasswordSendCodeEmail />}/>}/>
                        <Route path={ RECOVER_PASSWORD_SEND_CODE_URL } element={<App children={<RecoverPasswordSendCode />}/>}/>
                        <Route path={ RECOVER_PASSWORD_CHANGE_URL } element={<App children={<RecoverPasswordChange />}/>}/>
                    </Route>
                    <Route path="*" element={<App children={<PageMessage title={"Página não encontrada."} imageForTitle={errorIMG} />}/>} />
                </Routes>
            </BrowserRouter>
        </Provider>,
   document.getElementById("root")
);