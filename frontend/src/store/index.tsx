import { combineReducers, createStore } from 'redux';
import { changeSelectedSubMenuAsideReducer } from './Aside/aside.reducer';
import updateAuthenticationChangeStateReducer, { userAuthenticatedViewReducer } from './Authentication/login.reducer';
import getAllCondominiumsReducer, { setCurrentPaginationCondominiumReducer, setStateFormCondominiumReducer, stateSelectionCondominiumReducer, updateTableCondominiumReducer } from './Condominiums/condiminiums.reducer';
import getAllContractsReducer, { setCurrentPaginationContractReducer, setStateFormContractReducer, stateSelectionContractReducer, updateTableContractReducer } from './Contracts/contracts.reducer';
import getAllDebtsReducer, { setCurrentPaginationDebtReducer, setStateFormDebtReducer, stateSelectionDebtReducer, updateTableDebtReducer } from './Debts/debts.reducer';
import getAllEmployeesReducer, { setCurrentPaginationEmployeeReducer, setStateFormEmployeeReducer, stateSelectionEmployeeReducer, updateTableEmployeeReducer } from './Employees/employees.reducer';
import getAllLesseesReducer, { setCurrentPaginationLesseeReducer, setStateFormLesseeReducer, stateSelectionLesseeReducer, updateTableLesseeReducer } from './Lessees/lessees.reducer';
import getAllOrderServiceReducer, { changeStateModalOrderServiceEmployeesReducer, detailsModalOrderServiceReducer, selectedEmployeesOrderServiceReducer, setCurrentPaginationOrderServiceReducer, setStateFormOrderServiceReducer, stateSelectionOrderServiceReducer, updateTableOrderServiceReducer } from './OrderServices/order-services.reducer';
import getAllRepairRequestReducer, { changeStateModalOrderServiceRepairRequestsReducer, selectedRepairRequestsOrderServiceReducer, setCurrentPaginationRepairRequestReducer, setStateFormRepairRequestReducer, stateSelectionRepairRequestReducer, updateTableRepairRequestReducer } from './RepairRequests/repair-requests.reducer';

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
    selectedDebt: stateSelectionDebtReducer,

    // REPAIR REQUEST REDUCER
    repairRequests: getAllRepairRequestReducer,
    stateFormRepairRequest: setStateFormRepairRequestReducer,
    currentPaginationTableRepairRequests: setCurrentPaginationRepairRequestReducer,
    updateTableRepairRequestCurrentState: updateTableRepairRequestReducer,
    selectedRepairRequest: stateSelectionRepairRequestReducer,
    selectedRepairRequestsOrderService: selectedRepairRequestsOrderServiceReducer,
    stateModalOrderServiceRepairRequests: changeStateModalOrderServiceRepairRequestsReducer,

    // ORDER SERVICE REDUCER
    orderServices: getAllOrderServiceReducer,
    stateFormOrderService: setStateFormOrderServiceReducer,
    currentPaginationTableOrderServices: setCurrentPaginationOrderServiceReducer,
    updateTableOrderServiceCurrentState: updateTableOrderServiceReducer,
    selectedOrderService: stateSelectionOrderServiceReducer,
    selectedEmployeesOrderService: selectedEmployeesOrderServiceReducer,
    stateModalOrderServiceEmployees: changeStateModalOrderServiceEmployeesReducer,
    selectedDetailsModalOrderService: detailsModalOrderServiceReducer
})

const store = createStore(rootReducer);

export default store;