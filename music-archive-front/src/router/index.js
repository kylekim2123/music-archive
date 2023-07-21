import Vue from 'vue'
import VueRouter from 'vue-router'
import MusicListView from '@/views/MusicListView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: { name: 'TopMusicListView' }
  },
  {
    path: '/top-20-musics',
    name: 'TopMusicListView',
    component: () => import('@/views/TopMusicListView.vue'),
  },
  {
    path: '/musics',
    name: 'MusicListView',
    component: MusicListView
  },
  {
    path: '/musics/create',
    name: 'MusicCreateView',
    component: () => import('@/views/MusicCreateView.vue'),
  },
  {
    path: '/musics/:musicId',
    name: 'MusicDetailView',
    component: () => import('@/views/MusicDetailView.vue'),
    props: true
  },
  {
    path: '/musics/:musicId/update',
    name: 'MusicUpdateView',
    component: () => import('@/views/MusicUpdateView.vue'),
    props: true
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
