import React from "react";

import Button from '@mui/material/Button';


class AdminsPage extends React.Component{
    constructor(props){
        super(props);
        this.state = {
          id: 0,
      }
    }

    render(){
        const data_role = sessionStorage.getItem("USERS");
        //const data_id = sessionStorage.getItem("USERS_ID");
        //console.log("data_role = " + data_role);
        //console.log("data_id = " + data_id);

        if (data_role === "ADMINS"){
        //if(sessionStorage.getItem("USERS") === "ADMINS" ){
            return (
                <div>
                    <h3> Admins Page with CRUD </h3>
                    <div className="customNavbar">
                      
                        <div>
                            <Button onClick={() => {this.props.history.push("/viewclientscomponent");}}>
                                View_Clients
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/viewdevicescomponent");}}>
                                View_Devices
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => { this.props.history.push("/viewsensorscomponent");}}>
                                View_Sensors
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/viewmeasurementscomponent");}}>
                                View_Measurements
                            </Button>
                        </div>

                        

                        <div>
                            <Button onClick={() => {this.props.history.push("/crupdelclients");}}>
                                CRUD_Clients
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/crupdeldevices");}}>
                                CRUD_Devices
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/crupdelsensors");}}>
                                CRUD_Sensors
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/crupdelmeasurements");}}>
                                CRUD_Measurements
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/asociazadeviceclient");}}>
                                ASSOCIATE_DEVICE_CLIENT
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/asociazasensordevice");}}>
                                ASSOCIATE_SENSOR_DEVICE
                            </Button>
                        </div>

                        <div>
                            <Button onClick={() => {this.props.history.push("/asociazameasurementsensor");}}>
                                ASSOCIATE_MEASUREMENT_SENSOR
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
                You need to login as an Admin :((
            </div>;
        } 
    }

}

export default AdminsPage






