import { tokenValidate } from "services/authentication";
import { Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { setUserAuthenticated } from "store/Authentication/authentication.actions";
import { UserAuthenticatedViewTypesEnum } from "types/authentication-types";
import PageMessage from "components/Loader/PageLoading";
import { TextInformationEnum } from "types/text-information";

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
    if (auth === undefined) return <PageMessage title={TextInformationEnum.LOADING} />;
    return auth === true ? children : <Navigate to={failRedirect} />;
}