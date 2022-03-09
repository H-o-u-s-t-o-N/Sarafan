import Vue from 'vue'
import VueRouter from 'vue-router'
import MessagesList from 'pages/MessageList.vue'
import Auth from 'pages/Auth.vue'
import Users from 'pages/Users.vue'
import Profile from 'pages/Profile.vue'
import Subscriptions from 'pages/Subscriptions.vue'
import Channels from "pages/Channels.vue"
import Chats from "pages/Chats.vue"

Vue.use(VueRouter)

const routes = [
    { path: '/', component: MessagesList },
    { path: '/auth', component: Auth },
    { path: '/users', component: Users },
    { path: '/user/:id?', component: Profile },
    { path: '/subscriptions/:id', component: Subscriptions },
    { path: '/channels/:id', component: Channels },
    { path: '/chats', component: Chats },
    { path: '*', component: MessagesList },
]

export default new VueRouter({
    mode: 'history',
    routes
})