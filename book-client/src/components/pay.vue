<template>
  <div class="content">
    <el-dialog title="成为VIP" :modal="false" :visible.sync="payVsiable" width="30%" height="30%" :before-close="cancel">
      <div :span="24" class="txst">
        本站终身VIP: 98元
      </div>
      <!-- <el-row>
        <el-col :span="24" class="code">
          <img :src="QRCODE"> -->
          <!-- <div id="qrcode" ref="qrcode" class="qrcode" /> -->
        <!-- </el-col>
      </el-row> -->
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handlePay">点击购买</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { updateUserVip, updateWriterVip, payOrder } from '../api/index'
import { qr } from '../mixins/qr'
import QRCODE from '../pages/qrCode/qrCode3.png'
export default {
  mixins: [qr],
  props: {
    payVsiable: Boolean
  },
  data() {
    return {
      dialogVisible: false,
      QRCODE
    }
  },
  methods: {
    handlePay(row) {
      window.location.href= payOrder()
    //   let params = new URLSearchParams()
    //   params.append('id', this.$store.state.user.userId)
    //   params.append('vip', true)
    //   if (this.$store.state.user.userType == 'WRITER') {
    //     updateWriterVip(params).then((res) => {
    //       if (res) {
    //         this.$emit('update:payVsiable', false)
    //         this.$message({
    //           message: '操作成功',
    //           showClose: true,
    //           type: 'success'
    //         })
    //         this.$store.commit('setVip', true)
    //         // this.cleanCode()
    //       }
    //     })
    //   } else {
    //     updateUserVip(params).then((res) => {
    //       if (res) {
    //         this.$emit('update:payVsiable', false)
    //         this.$message({
    //           message: '操作成功',
    //           showClose: true,
    //           type: 'success'
    //         })
    //         this.$store.commit('setVip', true)
    //         // this.cleanCode()
    //       }
    //     })
    //   }
    },
    cancel() {
      this.$emit('update:payVsiable', false)
      //  this.cleanCode()
    }
  }
}
</script>
<style scoped>
.qrcode {
  width: 100%;
  height: 100%;
}
.qrcode >>> img {
  width: 70%;
  height: 70%;
  text-align: center !important;
}
img {
  width: 100%;
  height: 100%;
}
.dialog {
  z-index: 8000;
}
.txst {
  text-align: center;
  margin-top: -50px;
  color: red;
  font-size: 20pt;
}
.code {
  text-align: center;
  margin-bottom: -70px;
}
</style>