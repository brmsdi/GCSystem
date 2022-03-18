import { tokenValidate } from "services/Authentication";
import { Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { setUserAuthenticated } from "store/Authentication/Authentication.actions";
import { UserAuthenticatedViewTypesEnum } from "types/AuthenticationTypes";

export default function PrivateRoute({ children, failRedirect }) {
    const dispatch = useDispatch()
    const[auth, setAuth] = useState();
    useEffect(() => {
        tokenValidate()
        .then(response  => {
            if (response.status === 200) {
                dispatch(setUserAuthenticated(UserAuthenticatedViewTypesEnum.UPDATE, response.data))
                setAuth(true);
            } else {
                setAuth(false);
            }
        })
        .catch(() => {
            setAuth(false);
        })
    }, [dispatch]);
    if (auth === undefined) return null;
    return auth === true ? children : <Navigate to={failRedirect} />;
}