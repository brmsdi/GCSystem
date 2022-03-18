import { combineReducers, createStore } from 'redux';
import updateAuthenticationChangeStateReducer from './Authentication/Authentication.reducer';
import getAllEmployeesReducer, { setStateFormReducer, setCurrentPagination, stateSelectionEmployeeReducer } from './Employees/Employees.reducer';

const rootReducer = combineReducers({
    employees: getAllEmployeesReducer,
    stateForm: setStateFormReducer,
    currentPaginationTableEmployees: setCurrentPagination,
    authenticationChangeState: updateAuthenticationChangeStateReducer,
    selectedEmployee: stateSelectionEmployeeReducer
})

const store = createStore(rootReducer);

export default store;