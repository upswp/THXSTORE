<template>
  <div class="store-enrollment-container">
    <header><h2>스토어 정보입력</h2></header>
    <div>
      <form @submit.prevent>
        <ul>
          <li>
            <label for="">스토어 이름</label>
            <div class="input-content">
              <input v-model="storeName" type="text" />
            </div>
          </li>
          <li>
            <set-road-name v-if="loaded" @newAddress="setLocationByRoadName">스토어 주소 등록</set-road-name>
            <label for="">스토어 주소</label>
            <div class="input-content">
              <input id="nomalAddress" v-model="nomalAddress" type="text" placeholder="주소" />
              <button id="addressButton" @click="loaded = true">주소 찾기</button>
              <input id="detailAddress" v-model="detailAddress" type="text" placeholder="상세주소" />
            </div>
          </li>
          <li>
            <label for="">전화번호</label>
            <div class="input-content">
              <input
                v-model="phoneNum"
                type="tel"
                name="phone"
                pattern="[0,1]{3}-[0-9]{4}-[0-9]{4}"
                placeholder="010-○○○○-○○○○"
              />
            </div>
          </li>
          <li>
            <label for="">사업자등록 번호</label>
            <div class="input-content">
              <input
                v-model="comResNum"
                type="tel"
                name="phone"
                pattern="[0-9]{3}-[0-9]{2}-[0-9]{5}"
                placeholder="○○○-○○-○○○○○"
              />
            </div>
          </li>
          <li>
            <label for="">사업자등록사본</label>
            <div class="input-content">
              <div class="file-flex">
                <input id="fileName" type="text" class="file_input_textbox" readonly />
                <div class="file_input_div">
                  <label for="file_1">
                    <awesome id="faCloud" ref="cloud" icon="cloud-upload-alt" class="before-upload"></awesome>
                  </label>
                  <input id="file_1" type="file" name="file_1" class="file_input_hidden" @change="insertedFile" />
                </div>
              </div>
            </div>
            <div class="submit-button">
              <button type="submit">
                <b>신청 완료</b>
              </button>
            </div>
          </li>
        </ul>
      </form>
    </div>
  </div>
</template>

<script>
import SetRoadName from '@/components/common/SetRoadName.vue';
import { registerStore } from '@/api/seller';
export default {
  components: {
    SetRoadName,
  },
  data() {
    return {
      storeName: '',
      zip: '',
      nomalAddress: '',
      detailAddress: '',
      phoneNum: '',
      comResNum: '',
      loaded: false,
    };
  },
  methods: {
    async submitForm() {
      try {
        const storeData = {
          storeName: this.storeName,
          nomalAddress: this.nomalAddress,
          detailAddress: this.detailAddress,
          phoneNum: this.phoneNum,
          comResNum: this.comResNum,
        };
        await registerStore(storeData);
      } catch (error) {
        alert('스토어 등록에 문제가 생겼습니다. 다시 시도해주세요.');
      }
    },
    insertedFile(event) {
      const file = event.target.files[0];
      const fileValue = event.target.value;
      document.getElementById('fileName').value = fileValue;
      if (file) {
        this.$refs.cloud.classList.add('after-upload');
      }
    },
    setLocationByRoadName(data) {
      this.loaded = false;
      this.nomalAddress = data;
    },
  },
};
</script>

<style lang="scss" scoped>
.store-enrollment-container {
  padding: 2%;
  width: 100%;

  form {
    background-color: white;
    padding: 2%;
    margin: auto;
    max-width: 1024px;
    box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
  }

  header {
    @include flexbox;
    margin: 0% 5% 2% 5%;
    justify-content: center;
  }

  label {
    @include body1;
    width: 125px;
    margin-left: 2%;
    padding: 3px 5px 7px;
    float: left;
  }
  input {
    display: inline-block;
    width: 100%;
    margin-bottom: 10px;
    background-color: none;
    border: 2.5px solid #dfe1e6;
    padding: 10px 15px;
    border-radius: 3px;
    z-index: 0;
  }
  #nomalAddress {
    width: 60%;
  }
  .input-content {
    padding-left: 25%;
  }

  .file-flex {
    @include flexbox;
    background-color: none;
    // border: 2.5px solid #dfe1e6;
  }

  .file_input_textbox {
    display: inline-block;
    width: 90%;
    margin-bottom: 0px;
    margin-right: 1%;
    padding: 1px 1px;
    background-color: none;
    border: 2.5px solid #dfe1e6;
  }
  .file_input_div {
    position: relative;
    width: 70px;
    height: 36px;
    overflow: hidden;
  }
  .file_input_img_btn {
    padding: 0 0 0 5px;
  }
  .file_input_hidden {
    font-size: 20px;
    padding: 0;
    position: absolute;
    right: 0px;
    top: 0px;
    opacity: 0;
    filter: alpha(opacity=0);
    -ms-filter: alpha(opacity=0);
    cursor: pointer;
  }
  .before-upload {
    color: $blue400;
    font-size: 40px;
    cursor: pointer;
  }
  input::-webkit-file-upload-button {
    cursor: pointer;
  }

  .after-upload {
    transition: color 0.3s;
    color: $blue600;
  }

  #addressButton {
    padding: 0.5em 1em;
    margin: 0.4em 0.15em 0.7em 0.15em;
    border: 1px solid #ccc;
    border-color: #dbdbdb #d2d2d2 #b2b2b2 #d2d2d3;
    cursor: pointer;
    color: #464646;
    border-radius: 0.2em;
    vertical-align: middle;
    font-size: 1em;
    line-height: 1.25em;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#f2f2f2));
  }

  .submit-button {
    @include flexbox;
    @include justify-content(flex-end);
    button {
      @include box-shadow;
      background-color: $blue400;
      width: 30%;
      margin-top: 20px;
      color: $white;
      border: none;
      &:hover:enabled {
        background-color: $blue600;
      }
    }
  }
  @include mobile {
    .store-enrollment-container {
      padding: 0%;
      width: 20%;
    }
    header {
      font-size: 0.7rem;
    }
    label {
      width: 40%;
      float: left;
      font-size: 12px;
      padding-bottom: 0%;
      margin-left: 0px;
    }
    .input-content {
      width: 98%;
      padding: 1%;
      display: inline-block;
    }
    #nomalAddress {
      width: 70%;
    }
    #addressButton {
      padding-right: 0%;
      padding-left: 0%;
      margin-left: 1%;
    }
    button {
      margin-right: 4%;
    }
  }
  @include xs-mobile {
    header {
      font-size: 0.7rem;
    }
    label {
      width: 40%;
      float: left;
      font-size: 12px;
      padding-bottom: 0%;
      margin-left: 0px;
    }
    .before-upload {
      font-size: 30px;
    }
    input {
      padding: 5px 8px;
    }
    #addressButton {
      margin: 2%;
      padding: 5px 3px 5px 3px;
    }
    .input-content {
      width: 98%;
      padding: 1%;
      display: inline-block;
    }
    #nomalAddress {
      margin: 0px;
    }
  }
}
</style>
