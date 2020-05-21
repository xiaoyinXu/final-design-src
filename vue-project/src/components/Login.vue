<template>

  <div class="container">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
      <div class="form-signin" >
        <h2 class="form-signin-heading">Please sign in</h2>
        <p>
          <label for="username" class="sr-only">Username</label>
          <input v-model="username" type="text" id="username" name="username" class="form-control" placeholder="Username" required autofocus>
        </p>
        <p>
          <label for="password" class="sr-only">Password</label>
          <input @keyup.enter='login' v-model="password" type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        </p>  
<input name="_csrf" type="hidden" value="3b2af3cf-ab83-4fa2-8fb7-b5387aef25ac" />
        <button class="btn btn-lg btn-primary btn-block" @click="login">Sign in</button>
      </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    async login () {
    //   alert(this.username)
    //   alert(this.password)
    //   this.$router.push('/welcome')
      const { data: res } = await this.$http.post('/login', { username: this.username, password: this.password })
      console.log(res)
      if (res.meta.status === 200) {
        const token = res.data.jwt
        // alert(token)
        window.sessionStorage.setItem('jwt', token)
        this.$router.push('/welcome')
      } else {
        this.$message(res.meta.msg)
      }
    }
  }

}
</script>

<style>

</style>
