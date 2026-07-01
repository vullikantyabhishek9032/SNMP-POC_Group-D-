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
    Checkbox,
    FormControlLabel,
} from "@mui/material";
import {
    Visibility,
    VisibilityOff,
    Login as LoginIcon,
} from "@mui/icons-material";
import { useNavigate } from "react-router-dom";

const Login = () => {
    const [showPassword, setShowPassword] = useState(false);
    
    const navigate = useNavigate();

    const handleLogin = () => {
        console.log("Login clicked");
        localStorage.setItem("isLoggedIn", "true");
        navigate("/dashboard");
    };

    return (
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
                    />

                    <TextField
                        fullWidth
                        label="Password"
                        placeholder="Enter password"
                        type={showPassword ? "text" : "password"}
                        margin="normal"
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
                        onClick={handleLogin}
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
    );
};

export default Login;











