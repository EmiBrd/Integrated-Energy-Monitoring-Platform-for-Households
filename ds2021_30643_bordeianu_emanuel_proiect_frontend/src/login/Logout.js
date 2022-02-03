import React from 'react'
import axiosInstance from "../axios.js";

class Logout extends React.Component {
    constructor(){
        super();
        this.state = {
           user:"",
        }
    }

    componentDidMount(){
        const data_id = sessionStorage.getItem("USERS_ID");
        axiosInstance.put("/logout/" + data_id)
        .then(response => {
            this.setState({
                user: response.data,
            });
            console.log(this.state.user);
            sessionStorage.removeItem("USERS_ID");
            sessionStorage.removeItem("USERS");
            this.props.history.push("/login");
        })
        .catch(error => {
            console.log("Eroare la LOGOUT");
            console.log(error)
        })
    }

     render() {
        return (
            <div> 
            </div>    
        )                                
    }

}

export default Logout;

