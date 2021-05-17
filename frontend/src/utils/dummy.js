const dummy = [
  {
    id: 1,
    name: '무영 회센터',
    storeCategory: 'KR_FOOD',
    phoneNumber: '0101-22-302',
    mainAddress: '대전 유성구 계산동 30-2',
    subAddress: '경성빌딩 1층',
    openTime: '10:00',
    closeTime: '23:00',
    closeDay: 'MON|THU',
    introduce:
      '대전에서 가장 신선하다고 자부하는 대현 회센터입니다! 개업 기념으로 오신 분들께 전복(소)를 서비스로 드립니다.',
    thumbImg: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYrNbxhZxQL9zgoE2ku8iWL0cz0y23EV9_uQ&usqp=CAU',
    logo: 'https://t10.pimg.jp/058/506/850/1/58506850.jpg',
    distance: 0.38,
    timeDealStart: '20:00',
    timeDealList: [
      {
        id: 1,
        name: '모듬 회 (소)',
        price: 24000,
        productImg: 'https://cdn.onul-hoi.com/c7ad0a_200320113820.jpg',
        amount: '1~2인분 ',
        rate: 30,
        stock: 0,
        introduce: '제철 숭어와 우럭, 광어, 놀래미로 구성되서 나갑니다.',
      },
      {
        id: 2,
        name: '모듬 회 (중)',
        price: 34000,
        productImg:
          'data:image/jpeg;base64,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',
        amount: '2~3인분 ',
        rate: 25,
        stock: 10,
        introduce: '제철 숭어와 우럭, 광어, 놀래미로 구성되서 나갑니다.',
      },
      {
        id: 3,
        name: '숭어회(중)',
        price: 26000,
        productImg:
          'https://pds.joins.com/news/component/htmlphoto_mmdata/202005/08/8f515359-c4c0-4648-9753-ee3e42a5b3e9.jpg',
        amount: '1~2인분 ',
        rate: 30,
        stock: 20,
        introduce: '제철 숭어를 맘껏 즐기신다면 추천.',
      },
    ],
  },
  {
    id: 2,
    name: 'RORO 11',
    storeCategory: 'CAFE',
    phoneNumber: '0101-22-302',
    mainAddress: '대전 유성구 덕명동 2 신선로',
    subAddress: '103',
    openTime: '10:00',
    closeTime: '20:00',
    closeDay: 'MON|THU',
    introduce: '신X 호텔 출신 쉐프가 직접 운영하는 소규모 레스토랑',
    thumbImg: 'https://t1.daumcdn.net/cfile/tistory/9991B33D5EBF8C8533',
    logo: 'https://i.pinimg.com/originals/11/f5/c3/11f5c3dbed67c8951adc669a8aa23123.png',
    distance: 0.38,
    timeDealStart: '20:00',
    timeDealList: [
      {
        id: 1,
        name: '새우 굴소스 파스타',
        price: 11000,
        productImg:
          'https://contents.lotteon.com/display/thmrcp/00/00/01/10/95/29/P05E9CF1A8A57E1DA6B65317961626761E6876096931CF11DB3DB33B6B99F054D/file',
        amount: '1인분 ',
        rate: 10,
        stock: 10,
        introduce: '제철 새우에 굴소스로 감칠맛이 매력적인 파스타 ',
      },
      {
        id: 2,
        name: '알리오 올리오',
        price: 12000,
        productImg:
          'https://images.unsplash.com/photo-1591154405747-5ee97d563988?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80',
        amount: '1인분 ',
        rate: 20,
        stock: 30,
        introduce: '기본적인 파스타면서 호불호가 없는, RORO에서 자신있게 추천하는 파스타',
      },
    ],
  },
];
export { dummy };
