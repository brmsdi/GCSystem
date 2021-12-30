import { combineReducers, createStore } from 'redux';
import EmployeesReducer from './Employees/Employees.reducer';

const rootReducer = combineReducers({
    employees: EmployeesReducer
});

const store = createStore(rootReducer);

export default store;