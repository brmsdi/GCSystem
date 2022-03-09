import { combineReducers, createStore } from 'redux';
import updateAuthenticationChangeStateReducer from './Authentication/Authentication.reducer';
import getAllEmployeesReducer, { setStateFormReducer, setCurrentPagination } from './Employees/Employees.reducer';

const rootReducer = combineReducers({
    employees: getAllEmployeesReducer,
    stateForm: setStateFormReducer,
    currentPaginationTableEmployees: setCurrentPagination,
    authenticationChangeState: updateAuthenticationChangeStateReducer
});

const store = createStore(rootReducer);

export default store;