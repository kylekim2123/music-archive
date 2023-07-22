<template>
  <div>
    <MusicForm :option="option" :music="getMusic" />
  </div>
</template>

<script>
import MusicForm from '@/components/music/MusicForm.vue'

export default {
  name: "MusicUpdateView",
  components: {
    MusicForm,
  },
  props: ["musicId"],
  data() {
    return {
      option: {
        name: "수정",
        value: "update"
      }
    }
  },
  computed: {
    getMusic() {
      return this.$store.state.music
    },
    getPosterUrl() {
      return this.music.posterUrl
    },
  },
  created() {
    this.$store.dispatch('findMusicById', this.musicId)
  }
}
</script>

<style scoped>

</style>