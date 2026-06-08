
import { BrowserRouter, Routes, Route } from "react-router-dom";
import PermanentDrawerLeft from "./Drawer";
import Alaramlist from "./Components/alarmlist";
import Eventlist from "./Components/Eventlist";
import { useDispatch, useSelector } from "react-redux";
import { fetchUsers } from "./Redux/userSlice";
import { useEffect } from "react";
import SelectActionCard from "./Components/Dashboard";
import Devicelist from "./Components/Devices";
import Alramhistory from "./Components/Alaramhistory";
import Eventlogs from "./Components/Eventlogs";
import Performacelist from "./Components/Performace";
import Topologylist from "./Components/Topology";
import Reportlist from "./Components/Report";

const Settings = () => <h2>Settings Page</h2>

function App() {
  return (
    <BrowserRouter>
      <PermanentDrawerLeft>
        <Routes>
          <Route path="/dashboard" element={<SelectActionCard />} />
          <Route path="/devices" element={<Devicelist />} />
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
    </BrowserRouter>
  );
}

export default App;
