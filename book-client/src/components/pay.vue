/* 支付窗口 */
<template>
  <div class="content">
    <el-dialog title="购买VIP" :modal="false" :visible.sync="payVsiable" width="30%" height="30%" :before-close="cancel">
      <div :span="24" class="txst">
        本站终身VIP: 98元
      </div>
      <el-row>
        <el-col :span="24" class="code">
          <img :src="QRCODE">
          <!-- <div id="qrcode" ref="qrcode" class="qrcode" /> -->
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handlePay" style="background-color: #4747A5; border:none">点击我已购买</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { updateUserVip, updateWriterVip } from '../api/index'
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
      let params = new URLSearchParams()
      params.append('id', this.$store.state.user.userId)
      params.append('vip', true)
      if (this.$store.state.user.userType == 'WRITER') {
        updateWriterVip(params).then((res) => {
          if (res) {
            this.$emit('update:payVsiable', false)
            this.$message({
              message: '操作成功',
              showClose: true,
              type: 'success'
            })
            this.$store.commit('setVip', true)
            // this.cleanCode()
          }
        })
      } else {
        updateUserVip(params).then((res) => {
          if (res) {
            this.$emit('update:payVsiable', false)
            this.$message({
              message: '操作成功',
              showClose: true,
              type: 'success'
            })
            this.$store.commit('setVip', true)
            // this.cleanCode()
          }
        })
      }
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
  color: #D55969;
  font-size: 20pt;
}
.code {
  text-align: center;
  margin-bottom: -70px;
}
</style>