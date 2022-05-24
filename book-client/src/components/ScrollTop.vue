/* 返回顶部 */
<template>
    <p id="back-to-top">
        <a href="javascrpt:;" title="返回顶部">
            &#8673;
        </a>
    </p>
</template>
<script>
export default {
    name: 'scroll-top',
    // methods: {
    //     returnTop() {
    //         document.documentElement.scrollTop = document.body.scrollTop = 0;
    //     }
    // }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/scroll-top.scss';
</style>