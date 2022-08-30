import axios from "axios";

//const HTTP_PREFIX = window.location.href
const HTTP_PREFIX = "http://localhost:8080/"
const USER_SERVICE_URL = HTTP_PREFIX+"userDetails"
const GET_FRIENDS_URL = HTTP_PREFIX+"getFriends"
const GET_NOT_FRIENDS_URL = HTTP_PREFIX+"getNotFriends"
const UPDATE_FRIENDS_URL = HTTP_PREFIX+"updateFriends"
const REMOVE_FRIENDS_URL = HTTP_PREFIX+"removeFriend"
const SEARCH_USERS_URL = HTTP_PREFIX+"search"

const GET_HOT_NEWS_WS_URL = HTTP_PREFIX+"hot-news-websocket";

export const defaultHeaders = {
    'Acccess-Allow-Credential': 'origin',
    'Access-Control-Allow-Origin': '*',
    'Content-Type': 'Application/json',
    'Access-Control-Allow-Headers': 'Origin, X-Requested-With, privatekey, Content-Type, Accept',
}

const params = {
    mode: 'no-cors',
    headers: defaultHeaders,
    origin: "*",
    methods: "GET,PUT,POST,DELETE, OPTIONS",
    withCredentials: false,
    credentials: 'same-origin',
}

export const getWsHotNewsUrl = () => {
    return GET_HOT_NEWS_WS_URL;
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

export const searchUsers = async (data, rqData, setDataHndler) => {
    try {
        setDataHndler({...data, isFetching: true})
        const searchParams = {
            ...params,
            ...rqData,
            maxCount: 100
        }
        const response = await axios.get(SEARCH_USERS_URL, searchParams);
        setDataHndler({...data, users: response.data, isFetching: false});
    } catch (e) {
        console.log(e);
        setDataHndler({...data, isFetching: false});
    }
};