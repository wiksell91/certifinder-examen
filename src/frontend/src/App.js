import React from 'react';
import {
  BrowserRouter as Router,
    Routes,
        Route
} from "react-router-dom";
import Mainpage from "./pages/Mainpage";
import Cpage  from "./pages/Cpage";
import LoginPage from "./pages/LoginPage";


function App() {

    window.onbeforeunload = (event) => {
        const e = event || window.event;
        e.preventDefault();
        if (e) {
            e.returnValue = "";
        }
        return "";
    };

  return (
      <Router>
        <Routes>
            <Route path="/"  element={<LoginPage />}/>
            <Route path="/mainpage" element={<Mainpage/>}/>
          <Route  path="/cpage"  element={<Cpage />}/>
        </Routes>
      </Router>
  );
}

export default App;
