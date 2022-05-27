/* 书籍详情页 */
<template>
  <div class="book">
    <div class="detail-box-lf">
      <div class="detail-info-box clearfix">
        <div class="detail-info-header-container clearfix">
          <div class="clearfix">
            <div class="detail-info-header-box-lf">
              <span class="cover_mask">
                <a><img class="lazy" :src="getUrl(book.pic)" alt="" /></a>
              </span>
            </div>
            <div style="margin-left: 23px" class="detail-info-header-box-rt detail-info-list">
              <h2>{{ book.name }}</h2>
              <ul>
                <li>
                  作者&nbsp;：<a href="javascript:;">{{ writer.name }}</a>
                </li>
                <li>
                  <p>章节&nbsp;:&nbsp;{{ directory.length }}</p>
                </li>
                <li>
                  <p>发布时间&nbsp;：{{ book.createTime.slice(0, 11) }}</p>
                </li>
                <li>
                  <p>更新时间&nbsp;：{{ book.updateTime.slice(0, 11) }}</p>
                </li>
                <li>
                  <p>总字数&nbsp;：{{ this.count }}</p>
                </li>
                <li>
                  <p>类型&nbsp;：{{ book.style }}</p>
                </li>
                <li class="clearfix">
                  <div class="album-score">
                    <div class="re le">
                      <h3>书籍评分：</h3>
                      <div>
                        <el-rate v-model="average" disabled :colors="colors"></el-rate>
                      </div>
                      <span class="sum">{{sum}}</span>
                      <span id="score">{{ average * 2 }}</span>
                    </div>
                    <div style="margin-left: 65px;" class="le">
                      <h3>评价：</h3>
                      <div @click="setRank">
                        <el-rate v-model="rank" :colors="colors" allow-half show-text></el-rate>
                      </div>
                    </div>
                  </div>
                </li>
                <li class="clearfix" style="margin-top:10px; ">
                  <span v-if="!isShowCollect" class="online_read " @click="collection">加入书架</span>
                  <span v-if="isShowCollect" class="online_read " @click="delCollection">移出书架</span>
                </li>
              </ul>
            </div>
          </div>
          <div class="detail_info_intro">
            <h4 style="margin-bottom: 15px"><span>简介</span></h4>
            <div class="actual-height" style="padding-bottom: 0px">
              <p>{{ book.introduction }}</p>
            </div>
          </div>
        </div>
        <div class="directory clearfix">
          <h4 style="
              margin-bottom: 15px;
              font-size: 20px;
              font-weight: normal;
              display: block;
              color: #4747A5
            ">
            <span>章节列表</span>
          </h4>
          <ul>
            <li v-for="item in directory" :key="item.id" @click="contentPage(item.id, item.bookId)">
              {{ item.name }}
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="detail-box-rt">
      <div class="announcer_container_box announcer_detail clearfix">
        <h4 style="background-color: #4747A5">作者</h4>
        <div class="content">
          <span class="avatar_mask">
            <span @click="goWriter(writer.id)"><img :src="getUrl(writer.pic)" alt="" /></span>
          </span>
          <a class="announcer_name">{{ writer.name }}</a>
          <div class="contact_info">
            <p>{{ writer.introduction }}</p>
          </div>
        </div>
      </div>
      <!-- 评论组件 -->
      <comment :playId="bookId"></comment>
    </div>
  </div>
</template>

<script>
import {
  bookOfBookId,
  writerOfWriterId,
  directoryOfBookId,
  setRank,
  getRankOfBookID,
  getRankOfSum,
  setCollect,
  delCollect,
  getCollectOfUserId,
  setRecord
} from '../api/index'
import { mixin } from '../mixins/index'
import { mapGetters } from 'vuex'
import Comment from '../components/Comment'
export default {
  name: 'Book',
  mixins: [mixin],
  components: {
    Comment
  },
  data() {
    return {
      bookId: '',
      writerId: '',
      wId: 'writerId',
      book: [],
      writer: [],
      directory: [],
      count: 0,
      average: 0, //平均分
      rank: 0, //提交评价的分数
      sum: '', //评分人数
      colors: ['#d9a6ad', '#d48892', '#D55969'],
      isVip: null,
      isShowCollect: null
    }
  },
  computed: {
    ...mapGetters([
      'loginIn', //用户是否已登录
      'userId', //当前登录用户id
      'isActive', //当前书籍是否已收藏
      'userId'
    ])
  },
  created() {
    this.bookId = this.$route.query.id
    this.writerId = this.$route.query.writerId
    this.getBook()
    this.getWriter()
    this.getDirectory()
    this.getRank(this.bookId)
  },
  methods: {
    getBook() {
      this.book = []
      this.$store.commit('setIsActive', false)
      bookOfBookId(this.bookId).then((res) => {
        //平均分
        this.book = res
        // console.log(res);
      })
      if (this.loginIn) {
        getCollectOfUserId(this.userId) //获取读者收藏
          .then((res) => {
            // console.log(res);
            for (let item of res) {
              if (item.bookId == this.bookId) {
                this.isShowCollect = true
                //判断是否已经收藏
                this.$store.commit('setIsActive', true)
                break
              }
            }
          })
      }
    },
    getWriter() {
      //获取作者
      this.writer = []
      writerOfWriterId(this.writerId).then((res) => {
        this.writer = res
        // console.log(res);
      })
    },
    getDirectory() {
      //获取章节
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
      this.$store.commit('setActiveName', '')
      this.$router.push({ path: `/Content`, query: { id, bookId } })
      var params = new URLSearchParams()
      params.append('directoryId', id)
      params.append('bookId', bookId)
      params.append('consumerId', this.userId)
      setRecord(params).then((res) => {})
    },
    goWriter(id) {
      this.$router.push({ path: `/writer-album`, query: { id } })
    },
    //获取书籍评分
    getRank(id) {
      getRankOfBookID(id) //平均分
        .then((res) => {
          //   console.log('平均分：'+res);
          this.average = res / 2
        })
        .catch((err) => {
          console.log(err)
        })
      getRankOfSum(id) //评分人数
        .then((res) => {
          //   console.log('人数：'+res);
          if (res == 0) {
            this.sum = '暂无评分'
          } else {
            this.sum = res + '人评分'
          }
        })
        .catch((err) => {
          console.log(err)
        })
    },
    //提交评分
    setRank() {
      if (this.loginIn) {
        let params = new URLSearchParams()
        params.append('bookId', this.bookId)
        params.append('consumerId', this.userId)
        params.append('score', this.rank * 2)
        setRank(params)
          .then((res) => {
            if (res.code == 1) {
              this.notify('评分成功', 'success')
              this.getRank(this.bookId)
            } else {
              this.notify('评分失败', 'error')
            }
          })
          .catch((err) => {
            this.notify('您已经评价过啦', 'error')
          })
      } else {
        this.rank = null
        this.notify('请先登录', 'warning')
      }
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
            window.sessionStorage.getItem('isActive', true)
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
    //移除收藏
    delCollection() {
      var params = new URLSearchParams()
      params.append('userId', this.userId)
      params.append('bookId', this.bookId)
      delCollect(params)
        .then((res) => {
          // console.log(res);
          this.$store.commit('setIsActive', false)
          this.isShowCollect = false
          window.sessionStorage.removeItem('isActive')
          this.$message({
            message: '取消收藏成功',
            showClose: true,
            type: 'success'
          })
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/book.scss';
.album-score .re {
  position: relative;
}
.album-score .le {
  float: left;
}
#score {
  position: absolute;
  right: -50px;
  top: 22px;
  font-size: 40px;
}
.sum {
  color: #4747a5;
}
.online_read {
  background-color: #4747a5;
}
.online_read disabled {
  background-color: #d55969;
}
h4 span {
  color: #4747a5;
  border-left: 3px solid #4747a5;
  padding-left: 8px;
}
</style>
