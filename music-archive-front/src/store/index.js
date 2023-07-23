import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import router from "@/router";

import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

const BASE_URL = 'http://localhost:8080/api/v1'

const params = {
  language: 'ko-kr',
  region: 'kr',
}

export default new Vuex.Store({
  state: {
    status: "",
    message: "",
    topMusics: [],
    userMusics: [],
    music: {},
    comments: []
  },
  getters: {},
  mutations: {
    'SET_TOP_MUSIC_LIST'(state, {status, message, data}) {
      state.status = status
      state.message = message
      state.topMusics = data
    },
    'SET_USER_MUSIC_LIST'(state, {status, message, data}) {
      state.status = status
      state.message = message
      state.userMusics = data
    },
    'SET_MUSIC'(state, {status, message, data}) {
      state.status = status
      state.message = message
      state.music = data
    },
    'CREATE_COMMENT'(state, {status, message, data}) {
      state.status = status
      state.message = message
      state.comments.push(data)
    },
    'SET_COMMENT_LIST'(state, {status, message, data}) {
      state.status = status
      state.message = message
      state.comments = data
    },
  },
  actions: {
    findTopMusics({commit}) {
      axios({
        method: 'get',
        url: `${BASE_URL}/musics/top`,
        params: params
      })
      .then(response => commit('SET_TOP_MUSIC_LIST', response.data))
    },
    findUserMusics({commit}) {
      axios({
        method: 'get',
        url: `${BASE_URL}/musics/custom`,
        params: params
      })
      .then(response => commit('SET_USER_MUSIC_LIST', response.data))
    },
    findMusicById({commit}, musicId) {
      axios({
        method: 'get',
        url: `${BASE_URL}/musics/${musicId}`,
        params: params
      })
      .then(response => commit('SET_MUSIC', response.data))
    },
    createMusic({state, commit}, newMusic) {
      axios({
        method: 'post',
        url: `${BASE_URL}/musics`,
        params: params,
        data: newMusic
      })
      .then(response => commit('SET_MUSIC', response.data))
      .then(() => router.push(
          {name: "MusicDetailView", params: {musicId: state.music.id}}))
    },
    updateMusic({commit}, {musicId, newMusic}) {
      axios({
        method: 'put',
        url: `${BASE_URL}/musics/${musicId}`,
        params: params,
        data: newMusic
      })
      .then(response => commit('SET_MUSIC', response.data))
      .then(() => router.push(
          {name: "MusicDetailView", params: {musicId: musicId}}))
    },
    deleteMusic(context, {musicId, isSpotify}) {
      axios({
        method: 'delete',
        url: `${BASE_URL}/musics/${musicId}`,
        params: params,
      })
      .then(() => {
        if (isSpotify) {
          router.push({name: "TopMusicListView"})
        } else {
          router.push({name: "MusicListView"})
        }
      })
    },
    createComment({commit}, {musicId, description}) {
      axios({
        method: 'post',
        url: `${BASE_URL}/musics/${musicId}/comments`,
        params: params,
        data: {description: description}
      })
      .then(response => commit('CREATE_COMMENT', response.data))
    },
    findCommentsInMusic({commit}, musicId) {
      axios({
        method: 'get',
        url: `${BASE_URL}/musics/${musicId}/comments`,
        params: params
      })
      .then(response => commit('SET_COMMENT_LIST', response.data))
    },
    deleteComment({dispatch}, {musicId, commentId}) {
      axios({
        method: 'delete',
        url: `${BASE_URL}/musics/${musicId}/comments/${commentId}`,
        params: params,
      })
      .then(() => dispatch('findCommentsInMusic', musicId))
    },
  },
  modules: {},
  plugins: [createPersistedState()],
})
