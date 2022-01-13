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

reactDom.render(
    <React.StrictMode>
        <Provider store={store}>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={ isValidToken() ? (<App />) : (<Login />) } />
                    <Route path="/login" element={<Login />} />
                </Routes>
            </BrowserRouter>
        </Provider>
    </React.StrictMode>,
   document.getElementById("root")
);