<template>
  <div>
    <el-scrollbar>
             <div style="width:700px;height:700px;border:solid;" >
          ....... blabla.....
        </div>
    </el-scrollbar>
  </div>
</template>

<script>
export default {
  data: () => ({
    show3: true,
    loading: true
  }),
  created () {
    this.$message('123')
  }
}
</script>

<style>

</style>
