import React, { useState, useEffect } from "react";
import axios from "axios";

export default function TestButton() {
    const testButtonHandler = () => {
        console.log("Test 시작");
        const token = "fakeToken";
        const config = {
            headers: { Authorization: `Gomster ${token}` },
        };
        axios.get(`${process.env.REACT_APP_SERVER}/api/member`, config).then((res) => console.log(res));
    };

    return <button onClick={testButtonHandler}>테스트버튼</button>;
}
