import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import qs from 'qs';
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
    spotifyTop20Musics: [],
    musics: [],
    music: {},
    comments: []
  },
  getters: {},
  mutations: {
    'SET_SPOTIFY_TOP_20_MUSICS'(state, spotifyTopMusics) {
      state.spotifyTop20Musics = spotifyTopMusics
    },
    'SET_MUSIC_LIST'(state, {status, message, data}) {
      state.status = status
      state.message = message
      state.musics = data
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
    fetchSpotifyTopMusics({dispatch}) {
      const data = {
        grant_type: 'client_credentials',
        client_id: process.env.VUE_APP_SPOTIFY_CLIENT_ID,
        client_secret: process.env.VUE_APP_SPOTIFY_CLIENT_SECRET,
      };

      axios({
        method: 'post',
        url: 'https://accounts.spotify.com/api/token',
        data: qs.stringify(data),
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      })
      .then(response => {
        dispatch('fetchSpotifyGlobalTop50Tracks', response.data.access_token)
      })
    },
    fetchSpotifyGlobalTop50Tracks({dispatch}, token) {
      axios({
        method: 'get',
        url: 'https://api.spotify.com/v1/playlists/37i9dQZEVXbMDoHDwVN2tF',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`
        }
      })
      .then(response => {
        dispatch('setSpotifyTopMusics', response.data.tracks.items.slice(0, 20))
      })
    },
    setSpotifyTopMusics({commit}, topMusics) {
      const spotifyTopMusics = []

      for (const topMusic of topMusics) {
        const title = topMusic.track.name
        const posterUrl = topMusic.track.album.images[0].url
        const artistName = topMusic.track.artists[0].name
        const releasedDate = topMusic.track.album.release_date

        spotifyTopMusics.push({title, posterUrl, artistName, releasedDate})
      }

      commit('SET_SPOTIFY_TOP_20_MUSICS', spotifyTopMusics)
    },
    findAllMusics({commit}) {
      axios({
        method: 'get',
        url: `${BASE_URL}/musics`,
        params: params
      })
      .then(response => commit('SET_MUSIC_LIST', response.data))
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
    deleteMusic(context, musicId) {
      axios({
        method: 'delete',
        url: `${BASE_URL}/musics/${musicId}`,
        params: params,
      })
      .then(() => router.push({name: "MusicListView"}))
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
