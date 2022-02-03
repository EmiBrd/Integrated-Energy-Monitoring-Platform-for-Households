import React from "react";
import axiosInstance from "../../axios";

import { Table, TableCell, TableBody, TableContainer,
        TableHead, TableRow, Paper } from '@mui/material';


class ViewSensorsComponent extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            sensors: [],
            id: 0,
            description: "",
            maxValue: 0.0
        }
    }

    componentDidMount(){
        axiosInstance.get('/sensors/findallsensors')
        .then( response => {
            this.setState({sensors: response.data});
        })
        .catch( error =>
            console.log(error)
        );
    }

    render(){
        const data_role = sessionStorage.getItem("USERS");
        //const data_id = sessionStorage.getItem("USERS_ID");
        //console.log("data_role View_Sensors = " + data_role);
        //console.log("data_id View_Sensors = " + data_id);

        if ( data_role === "ADMINS" ){
            return (
                <TableContainer component={Paper}>
                  <Table sx={{ minWidth: 450 }} aria-label="simple table">
                    <TableHead>
                      <TableRow>
                        <TableCell> ID Sensor </TableCell>
                        <TableCell align="center"> Description </TableCell>
                        <TableCell align="center"> MaxValue </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {this.state.sensors.map((sensor) => (
                        <TableRow
                          key={sensor.id}
                          sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                          <TableCell component="th" scope="row">
                            {sensor.id}
                          </TableCell>
                          <TableCell align="center">{sensor.description}</TableCell>
                          <TableCell align="center">{sensor.maxValue}</TableCell>
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

export default ViewSensorsComponent