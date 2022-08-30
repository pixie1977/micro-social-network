import React, {useRef, useState} from 'react';
import {ThemeProvider} from '@material-ui/core/styles';
import {withStyles} from '@material-ui/core/styles';
import SockJsClient from 'react-stomp';
import {defaultHeaders, getWsHotNewsUrl} from "../api/api";
import {appTheme, baseStyles} from "../api/styles-template";

export const HotNewsWebsocketComponent = () => {
    let [userData, setUserData] = useState({messages: []});
    const wsUrl = getWsHotNewsUrl();
    const clientRef = useRef(null);
    const sendMessage = (msg) => {
        clientRef.current.sendMessage('/topic/hotnews', msg);
    }
    const onMessageHandler = (msg) => {
        console.log(msg);
        let updatedMessages = userData.messages;
        updatedMessages.push(msg);
        setUserData({...userData, messages: updatedMessages});
    }
    return (
        <div className="mainContainer" data-point="SockJsClient-container">
            <SockJsClient url={wsUrl} topics={['/topic/hotnews']}
                          headers={defaultHeaders}
                          onMessage={onMessageHandler}
                          ref={clientRef} />
            <div data-point="hot-news-container">
                <ol>
                    {userData.messages.map((msg) => (
                        <li>{msg}</li>
                    ))}
                </ol>
            </div>
        </div>
    );
};

export const HotNewsWebsocket = withStyles(baseStyles)(HotNewsWebsocketComponent);