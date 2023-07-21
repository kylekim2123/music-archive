<template>
  <li class="list-group-item">
    <div class="d-flex justify-content-between my-1">
      <div>
        <strong>{{ comment.description }}</strong>
      </div>
      <div class="d-flex justify-content-end align-items-center">
        <small class="text-secondary">{{ elapsedTime() }}</small>
        <button @click="deleteComment" class="bg-transparent border-0 ms-2">
          <font-awesome-icon icon="fa-solid fa-trash-can" class="text-danger" />
        </button>
      </div>
    </div>
  </li>
</template>

<script>
export default {
  name: "CommentItem",
  props: {
    musicId: {
      type: String
    },
    comment: {
      type: Object
    }
  },
  methods: {
    elapsedTime() {
      const start = new Date(this.comment.createdDatetime)
      const end = Date.now();
      const diff = (end - start) / 1000;

      const times = [
        {name: '년', milliSeconds: 60 * 60 * 24 * 365},
        {name: '개월', milliSeconds: 60 * 60 * 24 * 30},
        {name: '일', milliSeconds: 60 * 60 * 24},
        {name: '시간', milliSeconds: 60 * 60},
        {name: '분', milliSeconds: 60},
      ];

      for (const value of times) {
        const betweenTime = Math.floor(diff / value.milliSeconds);

        if (betweenTime > 0) {
          return `${betweenTime}${value.name} 전`;
        }
      }

      return '방금 전';
    },
    deleteComment() {
      this.$store.dispatch('deleteComment', {musicId: this.musicId, commentId: this.comment.id})
    }
  }
}
</script>

<style scoped>

</style>