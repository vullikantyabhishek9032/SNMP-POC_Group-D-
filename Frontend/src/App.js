
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import PermanentDrawerLeft from "./Drawer";
import Alaramlist from "./Components/alarmlist";
import Eventlist from "./Components/Eventlist";
import SelectActionCard from "./Components/Dashboard";
import Devicelist from "./Components/Devices";
import Alramhistory from "./Components/Alaramhistory";
import Eventlogs from "./Components/Eventlogs";
import Performacelist from "./Components/Performace";
import Topologylist from "./Components/Topology";
import Reportlist from "./Components/Report";
import Memorylist from "./Components/Devices/MemoryDevices";
import MetricsList from "./Components/Metrics";
import Login from "./Components/Login";
import ProtectedRoute from "./Components/Protectedroute";

const Settings = () => <h2>Settings Page</h2>

function LayoutModel() {
  return (
    <PermanentDrawerLeft>
      <Routes>
        <Route path="/dashboard" element={<SelectActionCard />} />
        <Route path="/devices/cpu" element={<Devicelist />} />
        <Route path="/devices/memory" element={<Memorylist />} />
        <Route path="/metrics" element={<MetricsList />} />
        <Route path="/alarms/active" element={<Alaramlist />} />
        <Route path="/alarms/history" element={<Alramhistory />} />
        <Route path="/events/list" element={<Eventlist />} />
        <Route path="/events/logs" element={<Eventlogs />} />
        <Route path="/performance" element={<Performacelist />} />
        <Route path="/topology" element={<Topologylist />} />
        <Route path="/reports" element={<Reportlist />} />
        <Route path="/settings" element={<Settings />} />
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
