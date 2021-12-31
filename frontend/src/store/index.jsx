import { combineReducers, createStore } from 'redux';
import EmployeesReducer, { setStateFormReducer } from './Employees/Employees.reducer';

const rootReducer = combineReducers({
    employees: EmployeesReducer,
    stateForm: setStateFormReducer
});

const store = createStore(rootReducer);

export default store;