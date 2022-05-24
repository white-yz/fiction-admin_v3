<template>
  <div id="app">
    <the-header />
    <router-view class="router-view"/>
    <scroll-top/> 
    <the-footer/>  
  </div>
</template>

<script>
import TheHeader from './components/TheHeader';
import ScrollTop from './components/ScrollTop';
import TheFooter from './components/TheFooter';

export default {
  name: 'App',
  components: {
    TheHeader,
    ScrollTop,
    TheFooter
  }
}
</script>

<style  lang="scss" scoped>
@import './assets/css/app.scss';
</style>
