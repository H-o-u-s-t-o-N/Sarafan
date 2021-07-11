import Vue from 'vue'

const profile = Vue.resource('/profile{/id}')

export default {
    get: id => profile.get({id}),
    getAll: page => Vue.http.get('/profile/all', {params: { page }}),
    changeSubscription: channelId => Vue.http.post(`/profile/change-subscription/${channelId}`),
    subscriberList: channelId => Vue.http.get(`/profile/get-subscribers/${channelId}`),
    channelList: subscriberId => Vue.http.get(`/profile/get-channels/${subscriberId}`),
    changeSubscriptionStatus: subscriberId => Vue.http.post(`/profile/change-status/${subscriberId}`)
}