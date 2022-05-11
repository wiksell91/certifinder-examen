import React from 'react';
import jwt_decode from "jwt-decode";
import {
  BrowserRouter as Router,
    Routes,
        Route
} from "react-router-dom";
import PrivateRoute from "./PrivateRoute";
import Mainpage from "./pages/Mainpage";
import Cpage  from "./pages/Cpage";
import LoginPage from "./pages/LoginPage";
import { useUser } from "./UserProvider";


function App() {

    // const [roles, setRoles] = useState([]);
    // const user = useUser();

    // useEffect(() => {
    //     setRoles(getRolesFromJWT());
    // }, [user.jwt]);

    // function getRolesFromJWT() {
    //     if (user.jwt) {
    //         const decodedJwt = jwt_decode(user.jwt);
    //         return decodedJwt.authorities;
    //     }
    //     return [];
    // }

  return (
      <Routes>
          <Route
              path="/cpage"
              element={
                      <PrivateRoute>
                          <Cpage />
                      </PrivateRoute>

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
