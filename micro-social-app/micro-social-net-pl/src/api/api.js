import axios from "axios";

//const PREFIX = "http://localhost:8080/"
const PREFIX = window.location.href
const USER_SERVICE_URL = PREFIX+"userDetails"
const GET_FRIENDS_URL = PREFIX+"getFriends"
const GET_NOT_FRIENDS_URL = PREFIX+"getNotFriends"
const UPDATE_FRIENDS_URL = PREFIX+"updateFriends"
const REMOVE_FRIENDS_URL = PREFIX+"removeFriend"

const headers = {
    'Acccess-Allow-Credential': 'origin',
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'Application/json',
    'Access-Control-Allow-Headers': 'Origin, X-Requested-With, privatekey, Content-Type, Accept',
}

const params = {
    mode: 'no-cors',
    headers: headers,
    origin: "*",
    methods: "GET,PUT,POST,DELETE, OPTIONS",
    withCredentials: false,
    credentials: 'same-origin',
}

export const fetchUserData = async (data, setDataHndler) => {
    try {
        setDataHndler({...data, isFetching: true})
        const response = await axios.get(USER_SERVICE_URL, params);
        setDataHndler({...data, currentUser: response.data, isFetching: false});
    } catch (e) {
        console.log(e);
        setDataHndler({...data, isFetching: false});
    }
};

export const fetchFriends = async (data, setDataHndler) => {
    try {
        setDataHndler({...data, isFetching: true})
        const response = await axios.get(GET_FRIENDS_URL, params);
        setDataHndler({...data, users: response.data, isFetching: false});
    } catch (e) {
        console.log(e);
        setDataHndler({...data, isFetching: false});
    }
};

export const fetchNotFriends = async (data, setDataHndler) => {
    try {
        setDataHndler({...data, isFetching: true})
        const response = await axios.get(GET_NOT_FRIENDS_URL, params);
        setDataHndler({...data, names: response.data, isFetching: false});
    } catch (e) {
        console.log(e);
        setDataHndler({...data, isFetching: false});
    }
};

export const saveFriends = async (rqData, callback) => {
    try {
        const response = await axios.post(UPDATE_FRIENDS_URL, rqData, params);
        console.log(response);
        callback();
    } catch (e) {
        console.log(e);
    }
};

export const removeFriend = async (rqData, callback) => {
    try {
        const response = await axios.post(REMOVE_FRIENDS_URL, rqData, params);
        console.log(response);
        callback();
    } catch (e) {
        console.log(e);
    }
};