<template>
  <span></span>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'LazyLoader',
  props: ['isMessagePage'],
  methods: mapActions(['loadMessagePageAction', 'loadUserPageAction']),
  mounted() {
    window.onscroll = () => {
      const el = document.documentElement
      const isBottomOfScreen = el.scrollTop + window.innerHeight === el.offsetHeight
      if (isBottomOfScreen) {
        if(this.isMessagePage === true){
          this.loadMessagePageAction()
        }else {
          this.loadUserPageAction()
        }
      }
    }
  },
  beforeDestroy() {
    window.onscroll = null
  }
}
</script>

<style scoped>
</style>