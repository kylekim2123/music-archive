<template>
  <router-link :to="{ name: 'MusicDetailView', params: {musicId: getMusicId} }" class="col text-decoration-none">
    <div class="card h-100">
      <img :src="getPosterUrl" class="card-img-top" alt="album-poster">

      <div class="card-body">
        <h4 class="card-title fw-bold">{{ music.title }}</h4>
        <hr>
        <p class="card-text">
          <strong>{{ music.description }}</strong>
        </p>
      </div>

      <div class="card-footer">
        <small>아티스트 : {{ music.artistName }}</small>
        <br>
        <small>발매일 : {{ music.releasedDate }}</small>
      </div>
    </div>
  </router-link>
</template>

<script>
export default {
  name: 'MusicCard',
  props: {
    music: {
      type: Object,
    }
  },
  computed: {
    getMusicId() {
      return this.music.id
    },
    getPosterUrl() {
      return this.music.posterUrl
    },
  }
}
</script>

<style>

</style>