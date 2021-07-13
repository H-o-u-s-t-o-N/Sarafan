<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <v-layout column>
          <template
              v-for="(user, index) in sortedUsers">
            <v-divider v-if="index > 0"></v-divider>
            <user-link
                :key="`user.id-${index}`"
                :user="user"
                size="48"
            ></user-link>
          </template>
          <lazy-loader :isMessagePage=false></lazy-loader>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import UserLink from "components/UserLink.vue"
import LazyLoader from 'components/LazyLoader.vue'

export default {
  name: "Users",
  components: {
    UserLink,
    LazyLoader,
  },
  computed: mapGetters(['sortedUsers'])
}
</script>

<style scoped>

</style>