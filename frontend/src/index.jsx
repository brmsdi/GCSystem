import React from 'react';
import reactDom from 'react-dom';
import { BrowserRouter, Route, Routes} from "react-router-dom";
import { Provider } from 'react-redux'
import store from 'store';
import 'bootstrap/dist/css/bootstrap.css'
import 'assets/css/index.css';
import 'assets/css/aside.css';
import 'assets/css/animation.css';
import 'assets/css/form.css';
import 'assets/css/table.css';
import 'assets/css/index-713px.css';
import 'assets/css/login.css';
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