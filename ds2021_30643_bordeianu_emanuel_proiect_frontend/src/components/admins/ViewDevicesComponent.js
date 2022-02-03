import React from "react";
import axiosInstance from "../../axios";

import { Table, TableCell, TableBody, TableContainer,
        TableHead, TableRow, Paper } from '@mui/material';


class ViewDevicesComponent extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            devices: [],
            id: 0,
            description: "",
            addressLocation: "",
            maxEnerCons: 0.0,
            avgBaselineEnerCons: 0.0
        }
    }

    componentDidMount(){
        axiosInstance.get('/devices/findalldevices')
        .then( response => {
            this.setState({devices: response.data});
        })
        .catch( error =>
            console.log(error)    
        );
    }

    render(){
        const data_role = sessionStorage.getItem("USERS");
        //const data_id = sessionStorage.getItem("USERS_ID");
        //console.log("data_role View_Devices = " + data_role);
        //console.log("data_id View_Devices = " + data_id);

        if (data_role === "ADMINS"){
            return (
                <TableContainer component={Paper}>
                  <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                      <TableRow>
                        <TableCell> ID Device </TableCell>
                        <TableCell align="center"> Description </TableCell>
                        <TableCell align="center"> AddressLocation </TableCell>
                        <TableCell align="center"> Max Energ Cons </TableCell>
                        <TableCell align="center"> Avg Baseline Energ Cons </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {this.state.devices.map((device) => (
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
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </TableContainer>
            );
        }
                
        else{
            return <div>
                      You need to login as an admin :((
                  </div>;
        } 
    }


}

export default ViewDevicesComponent;
















