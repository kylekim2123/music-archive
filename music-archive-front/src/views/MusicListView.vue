<template>
  <div>
    <h3 class="my-4">
      사용자가 직접 추가한 음악 목록
    </h3>
    <div class="row row-cols-1 row-cols-md-4 g-4">
      <MusicCard
          v-for="(music, idx) in getAllMusics"
          :key="`music-${idx}`"
          :music="music"
          class="heartbeat"
      />
    </div>
  </div>
</template>

<script>
import MusicCard from '@/components/music/MusicCard.vue'

export default {
  name: 'MusicListView',
  components: {
    MusicCard,
  },
  computed: {
    getAllMusics() {
      return this.$store.state.musics
    }
  },
  created() {
    this.$store.dispatch('findAllMusics')
  },
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