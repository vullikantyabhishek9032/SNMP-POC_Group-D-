import React, { useState } from "react";
import {
    Box,
    Card,
    CardContent,
    Typography,
    TextField,
    Button,
    InputAdornment,
    IconButton,
} from "@mui/material";
import {
    Visibility,
    VisibilityOff,
    Login as LoginIcon,
} from "@mui/icons-material";
import { useNavigate } from "react-router-dom";
import { useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';

// localStorage.setItem("isLoggedIn", "true");
// navigate("/dashboard");

const Login = () => {
    const [showPassword, setShowPassword] = useState(false);

    const navigate = useNavigate();

    const validatorSchema = yup.object().shape({
        email: yup.string().email("Please enter a valid email address").required("User Email is required"),
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
                    email: '',
                    password: ""
                }
            })

    const onSubmit = (datavalues) => {
        console.log("Login clicked", datavalues);
    };

    return (
        <form onSubmit={handleSubmit(onSubmit)} noValidate>
            <Box
                sx={{
                    minHeight: "95vh",
                    background:
                        "linear-gradient(135deg, #0f172a 0%, #1e293b 50%, #2563eb 100%)",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    p: 2,
                }}
            >
                <Card
                    sx={{
                        width: { xs: "100%", sm: 450 },
                        borderRadius: 4,
                        boxShadow: "0px 10px 40px rgba(0,0,0,0.3)",
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
                                NMS Portal
                            </Typography>

                            <Typography variant="body2" color="text.secondary">
                                Sign in to access your dashboard
                            </Typography>
                        </Box>

                        <TextField
                            fullWidth
                            label="Email Address"
                            placeholder="Enter email"
                            margin="normal"
                            {...register("email")}
                        />
                        {errors.email ? <Typography sx={{ color: "red", fontSize: "11px" }}>{errors.email.message} </Typography> : ""}
                        <TextField
                            fullWidth
                            label="Password"
                            placeholder="Enter password"
                            type={showPassword ? "text" : "password"}
                            margin="normal"
                            {...register("password")}
                            InputProps={{
                                endAdornment: (
                                    <InputAdornment position="end">
                                        <IconButton
                                            onClick={() =>
                                                setShowPassword((prev) => !prev)
                                            }
                                        >
                                            {showPassword ? (
                                                <VisibilityOff />
                                            ) : (
                                                <Visibility />
                                            )}
                                        </IconButton>
                                    </InputAdornment>
                                ),
                            }}
                        />
                        {errors.password ? <Typography sx={{ color: "red", fontSize: "11px" }}>{errors.password.message} </Typography> : ""}
                        <Typography
                            variant="body2"
                            color="primary"
                            sx={{ cursor: "pointer" }}
                        >
                            Forgot Password?
                        </Typography>

                        <Button
                            fullWidth
                            variant="contained"
                            size="large"
                            startIcon={<LoginIcon />}
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
                            Login
                        </Button>
                    </CardContent>
                </Card>
            </Box>
        </form>
    );
};

export default Login;











