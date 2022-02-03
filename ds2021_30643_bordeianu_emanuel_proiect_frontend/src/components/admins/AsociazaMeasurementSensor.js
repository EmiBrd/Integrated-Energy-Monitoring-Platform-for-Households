
import React from "react";
import axiosInstance from "../../axios";

import { Table, TableCell, TableBody, TableContainer,
    TableHead, TableRow, Paper } from '@mui/material';
    
import Button from '@mui/material/Button';


class AsociazaMeasurementSensor extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            measurements: [],
            idMeasurement: 0,
            timestampM: "",
            energyConsumption: 0.0,
            
            sensors: [],
            idSensor: 0,
            descriptionS: "",
            maxValue: 0.0,
            
            id: 0,

            viewSensors: false,
            viewMeasurements: false,
        }
    }


    componentDidMount(){
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

        axiosInstance.get("/measurements/findallmeasurements")
        .then( response => {
            this.setState({
                measurements: response.data,
            })
        })
        .catch(error =>
            console.log(error)
        )
        console.log("measurements");
        console.log(this.state.measurements);
    }

    viewSensors(){
        this.setState({
            viewSensors: !this.state.viewSensors,
        })
        console.log("viewSensors");
        console.log(this.state.viewSensors);
    }

    viewMeasurements(){
        this.setState({
            viewMeasurements: !this.state.viewMeasurements,
        })
        console.log("viewMeasurements");
        console.log(this.state.viewMeasurements);
    }


    asociere(idMeasurement, idSensor){
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

        axiosInstance.get("/measurements/"+idMeasurement)
        .then( response => {
            console.log("Intra in get-ul de la measurements");
            console.log(response.data);
            this.setState ({
                idMeasurement: response.data.id,
                timestampM: response.data.timestamp,
                energyConsumptionM: response.data.energyConsumption,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la measurements");
            console.log(error);
        })

        

        if(idMeasurement !== 0){
            axiosInstance.put("/measurements/asociaza/"+idMeasurement+"/"+idSensor)
            .then( (response) => {
                this.componentDidMount();
                //localStorage.setItem("IDDEVICE", response.data.idDevice);
                //localStorage.setItem("IDSENSOR", response.data.idSensor);
                console.log("Sensors response: "+this.state.sensors);
                console.log("Measurements response: "+this.state.measurements);
            })
            .catch( error => {
                console.log("Eroare la put in asociere measurements-sensors");
                console.log(error);
            })
        }
        else{
            console.log("idMeasurement = 0 in asociere measurements-sensors");
        }
    }


    stergere_asociere(idMeasurement, idSensor){
        if(idSensor !== 0){
            axiosInstance.put("/measurements/stergeasocierea/"+idMeasurement+"/"+idSensor)
            .then( (response) => {
                this.componentDidMount();
                //localStorage.setItem("IDDEVICE", response.data.idDevice);
                //localStorage.setItem("IDSENSOR", response.data.idSensor);
            })
            .catch( error => {
                console.log("Eroare la put in stergere asociere MEASUREMENT-SENSOR");
                console.log(error);
            })
        }
        else{
            console.log("idMeasurement = 0 in stergere asociere MEASUREMENT-SENSOR");
        }
        console.log("idMeasurement cu state = "+this.state.idMeasurement+" , idSensor cu state = "+this.state.idSensor);
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
                                            <TableCell component="th" scope="row">{sensor.id}</TableCell>
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



                    <div className="row2">
                        <div className="col s16">
                            <TableContainer component={Paper}>
                            <Button onClick={() => this.viewMeasurements() } variant="outlined"> View_Measurements </Button>
                                <Table sx={{ minWidth: 850 }} aria-label="simple table">
                                    <TableHead>
                                        <TableRow>
                                        <TableCell align="center">Measurement ID</TableCell>
                                        <TableCell align="center"> Timestamp </TableCell>
                                        <TableCell align="center"> EnergyConsumption </TableCell>

                                        <TableCell align="center">Actions</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {this.state.viewMeasurements && this.state.measurements.map((measurement) => (
                                        <TableRow
                                            key={measurement.id}
                                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                        >
                                            <TableCell component="th" scope="row">{measurement.id}</TableCell>
                                            <TableCell align="center">{measurement.timestamp}</TableCell>
                                            <TableCell align="center">{measurement.energyConsumption}</TableCell>

                                            <TableCell> 
                                                <Button onClick={() => this.setState({ idMeasurement: measurement.id}) } variant="outlined">
                                                    Select_Measurement
                                                </Button>     
                                            </TableCell>

                                        </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                        <div>
                            Selecting_idMeasurement  "{this.state.idMeasurement}"
                        </div>

                    </div>



                    <div>
                        <Button onClick={() => this.asociere(this.state.idMeasurement, this.state.idSensor) } variant="outlined"> 
                            Asociere 
                        </Button>
                    </div>
                    <div>
                        <Button onClick={() => this.stergere_asociere(this.state.idMeasurement, this.state.idSensor) } variant="outlined"> 
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


export default AsociazaMeasurementSensor
