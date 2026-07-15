import { Box, Button, Card, CardContent, FormControl, InputLabel, MenuItem, Select, TextField, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { userDataUrl } from "../Endpoint/endpoint";
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import { useNavigate } from "react-router-dom";

export default function AddUser() {

    const validatorSchema = yup.object().shape({
        username: yup.string().min(3, "Minimum of 3 characters").max(10, "Maximum of 10 characters").required("The character length should be minimum of 3 and max of 10 char"),
        email: yup.string().email("Please enter a valid email address").required("User Email is required"),
        mobileNumber: yup.number().required("Mobile Number is required").min(10, "Minimum of 10 characters is required"),
        roleId: yup.string().required("User Role is required"),
        password: yup.string().required("Password is required")
    });

    const {
        register,
        handleSubmit,
        formState: { errors, isSubmitting, isValid },
        reset,
    } = useForm
            ({
                resolver: yupResolver(validatorSchema),
                mode: 'onBlur',
                defaultValues: {
                    username: '',
                    email: '',
                    mobileNumber: 0,
                    roleId: '',
                    Password: ""
                }
            })

    const [dropdown, setDropdown] = useState();

    const response = async () => {
        try {
            const Aidata = await fetch(`${userDataUrl}/api/roles`);
            const Apiresponse = await Aidata.json();
            if (Apiresponse) {
                setDropdown(Apiresponse)
            }
        } catch (error) {
            console.log("log the error here", error)
        }
    }
    useEffect(() => {
        response()
    }, [])

    const [dropdowndata, setDropdowndata] = useState();

    const handleChange = (e) => {
        setDropdowndata(e.target.value)
    }

    console.log("log it...", dropdowndata)

    const navigate = useNavigate();

    const onSubmit = async (dataValues) => {
        console.log("log the value here", dataValues)
        try {
            const submitResponse = await fetch(
                `${userDataUrl}/api/users/create`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(dataValues),
                }
            );

            const responseData = await submitResponse.json();
            if (responseData) {
                alert("User Created Successfully....");
                navigate("/user")
            }
        } catch (error) {
            console.log("log the error here in console", error);
        }
    }
    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                <Card
                    sx={{
                        width: { xs: "100%", sm: 450 },
                        borderRadius: 2,
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        p: 2,
                        ml: 25
                    }}
                >
                    <CardContent sx={{ p: 4 }}>
                        <Box textAlign="center" mb={4}>
                            <Typography
                                variant="h4"
                                fontWeight="bold"
                                color="primary"
                                gutterBottom
                            >
                                Add New User
                            </Typography>
                        </Box>
                        <TextField
                            fullWidth
                            label="User Name"
                            placeholder="User Name"
                            margin="normal"
                            {...register("username")}
                        />
                        {errors.username ? <Typography sx={{ color: "red", fontSize: "11px" }}>{errors.username.message} </Typography> : ""}
                        <TextField
                            fullWidth
                            label="Email"
                            placeholder="Email"
                            margin="normal"
                            {...register("email")}
                        />
                        {errors.email ? <Typography sx={{ color: "red", fontSize: "11px" }}>{errors.email.message} </Typography> : ""}
                        <TextField
                            fullWidth
                            label="Password"
                            placeholder="Password"
                            margin="normal"
                            {...register("password")}
                        />
                        {errors.password ? <Typography sx={{ color: "red", fontSize: "11px" }}>{errors.password.message} </Typography> : ""}
                        <TextField
                            fullWidth
                            label="Mobile No"
                            placeholder="Mobile No"
                            margin="normal"
                            {...register("mobileNumber")}
                        />
                        {errors.mobileNumber ? <Typography sx={{ color: "red", fontSize: "11px" }}>{errors.mobileNumber.message} </Typography> : ""}
                        <FormControl
                            sx={{
                                minWidth: 410,
                                "& .MuiOutlinedInput-root": {
                                    borderRadius: "12px",
                                    backgroundColor: "#fafafa",
                                },
                                mt: 2
                            }}
                        >
                            <InputLabel>Role Name</InputLabel>

                            <Select
                                {...register("roleId")}
                                value={dropdowndata}
                                label="Role Name"
                                onChange={(e) => {
                                    handleChange(e)
                                }}
                            >
                                {dropdown?.map((drop, index) => (
                                    <MenuItem value={drop.id}>{drop.name}</MenuItem>
                                ))}

                            </Select>
                        </FormControl>
                        {errors.roleId ? <Typography sx={{ color: "red", fontSize: "11px" }}>{errors.roleId.message} </Typography> : ""}
                        <Button
                            fullWidth
                            variant="contained"
                            size="small"
                            type="submit"
                            sx={{
                                mt: 3,
                                py: 1.5,
                                borderRadius: 2,
                                fontWeight: "bold",
                                textTransform: "none",
                                fontSize: "16px",
                            }}
                        >
                            Submit
                        </Button>
                    </CardContent>
                </Card>
            </form>
        </>
    )
}


