<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <div  class="display-1 my-5 ">
          {{isMyProfile ? 'My channels' : profile.name + '`s channels' }}
        </div>
        <v-layout column>
          <v-list class="mt-5">
            <v-list-item
                v-for="item in channels"
            >
              <user-link
                  :user="item.channel"
              ></user-link>
            </v-list-item>
          </v-list>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import profileApi from 'api/profile'
import UserLink from 'components/UserLink.vue'
export default {
  name: "Channels",
  components: {UserLink},
  data() {
    return {
      profile: {},
      channels: []
    }
  },
  computed:{
    isMyProfile() {
      return !this.$route.params.id ||
          this.$route.params.id === this.$store.state.profile.id
    },
  },
  methods: {
    async changeSubscription(channelId) {
      const data = await profileApi.changeSubscription(this.channels[channelId].id)
      const channel = await data.json()
    },
    async updateProfile() {
      const id = this.$route.params.id || this.$store.state.profile.id
      const data = await profileApi.get(id)
      this.profile = await data.json()
      this.$forceUpdate()
    }
  },
  async beforeMount() {
    await this.updateProfile()

    const resp = await profileApi.channelList(this.$route.params.id)
    this.channels = await resp.json()
  }
}
</script>

<style scoped>

</style>