<template>
    <div :class="['sidebar',isDark?'dark':'']">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#334256"
            text-color="#ffffff"
            active-text-color="#4747A5 !important"
            router
         >
            <template v-for="item in items">
                <template>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span>{{$t(item.title)}}</span>
                    </el-menu-item>
                </template>
            </template>

        </el-menu>
    </div>
</template>

<script>
import bus from "../assets/js/bus";
export default {
    data(){
        return{
            collapse: false,
            isDark: false,
            items:[
                {
                    icon: 'icon-shouye1 iconfont',
                    index: 'Info',
                    title: 'leftNav.Info'
                },
                {
                    icon: 'icon-yonghu iconfont',
                    index: 'Consumer',
                    title: 'leftNav.Consumer'
                },
                {
                    icon: 'icon-zuozhe iconfont',
                    index: 'Writer',
                    title: 'leftNav.Writer'
                }
            ]
        }
    },
    computed:{
        onRoutes(){
            return this.$route.path.replace('/','');
        }
    },
    created(){
        //通过Bus进行组件间的通信，来折叠侧边栏
        bus.$on('collapse',msg =>{
            this.collapse = msg
        }),
        bus.$on('isDark',msg =>{
            this.isDark = msg
        })
    }
}
</script>

<style scoped>
.dark.sidebar {
    border-right: none;
}
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    background: #fff;
    overflow-y: scroll;
    z-index: 1;
    border-right: 3px #4747A5 solid;
}

.dark ul,.dark ul li{
    background: #141414 !important;
    color: #afb3b9;
}

.dark .sidebar-el-menu li:hover{
    background: #141414 !important;
}

.dark .sidebar-el-menu li:hover span,
.dark .sidebar-el-menu li:hover i{
    color: rgb(254,254,254, .9) !important;
}

.sidebar::-webkit-scrollbar{
    width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse){
    width: 150px;
}

.sidebar >ul {
    height: 100%;
    transition: border-color .3s,background-color .3s,color .3s;
}

.sidebar-el-menu{
    background: #fff !important;
}

.sidebar-el-menu li {
    color: #555 !important;
    background: #fff !important;
    font-size: 15px;
}
.sidebar-el-menu li:hover{
    background: #eaeafb !important;
}
.sidebar-el-menu li:hover span,
.sidebar-el-menu li:hover i{
    color:#4747A5 !important;
}
.iconfont{
    font-size: 20px;
    margin-right: 5px;
}

</style>