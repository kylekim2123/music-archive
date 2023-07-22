<template>
  <div class="col">
    <div class="card h-100">
      <img :src="getPosterUrl" class="card-img-top" alt="album-poster">

      <div class="card-body">
        <h4 class="card-title fw-bold">{{ music.title }}</h4>
      </div>

      <div class="card-footer">
        <small>아티스트 : {{ music.artistName }}</small>
        <br>
        <small>발매일 : {{ music.releasedDate }}</small>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TopMusicCard',
  props: {
    music: {
      type: Object,
    }
  },
  computed: {
    getPosterUrl() {
      return this.music.posterUrl
    },
  }
}
</script>

<style>

</style>