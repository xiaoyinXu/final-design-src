<template>
<div>
  <form action='http://backend:8080/login' method='post'>
    <input type="text" name='username' id='username' />
    <input type="password" name='password' id='password' />
    <input type='submit' />
  </form>

</div>
</template>

<script>
export default {
  data () {
    return {
      data: [1, 2, 3],
      menuVisible: true,
      input1: '123',
      input2: '11',
      input3: '55',
      input4: '66'
    }
  },
  components: {},
  methods: {
    rightClick (e, id, level) {
      this.rightClickData = { id: id, level: level }
      this.$refs.ctxshow.showMenu(e)
    },
    hideMenu () {
      this.$refs.ctxshow.hideMenu() // 隐藏菜单
    },
    mouseClickDel (data) {
      this.hideMenu()
      console.log('删除')
    },
    mouseClickOpen (data) {
      this.hideMenu()
      console.log('打开')
    },
    show (data) {
      console.log('显示菜单')
    },
    hide (data) {
      console.log('隐藏菜单')
    }

  }
}
</script>

<style lang="less" scoped>
.menu ul {
  margin: 0px;
  padding: 0px;
  text-align: center;
  list-style-type: none;
}

.menu ul li {
  padding: 3px 0px;
  font-size: 15px;
}

.menu ul li:hover {
  background: #e1dddd;
}

.menu ul li a:link {
  color: #000;
  text-decoration: none;
}
</style>
