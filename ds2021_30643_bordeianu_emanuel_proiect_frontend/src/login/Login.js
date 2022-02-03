import React from "react";
import axiosInstance from "../axios";

import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Container from "@material-ui/core/Container";
import { Grid } from "@material-ui/core";


class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",

/*             loginResponse: {
                role: "",
                id: 0
            }, */
/*             loginTokenResponse: {
                role: "",
                id: 0,
                token: "",
            }, */
            
            role: "",
            id: 0,
            token: "",
        };
    }

    updateAttributes = (event) => {
        event.preventDefault();
        this.setState({
          [event.target.name]: event.target.value,
        });
    };

    handleInput = event => {
        const { value, name } = event.target;
        this.setState({
            [name]: value
        });
        console.log(value);
    };

    onSubmitFun = event => {
        event.preventDefault();
        let credentials = {
            username: this.state.username,
            password: this.state.password
        }

        axiosInstance.post("/login/", credentials)
            .then(
                res => {
                    console.log("res = " + res);
                    //const val = res.data;
                    this.setState({
                        //loginTokenResponse: val,
                        role: res.data.role,
                        id: res.data.id,
                        token: res.data.token,
                    });
                    console.log("Succes !!!");
                    //console.log("DATA = " + this.state.loginTokenResponse);
                    //console.log("ID loginTokenResponse = " + this.state.loginTokenResponse.id);
                    //console.log("ROLE loginTokenResponse = " + this.state.loginTokenResponse.role);
                    //console.log("TOKEN loginTokenResponse = " + this.state.loginTokenResponse.token);

                    console.log("ID loginTokenResponse = " + this.state.id);
                    console.log("ROLE loginTokenResponse = " + this.state.role);
                    console.log("TOKEN loginTokenResponse = " + this.state.token);

                    sessionStorage.setItem("USERS", res.data.role);
                    sessionStorage.setItem("USERS_ID", res.data.id);
                    sessionStorage.setItem("USERS_TOKEN", res.data.token);

                    //localStorage.setItem("USERS", res.data.role);
                    //localStorage.setItem("USERS_ID", res.data.id);
                    //localStorage.setItem("", res.data.token);
                    console.log(this.state.role)

                    //if(localStorage.getItem("USERS") === "CLIENTS"){
                    if(sessionStorage.getItem("USERS") === "CLIENTS"){
                        console.log("Succes verificare CLIENTS !");
                        //console.log(this.state.loginTokenResponse);
                        this.props.history.push("/clientspage");
                    }
                    //else if(localStorage.getItem("USERS") === "ADMINS"){
                    else if(sessionStorage.getItem("USERS") === "ADMINS"){
                        console.log("Succes verificare ADMINS !");
                        //this.props.history.push("/clientsList")
                        this.props.history.push("/adminspage")
                    }
                    else{
                        console.log("Wrong data !!!");
                        return <div> Wrong data </div>
                    }
                }
            )
            .catch(error => {
                console.log(error)
            })
    }

    render() {
        return (
            <Container maxWidth="sm">
                <div>
                    <Grid>
                        <form onSubmit={this.onSubmitFun}>
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                id="username"
                                label="Username"
                                name="username"
                                autoComplete="string"
                                onChange={this.handleInput}
                                autoFocus
                            />
                            <TextField
                                variant="outlined"
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                onChange={this.handleInput}
                                autoComplete="current-password"
                            />
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                            >
                                Sign In
                            </Button>
                        </form>
                    </Grid>
                </div>
                
                <div>
                    <Grid>
                        <form>
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                                onClick={() => {
                                    this.props.history.push("/register");
                                }}
                            >
                                Register
                            </Button>
                        </form>
                    </Grid>
                </div>
            </Container>
        );
    }

}

export default Login;


