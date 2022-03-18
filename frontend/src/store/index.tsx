import { combineReducers, createStore } from 'redux';
import updateAuthenticationChangeStateReducer, { userAuthenticatedViewReducer } from './Authentication/Authentication.reducer';
import getAllEmployeesReducer, { setStateFormReducer, setCurrentPagination, stateSelectionEmployeeReducer } from './Employees/Employees.reducer';

const rootReducer = combineReducers({
    employees: getAllEmployeesReducer,
    stateForm: setStateFormReducer,
    currentPaginationTableEmployees: setCurrentPagination,
    authenticationChangeState: updateAuthenticationChangeStateReducer,
    selectedEmployee: stateSelectionEmployeeReducer,
    userAuthenticated: userAuthenticatedViewReducer
})

const store = createStore(rootReducer);

export default store;