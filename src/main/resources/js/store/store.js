import Vue from 'vue'
import Vuex from 'vuex'
import messagesApi from '../api/messages'
import commentApi from '../api/comment'
import profileApi from '../api/profile'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        messages,
        profile,
        users,
        ...frontendData
    },
    getters: {
        sortedMessages: state => (state.messages || []).sort((a, b) => -(a.id - b.id)),
        sortedUsers: state => (state.users || []).sort((a, b) => {
            return a.name?.localeCompare(b.name)
        }
)
    },
    mutations: {
        addMessageMutation(state, message) {
            state.messages = [
                ...state.messages,
                message
            ]
        },
        updateMessageMutation(state, message) {
            const updateIndex = state.messages.findIndex(item => item.id === message.id)

            state.messages = [
                ...state.messages.slice(0, updateIndex),
                message,
                ...state.messages.slice(updateIndex + 1)
            ]
        },
        removeMessageMutation(state, message) {
            const deletionIndex = state.messages.findIndex(item => item.id === message.id)

            if (deletionIndex > -1) {
                state.messages = [
                    ...state.messages.slice(0, deletionIndex),
                    ...state.messages.slice(deletionIndex + 1)
                ]
            }
        },
        addCommentMutation(state, comment) {
            const updateIndex = state.messages.findIndex(item => item.id === comment.message.id)
            const message = state.messages[updateIndex]

            if (!message.comments.find(it => it.id === comment.id)) {
                state.messages = [
                    ...state.messages.slice(0, updateIndex),
                    {
                        ...message,
                        comments: [
                            ...message.comments,
                            comment
                        ]
                    },
                    ...state.messages.slice(updateIndex + 1)
                ]
            }
        },
        addMessagePageMutation(state, messages) {
            const targetMessages = state.messages
                .concat(messages)
                .reduce((res, val) => {
                    res[val.id] = val
                    return res
                }, {})

            state.messages = Object.values(targetMessages)
        },
        addUserPageMutation(state, users){
            const targetUsers = state.users
                .concat(users)
                .reduce((res, val) => {
                    res[val.id] = val
                    return res
                }, {})

            state.users = Object.values(targetUsers)
        },
        updateCurrentAndTotalPagesMutation(state, payload){
            if(payload.isMessages === true){
                state.messagesTotalPages = payload.totalPages
                state.messagesCurrentPage = payload.currentPage
            }else {
                state.usersTotalPages = payload.totalPages
                state.usersCurrentPage = payload.currentPage
            }
        },
        // updateTotalPagesMutation(state, totalPages, isMessages) {
        //     if(isMessages === true){
        //         state.messagesTotalPages = totalPages
        //     }else {
        //         state.usersTotalPages = totalPages
        //     }
        // },
        // updateCurrentPageMutation(state, currentPage, isMessages) {
        //     if(isMessages === true){
        //         state.messagesCurrentPage = currentPage
        //     }else {
        //         state.usersCurrentPage = currentPage
        //     }
        // }
    },
    actions: {
        async addMessageAction({commit, state}, message) {
            const result = await messagesApi.add(message)
            const data = await result.json()
            const index = state.messages.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updateMessageMutation', data)
            } else {
                commit('addMessageMutation', data)
            }
        },
        async updateMessageAction({commit}, message) {
            const result = await messagesApi.update(message)
            const data = await result.json()
            commit('updateMessageMutation', data)
        },
        async removeMessageAction({commit}, message) {
            const result = await messagesApi.remove(message.id)

            if (result.ok) {
                commit('removeMessageMutation', message)
            }
        },
        async addCommentAction({commit, state}, comment) {
            const response = await commentApi.add(comment)
            const data = await response.json()
            commit('addCommentMutation', data)
        },
        async loadMessagePageAction({commit, state}) {
            const response = await messagesApi.page(state.messagesCurrentPage + 1)
            const data = await response.json()

            commit('addMessagePageMutation', data.messages)
            commit('updateCurrentAndTotalPagesMutation', {
                isMessages: true,
                totalPages: data.totalPages,
                currentPage: Math.min(data.currentPage, data.totalPages - 1)
            })
            // commit('updateTotalPagesMutation', data.totalPages, true)
            // commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1), true)
        },
        async loadUserPageAction({commit, state}) {
            const response = await profileApi.getAll(state.usersCurrentPage + 1)
            const data = await response.json()

            commit('addUserPageMutation', data.users)
            commit('updateCurrentAndTotalPagesMutation', {
                isMessages: false,
                totalPages: data.totalPages,
                currentPage: Math.min(data.currentPage, data.totalPages - 1)
            })
            // commit('updateTotalPagesMutation', data.totalPages, false)
            // commit('updateCurrentPageMutation', Math.min(data.currentPage, data.totalPages - 1), false)
        }
    }
})