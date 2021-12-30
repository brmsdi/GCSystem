
export default function isValidToken() {
    return true;
}


/*
const PrivateRoutes = ({ component: Component, ...rest }) => ( 
        <Route 
            { ...rest } 
            element = { 
                isValidToken() ? 
                (
                    <Component />
                ) : (            
                    <Login />
                )
            }
        />
    ) 
 
    export default PrivateRoutes; */

