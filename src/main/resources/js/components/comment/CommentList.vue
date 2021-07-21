<template>
  <v-list two-line>
    <v-subheader>
      Comments
    </v-subheader>

    <template
        v-for="(item, index) in comments"
    >
      <v-divider
          v-if="index > 0"
          :key="index"
          inset
      ></v-divider>

      <comment-item
          :comment="item"
          :message-author="messageAuthor"
          :key="'item' + index"
      ></comment-item>
    </template>

    <comment-form :message-id="messageId"></comment-form>
  </v-list>
</template>

<script>
import CommentForm from './CommentForm.vue'
import CommentItem from './CommentItem.vue'
export default {
  name: 'CommentList',
  components: {CommentForm, CommentItem},
  props: ['comments', 'messageId', 'messageAuthor']
}
</script>

<style scoped>
</style>