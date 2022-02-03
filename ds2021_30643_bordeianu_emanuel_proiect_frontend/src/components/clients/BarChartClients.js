import React from "react";
import axiosInstance from "../../axios";
    
import {Bar} from 'react-chartjs-2';
import Button from '@mui/material/Button';


class BarChartClients extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            measurements: [],
            idMeasurement: 0,
            timestamp: "",
            energyConsumption: 0.0, 

            idClient: 0,
            totalEnergConsumSelectedDay: 0.0,
            datee: "",
            energyList: [],

            id: 0,

            viewEnergyInSelectedDay: false,
        }
    }

    componentDidMount(){
        axiosInstance.get("/measurements/findallmeasurements")
        .then( response => {
            this.setState({
                measurements: response.data,
            })
        }).catch( error => {
            console.log("Eroare la get findallmeasurements");
            console.log(error);
        })
    }


    selectedEnergConsTotal(idClient, datee){
        axiosInstance.get("/measurements/clientconsumtotalinaday/"+idClient+"/"+datee)
        .then( response => {
            console.log("Intra in get-ul de la clientconsumtotalinaday");
            console.log(response.data);
            this.setState ({
                totalEnergConsumSelectedDay: response.data,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la clientconsumtotalinaday");
            console.log(error);
        })
        console.log("totalEnergConsumSelectedDay = " + this.state.totalEnergConsumSelectedDay);
    } 


    selectedEnergCons(idClient, datee){
        axiosInstance.get("/measurements/clientconsumbarchart/"+idClient+"/"+datee)
        .then( response => {
            console.log("Intra in get-ul de la clientconsumbarchart");
            console.log(response.data);
            this.setState ({
                energyList: response.data,
            })
        })
        .catch(error => {
            console.log("NU intra in get-ul de la clientconsumbarchart");
            console.log(error);
        })
    }
    

    viewEnergyInSelectedDay(){
        //const data_role = localStorage.getItem("USERS");
        //const data_id = localStorage.getItem("USERS_ID");
        const data_role = sessionStorage.getItem("USERS");
        const data_id = sessionStorage.getItem("USERS_ID");

        console.log("data_role = " + data_role);
        console.log("data_id = " + data_id);

        if (data_role === "CLIENTS"){
            console.log("datee din viewEnergyInSelectedDay = " + this.state.datee);
            this.selectedEnergCons(data_id, this.state.datee);
            this.selectedEnergConsTotal(data_id, this.state.datee);
            this.setState({
                viewEnergyInSelectedDay: !this.state.viewEnergyInSelectedDay,
            })
            console.log("viewEnergyInSelectedDay");
            console.log(this.state.viewEnergyInSelectedDay);
        }
        else{
            return <div>
                You need to login as a Client
            </div>;
        } 
    }


    render() {
        const data_role = sessionStorage.getItem("USERS");
        //const data_id = sessionStorage.getItem("USERS_ID");
        //console.log("data_role BarChartClients = " + data_role);
        //console.log("data_id BarChartClients = " + data_id);

        if (data_role === "CLIENTS"){
            return (
                <div className="container">

                    <div className="row">
                        <div className="col s6">
                            
                            <div className="input-field col s11">
                            <i className="material-icons prefix">date_range</i>
                                <input onChange={(event)=>this.setState({datee: event.target.value})} 
                                value={this.state.datee} type="date" id="autocomplete-input" className="autocomplete" />
                                <label htmlFor="autocomplete-input"> Selected_Day </label>
                            </div>
                                
                            <div>
                                Selected_Day = '{this.state.datee}'
                            </div>    
                        </div>

                        <div className="col s16">
                            <Button onClick={() => this.viewEnergyInSelectedDay() } variant="outlined"> 
                                Confirm_Selected_Day
                            </Button>

                            <div>
                                TotalEnergyConsumed = '{this.state.totalEnergConsumSelectedDay}'
                            </div>
                            <div>
                                Selected_Day = '{this.state.datee}'
                            </div>

                        </div>
                    </div>
                    

                    <div>
                        <Bar
                            data={ {labels: ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10","11", "12", "13", "14",
                                                "15", "16", "17", "18", "19", "20", "21", "22", "23"],
                                        datasets: [
                                        {
                                        label: 'Client historical energy consumption',
                                        backgroundColor: 'rgba(75,192)',
                                        borderColor: 'rgba(0,1)',
                                        borderWidth: 2,
                                        data: [
                                            this.state.energyList[0],
                                            this.state.energyList[1],
                                            this.state.energyList[2],
                                            this.state.energyList[3],
                                            this.state.energyList[4],
                                            this.state.energyList[5],
                                            this.state.energyList[6],
                                            this.state.energyList[7],
                                            this.state.energyList[8],
                                            this.state.energyList[9],
                                            this.state.energyList[10],
                                            this.state.energyList[11],
                                            this.state.energyList[12],
                                            this.state.energyList[13],
                                            this.state.energyList[14],
                                            this.state.energyList[15],
                                            this.state.energyList[16],
                                            this.state.energyList[17],
                                            this.state.energyList[18],
                                            this.state.energyList[19],
                                            this.state.energyList[20],
                                            this.state.energyList[21],
                                            this.state.energyList[22],
                                            this.state.energyList[23],
                                                ]
                                        }
                                        ]
                                    } 
                                    }
                            options={{
                                title:{
                                display:true,
                                text:'View historical energy consumption for each client',
                                fontSize:10
                                },
                                legend:{
                                display:true,
                                position:'center'
                                }
                            }}
                        />
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


export default BarChartClients
