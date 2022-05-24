/* 评论 */
<template>
  <div class="bg">
    <div class="comment">
      <h2 style="background-color: #4747A5">评论</h2>
      <div class="comment-msg">
        <div class="comment-img">
          <img class="img" v-show="loginIn" :src="attachImageUrl(avator)">
          <img class="img" v-show="!loginIn" src="../assets/img/logoIP.png">
        </div>
        <el-input class="comment-input" type="textarea" :rows="1" placeholder="请输入评论内容" v-model="textarea">
        </el-input>
      </div>
      <el-button type="primary" size="mini" class="sub-btn" @click="postComment">发表评论</el-button>
    </div>

    <p class="p">精彩评论：共{{commentList.length}}条</p>
    <div class="popular" v-for="(item,index) in commentList" :key="index">
      <div class="clearfix">
        <div class="popular-img le">
          <img :src="attachImageUrl(userPic[index])">
        </div>
        <div class="popular-msg le">
          <div class="clearfix">
            <span class="name le">{{userName[index]}}</span>
            <span v-if="userId == item.userId" style="float: right;margin-left:8px" @click.once="delComment(item.id,item.up, item.userId, index)">
              <i class="el-icon-delete" calss="del-com" ></i>
            </span>
            <span :style="{float: 'right', marginRight: (userId != item.userId ? '21px' : '') }" ref="up" @click.once="postUp(item.id,item.up,index)">
              <svg class="icon">
                <use xlink:href="#icon-zan"></use>
              </svg>
              {{item.up}}
            </span>

          </div>
          <p class="time">{{item.createTime}}</p>
        </div>
      </div>
      <div class="txt">
        <p>{{item.content}}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { mapGetters } from 'vuex'
import { setComment, setLike, getAllComment, getUserOfId, delComment } from '../api/index'

export default {
  name: 'comment',
  mixins: [mixin],
  props: [
    'playId' //当前小说id
  ],
  computed: {
    ...mapGetters([
      'id', //id
      'loginIn', //用户是否已登录
      'userId', //当前登录用户id
      'avator' //当前登录用户头像
    ])
  },
  data() {
    return {
      textarea: '', //存放输入的评论内容
      commentList: [], //存放评论列表
      userPic: [], //用户的头像
      userName: [] //用户的昵称
    }
  },
  mounted() {
      this.getComment()
  },
//   updated() {
//       this.getComment()
//   },
  methods: {
    //提交评论
    postComment() {
      if (this.loginIn) {
        let params = new URLSearchParams()
        params.append('bookId', this.playId)
        params.append('userId', this.userId)
        params.append('content', this.textarea)
        setComment(params)
          .then((res) => {
            if (res.code == 1) {
              this.notify(res.msg, 'success')
              this.textarea = ''
              this.userPic = []
              this.userName = []
              this.getComment()
            } else {
              this.notify(res.msg, 'error')
            }
          })
          .catch((err) => {
            this.notify(res.msg, 'error')
          })
      } else {
        this.rank = null
        this.notify('请先登录', 'warning')
      }
    },
    //获取评论列表
    getComment() {
      getAllComment(this.playId)
        .then((res) => {
          this.commentList = res
        //   console.log(res);
          for (let item of res) {//遍历评论列表获取单个对象
            this.getUsers(item.userId)//获取用户信息
          }
        })
        .catch((err) => {
          this.notify('评论加载失败', 'error')
        })
    },
    //获取用户的头像和昵称
    getUsers(id) {
      getUserOfId(id)
        .then((res) => {
          this.userPic.push(res.avator || res.pic)
          this.userName.push(res.username)
        })
        .catch((err) => {
          this.notify('出错了', 'error')
        })
    },
    //给某一个评论点赞
    postUp(id, up, index) {
      if (this.loginIn) {
        let params = new URLSearchParams()
        params.append('id', id)
        params.append('up', up + 1)
        setLike(params)
          .then((res) => {
            if (res.code == 1) {
              this.$refs.up[index].children[0].style.color = '#2796cd'
              this.getComment()
            } else {
              this.notify('点赞失败', 'error')
            }
          })
          .catch((err) => {
            this.notify('点赞失败', 'error')
          })
      } else {
        this.rank = null
        this.notify('请先登录', 'warning')
      }
    },
    //删除评论
    delComment(id, up, userId) {
      let params = new URLSearchParams()
      params.append('id', id)
      delComment(params)
        .then((res) => {
          this.getComment()
        })
        .catch((err) => {
          this.notify('删除失败', 'error')
        })
    }
  }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/comment.scss';
.bg {
    height: 140px;
}
.comment-input textarea {
  height: 39px;
}
i:hover {
  color: red;
}
.sub-btn{
    background-color: #4747A5;
    border: 1px solid #4747A5;
    padding: 10px;
    margin: 8px;
}
</style>
