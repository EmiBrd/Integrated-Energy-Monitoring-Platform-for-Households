import React from "react";
import axiosInstance from "../../axios";

import Button from '@mui/material/Button';
import { Table, TableCell, TableBody, TableContainer,
    TableHead, TableRow, Paper } from '@mui/material';

import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";


class WatchSensorSimulator extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            measurements: [],
            idMeasurement: 0,
            timestamp: "",
            energyConsumption: 0.0,

            idClient: 0,
            measurementsClient: [],
            
            viewMeasurements: false,
        }
    }

    // connect() {
    //     const URL = "http://localhost:8080/websocket";
    //     //const URL = "https://demo-spring-emi.herokuapp.com/websocket";
    //     const websocket = new SockJS(URL);
    //     const stompClient = Stomp.over(websocket);
    //     stompClient.connect({}, frame => {
    //         console.log("Conectat la " + frame);
    //         stompClient.subscribe("/enablebroker/websocket/measurements/measurementt", notification => {
    //             let message = notification.body;
    //             console.log(message);
    //             alert(message);
    //         })
    //     })
    // }

    connect() {
        //const URL = "http://localhost:8080/websocket";
        const URL = "https://spring-emi-backend.herokuapp.com/websocket";
        const websocket = new SockJS(URL);
        const stompClient = Stomp.over(websocket);
        stompClient.connect({}, frame => {
            console.log("CONNECTED TO: " + frame);
            stompClient.subscribe("/enablebroker/websocket/measurements/msg", notification => {
                let message = notification.body;
                console.log(message);
                alert(message);
            })
            stompClient.subscribe("/enablebroker/websocket/measurements/measurementt", notification => {
                const e = JSON.parse(notification.body);
                console.log(e);
                const prev = this.state.measurementsClient;
                prev.push(e);
                this.setState({measurementsClient: prev});
                console.log(this.state.measurementsClient);
            })
        })
    }


    componentDidMount(){
        this.connect();
        axiosInstance.get('/measurements/findallmeasurements')
        .then( response => {
            this.setState({measurements: response.data});
        })
        .catch( error =>
            console.log(error)
        );
    }  


    measurementsOfClient(){
      const id_Client_SS = sessionStorage.getItem("USERS_ID");
      console.log("id_Client_SS = " + id_Client_SS);

      axiosInstance.get("/measurements/findallmeasurementsfromfile/"+id_Client_SS)
      .then( response => {
          console.log("Intra in get-ul de la clients MEASUREMENTS");
          console.log(response.data);
          this.setState ({
              measurementsClient: response.data,
          })
      })
      .catch(error => {
          console.log("NU intra in get-ul de la clients MEASUREMENTS");
          console.log(error);
      })
    }

    viewMeasurements(){
        this.measurementsOfClient();
        this.setState({
            viewMeasurements: !this.state.viewMeasurements,
        })
        console.log("viewMeasurements");
        console.log(this.state.viewMeasurements);
    }


    render(){
        const data_role = sessionStorage.getItem("USERS");
        
        if (data_role === "CLIENTS"){
            return(
                <div className="container" >

                    <div className="row">
                        <div className="col s16">
                        <TableContainer component={Paper}>
                            <Button onClick={() => this.viewMeasurements() } variant="outlined"> 
                                View_Measurements
                            </Button>
                            <Table sx={{ minWidth: 750 }} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    {/* <TableCell align="center">Measurement ID</TableCell> */}
                                    <TableCell align="center"> Timestamp </TableCell>
                                    <TableCell align="center"> EnergyConsumption </TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                            {this.state.viewMeasurements && this.state.measurementsClient.map( (measurementClient) => (
                                <TableRow
                                    key={measurementClient.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    {/* <TableCell component="th" scope="row"> {measurementClient.id} </TableCell> */}
                                    <TableCell align="center">{measurementClient.timestamp}</TableCell>
                                    <TableCell align="center">{measurementClient.energyConsumption}</TableCell>
                                    
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


export default WatchSensorSimulator
