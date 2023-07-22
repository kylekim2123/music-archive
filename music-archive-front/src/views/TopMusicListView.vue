<template>
  <div>
    <h3 class="my-4">오늘의 Spotify Top 20</h3>
    <div class="row row-cols-1 row-cols-md-4 g-4">
      <TopMusicCard
          v-for="(music, idx) in getSpotifyTop20Musics"
          :key="`top-music-${idx}`"
          :music="music"
          class="heartbeat"
      />
    </div>
  </div>
</template>

<script>
import TopMusicCard from "@/components/music/TopMusicCard.vue";

export default {
  name: "TopMusicList",
  components: {
    TopMusicCard
  },
  computed: {
    getSpotifyTop20Musics() {
      return this.$store.state.spotifyTop20Musics
    }
  },
  created() {
    this.$store.dispatch('fetchSpotifyTopMusics')
  }
}
</script>

<style>
.heartbeat:hover {
  animation: heartbeat 0.5s linear infinite both;
  cursor: pointer;
}

@keyframes heartbeat {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(0.9);
  }
  100% {
    transform: scale(1);
  }
}
</style>