<template>
    <div :class="['header',isDark?'dark':'']">
        <div class="logo">
            <img style="height:50px;margin-top: 5px;" src="../assets/img/logoIP.png" alt="">
            <span style="vertical-align: top;">{{$t('header.manageName')}}</span>
        </div>
        <!-- 折叠 -->
        <div class="collapse-btn" @click="collapseChange">
            <i class="icon-shousuohou iconfont" style="font-size:20px;"></i>
        </div>
        <div class="header-right">
            <!-- 全屏 -->
            <div class="btn-fullscreen" @click="handleFullScreen">
                <el-tooltip :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                    <i class="icon-pingtaiiconhuizong_huaban1fuben9 iconfont" style="font-size: 20px;"></i>
                </el-tooltip>
            </div>
            <!-- 管理员头像 -->
            <div class="user-avator">
                <img src="../assets/img/user.jpg"/>
            </div>
            <!-- 用户名&退出 -->
            <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                <span class="el-dropdown-link">
                    {{userName}}
                    <i class="el-icon-caret-bottom"></i>
                </span>
                <el-dropdown-menu slot="dropdown" >
                    <el-dropdown-item command="logout">{{$t('header.signOut')}}</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <!-- 国际化 -->
            <div class="langDiv" style="height: 70px;margin-left:10px; font-size:14px;position: relative;">
                <i class="icon-yuyan iconfont" style="margin-top: 25px; font-size: 17px; display: inline-block;"></i>
                <span style="float: right;margin-left: 5px;line-height: 70px;">{{ Lang }}</span>
                <ul class="langUl">
                    <li v-for="item in langList" @click="setLang(item)"  class="langLi">{{ item.name }}</li>
                    <div id="popper__arrow" style="left: 62.4844px;"></div>
                </ul>
            </div>
            <!-- 主题 -->
            <el-switch style="margin-left:10px;" v-model="value" @change="changTheme" active-color="#4747A5" inactive-color="#D55969"></el-switch>
        </div>
    </div>
</template>

<script>
import bus from "../assets/js/bus";
export default {
    data(){
        return{
            collapse: false,//默认展开
            fullscreen:false,//默认非全屏
            Lang: '简体',//默认简体中文
            isDark: false,//默认明亮色
            value: false,
            langList: [
                {key: 'CN', name: '简体中文', alias: '简体'},
                {key: 'HK', name: '繁體中文', alias: '繁體'},
                {key: 'US', name: 'English', alias: 'English'},
            ],
        }
    },
    computed:{
        userName(){
            return localStorage.getItem('userName');//获取本地对话中的管理员姓名
        },
    },
    methods:{
        //侧边栏折叠
        collapseChange(){
            this.collapse = !this.collapse;
            bus.$emit('collapse',this.collapse);
        },
        //全屏事件
        handleFullScreen(){
            if(this.fullscreen){
                if(document.exitFullscreen){
                   document.exitFullscreen();
                }else if(document.webkitCancelFullScreen){      //safari 、Chrome
                    document.webkitCancelFullScreen();
                }else if (document.mozCancelFullScreen){        //firefox
                    document.mozCancelFullScreen();
                }else if(document.msExitFullScreen){            //ie
                    document.msExitFullScreen();
                }
            }else{
                let element = document.documentElement;
                if(element.requestFullscreen){
                    element.requestFullscreen();
                }else if(element.webkitRequestFullScreen){      //safari 、Chrome
                    element.webkitRequestFullScreen();
                }else if(element.mozRequestFullScreen){         //firefox
                    element.mozRequestFullScreen();
                }else if (element.msRequestFullScreen){         //ie
                    element.msRequestFullScreen();
                }
            }
            this.fullscreen = !this.fullscreen;
        },
        //退出登录
        handleCommand(command){
            if(command == "logout"){
                localStorage.removeItem('userName');
                this.$router.push("/");
            }
        },
        //切换语言
        setLang(item){
            this.Lang = item.alias;
            this.$i18n.locale = item.key;
        },
        //切换主题
        changTheme(){
            this.isDark = !this.isDark;
            bus.$emit('isDark',this.isDark);
        }
    }
}
</script>

<style scoped>
.header{
    position: relative;
    background-color: #fff;
    box-sizing: border-box;
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #595959;
    box-shadow: 0 2px 8px rgba(0,0,0, 0.15);
    z-index: 2;
    transition: border-color .3s,background-color .3s,color .3s;
}

.dark{
    background: #141414;
    color: #909399;;
}

.dark span{
    color: #909399;
}
.dark .icon-shousuohou:hover{
    color: #D55969;
}

.collapse-btn{
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
}

.header .logo{
    float: left;
    line-height: 70px;
    margin-left: 20px;
}

.header-right{
    float: right;
    padding-right: 50px;
    display:flex;
    height: 70px;
    align-items: center;
}

.btn-fullscreen{
    margin-right: 5px;
    font-size: 24px;
}

.user-avator{
    margin-left: 20px;
}

.user-avator img{
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}

.user-name{
    margin-left: 10px;
}

.el-dropdown-link{
    color: #595959;
    cursor: pointer;
}

.langDiv{
    cursor:pointer;
}

.langDiv:hover .langUl{
    display: block;

}

.langUl{
    display: none;
    color: #333;
    position: absolute;
    background: #fff;
    width: 100px;
    top: 54px;
    left: -40px;
    text-align: left;
    z-index: 1;
    padding: 10px 0;
    border: 1px solid #ebeef5;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}
.dark .langUl{
    background: #1F1F1F;
    color: #909399;
    border: 1px solid #303030;
}
.langLi{
    list-style: none;
    padding: 0 20px;
    line-height: 36px;
}

.langLi:hover{
    background-color: #e2effe;
    color: #4747A5;
}

.dark .langLi:hover{
    background-color: bla;
    color: #fff;
}

#popper__arrow::after{
    position: absolute;
    display: block;
    width: 0;
    height: 0;
    border-color: transparent;
    border-style: solid;
    content: " ";
    border-width: 6px;
    top: -6px;
    left: 70px;
    margin-left: -6px;
    border-top-width: 0;
    border-bottom-color: #fff;
}
.dark #popper__arrow::after{
    border-bottom-color: #303030;
}
</style>