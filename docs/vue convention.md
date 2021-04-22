# Vue convention 
---
## 필수 사항
0. 변수, 함수명:
    - 두 단어 이상일 경우 camelCase를 반드시 준수.
1. Vue 파일 이름
    - 확장자가 vue인 파일의 이름은 PaskalCase를 준수.
    - 반드시 두단어 이상을 조합할 것. 예) LoginForm, MainPage
        - 두단어가 만들기 어려울 경우에는 The를 붙일 것. 예) TheHeader
        - vue 파일이름은 다음과 같은 양식으로 적을 것
            - {FuntionTask}, 예) LoginForm, PostAddForm, PostEditForm
            - {PropertyRole}, 예) BounceSpinner, BigButton, MainHeader
2. 파일 참조
    - 파일 참조는 '@'를 활용한 절대경로로 쓸 것을 권장.
        -  리팩토링에 파일위치가 바뀔 때마다 상대 경로를 체크해야하기 때문.
        ```
        예) 
        import MainHeader from '../component/common/MainHeader' (x),
        import MainHeader from '@/componenet/common/MainHeader' (o),
3. src 폴더 밑 하위 폴더
    1. components 폴더
        - view 폴더의 파일을 기능 단위로 쪼깬 component들의 집합
            - 예시) MainPage의 Header, Body, Footer로 기능을 나눌 경우, MainBody, MainHeader, MainFooter등의 형태로 컴포넌트 생성할 것.
        - views 폴더와 같이 목적에 따라 폴더 분류가 가능
        - 공통적으로 사용이 가능한 컴포넌트는 common 폴더에 저장할 것. 예) components/common
    2. views 폴더
        - router-view를 통해 이동할 vue파일만 여기에 포함됨
        - 목적에 따라 폴더 생성 가능. 예) views/qna
        - views 폴더 안의 vue 파일들은 이름이 반드시 Page로 끝난다. 예) NotFoundPage.vue
    3. assets 폴더
        - 이미지, svg, CSS, SCSS와 같은 리소스 파일을 관리할 폴더
    4. router 폴더
        - index.js를 기본으로 router link를 등록한다.
        - path는 "/{기능}/{속성}" 형태로 적는다. 예) "/login", "/post/:id" (동적할당의 경우)
        - component 등록은 빠른 로딩을 위해 [코드 스플리팅](https://joshua1988.github.io/vue-camp/advanced/code-splitting.html)을 사용한다.
            - component: MainPage                               (x)
            - component: () => import('@/views/MainPage.vue)    (o) 
        - [네비게이션 가드](https://joshua1988.github.io/web-development/vuejs/vue-router-navigation-guards/)를 사용하고자 할 경우 
            - 모든 url에 대한 검사를 원할 경우 => 전역 가드
            - 특정 url에 대한 검사를 원할 경우 => 라우터 가드
            - 특정 view 컴포넌트에 들어갈 때, 또는 나갈 때 검사를 원할 경우 => 컴포넌트 가드
    5. store 폴더
        - 데이터를 전역으로 관리하기 위해서 사용하는 폴더. cookie나 localstorage랑 연계 가능
        - index.js를 기본으로 하고 코드가 길어질 경우, state, getters, actions, mutations를 모듈화해서 index.js에 import할 것
        - state의 property 키는 데이터를 나타내는 단어를 쓸 것. 
            - 예) id, token
        - getters의 함수는 ,단순히 state의 키를 가져올 경우, get{State}(state, ...args) 형태로 쓸 것. 
            - 예) getId(state), getToken(state) 
        - mutations의 함수는 {functionState}(state, ...args) 형태로 쓸 것. 
            - 예) setId(state), clearUserInfo(state) 
            - 만약 state를 한번에 여러개 처리할 경우, 각각의 함수를 따로 만들어줄 것. 
            - 예) clearId(state), clearToken(state)
        - actions의 함수는 {FUNCTION}({commit},...args) 형태로 쓸 것. 
            - 예) LOGIN({commit}, userData)
        - 왠만해서는 helper함수를 사용해서 불러 올 것.
    6. utils 폴더
        - 여러 파일에서 재사용 가능할 함수들을 따로 저장해둔 폴더
        - utils 폴더 안 파일명은 camelCase을 준수한다.
        - cookies 또는 validation, filter와 같이 공통적으로 사용이 가능한 함수들은 기능별로 저장한다.
    7. api 폴더
        - api 관리를 위해서 기능별로 분류할 폴더
        - 기능별로 js 파일을 구분해서 index.js에 등록함
        - baseURL은 .env.local 파일로 관리
4. v-bind
    - v-bind: 에서 v-bind를 생략해서 적는다.
        - 예) 
            ```html
            <component-example :id="id"></component-example>
             ```
    - class를 동적으로 제어하고 싶다면 []를 써서 항목화한다.
        - 예)
            ```js
            <template>
                <div :class=[computedWeight, computedLoading, computedFixed]>
            </template>
            <script>
            export default {
                props: {
                    loading: {
                        type: Boolean,
                        default: false,
                        validator(value) {
                            return typeof value === 'boolean';
                        },
		            },
                    fixed: {
                        type: Boolean,
                        default: false,
                        validator(value) {
                            return typeof value === 'boolean';
                        },
                    },
                },
                computed : {
                    computedValue() {
                        return 'text-bold';
                    },
                    computedLoading(){
                        // this.loading이 true이면 loading이란 클래스가 붙는다.
                        return {loading : this.loading};
                    },
                    computedFixed() {
                        return { 'p-fixed': this.fixed };
                    },
                }
            }
            </script>
            ```

## 권장 사항
1. store 사용
    - template 안의 마크업에서 이벤트 처리시 동작할 $store 함수가 길이가 짧더라도 method에 기술할 것을 권장
        -예)
        ```
         <div @click="$store.state.id=3"> (X) 
         ////////////////////////////////////
         <div @click="assign3"> (O) 
         method:{
             assign3(){
                 // 단순한 예시로 적긴했지만, 왠만해서는 mutation으로 값 할당 시킬 것.
                 this.$store.state.id=3
             }
         }
         ```
2. Vuex mapGetters 헬퍼 사용
    - computed에 this.$store.getters로 직접 접근하지말고 ...mapGetters([...,...,]) 식으로 불러오자. [참조](https://vuex.vuejs.org/kr/guide/getters.html)
