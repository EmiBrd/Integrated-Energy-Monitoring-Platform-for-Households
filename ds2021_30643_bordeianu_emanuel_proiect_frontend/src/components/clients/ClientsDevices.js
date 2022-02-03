import React from "react";
import axiosInstance from "../../axios";

import { Table, TableCell, TableBody, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import Button from '@mui/material/Button';


class ClientsDevices extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            devices: [],
            idDevice: 0,
            description: "",
            addressLocation: "",
            maxEnerCons: 0.0,
            avgBaselineEnerCons: 0.0,

            idClient: 0,
            devicesClient: [],

            id: 0,

            viewDevices: false,
        }
    }

    componentDidMount(){
        const config = {
            headers: {
                Authorization: 'Bearer ' + sessionStorage.getItem("USERS_TOKEN"),
            }
        }; 
        axiosInstance.get("/devices/findalldevices", config)
        .then( response => {
            this.setState({
                devices: response.data,
            })
        }).catch( error => {
            console.log("Eroare la get findalldevices");
            console.log(error);
        })
    }


    devicesOfClient(){
        //const id_Client_LS = localStorage.getItem("USERS_ID");
        //console.log("id_Client_LS = " + id_Client_LS);
        const id_Client_SS = sessionStorage.getItem("USERS_ID");
        console.log("id_Client_SS = " + id_Client_SS);

        axiosInstance.get("/devices/clientsdevices/"+id_Client_SS)
        .then( response => {
            console.log("Intra in get-ul de la clientsdevices");
            console.log(response.data);
            this.setState ({
                devicesClient: response.data,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la clientsdevices");
            console.log(error);
        })
    }

    viewDevices(){
        this.devicesOfClient();
        this.setState({
            viewDevices: !this.state.viewDevices,
        })
        console.log("viewDevices");
        console.log(this.state.viewDevices);
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
                            <Button onClick={() => this.viewDevices() } variant="outlined"> 
                                View_Devices
                            </Button>
                            <Table sx={{ minWidth: 850 }} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    {/* <TableCell align="center">Measurement ID</TableCell> */}
                                    <TableCell align="center"> Description </TableCell>
                                    <TableCell align="center"> AddressLocation </TableCell>
                                    <TableCell align="center"> MaxEnerCons </TableCell>
                                    <TableCell align="center"> AvgBaselineEnerCons </TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                            {this.state.viewDevices && this.state.devicesClient.map((deviceClient) => (
                                <TableRow
                                    key={deviceClient.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    {/* <TableCell component="th" scope="row"> {deviceClient.id} </TableCell> */}
                                    <TableCell align="center">{deviceClient.description}</TableCell>
                                    <TableCell align="center">{deviceClient.addressLocation}</TableCell>
                                    <TableCell align="center">{deviceClient.maxEnerCons}</TableCell>
                                    <TableCell align="center">{deviceClient.avgBaselineEnerCons}</TableCell>
                                    
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


export default ClientsDevices
