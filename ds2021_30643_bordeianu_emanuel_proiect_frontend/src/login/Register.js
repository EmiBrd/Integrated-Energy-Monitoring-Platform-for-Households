import React from "react";

import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Container from "@material-ui/core/Container";
import { Grid } from "@material-ui/core";
import axiosInstance from "../axios.js";


class Register extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            id: 0,
            name: "",
            birthDate: "",
            address: "",
            email: "",
            username: "",
            password: "",
            active: 0,
        }
    }

    handleInput = event => {
        const { value, name } = event.target;
        this.setState({
          [name]: value,
        });
        console.log(value);
    };

    onSubmitFun = (event) => {
        event.preventDefault();
        let date_inregistrare = {
            email: this.state.email,
            username: this.state.username,
            password: this.state.password,
            name: this.state.name, 
            address: this.state.address,
            birthDate: this.state.birthDate,
            active: 1,
        };
        console.log(date_inregistrare);
        this.componentDidMount(date_inregistrare);
      };

    componentDidMount(date_inregistrare){
        axiosInstance.post("clients/register", date_inregistrare)
        .then(response => {
            this.props.history.push("/login");
        })
        .catch( error => {
            console.log(error);
        })
    }

      render() {
        //localStorage.removeItem("Active user");
        //localStorage.removeItem("USER");
        //localStorage.removeItem("USER_ID");
        //sessionStorage.removeItem("USERS");
        //sessionStorage.removeItem("USERS_ID");

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
                        id="email"
                        label="Email"
                        name="email"
                        autoComplete="string"
                        onChange={this.handleInput}
                        autoFocus
                    />
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
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="name"
                        label="Name"
                        name="name"
                        autoComplete="string"
                        onChange={this.handleInput}
                        autoFocus
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="address"
                        label="Address"
                        name="address"
                        autoComplete="string"
                        onChange={this.handleInput}
                        autoFocus
                    />
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="birthDate"
                        label="BirthDate"
                        name="birthDate"
                        type="date"
                        autoComplete="date"
                        onChange={this.handleInput}
                        autoFocus
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
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

export default Register


