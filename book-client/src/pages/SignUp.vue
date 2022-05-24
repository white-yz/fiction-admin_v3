/* 注册 */
<template>
    <div class="zhuce" style="margin:130px 0;">
        <div class="signUp">
            <div class="logo"><img src="../assets/img/logoIP.png" alt=""></div>
            <div class="signUp-head">
                <span>注册</span>
            </div>
            <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
                <el-form-item prop="type" label="用户类型">
                    <el-radio-group v-model="registerForm.type" @change="handleType">
                        <el-radio :label="0">读者</el-radio>
                        <el-radio :label="1">作家</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="username" label="用户名">
                    <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input type="password" v-model="registerForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item v-if="registerForm.type==1" prop="name" label="作家姓名">
                    <el-input v-model="registerForm.name" placeholder="作家"></el-input>
                </el-form-item>
                <el-form-item prop="sex" label="性别">
                    <el-radio-group v-model="registerForm.sex">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item prop="phoneNum" label="手机">
                    <el-input v-model="registerForm.phoneNum" placeholder="手机"></el-input>
                </el-form-item>
                <el-form-item v-if="registerForm.type==0" prop="email" label="邮箱">
                    <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="birth" label="生日">
                    <el-date-picker v-model="registerForm.birth" placeholder="选择日期" style="width: 100%;"></el-date-picker>
                </el-form-item>
                <el-form-item v-if="registerForm.type==0" prop="introduction" label="签名">
                    <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
                </el-form-item>
                <el-form-item prop="location" label="地区">
                    <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%;">
                        <el-option v-for=" item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item v-if="registerForm.type==1" prop="intro" label="简介">
                    <el-input v-model="registerForm.introduction" type="textarea" :rows="2"></el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button style="width: 100%; background-color: #4747A5" type="primary" @click="SignUp">确定</el-button>
                </div>
                <a class="goLogin" @click="goPage('/login-in','登录')">已有账号？直接登录</a>
            </el-form>

        </div>
    </div>
</template>
<script>
import { rules, cities } from '../assets/data/form'
import { mixin } from '../mixins'
import { SignUp, authorSignUp } from '../api/index'

export default {
    name: 'sing-up',
    mixins: [mixin],
    data() {
        return {
            registerForm: {
                username: '', //用户名
                password: '', //密码
                type: 0, //密码
                sex: '', //性别
                state: 0,
                phoneNum: '', //手机
                email: '', //邮箱
                birth: '', //生日
                introduction: '', //签名
                location: '' //地区
            },
            cities: [], //所有的地区--省
            rules: {} //表单提交的规则
        }
    },
    created() {
        this.rules = rules
        this.cities = cities
    },
    methods: {
        SignUp() {
            let _this = this
            let d = this.registerForm.birth
            let datetime = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
            let params = new URLSearchParams()
            params.append('username', this.registerForm.username)
            params.append('password', this.registerForm.password)
            params.append('sex', this.registerForm.sex)
            params.append('phoneNum', this.registerForm.phoneNum)
            params.append('email', this.registerForm.email)
            params.append('birth', datetime)
            params.append('introduction', this.registerForm.introduction)
            params.append('location', this.registerForm.location)
            params.append('name', this.registerForm.name)
            params.append('avator', '/img/user.jpg')
            params.append('pic', '/img/user.jpg')
            if (this.registerForm.type == 0) {
                SignUp(params)
                    .then((res) => {
                        if (res.code == 1) {
                            _this.$message({
                                message: '注册成功',
                                type: 'success'
                            })
                            setTimeout(function () {
                                _this.$router.push({ path: '/login-in' })
                                _this.$store.commit('setActiveName', '登录')
                            }, 2000)
                        } else {
                            _this.$message.error('注册失败')
                        }
                    })
                    .catch((err) => {
                        _this.$message.error('注册失败')
                    })
            } else {
                authorSignUp(params)
                    .then((res) => {
                        if (res.code == 1) {
                            _this.$message({
                                message: '注册成功',
                                type: 'success'
                            })
                            setTimeout(function () {
                                _this.$router.push({ path: '/login-in' })
                                _this.$store.commit('setActiveName', '登录')
                            }, 2000)
                        } else {
                            _this.$message.error('注册失败')
                        }
                    })
                    .catch((err) => {
                        _this.$message.error('注册失败')
                    })
            }
        },
        goPage(path, name) {
            this.$store.commit('setActiveName', name)
            this.$router.push({ path: path })
        },
        handleType(val) {
            this.registerForm = {
                username: '', //用户名
                password: '', //密码
                type: val, //密码
                sex: '', //性别
                phoneNum: '', //手机
                email: '', //邮箱
                birth: '', //生日
                introduction: '', //签名
                location: '',
                state: 0
            }
        }
    }
}
</script>

<style scoped>
.zhuce {
    background-image: url('3.jpg');
}
.signUp {
    position: relative;
    background-color: #fff;
    border-radius: 10px;
    width: 350px;
    padding: 30px 30px;
    margin: 0 auto;
}
.signUp .logo {
    position: absolute;
    left: 170px;
    top: -35px;
    background: #fff;
    width: 70px;
    height: 70px;
    border-radius: 50%;
}
.signUp .logo img {
    width: 90px;
    margin: -18px -9px;
}
.signUp .signUp-head {
    margin: 10px 0;
    text-align: center;
    font-size: 26px;
    font-weight: bold;
    color: #4747A5;
}
.goLogin {
    margin-top: 5px;
    float: right;
    cursor: pointer;
}
.goLogin:hover {
    color: #4747A5;
}
</style>
