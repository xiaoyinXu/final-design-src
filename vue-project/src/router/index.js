import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../components/Home.vue'
import Welcome from '../components/Welcome.vue'
import Test from '../components/Test.vue'
import Test2 from '../components/Test2.vue'
import Test3 from '../components/Test3.vue'
import Note from '../components/Note.vue'
import Login from '../components/Login.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Home,
    redirect: '/Welcome',
    children: [
      { path: '/welcome', component: Welcome },
      { path: '/note', component: Note }
    ]
  }, {
    path: '/abc',
    component: Welcome
  }, {
    path: '/test',
    component: Test
  }, {
    path: '/test2',
    component: Test2
  }, {
    path: '/test3',
    component: Test3
  }, {
    path: '/login',
    component: Login
  }
]
const router = new VueRouter({
  routes
})
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    // alert(1)
    return next()
  }
  const token = window.sessionStorage.getItem('jwt')
  if (!token) {
    // alert(2)
    return next('/login')
  }
  next()
})
export default router
