<template>
  <v-list-item>
    <user-link
        :user="comment.author"
    ></user-link>
    <v-list-item-content>
      <v-list-item-title>{{comment.text}}</v-list-item-title>
    </v-list-item-content>
    <v-spacer></v-spacer>
    <v-btn v-if="isMine || isMyMessage"
        @click="del"
           class="mx-15"
           depressed
           icon
           color="indigo"
        x-small
    >
      <v-icon>delete</v-icon>
    </v-btn>
  </v-list-item>
</template>

<script>
import UserLink from '../UserLink.vue'
import { mapActions } from "vuex";
export default {
  name: 'CommentItem',
  components: {UserLink},
  props: ['comment', 'messageAuthor'],
  methods: {
    ...mapActions(['removeCommentAction']),
    del() {
      this.removeCommentAction(this.comment)
    }
  },
  computed: {
    isMine() {
      return this.comment.author.id === this.$store.state.profile.id
    },
    isMyMessage(){
      return this.messageAuthor.id === this.$store.state.profile.id
    }
  }
}
</script>

<style scoped>
</style>