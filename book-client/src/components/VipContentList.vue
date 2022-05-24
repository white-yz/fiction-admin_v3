// VIP小说列表框图
<template>
  <div class="content-list">
    <ul class="section-content">
      <li style="cursor: pointer;" class="content-item" v-for="(item,index) in contentList" :key="index" @click="goAlbum(item,item.location,item.id,item.writerId)">
        <!-- @click="goBook(item.id,item.writerId)" -->
        <div class="kuo">
          <img class="item-img" :src="attachImageUrl(item.pic)">
        </div>
        <p class="item-name">{{item.name}}<span class="viptext">&nbsp;vip</span></p>
        <p class="item-name" style="font-size:12px;color: #aaa;">{{item.location ||'更新时间：'+item.updateTime.slice(0,11)}}</p>
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
      if (!this.$store.state.user.vip || this.$store.state.user.vip == 0) {
        this.$message({
          message: '此为VIP专区, 请先购买成为VIP后即可免费阅读!',
          showClose: true,
          type: 'warning'
        })
        return
      }
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
.viptext {
  color: #ffcc00;
  font: bold 18pt/1.5 georgia, sans-serif !important;
  text-shadow: 0 0 2px #fff, 0 0 8px #fff, 0 0 3px #fff, 0 0 6px #ffcc00, 0 0 7px #ffcc00, 0 0 8px #ffcc00,
    0 0 9px #ffcc00, 0 0 10px #ffcc00 !important;
  // ff00de
}
</style>
