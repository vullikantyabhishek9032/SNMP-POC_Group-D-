import { useState } from "react";

export default function Searchinput({ PlaceHolder, search }) {
    return (
        <>
            <div style={styles.searchBox}>
                <input
                    type="text"
                    placeholder={PlaceHolder}
                    style={styles.input}
                    onChange={search}
                />
                {/* <button style={styles.button} onClick={() => {
                            onButtonClick()
                        }}>Search</button> */}
            </div>
        </>
    )
}

const styles = {

    searchBox: {
        display: "flex",
        gap: "12px",
        marginBottom: "30px",
    },

    input: {
        flex: 1,
        padding: "10px",
        borderRadius: "6px",
        border: "1px solid #ccc",
        outline: "none",
    },

    button: {
        padding: "10px 16px",
        backgroundColor: "#1976d2",
        color: "#fff",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer",
    },
};

