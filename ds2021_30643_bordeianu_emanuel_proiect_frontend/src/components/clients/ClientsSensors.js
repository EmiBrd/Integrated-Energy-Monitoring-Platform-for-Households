import React from "react";
import axiosInstance from "../../axios";

import { Table, TableCell, TableBody, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import Button from '@mui/material/Button';


class ClientsSensors extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            sensors: [],
            idSensor: 0,
            description: "",
            maxValue: 0.0,

            idClient: 0,
            sensorsClient: [],

            id: 0,

            viewSensors: false,
        }
    }

    componentDidMount(){
        axiosInstance.get("/sensors/findallsensors")
        .then( response => {
            this.setState({
                sensors: response.data,
            })
        }).catch( error => {
            console.log("Eroare la get findallsensors");
            console.log(error);
        })
    }


    sensorsOfClient(){
        //const id_Client_LS = localStorage.getItem("USERS_ID");
        //console.log("id_Client_LS = " + id_Client_LS);
        const id_Client_SS = sessionStorage.getItem("USERS_ID");
        console.log("id_Client_SS = " + id_Client_SS);

        axiosInstance.get("/sensors/clientssensors/"+id_Client_SS)
        .then( response => {
            console.log("Intra in get-ul de la clientssensors");
            console.log(response.data);
            this.setState ({
                sensorsClient: response.data,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la clientssensors");
            console.log(error);
        })
    }

    viewSensors(){
        this.sensorsOfClient();
        this.setState({
            viewSensors: !this.state.viewSensors,
        })
        console.log("viewSensors");
        console.log(this.state.viewSensors);
    }


    render(){
        const data_role = sessionStorage.getItem("USERS");
        //const data_id = sessionStorage.getItem("USERS_ID");
        //console.log("data_role ClientsEnergConsTodayAndHistory = " + data_role);
        //console.log("data_id ClientsEnergConsTodayAndHistory = " + data_id);

        if (data_role === "CLIENTS"){
            return(
                <div className="container" >

                    <div className="row">
                        <div className="col s16">
                        <TableContainer component={Paper}>
                            <Button onClick={() => this.viewSensors() } variant="outlined"> 
                                View_Sensors
                            </Button>
                            <Table sx={{ minWidth: 750 }} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    {/* <TableCell align="center">Measurement ID</TableCell> */}
                                    <TableCell align="center"> Description </TableCell>
                                    <TableCell align="center"> MaxValue </TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                            {this.state.viewSensors && this.state.sensorsClient.map((sensorClient) => (
                                <TableRow
                                    key={sensorClient.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    {/* <TableCell component="th" scope="row"> {sensorClient.id} </TableCell> */}
                                    <TableCell align="center">{sensorClient.description}</TableCell>
                                    <TableCell align="center">{sensorClient.maxValue}</TableCell>
                                    
                                </TableRow>
                                ))}
                            </TableBody>
                            </Table>
                        </TableContainer>
                        </div>
                    </div>


                </div>
            )
        }
                
        else{
            return <div>
                    You need to login as a client :((
                </div>;
        } 
    }


}


export default ClientsSensors
