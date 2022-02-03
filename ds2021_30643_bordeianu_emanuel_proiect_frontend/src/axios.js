import axios from "axios"

const axiosInstance = axios.create({
    //baseURL: "http://localhost:8080/",
    baseURL: "https://spring-emi-backend.herokuapp.com/",
    
/*     commons:{
        "Authorization": "Bearer " + sessionStorage.getItem("USERS_TOKEN"),
    }, */

    headers: {
        post: {
            "Content-Type": "application/json",
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Headers":
                "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With"
        }
    }
});

export default axiosInstance;