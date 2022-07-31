import React, { useState, useEffect } from "react";
import axios from "axios";

export default function NaverLoginButton() {
    const naverLoginHandler = () => {
        const NAVER_LOGIN_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${process.env.REACT_APP_NAVER_CLIENT_ID}&redirect_uri=http://localhost:3000`;
        window.location.href = NAVER_LOGIN_URL;
    };
    const params = new URLSearchParams(window.location.search);
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        const code = params.get("code");
        params.delete("code");
        if (loading === false && code !== null) {
            setLoading(true);
            axios.get(`${process.env.REACT_APP_SERVER}/api/auth/google/login/token?code=${code}`).then((res) => {
                console.log(res);
            });
        }
    }, []);

    return <button onClick={naverLoginHandler}>네이버로그인</button>;
}
