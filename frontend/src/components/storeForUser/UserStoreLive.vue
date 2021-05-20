<template>
  <div class="userstore-live-container">
    <div v-if="session" id="session">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input id="buttonLeaveSession" type="button" value="Leave session" @click="leaveSession" />
      </div>
      <div id="main-video">
        <user-video :stream-manager="mainStreamManager" />
      </div>
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
      storeId: this.$route.params.storeId,
      subscribers: [],
    };
  },
  computed: {
    ...mapGetters(['getUserInfo']),
  },
  created() {
    this.mySessionId = `Session${this.storeId}`;
    this.myUserName = `User${this.getUserInfo.id}`;
    this.joinSession();
  },
  methods: {
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on('streamCreated', ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
        this.mainStreamManager = this.subscribers[0];
      });

      // On every Stream destroyed...
      this.session.on('streamDestroyed', ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
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
          .then(() => {})
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
.userstore-live-container {
  width: 100%;
}
</style>
