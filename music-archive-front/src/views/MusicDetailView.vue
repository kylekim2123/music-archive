<template>
  <div class="d-flex justify-content-center">
    <div>
      <div class="d-flex mb-5">
        <img :src="getPosterUrl" alt="album_poster" style="width: 200px; height: 200px; border: 1px solid black;">
        <div class="d-flex flex-column justify-content-between ms-5">
          <h3>{{ getMusic.title }}</h3>
          <p>
            <strong>{{ getMusic.description }}</strong>
          </p>
          <p>아티스트: {{ getMusic.artistName }}</p>
          <p>발매일: {{ getMusic.releasedDate }}</p>
          <div class="d-flex">
            <router-link :to="{ name: 'MusicUpdateView' }" class="btn btn-outline-success btn-sm me-2">수정</router-link>
            <button @click="deleteMusic" class="btn btn-outline-danger btn-sm">삭제</button>
          </div>
        </div>
      </div>

      <div>
        <CommentForm :musicId="musicId" />
        <ul class="list-group list-group-flush">
          <CommentItem
              v-for="(comment, idx) in getAllComments"
              :key="`comment-${idx}`"
              :musicId="musicId"
              :comment="comment"
          />
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import CommentForm from "@/components/comment/CommentForm.vue";
import CommentItem from "@/components/comment/CommentItem.vue";

export default {
  name: "MusicDetailView",
  components: {
    CommentForm,
    CommentItem,
  },
  props: ["musicId"],
  computed: {
    getMusic() {
      return this.$store.state.music
    },
    getPosterUrl() {
      return this.getMusic.posterUrl
    },
    getAllComments() {
      return [...this.$store.state.comments].reverse();
    }
  },
  methods: {
    deleteMusic() {
      this.$store.dispatch('deleteMusic', {musicId: this.musicId, isSpotify: this.getMusic.isSpotify})
    }
  },
  created() {
    this.$store.dispatch('findMusicById', this.musicId)
    this.$store.dispatch('findCommentsInMusic', this.musicId)
  }
}
</script>

<style scoped>

</style>