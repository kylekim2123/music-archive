<template>
  <div>
    <MusicForm :option="option" />
  </div>
</template>

<script>
import MusicForm from '@/components/music/MusicForm.vue'

export default {
  name: "MusicCreateView",
  components: {
    MusicForm,
  },
  data() {
    return {
      option: {
        name: "생성",
        value: "create"
      }
    }
  },
}
</script>

<style scoped>

</style>