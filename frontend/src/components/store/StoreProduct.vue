<template>
  <div v-if="loaded" class="store-product-container">
    <group-list :group-list="groupList" @updateGroupList="loadGroupList" @pointing="pointGroup"></group-list>
    <group-detail v-if="!editable" :group-id="groupId" @toggleEdit="toggleEdit" @pointing="pointMenu"></group-detail>
    <group-edit v-else :product-id="productId" :group-id="groupId" @toggleEdit="toggleEdit"></group-edit>
  </div>
</template>

<script>
import GroupList from '@/components/store/product/GroupList';
import GroupDetail from '@/components/store/product/GroupDetail';
import GroupEdit from '@/components/store/product/GroupEdit';
import { getMenuGroups } from '@/api/menuGroup';
import { mapMutations } from 'vuex';
export default {
  components: {
    GroupList,
    GroupDetail,
    GroupEdit,
  },
  data() {
    return {
      groupListIndex: 0,
      groupList: [],
      editable: false,
      menu: '',
      loaded: false,
      productId: -1,
    };
  },
  computed: {
    groupId() {
      return this.groupList.length > 0 ? this.groupList[this.groupListIndex].groupId : -1;
    },
  },
  async created() {
    await this.loadGroupList();
  },
  methods: {
    ...mapMutations(['setSpinnerState']),
    toggleEdit(e) {
      if (e) {
        this.menu = e;
        this.productId = this.menu.productId;
      } else this.menu = '';
      this.editable = !this.editable;
    },
    async loadGroupList() {
      try {
        const { data } = await getMenuGroups();
        this.loaded = true;
        this.groupList = data;
        return data;
      } catch (error) {
        console.log(error);
        alert('메뉴 그룹 로드에 실패하였습니다.');
      } finally {
        this.setSpinnerState(false);
      }
    },
    pointGroup(i) {
      this.groupListIndex = i;
    },
    pointMenu(i) {
      this.productId = i;
    },
  },
};
</script>

<style lang="scss" scoped>
.store-product-container {
  background-color: white;
  padding-top: 10px;
  width: 100%;

  @include lg-pc {
    @include flexbox;
    @include justify-content(center);
  }
  @include pc {
  }
  @include mobile {
    font-size: 14px;
  }
  @include xs-mobile {
    font-size: 12px;
  }
}
</style>
