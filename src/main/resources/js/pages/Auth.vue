<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <div  class="title my-5 ">
          Please, login with:
        </div>
          <v-btn
              href="/login"
              color="primary"
              elevation="6"
              outlined
              small
          >
            Google
          </v-btn>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'Auth',
}
</script>

<style scoped>
</style>