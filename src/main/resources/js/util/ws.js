import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'


let stompClient = null
const handlers = []

export function connect(profile) {
    const socket = new SockJS('/sarafan')
    stompClient = Stomp.over(function(){return socket})
    // stompClient.debug = () => {}
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/queue/activity', message => {
            handlers.forEach(handler => handler(JSON.parse(message.body)))
        })
    })
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("Disconnected")
}

export function sendMessage(message) {
    stompClient.send("/app/changeMessage", {}, JSON.stringify(message))
}