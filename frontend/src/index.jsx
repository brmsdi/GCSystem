import React from 'react';
import reactDom from 'react-dom';
import { BrowserRouter, Route, Routes} from "react-router-dom";
import { Provider } from 'react-redux'
import store from 'store';
import 'bootstrap/dist/css/bootstrap.css'
import "bootstrap-icons/font/bootstrap-icons.css";
import 'assets/css/styles.scss';
import isValidToken from 'routes/PrivateRoutes';
import App from 'components/App';
import Login from 'views/Login';
import RecoverPasswordSendCodeEmail from 'views/Login/RecoverPasswordSendCodeEmail';
import RecoverPasswordSendCode from 'views/Login/RecoverPasswordSendCode';
import RecoverPasswordChange from 'views/Login/RecoverPasswordChange';
import { RECOVER_PASSWORD_CHANGE_URL, RECOVER_PASSWORD_SEND_CODE_URL, RECOVER_PASSWORD_URL } from 'utils/urls';

reactDom.render(
    <React.StrictMode>
        <Provider store={store}>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={ isValidToken() ? (<App />) : (<Login />) } />
                    <Route path="login">
                        <Route path="" element={<Login />} />
                        <Route path={RECOVER_PASSWORD_URL} element={<RecoverPasswordSendCodeEmail />} />
                        <Route path={ RECOVER_PASSWORD_SEND_CODE_URL } element={<RecoverPasswordSendCode />}/>
                        <Route path={ RECOVER_PASSWORD_CHANGE_URL } element={<RecoverPasswordChange />}/>
                    </Route>
                </Routes>
            </BrowserRouter>
        </Provider>
    </React.StrictMode>,
   document.getElementById("root")
);

/*
     <Route path="/login" element={<Login />} />
                    <Route path={ RECOVER_PASSWORD_URL } element={<RecoverPasswordSendCodeEmail />}/>
                    <Route path={ RECOVER_PASSWORD_SEND_CODE_URL } element={<RecoverPasswordSendCode />}/>
                    <Route path={ RECOVER_PASSWORD_CHANGE_URL } element={<RecoverPasswordChange />}/>
 */