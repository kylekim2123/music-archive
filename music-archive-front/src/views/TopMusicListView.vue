<template>
  <div>
    <h3 class="my-3 p-2 bg-info-subtle">
      Today's Spotify Top 50
    </h3>
    <div class="row row-cols-1 row-cols-md-4 g-4">
      <MusicCard
          v-for="(music, idx) in getTopMusics"
          :key="`top-music-${idx}`"
          :music="music"
          class="heartbeat"
      />
    </div>
  </div>
</template>

<script>
import MusicCard from "@/components/music/MusicCard.vue";

export default {
  name: "TopMusicList",
  components: {
    MusicCard
  },
  computed: {
    getTopMusics() {
      return this.$store.state.topMusics
    }
  },
  created() {
    this.$store.dispatch('findTopMusics')
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