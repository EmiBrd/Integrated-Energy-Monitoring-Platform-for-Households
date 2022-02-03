import React from 'react'

import Button from '@mui/material/Button';


class ClientsPage extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            id: 0,
        }
    }


    render() {
        const data_role = sessionStorage.getItem("USERS");
        //const data_id = sessionStorage.getItem("USERS_ID");
        //console.log("data_role = " + data_role);
        //console.log("data_id = " + data_id);

        if (data_role === "CLIENTS"){
            return (
                <div>
                    <h3>Hello from Client.</h3>
                    <div className="customNavbar">

                      
                        <div>
                            <Button onClick={() => {this.props.history.push("/clientsdevices");}}>
                                View_Devices
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/clientssensors");}}>
                                View_Sensors
                            </Button>
                        </div>

          
                        <div>
                            <Button onClick={() => {this.props.history.push("/clientsenergconstodayandhistory");}}>
                                Clients_Energ_Consum_Today_And_History
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/barchartclients");}}>
                                Bar_Chart_Energy_Consumption
                            </Button>
                        </div>

                        
                        <div>
                            <Button onClick={() => {this.props.history.push("/watchsensorsimulator");}}>
                                Watch_Sensor_Simulator
                            </Button>
                        </div>
                      
                        <div>
                            <Button onClick={() => {this.props.history.push("/logout");}}>
                                Logout
                            </Button>
                        </div>
                        
                    </div>
                </div>
            );
        }
                
        else{
            return <div>
                      You need to login as a client :((
                  </div>;
        } 
    }
    

}

export default ClientsPage



