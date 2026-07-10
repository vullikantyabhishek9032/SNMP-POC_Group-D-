
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import PermanentDrawerLeft from "./Drawer";
import Alaramlist from "./Components/alarmlist";
import SelectActionCard from "./Components/Dashboard";
import Performacelist from "./Components/Performace";
import Reportlist from "./Components/Report";
import MetricsList from "./Components/Metrics";
import Login from "./Components/Login";
import ProtectedRoute from "./Components/Protectedroute";
import Adddevice from "./Components/Metrics/Adddevicescreen";
import GetAlarmbyId from "./Components/alarmgetbyId";
import UserDetails from "./Userdetails/userIndex";
import Roles from "./Components/Topology";
import Planlist from "./Components/Report";
import AddUser from "./Userdetails/adduserdetails";
import Viewuserdetails from "./Userdetails/vieworedituserdetails";
import EventlistView from "./Components/Events/eventlistview";

const Settings = () => <h2>Settings Page</h2>

function LayoutModel() {
  return (
    <PermanentDrawerLeft>
      <Routes>
        <Route path="/dashboard" element={<SelectActionCard />} />
        <Route path="/metrics" element={<MetricsList />} />
        <Route path="/adddevices" element={<Adddevice />} />
        <Route path="/alerts" element={<Alaramlist />} />
        <Route path="/alerts/:id" element={<GetAlarmbyId />} />
        <Route path="/events" element={<EventlistView />} />
        <Route path="/performance" element={<Performacelist />} />
        <Route path="/roles" element={<Roles />} />
        <Route path="/avaviableplans" element={<Planlist />} />
        <Route path="/settings" element={<Settings />} />
        <Route path="/user" element={<UserDetails />} />
        <Route path="/addUser" element={<AddUser />} />
        <Route path="/Viewdetails" element={<Viewuserdetails />} />
      </Routes>
    </PermanentDrawerLeft>
  )
}

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            localStorage.getItem("isLoggedIn") === "true"
              ? <Navigate to="/dashboard" />
              : <Login />
          }
        />
        <Route
          path="/*"
          element={
            <ProtectedRoute>
              <LayoutModel />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
