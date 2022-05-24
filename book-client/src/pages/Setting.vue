/* 个人中心 */
<template>
  <div class="setting">
    <div class="leftCol">
      <ul class="setting-aside">
        <li v-for="(item,index) in settingList" :key="index" :class="{activeColor: activeName==item.name}" @click="handleClick(item)">
          {{item.name}}
        </li>
      </ul>
    </div>
    <div class="contentCol">
      <component :is="componentSrc"></component>
    </div>
  </div>
</template>

<script>
import Info from '../components/Info'
import Upload from '../components/Upload'
import MyBook from '../components/MyBook'
import Works from '../components/Works'
import { mixin } from '../mixins'
export default {
  name: 'setting',
  mixins: [mixin],
  components: {
    Info,
    Upload,
    MyBook,
    Works
  },
  created() {
    this.componentSrc = this.$route.query.nav
    this.activeName = this.$route.query.activeName
  },
  data() {
    return {
      settingList: [
        { name: '基本信息', path: 'Info' },
        { name: '我的书架', path: 'MyBook' }
      ],
      activeName: '',
      componentSrc: ''
    }
  },
  created() {
    if (this.$store.state.user.userType == 'WRITER') {
      this.settingList = [
        { name: '基本信息', path: 'Info' },
        { name: '我的作品', path: 'Works' }
      ]
    } else {
      this.settingList = [
        { name: '基本信息', path: 'Info' },
        { name: '我的书架', path: 'MyBook' }
      ]
    }
    this.componentSrc = 'Info'
  },
  methods: {
    handleClick(item) {
      this.activeName = item.name
      this.componentSrc = item.path
      this.$store.commit('setActiveName', item.name)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/setting.scss';
</style>