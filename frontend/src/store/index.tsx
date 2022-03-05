import { combineReducers, createStore } from 'redux';
import updateAuthenticationChangeStateReducer from './Authentication/Authentication.reducer';
import EmployeesReducer, { setStateFormReducer, setCurrentPagination } from './Employees/Employees.reducer';

const rootReducer = combineReducers({
    employees: EmployeesReducer,
    stateForm: setStateFormReducer,
    currentPaginationTableEmployees: setCurrentPagination,
    authenticationChangeState: updateAuthenticationChangeStateReducer
});

const store = createStore(rootReducer);

export default store;