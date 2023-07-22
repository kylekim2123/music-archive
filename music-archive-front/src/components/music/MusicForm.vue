<template>
  <div>
    <h3 class="my-4">음악 정보 {{ option.name }}</h3>

    <div class="form-floating mb-3">
      <input type="text" class="form-control" id="title" placeholder="title" v-model="newMusic.title">
      <label for="title">제목</label>
    </div>
    <div class="form-floating mb-3">
      <input type="url" class="form-control" id="posterUrl" placeholder="poster url" v-model="newMusic.posterUrl">
      <label for="posterUrl">앨범 포스터 URL</label>
    </div>
    <div class="form-floating mb-3">
      <input type="text" class="form-control" id="artistName" placeholder="artist name" v-model="newMusic.artistName">
      <label for="artistName">아티스트</label>
    </div>
    <div class="form-floating mb-3">
      <input type="date" class="form-control" id="releasedDate" placeholder="released date" v-model="newMusic.releasedDate">
      <label for="releasedDate">발매일</label>
    </div>
    <div class="form-floating mb-3">
      <textarea class="form-control" placeholder="description" id="description" name="description" style="height: 200px;" v-model="newMusic.description" required></textarea>
      <label for="description">음악에 대한 설명을 작성하세요.</label>
    </div>

    <div class="d-flex justify-content-end">
      <button @click="submitMusicForm" type="submit" class="btn btn-primary">{{ option.name }}</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "MusicForm",
  props: {
    option: {
      type: Object
    },
    music: {
      type: Object
    }
  },
  data() {
    return {
      newMusic: {
        title: null,
        posterUrl: null,
        artistName: null,
        releasedDate: null,
        description: null,
      }
    }
  },
  methods: {
    submitMusicForm() {
      if (this.option.value === "create") {
        this.$store.dispatch('createMusic', this.newMusic)
      } else if (this.option.value === "update") {
        this.$store.dispatch('updateMusic', {musicId: this.music.id, newMusic: this.newMusic})
      }
    },
  },
  created() {
    if (this.music) {
      this.newMusic.title = this.music.title
      this.newMusic.posterUrl = this.music.posterUrl
      this.newMusic.artistName = this.music.artistName
      this.newMusic.releasedDate = this.music.releasedDate
      this.newMusic.description = this.music.description
    }
  }
}
</script>

<style scoped>

</style>