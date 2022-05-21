import { checkPermissionView, tokenValidate } from "services/authentication";
import { Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { setUserAuthenticated } from "store/Authentication/authentication.actions";
import { UserAuthenticatedViewTypesEnum } from "types/authentication-types";
import PageMessage from "components/Loader/PageLoading";
import { TextInformationEnum } from "types/text-information";
import { PERMISSION_URL } from "utils/requests";

interface IProps {
  children: any;
  permissionCheck?: string;
  failRedirect: string;
}

export default function ElementVerified(props: IProps) {
  const dispatch = useDispatch();
  const [auth, setAuth] = useState<boolean>();
  useEffect(() => {
    async function validateAndPermission() 
    {
      try 
      {
        const userAuthenticatedView = await tokenValidate();
        if (props.permissionCheck) await checkPermissionView(`${props.permissionCheck}/${PERMISSION_URL}`);
        dispatch(setUserAuthenticated(UserAuthenticatedViewTypesEnum.UPDATE, userAuthenticatedView))
        setAuth(true)
      } catch (error : any)
      {
        setAuth(false)
      }
    }

    validateAndPermission()
  }, [dispatch, props.permissionCheck]);
  if (auth === undefined) return <PageMessage title={TextInformationEnum.LOADING} />;
  return auth === true ? props.children : <Navigate to={props.failRedirect} />;
}