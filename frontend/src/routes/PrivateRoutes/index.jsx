import { getToken } from "services/Authentication";
import { Navigate } from "react-router-dom";

export default function PrivateRoute({ children }) {
    return getToken() ? children : <Navigate to="/login" />;
}