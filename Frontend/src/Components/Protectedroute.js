// // ProtectedRoute.jsx
// import { Navigate } from "react-router-dom";

// const ProtectedRoute = ({ children }) => {
//     const isLoggedIn = localStorage.getItem("isLoggedIn");
//     console.log("log the values.....",isLoggedIn)
//     return isLoggedIn ? children : <Navigate to="/dashboard" />;
// };

// export default ProtectedRoute;

import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }) => {
    const isLoggedIn = localStorage.getItem("isLoggedIn");

    console.log("log the values.....", isLoggedIn);

    return isLoggedIn ? children : <Navigate to="/" />;
};

export default ProtectedRoute;