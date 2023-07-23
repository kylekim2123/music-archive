<template>
  <nav id="nav" class="d-flex align-items-center sticky-top border-bottom border-dark bg-dark p-4">
    <router-link :to="{ name: 'TopMusicListView' }" id="logo" class="text-white me-5">
      <h2>
        <font-awesome-icon icon="fa-solid fa-compact-disc" />
        TheMuzic
      </h2>
    </router-link>
    <div>
      <router-link :to="{ name: 'TopMusicListView' }">오늘의 추천</router-link>
      <span class="mx-3 text-white-50">|</span>
      <router-link :to="{ name: 'MusicListView' }">내 음악 목록</router-link>
      <span class="mx-3 text-white-50">|</span>
      <router-link :to="{ name: 'MusicCreateView' }">새 음악 추가</router-link>
    </div>
  </nav>
</template>

<script>
export default {
  name: "NavBar"
}
</script>

<style scoped>
#nav a {
  color: white;
  font-weight: bold;
  text-decoration: none;
}

#nav a.router-link-exact-active {
  color: greenyellow;
}

#logo {
  font-family: 'Anton', sans-serif;
}
</style>