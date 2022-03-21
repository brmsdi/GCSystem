import { combineReducers, createStore } from 'redux';
import updateAuthenticationChangeStateReducer, { userAuthenticatedViewReducer } from './Authentication/Authentication.reducer';
import getAllEmployeesReducer, { setStateFormReducer, setCurrentPagination, stateSelectionEmployeeReducer, updateTableEmployeeReducer } from './Employees/Employees.reducer';

const rootReducer = combineReducers({
    employees: getAllEmployeesReducer,
    stateForm: setStateFormReducer,
    currentPaginationTableEmployees: setCurrentPagination,
    authenticationChangeState: updateAuthenticationChangeStateReducer,
    selectedEmployee: stateSelectionEmployeeReducer,
    userAuthenticated: userAuthenticatedViewReducer,
    updateTableEmployeeCurrentState: updateTableEmployeeReducer
})

const store = createStore(rootReducer);

export default store;