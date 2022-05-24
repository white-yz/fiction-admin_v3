<template>
    <div>
        <the-header></the-header>
        <the-aside></the-aside>
        <div class="content-box" style="padding: 20px;"  :class="{'content-collapse':collapse,'dark':isDark}">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import TheHeader from './TheHeader';
import TheAside from './TheAside';
import bus from "../assets/js/bus";

export default {
    components:{
        TheHeader,
        TheAside
    },
    data(){
        return {
            collapse: false,
            isDark: false,
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
<style >
.el-table{
    border-radius: 5px;
}
.dark .el-table{
    background-color: #141414;
}
.dark{
    background: #000;
}
.dark .container{
    background: #1d1d1d;
    border-color: #303030;
}
.dark .el-table th{
    background-color: #1d1d1d;
}
.dark .el-table th.is-leaf,
.dark .el-table td{
    border-color: #303030;
}
.dark .el-table tr{
    background: #141414;
}
.dark .el-table--enable-row-hover .el-table__body tr:hover>td {
    background: #112123;
}
.dark .el-dialog{
    background: #000;
}
</style>