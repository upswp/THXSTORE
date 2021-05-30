<template>
  <div class="userstore-menu-containter">
    <div v-for="(menuListGroup, index) in storeMenuGroups" :key="index" class="menu-group-container">
      <div class="menu-group-title" @click="showAccordion(menuListGroup)">
        <div class="group-title">{{ menuListGroup.name }}</div>
        <awesome icon="chevron-up" class="chevron-up"></awesome>
      </div>
      <transition name="fade">
        <div v-if="menuListGroup.isShow" class="menu-list-groups">
          <div v-for="(menuList, menuIndex) in menuListGroup.product" :key="menuIndex" class="menu-list-group">
            <div class="menu-list-info">
              <div class="info-name">
                <b>{{ menuList.name }}</b>
              </div>
              <div class="info-content">
                {{ menuList.introduce }}
              </div>
              <br />
              <label class="info-degree"> {{ menuList.amount }} </label>
              <div class="info-price">{{ wonTrans(menuList.price) }}원</div>
            </div>
            <div class="menu-thumbnail">
              <img :src="menuList.productImg" />
            </div>
          </div>
        </div>
      </transition>
    </div>
  </div>
</template>

<script>
import { getStoreMenu } from '@/api/userStore';
import { mapMutations } from 'vuex';
import { wonTrans } from '@/utils/filters';
export default {
  data() {
    return {
      storeMenuGroups: [],
    };
  },
  async created() {
    await this.getMenuList();
    window.scrollTo({ top: 137, left: 0, behavior: 'smooth' });
  },
  methods: {
    wonTrans,
    ...mapMutations(['setSpinnerState']),
    async getMenuList() {
      try {
        this.setSpinnerState(true);
        const storeId = this.$route.params.storeId;
        const { data } = await getStoreMenu(storeId);
        console.log(data);
        data.forEach(x => {
          x['isShow'] = true;
        });
        console.log('isShow입력', data);
        this.storeMenuGroups = data;
        this.setSpinnerState(false);
        return data;
      } catch (error) {
        this.setSpinnerState(false);
        alert('타임딜 항목을 불러오는데 실패했습니다.');
      }
    },
    showAccordion(menuListGroup) {
      menuListGroup.isShow = !menuListGroup.isShow;
      // 자바스크립트로 조정하는 코드
      // e.currentTarget.querySelector('svg').classList.toggle('upside-down');
      // e.currentTarget.nextElementSibling.classList.toggle('hidden');
    },
  },
};
</script>

<style scoped lang="scss">
// @import '@/assets/scss/sample2';
.userstore-menu-containter {
  @include flexbox;
  flex-wrap: wrap;
  .menu-group-container {
    width: 100%;
    border: 2px $gray200 solid;
    box-shadow: 0 0 0 2px $gray200 inset;
    margin-bottom: 10px;
    .menu-group-title {
      background-color: $gray100;
      @include flexbox;
      @include align-items(flex-start);
      width: 100%;
      padding: 1%;
      font-size: 1.3rem;
      cursor: pointer;

      @include xs-mobile {
        padding: 1.3%;
      }
      // border: 2px $blue400 solid;
      .group-title {
        width: 95%;
        display: inline-block;
        margin: auto;
        font-weight: $bold;
        @include xs-mobile {
          font-size: 0.7em;
          margin-left: 3px;
          font-weight: $bold;
        }
      }
      .chevron-up {
        color: $gray600;
        font-size: 1.2em;
        margin-right: 1.4%;
        &:hover {
          cursor: pointer;
          color: $gray800;
        }
        @include xs-mobile {
          font-size: 0.8em;
        }
      }
    }
    .fade-enter-active,
    .fade-leave-active {
      transition: opacity 0.5s;
    }
    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
      opacity: 0;
    }
    // @keyframes fade-in {
    //   from {
    //     opacity: 0;
    //   }
    //   to {
    //     opacity: 1;
    //   }
    // }

    // @keyframes fade-out {
    //   from {
    //     opacity: 1;
    //   }
    //   to {
    //     opacity: 0;
    //   }
    // }

    .upside-down {
      transform: rotate(180deg);
    }
    .hidden {
      display: none;
      animation: fade-out 1s;

      // visibility: hidden;
    }
    .menu-list-groups {
      @include flexbox;
      flex-wrap: wrap;
      .menu-list-group {
        border: 1px solid $gray200;
        @include shadow3;
        margin: 1%;
        @include flexbox;
        width: 48%;
        padding: 1%;
        flex-wrap: wrap;
        @include xs-mobile {
          width: 100%;
        }
      }

      .menu-list-info {
        width: 70%;
        .info-name {
          padding-bottom: 3%;
          @include xs-mobile {
            font-size: 0.8rem;
          }
        }
        .info-content {
          width: 90%;
          font-size: 0.8em;
          @include ellipsis;
          // text-overflow: ellipsis; overflow: hidden;
          // white-space: nowrap;
          // -webkit-line-clamp:3; /* 라인수 */
          // -webkit-box-orient:vertical;
          // white-space:normal;
          // line-height:1.2em;
          // height:3.6em;
          // overflow:hidden;
          // text-align:left;
          @include lg-pc {
            height: 2.4em;
          }
          @include xs-mobile {
            // min-height: 18%;
          }
        }
        .info-price {
          font-size: 0.9em;
          @include xs-mobile {
            font-size: 0.8em;
          }
        }
        .info-degree {
          font-size: 0.7em;
        }
      }
      .menu-thumbnail {
        width: 30%;
        text-align: center;
        img {
          object-fit: cover;
          object-position: center 50%;
          border-radius: 5%;
          @include lg-pc {
            width: 130px;
            height: 140px;
          }
          @include pc {
            width: 95px;
            height: 100px;
          }
          @include mobile {
            width: 100px;
            height: 105px;
          }
          @include xs-mobile {
            width: 89px;
            height: 95px;
          }
        }
      }
    }
  }
}

.userstore-container {
  @include flexbox;
  flex-wrap: wrap;
  // align-items: flex-start;
  h2 {
    @include xs-mobile {
      font-size: 17px;
    }
  }
  .nav-aside {
    align-self: flex-start;
    position: fixed;
    text-align: center;
    font-size: 1rem;
    padding: 10px 0;
    margin: 0;
    width: 200px;
    min-height: 100%;
    box-shadow: 2px 0 5px rgba(0, 0, 0, 0.3);
    div {
      padding: 10px 0;
      &:hover {
        background: $blue400;
        color: white;
      }
      &.active {
        background: $blue400;
        color: white;
      }
      @include mobile {
        padding: 4px;
        flex-basis: 25%;
      }
      @include xs-mobile {
        padding: 2px;
        flex-basis: 25%;
        font-size: 11px;
      }
    }
    &:hover {
      cursor: pointer;
    }
    @include mobile {
      // display: none;
      @include flexbox;
      @include justify-content(space-evenly);
      padding: 5px;
      width: 100%;
      position: static;
      min-height: 5px;
      flex-grow: 0;
    }
    @include xs-mobile {
      @include flexbox;
      @include justify-content(space-evenly);
      padding: 3px;
      width: 100%;
      position: static;
      min-height: 5px;
      flex-grow: 0;
    }
  }
  .main-content {
    width: calc(100% - 200px);
    padding: 20px;
    max-width: 800px;
    margin-left: 200px;
    @include mobile {
      width: 100%;
      margin: 0px;
    }
    @include xs-mobile {
      margin: 0px;
      width: 100%;
    }
  }
  //   border: 2px blue solid;
  .header-container {
    .time-deal-ani {
      font-family: neon;
      color: #fb4264;
      font-size: 2vw;
      line-height: 3vw;
      text-shadow: 0 0 2vw #f40a35;
      animation: neon 1s ease infinite;
      -moz-animation: neon 1s ease infinite;
      -webkit-animation: neon 1s ease infinite;
    }
    @keyframes neon {
      0%,
      70% {
        //   text-shadow: 0 0 1vw #FA1C16, 0 0 3vw #FA1C16, 0 0 10vw #FA1C16, 0 0 10vw #FA1C16, 0 0 .4vw #FED128, ;
        color: #f40a35;
      }
      50% {
        text-shadow: 0 0 0.5vw #800e0b, 0 0 1.5vw #800e0b, 0 0 5vw #800e0b, 0 0 5vw #800e0b;
        //   color: #806914;
      }
    }
    @include pc {
    }
    .header-thumbnail {
      padding: 1%;
      border: 2px $gray200 solid;
    }
  }
  h2 {
    text-align: center;
  }
}
</style>
