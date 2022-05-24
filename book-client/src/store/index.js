import Vue from 'vue'
import Vuex from 'vuex'
import configure from './configure'
import user from './user'
import book from './book'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        configure,
        user,
        book
    },
    state:{
        HOST: 'http://127.0.0.1:8888'
    }
})

export default store