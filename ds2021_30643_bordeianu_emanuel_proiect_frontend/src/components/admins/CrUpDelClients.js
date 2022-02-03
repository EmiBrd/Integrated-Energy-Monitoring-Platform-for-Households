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


class CrUpDelClients extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            clients: [],
            id: 0,
            name: "",
            birthDate: "",
            address: "",
            email: "",
            username: "",
        }
    }

    componentDidMount(){
        axiosInstance.get("/clients/findallclients")
        .then( response => {
            this.setState({
                clients: response.data,
            })
        })
    }

    insert(event, id){
        event.preventDefault();
        let body = {
            id: this.state.id,
            name: this.state.name,
            birthDate: this.state.birthDate,
            address: this.state.address,
            email: this.state.email,
            username: this.state.username,
        };
        if(id === 0){
            axiosInstance.post("/clients/insert", body)
            .then( () => {
                this.componentDidMount();
            })
            .catch( error => {
                console.log("Eroare la post in insert");
                console.log(error);
            })
        }
        else{
            axiosInstance.put("/clients/update/"+id, body)
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
        //axiosInstance.delete(`/clients/delete/${id}`)
        axiosInstance.delete("/clients/delete/"+id)
        .then( () => {
            this.componentDidMount();
        })
        .catch( error => {
            console.log("Eroare la delete");
            console.log(error);
        })
    }

    edit(id){
        axiosInstance.get("/clients/"+id)
        .then( response => {
            console.log(response.data);
            this.setState ({
                id: response.data.id,
                name: response.data.name,
                birthDate: response.data.birthDate,
                address: response.data.address,
                email: response.data.email,
                username: response.data.username,
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
                                <div className="input-field col s11">
                                    <i className="material-icons prefix">person</i>
                                    <input onChange={(event)=>this.setState({name:event.target.value})} 
                                    value={this.state.name} type="text" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">Name</label>
                                </div>
                                <div className="input-field col s11">
                                <i className="material-icons prefix">date_range</i>
                                    <input onChange={(event)=>this.setState({birthDate:event.target.value})} 
                                    value={this.state.birthDate} type="date" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">BirthDate</label>
                                </div>
                                <div className="input-field col s11">
                                    <i className="material-icons prefix">address</i>
                                    <input onChange={(event)=>this.setState({address:event.target.value})} 
                                    value={this.state.address} type="text" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">Address</label>
                                </div>
                                <div className="input-field col s11">
                                    <i className="material-icons prefix">email</i>
                                    <input onChange={(event)=>this.setState({email:event.target.value})} 
                                    value={this.state.email} type="email" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">Email</label>
                                </div>
                                <div className="input-field col s11">
                                    <i className="material-icons prefix">person_outline</i>
                                    <input onChange={(event)=>this.setState({username:event.target.value})} 
                                    value={this.state.username} type="text" id="autocomplete-input" className="autocomplete" />
                                    <label htmlFor="autocomplete-input">Username</label>
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
                                <TableCell align="center">Client ID</TableCell>
                                <TableCell align="center"> Name </TableCell>
                                <TableCell align="center">BirthDate</TableCell>
                                <TableCell align="center">Address</TableCell>
                                <TableCell align="center">Email</TableCell>
                                <TableCell align="center">Username</TableCell>
                                <TableCell align="center">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {this.state.clients.map((client) => (
                                <TableRow
                                    key={client.id}
                                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                >
                                    <TableCell component="th" scope="row">
                                    {client.id}
                                    </TableCell>
                                    <TableCell align="center">{client.name}</TableCell>
                                    <TableCell align="center">{client.birthDate}</TableCell>
                                    <TableCell align="center">{client.address}</TableCell>
                                    <TableCell align="center">{client.email}</TableCell>
                                    <TableCell align="center">{client.username}</TableCell>
                                    <TableCell> 
                                        <Button onClick={(event)=>this.edit(client.id)} variant="outlined">Edit</Button>
                                    </TableCell>
                                    <TableCell>
                                        <Button onClick={(event)=>this.delete(client.id)} variant="outlined">Delete</Button>
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


export default CrUpDelClients





