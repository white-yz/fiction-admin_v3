//读者导航栏
const navMsg = [
    {name: '首页',path: '/'},
    {name: '书库',path: '/book-list'},
    {name: 'VIP专区',path: '/vip-book-list'},
    {name: '作家',path: '/writer'},
    {name: '我的书架',path: '/setting?nav=MyBook&activeName=我的书架'}
]
//作者导航栏
const writerNavMsg = [
    {name: '首页',path: '/'},
    {name: '书库',path: '/book-list'},
    {name: 'VIP专区',path: '/vip-book-list'},
    {name: '作家',path: '/writer'},
    {name: '我的作品',path: '/setting?nav=MyBook&activeName=我的作品'}
]
//游客导航栏
const loginMsg = [
    {name: '登录',path: '/login-in'},
    {name: '注册',path: '/sign-up'}
]

//用户下拉菜单
const menuList = [
    {name: '个人中心',path: '/setting?nav=Info&activeName=基本信息'},
    {name: '退出',path: 0}
]


export {
    navMsg,
    loginMsg,
    menuList,
    writerNavMsg
}