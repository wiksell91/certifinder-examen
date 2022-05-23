import React,{ useState, useEffect } from 'react';
import jwt_decode from "jwt-decode";
import {
    Routes,
        Route
} from "react-router-dom";
import PrivateRoute from "./PrivateRoute";
import Mainpage from "./pages/Mainpage";
import Cpage  from "./pages/Cpage";
import UserPage from "./pages/UserPage";
import LoginPage from "./pages/LoginPage";
import { useUser } from "./UserProvider";


function App() {
    const [roles, setRoles] = useState([]);
    const user = useUser();

    useEffect(() => {
        setRoles(getRolesFromJWT());
    }, [user.jwt]);

    function getRolesFromJWT() {
        if (user.jwt) {
            const decodedJwt = jwt_decode(user.jwt);
            return decodedJwt.authorities;
        }
        return [];
    }

  return (
      <Routes>
          <Route
              path="/cpage"
              element={roles.find((role) => role  === "ROLE_COMPANY") ?(
                      <PrivateRoute>
                          <Cpage />
                      </PrivateRoute>
              ) : (
                  <PrivateRoute>
                      <UserPage />
                  </PrivateRoute>
              )
              }
          />
          <Route path="/" element={<Mainpage />} />
          {/*<Route path="/" element={<Homepage />} />*/}
      </Routes>



      // <Router>
      //   <Routes>
      //       <Route path="/login"  element={<LoginPage />}/>
      //       <Route path="/" element={<Mainpage/>}/>
      //     <Route  path="/cpage"  element={<Cpage />}/>
      //   </Routes>
      // </Router>
  );
}

export default App;
