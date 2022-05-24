<template>
    <div class="the-footer">
        <p><span>关于我们</span> | <span>用户指南</span> | <span @click="goAgreement">服务协议</span> | <span>联系我们</span> | <span>加入我们</span></p>
        <p>Copyright ©2022 悦·读 </p>
    </div>
</template>
<script>
export default {
    name: 'the-footer',
    methods: {
        goAgreement(){
            this.$router.push({ path: `/agreement`});
        }
    },
}
</script>
<style lang="scss" scoped>
@import '../assets/css/the-footer.scss';
</style>