import React from 'react';
import reactDom from 'react-dom';
import App from './components/App';
import 'bootstrap/dist/css/bootstrap.css'
import './assets/css/index.css';
import './assets/css/aside.css';
import './assets/css/animation.css';
import './assets/css/form.css';
import './assets/css/table.css';
import './assets/css/index-713px.css';

reactDom.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>,
   document.getElementById("root")
);