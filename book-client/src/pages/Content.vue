/* 从阅读页面修改内容 */
<template>
  <div class="bg">
    <div class="content">
      <div class="textbox">
        <div class="story_title">
          <div class="title">
            <span class="h1">{{content.name}}</span>
            <span class="save" @click="saveDir" v-if="isEdit">点击保存</span>
          </div>

          <p class="textinfo">
            <span>书籍：<span class="a" @click="goBook(book.id,book.writerId)">{{book.name}}</span>&nbsp;</span>
            <span>类型：{{book.style}}&nbsp;</span>
            <span>作者：<span class="a" @click="goWriter(book.writerId)">{{writer.name}}</span>&nbsp;</span>
            <span>字数：{{content.content.replace(/\s+/g,"").length}}&nbsp;</span>
            <span>更新时间：{{book.updateTime.slice(0,11)}}&nbsp;</span>
          </p>
        </div>
        <div class="txtwrap">
          <div class="text">
            <div class="bookreadercontent ">
              <p class="body" id="content" style="white-space:pre-wrap;" :contenteditable="isEdit">{{content.content}}</p>
            </div>
          </div>
        </div>

      </div>

    </div>

    <div id="leftFloatBar" class="leftFloatBarBtnList">
      <ui class="barList">
        <li>
          <a class="iconfont icon-mulu1">
            <div class="ml">
              <span id="ml">目录</span>
              <ul>
                <li v-for="(item) in directory" :key="item.id" @click="contentPage(item.id,item.bookId)">{{item.name}}</li>
              </ul>
            </div>
          </a>
        </li>
        <li v-if="!isEdit">
          <div v-if="!isShowCollect" class="item" @click="collection" title="加入书架">
            <svg class="icon">
              <use xlink:href="#icon-xihuan-shi"></use>
            </svg>
          </div>
          <div v-if="isShowCollect" class="item" @click="delCollection" title="移出书架">
            <svg class="icon active">
              <use xlink:href="#icon-xihuan-shi"></use>
            </svg>
          </div>
        </li>
      </ui>
    </div>
    <div id="leftFloatBar" class="rightFloatBarBtnList">
      <ui class="barList">
        <li>
          <div v-if="key" class="item" @click="star()" title="开启滚动">
            <a class="iconfont icon-kaiji"></a>
          </div>
          <div v-if="!key" class="item" @click="stop()" title="暂停滚动">
            <a class="iconfont active icon-kaiji"></a>
          </div>
        </li>
        <li>
          <el-input-number step="10" v-model="speed" size="mini" @change="handleChange" :min="0" :max="100" label="速度"></el-input-number>
        </li>
      </ui>
    </div>
  </div>
</template>
<script>
import {
  bookContent,
  bookOfBookId,
  writerOfWriterId,
  directoryOfBookId,
  setCollect,
  delCollect,
  getCollectOfUserId,
  updateDirectory
} from '../api/index'
import { mapGetters } from 'vuex'
export default {
  name: 'Content',
  data() {
    return {
      id: '',
      bookId: '',
      writerId: '',
      directory: '',
      content: [],
      book: [],
      writer: [],
      key: true, //控制滚动条是否滚动
      timer: 0,
      speed: 10,
      isEdit: null,
      isShowCollect: null
    }
  },
  created() {
    this.id = this.$route.query.id
    this.bookId = this.$route.query.bookId
    this.writerId = this.$route.query.writerId
    this.isEdit = (this.$route.query.edit && true) || false
    this.getContent()
    this.getBook()
    this.getDirectory()
    // this.flag();
  },
  computed: {
    ...mapGetters([
      'loginIn', //用户是否已登录
      'userId', //当前登录用户id
      'isActive' //当前书籍是否已收藏
    ])
  },
  methods: {
    getContent() {
      this.content = []
      bookContent(this.id).then((res) => {
        this.content = res
      })
      if (this.loginIn) {
        getCollectOfUserId(this.userId).then((res) => {
          // console.log(res);
          for (let item of res) {
            if (item.bookId == this.bookId) {
              this.isShowCollect = true
              this.$store.commit('setIsActive', true)
              break
            }
          }
        })
      }
    },
    getBook() {
      this.book = []
      this.writer = []
      bookOfBookId(this.bookId).then((res) => {
        this.book = res
        writerOfWriterId(res.writerId).then((res) => {
          this.writer = res
          // console.log(res);
        })
      })
    },
    saveDir() {
      let params = new URLSearchParams()
      params.append('id', this.content.id)
      params.append('name', this.content.name)
      params.append('content', document.getElementById('content').innerHTML)
      updateDirectory(params)
        .then((res) => {
          if (res.code == 1) {
            this.getContent()
            this.$message({
              message: '修改成功',
              showClose: true,
              type: 'success'
            })
            this.notify('修改成功', '')
          } else {
            this.$message({
              message: '修改失败',
              showClose: true,
              type: 'error'
            })
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getDirectory() {
      this.directory = []
      this.count = 0
      directoryOfBookId(this.bookId).then((res) => {
        this.directory = res
        for (var i = 0; i < res.length; i++) {
          this.count += res[i].content.replace(/\s+/g, '').length
        }
      })
    },
    contentPage(id, bookId) {
      if (!this.isEdit) {
        this.$router.push({ path: `/Content`, query: { id, bookId } })
        location.reload()
      } else {
        bookContent(id).then((res) => {
          this.content = res
        })
      }
    },
    goBook(id, writerId) {
      this.$router.push({ path: `/Book`, query: { id, writerId } })
    },
    goWriter(id) {
      this.$router.push({ path: `/writer-album`, query: { id } })
    },
    //收藏
    collection() {
      if (this.loginIn) {
        var params = new URLSearchParams()
        params.append('userId', this.userId)
        params.append('bookId', this.bookId)
        setCollect(params).then((res) => {
          if (res.code == 1) {
            this.$store.commit('setIsActive', true)
            this.isShowCollect = true
            this.$message({
              message: '收藏成功',
              showClose: true,
              type: 'success'
            })
          } else if (res.code == 2) {
            this.$message({
              message: '已加入书架',
              showClose: true,
              type: 'warning'
            })
          } else {
            this.$message({
              message: '收藏失败',
              showClose: true,
              type: 'error'
            })
          }
        })
      } else {
        this.$message({
          message: '请先登录',
          showClose: true,
          type: 'warning'
        })
      }
    },
    delCollection() {
      var params = new URLSearchParams()
      params.append('userId', this.userId)
      params.append('bookId', this.bookId)
      delCollect(params)
        .then((res) => {
          // console.log(res);
          this.$store.commit('setIsActive', false)
          this.isShowCollect = false
          this.$message({
            message: '取消收藏',
            showClose: true,
            type: 'success'
          })
        })
        .catch((err) => {
          console.log(err)
        })
    },
    flag() {
      var demo =
        document.documentElement.scrollHeight -
        document.documentElement.scrollTop -
        document.documentElement.clientHeight
      if (demo == 0) {
        this.key = true
        clearInterval(this.timer) //取消由 setInterval() 函数设定的定时执行操作
      } else {
        this.key = false
      }
    },
    star() {
      var _this = this
      if (this.key) {
        this.timer = setInterval(function () {
          window.scrollBy(0, 1) //向右滚动0，向下滚动1px
          _this.flag()
        }, this.speed)
        this.key = false
      }
    },
    stop() {
      clearInterval(this.timer)
      this.key = true
    },
    handleChange() {
      if (!this.key) {
        clearInterval(this.timer) //停止
        this.key = true
        this.timer = setInterval(function () {
          window.scrollBy(0, 1)
        }, this.speed)
        this.key = false
      }
    }
  }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/content.scss';
.a {
  cursor: pointer;
  color: #333;
}
.a:hover {
  color: #5151b6;
}

.item {
  position: relative;
  width: 60px;
  height: 60px;
  line-height: 72px;
  text-align: center;
  cursor: pointer;
  background-color: #f6f4ec;
  font-size: 18px;
}
.item:hover {
  background-color: #fff;
}
.active {
  color: #5151b6 !important;
}
.icon {
  width: 1.2em;
  height: 1.2em;
  font-size: 1.2em;
  color: #000;
  fill: currentColor;
  overflow: hidden;
  position: relative;
}
.title {
  padding-top: 15px !important;
  margin-bottom: 15px !important;
}
.h1 {
  font-size: 20pt;
}
.save {
  font-size: 20pt;
  float: right;
  color: #5151b6;
}
.save:hover {
  color: #6b6bd4;
}
</style>
