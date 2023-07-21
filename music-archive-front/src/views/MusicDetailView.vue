<template>
  <div class="mt-5">
    <div class="row justify-content-center">
      <div class="col-sm-12 col-md-6 d-flex justify-content-around mb-5">
        <img :src="getPosterUrl" alt="album_poster">
        <div class="d-flex flex-column justify-content-between">
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
    </div>

    <div class="row justify-content-center">
      <div class="col-sm-12 col-md-5">
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
import CommentForm from "@/components/CommentForm.vue";
import CommentItem from "@/components/CommentItem.vue";

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
      this.$store.dispatch('deleteMusic', this.musicId)
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