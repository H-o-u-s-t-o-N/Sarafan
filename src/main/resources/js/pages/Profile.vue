<template>
  <v-container>
    <v-layout justify-space-around>
      <v-flex :xs6="!$vuetify.breakpoint.xsOnly">
        <div class="display-1 mb-15">User profile</div>

        <v-layout row justify-space-between>
          <v-flex>
            <v-layout column>
              <v-flex class="d-flex justify-center align-self-center ma-5">
                <v-avatar
                    v-if="profile && profile.userpic"
                    size="100px"
                    rounded
                >
                  <img :src="profile.userpic">
                </v-avatar>
                <v-avatar
                    v-else
                    size="100px"
                    color="indigo"
                    rounded
                >
                  <v-icon dark>perm_identity</v-icon>
                </v-avatar>
              </v-flex>
              <v-flex class="d-flex justify-center">
                <v-btn
                    v-if="!isMyProfile"
                    @click="changeSubscription"
                    :color="isISubscribed ? 'error' : 'primary'"
                    class="d-flex justify-center my-10"
                    elevation="12"
                    outlined
                >
                  {{isISubscribed ? 'Unsubscribe' : 'Subscribe'}}
                </v-btn>
              </v-flex>
            </v-layout>
          </v-flex>
          <v-flex class="px-1">
            <v-layout column>
              <sub class="caption">User name</sub>
              <v-flex class="
                  px-7
                  title"
              >{{profile.name}}</v-flex>
              <sub class="caption">Locale</sub>
              <v-flex class="
                  px-7
                  title"
              >{{profile.locale}}</v-flex>
              <v-flex>{{profile.gender}}</v-flex>
              <sub class="caption">Last Visit</sub>
              <v-flex
                  class="
                  px-7
                  title"
              >{{profile.lastVisit}}</v-flex>
              <sub class="caption">Subscriptions & Subscribers</sub>
              <v-hover
                  v-slot="{ hover }"
              >
                <v-card
                    :elevation="hover ? 16 : 2"
                    :class="{ 'on-hover': hover }"
                    class="
                    px-7
                    my-2
                    title"
                >
                  {{profile.subscriptions && profile.subscriptions.length}} subscriptions
                </v-card>
              </v-hover>
              <v-hover
                  v-slot="{ hover }"
              >
                <router-link
                    v-if="isMyProfile"
                    :to="`/subscriptions/${profile.id}`"
                    class="text-decoration-none"
                >
                  <v-card
                      :elevation="hover ? 16 : 2"
                      :class="{ 'on-hover': hover }"
                      class="
                      px-7
                      mb-15
                      title"
                  >
                    {{profile.subscribers && profile.subscribers.length}} subscribers
                  </v-card>
                </router-link>
                <v-card
                    v-else
                    :elevation="hover ? 16 : 2"
                    :class="{ 'on-hover': hover }"
                    class="
                    px-7
                    mb-15
                    title"
                >
                  {{profile.subscribers && profile.subscribers.length}} subscribers
                </v-card>
              </v-hover>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import profileApi from 'api/profile'
export default {
  name: 'Profile',
  data() {
    return {
      profile: {}
    }
  },
  computed: {
    isMyProfile() {
      return !this.$route.params.id ||
          this.$route.params.id === this.$store.state.profile.id
    },
    isISubscribed() {
      return this.profile.subscribers &&
          this.profile.subscribers.find(subscription => {
            return subscription.subscriber === this.$store.state.profile.id
          })
    }
  },
  watch: {
    '$route'() {
      this.updateProfile()
    }
  },
  methods: {
    async changeSubscription() {
      const data = await profileApi.changeSubscription(this.profile.id)
      this.profile = await data.json()
    },
    async updateProfile() {
      const id = this.$route.params.id || this.$store.state.profile.id
      const data = await profileApi.get(id)
      this.profile = await data.json()
      this.$forceUpdate()
    }
  },
  beforeMount() {
    this.updateProfile()
  }
}
</script>

<style scoped>
img {
  max-width: 100%;
  height: auto;
}
</style>