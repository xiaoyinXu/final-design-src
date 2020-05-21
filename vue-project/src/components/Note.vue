<template>
    <div>
      <div style="text-align:center">{{date}}</div>
      <mavonEditor class="my-editor" v-model="content" :scrollStyle="true"
      :toolbarsFlag="true" @imgAdd="$imgAdd"
       @save="$save(noteId)" :tabSize="tabSize" :subfield="true">
      </mavonEditor>
    </div>

</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
  props: ['noteId'],
  name: 'Note',
  components: { mavonEditor },
  data () {
    return {
      content: '',
      tabSize: 4,
      date: '未选中'
    }
  },
  methods: {
    async getContent (id) {
      const { data: res } = await this.$http.get('/notes_details/' + id)
      this.content = res.data.content
      this.date = res.data.createDate
    },
    $imgAdd (filename, $file) {
      // var formdata = new FormData()
      // formdata.append(`${filename}.jpg`, $file)
      alert(`${filename}.jpg`)
      // alert($file)
    },
    async $save (id) {
      // 像服务器发送put请求, 状态码成功后弹出成功消息, 否则弹出失败消息
      const { data: res } = await this.$http.put(`/notes_details/${id}`, { content: this.content })
      if (res.meta.status === 200) {
        this.$message('file already saved')
      } else {
        this.$message('save failed')
      }
    }
  },
  created () {
    this.getContent(this.noteId)
    // alert(777)
  },
  watch: {
    noteId (newId, oldId) {
      // 将content的内容存到oldId对应的文章中
      // 在变换前要保存结果
      // this.$save()
      this.$save(oldId)
      this.note = ''
      // alert(666)
      this.getContent(newId)
    }
  }
}
</script>

<style>
.my-editor {
  /* min-height: 800px !important; */
  height: 1000px;
}
</style>
