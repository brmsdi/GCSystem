
import allEmployees from '../../mocks/employees.json';

export default function getAll(state = allEmployees.employees , action) {

    switch(action.type)
    {
        case 'GET-ALL-EMPLOYEES':
            return state;
        default:
            return state

    }
}