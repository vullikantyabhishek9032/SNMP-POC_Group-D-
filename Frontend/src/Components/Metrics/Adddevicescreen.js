import { Box, Button, Card, CardContent, TextField, Typography } from "@mui/material";
import { Styles } from "../../CommonComponents/Commonstyles";
import { useRef } from "react";
// import { useForm } from 'react-hook-form';
// import * as yup from 'yup';
// import { yupResolver } from '@hookform/resolvers/yup';

export default function Adddevice() {

    const handleSubmit = () => {
        console.log("...........................")
    }
    const ref = useRef();

    return (
        <>
            {/* <form ref={ref} onSubmit={handleSubmit}> */}
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
                            Add New Device
                        </Typography>
                    </Box>
                    <TextField
                        fullWidth
                        label="Device Name"
                        placeholder="Device Name"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        label="Display Name"
                        placeholder="Display Name"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        label="Device Type"
                        placeholder="Device Type"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        label="Description"
                        placeholder="Enter email"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        label="Vendor"
                        placeholder="Vendor"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        label="Model"
                        placeholder="Model"
                        margin="normal"
                    />
                    <TextField
                        fullWidth
                        label="Message"
                        placeholder="Message"
                        margin="normal"
                    />
                    <Button
                        fullWidth
                        variant="contained"
                        size="large"
                        // startIcon={<LoginIcon />}
                        onClick={handleSubmit}
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
            {/* </form> */}
        </>
    )
}