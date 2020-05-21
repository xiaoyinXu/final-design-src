<template>
    <div>
      <!-- menus -->
      <template v-for="menu in menus">
        <el-submenu :key="menu.id+''" :index="menu.id+''" @contextmenu.native.stop   @contextmenu.native.prevent="addCart($event, menu.id, menu.name, true, false)">
          <template slot="title">
            <i class='el-icon-folder'></i>
            <span>{{menu.name}}</span>
          </template>
          <MyMenu :menus="menu.menus" :notes="menu.notes" @fn='fn2'></MyMenu>
        </el-submenu>
      </template>

      <!-- notes -->
      <template v-for="note in notes">
        <el-menu-item  @click="fn2(note.id)" :key="note.id+''" :index="note.id+''" @contextmenu.native.stop   @contextmenu.native.prevent="addCart($event, note.id, note.name, false, true)" >
          <template slot="title">
            <i class='el-icon-document'></i>
            <span>{{note.name}}</span>
          </template>
        </el-menu-item>
      </template>
        <!-- <template v-for="menu in menuList">
                <el-menu-item v-if='menu.children.length==0' @click='fn2(menu.id)' :index="menu.id+''" :key="menu.id+''" @contextmenu.native.stop   @contextmenu.native.prevent="addCart($event, menu.id, menu.name, false)">
                    <template slot='title'>
                        <i class = "el-icon-menu"></i>
                        <span>{{menu.name}}</span>
                    </template>
                </el-menu-item>

                <el-submenu v-else :index="menu.id+''" :key="menu.id+''"  @contextmenu.native.stop @contextmenu.native.prevent="addCart($event, menu.id, menu.name, true)">
                    <template slot="title">
                      <i class="el-icon-location"></i>
                      <span>{{menu.name}}</span>
                    </template>
                    <MyMenu  @fn='fn2' :menuList="menu.children" ></MyMenu>
                </el-submenu>

        </template>  -->
    </div>
   <!-- <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>导航一</span>
        </template>
          <el-menu-item index="4">
            <template slot='title'>
            <i class="el-icon-location"></i>
            <span slot="title">导航四</span>
            </template>
          </el-menu-item>
    </el-submenu> -->
</template>

<script>
import Bus from '../common/js/bus.js'
export default {
  props: ['menus', 'notes'],
  name: 'MyMenu',
  methods: {
    fn2 (id) {
      // console.log(id)
      // alert(123)
      // alert(id)
      this.$emit('fn', id)
      // 重定向到note
      this.$router.push('/note')
    },
    rightClick2 (e, id, name, isSubmenu, isNote) {
      alert(this.$parent.$el)
      console.log(this.$parent.$el)
      this.$emit('show2', e, id, name, isSubmenu, isNote)
      if (isSubmenu === false) {
        // window.event ? window.event.cancelBubble = true : e.stopPropagation()
      }
    },
    addCart (event, id, name, isSubmenu, isNote) {
      Bus.$emit('getTarget', event, id, name, isSubmenu, isNote)
    }

  },
  created () {
    // window.event ? window.event.cancelBubble = true : e.stopPropagation();
  }
}
</script>

<style>

</style>
