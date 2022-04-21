import { combineReducers, createStore } from 'redux';
import { changeSelectedSubMenuAsideReducer } from './Aside/aside.reducer';
import updateAuthenticationChangeStateReducer, { userAuthenticatedViewReducer } from './Authentication/authentication.reducer';
import getAllCondominiumsReducer, { setCurrentPaginationCondominiumReducer, setStateFormCondominiumReducer, stateSelectionCondominiumReducer, updateTableCondominiumReducer } from './Condominiums/condiminiums.reducer';
import getAllContractsReducer, { setCurrentPaginationContractReducer, setStateFormContractReducer, stateSelectionContractReducer, updateTableContractReducer } from './Contracts/contracts.reducer';
import getAllDebtsReducer, { setCurrentPaginationDebtReducer, setStateFormDebtReducer, stateSelectionDebtReducer, updateTableDebtReducer } from './Debts/debts.reducer';
import getAllEmployeesReducer, { setCurrentPaginationEmployeeReducer, setStateFormEmployeeReducer, stateSelectionEmployeeReducer, updateTableEmployeeReducer } from './Employees/employees.reducer';
import getAllLesseesReducer, { setCurrentPaginationLesseeReducer, setStateFormLesseeReducer, stateSelectionLesseeReducer, updateTableLesseeReducer } from './Lessees/lessees.reducer';

const rootReducer = combineReducers({
    employees: getAllEmployeesReducer,
    stateFormEmployee: setStateFormEmployeeReducer,
    currentPaginationTableEmployees: setCurrentPaginationEmployeeReducer,
    authenticationChangeState: updateAuthenticationChangeStateReducer,
    selectedEmployee: stateSelectionEmployeeReducer,
    userAuthenticated: userAuthenticatedViewReducer,
    updateTableEmployeeCurrentState: updateTableEmployeeReducer,

    // LESSEE REDUCER
    lessees: getAllLesseesReducer,
    stateFormLessee: setStateFormLesseeReducer,
    currentPaginationTableLessees: setCurrentPaginationLesseeReducer,
    updateTableLesseeCurrentState: updateTableLesseeReducer,
    selectedLessee: stateSelectionLesseeReducer,

    //ASIDE REDUCER
    menuSelected: changeSelectedSubMenuAsideReducer,

    // CONDOMINIUM REDUCER
    condominiums: getAllCondominiumsReducer,
    stateFormCondominium: setStateFormCondominiumReducer,
    currentPaginationTableCondominiums: setCurrentPaginationCondominiumReducer,
    updateTableCondominiumCurrentState: updateTableCondominiumReducer,
    selectedCondominium: stateSelectionCondominiumReducer,

    // CONTRACT REDUCER
    contracts: getAllContractsReducer,
    stateFormContract: setStateFormContractReducer,
    currentPaginationTableContracts: setCurrentPaginationContractReducer,
    updateTableContractCurrentState: updateTableContractReducer,
    selectedContract: stateSelectionContractReducer,

    // DEBT REDUCER
    debts: getAllDebtsReducer,
    stateFormDebt: setStateFormDebtReducer,
    currentPaginationTableDebts: setCurrentPaginationDebtReducer,
    updateTableDebtCurrentState: updateTableDebtReducer,
    selectedDebt: stateSelectionDebtReducer
})

const store = createStore(rootReducer);

export default store;