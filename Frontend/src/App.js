// import logo from './logo.svg';
// import './App.css';
// import TemporaryDrawer from './Drawer';
// import Alaramlist from './Components/alarmlist';

// function App() {
//   return (
//       <TemporaryDrawer>
//         <Alaramlist />
//       </TemporaryDrawer>
//   );
// }

// export default App;

import { BrowserRouter, Routes, Route } from "react-router-dom";
import PermanentDrawerLeft from "./Drawer";
import Alaramlist from "./Components/alarmlist";

const Dashboard = () => <h2>Dashboard</h2>;
const Devices = () => <h2>Devices</h2>;
const AlarmHistory = () => <h2>Alarm History</h2>;
const EventList = () => <h2>Event List</h2>
const EventLogs = () => <h2>Event Logs</h2>
const Performance = () => <h2>Performance list</h2>
const Topology = () => <h2>Topology Activities</h2>
const Reports = () => <h2>View the list of Report here</h2>
const Settings = () => <h2>Settings Page</h2>

function App() {
  return (
    <BrowserRouter>
      <PermanentDrawerLeft>
        <Routes>
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/devices" element={<Devices />} />
          <Route path="/alarms/active" element={<Alaramlist />} />
          <Route path="/alarms/history" element={<AlarmHistory />} />
          <Route path="/events/list" element={<EventList />} />
          <Route path="/events/logs" element={<EventLogs />} />
          <Route path="/performance" element={<Performance />} />
          <Route path="/topology" element={<Topology />} />
          <Route path="/reports" element={<Reports />} />
          <Route path="/settings" element={<Settings />} />
        </Routes>
      </PermanentDrawerLeft>
    </BrowserRouter>
  );
}

export default App;
