import React from "react";
import axiosInstance from "../../axios";

import { Table, TableCell, TableBody, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import Button from '@mui/material/Button';


class ClientsEnergConsTodayAndHistory extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            measurements: [],
            idMeasurement: 0,
            timestamp: "",
            energyConsumption: 0.0,

            idClient: 0,
            measurementsHistory: [],
            measurementsToday: [],

            id: 0,

            viewEnergHistory: false,
            viewEnergToday: false,
        }
    }

    componentDidMount(){
        axiosInstance.get("/measurements/findallmeasurements")
        .then( response => {
            this.setState({
                measurements: response.data,
            })
        }).catch( error => {
            console.log("Eroare la get findallmeasurements");
            console.log(error);
        })
    }


    historicalEnergCons(){
        //const id_Client_LS = localStorage.getItem("USERS_ID");
        //console.log("id_Client_LS = " + id_Client_LS);
        const id_Client_SS = sessionStorage.getItem("USERS_ID");
        console.log("id_Client_SS = " + id_Client_SS);

        axiosInstance.get("/measurements/clientconsumhistorical/"+id_Client_SS)
        .then( response => {
            console.log("Intra in get-ul de la clientconsumhistorical");
            console.log(response.data);
            this.setState ({
                measurementsHistory: response.data,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la clientconsumhistorical");
            console.log(error);
        })
    }

    viewEnergHistory(){
        this.historicalEnergCons();
        this.setState({
            viewEnergHistory: !this.state.viewEnergHistory,
        })
        console.log("viewEnergHistory");
        console.log(this.state.viewEnergHistory);
    }

    TodayEnergCons(){
        //const id_Client_LS = localStorage.getItem("USERS_ID");
        //console.log("id_Client_LS = " + id_Client_LS);
        const id_Client_SS = sessionStorage.getItem("USERS_ID");
        console.log("id_Client_SS = " + id_Client_SS);

        axiosInstance.get("/measurements/clientconsumtoday/"+id_Client_SS)
        .then( response => {
            console.log("Intra in get-ul de la clientconsumtoday");
            console.log(response.data);
            this.setState ({
                measurementsToday: response.data,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la clientconsumtoday");
            console.log(error);
        })
    }

    viewEnergToday(){
        this.TodayEnergCons();
        this.setState({
            viewEnergToday: !this.state.viewEnergToday,
        })
        console.log("viewEnergToday");
        console.log(this.state.viewEnergToday);
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
                            <Button onClick={() => this.viewEnergHistory() } variant="outlined"> 
                                View_Energ_History
                            </Button>
                            <Table sx={{ minWidth: 850 }} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell align="center">Measurement ID</TableCell>
                                    <TableCell align="center"> Timestamp </TableCell>
                                    <TableCell align="center"> EnergyCons </TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                            {this.state.viewEnergHistory && this.state.measurementsHistory.map((measurementHistory) => (
                                <TableRow
                                    key={measurementHistory.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row"> {measurementHistory.id} </TableCell>
                                    <TableCell align="center">{measurementHistory.timestamp}</TableCell>
                                    <TableCell align="center">{measurementHistory.energyConsumption}</TableCell>
                                    
                                </TableRow>
                                ))}
                            </TableBody>
                            </Table>
                        </TableContainer>
                        </div>
                    </div>


                    <div className="row2">
                        <div className="col s16">
                        <TableContainer component={Paper}>
                            <Button onClick={() => this.viewEnergToday() } variant="outlined"> 
                                View_Energ_Today 
                            </Button>
                            <Table sx={{ minWidth: 850 }} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell align="center">Measurement ID</TableCell>
                                    <TableCell align="center"> Timestamp </TableCell>
                                    <TableCell align="center"> EnergyCons </TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                            {this.state.viewEnergToday && this.state.measurementsToday.map((measurementToday) => (
                                <TableRow
                                    key={measurementToday.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row"> {measurementToday.id} </TableCell>
                                    <TableCell align="center">{measurementToday.timestamp}</TableCell>
                                    <TableCell align="center">{measurementToday.energyConsumption}</TableCell>
                                    
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


export default ClientsEnergConsTodayAndHistory
