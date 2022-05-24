/* 搜索结果页面 */
<template>
    <div class="search">
        <nav class="searchList-nav" ref="change">
            <span :class="{isActive: toggle=='Books'}" @click="handleChangeView('Books')">书籍</span>
            <span :class="{isActive: toggle=='Writers'}" @click="handleChangeView('Writers')">作家</span>
        </nav>
        <component :is="currentView"></component>
    </div>
</template>
<script>
import searchBooks from '../components/search/SearchBooks'
import searchWriters from '../components/search/SearchWriters'
export default {
    name: 'search',
    components: {
       searchBooks,
       searchWriters
   },
   data(){
       return {
           toggle: 'Books',
           currentView: 'searchBooks'
       }
   },
   methods:{
       //切换组件
       handleChangeView(component){
           this.currentView = 'search'+component;
           this.toggle = component;
       }
   }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/search.scss';
</style>