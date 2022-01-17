import { combineReducers, createStore } from 'redux';
import EmployeesReducer, { setStateFormReducer, setCurrentPagination } from './Employees/Employees.reducer';

const rootReducer = combineReducers({
    employees: EmployeesReducer,
    stateForm: setStateFormReducer,
    currentPaginationTableEmployees: setCurrentPagination
});

const store = createStore(rootReducer);

export default store;