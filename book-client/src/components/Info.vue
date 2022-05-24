// 个人信息页
<template>
  <div>
    <div class="info ">
      <div class="le title clearfix">
        <div class="imgDiv">
          <img class="img" :src='attachImageUrl(imgUrls)'>
          <el-upload class="upload" :action="uploadUrl()" :show-file-list="false" :on-success="handleAvatorSuccess" :before-upload="beforeAvatorUpload">
            <!-- <el-button size="small" class="iconfont icon-xiangji"></el-button> -->
            <div class="div">
              <i class="iconfont icon-xiangji"></i>
            </div>
          </el-upload>
        </div>
      </div>
      <div class="ri personal">
        <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
          <el-form-item prop="username" label="用户名">
            <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password" label="密码">
            <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item v-if="$store.state.user.userType== 'WRITER'" prop="name" label="姓名">
            <el-input v-model="registerForm.name" placeholder="姓名"></el-input>
          </el-form-item>
          <el-form-item prop="sex" label="性别">
            <el-radio-group v-model="registerForm.sex">
              <el-radio :label="0">女</el-radio>
              <el-radio :label="1">男</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item v-if="$store.state.user.userType != 'WRITER'" prop="phoneNum" label="手机">
            <el-input v-model="registerForm.phoneNum" placeholder="手机"></el-input>
          </el-form-item>
          <el-form-item v-if="$store.state.user.userType != 'WRITER'" prop="email" label="邮箱">
            <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
          </el-form-item>
          <el-form-item prop="birth" label="生日">
            <el-date-picker type='date' :editable="false" v-model="registerForm.birth" placeholder="选择日期" style="width: 100%;"></el-date-picker>
          </el-form-item>
          <el-form-item prop="introduction" :label="$store.state.user.userType== 'WRITER' ? '简介' : '签名'">
            <el-input v-model="registerForm.introduction" :placeholder="$store.state.user.userType== 'WRITER' ? '简介' : '签名'"></el-input>
          </el-form-item>
          <el-form-item prop="location" label="地区">
            <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%;">
              <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div class="btn">
          <div @click="saveMsg" style="background-color: #4747A5">保存</div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { rules, cities } from '../assets/data/form'
import { mixin } from '../mixins'
import { getUserOfId, updateUserMsg, updateWriterMsg, writerOfWriterId } from '../api/index'

export default {
  name: 'info',
  mixins: [mixin],
  data() {
    return {
      registerForm: {
        username: '', //用户名
        password: '', //密码
        name: '',
        sex: '', //性别
        phoneNum: '', //手机
        email: '', //邮箱
        birth: '', //生日
        introduction: '', //签名
        location: '' //地区
      },
      cities: [], //所有的地区--省
      rules: {}, //表单提交的规则
      imgUrl: this.$store.state.user.avator
    }
  },
  created() {
    this.rules = rules
    this.cities = cities
  },
  computed: {
      imgUrls(){
          return this.imgUrl;
      }
  },
  mounted() {
    this.getMsg(this.$store.state.user.userId)
  },
  methods: {
    getMsg(userId) {
      if (this.$store.state.user.userType == 'WRITER') {
        writerOfWriterId(userId)
          .then((res) => {
            this.registerForm.username = res.username
            this.registerForm.password = res.password
            this.registerForm.name = res.name
            this.registerForm.sex = res.sex
            this.registerForm.phoneNum = res.phoneNum
            this.registerForm.email = res.email
            this.registerForm.birth = res.birth
            this.registerForm.introduction = res.introduction
            this.registerForm.location = res.location
          })
          .catch((err) => {
            console.log(err)
          })
      } else {
        getUserOfId(userId)
          .then((res) => {
            this.registerForm.username = res.username
            this.registerForm.password = res.password
            this.registerForm.sex = res.sex
            this.registerForm.phoneNum = res.phoneNum
            this.registerForm.email = res.email
            this.registerForm.birth = res.birth
            this.registerForm.introduction = res.introduction
            this.registerForm.location = res.location
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    saveMsg() {
      let _this = this
      let d = new Date(this.registerForm.birth)
      let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
      let params = new URLSearchParams()
      params.append('id', this.$store.state.user.userId)
      params.append('username', this.registerForm.username)
      params.append('password', this.registerForm.password)
      params.append('name', this.registerForm.name)
      params.append('sex', this.registerForm.sex)
      params.append('phoneNum', this.registerForm.phoneNum)
      params.append('email', this.registerForm.email)
      params.append('birth', datetime)
      params.append('introduction', this.registerForm.introduction)
      params.append('location', this.registerForm.location)
      if (this.$store.state.user.userType == 'WRITER') {
        updateWriterMsg(params)
          .then((res) => {
            if (res.code == 1) {
              _this.$store.commit('setUsername', this.registerForm.username)
              _this.notify('修改成功', 'success')
            } else {
              _this.notify('修改失败', 'error')
            }
          })
          .catch((err) => {
            _this.notify('修改失败', 'error')
          })
      } else {
        updateUserMsg(params)
          .then((res) => {
            if (res.code == 1) {
              _this.$store.commit('setUsername', this.registerForm.username)
              _this.notify('修改成功', 'success')
            } else {
              _this.notify('修改失败', 'error')
            }
          })
          .catch((err) => {
            _this.notify('修改失败', 'error')
          })
      }
    },
    //上传地址
    uploadUrl() {
      if (this.$store.state.user.userType == 'WRITER') {
        return `${this.$store.state.configure.HOST}/writer/updateWriterPic?id=${this.$store.state.user.userId}`
      } else {
        return `${this.$store.state.configure.HOST}/consumer/updateConsumerPic?id=${this.$store.state.user.userId}`
      }
    },
    //上传成功
    handleAvatorSuccess(res, file) {
      if (res.code == 1) {
        this.$store.commit('setAvator', res.avator || res.pic)
        this.imgUrl = res.avator || res.pic
        this.notify('修改成功', 'success')
      } else {
        this.notify('修改失败', 'error')
      }
    },
    //上传之前的校验
    beforeAvatorUpload(file) {
      const isJPG = file.type == 'image/jpeg'
      const isLt10M = file.size / 1024 / 1024 < 10
      if (!isJPG) {
        this.notify('上传头像图片只能是JPG格式', 'error')
        return false
      }
      if (!isLt10M) {
        this.notify('上传头像图片不能大于10MB', 'error')
        return false
      }
      return true
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/info.scss';
</style>
