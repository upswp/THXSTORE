<template>
  <div class="admin-container">
    <div class="admin-header">
      <h1>관리자 페이지</h1>
      <div class="select-container">
        <select id="" v-model="showStoreEnrollmentList">
          <option value="applyStoreEnrollment">신청 목록</option>
          <option value="modifyStoreEnrollment">수정 목록</option>
        </select>
      </div>
    </div>
    <manager-list :show-store-enrollment-and-modification-list="showStoreEnrollmentList"></manager-list>
    <br />
    <br />
  </div>
</template>

<script>
import ManagerList from '@/components/admin/ManagerList.vue';
export default {
  components: {
    ManagerList,
  },
  data() {
    return {
      showStoreEnrollmentList: 'applyStoreEnrollment',
    };
  },
};
</script>

<style lang="scss" scoped>
.admin-container {
  min-height: 100vh;
  background-color: $gray100;
  width: 100%;
  .admin-header {
    width: 100%;
    padding-top: 50px;
    margin-bottom: 10px;
  }
  h1 {
    width: 100%;
    text-align: center;
    margin-top: 30px;
    @include xs-mobile {
      font-size: 1.2rem;
      margin-top: 10px;
    }
  }
  .select-container {
    width: 95%;
    max-width: 1080px;
    margin: 1px auto;
    padding: 1% 2%;
    @include flexbox;
    justify-content: flex-end;
    select {
      width: 15%;
      height: 30px;
      padding: 2px 4px;
      border: 0.5px solid grey;
      -webkit-appearance: none; /*select 내부 스타일 제거 (화살표 제거)*/
      -moz-appearance: none;
      -o-appearance: none;
      appearance: none;
      // background: #fff url('../assets/image/select화살표.jpg') no-repeat center right;
      background: #fff url('https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg') no-repeat center right;
      // background-size: contain;
      background-size: contain;
      @include shadow4;

      border-radius: 4px;
      border: 1px solid #fff;
      -moz-box-shadow: outset 1px 1px 3px rgba(0, 38, 95, 1);
      -webkit-box-shadow: outset 1px 1px 3px rgba(0, 38, 95, 1);
      box-shadow: outset 1px 1px 3px rgba(0, 38, 95, 1);

      @include xs-mobile {
        width: 20%;
        font-size: 0.7rem;
        height: 20px;
        padding: 1 4px;
      }
      @include mobile {
        width: 24%;
        height: 25px;
        padding: 1 4px;
      }
    }
  }
}
</style>
