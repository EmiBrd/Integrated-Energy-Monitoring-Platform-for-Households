import React from "react";
import axiosInstance from "../../axios";

import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

import Button from '@mui/material/Button';


class CrUpDelMeasurements extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            measurements: [],
            id: 0,
            timestamp: "",
            energyConsumption: 0.0,
        }
    }

    componentDidMount(){
        axiosInstance.get("/measurements/findallmeasurements")
        .then( response => {
            this.setState({
                measurements: response.data,
            })
        })
    }

    insert(event, id){
        event.preventDefault();
        let body = {
            id: this.state.id,
            timestamp: this.state.timestamp,
            energyConsumption: this.state.energyConsumption
        };
        if(id === 0){
            axiosInstance.post("/measurements/insert", body)
            .then( () => {
                this.componentDidMount();
            })
            .catch( error => {
                console.log("Eroare la post in insert");
                console.log(error);
            })
        }
        else{
            axiosInstance.put("/measurements/update/"+id, body)
            .then( () => {
                this.componentDidMount();
            })
            .catch( error => {
                console.log("Eroare la put in insert");
                console.log(error);
            })
        }
    }

    delete(id){
        //axiosInstance.delete(`/measurements/delete/${id}`)
        axiosInstance.delete("/measurements/delete/"+id)
        .then( () => {
            this.componentDidMount();
        })
        .catch( error => {
            console.log("Eroare la delete");
            console.log(error);
        })
    }

    edit(id){
        axiosInstance.get("/measurements/"+id)
        .then( response => {
            console.log(response.data);
            this.setState ({
                id: response.data.id,
                timestamp: response.data.timestamp,
                energyConsumption: response.data.energyConsumption,
            })
        })
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
                        <div className="col s6">
                            <form onSubmit={(event) => this.insert(event, this.state.id)}>
                                <div className="input-field col s10">
                                    <i className="material-icons prefix">person</i>
                                    <input onChange={(event)=>this.setState({timestamp:event.target.value})} 
                                    value={this.state.timestamp} type="date" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">timestamp</label>
                                </div>
                                <div className="input-field col s10">
                                    <i className="material-icons prefix">email</i>
                                    <input onChange={(event)=>this.setState({energyConsumption:event.target.value})} 
                                    value={this.state.energyConsumption} type="text" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">EnergyConsumption</label>
                                </div>
                                
                                <button className="btn waves-effect waves-light right" type="submit" name="action">Submit
                                    <i className="material-icons right">send</i>
                                </button>
                            </form>
                        </div>

                        <div className="col s16">
                        <TableContainer component={Paper}>
                            <Table sx={{ minWidth: 850 }} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                <TableCell align="center"> Measurement ID </TableCell>
                                <TableCell align="center"> Timestamp </TableCell>
                                <TableCell align="center"> EnergyConsumption </TableCell>
                                
                                <TableCell align="center">Actions</TableCell>
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
                                    
                                    <TableCell> 
                                        <Button onClick={(event)=>this.edit(measurement.id)} variant="outlined">
                                            Edit
                                        </Button>
                                    </TableCell>
                                    <TableCell>
                                        <Button onClick={(event)=>this.delete(measurement.id)} variant="outlined">
                                            Delete
                                        </Button>
                                    </TableCell>
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
                You need to login as an Admin :((
            </div>;
        } 
    }

}


export default CrUpDelMeasurements





