<template>
  <router-link
      :to="`/user/${user.id}`"
      class="pa-3 text-decoration-none"
  >
    <v-avatar
        v-if="user && user.userpic"
        :size="`${size || 36}px`"
        rounded
    >
      <img
          :src="user.userpic"
          :alt="user.name"
      >
    </v-avatar>
    <v-avatar
        v-else
        :size="`${size || 36}px`"
        color="indigo"
        rounded
    >
      <v-icon dark>perm_identity</v-icon>
    </v-avatar>
    <a class="
    d-inline
    font-weight-medium
    overline
    mx-3
">
      {{ userName }}
    </a>
  </router-link>
</template>

<script>
export default {
  name: 'UserLink',
  props: ['user', 'size'],
  computed: {
    userName() {
      return this.user ? this.user.name : 'unknown'
    }
  },
}
</script>

<style scoped>
</style>