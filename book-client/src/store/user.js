const user = {
    state:{
        userId: '',                 //用户id
        username: '',               //用户账号
        avator: '',               //用户头像
        userType: '',               // 用户类型
        vip: null
    },
    getters: {
        userId: state => {
            let userId = state.userId
            if(!userId){
                userId = JSON.parse(window.sessionStorage.getItem('userId')) 
            }
            return userId
        },
        username: state => {
            let username = state.username
            if(!username){
                username = JSON.parse(window.sessionStorage.getItem('username')) 
            }
            return username
        },
        avator: state => {
            let avator = state.avator
            let avators = window.sessionStorage.getItem('avator')
            if(!avator && avators != 'undefined'){
                avator = JSON.parse(window.sessionStorage.getItem('avator')) 
            }
            return avator
        },
        userType: state => {
            let userType = state.userType
            if(!userType && window.sessionStorage.getItem('userType')){
                userType = window.sessionStorage.getItem('userType')
            }
            return userType
        },
        vip: state => {
            let vip = state.vip
            if(!vip && window.sessionStorage.getItem('vip')){
                vip = window.sessionStorage.getItem('vip')
            }
            return vip
        }
        
    },
    mutations: {
        setUserId: (state,userId) => {
            state.userId = userId
            window.sessionStorage.setItem('userId',JSON.stringify(userId))
        },
        setUsername: (state,username) => {
            state.username = username
            window.sessionStorage.setItem('username',JSON.stringify(username))
        },
        setAvator: (state,avator) => {
            state.avator = avator
            window.sessionStorage.setItem('avator',JSON.stringify(avator))
        },
        setUserType: (state, userType) => {
            state.userType = userType
            if(userType)
            window.sessionStorage.setItem('userType', userType)
        },
        setVip: (state, vip) => {
            state.vip = vip
            if(vip)
            window.sessionStorage.setItem('vip', vip)
        }
    }
}

export default user