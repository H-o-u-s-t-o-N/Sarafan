<template>
  <v-card class="my-2" elevation="10">
    <v-card-text primary-title>
      <user-link
          :user="message.author"
          size="48"
      ></user-link>
      <div class="pa-8">
        <p class="font-weight-medium body-1">
          {{ message.text }}
        </p>
      </div>
    </v-card-text>
    <media v-if="message.link" :message="message"></media>
    <v-card-actions
        v-if="isMine"
        class="d-flex justify-end px-10">
      <v-btn
          value="Edit"
          @click="edit"
          color="primary"
          elevation="6"
          outlined
          small
      >
        <v-icon left>edit</v-icon>
        Edit</v-btn>
      <v-btn
          @click="del"
          color="primary"
          elevation="6"
          outlined
          small
      >
        <v-icon left>delete</v-icon>
        Delete
      </v-btn>
    </v-card-actions>
    <v-divider class="ma-4"></v-divider>
    <comment-list
        :comments="message.comments"
        :messageId="message.id"
        :messageAuthor="message.author"
    ></comment-list>
  </v-card>
</template>

<script>
import { mapActions } from 'vuex'
import Media from '../media/Media.vue'
import CommentList from '../comment/CommentList.vue'
import UserLink from '../UserLink.vue'
export default {
  props: ['message', 'editMessage'],
  components: {UserLink, CommentList, Media },
  methods: {
    ...mapActions(['removeMessageAction']),
    edit() {
      this.editMessage(this.message)
    },
    del() {
      this.removeMessageAction(this.message)
    }
  },
  computed: {
    isMine() {
      return this.message.author.id === this.$store.state.profile.id
    }
  }
}
</script>

<style>
</style>