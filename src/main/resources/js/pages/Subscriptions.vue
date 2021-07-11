<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <div  class="display-1 my-5 ">
         {{isMyProfile ? 'My subscribers' : profile.name + '`s subscribers' }}
        </div>
        <v-layout column>
          <v-list class="mt-5">
            <v-list-item
                v-for="item in subscriptions"
            >
              <user-link
                  :user="item.subscriber"
              ></user-link>

              <v-btn
                  v-if="isMyProfile"
                  @click="changeSubscriptionStatus(item.subscriber.id)"
                  :color="item.active ? 'error' : 'primary'"
                  elevation="8"
                  outlined
              >
                {{item.active ? "Dismiss" : "Approve"}}
              </v-btn>
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
  name: 'Subscriptions',
  components: {UserLink},
  data() {
    return {
      profile: {},
      subscriptions: []
    }
  },
  computed:{
    isMyProfile() {
      return !this.$route.params.id ||
          this.$route.params.id === this.$store.state.profile.id
    },
  },
  methods: {
    async changeSubscriptionStatus(subscriberId) {
      await profileApi.changeSubscriptionStatus(subscriberId)
      const subscriptionIndex = this.subscriptions.findIndex(item =>
          item.subscriber.id === subscriberId
      )
      const subscription = this.subscriptions[subscriptionIndex]
      this.subscriptions = [
        ...this.subscriptions.slice(0, subscriptionIndex),
        {
          ...subscription,
          active: !subscription.active
        },
        ...this.subscriptions.slice(subscriptionIndex + 1)
      ]
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

    const resp = await profileApi.subscriberList(this.$route.params.id)
    this.subscriptions = await resp.json()
  }
}
</script>

<style scoped>
</style>