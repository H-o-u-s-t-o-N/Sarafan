<template>
  <v-layout row class="pa-10">
    <v-dialog
        v-model="dialog"
        persistent
        transition="dialog-top-transition"
        max-width="600"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
            color="indigo"
            dark
            fixed
            bottom
            right
            fab
            v-bind="attrs"
            v-on="on"
        >
          <v-icon>add</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-text-field
            class="pa-5"
            :label="this.id === null ? `Add new message` : `Edit this message`"
            placeholder="Write something"
            required
            v-model="text"
            @keyup.enter="save(), dialog = false"
            counter
        />
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              elevation="12"
              outlined
              @click="clearData(), dialog = false"
          >
            Cancel
          </v-btn>
          <v-btn
              color="primary"
              elevation="12"
              outlined
              @click="save(), dialog = false"
          >
            <span v-if="this.id === null">Create</span>
            <span v-else>Save</span>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-layout>
</template>

<script>
import { mapActions } from 'vuex'
import * as Sentry from '@sentry/browser'
export default {
  props: ['messageAttr'],
  data() {
    return {
      text: '',
      id: null,
      dialog: false
    }
  },
  watch: {
    messageAttr(newVal) {
      this.text = newVal.text
      this.id = newVal.id
      this.dialog = true
    }
  },
  methods: {
    ...mapActions(['addMessageAction', 'updateMessageAction']),
    save() {
      // Sentry.captureMessage("Start editing")
      const message = {
        id: this.id,
        text: this.text
      }
      if(message.text !== '') {
        if (this.id !== null) {
          this.updateMessageAction(message)
        } else {
          this.addMessageAction(message)
        }
      }
      this.text = ''
      this.id = null

      // throw new Error("Bang!")
    },
    clearData(){
      this.text = ''
      this.id = null
    }
  }
}
</script>

<style>
</style>