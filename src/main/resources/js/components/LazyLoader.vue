<template>
  <span></span>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'LazyLoader',
  props: ['isMessagePage'],
  methods: mapActions(['loadPageAction']),
  mounted() {
    window.onscroll = () => {
      const el = document.documentElement
      const isBottomOfScreen = el.scrollTop + window.innerHeight === el.offsetHeight
      if (isBottomOfScreen) {
        if(this.isMessagePage){
          this.loadMessagePageAction()
        }else {
          this.loadPageAction()
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