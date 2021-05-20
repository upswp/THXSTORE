<template>
  <div class="live-container">
    <div class="reAccessLine">
      <span class="reAccessButton" @click="joinSession">재접속하기</span>
      <span v-if="session" class="leaveLiveButton" @click="leaveSession">방송 종료</span>
    </div>
    <div v-if="session" id="session">
      <header class="session-header">
        <div>라이브 방송 중</div>
      </header>
      <section class="session-body">
        <div id="main-video" class="main-video">
          <user-video :stream-manager="mainStreamManager" />
        </div>
        <div class="chat-box">
          <div class="chat-display">
            <div v-for="(chat, index) in chats" :key="index" class="chat-line">
              <div v-if="chat.userId === getUserInfo.id" class="my-comment">
                <img :src="chat.profileImage" class="user-profile" />
                <div>
                  <span class="participant-name">{{ chat.nickname }} </span><span class="chat-msg">{{ chat.msg }}</span>
                </div>
              </div>
              <div v-else class="other-comment">
                <img :src="chat.profileImage" class="user-profile" />
                <div>
                  <span class="participant-name other">{{ chat.nickname }} </span
                  ><span class="chat-msg">{{ chat.msg }}</span>
                </div>
              </div>
            </div>
          </div>
          <div class="msg-wrapper">
            <div class="msg-guide">
              <img :src="getUserInfo.profileImage" class="user-profile" />
              {{ getUserInfo.nickname }}
            </div>
            <input
              v-model="sendMsg"
              type="text"
              class="msg-input"
              placeholder="메세지를 입력해주세요"
              @keydown.enter="submitMsg"
            />
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { OpenVidu } from 'openvidu-browser';
import UserVideo from '@/components/live/UserVideo';
import axios from 'axios';

const OPENVIDU_SERVER_URL = 'https://k4b2021.p.ssafy.io:9000';
const OPENVIDU_SERVER_SECRET = 'MY_SECRET';

const instance = axios.create({
  baseURL: OPENVIDU_SERVER_URL,
  headers: { 'Content-Type': 'application/json' },
});
export default {
  components: {
    UserVideo,
  },
  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      mySessionId: '',
      myUserName: '',
      chats: [],
      sendMsg: '',
    };
  },
  computed: {
    ...mapGetters(['getStoreId', 'getUserInfo']),
  },
  created() {
    this.mySessionId = `Session${this.getStoreId}`;
    this.myUserName = `User${this.getUserInfo.id}`;
    this.joinSession();
  },
  methods: {
    submitMsg() {
      const sendData = {
        userId: this.getUserInfo.id,
        profileImage: this.getUserInfo.profileImage,
        nickname: this.getUserInfo.nickname,
        msg: this.sendMsg,
      };
      this.sendMsg = '';
      this.session
        .signal({
          data: JSON.stringify(sendData), // Any string (optional)
          to: [], // Array of Connection objects (optional. Broadcast to everyone if empty)
          type: 'my-chat', // The type of message (optional)
        })
        .then(() => {
          console.log('Message successfully sent');
        })
        .catch(error => {
          console.error(error);
        });
    },
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---
      this.session.on('signal:my-chat', event => {
        this.chats.push(JSON.parse(event.data));
      });
      // On every asynchronous exception...
      this.session.on('exception', ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then(token => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: '640x480', // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;

            this.session.publish(this.mainStreamManager);
          })
          .catch(error => {
            console.log('There was an error connecting to the session:', error.code, error.message);
          });
      });

      window.addEventListener('beforeunload', this.leaveSession);
    },
    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.OV = undefined;

      window.removeEventListener('beforeunload', this.leaveSession);
    },
    getToken(mySessionId) {
      return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
    },
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        instance
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: 'OPENVIDUAPP',
                password: OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then(response => response.data)
          .then(data => resolve(data.id))
          .catch(error => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`,
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`,
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        instance
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: 'OPENVIDUAPP',
                password: OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then(response => response.data)
          .then(data => resolve(data.token))
          .catch(error => reject(error.response));
      });
    },
  },
};
</script>

<style lang="scss" scoped>
@mixin fit-pc {
  @media only screen and (min-width: 1300px) {
    @content;
  }
}
.live-container {
  width: 100%;
  @include lg-pc {
    font-size: 16px;
  }
  @include pc {
    font-size: 15px;
  }
  @include mobile {
    font-size: 14px;
  }
  @include xs-mobile {
    font-size: 13px;
  }
  position: relative;
}
.reAccessLine {
  @include flexbox;
  @include justify-content(flex-end);
  margin-bottom: 10px;
}
.reAccessButton {
  top: 0px;
  right: 130px;
  cursor: pointer;
  display: inline-block;
  color: white;
  background-color: rgb(248, 190, 82);
  &:hover {
    background-color: rgb(255, 166, 0);
  }
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  padding: 10px;
  border-radius: 5px;
  @include lg-pc {
    font-size: 16px;
  }
  @include pc {
    font-size: 14px;
  }
  @include mobile {
    font-size: 12px;
  }
  @include xs-mobile {
    font-size: 10px;
  }
}
.session-header {
  font-weight: bold;
  padding: 10px;
  @include lg-pc {
    font-size: 22px;
  }
  @include pc {
    font-size: 21px;
  }
  @include mobile {
    font-size: 20px;
  }
  @include xs-mobile {
    font-size: 19px;
  }
}
.leaveLiveButton {
  margin-left: 10px;
  top: 0px;
  right: 0px;
  cursor: pointer;
  display: inline-block;
  color: white;
  background-color: rgb(231, 93, 68);
  &:hover {
    background-color: rgb(240, 53, 20);
  }
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  padding: 10px;
  border-radius: 5px;
  @include lg-pc {
    font-size: 16px;
  }
  @include pc {
    font-size: 14px;
  }
  @include mobile {
    font-size: 12px;
  }
  @include xs-mobile {
    font-size: 10px;
  }
}
.session-body {
  border-radius: 5px;
  border: 1px solid $gray400;
  padding: 10px;

  @include fit-pc {
    @include flexbox;
  }
}
.main-video {
  flex-basis: 50%;
  @include flexbox;
  @include justify-content(center);
  @include align-items(center);
  margin-bottom: 10px;
  @include fit-pc {
    margin-bottom: 0px;
  }
}
.chat-box {
  position: relative;
  flex-basis: 50%;
  @include mobile {
    width: 100%;
  }
  @include xs-mobile {
    width: 100%;
  }
  border-radius: 5px;
  border: 1px solid $gray400;
  background-color: white;
}
.chat-display {
  width: 100%;
  height: calc(500px - 100px);
  background-color: $gray100;
  overflow: auto;

  padding: 10px;
}
.my-comment,
.other-comment {
  padding: 5px;
  @include flexbox;
  @include align-items(center);
}
.user-profile {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  object-fit: cover;
  object-position: center 50%;
  margin-right: 10px;
  vertical-align: middle;
}
.participant-name {
  color: $purple800;
  &.other {
    color: $gray600;
  }
  margin-right: 10px;
}
.chat-msg {
  color: rgba(0, 0, 0, 0.8);
}
.msg-input {
  border: none;
  border-bottom: 1px solid $gray800;
  width: 100%;
}
.msg-wrapper {
  background-color: white;
  position: absolute;
  bottom: 0px;
  width: calc(100%);
  padding: 5px 15px;
}
.msg-guide {
  @include flexbox;
  @include align-items(center);
  margin-bottom: 10px;
}
</style>
