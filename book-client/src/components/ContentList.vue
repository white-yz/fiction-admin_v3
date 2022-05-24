// 首页小说、作家列表框图
<template>
  <div class="content-list">
    <ul class="section-content">
      <li style="cursor: pointer;" class="content-item" v-for="(item,index) in contentList" :key="index" @click="goAlbum(item,item.location,item.id,item.writerId)">
        <!-- @click="goBook(item.id,item.writerId)" -->
        <div class="kuo">
          <img class="item-img" :src="attachImageUrl(item.pic)">
        </div>
        <p class="item-name">{{item.name}}</p>
        <!-- <p class="item-name" style="font-size:12px;color: #aaa;">{{item.location ||'更新时间：'+item.updateTime.slice(0,11)}}</p> -->
        <p v-if="item.location" class="item-name" style="font-size:12px;color: #aaa;">{{item.location}}</p>
        <p v-if="item.updateTime" class="item-name" style="font-size:12px;color: #aaa;">{{'更新时间：'+item.updateTime.slice(0,11)}}</p>
        <span class="style">{{item.style}}</span>
      </li>
    </ul>
  </div>
</template>
<script>
import { mixin } from '../mixins'
export default {
  name: 'content-list',
  mixins: [mixin],
  props: {
    contentList: Array,
    isVip: Number
  },
  methods: {
    goAlbum(item, type, id, writerId) {
      this.$store.commit('setTempList', item)
      if (type) {
        //作家
        this.$router.push({ path: `/writer-album`, query: { id } })
      } else {
        //书籍
        this.$router.push({ path: `/Book`, query: { id, writerId } })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/content-list.scss';
.style {
  float: right;
  font-size: 12px;
  color: #4747A5;
  margin: 0 10px 5px;
}
</style>
