<template>
  <v-layout row class="px-16 my-4">
    <v-text-field
        label="Add comment"
        placeholder="Write something"
        v-model="text"
        @keyup.enter="save"
        counter
    />
    <v-btn
        color="primary"
        elevation="12"
        outlined
        small
        @click="save">
      <v-icon left>add</v-icon>
      Add
    </v-btn>
  </v-layout>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'CommentForm',
  props: ['messageId'],
  data() {
    return {
      text: ''
    }
  },
  methods: {
    ...mapActions(['addCommentAction']),
    save() {
      if(this.text !== ''){
        this.addCommentAction({
          text: this.text,
          message: {
            id: this.messageId
          }
        })
        this.text = ''
      }
    }
  }
}
</script>

<style scoped>
</style>