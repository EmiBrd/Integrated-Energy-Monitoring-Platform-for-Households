import React from "react";
import axiosInstance from "../../axios";

//import {FormGroup, Input, Label} from 'reactstrap';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

import Button from '@mui/material/Button';


class CrUpDelDevices extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            devices: [],
            id: 0,
            description: "",
            addressLocation: "",
            maxEnerCons: 0.0,
            avgBaselineEnerCons: 0.0,
        }
    }

    componentDidMount(){
        axiosInstance.get("/devices/findalldevices")
        .then( response => {
            this.setState({
                devices: response.data,
            })
        })
    }

    insert(event, id){
        event.preventDefault();
        let body = {
            id: this.state.id,
            description: this.state.description,
            addressLocation: this.state.addressLocation,
            maxEnerCons: this.state.maxEnerCons,
            avgBaselineEnerCons: this.state.avgBaselineEnerCons,
        };
        if(id === 0){
            axiosInstance.post("/devices/insert", body)
            .then( (response) => {
                console.log("Cand id === 0");
                console.log(response.data);
                this.componentDidMount();
            })
            .catch( error => {
                console.log("Eroare la post in insert");
                console.log(error);
            })
        }
        else{
            axiosInstance.put("/devices/update/"+id, {
                id: this.state.id,
                description: this.state.description,
                addressLocation: this.state.addressLocation,
                maxEnerCons: this.state.maxEnerCons,
                avgBaselineEnerCons: this.state.avgBaselineEnerCons,
            })
            .then( (response) => {
                console.log("Cand id NU e 0");
                console.log(response.data);
                this.componentDidMount();
            })
            .catch( error => {
                console.log("Eroare la put in insert");
                console.log(error);
            })
        }
    }

    delete(id){
        //axiosInstance.delete(`/devices/delete/${id}`)
        axiosInstance.delete("/devices/delete/"+id)
        .then( () => {
            this.componentDidMount();
        })
        .catch( error => {
            console.log("Eroare la delete");
            console.log(error);
        })
    }

    edit(id){
        axiosInstance.get("/devices/"+id)
        .then( response => {
            console.log(response.data);
            this.setState ({
                id: response.data.id,
                description: response.data.description,
                addressLocation: response.data.addressLocation,
                maxEnerCons: response.data.maxEnerCons,
                avgBaselineEnerCons: response.data.avgBaselineEnerCons,
            })
        })
        .catch(error => {
            console.log("Eroare in edit");
            console.log(error);
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
                                <div className="input-field col s11">
                                    <i className="material-icons prefix">person</i>
                                    <input onChange={(event)=>this.setState({description:event.target.value})} 
                                    value={this.state.description} type="text" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">description</label>
                                </div>
                                <div className="input-field col s11">
                                <i className="material-icons prefix">person</i>
                                    <input onChange={(event)=>this.setState({addressLocation:event.target.value})} 
                                    value={this.state.addressLocation} type="text" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">addressLocation</label>
                                </div>
                                <div className="input-field col s11">
                                    <i className="material-icons prefix">address</i>
                                    <input onChange={(event)=>this.setState({maxEnerCons:event.target.value})} 
                                    value={this.state.maxEnerCons} type="text" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">maxEnerCons</label>
                                </div>
                                <div className="input-field col s11">
                                    <i className="material-icons prefix">address</i>
                                    <input onChange={(event)=>this.setState({avgBaselineEnerCons:event.target.value})} 
                                    value={this.state.avgBaselineEnerCons} type="text" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">avgBaselineEnerCons</label>
                                </div>
                                
                                <button className="btn waves-effect waves-light left" type="submit" name="action">Submit
                                    <i className="material-icons right">send</i>
                                </button>
                                
                            </form>

                        </div>

                        <div className="col s16">
                        <TableContainer component={Paper}>
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
                                    <TableCell> 
                                        <Button onClick={(event)=>this.edit(device.id)} variant="outlined">Edit</Button>
                                    </TableCell>
                                    <TableCell>
                                        <Button onClick={(event)=>this.delete(device.id)} variant="outlined">Delete</Button>
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


export default CrUpDelDevices





