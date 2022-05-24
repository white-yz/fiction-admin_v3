<template>
  <div class="banner-container">
    <!-- 包裹图片的盒子宽度应该随着图片的个数动态变化 -->
    <ul
      class="images"
      :style="{
        width: banners.length * 100 + '%',
        marginLeft: -index * 100 + '%',
      }"
    >
      <li v-for="(item, i) in banners" :key="i">
        <img :src="item.url" alt="" :width="imgWidth" />
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    banners: {
      type: Array,
      required: true,
    },
    duration: {
      type: Number,
      default: 2000,//轮播时间间隔
    },
  },
  // 创建成时开始切换
  created() {
    this.autoStart();
  },
  //销毁时停止
  destroyed() {
    this.autoStop();
  },
  data() {
    return {
      imgWidth: 0,
      index: 0,//当前是第几张
      timer: null,
    };
  },
  methods: {
    autoStart() {
      this.imgWidth = window.innerWidth + "px";//设置图片宽度
      if (this.timer) {
        return;
      }
      //窗口变化时更改图片宽度
      window.onresize = () => {
        this.imgWidth = window.innerWidth + "px";
      };
      this.timer = setInterval(() => {//实现定时调用
        //轮播结束时取余换到第一张
        this.index = (this.index + 1) % this.banners.length;
      }, this.duration);
    },
    autoStop() {
      clearInterval(this.timer);//停止调用
      this.timer = null;
    },
  },
};
</script>

<style scoped>
.banner-container {
  position: relative;
  overflow: hidden;
}
.banner-container li {
  display: block;
  height: 100%;
  float: left;
}
.images {
  height: 100%;
  transition: 0.5s;
}
</style>