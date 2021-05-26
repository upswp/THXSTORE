import { validateEmail, validatePwd } from '@/utils/validation';

describe('이메일 검사', () => {
  test('@이 없는 경우', () => {
    expect(!validateEmail('a@a')).toBe(true);
  });
  test('@이 있지만 뒤에 .이 없는 경우', () => {
    expect(!validateEmail('a@a')).toBe(true);
  });
  test('이메일 검사 성공', () => {
    expect(validateEmail('a@a.com')).toBe(true);
  });
});

describe('패스워드 검사', () => {
  test('6자 미만', () => {
    expect(!validatePwd('1111')).toBe(true);
  });
  test('13자 이상', () => {
    expect(!validatePwd('1111111111111')).toBe(true);
  });
  test('6자 이상, 12자 이하지만 한글 포함', () => {
    expect(!validatePwd('a2223가나1')).toBe(true);
  });
  test('6자 이상, 12자 이하지만 특수문자 포함', () => {
    expect(!validatePwd('abc123@!')).toBe(true);
  });
  test('6자 이상, 12 이하 숫자만', () => {
    expect(validatePwd('1111111')).toBe(true);
  });
  test('6자 이상, 12 이하 영어만', () => {
    expect(validatePwd('aaabbbccc')).toBe(true);
  });
  test('6자 이상, 12 이하 영어 숫자 조합', () => {
    expect(validatePwd('aaabcom12')).toBe(true);
  });
});
