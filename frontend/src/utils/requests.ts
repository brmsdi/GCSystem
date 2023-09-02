
export const API_VERSION = 'api/v1/';
export const API_VERSION_WEB = API_VERSION + 'web/';
export const REQUEST_BASE_URL = 'http://127.0.0.1:8080'
export const REQUEST_BASE_URL_WITH_API_VERSION = REQUEST_BASE_URL + '/' + API_VERSION_WEB;

export const PERMISSION_URL = 'permission'

/** EMPLOYEES REQUEST */
export const REQUEST_LOGIN_EMPLOYEES = API_VERSION + 'login/employees';
export const REQUEST_EMPLOYEES = API_VERSION_WEB +'employees';
export const REQUEST_EMPLOYEE_SEARCH = '/search';
export const REQUEST_EMPLOYEES_TO_MODAL_ORDER_SERVICE = API_VERSION_WEB + 'employees/list/to-modal-order-service';

export const REQUEST_LOGIN = 'login';
export const REQUEST_VALIDATE_TOKEN = API_VERSION_WEB + 'validate/token';
export const REQUEST_REQUEST_CODE = API_VERSION_WEB + 'password/request-code';
export const REQUEST_VALIDATE_CODE = API_VERSION_WEB + 'password/receive-code';
export const REQUEST_PASSWORD_CHANGE = API_VERSION_WEB + 'password/change';

/*STATUS REQUEST */
export const REQUEST_STATUS = API_VERSION_WEB + 'status';
export const REQUEST_STATUS_FROM_VIEW_CONDOMINIUM = API_VERSION_WEB + 'status/condominium';
export const REQUEST_STATUS_FROM_VIEW_CONTRACT = API_VERSION_WEB + 'status/contract';
export const REQUEST_STATUS_FROM_VIEW_DEBT = API_VERSION_WEB + 'status/debt';
export const REQUEST_STATUS_FROM_VIEW_REPAIR_REQUEST = API_VERSION_WEB + 'status/repair-request';
export const REQUEST_STATUS_FROM_VIEW_ORDER_SERVICE = API_VERSION_WEB + 'status/order-service';

export const REQUEST_ROLES = API_VERSION_WEB + 'roles';

/*lESSEES REQUEST */
export const REQUEST_LESSEES = API_VERSION_WEB + 'lessees';
export const REQUEST_LESSEE_SEARCH = '/search';

/* CONDOMINIUM REQUEST */
export const REQUEST_CONDOMINIUMS = API_VERSION_WEB + 'condominiums';
export const REQUEST_CONDOMINIUM_SEARCH = '/search';
export const REQUEST_CONDOMINIUM_LIST_ALL = API_VERSION_WEB + 'condominiums/list';

/* CONTRACT REQUEST */
export const REQUEST_CONTRACTS = API_VERSION_WEB + 'contracts';
export const REQUEST_CONTRACT_SEARCH = '/search';
export const REQUEST_CONTRACTS_PRINTOUT = API_VERSION_WEB + 'contracts/printout';

/* DEBT REQUEST */
export const REQUEST_DEBTS = API_VERSION_WEB + 'debts';
export const REQUEST_DEBT_SEARCH = API_VERSION_WEB + '/search';

/* REPAIR-REQUEST REQUEST */
export const REQUEST_REPAIR_REQUESTS = API_VERSION_WEB + 'repair-requests';
export const REQUEST_REPAIR_REQUESTS_SEARCH = '/search';
export const REQUEST_REPAIR_REQUESTS_PER_STATUS_OPEN_PROGRESS_LATE = API_VERSION_WEB + 'repair-requests/list/status-open-progress-late';
export const REQUEST_REPAIR_REQUESTS_PER_ORDER_SERVICE_AND_STATUS = API_VERSION_WEB + 'repair-requests/list/order-service/to-modal-order-service';
export const REQUEST_REPAIR_REQUESTS_TO_MODAL_ORDER_SERVICE = API_VERSION_WEB + 'repair-requests/list/to-modal-order-service';

/* TYPE-PROBLEM REQUEST */
export const REQUEST_TYPE_PROBLEM = API_VERSION_WEB + 'types-problem';

/* ORDER SERVICE REQUEST */
export const REQUEST_ORDER_SERVICES = API_VERSION_WEB + 'order-services';
export const REQUEST_ORDER_SERVICES_SEARCH = '/search';