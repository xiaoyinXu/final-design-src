<template>
  <div v-if="true" @click="fn(1)">
    1
  </div>
</template>

<script>
export default {
  name: 'MyTest2',
  methods: {
    fn (id) {
      alert(id)
      this.$emit('fn')
    }
  }

}
</script>

<style>

</style>
