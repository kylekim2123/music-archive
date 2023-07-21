<template>
  <div class="input-group mb-3">
    <div class="form-floating">
      <!-- 한글 입력 시, keyup / keydown 은 두 번 입력되는 이슈가 있어서 keypress 사용 -->
      <input @keypress.enter="createComment" type="text" class="form-control border-dark" id="description" placeholder="description" v-model="description">
      <label for="description">코멘트를 남겨주세요.</label>
    </div>

    <button @click="createComment" class="btn btn-primary">
      <font-awesome-icon icon="fa-solid fa-paper-plane" />
    </button>
  </div>
</template>

<script>
export default {
  name: "CommentForm",
  props: {
    musicId: {
      type: [Number, String]
    }
  },
  data() {
    return {
      description: ""
    }
  },
  methods: {
    createComment() {
      this.$store.dispatch('createComment', {musicId: this.musicId, description: this.description})
      this.description = ""
    }
  }
}
</script>

<style scoped>

</style>