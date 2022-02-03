import React from "react";
import axiosInstance from "../../axios";

import { Table, TableCell, TableBody, TableContainer, TableHead, TableRow, Paper } from '@mui/material';
import Button from '@mui/material/Button';


class AsociazaDeviceClient extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            devices: [],
            idDevice: 0,
            description: "",
            addressLocation: "",
            maxEnerCons: 0.0,
            avgBaselineEnerCons: 0.0,
            client: [],
            id: 0,

            clients: [],
            idClient: 0,
            name: "",
            birthDate: "",
            address: "",
            email: "",
            username: "",
            password: "",
            active: 0,

            viewClients: false,
            viewDevices: false,
        }
    }

    

    componentDidMount(){
        axiosInstance.get("/clients/findallclients")
        .then( response => {
            this.setState({
                clients: response.data,
            })
        })
        .catch(error =>
             console.log(error)
        )
        console.log("Clienti");
        console.log(this.state.clients);
            
        
        axiosInstance.get("/devices/findalldevices")
        .then( response => {
            this.setState({
                devices: response.data,
            })
        })
        .catch(error =>
            console.log(error)
        )
        console.log("device-uri");
        console.log(this.state.devices);
    }


    viewClients(){
        this.setState({
            viewClients: !this.state.viewClients,
        })
        console.log("viewClients");
        console.log(this.state.viewClients);
    }

    viewDevices(){
        this.setState({
            viewDevices: !this.state.viewDevices,
        })
        console.log("viewDevices");
        console.log(this.state.viewDevices);
    }


    asociere( idDevice, idClient){
        axiosInstance.get("/clients/"+idClient)
        .then( response => {
            console.log("Intra in get-ul de la clients");
            console.log(response.data);
            this.setState ({
                //client: response.data,
                idClient: response.data.id,
                name: response.data.name,
                birthDate: response.data.birthDate,
                address: response.data.address,
                email: response.data.email,
                username: response.data.username,
                password: response.data.password,
                active: response.data.active,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la clients");
            console.log(error);
        })

        axiosInstance.get("/devices/"+idDevice)
        .then( response => {
            console.log("Intra in get-ul de la devices");
            console.log(response.data);
            this.setState ({
                idDevice: response.data.id,
                description: response.data.description,
                addressLocation: response.data.addressLocation,
                maxEnerCons: response.data.maxEnerCons,
                avgBaselineEnerCons: response.data.avgBaselineEnerCons,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la devices");
            console.log(error);
        })


        //console.log("INAINTE de BODIES idDevice = "+idDevice+" , idDevice cu state = "+this.state.idDevice+
        //                " , idClient cu state= "+this.state.idClient);
        
        if(idDevice !== 0){
            //axiosInstance.put(`/devices/asociaza/${idDevice}/${idClient}`, body_device)
            axiosInstance.put("/devices/asociaza/"+idDevice+"/"+idClient)
            .then( (response) => {
                this.componentDidMount();
                localStorage.setItem("IDCLIENT", response.data.idClient);
                localStorage.setItem("IDDEVICE", response.data.idDevice);
                console.log("Clients response: "+this.state.clients);
                console.log("Devices response: "+this.state.devices);
            })
            .catch( error => {
                console.log("Eroare la put in asociere");
                console.log(error);
            })
            console.log("In IF idDevice = "+idDevice+" , idDevice cu state = "+this.state.idDevice+
                            " , idClient cu state = "+this.state.idClient);
            
            //console.log("In IF body Client = " + body_client.target.idClient);
            //console.log("In IF body Device = " + body_device);
        }
        else{
            console.log("idDevice = 0 in asociere");
        }
        console.log("idDevice = "+idDevice+" , idDevice cu state = "+this.state.idDevice+
        " , idClient cu state = "+this.state.idClient);
        //console.log("Client body: " + this.state.client.idClient);
        //console.log("Devices: " + this.state.devices);
    }


    stergere_asociere(idDevice, idClient){
        //console.log("INAINTE de BODIES idDevice = "+idDevice+" , idDevice cu state = "+this.state.idDevice+
        //                " , idClient cu state= "+this.state.idClient);
         let body_device = {
            idDevice: this.state.id,
            //id: this.state.id,
            description: this.state.description,
            addressLocation: this.state.addressLocation,
            maxEnerCons: this.state.maxEnerCons,
            avgBaselineEnerCons: this.state.avgBaselineEnerCons,
        };
        
        if(idDevice !== 0){
            axiosInstance.put(`/devices/stergeasocierea/${idDevice}/${idClient}`, body_device)
            .then( (response) => {
                this.componentDidMount();
                localStorage.setItem("IDCLIENT", response.data.idClient);
                localStorage.setItem("IDDEVICE", response.data.idDevice);
            })
            .catch( error => {
                console.log("Eroare la put in asociere");
                console.log(error);
            })
            console.log("In IF idDevice = "+idDevice+" , idDevice cu state = "+this.state.idDevice+
                            " , idClient cu state = "+this.state.idClient);
            //console.log("In IF body Client = " + body_client.target.idClient);
            //console.log("In IF body Device = " + body_device);
        }
        else{
            console.log("idDevice = 0 in asociere");
        }
        console.log("idDevice = "+idDevice+" , idDevice cu state = "+this.state.idDevice+
        " , idClient cu state = "+this.state.idClient);
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
                        <div className="col s16">
                            <TableContainer component={Paper}>
                                <Button onClick={() => this.viewClients() } variant="outlined"> 
                                    View_Clients 
                                </Button>

                                <Table sx={{ minWidth: 850 }} aria-label="simple table">
                                <TableHead>
                                    <TableRow>
                                    <TableCell align="right">Client ID</TableCell>
                                    <TableCell align="right"> Name </TableCell>
                                    <TableCell align="right">BirthDate</TableCell>
                                    <TableCell align="right">Address</TableCell>
                                    <TableCell align="right">Email</TableCell>
                                    <TableCell align="right">Username</TableCell>
                                    <TableCell align="right">Actions</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {this.state.viewClients && this.state.clients.map((client) => (
                                    <TableRow
                                        key={client.id}
                                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                    >
                                        <TableCell component="th" scope="row">{client.id}</TableCell>
                                        <TableCell align="right">{client.name}</TableCell>
                                        <TableCell align="right">{client.birthDate}</TableCell>
                                        <TableCell align="right">{client.address}</TableCell>
                                        <TableCell align="right">{client.email}</TableCell>
                                        <TableCell align="right">{client.username}</TableCell>

                                        <TableCell> 
                                            <Button onClick={() => this.setState({ idClient: client.id}) } variant="outlined">
                                                Select_Client
                                            </Button>     
                                        </TableCell>
                                        
                                    </TableRow>
                                    ))}
                                </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                        <div>
                            Selecting_idClient  "{this.state.idClient}"
                        </div>

                    </div>



                    <div className="row2">
                        <div className="col s16">
                            <TableContainer component={Paper}>
                                <Button onClick={() => this.viewDevices() } variant="outlined"> 
                                    View_Devices 
                                </Button>
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
                                        {this.state.viewDevices && this.state.devices.map((device) => (
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
                                                <Button onClick={() => this.setState({ idDevice: device.id}) } variant="outlined">
                                                    Select_Device
                                                </Button>     
                                            </TableCell>

                                        </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>

                        <div>
                            Selecting_idDevice  "{this.state.idDevice}"
                        </div>

                    </div>



                    <div>
                        <Button onClick={() => this.asociere(this.state.idDevice, this.state.idClient) } variant="outlined"> 
                            Asociere 
                        </Button>
                    </div>
                    <div>
                        <Button onClick={() => this.stergere_asociere(this.state.idDevice, this.state.idClient) } variant="outlined"> 
                            Stergere_asociere 
                        </Button>
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


export default AsociazaDeviceClient
