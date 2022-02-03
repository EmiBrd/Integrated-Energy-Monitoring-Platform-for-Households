import React from "react";
import axiosInstance from "../../axios";

import { Table, TableCell, TableBody, TableContainer,
    TableHead, TableRow, Paper } from '@mui/material';
    
import Button from '@mui/material/Button';


class AsociazaSensorDevice extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            sensors: [],
            idSensor: 0,
            descriptionS: "",
            maxValue: 0.0,
            device: [],

            devices: [],
            idDevice: 0,
            descriptionD: "",
            addressLocation: "",
            maxEnerCons: 0.0,
            avgBaselineEnerCons: 0.0,

            id: 0,

            viewDevices: false,
            viewSensors: false,
        }
    }

    

    componentDidMount(){
        axiosInstance.get("/devices/findalldevices")
        .then( response => {
            this.setState({
                devices: response.data,
            })
        })
        .catch(error =>
            console.log(error)
        )
        console.log("device-uri");
        console.log(this.state.devices);


        axiosInstance.get("/sensors/findallsensors")
        .then( response => {
            this.setState({
                sensors: response.data,
            })
        })
        .catch(error =>
             console.log(error)
        )
        console.log("SENZORI: ");
        console.log(this.state.sensors);
        
    }

    viewDevices(){
        this.setState({
            viewDevices: !this.state.viewDevices,
        })
        console.log("viewDevices");
        console.log(this.state.viewDevices);
    }

    viewSensors(){
        this.setState({
            viewSensors: !this.state.viewSensors,
        })
        console.log("viewSensors");
        console.log(this.state.viewSensors);
    }

    asociere(idSensor, idDevice){
        axiosInstance.get("/devices/"+idDevice)
        .then( response => {
            console.log("Intra in get-ul de la devices");
            console.log(response.data);
            this.setState ({
                idDevice: response.data.id,
                description: response.data.description,
                addressLocation: response.data.addressLocation,
                maxEnerCons: response.data.maxEnerCons,
                avgBaselineEnerCons: response.data.avgBaselineEnerCons,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la devices");
            console.log(error);
        })

        axiosInstance.get("/sensors/"+idSensor)
        .then( response => {
            console.log("Intra in get-ul de la sensors");
            console.log(response.data);
            this.setState ({
                id: response.data.idSensor,
                descriptionS: response.data.description,
                maxValue: response.data.maxValue,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la sensors");
            console.log(error);
        })

        if(idSensor !== 0){
            //axiosInstance.put(`/sensors/asociaza/${idSensor}/${idDevice}`, body_device)
            axiosInstance.put("/sensors/asociaza/"+idSensor+"/"+idDevice)
            .then( (response) => {
                this.componentDidMount();
                localStorage.setItem("IDDEVICE", response.data.idDevice);
                localStorage.setItem("IDSENSOR", response.data.idSensor);
                
                console.log("Devices response: "+this.state.devices);
                console.log("Sensors response: "+this.state.sensors);
            })
            .catch( error => {
                console.log("Eroare la put in asociere senzori-device");
                console.log(error);
            })
        }
        else{
            console.log("idSensor = 0 in asociere sensor-device");
        }
    }


    stergere_asociere(idSensor, idDevice){
        if(idDevice !== 0){
            //axiosInstance.put(`/sensors/stergeasocierea/${idSensor}/${idDevice}`)
            axiosInstance.put("/sensors/stergeasocierea/"+idSensor+"/"+idDevice)
            .then( (response) => {
                this.componentDidMount();
                localStorage.setItem("IDDEVICE", response.data.idDevice);
                localStorage.setItem("IDSENSOR", response.data.idSensor);
            })
            .catch( error => {
                console.log("Eroare la put in stergere asociere SENSOR-DEVICE");
                console.log(error);
            })
        }
        else{
            console.log("idSensor = 0 in stergere asociere SENSOR-DEVICE");
        }
        console.log("idSensor cu state = "+this.state.idSensor+" , idDevice cu state = "+this.state.idDevice);
    }


    render(){
        const data_role = sessionStorage.getItem("USERS");
        //const data_id = sessionStorage.getItem("USERS_ID");
        //console.log("data_role = " + data_role);
        //console.log("data_id = " + data_id);

        if (data_role === "ADMINS"){
            return(
                <div className="container" >

                    <div className="row">
                        <div className="col s16">
                            <TableContainer component={Paper}>
                            <Button onClick={() => this.viewDevices() } variant="outlined"> View_Devices </Button>
                                <Table sx={{ minWidth: 850 }} aria-label="simple table">
                                    <TableHead>
                                        <TableRow>
                                        <TableCell align="center">Device ID</TableCell>
                                        <TableCell align="center"> Description </TableCell>
                                        <TableCell align="center"> AddressLocation </TableCell>
                                        <TableCell align="center"> MaxEnerCons </TableCell>
                                        <TableCell align="center"> AvgBaselineEnerCons </TableCell>
                        
                                        <TableCell align="center">Actions</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {this.state.viewDevices && this.state.devices.map((device) => (
                                        <TableRow
                                            key={device.id}
                                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                        >
                                            <TableCell component="th" scope="row">
                                            {device.id}
                                            </TableCell>
                                            <TableCell align="center">{device.description}</TableCell>
                                            <TableCell align="center">{device.addressLocation}</TableCell>
                                            <TableCell align="center">{device.maxEnerCons}</TableCell>
                                            <TableCell align="center">{device.avgBaselineEnerCons}</TableCell>

                                            <TableCell> 
                                                <Button onClick={() => this.setState({ idDevice: device.id}) } variant="outlined">
                                                    Select_Device
                                                </Button>     
                                            </TableCell>

                                        </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                        <div>
                            Selecting_idDevice  "{this.state.idDevice}"
                        </div>

                    </div>



                    <div className="row2">
                        <div className="col s16">
                            <TableContainer component={Paper}>
                            <Button onClick={() => this.viewSensors() } variant="outlined"> View_Sensors </Button>
                                <Table sx={{ minWidth: 850 }} aria-label="simple table">
                                    <TableHead>
                                        <TableRow>
                                        <TableCell align="center">Sensor ID</TableCell>
                                        <TableCell align="center"> Description </TableCell>
                                        <TableCell align="center"> MaxValue </TableCell>

                                        <TableCell align="center">Actions</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {this.state.viewSensors && this.state.sensors.map((sensor) => (
                                        <TableRow
                                            key={sensor.id}
                                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                        >
                                            <TableCell component="th" scope="row">
                                            {sensor.id}
                                            </TableCell>
                                            <TableCell align="center">{sensor.description}</TableCell>
                                            <TableCell align="center">{sensor.maxValue}</TableCell>

                                            <TableCell> 
                                                <Button onClick={() => this.setState({ idSensor: sensor.id}) } variant="outlined">
                                                    Select_Sensor
                                                </Button>     
                                            </TableCell>

                                        </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                        <div>
                            Selecting_idSensor  "{this.state.idSensor}"
                        </div>

                    </div>



                    <div>
                        <Button onClick={() => this.asociere(this.state.idSensor, this.state.idDevice) } variant="outlined"> 
                            Asociere 
                        </Button>
                    </div>
                    <div>
                        <Button onClick={() => this.stergere_asociere(this.state.idSensor, this.state.idDevice) } variant="outlined"> 
                            Stergere_asociere 
                        </Button>
                    </div>

                </div>
            )
        }
        else{
            return <div>
                You need to login as an Admin :((
            </div>;
        } 
    }

}


export default AsociazaSensorDevice
