import React from "react";
import axiosInstance from "../../axios";

import { Table, TableCell, TableBody, TableContainer,
        TableHead, TableRow, Paper } from '@mui/material';


class ViewMeasurementsComponent extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            measurements: [],
            id: 0,
            timestamp: "",
            energyConsumption: 0.0
        }
    }

    componentDidMount(){
        axiosInstance.get('/measurements/findallmeasurements')
        .then( response => {
            this.setState({measurements: response.data});
        })
        .catch( error =>
            console.log(error)
        );
    }

    render(){
      const data_role = sessionStorage.getItem("USERS");
      //const data_id = sessionStorage.getItem("USERS_ID");
      //console.log("data_role View_Measurements = " + data_role);
      //console.log("data_id View_Measurements = " + data_id);

      if (data_role === "ADMINS"){
            return (
                <TableContainer component={Paper}>
                  <Table sx={{ minWidth: 450 }} aria-label="simple table">
                    <TableHead>
                      <TableRow>
                        <TableCell> Measurement ID </TableCell>
                        <TableCell align="center"> Timestamp </TableCell>
                        <TableCell align="center"> Energy Consumption </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {this.state.measurements.map((measurement) => (
                        <TableRow
                          key={measurement.id}
                          sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                          <TableCell component="th" scope="row">{measurement.id}</TableCell>
                          <TableCell align="center">{measurement.timestamp}</TableCell>
                          <TableCell align="center">{measurement.energyConsumption}</TableCell>
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

export default ViewMeasurementsComponent