<template>
  <a-card :bordered="false" hoverable style="margin-top:  10px;background-color: #f9f9f9">
    <div style="text-align: left;font-size: 14px;margin-bottom: 30px"><b>物业管理系统</b></div>
    <div class="user-layout-register">
      <a-form ref="formRegister" :autoFormCreate="(form)=>{this.form = form}" id="formRegister">
        <a-form-item>
          <a-input type="text" v-model="name" placeholder="姓名"></a-input>
        </a-form-item>
        <a-button
          type="primary"
          @click.stop.prevent="faceViewOpen">人脸认证
        </a-button>
        <a-divider orientation="left"><span style="font-size: 12px">账户注册</span></a-divider>
        <a-form-item
          fieldDecoratorId="email"
          :fieldDecoratorOptions="{rules: [{ required: true, message: '请输入注册账号' },  { validator: this.handleUsernameCheck }], validateTrigger: ['change', 'blur']}">
          <a-input type="text" v-model="username" placeholder="账号"></a-input>
        </a-form-item>
        <a-popover placement="rightTop" trigger="click" :visible="state.passwordLevelChecked">
          <template slot="content">
            <div :style="{ width: '240px' }">
              <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span></div>
              <a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor "/>
              <div style="margin-top: 10px;">
                <span>请至少输入 6 个字符。请不要使用容易被猜到的密码。</span>
              </div>
            </div>
          </template>
          <a-form-item
            fieldDecoratorId="password"
            :fieldDecoratorOptions="{rules: [{ required: true, message: '至少6位密码'}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}">
            <a-input v-model="password" type="password" @click="handlePasswordInputClick" autocomplete="false"
                     placeholder="至少6位密码"></a-input>
          </a-form-item>
        </a-popover>
        <a-form-item
          fieldDecoratorId="password2"
          :fieldDecoratorOptions="{rules: [{ required: true, message: '至少6位密码' }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}">
          <a-input type="password" autocomplete="false" placeholder="确认密码"></a-input>
        </a-form-item>
        <a-form-item>
          <a-button
            size="large"
            type="primary"
            htmlType="submit"
            class="register-button"
            :loading="registerBtn"
            @click.stop.prevent="handleSubmit"
            :disabled="registerBtn">立即注册
          </a-button>
          <a class="login" @click="returnLogin">使用已有账户登录</a>
        </a-form-item>

      </a-form>
    </div>
    <a-modal v-model="faceView.visiable" title="上传人脸照片">
      <template slot="footer">
        <a-button key="back" @click="faceView.visiable = false">
          取消
        </a-button>
      </template>
      <div style="height: 350px">
        <div class="camera_outer">
          <video id="videoCamera" :width="videoWidth" :height="videoHeight" autoplay></video>
          <canvas style="display:none;" id="canvasCamera" :width="videoWidth" :height="videoHeight" ></canvas>
          <div v-if="imgSrc" class="img_bg_camera">
            <img :src="imgSrc" alt="" class="tx_img">
          </div>
          <div style="margin-top: 10px">
            <a-button
              size="small"
              type="primary"
              @click.stop.prevent="getCompetence">打开摄像头
            </a-button>
            <a-button
              size="small"
              type="primary"
              @click.stop.prevent="stopNavigator">关闭摄像头
            </a-button>
            <a-button
              size="small"
              type="primary"
              @click.stop.prevent="setImage">识别
            </a-button>
          </div>
        </div>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
const levelNames = {
  0: '低',
  1: '低',
  2: '中',
  3: '强'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}
export default {
  name: 'Regist',
  components: {},
  data () {
    return {
      name: '',
      idCard: '',
      form: null,
      username: '',
      password: '',
      state: {
        time: 60,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        progressColor: '#FF0000'
      },
      registerBtn: false,
      faceView: {
        visiable: false
      },
      videoWidth: 470,
      videoHeight: 300,
      imgSrc: '',
      thisCancas: null,
      thisContext: null,
      thisVideo: null,
      faceVerification: false
    }
  },
  computed: {
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    }
  },
  methods: {
    getCompetence () {
      var _this = this
      this.thisCancas = document.getElementById('canvasCamera')
      this.thisContext = this.thisCancas.getContext('2d')
      this.thisVideo = document.getElementById('videoCamera')
      // 旧版本浏览器可能根本不支持mediaDevices，我们首先设置一个空对象
      if (navigator.mediaDevices === undefined) {
        navigator.mediaDevices = {}
      }
      // 一些浏览器实现了部分mediaDevices，我们不能只分配一个对象
      // 使用getUserMedia，因为它会覆盖现有的属性。
      // 这里，如果缺少getUserMedia属性，就添加它。
      if (navigator.mediaDevices.getUserMedia === undefined) {
        navigator.mediaDevices.getUserMedia = function (constraints) {
          // 首先获取现存的getUserMedia(如果存在)
          var getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.getUserMedia
          // 有些浏览器不支持，会返回错误信息
          // 保持接口一致
          if (!getUserMedia) {
            return Promise.reject(new Error('getUserMedia is not implemented in this browser'))
          }
          // 否则，使用Promise将调用包装到旧的navigator.getUserMedia
          return new Promise(function (resolve, reject) {
            getUserMedia.call(navigator, constraints, resolve, reject)
          })
        }
      }
      var constraints = { audio: false, video: { width: this.videoWidth, height: this.videoHeight, transform: 'scaleX(-1)' } }
      navigator.mediaDevices.getUserMedia(constraints).then(function (stream) {
        // 旧的浏览器可能没有srcObject
        if ('srcObject' in _this.thisVideo) {
          _this.thisVideo.srcObject = stream
        } else {
          // 避免在新的浏览器中使用它，因为它正在被弃用。
          _this.thisVideo.src = window.URL.createObjectURL(stream)
        }
        _this.thisVideo.onloadedmetadata = function (e) {
          _this.thisVideo.play()
        }
      }).catch(err => {
        console.log(err)
      })
    },
    setImage () {
      var _this = this
      // 点击，canvas画图
      _this.thisContext.drawImage(_this.thisVideo, 0, 0, _this.videoWidth, _this.videoHeight)
      // 获取图片base64链接
      var image = this.thisCancas.toDataURL('image/png')
      let data = { file: image.replace(/^data:image\/\w+;base64,/, ''), name: this.name }
      this.$post('/cos/face/verification', data).then((r) => {
        if (r.data.msg !== '成功') {
          this.$message.error(r.data.msg)
        } else {
          this.$message.success('验证通过')
          setTimeout(() => {
            this.faceVerification = true
            this.faceView.visiable = false
          })
        }
      })
      // _this.imgSrc = image
      // this.$emit('refreshDataList', this.imgSrc)
    },
    dataURLtoFile (dataurl, filename) {
      var arr = dataurl.split(',')
      var mime = arr[0].match(/:(.*?);/)[1]
      var bstr = atob(arr[1])
      var n = bstr.length
      var u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new File([u8arr], filename, { type: mime })
    },
    stopNavigator () {
      this.thisVideo.srcObject.getTracks()[0].stop()
    },
    faceViewOpen () {
      if (this.name !== '') {
        this.faceView.visiable = true
      } else {
        this.$message.warning('请先输入姓名')
      }
    },
    isMobile () {
      return this.$store.state.setting.isMobile
    },
    handlePasswordLevel (rule, value, callback) {
      let level = 0

      // 判断这个字符串中有没有数字
      if (/[0-9]/.test(value)) {
        level++
      }
      // 判断字符串中有没有字母
      if (/[a-zA-Z]/.test(value)) {
        level++
      }
      // 判断字符串中有没有特殊符号
      if (/[^0-9a-zA-Z_]/.test(value)) {
        level++
      }
      this.state.passwordLevel = level
      this.state.percent = level * 30
      if (level >= 2) {
        if (level >= 3) {
          this.state.percent = 100
        }
        callback()
      } else {
        if (level === 0) {
          this.state.percent = 10
        }
        callback(new Error('密码强度不够'))
      }
    },

    handlePasswordCheck (rule, value, callback) {
      let password = this.form.getFieldValue('password')
      if (value === undefined) {
        callback(new Error('请输入密码'))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error('两次密码不一致'))
      }
      callback()
    },

    handleUsernameCheck (rule, value, callback) {
      let username = this.username.trim()
      if (username.length) {
        if (username.length > 10) {
          callback(new Error('用户名不能超过10个字符'))
        } else if (username.length < 4) {
          callback(new Error('用户名不能少于4个字符'))
        } else {
          this.$get(`user/check/${username}`).then((r) => {
            if (r.data) {
              callback()
            } else {
              this.validateStatus = 'error'
              callback(new Error('抱歉，该用户名已存在'))
            }
          })
        }
      } else {
        callback()
      }
    },

    // handlePhoneCheck (rule, value, callback) {
    //   callback()
    // },

    handlePasswordInputClick () {
      if (!this.isMobile()) {
        this.state.passwordLevelChecked = true
        return
      }
      this.state.passwordLevelChecked = false
    },

    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          if (this.faceVerification) {
            this.$post('regist', {
              username: this.username,
              password: this.password,
              name: this.name
            }).then(() => {
              this.$message.success('注册成功')
              this.returnLogin()
            }).catch(() => {
              this.$message.error('抱歉，注册账号失败')
            })
          } else {
            this.$message.warning('请先进行人脸识别')
          }
        }
      })
    },
    // getCaptcha (e) {
    //   e.preventDefault()
    //   let that = this
    //
    //   this.form.validateFields(['mobile'], {force: true},
    //     (err, values) => {
    //       if (!err) {
    //         this.state.smsSendBtn = true
    //
    //         let interval = window.setInterval(() => {
    //           if (that.state.time-- <= 0) {
    //             that.state.time = 60
    //             that.state.smsSendBtn = false
    //             window.clearInterval(interval)
    //           }
    //         }, 1000)
    //       }
    //     }
    //   )
    // },
    returnLogin () {
      this.$emit('regist', 'Login')
    }
  }
}
</script>
<style lang="less">
  .user-register {
    &.error {
      color: #ff0000;
    }
    &.warning {
      color: #ff7e05;
    }
    &.success {
      color: #52c41a;
    }
  }
  .user-layout-register {
    .ant-input-group-addon {
      &:first-child {
        background-color: #fff;
      }
    }
    & > h3 {
      font-size: 16px;
      margin-bottom: 20px;
    }
    .getCaptcha {
      display: block;
      width: 100%;
      height: 40px;
    }
    .register-button {
      width: 50%;
    }
    .login {
      float: right;
      line-height: 40px;
    }
  }
</style>
