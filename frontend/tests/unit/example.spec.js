const user = {
  name: 'Tony Stark',
  age: 11,
  job: 'student',
};

test('user matches', () => {
  expect(user).toMatchSnapshot();
});
