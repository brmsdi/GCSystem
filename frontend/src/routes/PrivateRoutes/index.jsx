import { tokenValidate } from "services/Authentication";
import { Navigate } from "react-router-dom";
import { useEffect, useState } from "react";

export default function PrivateRoute({ children, failRedirect }) {
    const[auth, setAuth] = useState();
    useEffect(() => {
        tokenValidate()
        .then(response  => {
            if (response.status === 200) {
                setAuth(true);
            } else {
                setAuth(false);
            }
        })
        .catch(() => {
            setAuth(false);
        })
    }, []);
    if (auth === undefined) return null;
    return auth === true ? children : <Navigate to={failRedirect} />;
}