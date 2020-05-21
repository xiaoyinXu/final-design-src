/ eslint-disable no-new /
<template>
  <el-container class='home-container' id="home">
    <el-header>
      <div>
        <!-- <img src="../assets/img/photo3.jpg" alt=""> -->
        <span>徐晓寅的个人笔记本</span>
      </div>
      <el-button type="info" @click="logout">退出</el-button>
      <!-- <el-button type="info" @click="test">测试</el-button> -->
    </el-header>
    <button @click="showMsg">test</button>
    <el-container direction="vertical">
      <el-aside class="side1" width="30px">
        <el-row>
          <el-button :type="colorType1" icon="el-icon-edit" circle size="mini" @click="toggleMenu(1)" class='left-button'>
          </el-button>
        </el-row>
        <el-row>
          <el-button :type="colorType2" icon="el-icon-search" circle size="mini" @click="toggleMenu(2)" class="left-button"></el-button>
        </el-row>
        <el-row>
          <el-button :type="colorType3" icon="el-icon-bell" circle size="mini" @click="toggleStatistics" class="left-button"></el-button>
        </el-row>
      </el-aside>
      <!-- 这里是一级菜单 -->
      <el-aside class="side2" width="200px" v-show="showMenu&&contentSwitch==1" @contextmenu.native.prevent>
        <el-container>
          <el-main class="my-main-container">
            <el-menu class="el-menu-vertical-demo" background-color="#333744" text-color="#fff"
              active-text-color="#409eff" :default-openeds="defaultOpeneds" :default-active="defaultActive"
              @select="clickFirstMenu">
              <el-menu-item v-for="item in firstMenus" :key="item.id+''" :index="item.id+''"
                @contextmenu.native.prevent="rightClick($event, item.id, item.name, false, false)">
                <template slot='title'>
                  <i class="el-icon-folder"></i>
                  <span>{{item.name}}</span>
                </template>
              </el-menu-item>
            </el-menu>
          </el-main>
          <footer class="my-footer">
            <el-button type="info" @click="addMainMenu" class="my-button">添加目录</el-button>
            <!-- <el-button type="info">修改</el-button> -->
          </footer>
        </el-container>
      </el-aside>
      <!-- 这里是二级及多级菜单, 当一级菜单点击后, 显示其一级菜单的子菜单 -->
      <el-aside class="side3" width="200px" v-show="showMenu&&contentSwitch==1" @contextmenu.native.prevent>
        <el-menu class="el-menu-vertical-demo" background-color="#333744" text-color="#fff" active-text-color="#409eff"
          :collapse="isCollapse">
          <MyMenu v-on:fn='fn' :menus="submenus" :notes="notes"></MyMenu>
        </el-menu>
        <footer class="my-footer2">
          <el-button-group>
            <el-button type="info" @click="addMenu" class="my-button">添加目录</el-button>
            <el-button type="info" @click="addNote" class="my-button">添加笔记</el-button>
          </el-button-group>
        </footer>
      </el-aside>
      <el-aside class="side4" v-show="showMenu&&contentSwitch==2" width="400px">
        <div class="block search">
          <el-row>
            <el-col :span=16>
 <el-autocomplete class="inline-input" v-model="searchInput" :fetch-suggestions="querySearch"
            placeholder="请输入搜索内容" style="width:100%" @keyup.native.enter="addSearch"></el-autocomplete>
           
            </el-col>
            <el-col :span=2>
              
            </el-col>
            <el-col :span=6>
               <el-button @click="search" class="submit-button" style = "margin-left:20px">搜索</el-button>
            </el-col>
           

          </el-row>

          <div class="block">
            <span class="demonstration">选择时间段</span>
            <el-date-picker v-model="date" type="daterange" align="right" unlink-panels range-separator="至"
              start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions">
            </el-date-picker>
          </div>
          
          <el-divider style="background-color:blue;color:blue" content-position="left">以下为搜寻结果</el-divider>
          <!-- <el-menu class="el-menu-vertical-demo"
      background-color="#333744"
      text-color="#fff"
      active-text-color="#409eff"
      :default-openeds="defaultOpeneds"
      :default-active="defaultActive"
      @select="clickFirstMenu"
      >
        <el-menu-item v-for="item in firstMenus" :key="item.id+''" :index="item.id+''" @contextmenu.native.prevent="rightClick($event, item.id, item.name, false)"
        >
                    <template slot='title'>
                        <i class = "el-icon-folder"></i>
                        <span>{{item.name}}</span>
                    </template>
        </el-menu-item>
      </el-menu> -->
          <!-- <el-date-picker
      v-model="value1"
      type="date"
      placeholder="选择日期">
    </el-date-picker>

    <div class="container">
    </div> -->

        </div>
        <!-- 搜索结果 日记列表 -->
        <el-menu background-color="#333744" text-color="#fff" active-text-color="#409eff"
          :default-openeds="defaultOpeneds" :default-active="defaultActive">
          <el-menu-item v-for="note2 in matchedNotes" :key="note2.id+''" :index="note2.id+''" @click="fn(note2.id)">
            <template slot='title'>
              <i class="el-icon-document"></i>
              <span>{{note2.name}}</span>
            </template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main class="my-main-content" :style="my_note_style">
        <router-view :noteId="noteId" :style="my_note_style" />
      </el-main>

    </el-container>
    <!-- utils -->
    <!-- 右键菜单 -->
    <e-vue-contextmenu ref="ctxshow" id="contextStyle" class="menu" @ctx-show="show" @ctx-hide="hide">
      <ul>
        <li @click="mouseClickDel()">删除</li>
        <li @click="mouseClickOpen()">修改</li>
        <li @click="mouseClickAddMenu()" v-if="isSubmenu">添加目录</li>
        <li @click="mouseClickAddNote()" v-if="isSubmenu">添加笔记</li>
      </ul>
    </e-vue-contextmenu>
    <Test3 :drawer='showStatistics' :size2='statisticsSize' @toggleStatistics='toggleStatistics' />
  </el-container>
</template>

<script>
  import MyMenu from '../components/MyMenu'
  import Bus from '../common/js/bus.js'
  import Test3 from '../components/Test3'
  export default {
    data() {
      return {
        colorType1: 'primary',
        colorType2: '',
        colorType3: '',
        matchedNotes: [],
        date: '',
        searchInput: '',
        defaultActive: '',
        defaultOpeneds: [],
        menuList: [],
        name: 'Cookie',
        activePath: '123',
        isCollapse: false,
        noteId: '-1',
        showMenu: true,
        firstMenu: [], // deleted
        contentSwitch: 1, // 1 菜单  2 搜索,
        value1: '',
        my_note_style: {
          left: '430px'
        },
        currentMenuId: '',
        currentMenuName: '',
        isSubmenu: false,
        isNote: false,
        idToMenuMap: {},
        idToNoteMap: {},
        menuListMenu: null,
        submenus: [], // 二级菜单
        notes: [], // 一级菜单下的日志,
        firstMenus: [],
        currentFirstMenu: null,
        restaurants: [{
          value: '设计模式',
          address: 'Yichang'
        }, {
          value: '数据库',
          address: 'Beijing'
        }],
        showStatistics: false,
        statisticsSize: '80%'
      }
    },
    methods: {
      toggleStatistics() {
        // 设置颜色

        // 长度
        this.statisticsSize = (document.body.clientWidth - 40) + 'px'
        this.showStatistics = !this.showStatistics
      },
      async search() {
        // this.searchInput
        // this.date[0]  this.date[1]
        // url /notes_details
        var data = {
          keyword: this.searchInput,
          from: this.date ? this.format(this.date[0]) : '',
          to: this.date ? this.format(this.date[1]) : ''
        }
        var url = `/notes_details?keyword=${data.keyword}&from=${data.from}&to=${data.to}`
        const {
          data: res
        } = await this.$http.get(url)
        console.log(res.data)
        // 更新matchedNotes
        this.matchedNotes = res.data
      },
      test() {
        alert(this.currentMenuId)
      },
      logout() {
        window.sessionStorage.clear()
        this.$router.push('/login')
      },
      addSearch() {
        this.restaurants.unshift({
          value: this.searchInput
        })
      },
      async getMenuList() {
        const {
          data: res
        } = await this.$http.get('/data')
        this.menuList = res.data
      },
      fn(newId) {
        this.noteId = newId
        // alert(this.noteId)
      },
      showMsg() {
        console.log(this.date)
      },
      toggleMenu(content) {
        // 设置颜色
        if (content === 1) {
          this.colorType1 = "primary"
          this.colorType2 = ''
          this.colorType3 = ''
        } else if (content === 2) {
          this.colorType1 = ""
          this.colorType2 = 'primary'
          this.colorType3 = ''          
        } else if (content === 3) {
          this.colorType1 = ""
          this.colorType2 = ''
          this.colorType3 = 'primary'
        }
        // this.showMenu = !this.showMenu
        if (this.contentSwitch !== content) {
          this.showMenu = true
        } else {
          this.showMenu = !this.showMenu
        }
        this.contentSwitch = content
        if (this.showMenu === true) {
          this.my_note_style.left = '430px'
        } else {
          this.my_note_style.left = '30px'
        }
      },
      async getFirstMenu() {
        const {
          data: res
        } = await this.$http.get('/menus')
        this.firstMenus = res.data
        // firstMenu数组里所有的item, 让这个item.id指向这个数组
        for (var i = 0; i < this.firstMenus.length; i++) {
          this.idToMenuMap[this.firstMenus[i].id] = {
            data: this.firstMenus[i],
            parent: null
          }
        }
      },
      clickFirstMenu(id) { // id 为一级菜单的id
        // index为一级菜单的id, 我们要做的是,找到firstMenu里 id==id的一项,然后将其children属性传递给menuList
        for (var i = 0; i < this.firstMenus.length; i++) {
          if (this.firstMenus[i].id + '' === id + '') { // 注意整数和字符串的比较
            this.submenus = this.firstMenus[i].menus
            this.notes = this.firstMenus[i].notes
            console.log(this.notes)
            this.currentFirstMenu = this.firstMenus[i]
            this.fillIdToMenuMap(this.currentFirstMenu)
            // this.menuList = this.firstMenu[i].children// menuList 为所有二级菜单, 是一个数组
            // this.menuListMenu = this.firstMenu[i]
            // this.fillIdToMenuMap(this.menuList, this.firstMenu[i])
            break
          }
        }
      },
      fillIdToMenuMap(menu) {
        // 让menus的menus和notes被记载到map中
        if (menu) {
          for (var i = 0; i < menu.menus.length; i++) {
            var submenu = menu.menus[i]
            this.idToMenuMap[submenu.id] = {
              data: submenu,
              parent: menu
            }
            this.fillIdToMenuMap(submenu)
          }
          for (i = 0; i < menu.notes.length; i++) {
            var note = menu.notes[i]
            // this.idToMenuMap[note.id] = { data: note, parent: menu }
            this.idToNoteMap[note.id] = {
              data: note,
              parent: menu
            }
          }
        }

        // menus 是一个数组
        // 遍历数组, 将menus[i].id = menus[i],  并且递归menus[i].children
        // if (!menus || menus.length <= 0) {
        //   return
        // }
        // for (var i = 0; i < menus.length; i++) {
        //   this.idToMenuMap[menus[i].id] = { data: menus[i], parent: parent2 }
        //   this.fillIdToMenuMap(menus[i].children, menus[i])
        // }
      },
      async addMainMenu() {
        // var newMenu = { id: null, name: 'untitled', menus: [], notes: [] }
        // var postData = { menu: newMenu, parent: null }
        var newMenu = {
          name: 'untitled',
          parentId: 1
        }
        const {
          data: res
        } = await this.$http.post('/menus', newMenu)
        console.log(res)
        if (res.meta.status === 201) {
          newMenu = res.data // 新增了id
          this.firstMenus.push(newMenu)
          this.idToMenuMap[newMenu.id] = {
            data: newMenu,
            parent: null
          }
          this.$message({
            message: '添加成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: '添加失败',
            type: 'failed'
          })
        }
        // var newMenu = { id: null, name: 'untitled', path: null, children: [] }
        // const { data } = await this.$http.post('/note/mainMenu', newMenu)
        // var status = data.meta.status
        // var menu = data.data

        // if (status === 201) {
        //   // console.log(this.firstMenu)
        //   // console.log(menu)
        //   // 新建菜单到firstMenu
        //   this.firstMenu.push(menu)
        //   this.idToMenuMap[menu.id] = { data: menu, parent: null }
        //   // console.log(this.menuList)
        //   // alert(this.firstMenu.length)
        // }
      },
      rightClick(e, id, name, isSubmenu, isNote) {
        // this.rightClickData = { id: id, level: level }
        this.isSubmenu = isSubmenu
        this.isNote = isNote
        this.currentMenuId = id
        this.currentMenuName = name
        this.$refs.ctxshow.showMenu(e)
      },
      hideMenu() {
        this.$refs.ctxshow.hideMenu() // 隐藏菜单
      },
      mouseClickDel(data) {
        this.hideMenu()
        console.log('删除')
        // 弹出确认框
        // 如果确认给服务器发送delete请求,返回码正确后, 从列表中删除
        // here is the thing, 我怎么知道从哪个列表中删除?,  我给每一个列表做一个哈希表...
        // {"307":[{},{}]},
        // 先考虑一级菜单,  只需要遍历firstMenu就好了
        this.openDeleteDialog()
      },
      mouseClickOpen(data) {
        this.hideMenu()
        // 弹出修改框
        this.openModificationWindow()
      },
      mouseClickAddMenu() {
        this.hideMenu()
        this.addMenuOfParent(this.currentMenuId)
      },
      mouseClickAddNote() {
        this.hideMenu()
        this.addNoteOfParent(this.currentMenuId)
      },
      show(data) {
        console.log('显示菜单')
      },
      hide(data) {
        console.log('隐藏菜单')
      },
      openModificationWindow(id) {
        this.$prompt('请输入新名', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          // inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
          // inputErrorMessage: '邮箱格式不正确',
          inputValue: this.currentMenuName
        }).then(async ({
          value
        }) => {
          if (this.isNote) {
            var obj = this.getNoteObjById(this.currentMenuId)
          } else {
            obj = this.getMenuObjById(this.currentMenuId)
          }
          // 发送post请求 修改名字
          // var oldMenu = this.getMenuObjById(this.currentMenuId)
          var newMenu = JSON.parse(JSON.stringify(obj)) // 注意 这里是副本 深拷贝
          newMenu.name = value
          // 判断是日志还是notes
          if (this.isNote) {
            const {
              data: res
            } = await this.$http.put(`/notes/${this.currentMenuId}`, newMenu)
            if (res.meta.status === 200) {
              // 如果状态码正确  修改当前menuId的name
              this.modifyNoteNameById(this.currentMenuId, value)
              this.$message({
                type: 'success',
                message: '新名是: ' + value
              })
            }
          } else {
            const {
              data: res
            } = await this.$http.put(`/menus/${this.currentMenuId}`, newMenu)
            if (res.meta.status === 200) {
              // alert(this.currentMenuId)
              // 如果状态码正确  修改当前menuId的name
              this.modifyMenuNameById(this.currentMenuId, value)
              this.$message({
                type: 'success',
                message: '新名是: ' + value
              })
            }
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          })
        })
      },
      openSubMenu(index) {},
      openDeleteDialog() {
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(async () => {
          // # 发送post请求
          if (this.isNote) {
            var obj = this.getNoteObjById(this.currentMenuId)
            const {
              data: res
            } = await this.$http.delete(`/notes/${obj.id}`) // wanr  menuId可以改成id
            if (res.meta.status === 200) {
              this.deleteNoteById(this.currentMenuId)
              // 删除成功
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }
          } else {
            obj = this.getMenuObjById(this.currentMenuId)
            const {
              data: res
            } = await this.$http.delete(`/menus/${obj.id}`) // wanr  menuId可以改成id
            if (res.meta.status === 200) {
              this.deleteMenuById(this.currentMenuId)
              // 删除成功
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }
          }
          // if (obj.menus) { // 如果有menus属性, 则说明是目录
          //   const { data: res } = await this.$http.delete(`/menus/${this.currentMenuId}`) // wanr  menuId可以改成id
          //   if (res.meta.status === 200) {
          //     this.deleteMenuById(this.currentMenuId)
          //     // 删除成功
          //     this.$message({
          //       type: 'success',
          //       message: '删除成功!'
          //     })
          //   }
          // } else {
          //   const { data: res } = await this.$http.delete(`/notes/${this.currentMenuId}`) // warn 还需要判断是note 还是 menu
          //   if (res.meta.status === 200) {
          //     this.deleteNoteById(this.currentMenuId)
          //     // 删除成功
          //     this.$message({
          //       type: 'success',
          //       message: '删除成功!'
          //     })
          //   }
          // }

          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      deleteMenuById(id) {
        var menu = this.idToMenuMap[id].data
        var parent = this.idToMenuMap[id].parent
        var children = null
        if (parent === null) {
          children = this.firstMenus
        } else {
          children = parent.menus
        }
        for (var i = 0; i < children.length; i++) {
          if (children[i].id === menu.id || children[i].id + '' === menu.id) {
            children.splice(i, 1)
            break
          }
        }
      },
      deleteNoteById(id) {
        // var note = this.idToMenuMap[id].data
        // var parent = this.idToMenuMap[id].parent
        var note = this.idToNoteMap[id].data
        var parent = this.idToNoteMap[id].parent

        var children = parent.notes
        for (var i = 0; i < children.length; i++) {
          if (children[i].id === note.id || children[i].id + '' === note.id) {
            children.splice(i, 1)
            break
          }
        }
      },
      modifyMenuNameById(id, value) {
        var menu = this.getMenuObjById(id)
        menu.name = value
      },
      modifyNoteNameById(id, value) {
        var note = this.getNoteObjById(id)
        note.name = value
      },
      getMenuObjById(id) {
        console.log(this.idToMenuMap)
        return this.idToMenuMap[id].data
      },
      getNoteObjById(id) {
        return this.idToNoteMap[id].data
      },
      async addMenu() {
        if (!this.currentFirstMenu) {
          this.$message('请选选中一个目录')
          return
        }
        // var newMenu = { id: null, name: 'untitled', menus: [], notes: [] }
        // var postData = { menu: newMenu, parent: this.currentFirstMenu }

        // 通过menuId 找到menu
        // var menu = this.getMenuObjById(this.currentMenuId)
        var newMenu = {
          name: 'untitled',
          parentId: this.currentFirstMenu.id
        }
        const {
          data: res
        } = await this.$http.post('/menus', newMenu)
        if (res.meta.status === 201) {
          newMenu = res.data
          this.submenus.push(newMenu)
          this.idToMenuMap[newMenu.id] = {
            data: newMenu,
            parent: this.currentFirstMenu
          }
          this.$message({
            message: '添加成功',
            type: 'success'
          })
        }

        // var newMenu = { id: null, name: 'untitled', path: null, children: [] }
        // const { data } = await this.$http.post('/note/mainMenu', newMenu)
        // var status = data.meta.status
        // var menu = data.data
        // menu.children.push({ id: '20000', name: 'untitled', path: null, children: [] })
        // if (status === 201) {
        //   // console.log(this.firstMenu)
        //   // console.log(menu)
        //   // 新建菜单到firstMenu
        //   this.menuList.push(menu)
        //   this.idToMenuMap[menu.id] = { data: menu, parent: this.menuListMenu }

        //   // console.log(this.menuList)
        //   // alert(this.firstMenu.length)
        // }
      },
      async addNote() {
        if (!this.currentFirstMenu) {
          this.$message('请选选中一个目录')
          return
        }
        // var newNote = { id: null, name: 'untitled' }
        // var postData = { note: newNote, parent: this.currentFirstMenu }
        var date = new Date()
        date = this.format(date)
        // console.log(this.idToMenuMap)
        // console.log(this.currentFirstMenu.id)
        var menu = this.getMenuObjById(this.currentFirstMenu.id)
        console.log(menu)
        var newNote = {
          name: 'untitled',
          menuId: this.currentFirstMenu.id,
          createDate: date,
          menu: {
            userId: menu.userId
          }
        }
        const {
          data: res
        } = await this.$http.post('/notes_details', newNote)
        if (res.meta.status === 201) {
          newNote = res.data
          this.notes.push(newNote)
          // this.idToMenuMap[newNote.id] = { data: newNote, parent: this.currentFirstMenu }
          this.idToNoteMap[newNote.id] = {
            data: newNote,
            parent: this.currentFirstMenu
          }

          this.$message({
            message: '添加成功',
            type: 'success'
          })
        }
        // var newNote = { id: null, name: 'untitled', path: null, children: [], isNote: true }
        // const { data } = await this.$http.post('/note', newNote)
        // var status = data.meta.status
        // var noteResult = data.data
        // if (status === 201) {
        //   // 找到当前添加笔记的父列表, 这里直接用menuList
        //   this.menuList.push(noteResult)
        //   this.idToMenuMap[noteResult.id] = { data: noteResult, parent: this.menuListMenu }
        // }
      },
      async addMenuOfParent(id) {
        var menu = this.getMenuObjById(id)
        // var newMenu = { id: null, name: 'untitled', menus: [], notes: [] }
        // var postData = { menu: newMenu, parent: menu }
        var newMenu = {
          name: 'untitled',
          parentId: menu.id,
          menus: [],
          notes: []
        }
        const {
          data: res
        } = await this.$http.post('/menus', newMenu)
        if (res.meta.status === 201) {
          newMenu = res.data
          menu.menus.push(newMenu)
          this.idToMenuMap[newMenu.id] = {
            data: newMenu,
            parent: menu
          }
          this.$message({
            message: '添加成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: '添加失败',
            type: 'fail'
          })
        }
        // var parentMenu = this.getMenuObjById(id)
        // var newMenu = { id: null, name: 'untitled', children: [] }
        // var postData = { menu: newMenu, parent: parentMenu }
        // const { data: res } = await this.$http.post('/menu', postData)
        // if (res.meta.status === 201) {
        //   // 往menu里的children里插入newMenu
        //   newMenu = res.data
        //   parentMenu.children.push(newMenu)
        //   this.$message('插入成功')
        //   this.idToMenuMap[newMenu.id] = { data: newMenu, parent: parentMenu }
        // } else {
        //   // continued
        // }
      },
      async addNoteOfParent(id) {
        var menu = this.getMenuObjById(id)
        // var newNote = { id: null, name: 'untitled' }
        // var postData = { note: newNote, parent: menu }
        var date = new Date()
        date = this.format(date)
        var newNote = {
          name: 'untitled',
          menuId: menu.id,
          createDate: date
        }
        const {
          data: res
        } = await this.$http.post('/notes_details', newNote)
        if (res.meta.status === 201) {
          newNote = res.data
          menu.notes.push(newNote)
          // this.idToMenuMap[newNote.id] = { data: newNote, parent: menu }
          this.idToNoteMap[newNote.id] = {
            data: newNote,
            parent: menu
          }
          this.$message({
            message: '添加成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: '添加失败',
            type: 'success'
          })
        }
        // var parentMenu = this.getMenuObjById(id)// parent
        // var newNote = { id: null, name: 'untitled', children: [] }
        // var postData = { note: newNote, parent: parentMenu }
        // const { data: res } = await this.$http.post('/note', postData)
        // if (res.meta.status === 201) {
        //   // 往menu里的children里插入newMenu
        //   newNote = res.data
        //   parentMenu.children.push(newNote)
        //   this.$message('插入成功')
        //   this.idToMenuMap[newNote.id] = { data: newNote, parent: parentMenu }
        // } else {
        //   // continued
        // }
      },
      querySearch(queryString, cb) {
        var restaurants = this.restaurants
        var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants
        // 调用 callback 返回建议列表的数据
        cb(results)
      },
      createFilter(queryString) {
        return (restaurant) => {
          return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
        }
      }
    },
    created() {
      this.getFirstMenu()
      // this.getMenuList()
      // alert(this.activePath)
      Bus.$on('getTarget', (event, id, name, isSubmenu, isNote) => {
        // console.log(id)
        // console.log(isSubmenu)
        this.rightClick(event, id, name, isSubmenu, isNote)
      })
    },
    components: {
      MyMenu,
      Test3
    }
  }

</script>
<style lang="less" scoped>
  .home-container {
    height: 100%
  }

  .el-header {
    position: fixed;
    width: 100%;
    background-color: #373d41;
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: #fff;
    font-size: 20px;

    >div {
      display: flex;
      align-items: center;

      span {
        margin-left: 15px
      }

    }
  }

  .el-aside {
    background-color: #333744;

    .el-menu {
      border: none
    }

    ;
    position: relative;
  }

  .el-main {
    background-color: #eaedf1
  }

  .mybutton {
    width: 100%
  }

  .left-button {
    margin-top: 10px
  }

  .my-input {
    margin-left: 20px;
    margin-right: 10px;
    width: 80%;
  }

  .my-main-container {
    padding: 0px
  }

  .el-footer {
    position: fixed;
    bottom: 0;
    text-align: center
  }

  .side1 {
    position: fixed;
    bottom: 0;
    top: 60px;
    left: 0px;
  }

  .side2 {
    position: fixed;
    bottom: 0;
    top: 60px;
    left: 30px;
  }

  .side3 {
    position: fixed;
    bottom: 0;
    top: 60px;
    left: 230px;
  }

  .side4 {
    position: fixed;
    bottom: 0;
    top: 60px;
    left: 30px;

  }

  .my-main-content {
    position: fixed;
    // left: 430px;
    right: 0px;
    top: 60px;
    bottom: 0px;
    padding: 0px;
    overflow: scroll;
    height: 100%;
  }

  .my-footer {
    position: fixed;
    left: 30px;
    bottom: 0;
  }

  .my-footer2 {
    position: fixed;
    left: 230px;
    bottom: 0;
  }

  .my-button {
    background-color: #333744
  }

  .my-button:hover {
    background-color: #409eff;
  }

  //----------------------------右键弹框-------------
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

  .search>* {
    margin-top: 20px;
  }


  //----------------------------右键弹框-------------

</style>>
