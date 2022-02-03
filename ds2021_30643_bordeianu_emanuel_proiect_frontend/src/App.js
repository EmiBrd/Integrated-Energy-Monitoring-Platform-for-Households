
import './App.css';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";

import Login from './login/Login';
import Register from './login/Register';
import Logout from './login/Logout';


import ClientsPage from './components/clients/ClientsPage';
import ClientsDevices from './components/clients/ClientsDevices';
import ClientsSensors from './components/clients/ClientsSensors';
import ClientsEnergConsTodayAndHistory from './components/clients/ClientsEnergConsTodayAndHistory'
import BarChartClients from './components/clients/BarChartClients';

import WatchSensorSimulator from './components/clients/WatchSensorSimulator';



import AdminsPage from './components/admins/AdminsPage';
import ViewClientsComponent from './components/admins/ViewClientsComponent';
import ViewDevicesComponent from './components/admins/ViewDevicesComponent';
import ViewSensorsComponent from './components/admins/ViewSensorsComponent';
import ViewMeasurementsComponent from './components/admins/ViewMeasurementsComponent';
import CrUpDelClients from './components/admins/CrUpDelClients';
import CrUpDelDevices from './components/admins/CrUpDelDevices';
import CrUpDelSensors from './components/admins/CrUpDelSensors';
import CrUpDelMeasurements from './components/admins/CrUpDelMeasurements';
import AsociazaDeviceClient from './components/admins/AsociazaDeviceClient';
import AsociazaSensorDevice from './components/admins/AsociazaSensorDevice';
import AsociazaMeasurementSensor from './components/admins/AsociazaMeasurementSensor';


function App() {
  const defaultRoute = window.location.pathname === "/" ? <Redirect to="/login" /> : undefined;
  return (
    <Router>
      <Switch>
        <Route exact path ="/login" component={Login} />
        <Route exact path = "/register" component={Register} />
        <Route exact path = "/logout" component={Logout} />


        <Route exact path = "/clientspage" component={ClientsPage} />
        <Route exact path = "/clientsdevices" component={ClientsDevices} />
        <Route exact path = "/clientssensors" component={ClientsSensors} />
        <Route exact path = "/clientsenergconstodayandhistory" component={ClientsEnergConsTodayAndHistory} />
        <Route exact path = "/barchartclients" component={BarChartClients} />

        <Route exact path = "/watchsensorsimulator" component={WatchSensorSimulator} />
        

        <Route exact path = "/adminspage" component={AdminsPage} />
        <Route exact path = "/viewclientscomponent" component={ViewClientsComponent} />
        <Route exact path = "/viewdevicescomponent" component={ViewDevicesComponent} />
        <Route exact path = "/viewsensorscomponent" component={ViewSensorsComponent} />
        <Route exact path = "/viewmeasurementscomponent" component={ViewMeasurementsComponent} />
        <Route exact path = "/crupdelclients" component={CrUpDelClients} />
        <Route exact path = "/crupdeldevices" component={CrUpDelDevices} />
        <Route exact path = "/crupdelsensors" component={CrUpDelSensors} />
        <Route exact path = "/crupdelmeasurements" component={CrUpDelMeasurements} />
        <Route exact path = "/asociazadeviceclient" component={AsociazaDeviceClient} />
        <Route exact path = "/asociazasensordevice" component={AsociazaSensorDevice} />
        <Route exact path = "/asociazameasurementsensor" component={AsociazaMeasurementSensor} />
        

      </Switch>
      {defaultRoute}
    </Router>

  );
}

export default App;