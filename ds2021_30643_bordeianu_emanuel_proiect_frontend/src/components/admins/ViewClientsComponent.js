import React from 'react'
import axiosInstance from '../../axios';

import { Table, TableCell, TableBody, TableContainer,
    TableHead, TableRow, Paper } from '@mui/material';


class ViewClientsComponent extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            clients:[]
        }
    }

    componentDidMount(){
        axiosInstance.get('/clients/findallclients').then((response) => {
            this.setState({clients: response.data})
        })
        .catch(error =>
            console.log(error)
        )
        console.log(this.state.clients);
    }

    render(){
        const data_role = sessionStorage.getItem("USERS");
        //const data_id = sessionStorage.getItem("USERS_ID");
        //console.log("data_role = " + data_role);
        //console.log("data_id = " + data_id);

        if (data_role === "ADMINS"){
            return(
                <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                    <TableCell> Client ID </TableCell>
                    <TableCell align="center"> Client Name </TableCell>
                    <TableCell align="center"> Client BirthDate </TableCell>
                    <TableCell align="center"> Client Address </TableCell>
                    <TableCell align="center"> Client Email </TableCell>
                    <TableCell align="center"> Client Username </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {this.state.clients.map((client) => (
                    <TableRow
                        key={client.id}
                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                        <TableCell component="th" scope="row"> {client.id} </TableCell>
                        <TableCell align="center">{client.name}</TableCell>
                        <TableCell align="center">{client.birthDate}</TableCell>
                        <TableCell align="center">{client.address}</TableCell>
                        <TableCell align="center">{client.email}</TableCell>
                        <TableCell align="center">{client.username}</TableCell>
                    </TableRow>
                    ))}
                </TableBody>
                </Table>
                </TableContainer>
            )
        }
        else{
            return <div>
                You need to login as an Admin :((
            </div>;
        } 
    }

}

export default ViewClientsComponent


