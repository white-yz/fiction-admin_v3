<template>
    <div class="big" style="margin:130px 0;">
        <div class="signUp">
            <div class="logo"><img src="../assets/img/logoIP.png" alt=""></div>
            <div class="signUp-head">
                <span>登录</span>
            </div>
            <el-form :model="loginForm" ref="loginForm" label-width="70px" class="demo-ruleForm" :rules="rules">
                <el-form-item prop="username" label="用户名">
                    <el-input v-model="loginForm.username" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码">
                    <el-input type="password" v-model="loginForm.password" placeholder="密码"></el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button style="width: 100%; background-color: #4747A5" type="primary" @click="handleLoginIn">登录</el-button>
                </div>
                <a class="goLogin" @click="goPage('/sign-up','注册')">还没账号？立即注册</a>
            </el-form>
        </div>
    </div>
</template>
<script>
import { mixin } from '../mixins'
import { loginIn } from '../api/index'

export default {
    name: 'login-in',
    mixins: [mixin],
    data() {
        return {
            loginForm: {
                username: '', //用户名
                password: '' //密码
            },
            rules: {
                username: [{ required: true, trigger: 'blur', message: '请输入用户名' }],
                password: [{ required: true, trigger: 'blur', message: '请输入密码' }]
            }
        }
    },
    mounted() {
        this.changeIndex('登录')
    },
    methods: {
        handleLoginIn() {
            let _this = this
            let params = new URLSearchParams()
            params.append('username', this.loginForm.username)
            params.append('password', this.loginForm.password)
            loginIn(params)
                .then((res) => {
                    if (res.code == 1) {
                        console.log(res)
                        _this.$message({
                            message: '登录成功',
                            type: 'success'
                        })
                        _this.$store.commit('setLoginIn', true)
                        _this.$store.commit('setUserId', res.userMsg.id)
                        _this.$store.commit('setUsername', res.userMsg.username)
                        _this.$store.commit('setVip', res.userMsg.vip)
                        _this.$store.commit('setAvator', res.userMsg.avator || res.userMsg.pic)
                        _this.$store.commit('setUserType', res.userType)
                        setTimeout(function () {
                            _this.changeIndex('首页')
                            _this.$router.push({ path: '/' })
                        }, 2000)
                    } else if (res.code == -1) {
                        this.$notify({
                            showClose: true,
                            title: '警告',
                            message: res.msg,
                            type: 'warning',
                            duration: 6000
                        })
                        // this.$message({
                        //     showClose: true,
                        //     message: res.msg,,
                        //     type: 'warning',
                        //     duration: 8000
                        // })
                    } else {
                        _this.$message.error('用户名或密码错误')
                    }
                })
                .catch((err) => {
                    _this.$message.error('用户名或密码错误')
                })
        },
        goPage(path, name) {
            this.$store.commit('setActiveName', name)
            this.$router.push({ path: path })
        },
        changeIndex(value) {
            this.$store.commit('setActiveName', value)
        }
    }
}
</script>

<style scoped >
.big {
    background-image: url('1.jpg');
    height: 430px;
}
.signUp {
    position: relative;
    background-color: #ffffff;
    border-radius: 10px;
    width: 350px;
    padding: 30px 30px;
    margin: 65px auto;
}
.signUp .logo {
    position: absolute;
    left: 170px;
    top: -35px;
    /* background: #fff; */
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
